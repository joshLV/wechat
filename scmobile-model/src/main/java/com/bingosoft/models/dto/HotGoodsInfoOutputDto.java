package com.bingosoft.models.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class HotGoodsInfoOutputDto  implements Serializable{
  private int goodsId;
  private String goodsName;
  private int saleCount;
  private String goodsImg;
  private String linkUrl;
}
