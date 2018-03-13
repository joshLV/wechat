package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class BindUserInputDto {
 private String openId;
 private String phoneNo;
 private String sharePhoneNo;
 private int partId;
 private int moduleId;
 private String subscribeTime;
 
}
