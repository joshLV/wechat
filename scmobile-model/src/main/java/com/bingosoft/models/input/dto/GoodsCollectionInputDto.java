package com.bingosoft.models.input.dto;

import lombok.Data;

@Data
public class GoodsCollectionInputDto {
 private String userId;
 private long goodsId;
 private int categoryId;
 private String goodsName;
 private double goodsPrice;
 private String goodsImage;
 private int partId;
}
