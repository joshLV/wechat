package com.bingosoft.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bingosoft.common.config.UrlConfig;
import com.bingosoft.core.mycat.service.IBrowseRecordsByPartService;
import com.bingosoft.core.mycat.service.IGoodsCollectionByPartService;
import com.bingosoft.core.mycat.service.IOrderByPartService;
import com.bingosoft.core.mycat.service.IOrderNewsByPartService;
import com.bingosoft.core.mysql.service.IAdInfoService;
import com.bingosoft.core.mysql.service.IArticleCategoryService;
import com.bingosoft.core.mysql.service.IBrowseRecordsService;
import com.bingosoft.core.mysql.service.IGoodsCollectionService;
import com.bingosoft.core.mysql.service.IGoodsInfoService;
import com.bingosoft.core.mysql.service.IOrderNewsService;
import com.bingosoft.core.mysql.service.IOrderService;
import com.bingosoft.core.redis.service.IUserInfoCacheService;
import com.bingosoft.core.web.service.IOperationService;
import com.bingosoft.core.web.service.IWebRestService;
import com.bingosoft.models.dto.AdInfoOutputDto;
import com.bingosoft.models.dto.ArticleCategoryOutputDto;
import com.bingosoft.models.dto.ArticleInfoItemOutputDto;
import com.bingosoft.models.dto.ArticleInfoOutputDto;
import com.bingosoft.models.dto.ArticleOutputDto;
import com.bingosoft.models.dto.BindUserInfoOutputDto;
import com.bingosoft.models.dto.BrowseCountOutput;
import com.bingosoft.models.dto.BrowseInputDto;
import com.bingosoft.models.dto.BrowseRecords2MonthOutputDto;
import com.bingosoft.models.dto.GoodsCategoryIdOutputDto;
import com.bingosoft.models.dto.GoodsCategoryInfoOutputDto;
import com.bingosoft.models.dto.GoodsCategoryOutputDto;
import com.bingosoft.models.dto.GoodsCollectionOutputDto;
import com.bingosoft.models.dto.GoodsCountOutputDto;
import com.bingosoft.models.dto.GoodsIdCollectionOutputDto;
import com.bingosoft.models.dto.GoodsItemInfoAndProdOutputDto;
import com.bingosoft.models.dto.GoodsItemInfoOutputDto;
import com.bingosoft.models.dto.HotCategoryGoodsOutputDto;
import com.bingosoft.models.dto.HotGoodsCategoryOutputDto;
import com.bingosoft.models.dto.JsSignatureDto;
import com.bingosoft.models.dto.MessageDto;
import com.bingosoft.models.dto.MessageObject;
import com.bingosoft.models.dto.OrderDetailOutputDto;
import com.bingosoft.models.dto.OrderInfo;
import com.bingosoft.models.dto.OrderItem;
import com.bingosoft.models.dto.OrderItemListOutputDto;
import com.bingosoft.models.dto.OrderNewItemOutputDto;
import com.bingosoft.models.dto.OrderNewListOutputDto;
import com.bingosoft.models.dto.OrderShortAddModeOutputDto;
import com.bingosoft.models.dto.TelPhoneInputDto;
import com.bingosoft.models.dto.TemplateData;
import com.bingosoft.models.dto.TemplateMessageInputDto;
import com.bingosoft.models.dto.UserBalance;
import com.bingosoft.models.dto.UserFlowInfoOutputDto;
import com.bingosoft.models.dto.UserInfoDto;
import com.bingosoft.models.dto.UserInfoOutputDto;
import com.bingosoft.models.input.dto.BrowseRecordInputDto;
import com.bingosoft.models.input.dto.GoodsCollectionInputDto;
import com.bingosoft.models.input.dto.OrderInputDto;
import com.bingosoft.models.input.dto.SignatureUrlInputDto;
import com.bingosoft.models.mongodb.entities.UserPhone;
import com.bingosoft.models.mongodb.entities.WechatUserInfo;
import com.bingosoft.models.msg.BaseMessage;
import com.bingosoft.models.msg.ResponseMessage;
import com.bingosoft.models.prefix.TemplateId;
import com.bingosoft.models.prefix.TemplateTipsMsg;
import com.bingosoft.models.rest.dto.GprsInfoOutputDto;
import com.bingosoft.models.rest.dto.OBFreeQryOutDataOutputDto;
import com.bingosoft.models.rest.dto.RealTimeFeeOutputDto;
import com.bingosoft.models.rest.dto.RestResponseOutputDto;
import com.bingosoft.models.rest.dto.SPFeeQueryOutputDto;
import com.bingosoft.persist.mongodb.dao.IUserPhoneRepository;
import com.bingosoft.persist.mongodb.dao.IWechatUserInfoRepository;
import com.bingosoft.utils.CmccDesUtils;
import com.bingosoft.utils.FlowConvertUtils;
import com.bingosoft.utils.IdGenerator;
import com.bingosoft.utils.JSONUtils;
import com.bingosoft.utils.crypt.ShareDesUtils;
import com.bingosoft.utils.crypt.TokenUtils;
import com.bingosoft.web.config.UrlParams;
import com.bingosoft.web.exception.WechatException;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "流量专区", description = "流量专区API")
@RestController
@RequestMapping("/goods/api/v2")
public class GoodsV2Controller {
	private static final Logger logger = LoggerFactory.getLogger(GoodsV2Controller.class);

	private static final Logger orderLog = LoggerFactory.getLogger("order");

	@Autowired
	IWebRestService webRestService;

	@Autowired
	IGoodsInfoService goodsInfoService;

	@Autowired
	IAdInfoService adInfoService;

	@Autowired
	IArticleCategoryService articleCategoryService;

	@Autowired
	IOrderNewsByPartService orderNewsService;

	@Autowired
	IGoodsCollectionByPartService goodsCollectionService;

	@Autowired
	IOrderByPartService orderService;

	@Autowired
	IBrowseRecordsByPartService browseRecordsService;

	@Autowired
	IOperationService opService;

	@Autowired
	UrlConfig config;

	@Autowired
	IUserInfoCacheService userCacheService;

	String tokenHeader = "os";

	@Autowired
	IUserPhoneRepository userPhone;

	@Autowired
	IWechatUserInfoRepository wechatUserInfo;

	@Autowired
	IWebRestService webrestService;

	SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日HH点mm分ss秒");
	SimpleDateFormat sdt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	@ApiOperation(value = "获取商品信息", notes = "获取商品信息")
	@RequestMapping(value = "/getGoodsInfo", method = RequestMethod.GET)
	public ResponseMessage<GoodsItemInfoOutputDto> getGoodsInfo(int goodsId) {
		GoodsItemInfoOutputDto rst = goodsInfoService.getGoodsInfo(goodsId);
		return new ResponseMessage<GoodsItemInfoOutputDto>(200, true, "", rst);
	}

	@ApiOperation(value = "获取广告列表", notes = "获取广告列表")
	@RequestMapping(value = "/getAdList", method = RequestMethod.GET)
	public ResponseMessage<List<AdInfoOutputDto>> getAdList(int postionId) {
		List<AdInfoOutputDto> rst = adInfoService.getAdList(postionId);
		return new ResponseMessage<List<AdInfoOutputDto>>(200, true, "", rst);
	}

	@ApiOperation(value = "获取首页流量课堂", notes = "获取首页流量课堂")
	@RequestMapping(value = "/getHomeArticleList", method = RequestMethod.GET)
	public ResponseMessage<List<ArticleInfoOutputDto>> getHomeArticleList(int categoryId, int records) {
		List<ArticleInfoOutputDto> rst = articleCategoryService.getHomeArticleList(categoryId, records);
		return new ResponseMessage<List<ArticleInfoOutputDto>>(200, true, "", rst);
	}

	@ApiOperation(value = "获取文章分类", notes = "获取文章分类")
	@RequestMapping(value = "/getArticleCategoryList", method = RequestMethod.GET)
	public ResponseMessage<List<ArticleCategoryOutputDto>> getArticleCategoryList(int categoryId) {
		List<ArticleCategoryOutputDto> rst = articleCategoryService.getArticleCategoryList(categoryId);
		return new ResponseMessage<List<ArticleCategoryOutputDto>>(200, true, "", rst);
	}

	@ApiOperation(value = "获取流量课堂文章列表", notes = "获取流量课堂文章列表")
	@RequestMapping(value = "/getArticleList", method = RequestMethod.GET)
	public ResponseMessage<List<ArticleOutputDto>> getArticleList(int categoryId, int articleType, int pageIndex,
			int pageSize) {
		List<ArticleOutputDto> rst = articleCategoryService.getArticleList(categoryId, articleType, pageIndex,
				pageSize);
		return new ResponseMessage<List<ArticleOutputDto>>(200, true, "", rst);
	}

	@ApiOperation(value = "获取流量课堂文章详情", notes = "获取流量课堂文章详情")
	@RequestMapping(value = "/getArticleInfo", method = RequestMethod.GET)
	public ResponseMessage<ArticleInfoItemOutputDto> getArticleInfo(int articleId) {
		ArticleInfoItemOutputDto rst = articleCategoryService.getArticleInfo(articleId);
		return new ResponseMessage<ArticleInfoItemOutputDto>(200, true, "", rst);
	}

	@ApiOperation(value = "获取流量套餐", notes = "获取流量套餐")
	@RequestMapping(value = "/getGoodsCategoryList", method = RequestMethod.GET)
	public ResponseMessage<List<GoodsCategoryOutputDto>> getGoodsCategoryList(int categoryId) {
		List<GoodsCategoryOutputDto> rst = goodsInfoService.getGoodsCategoryList(categoryId);
		return new ResponseMessage<List<GoodsCategoryOutputDto>>(200, true, "", rst);
	}

	@ApiOperation(value = "获取流量套餐详情", notes = "获取流量套餐详情")
	@RequestMapping(value = "/getGoodsByCategory", method = RequestMethod.GET)
	public ResponseMessage<GoodsCategoryInfoOutputDto> getGoodsByCategory(int categoryId) {
		GoodsCategoryInfoOutputDto rst = goodsInfoService.getGoodsCategory(categoryId);
		return new ResponseMessage<GoodsCategoryInfoOutputDto>(200, true, "", rst);
	}

	@ApiOperation(value = "收藏商品", notes = "收藏商品")
	@RequestMapping(value = "/addGoodsCollection", method = RequestMethod.POST)
	public BaseMessage addGoodsCollection(HttpServletRequest request, int goodsId, int categoryId) {
		UserInfoDto userDto = null;
		MessageDto dtos = validateToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new BaseMessage(dtos.getStatus(), true, dtos.getMessage());
		}
		GoodsItemInfoOutputDto dto = goodsInfoService.getGoodsInfo(goodsId);
		if (dto != null) {
			GoodsCollectionInputDto inputDto = new GoodsCollectionInputDto();
			inputDto.setCategoryId(dto.getCategoryId());
			inputDto.setGoodsId(goodsId);
			inputDto.setGoodsImage(dto.getGoodsImage());
			inputDto.setGoodsName(dto.getGoodsName());
			inputDto.setGoodsPrice(dto.getGoodsPrice());
			inputDto.setPartId(getPartId(userDto.getPhoenNo()));
			inputDto.setUserId(userDto.getUserId());
			goodsCollectionService.addGoodsCollection(inputDto);
			return new BaseMessage(200, true, "收藏成功");
		}
		return new BaseMessage(400, true, "收藏失败");

	}

	@ApiOperation(value = "是否收藏商品", notes = "是否收藏商品")
	@RequestMapping(value = "/isGoodsCollection", method = RequestMethod.POST)
	public BaseMessage isGoodsCollection(HttpServletRequest request, int goodsId) {
		UserInfoDto userDto = null;

		MessageDto dtos = validateToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new BaseMessage(dtos.getStatus(), true, dtos.getMessage());
		}

		int rst = goodsCollectionService.isGoodsCollection(userDto.getUserId(), goodsId,
				getPartId(userDto.getPhoenNo()));
		return new BaseMessage(200, rst > 0 ? true : false, "");
	}

	@ApiOperation(value = "获取收藏goodsId列表", notes = "获取收藏goodsId列表")
	@RequestMapping(value = "/getCollectionGoodsId", method = RequestMethod.GET)
	public ResponseMessage<List<GoodsIdCollectionOutputDto>> getCollectionGoodsId(HttpServletRequest request,
			int categoryId) {
		UserInfoDto userDto = null;

		MessageDto dtos = validateToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new ResponseMessage<List<GoodsIdCollectionOutputDto>>(dtos.getStatus(), true, dtos.getMessage());
		}
		List<GoodsIdCollectionOutputDto> rst = goodsCollectionService.getCollectionGoodsId(userDto.getUserId(),
				categoryId, getPartId(userDto.getPhoenNo()));
		return new ResponseMessage<List<GoodsIdCollectionOutputDto>>(200, true, "", rst);
	}

	@ApiOperation(value = "取消收藏商品", notes = "取消收藏商品")
	@RequestMapping(value = "/delGoodsCollection", method = RequestMethod.POST)
	public BaseMessage delGoodsCollection(HttpServletRequest request, int goodsId) {
		UserInfoDto userDto = null;

		MessageDto dtos = validateToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new BaseMessage(dtos.getStatus(), true, dtos.getMessage());
		}
		goodsCollectionService.delGoodsCollection(userDto.getUserId(), goodsId, getPartId(userDto.getPhoenNo()));
		return new BaseMessage(200, true, "取消收藏成功");
	}

	@ApiOperation(value = "获取订单详情", notes = "获取订单详情")
	@RequestMapping(value = "/getOrderDetail", method = RequestMethod.GET)
	public ResponseMessage<OrderDetailOutputDto> getOrderDetail(HttpServletRequest request, long orderId) {
		UserInfoDto userDto = null;
		MessageDto dtos = validateToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new ResponseMessage<OrderDetailOutputDto>(dtos.getStatus(), true, dtos.getMessage());
		}
		OrderDetailOutputDto dto = orderService.getOrderDetail(orderId, getPartId(userDto.getPhoenNo()));
		return new ResponseMessage<OrderDetailOutputDto>(200, true, "成功", dto);
	}

	@ApiOperation(value = "用户浏览记录", notes = "用户浏览记录")
	@RequestMapping(value = "/getBrowseRecords", method = RequestMethod.GET)
	public ResponseMessage<List<BrowseRecords2MonthOutputDto>> getBrowseRecords(HttpServletRequest request,
			int pageIndex, int pageSize) {
		UserInfoDto userDto = null;
		MessageDto dtos = validateToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new ResponseMessage<List<BrowseRecords2MonthOutputDto>>(dtos.getStatus(), true, dtos.getMessage());
		}
		List<BrowseRecords2MonthOutputDto> dto = browseRecordsService.getBrowseRecords(userDto.getUserId(), pageIndex,
				pageSize, getPartId(userDto.getPhoenNo()));
		return new ResponseMessage<List<BrowseRecords2MonthOutputDto>>(200, true, "成功", dto);
	}

	@ApiOperation(value = "用户浏览记录", notes = "用户浏览记录")
	@RequestMapping(value = "/addBrowseRecords", method = RequestMethod.POST)
	public BaseMessage addBrowseRecords(HttpServletRequest request, BrowseInputDto input) {
		UserInfoDto userDto = null;
		MessageDto dtos = validateToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new BaseMessage(dtos.getStatus(), true, dtos.getMessage());
		}
		GoodsItemInfoOutputDto dto = goodsInfoService.getGoodsInfo(input.getGoodsId());
		if (dto != null) {
			BrowseRecordInputDto inputDto = new BrowseRecordInputDto();
			inputDto.setCategoryId(dto.getCategoryId());
			inputDto.setGoodsId(input.getGoodsId());
			inputDto.setGoodsImage(dto.getGoodsImage());
			inputDto.setGoodsName(dto.getGoodsName());
			inputDto.setGoodsPrice(dto.getGoodsPrice());
			inputDto.setUpdateTime(new Date());
			inputDto.setUserId(userDto.getUserId());
			inputDto.setPartId(getPartId(userDto.getPhoenNo()));
			browseRecordsService.addBrowse(inputDto);
			return new BaseMessage(200, true, "成功");
		}
		return new BaseMessage(400, true, "失败");
	}

	@ApiOperation(value = "用户收藏记录", notes = "用户收藏记录")
	@RequestMapping(value = "/getGoodsCollection", method = RequestMethod.GET)
	public ResponseMessage<List<GoodsCollectionOutputDto>> getGoodsCollection(HttpServletRequest request, int pageIndex,
			int pageSize) {
		UserInfoDto userDto = null;

		MessageDto dtos = validateToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new ResponseMessage<List<GoodsCollectionOutputDto>>(dtos.getStatus(), true, dtos.getMessage());
		}

		List<GoodsCollectionOutputDto> dto = goodsCollectionService.getGoodsCollection(userDto.getUserId(), pageIndex,
				pageSize, getPartId(userDto.getPhoenNo()));
		return new ResponseMessage<List<GoodsCollectionOutputDto>>(200, true, "成功", dto);
	}

	@ApiOperation(value = "获取话费余额", notes = "获取话费余额")
	@RequestMapping(value = "/getUserMoney", method = RequestMethod.GET)
	public ResponseMessage<UserBalance> getUserMoney(HttpServletRequest request) {
		UserInfoDto userDto = null;

		MessageDto dtos = validateToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new ResponseMessage<UserBalance>(dtos.getStatus(), true, dtos.getMessage());
		}

		RestResponseOutputDto<SPFeeQueryOutputDto> moneyDto = webRestService.sPFeeQuery(userDto.getPhoenNo());
		UserBalance dto = new UserBalance();
		dto.setPhoneNo(userDto.getPhoenNo());
		if (moneyDto != null) {
			if (moneyDto.getOutData() != null) {
				double money = moneyDto.getOutData().getPREPAY_FEE() / 100.0;
				dto.setBalence(money);
				return new ResponseMessage<UserBalance>(200, true, "成功", dto);
			}

		} else {
			dto.setBalence(0.00);
		}
		return new ResponseMessage<UserBalance>(400, true, "未获取到", dto);

	}

	@ApiOperation(value = "根据商品Id获取分类Id", notes = "根据商品Id获取分类Id")
	@RequestMapping(value = "/getCategoryId", method = RequestMethod.GET)
	public ResponseMessage<GoodsCategoryIdOutputDto> getCategoryId(int goodsId) {
		String userId = "";
		GoodsCategoryIdOutputDto dto = goodsInfoService.getCategoryId(goodsId);
		return new ResponseMessage<GoodsCategoryIdOutputDto>(200, true, "成功", dto);
	}

	@ApiOperation(value = "下单商品", notes = "下单商品")
	@RequestMapping(value = "/orderGoods", method = RequestMethod.POST)
	public ResponseMessage<String> orderGoods(HttpServletRequest request, @RequestBody OrderInputDto input) {
		UserInfoDto userDto = null; // new UserInfoDto();
		// String msg=TemplateTipsMsg.getTips(input.getGoodsId());
		MessageDto dtos = validateToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new ResponseMessage<String>(dtos.getStatus(), true, dtos.getMessage());
		}

		int partId = Integer.valueOf(userDto.getPhoenNo().substring(userDto.getPhoenNo().length() - 1));
		OrderInfo order = new OrderInfo();
		order.setOrderId(Long.parseLong(IdGenerator.INSTANCE.nextId()));
		order.setChannelId(input.getChannelId());
		order.setUserId(userDto.getUserId());
		order.setUserName(userDto.getUserName());
		order.setPhoneNo(userDto.getPhoenNo());
		order.setPartId(partId);
		order.setOrderStatus(1);

		List<OrderItem> items = new ArrayList<OrderItem>();
		GoodsItemInfoAndProdOutputDto rst = goodsInfoService.getGoodsInfoAndProd(input.getGoodsId());
		if (rst == null) {
			return new ResponseMessage<String>(400, true, "未找到该产品");
		}
		OrderItem item = new OrderItem();
		order.setPayFee(rst.getGoodsPrice());
		order.setPayId(1);
		order.setPayNote("fhdk");
		item.setCategoryId(rst.getCategoryId());
		item.setGoodsCount(1);
		item.setGoodsDesc(rst.getGoodsDesc());
		item.setGoodsId(input.getGoodsId());
		item.setOrderId(order.getOrderId());
		item.setPartId(partId);
		item.setCreateTime(new Date());
		item.setGoodsImage(rst.getGoodsImage());
		item.setGoodsPrice(rst.getGoodsPrice());
		item.setOrderStatus(1);
		item.setPartId(partId);
		item.setGoodsName(rst.getGoodsName());
		item.setPayFee(rst.getGoodsPrice());
		item.setTotalAmout(1);

		if (StringUtils.isEmpty(rst.getEffectiveTime()))
			item.setEffectiveTime("");
		else {
			item.setEffectiveTime(rst.getEffectiveTime());
		}
		String prod_id = "";
		if (rst.getFeeCode().contains("\\")) {
			String[] codes = rst.getFeeCode().split("\\\\");
			prod_id = codes[0];
		} else {
			prod_id = rst.getFeeCode();
		}
		item.setPROD_PRCID(prod_id);
		item.setItemId(Long.parseLong(IdGenerator.INSTANCE.nextId()));
		items.add(item);

		order.setOrderItem(items);
		OrderShortAddModeOutputDto orderDto = new OrderShortAddModeOutputDto();
		// orderService.orderGoods(order);
		String postTime = sdt.format(new Date());
		try {
			orderDto = orderService.orderGoodsInto(order);
			if (orderDto.getRstCode() == 1) {
				// 如果是微信才更新消息且发送模版
				if (input.getChannelId() == 1) {
					if (!StringUtils.isEmpty(userDto.getOpenId())) {
						TemplateMessageInputDto template = new TemplateMessageInputDto();
						TemplateData data = new TemplateData();
						MessageObject first = new MessageObject();
						MessageObject keyword1 = new MessageObject();
						// String tips_msg = TemplateTipsMsg.getTips(input.getGoodsId());
						String tips_msg = rst.getTags();
						if (StringUtils.isEmpty(tips_msg)) {
							tips_msg = TemplateTipsMsg.getTips(input.getGoodsId());
							if (StringUtils.isEmpty(tips_msg)) {
								tips_msg = "你好，你的流量订购成功,业务将在" + orderDto.getEffDate() + "生效";
							}
						}

						// first.setValue("你好，你的流量订购成功,业务将在" + orderDto.getEffDate() + "生效");
						first.setValue(tips_msg);
						data.setFirst(first);
						keyword1.setValue(rst.getGoodsName());
						MessageObject keyword2 = new MessageObject();
						keyword2.setValue(userDto.getPhoenNo());
						MessageObject keyword3 = new MessageObject();
						keyword3.setValue(sdf.format(new Date()));
						MessageObject keyword4 = new MessageObject();
						keyword4.setValue(
								String.format("成功,你是第%s名办理用户,订购序号：%s", orderDto.getSeqIndex(), orderDto.getSeqIndex()));
						MessageObject remark = new MessageObject();
						remark.setValue("点击详情，了解更多！");
						data.setKeyword1(keyword1);
						data.setKeyword2(keyword2);
						data.setKeyword3(keyword3);
						data.setKeyword4(keyword4);
						data.setRemark(remark);
						template.setData(data);
						template.setTouser(userDto.getOpenId());
						template.setUrl("http://wx.10086.cn/sichuan/wxzq/llzq/index.html");
						logger.info("send template message openId:" + userDto.getOpenId());
						// System.out.println(userDto.getOpenId());
						// template.setTouser("ovCZJuPE45DnZGGrFPKzIkGws3xE");
						template.setTemplate_id(TemplateId.OP_SUCCESS);
						opService.templateMessage(template);
					}
					orderNewsService.addOrderNews(userDto.getPhoenNo(), getPartId(userDto.getPhoenNo()));
				}
				String url = orderDto.getLinkUrl();
				if (!StringUtils.isEmpty(url)) {
					url += "?orderId=" + order.getOrderId();
				}
				return new ResponseMessage<String>(200, true, "成功", url);
			} else {
				return new ResponseMessage<String>(400, true, "订购失败");
			}
		} catch (Exception e) {
			logger.error("goodsController:" + e.getMessage() + e.getStackTrace());
			logger.error(JSONUtils.toJson(order));
			logger.error(JSONUtils.toJson(orderDto));
			orderLog.info(JSONUtils.toJson(order));
			try {
				orderService.timeoutOrder(order, postTime, sdt.format(new Date()));
			} catch (Exception ex) {
				logger.error("timeoutOrder:" + ex.getMessage() + ex.getStackTrace());
			}
			return new ResponseMessage<String>(400, true, "订购失败");
		}
	}

	@ApiOperation(value = "营销活动办理", notes = "营销活动办理")
	@RequestMapping(value = "/MarkActHandle", method = RequestMethod.POST)
	public BaseMessage MarkActHandle(HttpServletRequest request, @RequestBody OrderInputDto input) {
		UserInfoDto userDto = null; // new UserInfoDto();
		// String msg=TemplateTipsMsg.getTips(input.getGoodsId());
		MessageDto dtos = validateToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new BaseMessage(dtos.getStatus(), true, dtos.getMessage());
		}

		int partId = Integer.valueOf(userDto.getPhoenNo().substring(userDto.getPhoenNo().length() - 1));
		OrderInfo order = new OrderInfo();
		order.setOrderId(Long.parseLong(IdGenerator.INSTANCE.nextId()));
		order.setChannelId(input.getChannelId());
		order.setUserId(userDto.getUserId());
		order.setUserName(userDto.getUserName());
		order.setPhoneNo(userDto.getPhoenNo());
		order.setPartId(partId);
		order.setOrderStatus(1);

		List<OrderItem> items = new ArrayList<OrderItem>();
		GoodsItemInfoAndProdOutputDto rst = goodsInfoService.getGoodsInfoAndProd(input.getGoodsId());
		if (rst == null) {
			return new BaseMessage(400, true, "未找到该产品");
		}
		OrderItem item = new OrderItem();
		order.setPayFee(rst.getGoodsPrice());
		order.setPayId(1);
		order.setPayNote("fhdk");
		item.setCategoryId(rst.getCategoryId());
		item.setGoodsCount(1);
		item.setGoodsDesc(rst.getGoodsDesc());
		item.setGoodsId(input.getGoodsId());
		item.setOrderId(order.getOrderId());
		item.setPartId(partId);
		item.setCreateTime(new Date());
		item.setGoodsImage(rst.getGoodsImage());
		item.setGoodsPrice(rst.getGoodsPrice());
		item.setOrderStatus(1);
		item.setPartId(partId);
		item.setGoodsName(rst.getGoodsName());
		item.setPayFee(rst.getGoodsPrice());
		item.setTotalAmout(1);

		if (StringUtils.isEmpty(rst.getEffectiveTime()))
			item.setEffectiveTime("");
		else {
			item.setEffectiveTime(rst.getEffectiveTime());
		}
		String prod_id = "";
		if (rst.getFeeCode().contains("\\")) {
			String[] codes = rst.getFeeCode().split("\\\\");
			prod_id = codes[0];
		} else {
			prod_id = rst.getFeeCode();
		}
		item.setPROD_PRCID(prod_id);
		item.setItemId(Long.parseLong(IdGenerator.INSTANCE.nextId()));
		items.add(item);

		order.setOrderItem(items);
		OrderShortAddModeOutputDto orderDto = new OrderShortAddModeOutputDto();
		// orderService.orderGoods(order);
		String postTime = sdt.format(new Date());
		try {
			orderDto = orderService.MarkActHandleInto(order);
			if (orderDto.getRstCode() == 1) {
				// 如果是微信才更新消息且发送模版
				if (input.getChannelId() == 1) {
					if (!StringUtils.isEmpty(userDto.getOpenId())) {
						TemplateMessageInputDto template = new TemplateMessageInputDto();
						TemplateData data = new TemplateData();
						MessageObject first = new MessageObject();
						MessageObject keyword1 = new MessageObject();
						// String tips_msg = TemplateTipsMsg.getTips(input.getGoodsId());
						String tips_msg = rst.getTags();
						if (StringUtils.isEmpty(tips_msg)) {
							tips_msg = TemplateTipsMsg.getTips(input.getGoodsId());
							if (StringUtils.isEmpty(tips_msg)) {
								tips_msg = "你好，你的流量订购成功,业务将在" + orderDto.getEffDate() + "生效";
							}
						}

						// first.setValue("你好，你的流量订购成功,业务将在" + orderDto.getEffDate() + "生效");
						first.setValue(tips_msg);
						data.setFirst(first);
						keyword1.setValue(rst.getGoodsName());
						MessageObject keyword2 = new MessageObject();
						keyword2.setValue(userDto.getPhoenNo());
						MessageObject keyword3 = new MessageObject();
						keyword3.setValue(sdf.format(new Date()));
						MessageObject keyword4 = new MessageObject();
						// keyword4.setValue(
						// String.format("成功,你是第%s名办理用户,订购序号：%s", orderDto.getSeqIndex(),
						// orderDto.getSeqIndex()));
						keyword4.setValue(String.format("成功,你是第%s名办理用户", orderDto.getSeqIndex()));
						MessageObject remark = new MessageObject();
						remark.setValue("点击详情，了解更多！");
						data.setKeyword1(keyword1);
						data.setKeyword2(keyword2);
						data.setKeyword3(keyword3);
						data.setKeyword4(keyword4);
						data.setRemark(remark);
						template.setData(data);
						template.setTouser(userDto.getOpenId());
						template.setUrl("http://wx.10086.cn/sichuan/wxzq/llzq/index.html");
						logger.info("send template message openId:" + userDto.getOpenId());
						// System.out.println(userDto.getOpenId());
						// template.setTouser("ovCZJuPE45DnZGGrFPKzIkGws3xE");
						template.setTemplate_id(TemplateId.OP_SUCCESS);
						opService.templateMessage(template);
					}
					orderNewsService.addOrderNews(userDto.getPhoenNo(), getPartId(userDto.getPhoenNo()));
				}
				return new BaseMessage(200, true, "成功");
			} else {
				return new BaseMessage(400, true, "订购失败");
			}
		} catch (Exception e) {
			logger.error("goodsController:" + e.getMessage() + e.getStackTrace());
			logger.error(JSONUtils.toJson(order));
			logger.error(JSONUtils.toJson(orderDto));
			orderLog.info(JSONUtils.toJson(order));
			try {
				orderService.timeoutOrder(order, postTime, sdt.format(new Date()));
			} catch (Exception ex) {
				logger.error("timeoutOrder:" + ex.getMessage() + ex.getStackTrace());
			}
			return new BaseMessage(400, true, "订购失败");
		}
	}

	@ApiOperation(value = "获取火爆单品", notes = "获取火爆单品")
	@RequestMapping(value = "/getHotGoodsCategory", method = RequestMethod.GET)
	public ResponseMessage<List<HotGoodsCategoryOutputDto>> getHotGoodsCategory(int categoryId, int records) {
		String userId = "";
		List<HotGoodsCategoryOutputDto> dto = goodsInfoService.getHotGoodsCategory(categoryId, records);
		return new ResponseMessage<List<HotGoodsCategoryOutputDto>>(200, true, "成功", dto);
	}

	@ApiOperation(value = "获取订单列表", notes = "获取订单列表")
	@RequestMapping(value = "/getOrderList", method = RequestMethod.GET)
	public ResponseMessage<List<OrderItemListOutputDto>> getOrderList(HttpServletRequest request, int pageIndex,
			int pageSize) {
		UserInfoDto userDto = null;

		MessageDto dtos = validateToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new ResponseMessage<List<OrderItemListOutputDto>>(dtos.getStatus(), true, dtos.getMessage());
		}

		List<OrderItemListOutputDto> dto = orderService.getOrderItemList(userDto.getUserId(), pageIndex, pageSize,
				getPartId(userDto.getPhoenNo()));
		return new ResponseMessage<List<OrderItemListOutputDto>>(200, true, "成功", dto);
	}

	@ApiOperation(value = "获取消息列表", notes = "获取消息列表")
	@RequestMapping(value = "/getNewsList", method = RequestMethod.GET)
	public ResponseMessage<List<OrderNewListOutputDto>> getNewsList(HttpServletRequest request, int pageIndex,
			int pageSize) {
		UserInfoDto userDto = null;

		MessageDto dtos = validateToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new ResponseMessage<List<OrderNewListOutputDto>>(dtos.getStatus(), true, dtos.getMessage());
		}

		List<OrderNewListOutputDto> dto = orderService.getOrderNewsList(userDto.getUserId(), pageIndex, pageSize,
				getPartId(userDto.getPhoenNo()));
		return new ResponseMessage<List<OrderNewListOutputDto>>(200, true, "成功", dto);
	}

	@ApiOperation(value = "获取消息详情", notes = "获取消息详情")
	@RequestMapping(value = "/getNewItem", method = RequestMethod.GET)
	public ResponseMessage<OrderNewItemOutputDto> getNewsItem(HttpServletRequest request, long itemId) {
		UserInfoDto userDto = null; // new UserInfoDto();
		MessageDto dtos = validateToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new ResponseMessage<OrderNewItemOutputDto>(dtos.getStatus(), true, dtos.getMessage());
		}
		OrderNewItemOutputDto dto = orderService.getNewsItem(itemId, getPartId(userDto.getPhoenNo()));
		return new ResponseMessage<OrderNewItemOutputDto>(200, true, "成功", dto);
	}

	@ApiOperation(value = "获取js签名", notes = "获取js签名")
	@RequestMapping(value = "/getJsSignature", method = RequestMethod.POST)
	public ResponseMessage<JsSignatureDto> getJsSignature(HttpServletRequest request,
			@RequestBody SignatureUrlInputDto input) throws WechatException {
		try {
			JsSignatureDto dto = opService.getJsSignature(input.getUrl());
			return new ResponseMessage<JsSignatureDto>(200, true, "数据获取成功", dto);
		} catch (Exception ex) {
			throw new WechatException(ex.getMessage());
		}
	}

	@ApiOperation(value = "重置消息记录", notes = "重置消息记录")
	@RequestMapping(value = "/restOrderNews", method = RequestMethod.POST)
	public BaseMessage restOrderNews(HttpServletRequest request) {

		UserInfoDto user = new UserInfoDto();
		MessageDto dtos = validateToken(request);
		user = dtos.getUserInfo();
		if (user == null) {
			return new BaseMessage(dtos.getStatus(), true, dtos.getMessage());
		}
		orderNewsService.updateOrderNews(user.getPhoenNo(), getPartId(user.getPhoenNo()));

		return new BaseMessage(200, true, "成功");
	}

	@ApiOperation(value = "获取消息记录数", notes = "获取消息记录数")
	@RequestMapping(value = "/getOrderNewsCount", method = RequestMethod.GET)
	public ResponseMessage<GoodsCountOutputDto> getOrderNewsCount(HttpServletRequest request) {

		UserInfoDto user = new UserInfoDto();
		MessageDto dtos = validateToken(request);
		user = dtos.getUserInfo();
		if (user == null) {
			return new ResponseMessage<GoodsCountOutputDto>(dtos.getStatus(), true, dtos.getMessage());
		}
		GoodsCountOutputDto dto = orderNewsService.getOrderNews(user.getPhoenNo(), getPartId(user.getPhoenNo()));

		return new ResponseMessage<GoodsCountOutputDto>(200, true, "成功", dto);
	}

	@ApiOperation(value = "获取用户在线客服地址", notes = "获取用户在线客服地址")
	@RequestMapping(value = "/getCmccUrl", method = RequestMethod.GET)
	public BaseMessage getCmccUrl(HttpServletRequest request) throws WechatException {
		UserInfoDto userDto = null; // new UserInfoDto();
		MessageDto dtos = validateToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new BaseMessage(dtos.getStatus(), true, dtos.getMessage());
		}
		try {
			String key = String.format(UrlParams.cmcc, userDto.getPhoenNo());
			return new BaseMessage(200, true, config.getCmccUrl() + CmccDesUtils.encode(key));
			// return new BaseMessage(200, true,
			// "http://wx.10086.cn/website/customerService");
		} catch (Exception ex) {
			throw new WechatException(ex.getMessage());
		}
	}

	@ApiOperation(value = "订单补偿", notes = "订单补偿")
	@RequestMapping(value = "/tccOrder", method = RequestMethod.POST)
	public BaseMessage tccOrder(HttpServletRequest request, String param) throws WechatException {
		// UserInfoDto userDto = null; // new UserInfoDto();
		// MessageDto dtos = validateToken(request);
		// userDto = dtos.getUserInfo();
		// if (userDto == null) {
		// return new BaseMessage(dtos.getStatus(), true, dtos.getMessage());
		// }
		if (StringUtils.isEmpty(param))
			return null;

		try {
			OrderInfo info = JSONUtils.toBean(param, OrderInfo.class);
			// String key = String.format(UrlParams.cmcc, userDto.getPhoenNo());
			// return new BaseMessage(200, true, config.getCmccUrl() +
			// CmccDesUtils.encode(key));
			if (info != null) {
				orderService.TccFailOrder(info);
				return new BaseMessage(200, true, "");
			}
			return new BaseMessage(400, true, "什么都没有");
			// return new BaseMessage(200, true,
			// "http://wx.10086.cn/website/customerService");
		} catch (Exception ex) {
			throw new WechatException(ex.getMessage());
		}
	}

	@ApiOperation(value = "获取用户流量信息", notes = "获取用户流量信息")
	@RequestMapping(value = "/getFlowInfo", method = RequestMethod.GET)
	public ResponseMessage<UserFlowInfoOutputDto> getFlowInfo(HttpServletRequest request) {
		UserInfoDto userDto = null;
		// String code = request.getHeader(tokenHeader);
		// if (StringUtils.isEmpty(code)) {
		// return new ResponseMessage<UserFlowInfoOutputDto>(501, false, "非法请求", null);
		// }
		MessageDto dtos = validateToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new ResponseMessage<UserFlowInfoOutputDto>(dtos.getStatus(), true, dtos.getMessage());
		}
		RestResponseOutputDto<OBFreeQryOutDataOutputDto> flowDto = webRestService.sOBFreeQry(userDto.getPhoenNo());
		double flow = 0.0, useFlow = 0.0, snUsedFlow = 0.0, gnUsedFlow = 0.0, gnTotalFlow = 0.0, snTotalFlow = 0.0;
		if (flowDto.getOutData() != null) {
			OBFreeQryOutDataOutputDto freeDtos = flowDto.getOutData();
			GprsInfoOutputDto itemDto = null;
			for (int index = 0; index <= freeDtos.getGPRS_INFO().size() - 1; index++) {
				itemDto = freeDtos.getGPRS_INFO().get(index);
				flow += itemDto.getSUM();
				useFlow += itemDto.getUSED();
				if (itemDto.getNAME().contains("国内")) {
					gnUsedFlow += itemDto.getUSED();
					gnTotalFlow += itemDto.getSUM();
				} else {
					snUsedFlow += itemDto.getUSED();
					snTotalFlow += itemDto.getSUM();
				}
			}
		}
		UserFlowInfoOutputDto dto = new UserFlowInfoOutputDto();
		dto.setFlow(flow);
		if (flow <= 0) {
			dto.setFlowPercent(0);
		} else {
			dto.setFlowPercent((flow - useFlow) / flow);
		}
		dto.setUseFlow(useFlow);
		dto.setGnUsedFlow(gnUsedFlow);
		dto.setSnUsedFlow(snUsedFlow);
		dto.setSnTotalFlow(snTotalFlow);
		dto.setGnTotalFlow(gnTotalFlow);
		dto.setHeadImage(userDto.getHeadImg());

		RestResponseOutputDto<SPFeeQueryOutputDto> moneyDto = webRestService.sPFeeQuery(userDto.getPhoenNo());

		if (moneyDto != null) {
			if (moneyDto.getOutData() != null) {
				double money = moneyDto.getOutData().getPREPAY_FEE() / 100.0;
				double un_bill = moneyDto.getOutData().getUNBILL_FEE() / 100.0;
				dto.setMoney(money);
				dto.setUn_bill_fee(un_bill);
			}
		} else {
			dto.setMoney(0.00);
		}

		dto.setPhoneNo(userDto.getPhoenNo());
		return new ResponseMessage<UserFlowInfoOutputDto>(200, true, "成功", dto);
	}

	@ApiOperation(value = "获取用户信息", notes = "获取用户信息")
	@RequestMapping(value = "/getUserInfo", method = RequestMethod.GET)
	public ResponseMessage<UserInfoOutputDto> getUserInfo(HttpServletRequest request) {
		UserInfoOutputDto userDto = new UserInfoOutputDto();
		UserInfoDto user = new UserInfoDto();
		MessageDto dtos = validateToken(request);
		user = dtos.getUserInfo();
		if (user == null) {
			return new ResponseMessage<UserInfoOutputDto>(dtos.getStatus(), true, dtos.getMessage());
		}
		userDto.setHeadImg(user.getHeadImg());
		userDto.setPhoenNo(user.getPhoenNo());
		userDto.setUserName(user.getUserName());
		BrowseCountOutput dto = browseRecordsService.getBrowseCount(user.getUserId(), getPartId(userDto.getPhoenNo()));
		if (dto != null) {
			userDto.setBrowseCount(dto.getBrowseCount());
			userDto.setColCount(dto.getColCount());
		}
		// String token = request.getHeader(tokenHeader);
		// token = "user_test01";
		// UserInfoDto user = new UserInfoDto();
		// user.setHeadImg(
		// "http:\\/\\/wx.qlogo.cn\\/mmopen\\/NjkzhkuBicLRAwsaEiafU3LM0bD4fDN9ABjfkMQwEbh7FKE2blgoHGN5rRTW3RM\\neWI7Ud5ArCJ3xZbkZSbletTvQ\\/0");
		// user.setOpenId("ovCZJuPE45DnZGGrFPKzIkGws3x1");
		// user.setPhoenNo("13548074395");
		// user.setUserId("1354807435");
		// user.setUserName("135****4395");
		// userCacheService.setUser(token, user);
		// if (StringUtils.isEmpty(token)) {
		// return new ResponseMessage<UserInfoOutputDto>(502, true, "非法请求");
		// }
		// userDto = userCacheService.getUserInfo(token);
		//
		// if (userDto == null) {
		// return new ResponseMessage<UserInfoOutputDto>(500, true, "登录超时，请重新进入该页面");
		// }
		return new ResponseMessage<UserInfoOutputDto>(200, true, "成功", userDto);
	}

	@ApiOperation(value = "绑定手机号", notes = "绑定手机号")
	@RequestMapping(value = "/bindTelPhone", method = RequestMethod.POST)
	public ResponseMessage<BindUserInfoOutputDto> bindTelPhone(HttpServletRequest request,
			@RequestBody TelPhoneInputDto input) throws WechatException {
		// UserInfoDto userDto = new UserInfoDto();
		// String token = request.getHeader(tokenHeader);
		// if(StringUtils.isEmpty(token))
		// {
		// return new ApiResponseMsg(502, true, "非法请求");
		// }
		// userDto = userInfoService.getUser(token);

		try {
			UserPhone phone = userPhone.findByPhoneNo(input.getTelNum());
			if (phone == null)
				return new ResponseMessage<BindUserInfoOutputDto>(201, false, "抱歉没有获取到号码相关信息，请联系在线客服", null);
			else {
				UserInfoDto user = new UserInfoDto();
				user.setHeadImg("");
				// user.setOpenId(input.getTelNum());
				user.setUserName(input.getTelNum().replaceAll("(\\d{3})\\d{4}(\\w{4})", "$1****$2"));
				user.setPhoenNo(input.getTelNum());
				user.setUserId(input.getTelNum());

				String desOpenId = TokenUtils.encrypt(JSONUtils.toJson(user));// CryptoUtils.encodeSHA(user.getOpenId());
				// String shareId = ShareDesUtils.encode(user.getOpenId());
				String shareId = "";
				try {
					shareId = TokenUtils.encrypt(user.getPhoenNo());
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
				// 目前取微信生成的token作为用户缓存的key
				BindUserInfoOutputDto dto = new BindUserInfoOutputDto();
				dto.setShare(shareId);
				dto.setToken(desOpenId);
				// userCacheService.setUser(desOpenId, user);
				WechatUserInfo wechat = new WechatUserInfo();
				wechat.setHeadImg(user.getHeadImg());
				wechat.setOpenId(user.getOpenId());
				wechat.setPhoenNo(user.getPhoenNo());
				wechat.setUserId(user.getUserId());
				wechat.setUserName(user.getUserName());
				wechatUserInfo.save(wechat);
				return new ResponseMessage<BindUserInfoOutputDto>(200, true, "成功", dto);
			}
		} catch (Exception ex) {
			throw new WechatException(ex.getMessage());
		}
	}

	@ApiOperation(value = "获取分类订购次数", notes = "获取分类订购次数")
	@RequestMapping(value = "/getHotCategoryGoodsSaleCount", method = RequestMethod.GET)
	public ResponseMessage<List<HotCategoryGoodsOutputDto>> getHotCategoryGoodsSaleCount(HttpServletRequest request) {

		List<HotCategoryGoodsOutputDto> goods = goodsInfoService.getHotCategoryGoodsSaleCount();
		return new ResponseMessage<List<HotCategoryGoodsOutputDto>>(200, true, "成功", goods);
	}

	@SuppressWarnings("unused")
	private MessageDto validateToken(HttpServletRequest request) {
		MessageDto dto = validateBaseToken(request);
		if (dto.getUserInfo() == null)
			return dto;
		if (StringUtils.isEmpty(dto.getUserInfo().getPhoenNo())) {
			dto.setStatus(501);
			dto.setMessage("您暂未绑定手机号");
			dto.setUserInfo(null);
			return dto;
		}
		// dto.setUserInfo(userDto);
		return dto;
	}

	@SuppressWarnings("unused")
	private MessageDto validateBaseToken(HttpServletRequest request) {
		MessageDto dto = new MessageDto();
		String token = request.getHeader(tokenHeader);
		// token="26F86C8245045FC656E3EE6B156D54943748E844C3C5D44A3BAD935D7480A2BA367EB523C6D2AA9A5324786E7B430EBBA4293008D086D1A5195A711FEF265449CB8AE62671E8C798B45E23170B6C49C34F4A0082841EF23C55B4C6F15F7369EE1D7C4D92100EC878E164B7E86DDEA3231F9AC14645FCBEDE187CBF6CAB66B348EBE9504C79B320085EB8F968743B08CEC9110E33AD43BF4D883B8A41488B396B26DDB98C2EE3B1D22AAC3ACAFB5832BA6369BBCC75CFE133064F42038E5EE9CD31F2B3CCE9BF18FD6206A2FBC82E58E9AE876773DBC628B166E4FAAED359B5A5";
		// token="26F86C8245045FC656E3EE6B156D54943748E844C3C5D44A3BAD935D7480A2BA367EB523C6D2AA9A5324786E7B430EBBA4293008D086D1A5195A711FEF265449CB8AE62671E8C798B45E23170B6C49C34F4A0082841EF23C55B4C6F15F7369EE1D7C4D92100EC878E164B7E86DDEA3231F9AC14645FCBEDE187CBF6CAB66B348EBE9504C79B320085EB8F968743B08CEC9110E33AD43BF4D883B8A41488B396B26DDB98C2EE3B1D22AAC3ACAFB5832BA6369BBCC75CFE133064F42038E5EE9CD31F2B3CCE9BF18FD6206A2FBC82E58E9AE876773DBC628B166E4FAAED359B5A5";
		if (StringUtils.isEmpty(token)) {
			dto.setStatus(502);
			dto.setMessage("非法请求");
			return dto;
		}
		String user = "";
		try {
			user = TokenUtils.decrypt(token);
		} catch (Exception e) {

		}
		if (StringUtils.isEmpty(user)) {
			dto.setStatus(502);
			dto.setMessage("非法请求");
			return dto;
		}
		UserInfoDto userDto = JSONUtils.toBean(user, UserInfoDto.class);

		if (userDto == null) {
			dto.setStatus(503);
			dto.setMessage("登录超时，请重新进入该页面");
			return dto;

		}
		dto.setUserInfo(userDto);
		return dto;
	}

	private int getPartId(String phoneNo) {
		return Integer.valueOf(phoneNo.substring(10));
	}
}
