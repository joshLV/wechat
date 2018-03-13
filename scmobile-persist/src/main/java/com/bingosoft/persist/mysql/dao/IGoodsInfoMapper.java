package com.bingosoft.persist.mysql.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.bingosoft.models.dto.GoodsCategoryIdOutputDto;
import com.bingosoft.models.dto.GoodsCategoryInfoOutputDto;
import com.bingosoft.models.dto.GoodsInfoOutputDto;
import com.bingosoft.models.dto.GoodsItemInfoAndProdOutputDto;
import com.bingosoft.models.dto.GoodsItemInfoOutputDto;

public interface IGoodsInfoMapper {
	@Select("select category_id categoryId,category_name categoryName,category_desc categoryDesc,show_style showStyle,image_url imageUrl,sale_count saleCount from goods_category where category_id = #{categoryId} and category_status = 1")
    public GoodsCategoryInfoOutputDto getGoodsCategory(int categoryId);
	
	@Select("select goods_id goodsId,goods_name goodsName,goods_price goodsPrice,is_recommend isRecommend,goods_desc goodsDesc,goods_note goodsNote,effective_time effectiveTime from goods_info where category_id = #{categoryId} and goods_status = 1 order by sort_order")
	public List<GoodsInfoOutputDto> getGoodsInfoByCategoryId(int categoryId);
	
	@Select("select category_id categoryId,goods_name goodsName,goods_title goodsTitle,goods_image goodsImage,goods_desc goodsDesc,goods_note goodsNote,keywords,goods_price goodsPrice from goods_info where goods_id = #{goodsId} and goods_status = 1")
	public GoodsItemInfoOutputDto getGoodsInfo(long goodsId);
	
	@Select("select category_id categoryId,goods_name goodsName,goods_title goodsTitle,goods_image goodsImage,goods_desc goodsDesc,goods_note goodsNote,keywords,goods_price goodsPrice,fee_code feeCode,effective_time effectiveTime,tags from goods_info where goods_id = #{goodsId} and goods_status = 1")
	public GoodsItemInfoAndProdOutputDto getGoodsInfoAndProd(long goodsId);
	
	@Select("select category_id categoryId from goods_info where goods_id = #{goodsId} and goods_status = 1")
	public GoodsCategoryIdOutputDto getCategoryId(int goodsId);
}
