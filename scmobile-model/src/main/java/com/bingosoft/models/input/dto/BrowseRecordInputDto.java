package com.bingosoft.models.input.dto;

import java.util.Date;

import lombok.Data;

@Data
public class BrowseRecordInputDto {
  private String userId;
  private long goodsId;
  private Date updateTime;
  private long categoryId;
  private String goodsName;
  private String goodsImage;
  private double goodsPrice;
  private int partId;
  
}
