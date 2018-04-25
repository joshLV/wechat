package com.bingosoft.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtils {

	private final static String[] week = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六" };

	/**
	 * 
	 * @param date是为则默认今天日期、可自行设置“2013-06-03”格式的日期
	 * 
	 * @return 返回1是星期日、2是星期一、3是星期二、4是星期三、5是星期四、6是星期五、7是星期六
	 * 
	 */

	public static int getDayofweek(String date) {
		Calendar cal = Calendar.getInstance();
		// cal.setTime(new Date(System.currentTimeMillis()));
		if (date.equals("")) {
			cal.setTime(new Date(System.currentTimeMillis()));
		} else {
			cal.setTime(new Date(getDateByStr2(date).getTime()));
		}
		return cal.get(Calendar.DAY_OF_WEEK);
	}

	public static String getDay2week(String date) {
		Calendar cal = Calendar.getInstance();
		// cal.setTime(new Date(System.currentTimeMillis()));
		if (date.equals("")) {
			cal.setTime(new Date(System.currentTimeMillis()));
		} else {
			cal.setTime(new Date(getDateByStr2(date).getTime()));
		}
		return week[cal.get(Calendar.DAY_OF_WEEK)];
	}

	public static String getDay2week(Date date) {
		Calendar cal = Calendar.getInstance();
		// cal.setTime(new Date(System.currentTimeMillis()));

		cal.setTime(date);
		String time = getDateByStr(date);
		// System.out.println(cal.get(Calendar.DAY_OF_WEEK));
		// System.out.println(week[cal.get(Calendar.DAY_OF_WEEK)-1]);
		return week[cal.get(Calendar.DAY_OF_WEEK) - 1] + " " + time;
	}

	public static Date getDateByStr2(String dd) {

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd");
		Date date;
		try {
			date = sd.parse(dd);
		} catch (ParseException e) {
			date = null;
			e.printStackTrace();
		}
		return date;
	}

	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getDateToFormatStr() {

		SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd HH:mm:sss");
		String date = "";
		try {
			date = sd.format(new Date());
		} catch (Exception e) {

			e.printStackTrace();
		}
		return date;
	}
	
	/**
	 * 获取当前时间
	 * 
	 * @return
	 */
	public static String getDateToStr() {

		SimpleDateFormat sd = new SimpleDateFormat("yyyyMMddHHmmsss");
		String date = "";
		try {
			date = sd.format(new Date());
		} catch (Exception e) {

			e.printStackTrace();
		}
		return date;
	}

	public static String getDateByStr(Date dd) {

		SimpleDateFormat sd = new SimpleDateFormat("HH:mm");
		String date;
		date = sd.format(dd);
		return date;
	}

	public static String formatToNaturalDate(String strDate) {
		String year = strDate.substring(0, 4);
		String month = strDate.substring(4, 6);
		String day = strDate.substring(6, 8);
		String hour = strDate.substring(8, 10);
		String minute = strDate.substring(10, 12);
		String s = strDate.substring(12, 14);

		return String.format("%s-%s-%s %s:%s:%s", year, month, day, hour, minute, s);
	}

	public static String formatToDate(String strDate) {
		String year = strDate.substring(0, 4);
		String month = strDate.substring(4, 6);
		String day = strDate.substring(6, 8);
		String hour = strDate.substring(8, 10);
		String minute = strDate.substring(10, 12);
		String s = strDate.substring(12, 14);

		return String.format("%s年%s月%s日 %s时%s分%s秒", year, month, day, hour, minute, s);
	}

	public static long getTimeInMillis() {

		Date dt = new Date();
		dt.setHours(23);
		dt.setMinutes(59);
		dt.setSeconds(59);
		return dt.getTime();
	}
	
	/** 
     * 使用参数Format格式化Date成字符串 
     */  
    public static String format(Date date, String pattern)  
    {  
        return date == null ? " " : new SimpleDateFormat(pattern).format(date);  
    }  
    
    
    public static boolean compareValidate(Date dt)
    {
    	Date d=new Date();
    	long from = dt.getTime()  ;
    	long to = d.getTime();  
    	int sec = (int) ((to - from)/(1000 * 60));  
    	if(sec<=60)
    	{
    	  return true;
    	}
    	else
    	{
    		return false;
    	}
    }

}
