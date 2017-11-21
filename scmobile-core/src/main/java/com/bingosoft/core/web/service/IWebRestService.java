package com.bingosoft.core.web.service;

import com.bingosoft.models.rest.dto.OBFreeQryOutDataOutputDto;
import com.bingosoft.models.rest.dto.RealTimeFeeOutputDto;
import com.bingosoft.models.rest.dto.RestResponseOutputDto;
import com.bingosoft.models.rest.dto.SPFeeQueryOutputDto;
import com.bingosoft.models.rest.dto.ShortAddModeOutputDto;

public interface IWebRestService {
   public RestResponseOutputDto<RealTimeFeeOutputDto> sYdscOrdQry(String phoneNo);
   public RestResponseOutputDto<RealTimeFeeOutputDto> qryRealTimeFee(String phoneNo);
   public RestResponseOutputDto<SPFeeQueryOutputDto> sPFeeQuery(String phoneNo);
   
   /**
    * 套餐余量查询
    * @param phoneNo
    * @return
    */
   public RestResponseOutputDto<OBFreeQryOutDataOutputDto> sOBFreeQry(String phoneNo);
   
   /**
    *附加资费办理
    * @param phoneNo
    * @param prod_id
    * @return
    */
   public RestResponseOutputDto<ShortAddModeOutputDto> sShortAddMode(String phoneNo,String prod_id);
}
