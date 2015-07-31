package com.bncq.common;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.R.bool;

public class StringUtil {
	/**
	 * ≈–∂œ◊÷∑˚¥Æ «∑Ò «ø’
	 * @param obj
	 * @return
	 */
	public static boolean stringiSNotEmpty(Object obj){
		boolean flag=false;
		try {
			if(obj!=null){
				String objString=obj.toString().trim();
				if(!"".equals(objString)&&!"null".equals(objString)&&objString.length()>0){
					flag=true;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		return flag;
	}
	/**
	 * ≈–∂œ «∑Ò « ˝◊÷
	 * @param obj
	 * @return
	 */
	public static boolean isNumber(Object obj){
		boolean flag=false;
		if(stringiSNotEmpty(obj)){
			Pattern pattern=java.util.regex.Pattern.compile("[0-9]*");
	        Matcher match=pattern.matcher(obj.toString().trim());
	        if(match.matches()){
	        	flag= true;
	        }
		}
		return flag;
	}
	/**
	 * —È÷§” œ‰
	 * @param email
	 * @return true or false
	 */
   public static boolean checkEmail(String email){
		boolean flag = false;
		try {
			String check ="^([a-zA-Z0-9_\\-\\.]+)@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.)|(([a-zA-Z0-9\\-]+\\.)+))([a-zA-Z]{2,4}|[0-9]{1,3})(\\]?)$";
			Pattern regex = Pattern.compile(check);
			Matcher matcher = regex.matcher(email);
			flag = matcher.matches();
		} catch (Exception e) {
			flag = false;
		}
		return flag;
	}
}
