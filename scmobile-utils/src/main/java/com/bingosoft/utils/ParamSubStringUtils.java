package com.bingosoft.utils;

public class ParamSubStringUtils {
	public static int getPartId(String phoneNo) {
		return Integer.valueOf(phoneNo.substring(10));
	}
}
