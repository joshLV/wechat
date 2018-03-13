package com.bingosoft.models.dto;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

@Data
public class UserInfoDto implements Serializable {
	/**
	 * 
	 */

	public UserInfoDto() {
		this.time = new Date();
	}

	private static final long serialVersionUID = 1L;

	private String openId = "wmymtx";

	private String userId = "wmymtx";

	private String phoenNo = "13548074395";

	private String headImg = "http://www.baidu.com";

	private String userName = "wmymtx";

	private Date time;
	
	private String subscribeTime;

}
