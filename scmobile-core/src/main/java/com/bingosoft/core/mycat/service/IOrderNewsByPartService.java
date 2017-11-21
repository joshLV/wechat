package com.bingosoft.core.mycat.service;

import com.bingosoft.models.dto.GoodsCountOutputDto;

public interface IOrderNewsByPartService {
   public void addOrderNews(String userId,int partId);
   public void updateOrderNews(String userId,int partId);
   public GoodsCountOutputDto getOrderNews(String userId,int partId);
}
