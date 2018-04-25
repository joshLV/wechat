package com.bingosoft.core.mycat.service;



import com.bingosoft.models.dto.UpUserGradeOutputDto;
import com.bingosoft.models.dto.UserBasicCurrentOutputDto;
import com.bingosoft.models.dto.UserCurGradeOutputDto;
import com.bingosoft.models.dto.UserGradeUpOutputDto;
import com.bingosoft.models.wenum.OrderGradeEnum;

public interface IUserBasicByPartService {
	
    public UserBasicCurrentOutputDto getUserBasicCur(String phoneNo,int partId);
	
	public void addUserBasic(String phoneNo,int part_id,int gradeId,String gradeName,String gradeImg);
	
	/**
	 * 普通累积更新
	 */
	public void updateUserBasic(String phoneNo,int part_id);
	
	/**
	 * 升级时更新
	 */
	public void updateUserBasicByUp(String phoneNo,int part_id,int gradeId,String gradeName,String gradeImg);
	
	/**
	 * 获取当前等级
	 * @param phoneNo
	 * @return
	 */
	public UserGradeUpOutputDto getCurGrade(String phoneNo,int partId);
	
	/**
	 * 是否升级
	 * @param phone
	 * @return
	 */
	public UpUserGradeOutputDto isUpGrade(String phone);
	
	/**
	 * 获取当前等级
	 * @param phoneNo
	 * @param partId
	 * @return
	 */
	public UserCurGradeOutputDto getUserCurGrade(String phoneNo, int partId);
}
