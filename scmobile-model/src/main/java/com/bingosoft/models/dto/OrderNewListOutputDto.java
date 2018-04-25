package com.bingosoft.models.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OrderNewListOutputDto {

	   private String itemId;
	   private String goodsName;
	   private double goodsPrice;
	   private double payFee;
	   private int orderStatus;
	   @JsonFormat(pattern = "MM-dd HH:mm",timezone="GMT+8")
	   private Date createTime;
	   private int totalAmount;
	   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	   private Date effTime;
	   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	   private Date expTime;
	   private String timeTitle;
	   private String effectiveTime;
	   private String orderId;
}
