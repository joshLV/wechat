package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class GoodsCategoryOutputDto {
   private int categoryId;
   private String categoryName;
   private String categoryDesc;
   private String showStyle;
   private String imageUrl;
}
