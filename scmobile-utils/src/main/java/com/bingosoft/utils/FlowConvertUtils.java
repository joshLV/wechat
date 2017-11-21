package com.bingosoft.utils;

public class FlowConvertUtils {
  public static String convertFlow(double source)
  {
	  if(source>(1024*1024))
	  {
		  return String.format("%.2f",source/1024/1024)+"G";
	  }
	  return String.format("%.2f",source/1024)+"M";
  }
}
