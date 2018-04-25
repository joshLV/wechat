package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class UserBasicOutputDto {
   private String phoneNo;
   private String gradeName;
   private String gradeImage;
   private int gradeId;
   private int userPoints;
   private int buyTotal;
   private int partId;
}
