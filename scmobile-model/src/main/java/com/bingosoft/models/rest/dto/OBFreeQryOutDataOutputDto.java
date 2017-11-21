package com.bingosoft.models.rest.dto;

import java.util.List;

import lombok.Data;

@Data
public class OBFreeQryOutDataOutputDto {
	private List<GprsInfoOutputDto> GPRS_INFO;
    private List<ProdInfoOutputDto> PROD_INFO;
}
