package com.bingosoft.models.dto;

import com.bingosoft.models.entities.CommonInfo;

import lombok.Data;

@Data
public class UserOrdQryDto {
  private String LOGIN_NO="a18610";
  private String PHONE_NO;
  private String WORN_SERV_CODE="sQryFamWlanCfm";
  private CommonInfo COMMON_INFO=new CommonInfo();
}


