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
	 * 搜索post
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
			msg="获取数据失败";
		}
		retBdl.putBoolean("flag", flag);
		retBdl.putString("msg", msg);
		return retBdl;
	}
	/**
	 * 获取话题数据
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
	 * 获取论坛总贴数
	 * @return
	 */
	public String getForumSize(){
		return forumSize;
	}
	/**
	 * 每日签到
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
				msg="签到成功";
			}else if("01".equals(retObj.getString("return"))){
				msg="今天你已经签到过了";
			}
		} catch (Exception e) {
			msg="获取数据失败";
		}
		retBdl.putBoolean("flag", flag);
		retBdl.putString("msg", msg);
		return retBdl;
	}
	/**
	 * 发布话题
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
				msg="发布成功";
			}else{
				msg="发布失败，请稍候再试";
			}
		} catch (Exception e) {
			msg="发布失败，请稍候再试";
		}
		retBdl.putBoolean("flag", flag);
		retBdl.putString("msg", msg);
		return retBdl;
	}
	/**
	 * 获取回复数据
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
	 * 评论话题
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
				msg="回复成功";
			}else{
				msg="回复失败，请稍候再试";
			}
		} catch (Exception e) {
			msg="回复失败，请稍候再试";
		}
		retBdl.putBoolean("flag", flag);
		retBdl.putString("msg", msg);
		return retBdl;
	}
	/**
	 * 获取我的回复话题数据
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
