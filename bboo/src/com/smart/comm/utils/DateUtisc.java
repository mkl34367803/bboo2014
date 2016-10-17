package com.smart.comm.utils;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateUtisc {
	
	public static void main(String[] args) {
		String i = getCurrentHandM();
		System.out.println(i);
	}
	
	/**
	 * 获取当前的时分
	 * @return 小时：分钟
	 */
	public static String getCurrentHandM() {
		Calendar cal = Calendar.getInstance();
		int h = cal.get(Calendar.HOUR_OF_DAY);
		int m = cal.get(Calendar.MINUTE);
		return h+":"+m;
	}
	
	/**
	 * 获取当前系统时间的周几
	 * @return
	 */
	public static Integer getCurrentWeekOfDate() {
		Calendar cal = Calendar.getInstance();
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}

	/**
	 * 获取周几
	 * @param date
	 * @return
	 */
	public static Integer getWeekOfDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_WEEK) - 1;
	}
	
	/**
	 * 比较时间 t1 是否 大于 t2
	 * @param t1
	 * @param t2
	 * @return true 大于或等于
	 * @throws ParseException
	 */
	public static boolean compareTime(String t1, String t2) throws ParseException {
		t1 = t1.replace(':', '0');
		t2 = t2.replace(':', '0');
		int i = Integer.parseInt(t1) - Integer.parseInt(t2);
		if (i < 0) {
			return false;
		} else {
			return true;
		}
	}
}
