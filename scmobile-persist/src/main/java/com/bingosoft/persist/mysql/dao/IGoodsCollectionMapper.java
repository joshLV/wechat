package com.bingosoft.persist.mysql.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bingosoft.models.dto.GoodsCollectionOutputDto;
import com.bingosoft.models.dto.GoodsCountOutputDto;
import com.bingosoft.models.dto.GoodsIdCollectionOutputDto;

public interface IGoodsCollectionMapper {
	@Insert("insert into goods_collection(user_id,goods_id,category_id,create_time)values(#{userId},#{goodsId},#{categoryId},now())")
	public void addGoodsCollection(@Param("userId") String userId, @Param("goodsId") int goodsId,@Param("categoryId") int categoryId);

	@Delete("delete from goods_collection where user_id = #{userId} and goods_id = #{goodsId}")
	public void delGoodsCollection(@Param("userId") String userId, @Param("goodsId") int goodsId);

	@Select("select b.goods_id goodsId,b.goods_name goodsName,b.goods_price goodsPrice,b.goods_image goodsImage,a.category_id categoryId from goods_collection a inner join goods_info b on a.goods_id = b.goods_id where user_id = #{userId} limit #{pageStart},#{pageEnd}")
	public List<GoodsCollectionOutputDto> getGoodsCollection(@Param("userId")String userId,@Param("pageStart")  int pageStart,@Param("pageEnd")  int pageEnd);
	
	@Select("select count(1) row from goods_collection where user_id=#{userId} and goods_id=#{goodsId}")
	public GoodsCountOutputDto isGoodsCollection(@Param("userId") String userId, @Param("goodsId") int goodsId);
	
	@Select("select goods_id goodsId from goods_collection where user_id=#{userId} and category_id=#{categoryId}")
	public List<GoodsIdCollectionOutputDto> getCollectionGoodsId(@Param("userId") String userId, @Param("categoryId") int categoryId);
}
