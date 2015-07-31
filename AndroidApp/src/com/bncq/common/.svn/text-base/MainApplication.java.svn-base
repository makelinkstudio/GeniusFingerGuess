package com.bncq.common;

import android.app.Application;
import android.content.Context;

public class MainApplication extends Application {

	private static Context context;
	private static String fragmentArg;//fragment之间传递参数
	public static final int PAGE_SIZE =10;//默认分页大小
	
	@Override
	public void onCreate(){
		super.onCreate();
		context=getApplicationContext();
	}
	/**
	 * 获取全局context 
	 * @return
	 */
	public static Context  getContext() {
		return context;
	}
	
	@Override
	public void onLowMemory(){
		super.onLowMemory();
	}
	//fragment之间传递值
	public static String getFragmentArg() {
		return fragmentArg;
	}
	public static void setFragmentArg(String value){
		fragmentArg=value;
	}
}
