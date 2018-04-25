package com.bingosoft.models.csf;

public class ResourcesDataInfoDto {
	private String oprTime;
    private PlanRemainDto planRemain;
    private MealInfoUpDto mealInfoUp;
    private String transIdo;
    public void setOprTime(String oprTime) {
         this.oprTime = oprTime;
     }
     public String getOprTime() {
         return oprTime;
     }

    public void setPlanRemain(PlanRemainDto planRemain) {
         this.planRemain = planRemain;
     }
     public PlanRemainDto getPlanRemain() {
         return planRemain;
     }

    public void setMealInfoUp(MealInfoUpDto mealInfoUp) {
         this.mealInfoUp = mealInfoUp;
     }
     public MealInfoUpDto getMealInfoUp() {
         return mealInfoUp;
     }

    public void setTransIdo(String transIdo) {
         this.transIdo = transIdo;
     }
     public String getTransIdo() {
         return transIdo;
     }
}
