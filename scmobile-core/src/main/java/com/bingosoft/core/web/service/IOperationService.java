package com.bingosoft.core.web.service;

import com.bingosoft.models.dto.FansInfoByOauthDto;
import com.bingosoft.models.dto.JsSignatureDto;
import com.bingosoft.models.dto.MobileDto;
import com.bingosoft.models.dto.TemplateMessageInputDto;

public interface IOperationService {
	/**
	 * 通过手机号获取用户基本信息(get)
	 * @param accountId
	 * @param phone
	 * @return
	 */
	String getUserInfoByPhone(String accountId, String phone);
	
	/**
	 * 根据openid获取用户绑定手机号
	 * @param openId
	 * @return
	 */
	MobileDto getMobile(String openId);
	
	/**
	 * 根据code获取用户基本信息，code由腾讯网页授权返回
	 * @param accountId 公众号ID
	 * @param code 填写获取的code参数
	 * @return
	 */
	FansInfoByOauthDto getFansInfoByOauth2(String accountId,String code);
	
	/**
	 * 获取用户信息
	 * @param code
	 * @return
	 */
	String fansInfo(String code);
	
	/**
	 * 获取验证码
	 * @param code
	 * @return
	 */
	MobileDto sendCaptcha(String openId,String telNum);
	
	/**
	 * 绑定手机号码
	 * @param openId
	 * @param telNum
	 * @param captcha
	 * @return
	 */
	MobileDto telbindbycaptcha(String openId,String telNum,String captcha);
	
	/**
	 * JS签名
	 * @param accountId
	 * @param url
	 * @return
	 */
	JsSignatureDto getJsSignature(String url);
	
	/**
	 * 发送模版消息
	 * @param input
	 * @return
	 */
	MobileDto templateMessage(TemplateMessageInputDto input);
}
