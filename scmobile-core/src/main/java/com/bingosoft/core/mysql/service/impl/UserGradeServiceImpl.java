package com.bingosoft.core.mysql.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bingosoft.core.mysql.service.IUserGradeService;
import com.bingosoft.models.dto.UserBasicCurrentOutputDto;
import com.bingosoft.models.dto.UserGradeOutputDto;
import com.bingosoft.models.prefix.RedisKeyPrefix;
import com.bingosoft.persist.mysql.dao.IUserGradeMapper;
import com.bingosoft.persist.redis.dao.IRedisService;
import com.bingosoft.utils.serialize.JsonUtils;

@Service
public class UserGradeServiceImpl implements IUserGradeService{

	@Autowired
	IRedisService redisService;
	
	@Autowired
	IUserGradeMapper userGradeMapper;
	
	@Override
	public List<UserGradeOutputDto> getUserGradeList() {
		// TODO Auto-generated method stub
		String userGradeKey = RedisKeyPrefix.User_Grade_Prefix;
		String cache = redisService.get(userGradeKey);
		List<UserGradeOutputDto> output = null;
		if (StringUtils.isEmpty(cache)) {
			output = userGradeMapper.getUserGradeList();
			redisService.set(userGradeKey, JsonUtils.toJson(output), 10000);
		} else {
			output = JsonUtils.toList(cache, UserGradeOutputDto.class);
		}
		return output;
	}

}
