package com.bingosoft.persist.mycat.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.bingosoft.models.dto.UserBasicCurrentOutputDto;

public interface IUserBasicByPartMapper {
	@Select("select grade_name gradeName,grade_image gradeImage,grade_id gradeId,user_points userPoints from user_basic where phone_no=#{phoneNo} and part_id=#{partId}")
    public UserBasicCurrentOutputDto getUserBasicCur(@Param("phoneNo")String phoneNo,@Param("partId")int partId);
	
	@Insert("insert into user_basic(phone_no,grade_id,grade_name,grade_image,part_id) VALUES(#{phoneNo},#{gradeId},#{gradeName},#{gradeImg},#{partId})")
	public void addUserBasic(@Param("phoneNo")String phoneNo,@Param("partId")int part_id,@Param("gradeId")int gradeId,@Param("gradeName")String gradeName,@Param("gradeImg")String gradeImg);
	
	/**
	 * 普通累积更新
	 */
	@Update("UPDATE user_basic set user_points=user_points+1,buy_total=buy_total+1,update_time=CURRENT_TIMESTAMP() where phone_no=#{phoneNo} and part_id=#{partId}")
	public void updateUserBasic(@Param("phoneNo")String phoneNo,@Param("partId")int part_id);
	
	/**
	 * 升级时更新
	 */
	@Update("UPDATE user_basic set user_points=user_points+1,buy_total=buy_total+1,update_time=CURRENT_TIMESTAMP(),up_time=CURRENT_TIMESTAMP(),grade_id=#{gradeId},grade_name=#{gradeName},grade_image=#{gradeImg} where phone_no=#{phoneNo} and part_id=#{partId}")
	public void updateUserBasicByUp(@Param("phoneNo")String phoneNo,@Param("partId")int part_id,@Param("gradeId")int gradeId,@Param("gradeName")String gradeName,@Param("gradeImg")String gradeImg);
}
