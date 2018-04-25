package com.bingosoft.persist.mysql.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.bingosoft.models.dto.GoodsCategoryDto;
import com.bingosoft.models.dto.GoodsCategoryIdOutputDto;
import com.bingosoft.models.dto.GoodsCategoryInfoOutputDto;
import com.bingosoft.models.dto.GoodsInfoOutputDto;
import com.bingosoft.models.dto.GoodsInfosOutputDto;
import com.bingosoft.models.dto.GoodsItemInfoAndProdOutputDto;
import com.bingosoft.models.dto.GoodsItemInfoOutputDto;
import com.bingosoft.models.dto.HotGoodsInfoOutputDto;

public interface IGoodsInfoMapper {
	@Select("select category_id categoryId,category_name categoryName,category_desc categoryDesc,show_style showStyle,image_url imageUrl,(sale_count+sale_init) saleCount,head_pic headPic from goods_category where category_id = #{categoryId} and category_status = 1  and shelf_time<=NOW() and down_time>=NOW()")
    public GoodsCategoryInfoOutputDto getGoodsCategory(int categoryId);
	
	@Select("select goods_id goodsId,goods_name goodsName,goods_price goodsPrice,is_recommend isRecommend,goods_desc goodsDesc,goods_note goodsNote,effective_time effectiveTime,goods_content goodsContent,(sale_count+sale_init) saleCount,goods_alias goodsAlias,goods_title goodsTitle from goods_info where category_id = #{categoryId} and goods_status = 1 and shelf_time<=NOW() and down_time>=NOW() order by sort_order")
	public List<GoodsInfoOutputDto> getGoodsInfoByCategoryId(int categoryId);
	
	@Select("select category_id categoryId,goods_name goodsName,goods_title goodsTitle,goods_image goodsImage,goods_desc goodsDesc,goods_note goodsNote,keywords,goods_price goodsPrice from goods_info where goods_id = #{goodsId} and goods_status = 1")
	public GoodsItemInfoOutputDto getGoodsInfo(long goodsId);
	
	@Select("select info.category_id categoryId,info.goods_name goodsName,info.goods_title goodsTitle,info.goods_image goodsImage,info.goods_desc goodsDesc,info.goods_note goodsNote,info.keywords,info.goods_price goodsPrice,info.fee_code feeCode,info.effective_time effectiveTime,info.tags,cate.category_name cateName,cate.image_url cateImg from goods_info info,goods_category cate where info.category_id=cate.category_id and info.goods_id = #{goodsId} and info.goods_status = 1")
	public GoodsItemInfoAndProdOutputDto getGoodsInfoAndProd(long goodsId);
	
	@Select("select category_id categoryId from goods_info where goods_id = #{goodsId} and goods_status = 1")
	public GoodsCategoryIdOutputDto getCategoryId(int goodsId);
	
	@Select("SELECT info.category_id cateId,info.category_name cateName,info.image_url icon,info.category_desc cateDesc,(info.sale_count+info.sale_init) saleCount,info.link_url linkUrl FROM goods_category info where info.parent_id=#{cateId} and info.category_status=1 and info.shelf_time<=NOW() and info.down_time>=NOW() order by sort_order")
	public List<GoodsCategoryDto> getPackageList(int cateId);
	
	
	@Select("SELECT info.goods_id goodsId,info.head_pic headPic,info.goods_name goodsName,info.goods_title goodsTitle,info.goods_desc goodsDesc,info.goods_price goodsPrice,(info.sale_count+info.sale_init) saleCount,info.goods_content goodsContent,cate.category_id cateId,cate.category_name cateName,cate.head_pic cateHeadPic,cate.category_desc cateDesc,info.goods_note goodsNote,info.effective_time effTime,goods_alias goodsAlias FROM goods_info info LEFT JOIN goods_category cate ON info.category_id=cate.category_id where info.goods_id=#{goodsId} and info.goods_status=1 and info.shelf_time<=NOW() and info.down_time>=NOW()")
	public GoodsInfosOutputDto getGoodsInfoById(long goodsId);
	
	@Select("SELECT goods_id goodsId,goods_name goodsName,goods_image goodsImg,link_url linkUrl,sale_count saleCount from hot_goods")
	public List<HotGoodsInfoOutputDto> getHotgoods();
}
