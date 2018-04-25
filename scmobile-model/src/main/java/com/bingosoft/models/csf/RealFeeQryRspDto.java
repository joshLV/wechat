package com.bingosoft.models.csf;

public class RealFeeQryRspDto {
	private double curFeeTotal;
	private double curFee;
	private double realFee;
	private double oweFee;

	public void setCurFeeTotal(double curFeeTotal) {
		this.curFeeTotal = curFeeTotal;
	}

	public double getCurFeeTotal() {
		return curFeeTotal;
	}

	public void setCurFee(double curFee) {
		this.curFee = curFee;
	}

	public double getCurFee() {
		return curFee;
	}

	public void setRealFee(double realFee) {
		this.realFee = realFee;
	}

	public double getRealFee() {
		return realFee;
	}

	public void setOweFee(double oweFee) {
		this.oweFee = oweFee;
	}

	public double getOweFee() {
		return oweFee;
	}
}
