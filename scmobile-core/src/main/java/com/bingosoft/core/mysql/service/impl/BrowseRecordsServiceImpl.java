package com.bingosoft.core.mysql.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bingosoft.core.mysql.service.IBrowseRecordsService;
import com.bingosoft.models.dto.BrowseCountOutput;
import com.bingosoft.models.dto.BrowseRecords2MonthOutputDto;
import com.bingosoft.models.dto.BrowseRecordsOutputDto;
import com.bingosoft.models.dto.DataRows;
import com.bingosoft.persist.mysql.dao.IBrowseRecordsMapper;

@Service
public class BrowseRecordsServiceImpl implements IBrowseRecordsService {

	@Autowired
	IBrowseRecordsMapper browseRecordsMapper;

	Date d = new Date();

	@Override
	public List<BrowseRecords2MonthOutputDto> getBrowseRecords(String userId, int pageIndex, int pageSize) {
		// TODO Auto-generated method stub
		int pageEnd = pageIndex * pageSize;
		List<BrowseRecordsOutputDto> inputDto = browseRecordsMapper.getBrowseRecords(userId, pageEnd - pageSize,
				pageEnd);
		return dealBrowseRecords(inputDto);
	}

	private List<BrowseRecords2MonthOutputDto> dealBrowseRecords(List<BrowseRecordsOutputDto> inputDto) {
		BrowseRecordsOutputDto dto = null;
		List<String> month = new ArrayList<String>();
		for (int i = 0; i < inputDto.size(); i++) {
			dto = inputDto.get(i);
			String m = monthConvert2Text(dto.getUpdateTime());
			dto.setMonth(m);
			if (!month.contains(m)) {
				month.add(m);
			}
		}
		List<BrowseRecords2MonthOutputDto> out = new ArrayList<BrowseRecords2MonthOutputDto>();
		for (int i = 0; i < month.size(); i++) {
			final String mon = month.get(i);
			BrowseRecords2MonthOutputDto iDto = new BrowseRecords2MonthOutputDto();
			iDto.setMonth(mon);

			Predicate<BrowseRecordsOutputDto> contain1 = n -> n.getMonth().contains(mon);
			iDto.setItems(inputDto.stream().filter(contain1).collect(Collectors.toList()));
			out.add(iDto);
		}
		return out;
	}

	private String monthConvert2Text(Date sourceDate) {
		if (sourceDate.getMonth() == d.getMonth()) {
			return "当月";
		} else {
			return String.valueOf(sourceDate.getMonth() + 1) + "月";
		}
	}

	@Override
	public List<BrowseRecordsOutputDto> getBrowseRecords2(String userId) {
		// TODO Auto-generated method stub
		return browseRecordsMapper.getBrowseRecords(userId, 0, 2);
	}

	@Override
	public void addBrowse(String userId, long goodsId) {
		// TODO Auto-generated method stub
		DataRows rows = browseRecordsMapper.isExistsBrowse(userId, goodsId);
		if (rows.getDataRow() >= 1) {
			browseRecordsMapper.updateBrowse(userId, goodsId, new Date());
		} else {
			browseRecordsMapper.AddBrowse(userId, goodsId, new Date());
		}

	}

	@Override
	public BrowseCountOutput getBrowseCount(String userId) {
		// TODO Auto-generated method stub
		return browseRecordsMapper.getBrowseCount(userId);
	}

}
