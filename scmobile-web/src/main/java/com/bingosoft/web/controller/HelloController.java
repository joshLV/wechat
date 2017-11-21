package com.bingosoft.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.bingosoft.core.mycat.service.IOrderByPartService;
import com.bingosoft.models.dto.OrderMycatOutputDto;
import com.bingosoft.models.dto.OrderNewListOutputDto;
import com.bingosoft.models.msg.ResponseMessage;

import io.swagger.annotations.ApiOperation;

@RestController
public class HelloController {
	
	@Autowired
	IOrderByPartService orderService;
	
	@RequestMapping(value = "/Hello", method = RequestMethod.GET)
	public String Hello() {
		return "hello msg";
	}
	
	@ApiOperation(value = "获取消息列表", notes = "获取消息列表")
	@RequestMapping(value = "/getNewList", method = RequestMethod.GET)
	public ResponseMessage<List<OrderNewListOutputDto>> getNewList(int pageIndex,int pageSize,int partId) {
		String userId = "";
		List<OrderNewListOutputDto> dto = orderService.getOrderNewsList(userId, pageIndex, pageSize, partId);
		return new ResponseMessage<List<OrderNewListOutputDto>>(200, true, "成功", dto);
	}
}
