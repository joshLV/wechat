package com.bingosoft.core.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bingosoft.core.domain.service.IUserCsfService;
import com.bingosoft.core.mycat.service.IUserBasicByPartService;
import com.bingosoft.core.mysql.service.IUserGradeService;
import com.bingosoft.core.web.service.ICsfWebService;
import com.bingosoft.models.config.GradeDefaultConfig;
import com.bingosoft.models.csf.CsfResultDto;
import com.bingosoft.models.csf.ObjectData;
import com.bingosoft.models.csf.ObjectMsg;
import com.bingosoft.models.csf.PlanRemainDto;
import com.bingosoft.models.csf.RealFeeQryRspDataDto;
import com.bingosoft.models.csf.RealFeeQryRspDto;
import com.bingosoft.models.csf.ResDataOutputDto;
import com.bingosoft.models.csf.ResourcesDataInfoDto;
import com.bingosoft.models.csf.ResourcesInfoDto;
import com.bingosoft.models.csf.ResourcesRootDto;
import com.bingosoft.models.dto.UserBasicCurrentOutputDto;
import com.bingosoft.models.dto.UserFlowInfoOutputDto;
import com.bingosoft.models.dto.UserGradeOutputDto;
import com.bingosoft.utils.ParamSubStringUtils;

@Service
public class UserCsfServiceImpl implements IUserCsfService {

	@Autowired
	ICsfWebService csfWebService;

	@Autowired
	IUserBasicByPartService userBasicService;

	@Autowired
	IUserGradeService userGradeService;

	@Autowired
	GradeDefaultConfig gradeDefault;

	@Override
	public RealFeeQryRspDto getUserMoney(String phoneNo) {
		// TODO Auto-generated method stub
		int partId = ParamSubStringUtils.getPartId(phoneNo);
		UserBasicCurrentOutputDto output = userBasicService.getUserBasicCur(phoneNo, partId);
		if (output == null) {
			List<UserGradeOutputDto> grade = userGradeService.getUserGradeList();
			if (grade != null) {
				// grade.sort((UserGradeOutputDto u1,UserGradeOutputDto
				// u2)->u1.getStartValue()<u2.getStartValue());
				grade.sort((u1, u2) -> u1.getStartValue() - u2.getStartValue());
				UserGradeOutputDto userGrade = grade.get(0);
				userBasicService.addUserBasic(phoneNo, partId, userGrade.getGradeId(), userGrade.getGradeName(),
						userGrade.getGradeImg());
				output = userBasicService.getUserBasicCur(phoneNo, partId);
			}
		}
		CsfResultDto<ObjectData<RealFeeQryRspDataDto>> rst = csfWebService.CSCHQFareBalance(phoneNo);
		if (rst == null || rst.getObject() == null)
			return null;
		RealFeeQryRspDataDto dto = rst.getObject().getData();
		if (dto == null)
			return null;
		return dto.getRealFeeQryRsp();
	}

	@Override
	public UserFlowInfoOutputDto getUserFlow(String phoneNo) {
		// TODO Auto-generated method stub
		UserFlowInfoOutputDto dto = new UserFlowInfoOutputDto();
		int partId = ParamSubStringUtils.getPartId(phoneNo);
		UserBasicCurrentOutputDto output = userBasicService.getUserBasicCur(phoneNo, partId);
		if (output != null) {
			dto.setGradeName(output.getGradeName());
			dto.setGradeImg(output.getGradeImage());
		} else {
			dto.setGradeName(gradeDefault.getGradeName());
			dto.setGradeImg(gradeDefault.getGradeImg());
		}
		CsfResultDto<ObjectData<ResDataOutputDto>> rst = csfWebService.CSCHQPlanRemian(phoneNo);
		if (rst == null || rst.getObject() == null || rst.getObject().getData() == null)
			return dto;

		ResDataOutputDto rstDto = rst.getObject().getData();
		List<PlanRemainDto> planInfo = rstDto.getPlanRemain();
		ResourcesInfoDto resitem = null;
		double flow = 0.0, useFlow = 0.0, snUsedFlow = 0.0, gnUsedFlow = 0.0, gnTotalFlow = 0.0, snTotalFlow = 0.0;
		for (int planIndex = 0; planIndex <= planInfo.size() - 1; planIndex++) {
			List<ResourcesInfoDto> resInfo = planInfo.get(planIndex).getResourcesInfo();
			for (int resIndex = 0; resIndex <= resInfo.size() - 1; resIndex++) {

				resitem = resInfo.get(resIndex);
				if (resitem.getResourcesCode().equals("04")) {
					useFlow += resitem.getSecResourcesInfo().getResourcesLeftInfo().getUsedRes();
					flow += resitem.getSecResourcesInfo().getResourcesLeftInfo().getTotalRes();
					if (resitem.getSecResourcesInfo().getSecResourcesName().contains("国内")) {
						gnUsedFlow += resitem.getSecResourcesInfo().getResourcesLeftInfo().getUsedRes();
						gnTotalFlow += resitem.getSecResourcesInfo().getResourcesLeftInfo().getTotalRes();
					} else {
						snUsedFlow += resitem.getSecResourcesInfo().getResourcesLeftInfo().getUsedRes();
						snTotalFlow += resitem.getSecResourcesInfo().getResourcesLeftInfo().getTotalRes();
					}
				}
			}
		}

		dto.setFlow(flow);
		if (flow <= 0) {
			dto.setFlowPercent(0);
		} else {
			dto.setFlowPercent((flow - useFlow) / flow);
		}
		dto.setUseFlow(useFlow);
		dto.setGnUsedFlow(gnUsedFlow);
		dto.setSnUsedFlow(snUsedFlow);
		dto.setSnTotalFlow(snTotalFlow);
		dto.setGnTotalFlow(gnTotalFlow);
		RealFeeQryRspDto realFee = getUserMoney(phoneNo);
		if (realFee != null) {
			dto.setMoney(realFee.getCurFee());
			dto.setUn_bill_fee(realFee.getRealFee());
		}
		
		return dto;
		// dto.setHeadImage(userDto.getHeadImg());
		// dto.setNickName(userDto.getUserName());
		// if(dto==null)
		// return 0;
		// return Double.valueOf(dto.getRealFeeQryRsp().getCurFee());

	}

}
