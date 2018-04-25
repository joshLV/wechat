package com.bingosoft.models.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;



import lombok.Data;

@Data
@Component
public class OrderConfig {
	@Value("${order.limit}")
	private double limit;

	@Value("${order.tips1}")
	private String tip1;

	@Value("${order.tips1}")
	private String tip2;
}
