package com.bingosoft.web.filter;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.AbstractJsonpResponseBodyAdvice;

@ControllerAdvice("com.bingosoft.web.controller.GoodsV2Controller")
public class JsonpAdvice extends AbstractJsonpResponseBodyAdvice {
	public JsonpAdvice() {
		super("callback", "jsonp");
	}
}
