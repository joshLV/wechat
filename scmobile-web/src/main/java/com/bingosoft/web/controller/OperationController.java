package com.bingosoft.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bingosoft.core.mycat.service.IBindUserByPartService;
import com.bingosoft.core.redis.service.IUserInfoCacheService;
import com.bingosoft.core.web.service.IOperationService;
import com.bingosoft.models.dto.BindUserInfoOutputDto;
import com.bingosoft.models.dto.BindUserInputDto;
import com.bingosoft.models.dto.CaptchaInputDto;
import com.bingosoft.models.dto.MessageDto;
import com.bingosoft.models.dto.MobileDto;
import com.bingosoft.models.dto.TelPhoneInputDto;
import com.bingosoft.models.dto.UserInfoDto;
import com.bingosoft.models.mongodb.entities.WechatUserInfo;
import com.bingosoft.models.msg.BaseMessage;
import com.bingosoft.models.msg.ResponseMessage;
import com.bingosoft.persist.mongodb.dao.IWechatUserInfoRepository;
import com.bingosoft.utils.JSONUtils;
import com.bingosoft.utils.crypt.TokenUtils;
import com.bingosoft.web.exception.WechatException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "流量专区", description = "外部接口API")
@RequestMapping("/operation/api/v1")
@RestController
public class OperationController {

	
	
	private Logger logger = Logger.getLogger(getClass());

	@Autowired
	IBindUserByPartService bingUserService;
	
	@Autowired
	private IOperationService opService;

	@Autowired
	IUserInfoCacheService userCacheService;

	String tokenHeader = "os";

	@Autowired
	IWechatUserInfoRepository wechatUserInfo;

	@ApiOperation(value = "获取短信验证码", notes = "获取短信验证码")
	@RequestMapping(value = "/sendCaptcha", method = RequestMethod.POST)
	public BaseMessage sendCaptcha(HttpServletRequest request, @RequestBody CaptchaInputDto input)
			throws WechatException {
		UserInfoDto userDto = null;// new UserInfoDto();
		// String token = request.getHeader(tokenHeader);
		// if (StringUtils.isEmpty(token)) {
		// return new BaseMessage(502, true, "非法请求");
		// }
		// userDto = userCacheService.getUser(token);
		MessageDto dtos = validateBaseToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new BaseMessage(dtos.getStatus(), true, dtos.getMessage());
		}
		try {
			MobileDto dto = opService.sendCaptcha(userDto.getOpenId(), input.getTelNum());
			if (dto.getStatus() == 0) {
				return new BaseMessage(200, true, "获取验证码成功");
			}
			return new BaseMessage(400, true, dto.getMessage());
		} catch (Exception ex) {
			throw new WechatException(ex.getMessage());
		}
	}

	@ApiOperation(value = "绑定手机号", notes = "绑定手机号")
	@RequestMapping(value = "/telbindbycaptcha", method = RequestMethod.POST)
	public ResponseMessage<BindUserInfoOutputDto> telbindbycaptcha(HttpServletRequest request,
			@RequestBody TelPhoneInputDto input) throws WechatException {
		// UserInfoDto userDto = null;
		UserInfoDto userDto = null;// new UserInfoDto();
		// String token = request.getHeader(tokenHeader);
		// if (StringUtils.isEmpty(token)) {
		// return new BaseMessage(502, true, "非法请求");
		// }
		// userDto = userCacheService.getUser(token);
		MessageDto dtos = validateBaseToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new ResponseMessage<BindUserInfoOutputDto>(dtos.getStatus(), true, dtos.getMessage());
		}
		try {
			MobileDto dto = opService.telbindbycaptcha(userDto.getOpenId(), input.getTelNum(), input.getCaptcha());
			logger.info("绑定信息" + dto.getMessage() + "状态码:" + dto.getStatus());
			if (dto.getMessage().equals("绑定成功")) {

				userDto.setPhoenNo(input.getTelNum());
				userDto.setUserId(input.getTelNum());
				// userCacheService.setUser(token, userDto);
				String token = TokenUtils.encrypt(JSONUtils.toJson(userDto));
				WechatUserInfo wechat = new WechatUserInfo();
				wechat.setHeadImg(userDto.getHeadImg());
				wechat.setOpenId(userDto.getOpenId());
				wechat.setPhoenNo(userDto.getPhoenNo());
				wechat.setUserId(userDto.getUserId());
				wechat.setUserName(userDto.getUserName());
				BindUserInputDto inDto=new BindUserInputDto();
				inDto.setModuleId(input.getModuleId());
				inDto.setOpenId(userDto.getOpenId());
				inDto.setPhoneNo(userDto.getPhoenNo());
				inDto.setPartId(getPartId(userDto.getPhoenNo()));
				inDto.setSubscribeTime(userDto.getSubscribeTime());
				if(!StringUtils.isEmpty(input.getShare()))
				{
					String sharePhone=TokenUtils.decrypt(input.getShare());
					inDto.setSharePhoneNo(sharePhone);
				}
				
				BindUserInfoOutputDto outDto = new BindUserInfoOutputDto();
				String shareId = "";
				try {
					bingUserService.addUser(inDto);
					wechatUserInfo.save(wechat);
					shareId = TokenUtils.encrypt(userDto.getPhoenNo());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				outDto.setShare(shareId);
				outDto.setToken(token);
				return new ResponseMessage<BindUserInfoOutputDto>(200, true, token, outDto);
			}
			return new ResponseMessage<BindUserInfoOutputDto>(400, true, dto.getMessage());
		} catch (Exception ex) {
			throw new WechatException(ex.getMessage());
		}
	}

	private int getPartId(String phoneNo) {
		return Integer.valueOf(phoneNo.substring(10));
	}
	
	@SuppressWarnings("unused")
	private MessageDto validateToken(HttpServletRequest request) {
		MessageDto dto = validateBaseToken(request);
		if (dto.getUserInfo() == null)
			return dto;
		if (StringUtils.isEmpty(dto.getUserInfo().getPhoenNo())) {
			dto.setStatus(501);
			dto.setMessage("您暂未绑定手机号");
			return dto;
		}
		// dto.setUserInfo(userDto);
		return dto;
	}

	@SuppressWarnings("unused")
	private MessageDto validateBaseToken(HttpServletRequest request) {
		MessageDto dto = new MessageDto();
		String token = request.getHeader(tokenHeader);
		if (StringUtils.isEmpty(token)) {
			dto.setStatus(502);
			dto.setMessage("非法请求");
			return dto;
		}
		String user = "";
		try {
			user = TokenUtils.decrypt(token);
		} catch (Exception e) {

		}
		if (StringUtils.isEmpty(user)) {
			dto.setStatus(502);
			dto.setMessage("非法请求");
			return dto;
		}
		UserInfoDto userDto = JSONUtils.toBean(user, UserInfoDto.class);

		if (userDto == null) {
			dto.setStatus(503);
			dto.setMessage("登录超时，请重新进入该页面");
			return dto;

		}
		dto.setUserInfo(userDto);
		return dto;
	}
}
