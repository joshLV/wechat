package com.bingosoft.persist.mycat.dao;

import java.util.Date;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bingosoft.models.dto.GoodsCountOutputDto;

public interface IOrderNewsByPartMapper {
	@Select("SELECT COUNT(1) as row FROM order_news WHERE part_id=#{partId} and user_id=#{userId}")
	public GoodsCountOutputDto isHasNewsRecord(@Param("userId") String userId, @Param("partId") int partId);
	
	@Select("SELECT news as row FROM order_news WHERE PART_ID=#{partId} and user_id=#{userId}")
	public GoodsCountOutputDto getNewsRecord(@Param("userId") String userId, @Param("partId") int partId);

	@Insert("insert into order_news (user_id,news,update_time,part_id) values(#{userId},1,#{updateTime},#{partId})")
	public void addOrderNews(@Param("userId") String userId, @Param("partId") int partId,
			@Param("updateTime") Date updateTime);

	@Insert("update order_news set news=news+1,update_time=#{updateTime} where part_id=#{partId} and user_id=#{userId}")
	public void updateOrderNewsIncr(@Param("userId") String userId, @Param("partId") int partId,
			@Param("updateTime") Date updateTime);
	
	@Insert("update order_news set news=0,update_time=#{updateTime} where part_id=#{partId} and user_id=#{userId}")
	public void updateOrderNewsdscr(@Param("userId") String userId, @Param("partId") int partId,
			@Param("updateTime") Date updateTime);
}
