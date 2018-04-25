package com.bingosoft.models.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class GoodsInfoOutputDto implements  Serializable{
	private int goodsId;
	private String goodsName;
	private double goodsPrice;
	private int isRecommend;
	private String goodsDesc;
	private String goodsNote;
	private String goodsContent;
	private String effectiveTime;
	private int saleCount;
	private String goodsAlias;
	private String goodsTitle;
}
