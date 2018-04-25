package com.bingosoft.models.csf;

import java.util.List;

public class PlanRemainDto {
	private String planName;
    private String planId;
    private String validDate;
    private List<ResourcesInfoDto> resourcesInfo;
    public void setPlanName(String planName) {
         this.planName = planName;
     }
     public String getPlanName() {
         return planName;
     }

    public void setPlanId(String planId) {
         this.planId = planId;
     }
     public String getPlanId() {
         return planId;
     }

    public void setValidDate(String validDate) {
         this.validDate = validDate;
     }
     public String getValidDate() {
         return validDate;
     }

    public void setResourcesInfo(List<ResourcesInfoDto> resourcesInfo) {
         this.resourcesInfo = resourcesInfo;
     }
     public List<ResourcesInfoDto> getResourcesInfo() {
         return resourcesInfo;
     }
}
