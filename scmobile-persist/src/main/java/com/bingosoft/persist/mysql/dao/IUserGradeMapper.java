package com.bingosoft.persist.mysql.dao;

import java.util.List;

import org.apache.ibatis.annotations.Select;

import com.bingosoft.models.dto.UserGradeOutputDto;

public interface IUserGradeMapper {

	@Select("SELECT grade_id gradeId,grade_name gradeName,grade_image gradeImg,start_value startValue,end_value endvalue FROM user_grade order by start_value")
	public List<UserGradeOutputDto> getUserGradeList();
}
