package com.bncq.dao;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;
import com.bncq.common.HttpClientUtil;
import com.bncq.common.MainApplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;



public class LoginRegisterDao  {

	/**
	 * 判断是否登陆
	 * @return
	 */
	public boolean isLogin() {
		boolean flag=false;
		try {
			Map map=new HashMap();
			map.put(APIModel.CODE, APIModel.USER_INFO);
			List paramsList=CommonUtil.setParams(map, CommonUtil.DIRECT_LIST);
			JSONObject obj=HttpClientUtil.getRespond(paramsList);
			if(APIModel.SUCCEE.equals(obj.getString(APIModel.RETCODE))){
				Context context=CommonUtil.getContext();
				flag=true;
				JSONObject data=obj.getJSONObject("data");
				SharedPreferences sp=context.getSharedPreferences("userInfo",context.MODE_PRIVATE);		//写入本地缓存
				Editor ed=sp.edit();
				ed.putString("USER_ID", data.getString("USER_ID"));//记录用户ID
				ed.putString("USER_NAME",data.getString("USER_NAME"));	//用户名
				ed.putString("USER_EMAIL",data.getString("USER_EMAIL"));	//用户名
				ed.commit();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
	}
	
	/**
	 * 用户登录
	 * @param params
	 * @return
	 */
	public Map setLogin(List params){
		boolean flag=false;
		Map retMap=new HashMap();
		String msg="";
		Context context=CommonUtil.getContext();
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){	//登录成功
				if(retObj.get("data")!=null&&!"".equals(retObj.get("data"))){
					JSONObject data=retObj.getJSONObject("data");
					SharedPreferences sp=context.getSharedPreferences("userInfo",context.MODE_PRIVATE);		//写入本地缓存
					Editor ed=sp.edit();
					ed.putString("USER_ID", data.getString("USER_ID"));//记录用户ID
					ed.putString("USER_NAME",data.getString("USER_NAME"));	//用户名
					ed.putString("USER_EMAIL",data.getString("USER_EMAIL"));	//用户名
					ed.commit();
				}
				msg="登录成功";
				flag=true;
			}else{
				if("005".equals(retObj.getString(APIModel.RETCODE))){
					msg="用户名不存在";
				}else if("006".equals(retObj.getString(APIModel.RETCODE))){
					msg="密码错误";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			msg="登录失败";
		}
		retMap.put("flag", flag);
		retMap.put("msg", msg);
		return retMap;
	}
	/**
	 * 退出登录
	 * @param params
	 * @return
	 */
	public Map logOut(List params){
		boolean flag=false;
		Map retMap=new HashMap();
		String msg="";
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.get(APIModel.RETCODE))){//退出成功
				flag=true;
			}else{
				msg="退出失败";
			}
		} catch (Exception e) {
			msg="退出失败";
		}
		CommonUtil.removeSp("userInfo");//清理用户信息
		CommonUtil.removeSp("cookieInfo");//清理cookie缓存
		retMap.put("flag", flag);
		retMap.put("msg", msg);
		return retMap;
	}
	/**
	 * 注册
	 * @param params
	 * @param context
	 * @return
	 */
	public Map register(List params){
		boolean flag=false;
		Map retMap=new HashMap();
		String msg="";
		Context context=CommonUtil.getContext();
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){	//注册成功，登录成功
				SharedPreferences sp=context.getSharedPreferences("userInfo",context.MODE_PRIVATE);		//写入本地缓存
				Editor ed=sp.edit();
				if(retObj.get("data")!=null&&!"".equals(retObj.get("data"))){
					JSONObject dataObj=retObj.getJSONObject("data");
					ed.putString("USER_ID", dataObj.getString("USER_ID"));//记录用户ID
					ed.putString("USER_NAME",dataObj.getString("USER_NAME"));	//用户名
					ed.putString("USER_EMAIL", dataObj.getString("USER_EMAIL"));	//记录结束时间
				}
				ed.commit();
				msg="注册成功";
				flag=true;
			}else{
				String retCode=retObj.getString(APIModel.RETCODE);
				if("009".equals(retCode)){
					msg="输入邮箱已存在";
				}else if("010".equals(retCode)){
					msg="输入用户名已存在";
				}else{
					msg="注册失败";
				}
			}
		} catch (Exception e) {
			msg="注册失败";
		}
		retMap.put("flag", flag);
		retMap.put("msg", msg);
		return retMap;
	}
}
