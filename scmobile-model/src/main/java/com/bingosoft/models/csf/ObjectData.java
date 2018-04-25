package com.bingosoft.models.csf;

import lombok.Data;

@Data
public class ObjectData<T> {
	private String message;
	private String status;
	private T data;
}
