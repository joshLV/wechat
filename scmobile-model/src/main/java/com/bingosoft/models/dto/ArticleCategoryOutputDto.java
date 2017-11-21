package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class ArticleCategoryOutputDto {
  private int categoryId;
  private String categoryName;
  private String categoryDesc;
  private String tags;
}
