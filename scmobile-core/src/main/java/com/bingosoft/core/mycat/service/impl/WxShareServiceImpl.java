package com.bingosoft.core.mycat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bingosoft.core.mycat.service.IWxShareService;
import com.bingosoft.models.dto.GameShareRecordInputDto;
import com.bingosoft.models.prefix.RedisKeyPrefix;
import com.bingosoft.persist.mycat.dao.IWxShareByPartMapper;
import com.bingosoft.persist.redis.dao.IRedisService;
import com.bingosoft.utils.DateUtils;
import com.bingosoft.utils.LuaScriptUtils;

@Service
public class WxShareServiceImpl implements IWxShareService {

	@Autowired
	IRedisService redisService;

	@Autowired
	IWxShareByPartMapper wxShareMapper;

	@Override
	public String wxShare(GameShareRecordInputDto input) {
		// TODO Auto-generated method stub
		String key = String.format(RedisKeyPrefix.GameShare_Prefix, input.getSharePhone());
		String chanceKey = String.format(RedisKeyPrefix.Chance_Prefix, input.getSharePhone());
		String orderKey = String.format(RedisKeyPrefix.Order_Game_Share_Prefix, input.getSharePhone(),
				input.getOrderId());
		// String time = String.valueOf(DateUtils.getTimeInMillis());
		String[] param = { orderKey, chanceKey };
		String shareFlag = redisService.evalScript(LuaScriptUtils.GAME_SHARE_LUA, 2, param);
		if (!shareFlag.equals("0")) {
			try {
				wxShareMapper.AddShareLog(input.getShareId(), input.getPartId(), input.getRuleId(),
						input.getShareModuleId(), input.getSharePage(), input.getSharePhone(), input.getShareStatus(),
						input.getShareNote());
			} catch (Exception ex) {

			}
		}
		return shareFlag;
	}

}
