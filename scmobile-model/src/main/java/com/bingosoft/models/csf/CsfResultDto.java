package com.bingosoft.models.csf;

import lombok.Data;

@Data
public class CsfResultDto<T> {
   private String rtnCode;
   private String rtnMsg;
   private T object;
}
