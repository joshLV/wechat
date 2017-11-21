package com.bingosoft.core.redis.service.impl;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.bingosoft.core.redis.service.IUserInfoCacheService;
import com.bingosoft.models.dto.UserInfoDto;
import com.bingosoft.models.dto.UserInfoOutputDto;
import com.bingosoft.persist.redis.dao.IRedisService;
import com.bingosoft.utils.serialize.JsonUtils;

@Service
public class UserInfoCacheServiceImpl implements IUserInfoCacheService{

	@Autowired
	IRedisService redisService;
	
	@Override
	public UserInfoDto getUser(String key) {
		// TODO Auto-generated method stub
		String userCache=redisService.get(key);
		if(StringUtils.isEmpty(userCache))
		{
			return null;
		}
		
		return JsonUtils.toBean(userCache, UserInfoDto.class);
	}

	@Override
	public void setUser(String key, UserInfoDto user) {
		// TODO Auto-generated method stub
		if(user!=null)
		{
			String userJson=JSON.toJSONString(user);
			redisService.set(key, userJson);
		}
		
	}

	@Override
	public UserInfoOutputDto getUserInfo(String key) {
		// TODO Auto-generated method stub
		String userCache=redisService.get(key);
		if(StringUtils.isEmpty(userCache))
		{
			return null;
		}
		//return JsonUtils.toBean(userCache.replace("\n", ""), UserInfoOutputDto.class);
		
		UserInfoDto dto= JsonUtils.toBean(userCache, UserInfoDto.class);
		UserInfoOutputDto out=new UserInfoOutputDto();
		if(dto!=null)
		{
			out.setHeadImg(dto.getHeadImg());
			out.setPhoenNo(dto.getPhoenNo());
			out.setUserName(dto.getUserName());
		}
		return out;
	}

}
