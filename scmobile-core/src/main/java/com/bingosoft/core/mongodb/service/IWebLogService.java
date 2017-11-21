package com.bingosoft.core.mongodb.service;

import org.springframework.scheduling.annotation.Async;

import com.bingosoft.models.mongodb.entities.WebLog;

public interface IWebLogService {
	@Async
	public void writeWebLog(WebLog log);
}
