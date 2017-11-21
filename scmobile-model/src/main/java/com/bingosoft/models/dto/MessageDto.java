package com.bingosoft.models.dto;



import lombok.Data;

@Data
public class MessageDto {
  private int status;
  private String message;
  private UserInfoDto userInfo;
}
