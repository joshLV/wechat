package com.bingosoft.models.csf;

import java.util.List;

public class OfferListResultOutputDto {
	private String userId;
    private List<OfferItemOutputDto> offerList;
    public void setUserId(String userId) {
         this.userId = userId;
     }
     public String getUserId() {
         return userId;
     }

    public void setOfferList(List<OfferItemOutputDto> offerList) {
         this.offerList = offerList;
     }
     public List<OfferItemOutputDto> getOfferList() {
         return offerList;
     }
}
