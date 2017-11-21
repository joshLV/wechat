package com.bingosoft.core.mysql.service.impl;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bingosoft.core.mysql.service.IOrderNewsService;
import com.bingosoft.models.dto.GoodsCountOutputDto;
import com.bingosoft.persist.mysql.dao.IOrderNewsMapper;

@Service
public class OrderNewsServiceImpl implements IOrderNewsService {

	@Autowired
	IOrderNewsMapper orderNewsMapper;

	@Override
	public void addOrderNews(String userId, int partId) {
		try {
			GoodsCountOutputDto dto = orderNewsMapper.isHasNewsRecord(userId, partId);
			if (dto != null && dto.getRow() >= 1) {
				orderNewsMapper.updateOrderNewsIncr(userId, partId, new Date());
			} else {
				orderNewsMapper.addOrderNews(userId, partId, new Date());
			}
		} catch (Exception ex) {
			System.out.println(ex.getMessage()+ex.getStackTrace());
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
