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
	 * �û�ԤԼ��Ʒ
	 * @param params
	 * @return �����Ƿ�ɹ�
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
				msg="ԤԼ�ɹ�";
			}else if("062".equals(returnCode)){
				msg="���ֲ���";
			}
		} catch (Exception e) {
			msg="ԤԼʧ��";
		}
		retBdl.putBoolean("flag", flag);
		retBdl.putString("msg", msg);
		return retBdl;
	}
	/**
	 * �û��ղ���Ʒ
	 * @param params
	 * @return �����Ƿ�ɹ�
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
				msg="�ղسɹ�";
			}else{
				msg="�ղ�ʧ��";
			}
		} catch (Exception e) {
			msg="�ղ�ʧ��";
		}
		retBdl.putBoolean("flag", flag);
		retBdl.putString("msg", msg);
		return retBdl;
	}
	/**
	 * �û�ԤԼ��Ʒ
	 * @param params
	 * @return �����Ƿ�ɹ�
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
				msg="ȡ���ɹ�";
			}else {
				msg="ȡ��ʧ��";
			}
		} catch (Exception e) {
			msg="ȡ��ʧ��";
		}
		retBdl.putBoolean("flag", flag);
		retBdl.putString("msg", msg);
		return retBdl;
	}
	/**
	 * ��ȡ�һ���������
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
	 * ��ȡ�ҵ�ԤԼ����
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
	 * ��ȡ�ҵ��ղ�����
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
	 * ��ȡ������Ʒ����
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
