package com.bingosoft.persist.mysql.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.bingosoft.models.dto.GoodsCategoryOutputDto;
import com.bingosoft.models.dto.HotCategoryGoodsOutputDto;
import com.bingosoft.models.dto.HotGoodsCategoryOutputDto;

public interface IGoodsMapper {

	/**
	 * 获取流量套餐
	 * @param categoryId
	 * @return
	 */
	@Select("select category_id categoryId,category_name categoryName,category_desc categoryDesc,show_style showStyle,image_url imageUrl from goods_category where parent_id = #{categoryId} and category_status = 1 order by sort_order")
	public List<GoodsCategoryOutputDto> getGoodsCategoryList(int categoryId);
	
	/**
	 * 
	 * @param categoryId
	 * @return
	 */
	@Select("select category_id categoryId,category_name categoryName,category_desc categoryDesc,show_style showStyle,image_url imageUrl,sale_count saleCount\r\n" + 
			" from goods_category where parent_id = #{categoryId} and category_status = 1 and is_hot = 1 order by sort_order limit 0,#{records}\r\n")
	public List<HotGoodsCategoryOutputDto> getHotGoodsCategory(@Param("categoryId")int categoryId,@Param("records")int records);
	
	@Select("select category_id categoryId,sale_count saleCount from goods_category where parent_id=1 and category_status=1 and is_hot=1")
	public List<HotCategoryGoodsOutputDto> getHotCategoryGoodsSaleCount();
	
	
}
