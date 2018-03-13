package com.bingosoft.models.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class GoodsCategoryOutputDto implements Serializable{
   private int categoryId;
   private String categoryName;
   private String categoryDesc;
   private String showStyle;
   private String imageUrl;
}
