package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class HotGoodsOutputDto {
   private int goodsId;
   private String goodsName;
   private int saleCount;
   private String imgUrl;
}
