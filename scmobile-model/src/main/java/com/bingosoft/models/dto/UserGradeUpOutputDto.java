package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class UserGradeUpOutputDto {
   private String curGradeName;
   private int curGradeVal;
   private String curGradeImg;
   private String nextGradeName;
   private int nextGradeVal;
   private String nextGradeImg;
   private String phone;
   private String headImg;
   private String nickName;
}
