package com.bingosoft.core.mycat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bingosoft.core.mycat.service.IOrderByPartService;
import com.bingosoft.core.web.service.IWebRestService;
import com.bingosoft.models.dto.OrderDetailOutputDto;
import com.bingosoft.models.dto.OrderInfo;
import com.bingosoft.models.dto.OrderItem;
import com.bingosoft.models.dto.OrderItemListOutputDto;
import com.bingosoft.models.dto.OrderNewItemOutputDto;
import com.bingosoft.models.dto.OrderNewListOutputDto;
import com.bingosoft.models.prefix.RedisKeyPrefix;
import com.bingosoft.models.rest.dto.RestResponseOutputDto;
import com.bingosoft.models.rest.dto.ShortAddModeOutputDto;
import com.bingosoft.persist.mycat.dao.IOrderByPartMapper;
import com.bingosoft.persist.redis.dao.IRedisService;
import com.bingosoft.utils.DateUtils;
import com.bingosoft.utils.LuaScriptUtils;

@Service
public class OrderByPartServiceImpl implements IOrderByPartService {

	@Autowired
	IWebRestService webRestService;

	@Autowired
	IOrderByPartMapper orderMapper;

	@Autowired
	IRedisService redisService;

	@Override
	public List<OrderNewListOutputDto> getOrderNewsList(String userId, int pageIndex, int pageSize, int partId) {
		// TODO Auto-generated method stub
		int pageEnd = pageIndex * pageSize;
		List<OrderNewListOutputDto> dto = orderMapper.getOrderNewsList(userId, pageEnd - pageSize, pageEnd, partId);
		OrderNewListOutputDto item = null;
		for (int index = 0; index <= dto.size() - 1; index++) {
			item = dto.get(index);
			item.setTimeTitle(DateUtils.getDay2week(item.getCreateTime()));
		}
		return dto;
	}

	@Override
	public OrderDetailOutputDto getOrderDetail(long orderId, int partId) {
		// TODO Auto-generated method stub
		return orderMapper.getOrderDetail(orderId, partId);
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public int orderGoodsInto(OrderInfo orderInfo) {
		OrderItem item = orderInfo.getOrderItem().get(0);
		if (item != null) {
			RestResponseOutputDto<ShortAddModeOutputDto> dto = webRestService.sShortAddMode(orderInfo.getPhoneNo(),
					item.getPROD_PRCID());
			if (dto.getResMsg().contains("ok")) {
				orderMapper.addOrder(orderInfo.getOrderId(), orderInfo.getUserId(), orderInfo.getUserName(),
						orderInfo.getPhoneNo(), orderInfo.getTotalAmout(), orderInfo.getPayId(), orderInfo.getPayFee(),
						orderInfo.getPayNote(), orderInfo.getOrderDesc(), orderInfo.getOrderStatus(),
						orderInfo.getChannelId(), orderInfo.getPartId());

				orderMapper.addOrderItem(item.getOrderId(), item.getGoodsId(), item.getGoodsName(),
						item.getGoodsImage(), item.getGoodsCount(), item.getGoodsPrice(), item.getGoodsDesc(),
						item.getGoodsCount(), item.getPayFee(), item.getOrderStatus(), item.getPartId(),
						item.getItemId(), DateUtils.formatToNaturalDate(dto.getOutData().getEFF_DATE()),
						DateUtils.formatToNaturalDate(dto.getOutData().getEXP_DATE()));
				String key = String.format(RedisKeyPrefix.GoodsOrderCount_Prefix, item.getGoodsId());
				String cateKey = String.format(RedisKeyPrefix.CategoryOrderCount_Prefix, item.getCategoryId());
				String[] params = { key, cateKey };
				redisService.evalScript(LuaScriptUtils.CATE_INCR_BY_KEY, 2, params);
				return 1;
			} else {
				return 0;
			}
		}
		return 0;

	}

	@Override
	public OrderNewItemOutputDto getNewsItem(long itemId, int partId) {
		// TODO Auto-generated method stub
		return orderMapper.getNewsItem(itemId, partId);
	}

	@Override
	public List<OrderItemListOutputDto> getOrderItemList(String userId, int pageIndex, int pageSize, int partId) {
		// TODO Auto-generated method stub
		int pageEnd = pageIndex * pageSize;
		return orderMapper.getOrderItemList(userId, pageEnd - pageSize, pageEnd, partId);
	}

}
