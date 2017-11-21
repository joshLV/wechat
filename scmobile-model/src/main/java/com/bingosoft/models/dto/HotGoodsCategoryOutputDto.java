package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class HotGoodsCategoryOutputDto {
	private int categoryId;
	private String categoryName;
	private String categoryDesc;
	private String showStyle;
	private String imageUrl;
	private int saleCount;
}
