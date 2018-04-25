package com.bingosoft.models.dto;

import java.io.Serializable;

import lombok.Data;

@Data
public class GoodsInfosOutputDto  implements Serializable{
   private int goodsId;
   private String headPic;
   private String goodsName;
   private String goodsTitle;
   private String goodsDesc;
   private double goodsPrice;
   private int saleCount;
   /**
    * 包含内容
    */
   private String goodsContent;
   /**
    * 温馨提示
    */
   private String goodsNote;
   
   private int cateId;
   
   private String cateName;
   
   private String cateHeadPic;
   
   private String cateDesc;
   
   private String effTime;
   private String goodsAlias;
   
}
