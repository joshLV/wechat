package com.bingosoft.core.mysql.service;

import java.util.List;

import com.bingosoft.models.dto.OrderDetailOutputDto;
import com.bingosoft.models.dto.OrderInfo;
import com.bingosoft.models.dto.OrderItemListOutputDto;
import com.bingosoft.models.dto.OrderMycatOutputDto;
import com.bingosoft.models.dto.OrderNewItemOutputDto;
import com.bingosoft.models.dto.OrderNewListOutputDto;

public interface IOrderService {
	public OrderDetailOutputDto getOrderDetail(long orderId);

	public void orderGoods(OrderInfo order);

	public void orderGoodsInto(OrderInfo order);

	public List<OrderItemListOutputDto> getOrderItemList(String userId, int pageIndex, int pageSize);

	public void getOrderInRocketmq(String mqMsg);

	public List<OrderNewListOutputDto> getOrderNewsList(String userId, int pageIndex, int pageSize);

	public OrderNewItemOutputDto getNewsItem(long itemId);

	public OrderMycatOutputDto getMycatTest(int part);

}
