package com.bingosoft.utils;

import java.util.Random;

public class RandomUtils {
	private static String sources = "0123456789";
	
	/**
	 * 生成指定位数随机数
	 * @param pos
	 * @return
	 */
	public static String getRandom(int pos)
	{
		 // 加上一些字母，就可以生成pc站的验证码了  
	    Random rand = new Random();  
	    StringBuffer flag = new StringBuffer();  
	    for (int j = 0; j < pos; j++)   
	    {  
	        flag.append(sources.charAt(rand.nextInt(9)) + "");  
	    }  
	    return flag.toString();
	}

}
