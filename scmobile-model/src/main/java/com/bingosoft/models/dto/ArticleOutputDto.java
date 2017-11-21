package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class ArticleOutputDto {
   private int articleId;
   private int articleType;
   private String imageUrl;
   private String articleTitle;
   private String articleNote;
   private String tags;
   private int clickCount;
}
