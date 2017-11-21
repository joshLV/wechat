package com.bingosoft.models.dto;

import java.util.Date;

import lombok.Data;

@Data
public class OrderDetailOutputDto {
   private int goodsId;
   private String goodsName;
   private String goodsImage;
   private int goodsCount;
   private double goodsPrice;
   private String goodsDesc;
   private int totalAmount;
   private double payFee;
   private int orderStatus;
   private Date createTime;
}
