package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class UserInfoOutputDto {
	private String userName;
	private String headImg;
	private String phoneNo;
	private int colCount;
	private int browseCount;
	private String gradeName;
	private String gradeImg;
}
