package com.bingosoft.persist.mycat.dao;

import java.util.List;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bingosoft.models.dto.GoodsCollectionOutputDto;
import com.bingosoft.models.dto.GoodsCountOutputDto;
import com.bingosoft.models.dto.GoodsIdCollectionOutputDto;

public interface IGoodsCollectionByPartMapper {
	@Insert("insert into goods_collection(user_id,goods_id,category_id,create_time,goods_name,goods_price,goods_image,part_id)values(#{userId},#{goodsId},#{categoryId},now(),#{goodsName},#{goodsPrice},#{goodsImage},#{partId})")
	public void addGoodsCollection(@Param("userId") String userId, @Param("goodsId") long goodsId,@Param("categoryId") int categoryId,
			@Param("goodsName")String goods_name,@Param("goodsPrice")double goods_price,@Param("goodsImage")String goods_image,@Param("partId")int partId);

	@Delete("delete from goods_collection where part_id=#{partId} and user_id = #{userId} and goods_id = #{goodsId}")
	public void delGoodsCollection(@Param("userId") String userId, @Param("goodsId") long goodsId,@Param("partId")int partId);

	@Select("select b.goods_id as goodsId,b.goods_name as goodsName,b.goods_price as goodsPrice,b.goods_image as goodsImage,b.category_id as categoryId from goods_collection b  where b.part_id=#{partId} and b.user_id = #{userId} limit #{pageStart},#{pageEnd}")
	public List<GoodsCollectionOutputDto> getGoodsCollection(@Param("userId")String userId,@Param("pageStart")  int pageStart,@Param("pageEnd")  int pageEnd,@Param("partId")int partId);
	
	@Select("select count(1) as row from goods_collection where part_id=#{partId} and user_id=#{userId} and goods_id=#{goodsId}")
	public GoodsCountOutputDto isGoodsCollection(@Param("userId") String userId, @Param("goodsId") long goodsId,@Param("partId")int partId);
	
	@Select("select goods_id as goodsId from goods_collection where part_id=#{partId} and user_id=#{userId} and category_id=#{categoryId}")
	public List<GoodsIdCollectionOutputDto> getCollectionGoodsId(@Param("userId") String userId, @Param("categoryId") int categoryId,@Param("partId")int partId);
}
