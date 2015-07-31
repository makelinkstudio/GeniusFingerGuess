package com.bncq.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;
import com.bncq.common.HttpClientUtil;

import android.os.Bundle;

public class MessageDao {

	/**
	 * 获取推送信息
	 * @param params
	 * @return
	 */
	public Bundle getPushMsg(List params){
		Bundle retBdl=new Bundle();
		boolean flag=false;
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){
				flag=true;
				retBdl.putString(APIModel.DATA, retObj.getString(APIModel.DATA));
			}
		} catch (Exception e) {
			flag=false;
		}
		retBdl.putBoolean("flag", flag);
		return retBdl;
	}
	/**
	 * 获取信息
	 * @param params
	 * @return
	 */
	public ArrayList<HashMap<String, Object>> getMyNoticeMsg(List params){
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
}
