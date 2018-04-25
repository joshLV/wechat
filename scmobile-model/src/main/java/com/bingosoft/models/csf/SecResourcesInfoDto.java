package com.bingosoft.models.csf;

public class SecResourcesInfoDto {
	private String secResourcesName;
    private ResourcesLeftInfoDto resourcesLeftInfo;
    public void setSecResourcesName(String secResourcesName) {
         this.secResourcesName = secResourcesName;
     }
     public String getSecResourcesName() {
         return secResourcesName;
     }

    public void setResourcesLeftInfo(ResourcesLeftInfoDto resourcesLeftInfo) {
         this.resourcesLeftInfo = resourcesLeftInfo;
     }
     public ResourcesLeftInfoDto getResourcesLeftInfo() {
         return resourcesLeftInfo;
     }
}
