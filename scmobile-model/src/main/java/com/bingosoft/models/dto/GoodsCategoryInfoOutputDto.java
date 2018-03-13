package com.bingosoft.models.dto;

import java.io.Serializable;
import java.util.List;

import lombok.Data;

@Data
public class GoodsCategoryInfoOutputDto implements Serializable {
	private int categoryId;
	private String categoryName;
	private String categoryDesc;
	private String showStyle;
	private String imageUrl;
	private int saleCount;
	private List<GoodsInfoOutputDto> goodsList;
}
