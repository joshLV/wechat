package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class UpUserGradeOutputDto {
	private String gradeName;
	private String gradeImg;
	private int gradeId;
	private boolean isUp;
}
