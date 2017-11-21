package com.bingosoft.models.dto;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class ArticleInfoItemOutputDto {
  private String articleTitle;
  private String articleContent;
  private String articleAuthor;
  private String articleSource;
  private String imageUrl;
  private String keywords;
  private String tags;
  private int articleType;
  private int clickCount;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone="GMT+8")
  private Date createTime; 
}
