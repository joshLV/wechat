package com.bingosoft.models.rest.dto;

import lombok.Data;

@Data
public class GprsInfoOutputDto {
	private String NAME;
    private int SUM;
    private int USED;
    private int REMAIN;
    private String BEGIN_TIME;
    private String END_TIME;
    private String OPR_TIME;
    private int PREMONTH_FLAG;
    private String UNIT;
}
