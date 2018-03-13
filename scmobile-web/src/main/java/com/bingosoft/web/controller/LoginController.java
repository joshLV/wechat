package com.bingosoft.web.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.bingosoft.core.web.service.IOperationService;
import com.bingosoft.models.dto.FansInfoByOauthDto;
import com.bingosoft.models.dto.UserInfoDto;
import com.bingosoft.models.mongodb.entities.WechatUserInfo;
import com.bingosoft.persist.mongodb.dao.IWechatUserInfoRepository;
import com.bingosoft.utils.JSONUtils;
import com.bingosoft.utils.UserInfoUtils;
import com.bingosoft.utils.crypt.ShareDesUtils;
import com.bingosoft.utils.crypt.TokenUtils;
import com.bingosoft.web.config.WebConfig;




@RestController
public class LoginController {
	private Logger logger = LoggerFactory.getLogger(getClass());
	
	@Autowired
	WebConfig webConfig;
	

	@Autowired
	IWechatUserInfoRepository wechatUserInfo;

	@Autowired
	IOperationService operationService;
	
	@GetMapping("/login")
	public void weLogin(@RequestParam(name = "code", required = false) String code,
			@RequestParam(name = "state", required = false) String state,
			@RequestParam(name = "url", required = false) String url, HttpServletResponse httpResponse)
	{
		// 1. 用户同意授权,获取code
				logger.info("收到微信重定向跳转.");
				logger.info("用户同意授权,获取code:{} , state:{}", code, state);

				// 2. 通过code换取网页授权access_token
				if (code != null) {

					String APPID = webConfig.getAppId();
					String SECRET = webConfig.getAccountId();
					String CODE = code;
					String WebAccessToken = "";
					String openId = "";
					String nickName, sex, openid = "";
					String REDIRECT_URI = "http://wmymtx.ngrok.cc/url";
					String SCOPE = "snsapi_userinfo";

					FansInfoByOauthDto dto = operationService.getFansInfoByOauth2(webConfig.getAccountId(), CODE);
					UserInfoDto user = new UserInfoDto();
					user.setHeadImg(dto.getHeadimgurl());
					user.setOpenId(dto.getOpenid());
					user.setUserName(dto.getNickname());
					user.setPhoenNo(dto.getTelnum());
					user.setSubscribeTime(dto.getSubscribeTime());
					if (StringUtils.isEmpty(dto.getTelnum()))
						user.setUserId(dto.getOpenid());
					else
						user.setUserId(dto.getTelnum());
					if (dto == null && StringUtils.isEmpty(dto.getOpenid())) {
						try {
							httpResponse.sendRedirect("http://wx.10086.cn/sichuan/wxzq/llzq/author.html?token=none");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						return;
					}
					if (dto.getStatus() == 3001) {
						try {
							httpResponse.sendRedirect("http://wx.10086.cn/sichuan/wxzq/llzq/qrcode.html");
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					if (!StringUtils.isEmpty(user.getOpenId())) {
						String desOpenId = "";
						try {
							desOpenId = TokenUtils.encrypt(JSONUtils.toJson(user));
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						} // CmccDesUtil.encode(user.getOpenId())
							// ;//CryptoUtils.encodeSHA(user.getOpenId());
						String shareId="";
						try {
							shareId = TokenUtils.encrypt(user.getPhoenNo());
						} catch (Exception e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						// 目前取微信生成的token作为用户缓存的key
						// userInfoService.addUser(user, desOpenId);
						WechatUserInfo wechat = new WechatUserInfo();
						wechat.setHeadImg(user.getHeadImg());
						wechat.setOpenId(user.getOpenId());
						wechat.setPhoenNo(user.getPhoenNo());
						wechat.setUserId(user.getUserId());
						wechat.setUserName(user.getUserName());
						wechatUserInfo.save(wechat);
						// wechat.setId(user.getOpenId());
						try {
							httpResponse.sendRedirect(
									"http://wx.10086.cn/sichuan/wxzq/llzq/author.html?os=" + desOpenId + "&token=" + shareId);
							// if (!url.contains("?"))
							// httpResponse.sendRedirect(url + "?token=" + desOpenId);
							// else
							// httpResponse.sendRedirect(url + "&token=" + desOpenId);
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}

				} else {
					String APPID = webConfig.getAppId();
					String SECRET = webConfig.getAccountId();
					String CODE = code;
					String WebAccessToken = "";
					String openId = "";
					String nickName, sex, openid = "";
//					String REDIRECT_URI = "http://wx.10086.cn/sichuan/wxzq/llzq/login";
					String REDIRECT_URI = "http://wx.10086.cn/sichuan/wxzq/llzq/login";
					String SCOPE = "snsapi_userinfo";

					String getCodeUrl = UserInfoUtils.getCode(APPID, REDIRECT_URI, SCOPE);
					logger.info("第一步:用户授权, get Code URL:{}", getCodeUrl);
					try {
						httpResponse.sendRedirect(getCodeUrl);
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
	}
	
	@GetMapping("/locallogin")
	public void weLocalLogin(@RequestParam(name = "code", required = false) String code,
			@RequestParam(name = "state", required = false) String state,
			@RequestParam(name = "url", required = false) String url, HttpServletResponse httpResponse,HttpServletRequest request)
	{
	
		try {
		   
		   
			//httpResponse.sendRedirect("http://wx.10085.cn/sichuan/url/author?token=user_test01&share="+ShareDesUtils.encode("sharecode"));
			//httpResponse.sendRedirect("http://"+ip+"/author?token=user_test02&share="+ShareDesUtils.encode("sharecode"));
		   //httpResponse.sendRedirect("http://localhost:8001/author?token=user_test02&share="+ShareDesUtils.encode("sharecode"));
		  // httpResponse.sendRedirect(url+"?os=none");
			 httpResponse.sendRedirect(url+"?os=26F86C8245045FC656E3EE6B156D54943748E844C3C5D44A3BAD935D7480A2BA367EB523C6D2AA9A5324786E7B430EBBA4293008D086D1A5195A711FEF265449CB8AE62671E8C798B45E23170B6C49C34F4A0082841EF23C55B4C6F15F7369EE1D7C4D92100EC878E164B7E86DDEA3231F9AC14645FCBEDE187CBF6CAB66B348EBE9504C79B320085EB8F968743B08CEC9110E33AD43BF4D883B8A41488B396B26DDB98C2EE3B1D22AAC3ACAFB5832BA6369BBCC75CFE133064F42038E5EE9CD31F2B3CCE9BF18FD6206A2FBC82E58E9AE876773DBC628B166E4FAAED359B5A5");
			//httpResponse.sendRedirect("http://localhost:8001/qrcode?token=user_test01&share="+ShareDesUtils.encode("sharecode"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
