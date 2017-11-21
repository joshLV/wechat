package com.bingosoft.models.dto;

import java.util.Date;

import lombok.Data;

@Data
public class GoodsInfoOutputDto {
	private int goodsId;
	private String goodsName;
	private double goodsPrice;
	private int isRecommend;
	private String goodsDesc;
	private String goodsNote;
}
