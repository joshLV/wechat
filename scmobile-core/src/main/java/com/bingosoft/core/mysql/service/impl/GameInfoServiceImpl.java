package com.bingosoft.core.mysql.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bingosoft.core.mysql.service.IGameInfoService;
import com.bingosoft.models.dto.GameActivityRuleOutputDto;
import com.bingosoft.models.dto.GoodsCategoryInfoOutputDto;
import com.bingosoft.models.dto.GoodsInfoOutputDto;
import com.bingosoft.models.prefix.RedisKeyPrefix;
import com.bingosoft.persist.mysql.dao.GameInfoMapper;
import com.bingosoft.persist.redis.dao.IRedisService;
import com.bingosoft.utils.DateFormatUtils;
import com.bingosoft.utils.DateUtils;
import com.bingosoft.utils.serialize.JsonUtils;

@Service
@CacheConfig(cacheNames = "city")
public class GameInfoServiceImpl implements IGameInfoService {

	@Autowired
	GameInfoMapper gameInfoMapper;

	@Autowired
	IRedisService redisService;

	@Override
	@Cacheable(value="game_rule")
	public String getLinkUrl() {
		// TODO Auto-generated method stub
		String ruleKey = String.format(RedisKeyPrefix.GameRule_Prefix);
		String cache = redisService.get(ruleKey);
		GameActivityRuleOutputDto dto = null;
		if (StringUtils.isEmpty(cache)) {
			dto = gameInfoMapper.getGameInfo();

			redisService.set(ruleKey, JsonUtils.toJson(dto), 10000);
		} else {
			dto = JsonUtils.toBean(cache, GameActivityRuleOutputDto.class);
		}
		if (dto != null) {
			if (DateFormatUtils.getTimeToEnd(DateUtils.format(dto.getGameEnd(), "yyyy-MM-dd HH:mm:ss")) >= 1
					&& DateFormatUtils.getTimeToEnd(DateUtils.format(dto.getGameStart(), "yyyy-MM-dd HH:mm:ss")) <= 0) {
				return dto.getGameUrl();
			}

		}
		return "";
	}

}
