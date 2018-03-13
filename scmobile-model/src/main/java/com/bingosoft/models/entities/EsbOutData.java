package com.bingosoft.models.entities;

import java.util.List;

import lombok.Data;

@Data
public class EsbOutData<T> {
	private List<EsbOfferInfo> offerList;
	private String userId;
}
