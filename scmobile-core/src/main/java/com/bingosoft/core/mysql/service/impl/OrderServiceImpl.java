package com.bingosoft.core.mysql.service.impl;

import java.util.List;

import org.apache.rocketmq.client.exception.MQBrokerException;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.client.producer.SendStatus;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.exception.RemotingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bingosoft.common.rocketmq.BingoConsumer;
import com.bingosoft.common.rocketmq.BingoProducer;
import com.bingosoft.core.mysql.service.IOrderService;
import com.bingosoft.core.web.service.IWebRestService;
import com.bingosoft.models.dto.OrderDetailOutputDto;
import com.bingosoft.models.dto.OrderInfo;
import com.bingosoft.models.dto.OrderItem;
import com.bingosoft.models.dto.OrderItemListOutputDto;
import com.bingosoft.models.dto.OrderMycatOutputDto;
import com.bingosoft.models.dto.OrderNewItemOutputDto;
import com.bingosoft.models.dto.OrderNewListOutputDto;
import com.bingosoft.models.prefix.RedisKeyPrefix;
import com.bingosoft.models.rest.dto.OBFreeQryOutDataOutputDto;
import com.bingosoft.models.rest.dto.RestResponseOutputDto;
import com.bingosoft.models.rest.dto.ShortAddModeOutputDto;
import com.bingosoft.persist.mycat.dao.IOrderMycatTestMapper;
import com.bingosoft.persist.mysql.dao.IOrderMapper;
import com.bingosoft.persist.redis.dao.IRedisService;
import com.bingosoft.utils.DateUtils;
import com.bingosoft.utils.LuaScriptUtils;
import com.bingosoft.utils.serialize.JsonUtils;

@Service
public class OrderServiceImpl implements IOrderService {

	private final Logger logger = LoggerFactory.getLogger(OrderServiceImpl.class);

	@Autowired
	IOrderMycatTestMapper mycatMapper;

	@Autowired
	IOrderMapper orderMapper;

	@Autowired
	BingoProducer producer;

	@Autowired
	IRedisService redisService;

	@Autowired
	IWebRestService webRestService;

	@Override
	public OrderDetailOutputDto getOrderDetail(long orderId) {
		// TODO Auto-generated method stub
		return orderMapper.getOrderDetail(orderId);
	}

	@Override
	public void orderGoods(OrderInfo order) {
		// TODO Auto-generated method stub

		// BingoProducer producer=new BingoProducer();
		String orderInfo = JsonUtils.toJson(order);
		producer.sendMsg(orderInfo);

	}

	@Override
	public void getOrderInRocketmq(String mqMsg) {

		OrderInfo orderInfo = JsonUtils.toBean(mqMsg, OrderInfo.class);
		// TODO Auto-generated method stub
		orderMapper.addOrder(orderInfo.getOrderId(), orderInfo.getUserId(), orderInfo.getUserName(),
				orderInfo.getUserName(), orderInfo.getTotalAmout(), orderInfo.getPayId(), orderInfo.getPayFee(),
				orderInfo.getPayNote(), orderInfo.getOrderDesc(), orderInfo.getOrderStatus(), orderInfo.getChannelId(),
				orderInfo.getPartId());
		OrderItem item = orderInfo.getOrderItem().get(0);
		//if (item != null)
//			orderMapper.addOrderItem(item.getOrderId(), item.getGoodsId(), item.getGoodsName(), item.getGoodsImage(),
//					item.getGoodsCount(), item.getGoodsPrice(), item.getGoodsDesc(), item.getGoodsCount(),
//					item.getPayFee(), item.getOrderStatus(), item.getPartId(), item.getItemId());
	}

	@Override
	public List<OrderItemListOutputDto> getOrderItemList(String userId, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int pageEnd = pageIndex * pageSize;
		return orderMapper.getOrderItemList(userId, pageEnd - pageSize, pageEnd);
	}

	@Override
	public List<OrderNewListOutputDto> getOrderNewsList(String userId, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int pageEnd = pageIndex * pageSize;
		List<OrderNewListOutputDto> dto = orderMapper.getOrderNewsList(userId, pageEnd - pageSize, pageEnd);
		OrderNewListOutputDto item = null;
		for (int index = 0; index <= dto.size() - 1; index++) {
			item = dto.get(index);
			item.setTimeTitle(DateUtils.getDay2week(item.getCreateTime()));
		}
		return dto;
	}

	@Override
	public OrderNewItemOutputDto getNewsItem(long itemId) {
		// TODO Auto-generated method stub
		return orderMapper.getNewsItem(itemId);
	}

	@Override
	public OrderMycatOutputDto getMycatTest(int part) {
		// TODO Auto-generated method stub
		return mycatMapper.getMycatTest(part);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public void orderGoodsInto(OrderInfo orderInfo) {

		// TODO Auto-generated method stub
		try {
			orderMapper.addOrder(orderInfo.getOrderId(), orderInfo.getUserId(), orderInfo.getUserName(),
					orderInfo.getPhoneNo(), orderInfo.getTotalAmout(), orderInfo.getPayId(), orderInfo.getPayFee(),
					orderInfo.getPayNote(), orderInfo.getOrderDesc(), orderInfo.getOrderStatus(),
					orderInfo.getChannelId(), orderInfo.getPartId());
			OrderItem item = orderInfo.getOrderItem().get(0);
			if (item != null) {
				RestResponseOutputDto<ShortAddModeOutputDto> dto = webRestService.sShortAddMode(orderInfo.getPhoneNo(),
						item.getPROD_PRCID());
				orderMapper.addOrderItem(item.getOrderId(), item.getGoodsId(), item.getGoodsName(),
						item.getGoodsImage(), item.getGoodsCount(), item.getGoodsPrice(), item.getGoodsDesc(),
						item.getGoodsCount(), item.getPayFee(), item.getOrderStatus(), item.getPartId(),
						item.getItemId(), DateUtils.formatToNaturalDate(dto.getOutData().getEFF_DATE()),
						DateUtils.formatToNaturalDate(dto.getOutData().getEXP_DATE()));
				String key = String.format(RedisKeyPrefix.GoodsOrderCount_Prefix, item.getGoodsId());
				String cateKey = String.format(RedisKeyPrefix.CategoryOrderCount_Prefix, item.getCategoryId());
				String[] params = { key, cateKey };
				redisService.evalScript(LuaScriptUtils.CATE_INCR_BY_KEY, 2, params);
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage()+ex.getStackTrace());
		}

	}

}
