package com.bingosoft.core.mysql.service;

import java.util.List;

import com.bingosoft.models.dto.GoodsCategoryIdOutputDto;
import com.bingosoft.models.dto.GoodsCategoryInfoOutputDto;
import com.bingosoft.models.dto.GoodsCategoryOutputDto;
import com.bingosoft.models.dto.GoodsInfoOutputDto;
import com.bingosoft.models.dto.GoodsItemInfoAndProdOutputDto;
import com.bingosoft.models.dto.GoodsItemInfoOutputDto;
import com.bingosoft.models.dto.HotCategoryGoodsOutputDto;
import com.bingosoft.models.dto.HotGoodsCategoryOutputDto;

public interface IGoodsInfoService {
	public GoodsCategoryInfoOutputDto getGoodsCategory(int categoryId);

	public GoodsItemInfoOutputDto getGoodsInfo(long goodsId);

	public List<GoodsCategoryOutputDto> getGoodsCategoryList(int categoryId);

	public List<HotGoodsCategoryOutputDto> getHotGoodsCategory(int categoryId, int records);

	public GoodsCategoryIdOutputDto getCategoryId(int goodsId);
	
	public List<HotCategoryGoodsOutputDto> getHotCategoryGoodsSaleCount();
	
	public GoodsItemInfoAndProdOutputDto getGoodsInfoAndProd(long goodsId);
}
