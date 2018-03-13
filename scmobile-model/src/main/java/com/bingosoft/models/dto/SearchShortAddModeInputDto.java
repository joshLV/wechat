package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class SearchShortAddModeInputDto {
  private String vidateCode;
  private String phoneNo;
  private String prodId;
  private int type;
}
