package com.bingosoft.core.mongodb.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bingosoft.core.mongodb.service.IWebLogService;
import com.bingosoft.models.mongodb.entities.WebLog;
import com.bingosoft.persist.mongodb.dao.IWebLogRepository;

@Service
public class WebLogServiceImpl implements IWebLogService{

	@Autowired
	IWebLogRepository webLogRepository;
	
	@Override
	public void writeWebLog(WebLog log) {
		// TODO Auto-generated method stub
		webLogRepository.save(log);
	}

}
