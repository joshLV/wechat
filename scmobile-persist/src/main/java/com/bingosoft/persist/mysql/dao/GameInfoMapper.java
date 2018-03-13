package com.bingosoft.persist.mysql.dao;

import org.apache.ibatis.annotations.Select;

import com.bingosoft.models.dto.GameActivityRuleOutputDto;

public interface GameInfoMapper {
	@Select("SELECT rule_id ruleId,activity_time_begin gameStart,activity_time_end gameEnd,link_url gameUrl from game_activity_rule where activity_state=1 and (NOW() between activity_time_begin and activity_time_end)")
	public GameActivityRuleOutputDto getGameInfo();
}
