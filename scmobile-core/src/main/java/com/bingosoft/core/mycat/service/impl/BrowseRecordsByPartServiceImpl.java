package com.bingosoft.core.mycat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bingosoft.core.mycat.service.IBrowseRecordsByPartService;
import com.bingosoft.models.dto.BrowseCountOutput;
import com.bingosoft.models.dto.BrowseRecords2MonthOutputDto;
import com.bingosoft.models.dto.BrowseRecordsOutputDto;
import com.bingosoft.models.dto.DataRows;
import com.bingosoft.models.input.dto.BrowseRecordInputDto;
import com.bingosoft.persist.mycat.dao.IBrowseRecordsByPartMapper;

@Service
public class BrowseRecordsByPartServiceImpl implements IBrowseRecordsByPartService {

	@Autowired
	IBrowseRecordsByPartMapper browseRecordsMapper;

	Date d = new Date();

	@Override
	public List<BrowseRecordsOutputDto> getBrowseRecords2(String userId, int pageIndex, int pageSize, int partId) {
		// TODO Auto-generated method stub
		int pageEnd = pageIndex * pageSize;
		return browseRecordsMapper.getBrowseRecords(userId, pageEnd - pageSize, pageEnd, partId);
	}

	@Override
	public List<BrowseRecords2MonthOutputDto> getBrowseRecords(String userId, int pageIndex, int pageSize, int partId) {
		int pageEnd = pageIndex * pageSize;
		List<BrowseRecordsOutputDto> inputDto = browseRecordsMapper.getBrowseRecords(userId, pageEnd - pageSize,
				pageEnd, partId);
		return dealBrowseRecords(inputDto);
	}

	@Override
	public void addBrowse(BrowseRecordInputDto input) {
		// TODO Auto-generated method stub
		DataRows rows = browseRecordsMapper.isExistsBrowse(input.getUserId(), input.getGoodsId(), input.getPartId());
		if (rows.getDataRow() >= 1) {
			browseRecordsMapper.updateBrowse(input.getUserId(), input.getGoodsId(), input.getUpdateTime(),
					input.getPartId());
		} else {
			browseRecordsMapper.AddBrowse(input.getUserId(), input.getGoodsId(), input.getUpdateTime(),
					input.getCategoryId(), input.getGoodsName(), input.getGoodsImage(), input.getGoodsPrice(),
					input.getPartId());
		}
	}

	@Override
	public BrowseCountOutput getBrowseCount(String userId, int partId) {
		// TODO Auto-generated method stub
		return browseRecordsMapper.getBrowseCount(userId, partId);
	}

	private String monthConvert2Text(Date sourceDate) {
		if (sourceDate.getMonth() == d.getMonth()) {
			return "当月";
		} else {
			return String.valueOf(sourceDate.getMonth() + 1) + "月";
		}
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

}
