package com.bingosoft.core.mongodb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bingosoft.core.mongodb.service.IUserInfoService;
import com.bingosoft.models.mongodb.entities.UserInfo;
import com.bingosoft.persist.mongodb.dao.IUserInfoRepository;

@Service
public class UserInfoServiceImpl implements IUserInfoService{

	@Autowired
	IUserInfoRepository userInfoRepository;
	
	@Override
	public void writeUserInfo(UserInfo user) {
		// TODO Auto-generated method stub
		userInfoRepository.save(user);
	}

	@Override
	public UserInfo getUserInfo(String userId) {
		// TODO Auto-generated method stub
		userInfoRepository.findOne(userId);
		return null;
	}

}
