package com.bncq.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;
import com.bncq.common.HttpClientUtil;

import android.os.Bundle;

public class ShopDao {
	/**
	 * 用户预约商品
	 * @param params
	 * @return 返回是否成功
	 */
	public Bundle setAppoint(List params) {
		Bundle retBdl=new Bundle();
		boolean flag=false;
		String msg="";
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			String returnCode=retObj.getString(APIModel.RETCODE);
			if(APIModel.SUCCEE.equals(returnCode)){
				flag=true;
				msg="预约成功";
			}else if("062".equals(returnCode)){
				msg="积分不够";
			}
		} catch (Exception e) {
			msg="预约失败";
		}
		retBdl.putBoolean("flag", flag);
		retBdl.putString("msg", msg);
		return retBdl;
	}
	/**
	 * 用户收藏商品
	 * @param params
	 * @return 返回是否成功
	 */
	public Bundle setCollect(List params) {
		Bundle retBdl=new Bundle();
		boolean flag=false;
		String msg="";
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			String returnCode=retObj.getString(APIModel.RETCODE);
			if(APIModel.SUCCEE.equals(returnCode)){
				flag=true;
				msg="收藏成功";
			}else{
				msg="收藏失败";
			}
		} catch (Exception e) {
			msg="收藏失败";
		}
		retBdl.putBoolean("flag", flag);
		retBdl.putString("msg", msg);
		return retBdl;
	}
	/**
	 * 用户预约商品
	 * @param params
	 * @return 返回是否成功
	 */
	public Bundle setCancelCollect(List params) {
		Bundle retBdl=new Bundle();
		boolean flag=false;
		String msg="";
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			String returnCode=retObj.getString(APIModel.RETCODE);
			if(APIModel.SUCCEE.equals(returnCode)){
				flag=true;
				msg="取消成功";
			}else {
				msg="取消失败";
			}
		} catch (Exception e) {
			msg="取消失败";
		}
		retBdl.putBoolean("flag", flag);
		retBdl.putString("msg", msg);
		return retBdl;
	}
	/**
	 * 获取兑换大厅数据
	 * @param params
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getShopData(List params){
		ArrayList<HashMap<String,Object>> retList=new ArrayList<HashMap<String,Object>>();
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){
				JSONObject data=(JSONObject) retObj.get(APIModel.DATA);
				JSONArray list=(JSONArray) data.get("AVD_LIST");
				if(list!=null&&list.length()>0){
					for(int i=0;i<list.length();i++){
						JSONObject rowData=list.getJSONObject(i);
						retList.add(CommonUtil.getMapFromJson(rowData));
					}
				}
			}
		} catch (Exception e) {
		}
		return retList;
	}
	/**
	 * 获取我的预约数据
	 * @param params
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getShopMyApptData(List params){
		ArrayList<HashMap<String,Object>> retList=new ArrayList<HashMap<String,Object>>();
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){
				JSONObject data=(JSONObject) retObj.get(APIModel.DATA);
				JSONArray list=(JSONArray) data.get("AVD_LIST");
				if(list!=null&&list.length()>0){
					for(int i=0;i<list.length();i++){
						JSONObject rowData=list.getJSONObject(i);
						retList.add(CommonUtil.getMapFromJson(rowData));
					}
				}
			}
		} catch (Exception e) {
		}
		return retList;
	}
	/**
	 * 获取我的收藏数据
	 * @param params
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getShopCollectionData(List params){
		ArrayList<HashMap<String,Object>> retList=new ArrayList<HashMap<String,Object>>();
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){
				JSONObject data=(JSONObject) retObj.get(APIModel.DATA);
				JSONArray list=(JSONArray) data.get("AVD_LIST");
				if(list!=null&&list.length()>0){
					for(int i=0;i<list.length();i++){
						JSONObject rowData=list.getJSONObject(i);
						retList.add(CommonUtil.getMapFromJson(rowData));
					}
				}
			}
		} catch (Exception e) {
		}
		return retList;
	}
	/**
	 * 获取单个商品数据
	 * @param params
	 * @return
	 */
	public  Bundle getShopDetailData(List params){
		Bundle retBun=new Bundle();
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){
				JSONObject data=retObj.getJSONObject(APIModel.DATA);
				Iterator iterator=data.keys();
				while (iterator.hasNext()) {
					String key=(String) iterator.next();
					retBun.putString(key, data.getString(key));
				}
			}
		} catch (Exception e) {
		}
		return retBun;
	}
}
