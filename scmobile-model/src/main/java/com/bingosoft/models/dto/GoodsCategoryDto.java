package com.bingosoft.models.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class GoodsCategoryDto  implements Serializable{
  private int cateId;
  private String icon;
  private String cateName;
  /**
   * 描述
   */
  private String cateDesc;
  private int saleCount;
  private String linkUrl;
  
}
