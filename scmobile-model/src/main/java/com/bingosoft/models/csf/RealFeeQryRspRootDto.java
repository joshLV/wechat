package com.bingosoft.models.csf;

public class RealFeeQryRspRootDto {
	private String message;
    private int status;
    private RealFeeQryRspDataDto data;
    public void setMessage(String message) {
         this.message = message;
     }
     public String getMessage() {
         return message;
     }

    public void setStatus(int status) {
         this.status = status;
     }
     public int getStatus() {
         return status;
     }

    public void setData(RealFeeQryRspDataDto data) {
         this.data = data;
     }
     public RealFeeQryRspDataDto getData() {
         return data;
     }
}
