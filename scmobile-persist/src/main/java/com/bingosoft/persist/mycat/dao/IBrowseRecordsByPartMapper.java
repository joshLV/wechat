package com.bingosoft.persist.mycat.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bingosoft.models.dto.BrowseCountOutput;
import com.bingosoft.models.dto.BrowseRecordsOutputDto;
import com.bingosoft.models.dto.DataRows;

public interface IBrowseRecordsByPartMapper {
	@Select("select b.goods_id as goodsId,b.category_id as categoryId,b.goods_name as goodsName,b.goods_image as goodsImage,b.goods_price as goodsPrice,b.update_time as updateTime from goods_browse b  where b.part_id=#{partId} and b.user_id = #{userId} order by b.update_time desc limit #{pageStart},#{pageEnd}")
	public List<BrowseRecordsOutputDto> getBrowseRecords(@Param("userId") String userId,
			@Param("pageStart") int pageStart, @Param("pageEnd") int pageEnd,@Param("partId") int partId);

	@Select("select count(1) as dataRow from goods_browse where part_id=#{partId} and user_id=#{userId} and goods_id=#{goodsId}")
	public DataRows isExistsBrowse(@Param("userId") String userId, @Param("goodsId") long goodsId,@Param("partId")int partId);

	@Insert("insert into goods_browse (user_id,goods_id,update_time,category_id,goods_name,goods_image,goods_price,part_id) values(#{userId},#{goodsId},#{updateTime},#{categoryId},#{goodsName},#{goodsImage},#{goodsPrice},#{partId})")
	public void AddBrowse(@Param("userId") String userId, @Param("goodsId") long goodsId,
			@Param("updateTime") Date updateTime,@Param("categoryId") long categoryId,
			@Param("goodsName") String goods_name,@Param("goodsImage")String goodsImage,@Param("goodsPrice") double goodsprice,@Param("partId")int partId);

	@Update("update goods_browse set update_time=#{updateTime} where part_id=#{partId} and user_id=#{userId} and goods_id=#{goodsId}")
	public void updateBrowse(@Param("userId") String userId, @Param("goodsId") long goodsId,
			@Param("updateTime") Date updateTime,@Param("partId") int partId);

	@Select(" select count(1)  browseCount,(select count(1) from goods_collection where part_id=#{partId} and user_id=#{userId}) colCount from goods_browse where part_id=#{partId} and user_id=#{userId}")
	public BrowseCountOutput getBrowseCount(@Param("userId") String userId,@Param("partId") int partId);
}
