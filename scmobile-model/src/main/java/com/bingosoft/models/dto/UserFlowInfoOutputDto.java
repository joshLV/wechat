package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class UserFlowInfoOutputDto {
  private String headImage;
  private String phoneNo;
  private double money;
  private double flow;
  private double useFlow;
  private double flowPercent;
}
