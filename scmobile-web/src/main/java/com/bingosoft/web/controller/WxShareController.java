package com.bingosoft.web.controller;

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

import com.bingosoft.core.mycat.service.IWxShareService;
import com.bingosoft.models.dto.GameShareRecordInputDto;
import com.bingosoft.models.dto.GoodsItemInfoAndProdOutputDto;
import com.bingosoft.models.dto.MessageDto;
import com.bingosoft.models.dto.MessageObject;
import com.bingosoft.models.dto.OrderInfo;
import com.bingosoft.models.dto.OrderItem;
import com.bingosoft.models.dto.OrderShortAddModeOutputDto;
import com.bingosoft.models.dto.SahreInputDto;
import com.bingosoft.models.dto.TemplateData;
import com.bingosoft.models.dto.TemplateMessageInputDto;
import com.bingosoft.models.dto.UserInfoDto;
import com.bingosoft.models.input.dto.OrderInputDto;
import com.bingosoft.models.msg.BaseMessage;
import com.bingosoft.models.prefix.TemplateId;
import com.bingosoft.models.prefix.TemplateTipsMsg;
import com.bingosoft.utils.IdGenerator;
import com.bingosoft.utils.JSONUtils;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "流量专区", description = "微信分享")
@RestController
@RequestMapping("/wx.share/api/v1")
public class WxShareController extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(WxShareController.class);

	@Autowired
	IWxShareService wxShareService;

	@ApiOperation(value = "微信分享获得次数", notes = "微信分享获得次数")
	@RequestMapping(value = "/game.share", method = RequestMethod.POST)
	public BaseMessage shareGoods(HttpServletRequest request, @RequestBody SahreInputDto input) {
		UserInfoDto userDto = null; // new UserInfoDto();
		// String msg=TemplateTipsMsg.getTips(input.getGoodsId());
		MessageDto dtos = validateToken(request);
		userDto = dtos.getUserInfo();
		if (userDto == null) {
			return new BaseMessage(dtos.getStatus(), true, dtos.getMessage());
		}
		try {
			GameShareRecordInputDto shareInput = new GameShareRecordInputDto();
			shareInput.setPartId(getPartId(userDto.getPhoenNo()));
			shareInput.setRuleId(input.getRuleId());
			shareInput.setShareModuleId(input.getShareModuleId());
			shareInput.setSharePage(input.getSharePage());
			shareInput.setShareNote("");
			shareInput.setShareStatus(1);
			shareInput.setSharePhone(userDto.getPhoenNo());
			shareInput.setOrderId(input.getOrderId());
			shareInput.setShareId(Long.parseLong(IdGenerator.INSTANCE.nextId()));
			String chance=wxShareService.wxShare(shareInput);
			if (!chance.equals("0")) {
				return new BaseMessage(200, true, chance);
			} else {
				return new BaseMessage(200, false, "分享失败");
			}
		} catch (Exception ex) {
			logger.error("shareGoods:" + ex.getMessage() + ex.getStackTrace());
		}
		return new BaseMessage(400, true, "分享出问题了");
	}

}
