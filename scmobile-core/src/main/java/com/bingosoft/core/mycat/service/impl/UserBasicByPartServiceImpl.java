package com.bingosoft.core.mycat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.bingosoft.core.mycat.service.IUserBasicByPartService;
import com.bingosoft.core.mysql.service.IUserGradeService;
import com.bingosoft.models.config.GradeDefaultConfig;
import com.bingosoft.models.dto.UpUserGradeOutputDto;
import com.bingosoft.models.dto.UserBasicCurrentOutputDto;
import com.bingosoft.models.dto.UserCurGradeOutputDto;
import com.bingosoft.models.dto.UserGradeOutputDto;
import com.bingosoft.models.dto.UserGradeUpOutputDto;
import com.bingosoft.models.prefix.RedisKeyPrefix;
import com.bingosoft.models.wenum.OrderGradeEnum;
import com.bingosoft.persist.mycat.dao.IUserBasicByPartMapper;
import com.bingosoft.persist.redis.dao.IRedisService;
import com.bingosoft.utils.serialize.JsonUtils;

@Service
public class UserBasicByPartServiceImpl implements IUserBasicByPartService {

	@Autowired
	IRedisService redisService;

	@Autowired
	IUserBasicByPartMapper userBasicMapper;

	@Autowired
	IUserGradeService userGradeService;

	@Autowired
	GradeDefaultConfig gradeDefault;

	@Override
	public UserBasicCurrentOutputDto getUserBasicCur(String phoneNo, int partId) {
		// TODO Auto-generated method stub
		String userBasicKey = String.format(RedisKeyPrefix.User_Basic_Prefix, phoneNo);
		String cache = redisService.get(userBasicKey);
		UserBasicCurrentOutputDto output = null;
		if (StringUtils.isEmpty(cache)) {
			output = userBasicMapper.getUserBasicCur(phoneNo, partId);
			redisService.set(userBasicKey, JsonUtils.toJson(output), 10000);
		} else {
			output = JsonUtils.toBean(cache, UserBasicCurrentOutputDto.class);
		}
		return output;
	}

	@Override
	public void addUserBasic(String phoneNo, int part_id, int gradeId, String gradeName, String gradeImg) {
		// TODO Auto-generated method stub
		userBasicMapper.addUserBasic(phoneNo, part_id, gradeId, gradeName, gradeImg);
		String userBasicKey = String.format(RedisKeyPrefix.User_Basic_Prefix, phoneNo);
		redisService.del(userBasicKey);
	}

	@Override
	public void updateUserBasic(String phoneNo, int part_id) {
		// TODO Auto-generated method stub
		userBasicMapper.updateUserBasic(phoneNo, part_id);
	}

	@Override
	public void updateUserBasicByUp(String phoneNo, int part_id, int gradeId, String gradeName, String gradeImg) {
		// TODO Auto-generated method stub
		userBasicMapper.updateUserBasicByUp(phoneNo, part_id, gradeId, gradeName, gradeImg);
	}

	@Override
	public UserGradeUpOutputDto getCurGrade(String phoneNo, int partId) {
		// TODO Auto-generated method stub
		UserBasicCurrentOutputDto dto = getUserBasicCur(phoneNo, partId);
		UserGradeUpOutputDto userGrade = new UserGradeUpOutputDto();
		List<UserGradeOutputDto> userGradeList = userGradeService.getUserGradeList();
		if (dto != null) {
			userGrade.setCurGradeImg(dto.getGradeImage());
			userGrade.setCurGradeName(dto.getGradeName());
			userGrade.setCurGradeVal(dto.getUserPoints());
			if (userGradeList != null) {
				UserGradeOutputDto userGradeDto;
				for (int gradeInex = 0; gradeInex <= userGradeList.size() - 1; gradeInex++) {
					userGradeDto = userGradeList.get(gradeInex);
					if (dto.getUserPoints() >= userGradeDto.getStartValue()
							&& dto.getUserPoints() < userGradeDto.getEndValue()) {
						userGrade.setNextGradeImg(userGradeDto.getGradeImg());
						userGrade.setNextGradeName(userGradeDto.getGradeName());
						userGrade.setNextGradeVal(userGradeDto.getEndValue());
						break;
					}
				}
			}

		} else {
			userGrade.setCurGradeImg(gradeDefault.getGradeImg());
			userGrade.setCurGradeName(gradeDefault.getGradeName());
			if (userGradeList != null && userGradeList.size() >= 1) {
				UserGradeOutputDto userGradeDto = userGradeList.get(1);

				userGrade.setNextGradeImg(userGradeDto.getGradeImg());
				userGrade.setNextGradeName(userGradeDto.getGradeName());
				userGrade.setNextGradeVal(userGradeDto.getStartValue());
			}
		}
		return userGrade;
	}

	/**
	 * 获取当前等级
	 * 
	 * @param phoneNo
	 * @param partId
	 * @return
	 */
	public UserCurGradeOutputDto getUserCurGrade(String phoneNo, int partId) {
		// TODO Auto-generated method stub
		UserBasicCurrentOutputDto dto = getUserBasicCur(phoneNo, partId);
		UserCurGradeOutputDto userGrade = new UserCurGradeOutputDto();
		List<UserGradeOutputDto> userGradeList = userGradeService.getUserGradeList();
		if (dto != null) {
			userGrade.setGradeId(dto.getGradeId());
			userGrade.setSurplusGrade(dto.getUserPoints());
//			if (userGradeList != null) {
//				UserGradeOutputDto userGradeDto;
//				for (int gradeInex = 0; gradeInex <= userGradeList.size() - 1; gradeInex++) {
//					userGradeDto = userGradeList.get(gradeInex);
//					if (dto.getGradeId() == userGradeDto.getGradeId()) {
//						userGrade.setSurplusGrade(userGradeDto.getEndValue() - dto.getUserPoints() + 1);
//						break;
//					}
//				}
//			}
		}
		userGrade.setGradeList(userGradeList);
		return userGrade;
	}

	@Override
	public UpUserGradeOutputDto isUpGrade(String phone) {
		UserBasicCurrentOutputDto basicDto = getCurrentUserBasicCache(phone);
		if (basicDto == null) {
			return null;
		}
		UpUserGradeOutputDto dto = new UpUserGradeOutputDto();
		List<UserGradeOutputDto> userGradeList = userGradeService.getUserGradeList();
		if (userGradeList != null) {
			UserGradeOutputDto userGradeDto;
			for (int gradeIndex = 0; gradeIndex <= userGradeList.size() - 1; gradeIndex++) {
				userGradeDto = userGradeList.get(gradeIndex);
				if (basicDto.getUserPoints() == userGradeDto.getStartValue() - 1) {
//					if (gradeIndex + 1 < userGradeList.size()) {
//						gradeIndex++;
//					}
//
//					userGradeDto = userGradeList.get(gradeIndex);
					dto.setGradeId(userGradeDto.getGradeId());
					dto.setGradeImg(userGradeDto.getGradeImg());
					dto.setGradeName(userGradeDto.getGradeName());
					dto.setUp(true);
					return dto;

				}
			}
		}
		return dto;

	}

	private UserBasicCurrentOutputDto getCurrentUserBasicCache(String phoneNo) {
		String userBasicKey = String.format(RedisKeyPrefix.User_Basic_Prefix, phoneNo);
		String cache = redisService.get(userBasicKey);
		UserBasicCurrentOutputDto output = null;
		if (!StringUtils.isEmpty(cache)) {
			output = JsonUtils.toBean(cache, UserBasicCurrentOutputDto.class);
		}
		return output;
	}

}
