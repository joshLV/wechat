package com.bingosoft.models.csf;

public class ResourcesInfoDto {
	private String resourcesCode;
    private SecResourcesInfoDto secResourcesInfo;
    public void setResourcesCode(String resourcesCode) {
         this.resourcesCode = resourcesCode;
     }
     public String getResourcesCode() {
         return resourcesCode;
     }

    public void setSecResourcesInfo(SecResourcesInfoDto secResourcesInfo) {
         this.secResourcesInfo = secResourcesInfo;
     }
     public SecResourcesInfoDto getSecResourcesInfo() {
         return secResourcesInfo;
     }
}
