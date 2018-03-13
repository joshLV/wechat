package com.bingosoft.models.entities;

import lombok.Data;

@Data
public class EsbResponse {
	private int RETURN_CODE;
	private String RETURN_MSG;
	private String USER_MSG;
	private String DETAIL_MSG;
	private String PROMPT_MSG;
	private EsbOutData OUT_DATA;
}
