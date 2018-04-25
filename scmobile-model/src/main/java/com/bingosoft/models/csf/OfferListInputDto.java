package com.bingosoft.models.csf;

public class OfferListInputDto {
	private String offerId;
    private String isMain="0";
    private String isPackage="0";
    private String action="0";
    public void setOfferId(String offerId) {
         this.offerId = offerId;
     }
     public String getOfferId() {
         return offerId;
     }

    public void setIsMain(String isMain) {
         this.isMain = isMain;
     }
     public String getIsMain() {
         return isMain;
     }

    public void setIsPackage(String isPackage) {
         this.isPackage = isPackage;
     }
     public String getIsPackage() {
         return isPackage;
     }

    public void setAction(String action) {
         this.action = action;
     }
     public String getAction() {
         return action;
     }
}
