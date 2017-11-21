package com.bingosoft.core.mysql.service;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.bingosoft.models.dto.GoodsCollectionOutputDto;
import com.bingosoft.models.dto.GoodsIdCollectionOutputDto;

public interface IGoodsCollectionService {
	public void addGoodsCollection(String userId, int goodsId, int categoryId);

	public void delGoodsCollection(String userId, int goodsId);

	public List<GoodsCollectionOutputDto> getGoodsCollection(String userId, int pageIndex, int pageSize);

	public int isGoodsCollection(String userId, int goodsId);

	public List<GoodsIdCollectionOutputDto> getCollectionGoodsId(String userId, int categoryId);

}
