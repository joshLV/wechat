package com.bingosoft.models.dto;


import lombok.Data;

@Data
public class TelPhoneInputDto {
   private String telNum;
   private String captcha;
   private int moduleId;
   private String share;
}
