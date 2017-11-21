package com.bingosoft.models.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OrderNewItemOutputDto {
	   
	   private String goodsName;
	   private String goodsDesc;
	   private double payFee;
	   private int orderStatus;
	   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
	   private Date orderTime;
	   private String payMethod;
}
