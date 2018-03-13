package com.bingosoft.models.dto;

import lombok.Data;

@Data
public class UserFlowInfoOutputDto {
	private String headImage;
	private String phoneNo;
	// 余额
	private double money;
	// 已消费
	private double un_bill_fee;
	private double flow;
	// 剩余总流量
	private double useFlow;
	// 国内已用流量
	private double gnUsedFlow;
	// 省内已用流量
	private double snUsedFlow;
	// 省内总流量
	private double snTotalFlow;
	// 国内总流量
	private double gnTotalFlow;
	private double flowPercent;
}
