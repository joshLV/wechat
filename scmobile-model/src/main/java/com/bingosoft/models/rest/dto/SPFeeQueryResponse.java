package com.bingosoft.models.rest.dto;

public class SPFeeQueryResponse {
	private String resCode;
	private String resMsg;
	private SPFeeQueryOutData outData;

	public void setResCode(String resCode) {
		this.resCode = resCode;
	}

	public String getResCode() {
		return resCode;
	}

	public void setResMsg(String resMsg) {
		this.resMsg = resMsg;
	}

	public String getResMsg() {
		return resMsg;
	}

	public void setOutData(SPFeeQueryOutData outData) {
		this.outData = outData;
	}

	public SPFeeQueryOutData getOutData() {
		return outData;
	}
}
