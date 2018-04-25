package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class OrderShortAddModeOutputDto {
 private int rstCode;
 private String effDate;
 private String seqIndex;
 private String linkUrl;
 private String retMsg;
 private boolean isUp;
 private String gradeName;
 private String gradeImg;
}
