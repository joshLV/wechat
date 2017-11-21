package com.bingosoft.models.dto;

import java.util.Date;

import lombok.Data;

@Data
public class GoodsCollectionInputDto {
   private String userId;
   private int goodsId;
   private Date createTime;
}
