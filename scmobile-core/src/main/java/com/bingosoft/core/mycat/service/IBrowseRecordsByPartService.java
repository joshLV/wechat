package com.bingosoft.core.mycat.service;

import java.util.List;

import com.bingosoft.models.dto.BrowseCountOutput;
import com.bingosoft.models.dto.BrowseRecords2MonthOutputDto;
import com.bingosoft.models.dto.BrowseRecordsOutputDto;
import com.bingosoft.models.input.dto.BrowseRecordInputDto;

public interface IBrowseRecordsByPartService {
	public List<BrowseRecordsOutputDto> getBrowseRecords2(String userId,int pageIndex, int pageSize, int partId);

	public List<BrowseRecords2MonthOutputDto> getBrowseRecords(String userId, int pageIndex, int pageSize, int partId);

	public void addBrowse(BrowseRecordInputDto input);

	public BrowseCountOutput getBrowseCount(String userId, int partId);
}
