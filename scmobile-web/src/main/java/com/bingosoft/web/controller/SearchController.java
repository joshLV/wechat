package com.bingosoft.web.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bingosoft.core.web.service.IWebRestService;
import com.bingosoft.models.dto.BrowseCountOutput;
import com.bingosoft.models.dto.MessageDto;
import com.bingosoft.models.dto.SearchShortAddModeInputDto;
import com.bingosoft.models.dto.UpdateMainChargesOutputDto;
import com.bingosoft.models.dto.UserInfoDto;
import com.bingosoft.models.dto.UserInfoOutputDto;
import com.bingosoft.models.msg.ResponseMessage;
import com.bingosoft.models.rest.dto.RestResponseOutputDto;
import com.bingosoft.models.rest.dto.ShortAddModeOutputDto;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value = "测试调用", description = "流量专区API测试调用")
@RestController
@RequestMapping("/search/api/v1")
public class SearchController {

	@Autowired
	IWebRestService webRestService;

	@ApiOperation(value = "资费代码验证", notes = "资费代码验证")
	@RequestMapping(value = "/sShortAddMode", method = RequestMethod.POST)
	public ResponseMessage<Object> ShortAddMode(HttpServletRequest request, SearchShortAddModeInputDto input) {
		if (StringUtils.isEmpty(input.getVidateCode())) {
			return new ResponseMessage<Object>(201, true, "不能乱来哟", null);
		}
		if (!input.getVidateCode().equals("ac8dbdb8-8d0f-4929-9ad9-2b325aad95f6"))
			return new ResponseMessage<Object>(202, true, "秘钥错误！", null);
		if (StringUtils.isEmpty(input.getPhoneNo()))
			return new ResponseMessage<Object>(202, true, "手机号都没得，怕是测不到哟！", null);
		if (StringUtils.isEmpty(input.getProdId()) || input.getProdId().length() <= 6)
			return new ResponseMessage<Object>(202, true, "资费代码都没得，怕是测不到哟！", null);
		if (0 == input.getType()) {
			if (input.getProdId().startsWith("AZ")) {
				String actId = input.getProdId().substring(0, 4);
				Object obj = webRestService.MarkActHandle(input.getPhoneNo(), actId, input.getProdId());
				return new ResponseMessage<Object>(200, true, "成功", obj);
			}
			RestResponseOutputDto<ShortAddModeOutputDto> dto = webRestService.sShortAddMode(input.getPhoneNo(),
					input.getProdId());
			return new ResponseMessage<Object>(200, true, "成功", dto);
		}else
		{
			RestResponseOutputDto<UpdateMainChargesOutputDto> dto=webRestService.updateMainCharges(input.getPhoneNo(),
					input.getProdId());
			return new ResponseMessage<Object>(200, true, "成功", dto);
		}

	}
}
