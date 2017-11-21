package com.bingosoft.core.mysql.service;

import java.util.List;

import com.bingosoft.models.dto.BrowseCountOutput;
import com.bingosoft.models.dto.BrowseRecords2MonthOutputDto;
import com.bingosoft.models.dto.BrowseRecordsOutputDto;

public interface IBrowseRecordsService {
	public List<BrowseRecordsOutputDto> getBrowseRecords2(String userId);
	
	public List<BrowseRecords2MonthOutputDto> getBrowseRecords(String userId,int pageIndex,int pageSize);
	
	public void addBrowse(String userId,long goodsId);
	
	public BrowseCountOutput getBrowseCount(String userId);
}
