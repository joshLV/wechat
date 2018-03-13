package com.bingosoft.core.mycat.service;

import java.util.List;

import com.bingosoft.models.dto.OrderDetailOutputDto;
import com.bingosoft.models.dto.OrderInfo;
import com.bingosoft.models.dto.OrderItemListOutputDto;
import com.bingosoft.models.dto.OrderNewItemOutputDto;
import com.bingosoft.models.dto.OrderNewListOutputDto;
import com.bingosoft.models.dto.OrderShortAddModeOutputDto;

public interface IOrderByPartService {
	public List<OrderNewListOutputDto> getOrderNewsList(String userId,int pageIndex,int pageSize,int partId);
	
	public OrderDetailOutputDto getOrderDetail(long orderId,int partId);

	public OrderNewItemOutputDto getNewsItem(long itemId,int partId);
	
	public OrderShortAddModeOutputDto orderGoodsInto(OrderInfo order);
	
	public OrderShortAddModeOutputDto MarkActHandleInto(OrderInfo order);
	
	public void TccFailOrder(OrderInfo order);
	
	public void timeoutOrder(OrderInfo order,String postTime,String timeoutTime);
	
	public List<OrderItemListOutputDto> getOrderItemList(String userId, int pageIndex, int pageSize,int partId);
}
