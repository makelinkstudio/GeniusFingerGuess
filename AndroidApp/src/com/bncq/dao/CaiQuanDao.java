package com.bncq.dao;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;

import com.bncq.common.APIModel;
import com.bncq.common.HttpClientUtil;


public class CaiQuanDao {
	/**
	 * 获取当日当前猜拳信息
	 * @param params
	 * @return
	 */
	public Bundle getCQAllInfo(List params) {
		boolean flag=false;
		Bundle retBdl=new Bundle();
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){ //获取当日当前猜拳信息
				flag=true;
				JSONObject dataObj=retObj.getJSONObject(APIModel.DATA);
				retBdl.putString("THIS_MAT_STATUS", dataObj.getString("THIS_MAT_STATUS"));
				retBdl.putString("THIS_MAT_INFO", dataObj.getString("THIS_MAT_INFO"));
				retBdl.putString("NEXT_MAT_INFO", dataObj.getString("NEXT_MAT_INFO"));
			}
		} catch (Exception e) {
			flag=false;
		}
		retBdl.putBoolean("flag", flag);
		return retBdl;
	}
	/**
	 * 获取猜拳界面信息
	 * @param params
	 * @return
	 */
	public Bundle getCQUserPunchInfo(List params) {
		boolean flag=false;
		Bundle retBdl=new Bundle();
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){ //获取个人信息成功
				flag=true;
				JSONArray arr=retObj.getJSONArray(APIModel.DATA);
				JSONObject object=arr.getJSONObject(0);
				Iterator it= object.keys();
				while (it.hasNext()) {
					String key=(String) it.next();
					retBdl.putString(key,object.getString(key));
				}
			}
		} catch (Exception e) {
			flag=false;
			e.printStackTrace();
		}
		retBdl.putBoolean("flag", flag);
		return retBdl;
	}
	/**
	 * 获取猜拳界面信息
	 * @param params
	 * @return
	 */
	public Bundle getCQUsesrInfo(List params) {
		boolean flag=false;
		Bundle retBdl=new Bundle();
		String msg="";
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){ //获取个人信息成功
				flag=true;
				msg="ok";
				retBdl.putString("data", retObj.get(APIModel.DATA).toString());
			}else{
				msg="获取数据失败";
			}
		} catch (Exception e) {
			flag=false;
			msg="获取数据失败";
		}
		retBdl.putBoolean("flag", flag);
		retBdl.putString("msg", msg);
		return retBdl;
	}
	/**
	 * 参与猜拳
	 * @param params
	 * @return
	 */
	public Bundle setUserPunch(List params) {
		boolean flag=false;
		Bundle retBdl=new Bundle();
		String msg="";
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){ //获取个人信息成功
				flag=true;
				msg="参与成功";
				retBdl.putString("data", retObj.get(APIModel.DATA).toString());
			}else if("023".equals(retObj.getString(APIModel.RETCODE))){
				msg="别心急，还没开场哦";
			}else if("027".equals(retObj.getString(APIModel.RETCODE))){
				msg="下手太慢了，提前10分钟准备计算结果...";
			}else if("028".equals(retObj.getString(APIModel.RETCODE))){
				msg="主人，你积分不够，暂时不能参与哦";
			}else{
				msg="参与人太多了，请君稍后再试";
			}
		} catch (Exception e) {
			msg="参与人太多了，请君稍后再试";
			flag=false;
		}
		retBdl.putBoolean("flag", flag);
		retBdl.putString("msg", msg);
		return retBdl;
	}
	/**
	 * 获取猜拳结果
	 * @param params
	 * @return
	 */
	public Bundle getCQResult(List params) {
		boolean flag=false;
		Bundle retBdl=new Bundle();
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){ //获取个人信息成功
				flag=true;
				retBdl.putBoolean("is_user_join", true);
				JSONObject data=retObj.getJSONObject(APIModel.DATA);
				retBdl.putString("USER_MAT_RESULT", data.getString("USER_MAT_RESULT"));//场次结果
				retBdl.putString("USER_PUNCH_INTEFRAL_CHANGE", data.getString("USER_PUNCH_INTEFRAL_CHANGE"));//用户变动积分
				JSONObject obj=data.getJSONObject("THIS_MAT_RESULT");
				Iterator it=obj.keys();
				while (it.hasNext()) {
					String key=(String) it.next();
					retBdl.putString(key,obj.getString(key));
				}
			}else if("079".equals(retObj.getString(APIModel.RETCODE))){
				flag=true;
				retBdl.putBoolean("is_user_join", false);
			}
		} catch (Exception e) {
			flag=false;
		}
		retBdl.putBoolean("flag", flag);
		return retBdl;
	}
	/**
	 * 获取上一场次信息
	 * @param params
	 * @return
	 */
	public Bundle getCQLastMat(List params) {
		boolean flag=false;
		Bundle retBdl=new Bundle();
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){ //获取个人信息成功
				flag=true;
				JSONObject data=retObj.getJSONObject(APIModel.DATA);
				Iterator it=data.keys();
				while (it.hasNext()) {
					String key=(String) it.next();
					retBdl.putString(key,data.getString(key));
				}
			}
		} catch (Exception e) {
			flag=false;
		}
		retBdl.putBoolean("flag", flag);
		return retBdl;
	}
	/**
	 * 获取系统时间
	 * @param params
	 * @return
	 */
	public Bundle getSysTime(List params){
		boolean flag=false;
		Bundle retBdl=new Bundle();
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){ //获取个人信息成功
				flag=true;
				retBdl.putLong("sys_time", retObj.getLong(APIModel.DATA));
			}
		} catch (Exception e) {
			flag=false;
		}
		retBdl.putBoolean("flag", flag);
		return retBdl;
	}
}
