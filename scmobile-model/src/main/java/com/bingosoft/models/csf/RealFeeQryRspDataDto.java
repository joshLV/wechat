package com.bingosoft.models.csf;

public class RealFeeQryRspDataDto {
	private String oprTime;
    private RealFeeQryRspDto realFeeQryRsp;
    private String transIdo;
    public void setOprTime(String oprTime) {
         this.oprTime = oprTime;
     }
     public String getOprTime() {
         return oprTime;
     }

    public void setRealFeeQryRsp(RealFeeQryRspDto realFeeQryRsp) {
         this.realFeeQryRsp = realFeeQryRsp;
     }
     public RealFeeQryRspDto getRealFeeQryRsp() {
         return realFeeQryRsp;
     }

    public void setTransIdo(String transIdo) {
         this.transIdo = transIdo;
     }
     public String getTransIdo() {
         return transIdo;
     }
}
