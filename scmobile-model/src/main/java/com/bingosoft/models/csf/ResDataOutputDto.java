package com.bingosoft.models.csf;

import java.util.List;

import lombok.Data;

@Data
public class ResDataOutputDto {
	private List<PlanRemainDto> planRemain;
    private List<String> mealInfoUp;
    private String oprTime;
    private String transIdo;
    public void setPlanRemain(List<PlanRemainDto> planRemain) {
         this.planRemain = planRemain;
     }
     public List<PlanRemainDto> getPlanRemain() {
         return planRemain;
     }

    public void setMealInfoUp(List<String> mealInfoUp) {
         this.mealInfoUp = mealInfoUp;
     }
     public List<String> getMealInfoUp() {
         return mealInfoUp;
     }

    public void setOprTime(String oprTime) {
         this.oprTime = oprTime;
     }
     public String getOprTime() {
         return oprTime;
     }

    public void setTransIdo(String transIdo) {
         this.transIdo = transIdo;
     }
     public String getTransIdo() {
         return transIdo;
     }
}
