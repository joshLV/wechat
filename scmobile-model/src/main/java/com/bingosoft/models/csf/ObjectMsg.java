package com.bingosoft.models.csf;

import lombok.Data;

@Data
public class ObjectMsg<T> {
  private String respCode;
  private String respDesc;
  private T result;
  
}
