package com.bingosoft.models.entities;

import lombok.Data;

@Data
public class EsbOfferInfo {
	private String offerId;
	private String offerName;
	private String offerType;
	private String offerTypeName;
	private String isMain;
	private String canUnsub;
	private String isPackage;
	private String effTime;
	private String expTime;

}
