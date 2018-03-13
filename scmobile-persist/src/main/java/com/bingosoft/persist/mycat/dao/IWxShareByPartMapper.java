package com.bingosoft.persist.mycat.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;

public interface IWxShareByPartMapper {
	@Insert("insert into game_share_record (share_id,part_id,share_time,rule_id,share_module_id,share_page,share_phone,share_status,share_note) values(#{share_id},"
			+ "#{part_id},now(),#{rule_id},#{share_module_id},#{share_page},#{share_phone},#{share_status},#{share_note})")
	public void AddShareLog(@Param("share_id")long shareId,@Param("part_id")int partId,@Param("rule_id")int ruleId,@Param("share_module_id")int shareModuleId,
			@Param("share_page")String sharePage,@Param("share_phone")String sharePhone,@Param("share_status")int shareStatus,@Param("share_note")String shareNote);
}
