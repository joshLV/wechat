package com.bingosoft.models.dto;

import java.util.List;

import lombok.Data;

@Data
public class BrowseRecords2MonthOutputDto {
   private String month;
   private List<BrowseRecordsOutputDto> items;
}
