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
	 * �ж��Ƿ��½
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
				SharedPreferences sp=context.getSharedPreferences("userInfo",context.MODE_PRIVATE);		//д�뱾�ػ���
				Editor ed=sp.edit();
				ed.putString("USER_ID", data.getString("USER_ID"));//��¼�û�ID
				ed.putString("USER_NAME",data.getString("USER_NAME"));	//�û���
				ed.putString("USER_EMAIL",data.getString("USER_EMAIL"));	//�û���
				ed.commit();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return flag;
	}
	
	/**
	 * �û���¼
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
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){	//��¼�ɹ�
				if(retObj.get("data")!=null&&!"".equals(retObj.get("data"))){
					JSONObject data=retObj.getJSONObject("data");
					SharedPreferences sp=context.getSharedPreferences("userInfo",context.MODE_PRIVATE);		//д�뱾�ػ���
					Editor ed=sp.edit();
					ed.putString("USER_ID", data.getString("USER_ID"));//��¼�û�ID
					ed.putString("USER_NAME",data.getString("USER_NAME"));	//�û���
					ed.putString("USER_EMAIL",data.getString("USER_EMAIL"));	//�û���
					ed.commit();
				}
				msg="��¼�ɹ�";
				flag=true;
			}else{
				if("005".equals(retObj.getString(APIModel.RETCODE))){
					msg="�û���������";
				}else if("006".equals(retObj.getString(APIModel.RETCODE))){
					msg="�������";
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			msg="��¼ʧ��";
		}
		retMap.put("flag", flag);
		retMap.put("msg", msg);
		return retMap;
	}
	/**
	 * �˳���¼
	 * @param params
	 * @return
	 */
	public Map logOut(List params){
		boolean flag=false;
		Map retMap=new HashMap();
		String msg="";
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.get(APIModel.RETCODE))){//�˳��ɹ�
				flag=true;
			}else{
				msg="�˳�ʧ��";
			}
		} catch (Exception e) {
			msg="�˳�ʧ��";
		}
		CommonUtil.removeSp("userInfo");//�����û���Ϣ
		CommonUtil.removeSp("cookieInfo");//����cookie����
		retMap.put("flag", flag);
		retMap.put("msg", msg);
		return retMap;
	}
	/**
	 * ע��
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
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){	//ע��ɹ�����¼�ɹ�
				SharedPreferences sp=context.getSharedPreferences("userInfo",context.MODE_PRIVATE);		//д�뱾�ػ���
				Editor ed=sp.edit();
				if(retObj.get("data")!=null&&!"".equals(retObj.get("data"))){
					JSONObject dataObj=retObj.getJSONObject("data");
					ed.putString("USER_ID", dataObj.getString("USER_ID"));//��¼�û�ID
					ed.putString("USER_NAME",dataObj.getString("USER_NAME"));	//�û���
					ed.putString("USER_EMAIL", dataObj.getString("USER_EMAIL"));	//��¼����ʱ��
				}
				ed.commit();
				msg="ע��ɹ�";
				flag=true;
			}else{
				String retCode=retObj.getString(APIModel.RETCODE);
				if("009".equals(retCode)){
					msg="���������Ѵ���";
				}else if("010".equals(retCode)){
					msg="�����û����Ѵ���";
				}else{
					msg="ע��ʧ��";
				}
			}
		} catch (Exception e) {
			msg="ע��ʧ��";
		}
		retMap.put("flag", flag);
		retMap.put("msg", msg);
		return retMap;
	}
}
