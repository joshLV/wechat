package com.bingosoft.models.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class ApiUrlConfig {

	@Value("${api.csf}")
	private String csfUrl;
	
	@Value("${api.rest}")
	private String restUrl;
}
