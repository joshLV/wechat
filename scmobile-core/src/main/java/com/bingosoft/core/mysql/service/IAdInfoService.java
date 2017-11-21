package com.bingosoft.core.mysql.service;

import java.util.List;

import com.bingosoft.models.dto.AdInfoOutputDto;

public interface IAdInfoService {
	List<AdInfoOutputDto> getAdList(int postionId);
}
