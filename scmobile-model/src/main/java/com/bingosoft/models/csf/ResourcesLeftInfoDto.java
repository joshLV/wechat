package com.bingosoft.models.csf;

public class ResourcesLeftInfoDto {
	private double totalRes;
    private double usedRes;
    private double remainRes;
    private String unit;
    private String validDate;
    public void setTotalRes(double totalRes) {
         this.totalRes = totalRes;
     }
     public double getTotalRes() {
         return totalRes;
     }

    public void setUsedRes(double usedRes) {
         this.usedRes = usedRes;
     }
     public double getUsedRes() {
         return usedRes;
     }

    public void setRemainRes(double remainRes) {
         this.remainRes = remainRes;
     }
     public double getRemainRes() {
         return remainRes;
     }

    public void setUnit(String unit) {
         this.unit = unit;
     }
     public String getUnit() {
         return unit;
     }

    public void setValidDate(String validDate) {
         this.validDate = validDate;
     }
     public String getValidDate() {
         return validDate;
     }
}
