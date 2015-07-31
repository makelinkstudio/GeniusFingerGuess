package com.bncq.common;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DateUtil {
	private static final long ONE_SECOND = 1000L;  
	private static final long ONE_MINUTE = 60000L;  
    private static final long ONE_HOUR = 3600000L;  
    private static final long ONE_DAY = 86400000L;  
    private static final long ONE_WEEK = 604800000L;  
  
    private static final String ONE_SECOND_AGO = "��ǰ";  
    private static final String ONE_MINUTE_AGO = "����ǰ";  
    private static final String ONE_HOUR_AGO = "Сʱǰ";  
    private static final String ONE_DAY_AGO = "��ǰ";  
    private static final String ONE_MONTH_AGO = "��ǰ";  
    private static final String ONE_YEAR_AGO = "��ǰ"; 
    
    public static final long RANGE_MINUTE = 600L;  //��Χʱ��
	/**
	 * ʱ���ת��stringʱ��
	 * @param value
	 * @return
	 */
	public static String UnixTimeToDate(String value){
		String rtnTime="";
		try {
			SimpleDateFormat sd=new SimpleDateFormat("yyyy-MM-dd");
			Long timeLong=new Long(value)*1000L;
			rtnTime=sd.format(timeLong);
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rtnTime;
	}
	/**
	 * ��ȡ����ʱ��
	 * @param format
	 * @return
	 */
	public static String getNowDate(String format){
		String rtnTime="";
		try {
			SimpleDateFormat sd=new SimpleDateFormat(format);
			rtnTime=sd.format(new Date());
		} catch (Exception e) {
			// TODO: handle exception
		}
		return rtnTime;
	}
	/**
	 * ���㵹��ʱ��
	 * @param value
	 * @return
	 */
	public static String getCountDownTime(String value){
		String rtnTime="";
		long tempValue=Long.valueOf(value)*1000L;
		if(tempValue>ONE_DAY){
			long day=tempValue/ONE_DAY;
			tempValue=tempValue%ONE_DAY;
			rtnTime+=String.format("%02d",day)+"��";
		}
		if(tempValue>ONE_HOUR){
			long hour=tempValue/ONE_HOUR;
			tempValue=tempValue%ONE_HOUR;
			rtnTime+=String.format("%02d",hour)+"ʱ";
		}
		if(tempValue>ONE_MINUTE){
			long min=tempValue/ONE_MINUTE;
			tempValue=tempValue%ONE_MINUTE;
			rtnTime+=String.format("%02d",min)+"��";
		}
		if(tempValue>ONE_SECOND){
			long sec=tempValue/ONE_SECOND;
			tempValue=tempValue%ONE_SECOND;
			rtnTime+=String.format("%02d",sec)+"��";
		}else{
			rtnTime+="00��";
		}
		
		return rtnTime;
	}
	/**
	 * ���㵹��ʱ��
	 * @param value
	 * @return
	 */
	public static String getCountDownTimeShort(String value){
		String rtnTime="";
		long tempValue=Long.valueOf(value)*1000L;
		if(tempValue>ONE_HOUR){
			long hour=tempValue/ONE_HOUR;
			tempValue=tempValue%ONE_HOUR;
			rtnTime+=String.format("%02d", hour)+"��";
		}
		if(tempValue>ONE_MINUTE){
			long min=tempValue/ONE_MINUTE;
			tempValue=tempValue%ONE_MINUTE;
			rtnTime+=String.format("%02d", min)+"��";
		}
		if(tempValue>ONE_SECOND){
			long sec=tempValue/ONE_SECOND;
			tempValue=tempValue%ONE_SECOND;
			rtnTime+=String.format("%02d", sec);
		}else{
			rtnTime+="00";
		}
		return rtnTime;
	}
	/**
	 * ������̳��ʾ�����൱ǰϵͳʱ��
	 * @param value ʱ���
	 * @return
	 */
	public static String formatToForum(long value) {
		long delta = new Date().getTime() - value * 1000L;
		if (delta < 1L * ONE_MINUTE) {
			long seconds = toSeconds(delta);
			return (seconds <= 0 ? 1 : seconds) + ONE_SECOND_AGO;
		}
		if (delta < 60L * ONE_MINUTE) {
			long minutes = toMinutes(delta);
			return (minutes <= 0 ? 1 : minutes) + ONE_MINUTE_AGO;
		}
		if (delta < 24L * ONE_HOUR) {
			long hours = toHours(delta);
			return (hours <= 0 ? 1 : hours) + ONE_HOUR_AGO;
		}
		if (delta < 48L * ONE_HOUR) {
			return "����";
		}
		if (delta < 30L * ONE_DAY) {
			long days = toDays(delta);
			return (days <= 0 ? 1 : days) + ONE_DAY_AGO;
		}
		if (delta < 12L * 4L * ONE_WEEK) {
			long months = toMonths(delta);
			return (months <= 0 ? 1 : months) + ONE_MONTH_AGO;
		} else {
			long years = toYears(delta);
			return (years <= 0 ? 1 : years) + ONE_YEAR_AGO;
		}
	}
	//�������
	private static long toSeconds(long date) {
		return date / 1000L;
	}
	//����ɷ�
	private static long toMinutes(long date) {
		return toSeconds(date) / 60L;
	}
	//�����Сʱ
	private static long toHours(long date) {
		return toMinutes(date) / 60L;
	}
	//�������
	private static long toDays(long date) {
		return toHours(date) / 24L;
	}
	//�������
	private static long toMonths(long date) {
		return toDays(date) / 30L;
	}
	//�������
	private static long toYears(long date) {
		return toMonths(date) / 12L;
	}
}
