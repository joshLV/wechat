package com.bingosoft.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="goods_category")
public class GoodsCategory {
	
	@Id
	@Column(name="category_id")
	private int categoryId;
	
	@Column(name="category_name")
	private String categoryName;
	
	@Column(name="category_desc")
	private String categoryDesc;
	
	@Column(name="show_style")
	private String showStyle;
	
	@Column(name="image_url")
	private String imageUrl;
	
	@Column(name="parent_id")
	private int parentId;
	
	@Column(name="sort_order")
	private int sortOrder;
	
	@Column(name="category_status")
	private int categoryStatus;

}
