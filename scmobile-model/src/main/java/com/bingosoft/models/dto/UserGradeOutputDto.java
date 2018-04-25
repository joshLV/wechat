package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class UserGradeOutputDto {
  private int gradeId;
  private String gradeName;
  private int startValue;
  private int endValue;
  private String gradeImg;
}
