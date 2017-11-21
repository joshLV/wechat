package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class ArticleInfoOutputDto {
	private int articleId;
	private String articleTitle;
	private String tags;
}
