package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class UserBasicInfoOutputDto {
	private String gradeName;
	private String gradeImage;
	private int userPoints;
	private int buy_total;
}
