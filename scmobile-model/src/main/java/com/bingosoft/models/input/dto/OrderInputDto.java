package com.bingosoft.models.input.dto;

import lombok.Data;

@Data
public class OrderInputDto {
  private int goodsId;
  private int channelId;
  private String smsCode;
}
