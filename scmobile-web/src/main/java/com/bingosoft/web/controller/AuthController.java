package com.bingosoft.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bingosoft.models.dto.BindUserInfoOutputDto;
import com.bingosoft.models.dto.TelPhoneInputDto;
import com.bingosoft.models.dto.UserFlowInfoOutputDto;
import com.bingosoft.models.dto.UserInfoDto;
import com.bingosoft.models.mongodb.entities.UserPhone;
import com.bingosoft.models.mongodb.entities.WechatUserInfo;
import com.bingosoft.models.msg.ResponseMessage;
import com.bingosoft.persist.mongodb.dao.IWechatUserInfoRepository;
import com.bingosoft.utils.JSONUtils;
import com.bingosoft.utils.crypt.Des;
import com.bingosoft.utils.crypt.ShareDesUtils;
import com.bingosoft.utils.crypt.TokenUtils;
import com.bingosoft.web.exception.WechatException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "流量专区", description = "手机App授权Api")
@RestController
@RequestMapping("/rest/api/v1")
public class AuthController {
	
	@Autowired
	IWechatUserInfoRepository wechatUserInfo;
	
	@ApiOperation(value = "手机端App授权", notes = "手机端App授权")
	@RequestMapping(value = "/appUser", method = RequestMethod.POST)
	public ResponseMessage<BindUserInfoOutputDto> AppAuth(HttpServletRequest request
			) throws WechatException {
		// UserInfoDto userDto = new UserInfoDto();
		// String token = request.getHeader(tokenHeader);
		// if(StringUtils.isEmpty(token))
		// {
		// return new ApiResponseMsg(502, true, "非法请求");
		// }
		// userDto = userInfoService.getUser(token);

		String code = request.getHeader("code");
		//code="nUonxlxXm66vhd2CVHbmoSVrJZO01mNz";
		if (StringUtils.isEmpty(code)) {
			return new ResponseMessage<BindUserInfoOutputDto>(501, false, "非法请求", null);
		}
		try {
			String des = Des.decrypt(code);
			String phoneNo="";
			if (!StringUtils.isEmpty(des) && des.contains("="))
				phoneNo=des.split("=")[1];
			
				UserInfoDto user = new UserInfoDto();
				user.setHeadImg("http://wx.qlogo.cn/mmopen/NjkzhkuBicLRAwsaEiafU3LM0bD4fDN9ABjfkMQwEbh7FKE2blgoHGN5rRTW3RMeWI7Ud5ArCJ3xZbkZSbletTvQ/0");
				//user.setOpenId(phoneNo);
				user.setUserName(phoneNo.replaceAll("(\\d{3})\\d{4}(\\w{4})", "$1****$2"));
				user.setPhoenNo(phoneNo);
				user.setUserId(phoneNo);

				String desOpenId = TokenUtils.encrypt(JSONUtils.toJson(user));//CryptoUtils.encodeSHA(user.getOpenId());
				String shareId = ShareDesUtils.encode(user.getOpenId());
				// 目前取微信生成的token作为用户缓存的key
				BindUserInfoOutputDto dto = new BindUserInfoOutputDto();
				dto.setShare(shareId);
				dto.setToken(desOpenId);
				//userCacheService.setUser(desOpenId, user);
				WechatUserInfo wechat = new WechatUserInfo();
				wechat.setHeadImg(user.getHeadImg());
				wechat.setOpenId(user.getOpenId());
				wechat.setPhoenNo(user.getPhoenNo());
				wechat.setUserId(user.getUserId());
				wechat.setUserName(user.getUserName());
				wechatUserInfo.save(wechat);
				return new ResponseMessage<BindUserInfoOutputDto>(200, true, "成功", dto);
			
		} catch (Exception ex) {
			throw new WechatException(ex.getMessage());
		}
	}
}
