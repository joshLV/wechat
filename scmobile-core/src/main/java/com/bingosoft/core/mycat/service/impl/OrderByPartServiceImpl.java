package com.bingosoft.core.mycat.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bingosoft.core.mycat.service.IOrderByPartService;
import com.bingosoft.core.mysql.service.IGameInfoService;
import com.bingosoft.core.web.service.IWebRestService;
import com.bingosoft.models.dto.OrderDetailOutputDto;
import com.bingosoft.models.dto.OrderInfo;
import com.bingosoft.models.dto.OrderItem;
import com.bingosoft.models.dto.OrderItemListOutputDto;
import com.bingosoft.models.dto.OrderNewItemOutputDto;
import com.bingosoft.models.dto.OrderNewListOutputDto;
import com.bingosoft.models.dto.OrderShortAddModeOutputDto;
import com.bingosoft.models.prefix.RedisKeyPrefix;
import com.bingosoft.models.rest.dto.MarkActHandleOutputDto;
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

	@Autowired
	IGameInfoService gameInfoService;

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
	public OrderShortAddModeOutputDto orderGoodsInto(OrderInfo orderInfo) {

		OrderItem item = orderInfo.getOrderItem().get(0);
		OrderShortAddModeOutputDto orderDto = new OrderShortAddModeOutputDto();
		if (item != null) {
			RestResponseOutputDto<ShortAddModeOutputDto> dto = webRestService.sShortAddMode(orderInfo.getPhoneNo(),
					item.getPROD_PRCID());
			if (dto != null && dto.getResMsg().contains("ok")) {
				orderMapper.addOrder(orderInfo.getOrderId(), orderInfo.getUserId(), orderInfo.getUserName(),
						orderInfo.getPhoneNo(), orderInfo.getTotalAmout(), orderInfo.getPayId(), orderInfo.getPayFee(),
						orderInfo.getPayNote(), orderInfo.getOrderDesc(), orderInfo.getOrderStatus(),
						orderInfo.getChannelId(), orderInfo.getPartId());

				orderMapper.addOrderItem(item.getOrderId(), item.getGoodsId(), item.getGoodsName(),
						item.getGoodsImage(), item.getGoodsCount(), item.getGoodsPrice(), item.getGoodsDesc(),
						item.getGoodsCount(), item.getPayFee(), item.getOrderStatus(), item.getPartId(),
						item.getItemId(), DateUtils.formatToNaturalDate(dto.getOutData().getEFF_DATE()),
						DateUtils.formatToNaturalDate(dto.getOutData().getEXP_DATE()), item.getEffectiveTime());
				String key = String.format(RedisKeyPrefix.GoodsOrderCount_Prefix, item.getGoodsId());
				String cateKey = String.format(RedisKeyPrefix.CategoryOrderCount_Prefix, item.getCategoryId());
				String ruleLinkUrl = gameInfoService.getLinkUrl();
				String seqIndex = "";
				if (StringUtils.isEmpty(ruleLinkUrl)) {
					String[] params = { key, cateKey };
					// String seqIndex = redisService.evalScript(LuaScriptUtils.CATE_INCR_BY_KEY, 2,
					// params);
					seqIndex = redisService.evalScript(LuaScriptUtils.CATE_INCR_BY_KEY, 2, params);
				} else {
					orderDto.setLinkUrl(ruleLinkUrl);
					String chanceKey = String.format(RedisKeyPrefix.Chance_Prefix, orderInfo.getPhoneNo());
					String orderKey= String.format(RedisKeyPrefix.Order_Game_Share_Prefix, orderInfo.getPhoneNo(),orderInfo.getOrderId());
					String time=String.valueOf(DateUtils.getTimeInMillis()+60000);
					String[] params = { key, cateKey, chanceKey,orderKey,time.substring(0, time.length()-3) };
					
					seqIndex = redisService.evalScript(LuaScriptUtils.CATE_INCR_AND_CHANCE, 4, params);
				}
				// seqIndex = redisService.evalScript(LuaScriptUtils.CATE_INCR_AND_CHANCE, 3,
				// params);
				orderDto.setSeqIndex(seqIndex);
				orderMapper.addSeqIndex(item.getOrderId(), seqIndex, item.getPartId());
				orderDto.setRstCode(1);
				orderDto.setEffDate(DateUtils.formatToDate(dto.getOutData().getEFF_DATE()));
				return orderDto;

			} else {
				if (dto != null) {
					item.setEffectiveTime(dto.getResMsg());
				}
				orderMapper.addFailOrder(orderInfo.getOrderId(), orderInfo.getUserId(), orderInfo.getUserName(),
						orderInfo.getPhoneNo(), orderInfo.getTotalAmout(), orderInfo.getPayId(), orderInfo.getPayFee(),
						orderInfo.getPayNote(), orderInfo.getOrderDesc(), orderInfo.getOrderStatus(),
						orderInfo.getChannelId(), orderInfo.getPartId());

				orderMapper.addFailOrderItem(item.getOrderId(), item.getGoodsId(), item.getGoodsName(),
						item.getGoodsImage(), item.getGoodsCount(), item.getGoodsPrice(), item.getGoodsDesc(),
						item.getGoodsCount(), item.getPayFee(), item.getOrderStatus(), item.getPartId(),
						item.getItemId(), "1970-01-01 00:00:00", "1970-01-01 00:00:00", item.getEffectiveTime());
				orderDto.setRstCode(0);
				
				

				return orderDto;
			}
		}
		orderDto.setRstCode(0);
		return orderDto;

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

	@Override
	public void TccFailOrder(OrderInfo orderInfo) {
		// TODO Auto-generated method stub
		OrderItem item = orderInfo.getOrderItem().get(0);

		if (item != null) {

			orderMapper.addFailOrder(orderInfo.getOrderId(), orderInfo.getUserId(), orderInfo.getUserName(),
					orderInfo.getPhoneNo(), orderInfo.getTotalAmout(), orderInfo.getPayId(), orderInfo.getPayFee(),
					orderInfo.getPayNote(), orderInfo.getOrderDesc(), orderInfo.getOrderStatus(),
					orderInfo.getChannelId(), orderInfo.getPartId());

			orderMapper.addFailOrderItem(item.getOrderId(), item.getGoodsId(), item.getGoodsName(),
					item.getGoodsImage(), item.getGoodsCount(), item.getGoodsPrice(), item.getGoodsDesc(),
					item.getGoodsCount(), item.getPayFee(), item.getOrderStatus(), item.getPartId(), item.getItemId(),
					"1970-01-01 00:00:00", "1970-01-01 00:00:00", item.getEffectiveTime());

		}

	}

	@Override
	public void timeoutOrder(OrderInfo orderInfo, String postTime, String timeoutTime) {
		OrderItem item = orderInfo.getOrderItem().get(0);

		if (item != null) {
			// TODO Auto-generated method stub
			orderMapper.addFailOrder(orderInfo.getOrderId(), orderInfo.getUserId(), orderInfo.getUserName(),
					orderInfo.getPhoneNo(), orderInfo.getTotalAmout(), orderInfo.getPayId(), orderInfo.getPayFee(),
					orderInfo.getPayNote(), orderInfo.getOrderDesc(), orderInfo.getOrderStatus(),
					orderInfo.getChannelId(), orderInfo.getPartId());

			orderMapper.addFailOrderItem(item.getOrderId(), item.getGoodsId(), item.getGoodsName(),
					item.getGoodsImage(), item.getGoodsCount(), item.getGoodsPrice(), item.getGoodsDesc(),
					item.getGoodsCount(), item.getPayFee(), item.getOrderStatus(), item.getPartId(), item.getItemId(),
					postTime, timeoutTime, item.getEffectiveTime() + "timeout");
		}
	}

	@Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public OrderShortAddModeOutputDto MarkActHandleInto(OrderInfo orderInfo) {
		// TODO Auto-generated method stub
		OrderItem item = orderInfo.getOrderItem().get(0);
		OrderShortAddModeOutputDto orderDto = new OrderShortAddModeOutputDto();
		if (item != null) {
			String mkId = item.getPROD_PRCID().substring(0, 4);
			RestResponseOutputDto<MarkActHandleOutputDto> dto = webRestService.MarkActHandle(orderInfo.getPhoneNo(),
					mkId, item.getPROD_PRCID());
			if (dto != null && dto.getResCode().equals("0000000") && dto.getOutData() != null
					&& dto.getOutData().getPASS_FLAG().equals("Y")) {
				orderMapper.addOrder(orderInfo.getOrderId(), orderInfo.getUserId(), orderInfo.getUserName(),
						orderInfo.getPhoneNo(), orderInfo.getTotalAmout(), orderInfo.getPayId(), orderInfo.getPayFee(),
						orderInfo.getPayNote(), orderInfo.getOrderDesc(), orderInfo.getOrderStatus(),
						orderInfo.getChannelId(), orderInfo.getPartId());

				orderMapper.addOrderItem(item.getOrderId(), item.getGoodsId(), item.getGoodsName(),
						item.getGoodsImage(), item.getGoodsCount(), item.getGoodsPrice(), item.getGoodsDesc(),
						item.getGoodsCount(), item.getPayFee(), item.getOrderStatus(), item.getPartId(),
						item.getItemId(), DateUtils.getDateToFormatStr(), "2018-12-31 23:59:59",
						item.getEffectiveTime());
				String key = String.format(RedisKeyPrefix.GoodsOrderCount_Prefix, item.getGoodsId());
				String cateKey = String.format(RedisKeyPrefix.CategoryOrderCount_Prefix, item.getCategoryId());
				String[] params = { key, cateKey };
				String seqIndex = redisService.evalScript(LuaScriptUtils.CATE_INCR_BY_KEY, 2, params);
				orderDto.setSeqIndex(seqIndex);
				orderMapper.addSeqIndex(item.getOrderId(), seqIndex, item.getPartId());
				orderDto.setRstCode(1);
				orderDto.setEffDate(DateUtils.getDateToFormatStr());
				return orderDto;

			} else {
				if (dto != null) {
					item.setEffectiveTime(dto.getResMsg());
				}
				orderMapper.addFailOrder(orderInfo.getOrderId(), orderInfo.getUserId(), orderInfo.getUserName(),
						orderInfo.getPhoneNo(), orderInfo.getTotalAmout(), orderInfo.getPayId(), orderInfo.getPayFee(),
						orderInfo.getPayNote(), orderInfo.getOrderDesc(), orderInfo.getOrderStatus(),
						orderInfo.getChannelId(), orderInfo.getPartId());

				orderMapper.addFailOrderItem(item.getOrderId(), item.getGoodsId(), item.getGoodsName(),
						item.getGoodsImage(), item.getGoodsCount(), item.getGoodsPrice(), item.getGoodsDesc(),
						item.getGoodsCount(), item.getPayFee(), item.getOrderStatus(), item.getPartId(),
						item.getItemId(), "1970-01-01 00:00:00", "1970-01-01 00:00:00", item.getEffectiveTime());
				orderDto.setRstCode(0);

				return orderDto;
			}
		}
		orderDto.setRstCode(0);
		return orderDto;
	}

}
