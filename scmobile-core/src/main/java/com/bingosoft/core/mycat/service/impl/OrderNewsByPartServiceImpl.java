package com.bingosoft.core.mycat.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bingosoft.core.mycat.service.IOrderNewsByPartService;
import com.bingosoft.models.dto.GoodsCountOutputDto;
import com.bingosoft.persist.mycat.dao.IOrderNewsByPartMapper;

@Service
public class OrderNewsByPartServiceImpl implements IOrderNewsByPartService{

	@Autowired
	IOrderNewsByPartMapper orderNewsMapper;
	
	@Override
	public void addOrderNews(String userId, int partId) {
		GoodsCountOutputDto dto=orderNewsMapper.isHasNewsRecord(userId, partId);
		if(dto!=null&&dto.getRow()>=1)
		{
			orderNewsMapper.updateOrderNewsIncr(userId, partId, new Date());
		}else
		{
			orderNewsMapper.addOrderNews(userId, partId, new Date());
		}
		
	}

	@Override
	public void updateOrderNews(String userId, int partId) {
		// TODO Auto-generated method stub
		orderNewsMapper.updateOrderNewsdscr(userId, partId, new Date());
	}

	@Override
	public GoodsCountOutputDto getOrderNews(String userId, int partId) {
		// TODO Auto-generated method stub
		return orderNewsMapper.getNewsRecord(userId, partId);
	}

}
