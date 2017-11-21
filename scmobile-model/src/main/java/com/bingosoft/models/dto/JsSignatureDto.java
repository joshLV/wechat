package com.bingosoft.models.dto;


import lombok.Data;

@Data
public class JsSignatureDto {
   private int status;
   
   private String jsSignature;
   
   private String timeStamp;
   
   private String nonceStr;
   
   private String message;
   
   private String appId;
}
