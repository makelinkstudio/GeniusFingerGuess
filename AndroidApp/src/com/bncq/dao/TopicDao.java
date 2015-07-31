package com.bncq.dao;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;
import com.bncq.common.HttpClientUtil;

import android.R.integer;
import android.os.Bundle;

public class TopicDao {
	private String forumSize;
	/**
	 * ����post
	 * @param params
	 * @return
	 */
	public Bundle getSearch(List params){
		Bundle retBdl=new Bundle();
		boolean flag=false;
		String msg="";
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.STATUS))){
				flag=true;
				msg="ok";
			}
		} catch (Exception e) {
			msg="��ȡ����ʧ��";
		}
		retBdl.putBoolean("flag", flag);
		retBdl.putString("msg", msg);
		return retBdl;
	}
	/**
	 * ��ȡ��������
	 * @param params
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> getForumData(List params) {
		ArrayList<HashMap<String,Object>> retList=new ArrayList<HashMap<String,Object>>();
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){
				JSONObject data=(JSONObject) retObj.get(APIModel.DATA);
				forumSize=data.getString("TOPIC_COUNT");
				JSONArray topicList=(JSONArray) data.get("TOPIC_LIST");
				if(topicList!=null&&topicList.length()>0){
					for(int i=0;i<topicList.length();i++){
						JSONObject rowData=topicList.getJSONObject(i);
						retList.add(CommonUtil.getMapFromJson(rowData));
					}
				}
			}
		} catch (Exception e) {
		}
		return retList;
	}
	/**
	 * ��ȡ��̳������
	 * @return
	 */
	public String getForumSize(){
		return forumSize;
	}
	/**
	 * ÿ��ǩ��
	 * @param Params
	 * @return
	 */
	public Bundle setSign(List params){
		Bundle retBdl=new Bundle();
		boolean flag=false;
		String msg="";
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.STATUS))){
				flag=true;
				msg="ǩ���ɹ�";
			}else if("01".equals(retObj.getString("return"))){
				msg="�������Ѿ�ǩ������";
			}
		} catch (Exception e) {
			msg="��ȡ����ʧ��";
		}
		retBdl.putBoolean("flag", flag);
		retBdl.putString("msg", msg);
		return retBdl;
	}
	/**
	 * ��������
	 * @param params
	 * @return
	 */
	public Bundle release(List params){
		Bundle retBdl=new Bundle();
		boolean flag=false;
		String msg="";
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){
				flag=true;
				msg="�����ɹ�";
			}else{
				msg="����ʧ�ܣ����Ժ�����";
			}
		} catch (Exception e) {
			msg="����ʧ�ܣ����Ժ�����";
		}
		retBdl.putBoolean("flag", flag);
		retBdl.putString("msg", msg);
		return retBdl;
	}
	/**
	 * ��ȡ�ظ�����
	 * @param params
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> getTopicDetData(List params) {
		ArrayList<HashMap<String,Object>> retList=new ArrayList<HashMap<String,Object>>();
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){
				JSONObject data=(JSONObject) retObj.get(APIModel.DATA);
				JSONArray topicList=(JSONArray) data.get("REPLY");
				if(topicList!=null&&topicList.length()>0){
					for(int i=0;i<topicList.length();i++){
						JSONObject rowData=topicList.getJSONObject(i);
						retList.add(CommonUtil.getMapFromJson(rowData));
					}
				}
			}
		} catch (Exception e) {
			
		}
		return retList;
	}
	/**
	 * ���ۻ���
	 * @param params
	 * @return
	 */
	public Bundle topicDetReply(List params){
		Bundle retBdl=new Bundle();
		boolean flag=false;
		String msg="";
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){
				flag=true;
				msg="�ظ��ɹ�";
			}else{
				msg="�ظ�ʧ�ܣ����Ժ�����";
			}
		} catch (Exception e) {
			msg="�ظ�ʧ�ܣ����Ժ�����";
		}
		retBdl.putBoolean("flag", flag);
		retBdl.putString("msg", msg);
		return retBdl;
	}
	/**
	 * ��ȡ�ҵĻظ���������
	 * @param params
	 * @return
	 */
	public ArrayList<HashMap<String,Object>> getMyReplyTopData(List params) {
		ArrayList<HashMap<String,Object>> retList=new ArrayList<HashMap<String,Object>>();
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){
				JSONObject data=(JSONObject) retObj.get(APIModel.DATA);
				JSONArray topicList=(JSONArray) data.get("TOPIC_LIST");
				if(topicList!=null&&topicList.length()>0){
					for(int i=0;i<topicList.length();i++){
						JSONObject rowData=topicList.getJSONObject(i);
						retList.add(CommonUtil.getMapFromJson(rowData));
					}
				}
			}
		} catch (Exception e) {
			
		}
		return retList;
	}
}
