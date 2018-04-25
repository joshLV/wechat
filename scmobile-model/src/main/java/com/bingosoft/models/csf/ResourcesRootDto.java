package com.bingosoft.models.csf;

public class ResourcesRootDto {
	private String message;
    private int status;
    private ResourcesDataInfoDto data;
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

    public void setData(ResourcesDataInfoDto data) {
         this.data = data;
     }
     public ResourcesDataInfoDto getData() {
         return data;
     }
}
