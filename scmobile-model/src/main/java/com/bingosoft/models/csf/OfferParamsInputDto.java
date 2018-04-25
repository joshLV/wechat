package com.bingosoft.models.csf;

import java.util.List;

public class OfferParamsInputDto {
	 private String userMobile;
	    private List<OfferListInputDto> offerList;
	    private CrmpfPubInfoInputDto crmpfPubInfo;
	    public void setUserMobile(String userMobile) {
	         this.userMobile = userMobile;
	     }
	     public String getUserMobile() {
	         return userMobile;
	     }

	    public void setOfferList(List<OfferListInputDto> offerList) {
	         this.offerList = offerList;
	     }
	     public List<OfferListInputDto> getOfferList() {
	         return offerList;
	     }

	    public void setCrmpfPubInfo(CrmpfPubInfoInputDto crmpfPubInfo) {
	         this.crmpfPubInfo = crmpfPubInfo;
	     }
	     public CrmpfPubInfoInputDto getCrmpfPubInfo() {
	         return crmpfPubInfo;
	     }
}
