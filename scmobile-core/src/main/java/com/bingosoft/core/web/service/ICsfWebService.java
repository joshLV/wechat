package com.bingosoft.core.web.service;

import com.bingosoft.models.csf.CsfResultDto;
import com.bingosoft.models.csf.ObjectData;
import com.bingosoft.models.csf.ObjectMsg;
import com.bingosoft.models.csf.OfferItemOutputDto;
import com.bingosoft.models.csf.OrderResultOutputDto;
import com.bingosoft.models.csf.PlanRemainDto;
import com.bingosoft.models.csf.RealFeeQryRspDataDto;
import com.bingosoft.models.csf.ResDataOutputDto;
import com.bingosoft.models.csf.ResourcesDataInfoDto;
import com.bingosoft.models.csf.ResourcesRootDto;

public interface ICsfWebService {
	
	/**
	 * 实时话费查询
	 * @param phone
	 */
	public CsfResultDto<ObjectData<RealFeeQryRspDataDto>> CSCHQFareBalance(String phone);
	
	/**
	 * 套餐余量查询
	 * @param phone
	 */
	public CsfResultDto<ObjectData<ResDataOutputDto>> CSCHQPlanRemian(String phone);
	
	/**
	 * 
	 */
	public void flowAdditionalDeal();
	
	/**
	 * 
	 * {
 "params": {
  "userMobile": "13980808618",
  "offerList": [{
   "offerId": "ACAS03aamoi",
   "isMain": "0",
   "isPackage": "0",
   "action": "0"
  }],
  "crmpfPubInfo": {
   "staffId": "a18610"
  }
 }
}
	 */
	public OrderResultOutputDto CSCHTSubmitOffersOrder(String phone,String prod);
	
	/**
	 * 
	 * @param phone
	 * @param prod
	 */
	public void CSCHTSubmitCampaignsOrder(String phone,String prod);
	
	/**
	 * 发送短信验证码
	 * @param phone
	 * @param smsContent
	 * @return
	 */
	public boolean smsCentral(String phone, String smsContent);
	
	/**
	 * 查询订购业务时间
	 * @param phone
	 * @param prod
	 * @return
	 */
	public OfferItemOutputDto subscribeInfoCentral(String phone,String prod);
	
	public OfferItemOutputDto getOrderProdInfo(String phone, String prod, int eachNum);
}
