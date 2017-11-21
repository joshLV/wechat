package com.bingosoft.models.entities;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "activity_collection")
public class GoodsInfo {
  @Id
  @Column(name = "goods_id")
  private long goodsId;
  
  @Column(name="category_id")
  private int categoryId;
  
  @Column(name="goods_name")
  private String goodsName;
  
  @Column(name="goods_title")
  private String goodsTitle;
  
  @Column(name="title_style")
  private String titleStyle;
  
  @Column(name="goods_image")
  private String goodsImage;
  
  @Column(name="goods_desc")
  private String goodsDesc;
  
  @Column(name="goods_note")
  private String goodsNote;
  
  @Column(name="keywords")
  private String keywords;
  
  @Column(name="goods_price")
  private double goodsPrice;
  
  @Column(name="promote_price")
  private double promotePrice;
  
  @Column(name="promote_start")
  private Date promoteStart;
  
  @Column(name="promote_end")
  private Date promoteEnd;
  
  @Column(name="is_promote")
  private int isPromote;
  
  @Column(name="goods_number")
  private int goodsNumber;
  
  @Column(name="sale_count")
  private int saleCount;
  
  @Column(name="is_hot")
  private int isHot;
  
  @Column(name="is_new")
  private int isNew;
  
  @Column(name="sort_order")
  private int sortOrder;
  
  @Column(name="goods_status")
  private int goodsStatus;
  
  @Column(name="create_time")
  private Date createTime;
}
