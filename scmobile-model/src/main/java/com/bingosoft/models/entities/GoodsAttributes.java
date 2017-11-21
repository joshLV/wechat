package com.bingosoft.models.entities;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name="goods_attributes")
public class GoodsAttributes {
	
   @Id
   @Column(name="attr_id")
   private int attrId;
   
   @Column(name="attr_name")
   private String attrName;
   
   @Column(name="attrDesc")
   private String attrDesc;
   
   @Column(name="attr_price")
   private double attrPrice;
   
   @Column(name="goods_id")
   private int goodsId;
   
   @Column(name="sort_order")
   private int sortOrder;
}
