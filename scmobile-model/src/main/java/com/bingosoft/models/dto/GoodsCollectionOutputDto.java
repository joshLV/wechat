package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class GoodsCollectionOutputDto {
  private int goodsId;
  private int categoryId;
  private String goodsName;
  private double goodsPrice;
  private String goodsImage;
}
