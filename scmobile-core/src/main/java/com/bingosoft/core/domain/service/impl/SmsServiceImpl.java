package com.bingosoft.core.domain.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bingosoft.core.domain.service.ISmsService;
import com.bingosoft.core.web.service.ICsfWebService;
import com.bingosoft.models.prefix.RedisKeyPrefix;
import com.bingosoft.persist.mycat.dao.IOrderByPartMapper;
import com.bingosoft.persist.redis.dao.IRedisService;
import com.bingosoft.utils.RandomUtils;

@Service
public class SmsServiceImpl implements ISmsService {
	@Autowired
	ICsfWebService csfWebService;
	
	@Autowired
	IOrderByPartMapper orderMapper;

	@Autowired
	IRedisService redisService;

	@Override
	public boolean SendSms(String phoneNo) {
		String random = RandomUtils.getRandom(6);
		String smsKey=String.format(RedisKeyPrefix.Sms_Prefix, phoneNo);
		redisService.set(smsKey, random,60);
		String smsContent = String.format("尊敬的用户，您正在微信流量超市订购业务，验证码[%s],请在1分钟内按页面提示提交验证码，切勿将验证码泄露于他人。", random);
		boolean isSend = csfWebService.smsCentral(phoneNo, smsContent);
		return isSend;
	}
}
