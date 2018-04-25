package com.bingosoft.models.dto;

import java.util.List;

import lombok.Data;

@Data
public class UserCurGradeOutputDto {
   private int gradeId;
   private int surplusGrade;
   private List<UserGradeOutputDto> gradeList;
}
