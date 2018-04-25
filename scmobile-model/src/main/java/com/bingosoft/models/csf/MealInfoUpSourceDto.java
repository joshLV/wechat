package com.bingosoft.models.csf;

public class MealInfoUpSourceDto {
	private String classify;
    private String usageAmount;
    private String unit;
    private String charging;
    public void setClassify(String classify) {
         this.classify = classify;
     }
     public String getClassify() {
         return classify;
     }

    public void setUsageAmount(String usageAmount) {
         this.usageAmount = usageAmount;
     }
     public String getUsageAmount() {
         return usageAmount;
     }

    public void setUnit(String unit) {
         this.unit = unit;
     }
     public String getUnit() {
         return unit;
     }

    public void setCharging(String charging) {
         this.charging = charging;
     }
     public String getCharging() {
         return charging;
     }
}
