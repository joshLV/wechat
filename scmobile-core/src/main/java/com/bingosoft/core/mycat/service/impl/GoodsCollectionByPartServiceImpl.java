package com.bingosoft.core.mycat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bingosoft.core.mycat.service.IGoodsCollectionByPartService;
import com.bingosoft.models.dto.GoodsCollectionOutputDto;
import com.bingosoft.models.dto.GoodsCountOutputDto;
import com.bingosoft.models.dto.GoodsIdCollectionOutputDto;
import com.bingosoft.models.input.dto.GoodsCollectionInputDto;
import com.bingosoft.persist.mycat.dao.IGoodsCollectionByPartMapper;

@Service
public class GoodsCollectionByPartServiceImpl implements IGoodsCollectionByPartService {

	@Autowired
	IGoodsCollectionByPartMapper goodsCollectionMapper;

	@Override
	public void addGoodsCollection(GoodsCollectionInputDto input) {
		if (goodsCollectionMapper.isGoodsCollection(input.getUserId(), input.getGoodsId(), input.getPartId())
				.getRow() <= 0)
			goodsCollectionMapper.addGoodsCollection(input.getUserId(), input.getGoodsId(), input.getCategoryId(),
					input.getGoodsName(), input.getGoodsPrice(), input.getGoodsImage(), input.getPartId());
		// TODO Auto-generated method stub

	}

	@Override
	public void delGoodsCollection(String userId, long goodsId, int partId) {
		// TODO Auto-generated method stub
		goodsCollectionMapper.delGoodsCollection(userId, goodsId, partId);

	}

	@Override
	public List<GoodsCollectionOutputDto> getGoodsCollection(String userId, int pageIndex, int pageSize, int partId) {
		// TODO Auto-generated method stub
		int pageEnd = pageIndex * pageSize;
		return goodsCollectionMapper.getGoodsCollection(userId, pageEnd - pageSize, pageEnd, partId);

	}

	@Override
	public int isGoodsCollection(String userId, long goodsId, int partId) {
		// TODO Auto-generated method stub
		GoodsCountOutputDto dto = goodsCollectionMapper.isGoodsCollection(userId, goodsId, partId);
		return dto.getRow();
	}

	@Override
	public List<GoodsIdCollectionOutputDto> getCollectionGoodsId(String userId, int categoryId, int partId) {
		// TODO Auto-generated method stub
		return goodsCollectionMapper.getCollectionGoodsId(userId, categoryId, partId);
	}

}
