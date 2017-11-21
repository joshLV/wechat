package com.bingosoft.models.dto;

import java.util.Date;

import lombok.Data;

@Data
public class BrowseRecordsOutputDto {
  private int goodsId;
  private int categoryId;
  private String goodsName;
  private String goodsImage;
  private double goodsPrice;
  private Date updateTime;
  private String month;
  
}
