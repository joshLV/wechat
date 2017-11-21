package com.bingosoft.persist.mysql.dao;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bingosoft.models.dto.BrowseCountOutput;
import com.bingosoft.models.dto.BrowseRecordsOutputDto;
import com.bingosoft.models.dto.DataRows;

/**
 * 用户浏览记录
 * 
 * @author Administrator
 *
 */
public interface IBrowseRecordsMapper {
	@Select("select b.goods_id goodsId,b.category_id categoryId,b.goods_name goodsName,b.goods_image goodsImage,b.goods_price goodsPrice,a.update_time updateTime from goods_browse a inner join goods_info b on a.goods_id = b.goods_id where a.user_id = #{userId} order by a.update_time desc limit #{pageStart},#{pageEnd}")
	public List<BrowseRecordsOutputDto> getBrowseRecords(@Param("userId") String userId,
			@Param("pageStart") int pageStart, @Param("pageEnd") int pageEnd);

	@Select("select count(1) dataRow from goods_browse where user_id=#{userId} and goods_id=#{goodsId}")
	public DataRows isExistsBrowse(@Param("userId") String userId, @Param("goodsId") long goodsId);

	@Insert("insert into goods_browse (user_id,goods_id,update_time) values(#{userId},#{goodsId},#{updateTime})")
	public void AddBrowse(@Param("userId") String userId, @Param("goodsId") long goodsId,
			@Param("updateTime") Date updateTime);

	@Update("update goods_browse set update_time=#{updateTime} where user_id=#{userId} and goods_id=#{goodsId}")
	public void updateBrowse(@Param("userId") String userId, @Param("goodsId") long goodsId,
			@Param("updateTime") Date updateTime);

	@Select(" select (SELECT count(1) FROM goods_browse where user_id=#{userId}) browseCount,(select count(1) from goods_collection where user_id=#{userId}) colCount from DUAL")
	public BrowseCountOutput getBrowseCount(@Param("userId") String userId);
}
