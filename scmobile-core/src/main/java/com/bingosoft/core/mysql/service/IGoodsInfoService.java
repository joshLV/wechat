package com.bingosoft.core.mysql.service;

import java.util.List;

import com.bingosoft.models.dto.GoodsCategoryDto;
import com.bingosoft.models.dto.GoodsCategoryIdOutputDto;
import com.bingosoft.models.dto.GoodsCategoryInfoOutputDto;
import com.bingosoft.models.dto.GoodsCategoryOutputDto;
import com.bingosoft.models.dto.GoodsInfoOutputDto;
import com.bingosoft.models.dto.GoodsInfosOutputDto;
import com.bingosoft.models.dto.GoodsItemInfoAndProdOutputDto;
import com.bingosoft.models.dto.GoodsItemInfoOutputDto;
import com.bingosoft.models.dto.HotCategoryGoodsOutputDto;
import com.bingosoft.models.dto.HotGoodsCategoryOutputDto;
import com.bingosoft.models.dto.HotGoodsInfoOutputDto;
import com.bingosoft.models.dto.HotGoodsOutputDto;

public interface IGoodsInfoService {
	public GoodsCategoryInfoOutputDto getGoodsCategory(int categoryId);

	public GoodsItemInfoOutputDto getGoodsInfo(long goodsId);

	public List<GoodsCategoryOutputDto> getGoodsCategoryList(int categoryId);

	public List<HotGoodsCategoryOutputDto> getHotGoodsCategory(int categoryId, int records);

	public GoodsCategoryIdOutputDto getCategoryId(int goodsId);
	
	public List<HotCategoryGoodsOutputDto> getHotCategoryGoodsSaleCount();
	
	public GoodsItemInfoAndProdOutputDto getGoodsInfoAndProd(long goodsId);
	
	/**
	 * 获取火热单品
	 * @param records
	 * @return
	 */
	public List<HotGoodsOutputDto> getHotGoods(int records);
	
	/**
	 * 获取分类列表
	 * @param records
	 * @return
	 */
	public List<GoodsCategoryDto> getPackageList(int records);
	
	/**
	 * 获取单品信息
	 * @param goodsId
	 * @return
	 */
	public GoodsInfosOutputDto getGoodsById(long goodsId);
	
	/**
	 * 
	 * @return
	 */
	public List<HotGoodsInfoOutputDto> getHotgoods();
}
