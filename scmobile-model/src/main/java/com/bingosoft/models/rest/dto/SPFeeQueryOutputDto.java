package com.bingosoft.models.rest.dto;

import lombok.Data;

@Data
public class SPFeeQueryOutputDto {
	private int PREPAY_FEE;
    private int LIMIT_FEE;
    private int OWE_FEE;
    private int UNBILL_FEE;
    private int FAVOUR_FEE;
    private String CUST_NAME;
    private String OLD_RUN_NAME;
    private String NEW_RUN_NAME;
    private int REMONTH_FEE;
    private int DELAY_FEE;
    private int BILL_TYPE;
    private int BEGIN_DATE;
    private int END_DATE;
    private String PHONE_NO;
    private long CONTRACT_NO;
    private String BRAND_NAME;
    private String REGION_NAME;
    private String PAY_NAME;
    private String PAY_CODE;
    private String DAY_FEE;
    private String RUN_CODE;
    private String BRAND_ID;
    private String USER_TYPE;
    private String EXPIRE_TIME;
    private int DK_PREPAY;
    private int UNIFIED_UNBILL_FEE;
    private int MEAL_FLAG;
    private int VOICE;
    private int SMS;
    private int XSL;
    private int MMS;
    private int GPRS;
    private int ROAM;
    private int VNET_BASE;
    private int VNET_LOCAL;
    private int VNET_ROMA;
    private int VNET_TOLL;
    private int FM_HBBAL;
    private int MEAL_FAVOURED_FEE;
    private int MEAL_FAVOUR_FREE;
    private String PHONE_MSG;
    private String MEM_MSG;
    private String HJH_CONTENT;
    private String IS_KEY;
    private String IS_CON;
    private String OUT_MSG;
  
}
