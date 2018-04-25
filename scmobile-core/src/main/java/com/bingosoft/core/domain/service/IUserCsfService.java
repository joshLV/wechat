package com.bingosoft.core.domain.service;

import com.bingosoft.models.csf.RealFeeQryRspDto;
import com.bingosoft.models.dto.UserFlowInfoOutputDto;

public interface IUserCsfService {
	/**
	 * 获取话费
	 * @param phoneNo
	 * @return
	 */
   public RealFeeQryRspDto getUserMoney(String phoneNo);
   
   /**
    * 获取流量
    * @param phoneNo
    */
   public UserFlowInfoOutputDto getUserFlow(String phoneNo);
   
}
