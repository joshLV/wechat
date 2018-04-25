package com.bingosoft.models.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class OrderItemListOutputDto {
   private long orderId;
   private String itemId;
   private int goodsId;
   private String goodsName;
   private String goodsImage;
   private int goodsCount;
   private double goodsPrice;
   private double payFee;
   private int orderStatus;
   @JsonFormat(pattern = "yy-MM-dd HH:mm",timezone="GMT+8")
   private Date createTime;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
   private Date effTime;
   @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
   private Date expTime;
   private int totalAmount;
   private String prodStatus;
   private String effectiveTime;
   private String cateName;
   private String cateImg;
}
