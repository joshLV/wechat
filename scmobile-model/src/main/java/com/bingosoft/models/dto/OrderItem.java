package com.bingosoft.models.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderItem {
	private long itemId;
	private String PROD_PRCID;
	private long orderId;
	private int partId;
	private long goodsId;
	private String goodsName;
	private String goodsImage;
	private int goodsCount;
	private double goodsPrice;
	private String goodsDesc;
	private double totalAmout;
	private double payFee;
	private int orderStatus;
	private Date createTime;
	private int categoryId;
}
