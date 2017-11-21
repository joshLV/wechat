package com.bingosoft.core.mysql.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bingosoft.core.mysql.service.IGoodsCollectionService;
import com.bingosoft.models.dto.GoodsCollectionOutputDto;
import com.bingosoft.models.dto.GoodsIdCollectionOutputDto;
import com.bingosoft.persist.mysql.dao.IGoodsCollectionMapper;

@Service
public class GoodsCollectionServiceImpl implements IGoodsCollectionService {

	@Autowired
	IGoodsCollectionMapper goodsCollectionMapper;

	@Override
	public void addGoodsCollection(String userId, int goodsId, int categoryId) {
		// TODO Auto-generated method stub
		if (goodsCollectionMapper.isGoodsCollection(userId, goodsId).getRow() <= 0)
			goodsCollectionMapper.addGoodsCollection(userId, goodsId, categoryId);

	}

	@Override
	public void delGoodsCollection(String userId, int goodsId) {
		// TODO Auto-generated method stub
		goodsCollectionMapper.delGoodsCollection(userId, goodsId);
	}

	@Override
	public List<GoodsCollectionOutputDto> getGoodsCollection(String userId, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int pageEnd = pageIndex * pageSize;
		return goodsCollectionMapper.getGoodsCollection(userId, pageEnd - pageSize, pageEnd);
	}

	@Override
	public int isGoodsCollection(String userId, int goodsId) {
		// TODO Auto-generated method stub
		return goodsCollectionMapper.isGoodsCollection(userId, goodsId).getRow();
	}

	@Override
	public List<GoodsIdCollectionOutputDto> getCollectionGoodsId(String userId, int categoryId) {
		// TODO Auto-generated method stub
		return goodsCollectionMapper.getCollectionGoodsId(userId, categoryId);
	}

}
