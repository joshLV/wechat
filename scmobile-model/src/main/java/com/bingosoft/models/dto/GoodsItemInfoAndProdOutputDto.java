package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class GoodsItemInfoAndProdOutputDto {
	 private int categoryId;
	  private String goodsName;
	  private String goodsTitle;
	  private String goodsImage;
	  private String goodsDesc;
	  private String goodsNote;
	  private String keywords;
	  private double goodsPrice;
	  private String feeCode;
}
