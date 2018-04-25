package com.bingosoft.models.csf;

public class MealInfoUpDto {
	private String mealInfoUpCode;
    private MealInfoUpSourceDto mealInfoUpSource;
    public void setMealInfoUpCode(String mealInfoUpCode) {
         this.mealInfoUpCode = mealInfoUpCode;
     }
     public String getMealInfoUpCode() {
         return mealInfoUpCode;
     }

    public void setMealInfoUpSource(MealInfoUpSourceDto mealInfoUpSource) {
         this.mealInfoUpSource = mealInfoUpSource;
     }
     public MealInfoUpSourceDto getMealInfoUpSource() {
         return mealInfoUpSource;
     }
}
