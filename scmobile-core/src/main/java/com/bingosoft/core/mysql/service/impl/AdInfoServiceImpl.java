package com.bingosoft.core.mysql.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bingosoft.core.mysql.service.IAdInfoService;
import com.bingosoft.models.dto.AdInfoOutputDto;
import com.bingosoft.models.prefix.RedisKeyPrefix;
import com.bingosoft.persist.mysql.dao.IAdInfoMapper;
import com.bingosoft.persist.redis.dao.IRedisService;
import com.bingosoft.utils.serialize.JsonUtils;

@Service
public class AdInfoServiceImpl implements IAdInfoService {

	@Autowired
	IAdInfoMapper adInfoMapper;

	@Autowired
	IRedisService redisService;

	@Override
	public List<AdInfoOutputDto> getAdList(int postionId) {
		// TODO Auto-generated method stub
		String adKey = RedisKeyPrefix.AD_LIST_PREFIX;
		String cache = redisService.get(adKey);
		List<AdInfoOutputDto> output = null;
		if (StringUtils.isEmpty(cache)) {
			output = adInfoMapper.getAdList(postionId);
			redisService.set(adKey, JsonUtils.toJson(output), 10000);
		} else {
			output = JsonUtils.toList(cache, AdInfoOutputDto.class);
		}
		return adInfoMapper.getAdList(postionId);
	}

}
