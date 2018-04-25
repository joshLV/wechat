package com.bingosoft.models.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class GradeDefaultConfig {

	@Value("${GradeDefault.Name}")
	private String gradeName;
	
	@Value("${GradeDefault.Img}")
	private String gradeImg;
}
