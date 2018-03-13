package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class FansInfoByOauthV2Dto {
	private int status;

	private String message;

	private String nickname;

	private String headimgurl;

	private String openid;

	private String telnum;

	private int provinceCode;
	
	private int cityCode;
}
