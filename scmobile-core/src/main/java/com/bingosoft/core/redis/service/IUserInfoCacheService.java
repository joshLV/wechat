package com.bingosoft.core.redis.service;

import com.bingosoft.models.dto.UserInfoDto;
import com.bingosoft.models.dto.UserInfoOutputDto;

public interface IUserInfoCacheService {
	public UserInfoDto getUser(String key);

	public void setUser(String key, UserInfoDto user);
	
	public UserInfoOutputDto getUserInfo(String key);
}
