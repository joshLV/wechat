package com.bingosoft.models.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class HotCategoryGoodsOutputDto implements  Serializable{
  private long categoryId;
  private int saleCount;
}
