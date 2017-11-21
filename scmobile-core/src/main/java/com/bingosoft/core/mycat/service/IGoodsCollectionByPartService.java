package com.bingosoft.core.mycat.service;

import java.util.List;

import com.bingosoft.models.dto.GoodsCollectionOutputDto;
import com.bingosoft.models.dto.GoodsIdCollectionOutputDto;
import com.bingosoft.models.input.dto.GoodsCollectionInputDto;

public interface IGoodsCollectionByPartService {
	public void addGoodsCollection(GoodsCollectionInputDto input);

	public void delGoodsCollection(String userId, long goodsId,int partId);

	public List<GoodsCollectionOutputDto> getGoodsCollection(String userId, int pageIndex, int pageSize,int partId);

	public int isGoodsCollection(String userId, long goodsId,int partId);

	public List<GoodsIdCollectionOutputDto> getCollectionGoodsId(String userId, int categoryId,int partId);
}
