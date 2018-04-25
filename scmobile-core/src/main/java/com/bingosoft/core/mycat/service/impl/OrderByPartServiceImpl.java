package com.bingosoft.core.mycat.service.impl;

import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.bingosoft.core.mycat.service.IOrderByPartService;
import com.bingosoft.core.mycat.service.IUserBasicByPartService;
import com.bingosoft.core.mysql.service.IGameInfoService;
import com.bingosoft.core.web.service.ICsfWebService;
import com.bingosoft.core.web.service.IWebRestService;
import com.bingosoft.models.config.OrderConfig;
import com.bingosoft.models.csf.OfferItemOutputDto;
import com.bingosoft.models.csf.OrderResultOutputDto;
import com.bingosoft.models.dto.OrderDetailOutputDto;
import com.bingosoft.models.dto.OrderInfo;
import com.bingosoft.models.dto.OrderItem;
import com.bingosoft.models.dto.OrderItemListOutputDto;
import com.bingosoft.models.dto.OrderNewItemOutputDto;
import com.bingosoft.models.dto.OrderNewListOutputDto;
import com.bingosoft.models.dto.OrderShortAddModeOutputDto;
import com.bingosoft.models.dto.UpUserGradeOutputDto;
import com.bingosoft.models.prefix.RedisKeyPrefix;
import com.bingosoft.models.rest.dto.MarkActHandleOutputDto;
import com.bingosoft.models.rest.dto.RestResponseOutputDto;
import com.bingosoft.models.rest.dto.ShortAddModeOutputDto;
import com.bingosoft.models.wenum.OrderGradeEnum;
import com.bingosoft.models.wenum.OrderSmsStatus;
import com.bingosoft.persist.mycat.dao.IOrderByPartMapper;
import com.bingosoft.persist.redis.dao.IRedisService;
import com.bingosoft.utils.DateUtils;
import com.bingosoft.utils.LuaScriptUtils;


@Service
public class OrderByPartServiceImpl implements IOrderByPartService {

	private static final Logger logger = LoggerFactory.getLogger(OrderByPartServiceImpl.class);

	@Autowired
	IWebRestService webRestService;

	@Autowired
	IOrderByPartMapper orderMapper;

	@Autowired
	IRedisService redisService;

	@Autowired
	IGameInfoService gameInfoService;

	@Autowired
	ICsfWebService csfWebService;

	@Autowired
	OrderConfig orderConfig;

	@Autowired
	IUserBasicByPartService userBasicService;

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
						DateUtils.formatToNaturalDate(dto.getOutData().getEXP_DATE()), item.getEffectiveTime(),
						item.getCateName(), item.getCateImg());
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
					String orderKey = String.format(RedisKeyPrefix.Order_Game_Share_Prefix, orderInfo.getPhoneNo(),
							orderInfo.getOrderId());
					String time = String.valueOf(DateUtils.getTimeInMillis() + 60000);
					String[] params = { key, cateKey, chanceKey, orderKey, time.substring(0, time.length() - 3) };

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
					orderDto.setRetMsg(dto.getResMsg());
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
						item.getEffectiveTime(), item.getCateName(), item.getCateImg());
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
					orderDto.setRetMsg(dto.getResMsg());
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

	/**
	 * 办理营销活动并可参加转盘活动
	 */
	@Override
	public OrderShortAddModeOutputDto MarkActHandleIntoSame(OrderInfo orderInfo) {
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
						item.getEffectiveTime(), item.getCateName(), item.getCateImg());
				// String key = String.format(RedisKeyPrefix.GoodsOrderCount_Prefix,
				// item.getGoodsId());
				// String cateKey = String.format(RedisKeyPrefix.CategoryOrderCount_Prefix,
				// item.getCategoryId());
				// String[] params = { key, cateKey };
				// String seqIndex = redisService.evalScript(LuaScriptUtils.CATE_INCR_BY_KEY, 2,
				// params);
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
					String orderKey = String.format(RedisKeyPrefix.Order_Game_Share_Prefix, orderInfo.getPhoneNo(),
							orderInfo.getOrderId());
					String time = String.valueOf(DateUtils.getTimeInMillis() + 60000);
					String[] params = { key, cateKey, chanceKey, orderKey, time.substring(0, time.length() - 3) };

					seqIndex = redisService.evalScript(LuaScriptUtils.CATE_INCR_AND_CHANCE, 4, params);
				}
				orderDto.setSeqIndex(seqIndex);
				orderMapper.addSeqIndex(item.getOrderId(), seqIndex, item.getPartId());
				orderDto.setRstCode(1);
				orderDto.setEffDate(DateUtils.getDateToFormatStr());
				return orderDto;

			} else {
				if (dto != null) {
					item.setEffectiveTime(dto.getResMsg());
					orderDto.setRetMsg(dto.getResMsg());
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

	// @Override
	@Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, timeout = 36000, rollbackFor = Exception.class)
	public OrderShortAddModeOutputDto orderCsfGoodsInto(OrderInfo orderInfo, String smsCode) {

		OrderItem item = orderInfo.getOrderItem().get(0);
		OrderShortAddModeOutputDto orderDto = new OrderShortAddModeOutputDto();

		if (item != null) {
			if (!smsCode.isEmpty()) {
				String cacheCode = redisService.get(String.format(RedisKeyPrefix.Sms_Prefix, orderInfo.getPhoneNo()));
				if (!smsCode.equals(cacheCode)) {
					orderDto.setRstCode(0);
					orderDto.setRetMsg("验证码已过期！");
					return orderDto;
				}
			} else {
				String tips = orderLimit(orderInfo.getPhoneNo(), orderInfo.getPartId(), item.getGoodsPrice());
				if (!tips.isEmpty()) {
					orderDto.setRstCode(0);
					orderDto.setRetMsg(tips);
					return orderDto;
				}
			}
			UpUserGradeOutputDto userGrade = userBasicService.isUpGrade(orderInfo.getPhoneNo());
			if (userGrade == null) {
				orderDto.setRetMsg("未获取到相关信息");
				return orderDto;
			}

			// RestResponseOutputDto<ShortAddModeOutputDto> dto =
			// webRestService.sShortAddMode(orderInfo.getPhoneNo(),
			// item.getPROD_PRCID());
			OrderResultOutputDto orderStatus = csfWebService.CSCHTSubmitOffersOrder(orderInfo.getPhoneNo(),
					item.getPROD_PRCID());
			if (orderStatus.isSuccess()) {
				try {
					if (userGrade.isUp()) {
						orderDto.setUp(true);
						orderDto.setGradeImg(userGrade.getGradeImg());
						orderDto.setGradeName(userGrade.getGradeName());
						userBasicService.updateUserBasicByUp(orderInfo.getPhoneNo(), orderInfo.getPartId(),
								userGrade.getGradeId(), userGrade.getGradeName(), userGrade.getGradeImg());
					} else {
						orderDto.setUp(false);
						userBasicService.updateUserBasic(orderInfo.getPhoneNo(), orderInfo.getPartId());

					}
				} catch (Exception ex) {
					logger.error(
							String.format("等级更新失败:用户号码->%s,订单id->%s,失败原因->%s", orderInfo.getPhoneNo(), orderInfo.getOrderId(),ex.getMessage()));
				}
				redisService.del(String.format(RedisKeyPrefix.User_Basic_Prefix, orderInfo.getPhoneNo()));
//				OfferItemOutputDto dto = csfWebService.subscribeInfoCentral(orderInfo.getPhoneNo(),
//						item.getPROD_PRCID());
				OfferItemOutputDto dto = csfWebService.getOrderProdInfo(orderInfo.getPhoneNo(),
						item.getPROD_PRCID(),0);
				if (dto == null) {
					logger.info("未获取到订购信息");
					dto = new OfferItemOutputDto();
					dto.setEffTime("197001010000000");
					dto.setExpTime("197001010000000");
				}
				orderMapper.addOrder(orderInfo.getOrderId(), orderInfo.getUserId(), orderInfo.getUserName(),
						orderInfo.getPhoneNo(), orderInfo.getTotalAmout(), orderInfo.getPayId(), orderInfo.getPayFee(),
						orderInfo.getPayNote(), orderInfo.getOrderDesc(), orderInfo.getOrderStatus(),
						orderInfo.getChannelId(), orderInfo.getPartId());

				orderMapper.addOrderItem(item.getOrderId(), item.getGoodsId(), item.getGoodsName(),
						item.getGoodsImage(), item.getGoodsCount(), item.getGoodsPrice(), item.getGoodsDesc(),
						item.getGoodsCount(), item.getPayFee(), item.getOrderStatus(), item.getPartId(),
						item.getItemId(), DateUtils.formatToNaturalDate(dto.getEffTime()),
						DateUtils.formatToNaturalDate(dto.getExpTime()), item.getEffectiveTime(), item.getCateName(),
						item.getCateImg());
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
					String orderKey = String.format(RedisKeyPrefix.Order_Game_Share_Prefix, orderInfo.getPhoneNo(),
							orderInfo.getOrderId());
					String time = String.valueOf(DateUtils.getTimeInMillis() + 60000);
					String[] params = { key, cateKey, chanceKey, orderKey, time.substring(0, time.length() - 3) };

					seqIndex = redisService.evalScript(LuaScriptUtils.CATE_INCR_AND_CHANCE, 4, params);
				}
				// seqIndex = redisService.evalScript(LuaScriptUtils.CATE_INCR_AND_CHANCE, 3,
				// params);
				orderDto.setSeqIndex(seqIndex);
				orderMapper.addSeqIndex(item.getOrderId(), seqIndex, item.getPartId());

				orderDto.setRstCode(1);
				// orderDto.setEffDate(DateUtils.formatToDate(dto.getOutData().getEFF_DATE()));
				orderDto.setEffDate(item.getEffectiveTime());
				return orderDto;

			} else {
				if (orderStatus != null) {
					item.setEffectiveTime(orderStatus.getMsg());
					orderDto.setRetMsg(orderStatus.getMsg());
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
	public String orderLimit(String phone, int partId, double price) {
		double sum = orderMapper.getOrderSum(phone, partId);
		String sendContent = "";
		double sendLimit = orderConfig.getLimit();
		if (sum + price >= sendLimit) {
			if (sum >= sendLimit) {
				sendContent = String.format(orderConfig.getTip2(), sendLimit);
			} else {
				sendContent = String.format(orderConfig.getTip1(), sendLimit);
			}
		}
		return sendContent;
	}

}
