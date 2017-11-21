package com.bingosoft.models.rest.dto;

import lombok.Data;

@Data
public class RestResponseOutputDto<T> {
    private String resCode;
    private String resMsg;
    private T outData;
}
