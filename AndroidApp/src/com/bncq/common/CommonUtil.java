package com.bncq.common;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.cookie.Cookie;
import org.apache.http.impl.cookie.BasicClientCookie;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.bncq.R;

import android.R.bool;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.Toast;



public class CommonUtil {

	public  static final int DIRECT_LIST=1;//{key1:value1,key2:value2}��ʽ
	public  static final int ARRAY_LIST=2;//{code:"0001",data{key1:value1,key2:value2}}
	private static Toast toast;
	private static Context context;
    
	/**
	 * ��ʼ��context
	 */
	static{
		getContext();
	}

	/**
	 * ��ȡȫ��context
	 * @return
	 */
	public static Context getContext(){
		if(context==null){
			context=MainApplication.getContext();
		}
		return context;
	}
	/**
	 * ��������post��������
	 * @param map ����
	 * @param method post��ʽ
	 * @return post��ʽ����
	 */
	public static List setParams(Map map,int method){
		List returnList=new ArrayList();
		try {
			if(map!=null&&map.size()>0){
				switch (method) {
				case 1:
					Set set= map.entrySet();
					for (Iterator it=set.iterator();it.hasNext();){
						Entry entry=(Entry) it.next();
						returnList.add(new BasicNameValuePair(entry.getKey().toString(),entry.getValue().toString()));
					}
					break;
				case 2:
					returnList.add(new BasicNameValuePair(APIModel.CODE,map.get(APIModel.CODE).toString()));
					Map dataMap=(Map) map.get(APIModel.DATA);
					Set dataSet= dataMap.entrySet();
					for (Iterator it=dataSet.iterator();it.hasNext();){
						Entry entry=(Entry) it.next();
						returnList.add(new BasicNameValuePair(APIModel.DATA+"["+entry.getKey().toString()+"]",entry.getValue().toString()));
					}
				default:
					break;
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return returnList;
	}
	/**
	 * MD5����
	 * @param string
	 * @return ����
	 */
	public static String MD5(String string) {
	    byte[] hash;
	    try {
	        hash = MessageDigest.getInstance("MD5").digest(string.getBytes("UTF-8"));
	    } catch (NoSuchAlgorithmException e) {
	        throw new RuntimeException("Huh, MD5 should be supported?", e);
	    } catch (UnsupportedEncodingException e) {
	        throw new RuntimeException("Huh, UTF-8 should be supported?", e);
	    }
	    StringBuilder hex = new StringBuilder(hash.length * 2);
	    for (byte b : hash) {
	        if ((b & 0xFF) < 0x10) hex.append("0");
	        hex.append(Integer.toHexString(b & 0xFF));
	    }
	    return hex.toString().toUpperCase();
	}
   /**
    * ����Fragment֮���л�
    * @param fragmentManager	��ǰҳ�������
    * @param hideTag		���ض���tag
    * @param showTag	��ʾ����tag
    * @param isSlide		�Ƿ�ԭĬ��ֵ 0:��ԭ 1���л�Ч��
    * @param choose		ѡ���Ƿ�0:hide 1: remove 
    */
   public static void transactionSwitch(FragmentManager fragmentManager,String hideTag,String showTag,String showFraName,String isSlide,String choose){
	   		try {
	   			Fragment hideFragment=fragmentManager.findFragmentByTag(hideTag);
				FragmentTransaction transaction=fragmentManager.beginTransaction();
				if("0".equals(isSlide)){
					transaction.setCustomAnimations(R.animator.slide_normal, R.animator.slide_normal);
				}else if("1".equals(isSlide)){
					transaction.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_right);
				}
				if(hideFragment!=null){
					if("0".equals(choose)){
						transaction.hide(hideFragment);
					}else {
						transaction.remove(hideFragment);
					}
				}
				Fragment showFragment=fragmentManager.findFragmentByTag(showTag);
				if(showFragment==null){
					showFragment= (Fragment) Class.forName("com.bncq.controller."+showFraName).newInstance();
					transaction.add(R.id.content, showFragment,showTag);
				}else {
					transaction.show(showFragment);
				}
				transaction.commit();
				//��¼ ��ǰ���� ��ʾ fragment
				setValueSp("fragment", "hideTag", hideTag);
				setValueSp("fragment", "showTag", showTag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
   }

   /**
    * ����Fragment֮���л�
    * @param fragmentManager	��ǰҳ�������
    * @param hideTag		���ض���tag
    * @param showTag	��ʾ����tag
    * @param isSlide		�Ƿ�ԭĬ��ֵ 0:��ԭ 1���л�Ч��
    * @param choose		ѡ���Ƿ�0:hide 1: remove 
    * @param paramValue ���ݲ���
    */
   public static void transactionSwitch(FragmentManager fragmentManager,String hideTag,String showTag,String showFraName,String isSlide,String choose,Bundle data){
	   		try {
	   			Fragment hideFragment=fragmentManager.findFragmentByTag(hideTag);
				FragmentTransaction transaction=fragmentManager.beginTransaction();
				if("0".equals(isSlide)){
					transaction.setCustomAnimations(R.animator.slide_normal, R.animator.slide_normal);
				}else if("1".equals(isSlide)){
					transaction.setCustomAnimations(R.animator.slide_in_left,R.animator.slide_out_right);
				}
				if(hideFragment!=null){
					if("0".equals(choose)){
						transaction.hide(hideFragment);
					}else {
						transaction.remove(hideFragment);
					}
				}
				Fragment showFragment=fragmentManager.findFragmentByTag(showTag);
				if(showFragment==null){
					showFragment= (Fragment) Class.forName("com.bncq.controller."+showFraName).newInstance();
					transaction.add(R.id.content, showFragment,showTag);
				}else {
					transaction.show(showFragment);
				}
				if(!data.isEmpty()){
					showFragment.setArguments(data);
				}
				transaction.commit();
				//��¼ ��ǰ���� ��ʾ fragment
				setValueSp("fragment", "hideTag", hideTag);
				setValueSp("fragment", "showTag", showTag);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} 
   }
   /**
    * ��ȡsp�д�ֵ
    * @param fileName �����ļ���
    * @param keyName ����keyName
    * @return
    */
   public static String getValueSp(String fileName,String keyName){
	   try{
		   SharedPreferences sp=context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		   return sp.getString(keyName, "");
	   }catch(Exception e){
		   return null;
	   }
   }
   /**
    * д��sp����
    * @param fileName xml����
    * @param keyName 
    * @param KeyValue
    */
   public static void setValueSp(String fileName,String keyName,String KeyValue){
	   try {
		   SharedPreferences sp=context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		   Editor ed=sp.edit();
		   ed.putString(keyName, KeyValue);
		   ed.commit();
	   } catch (Exception e) {
		// TODO: handle exception
	   }
   }
   /**
    * �Ƴ�sp�д洢����
    * @param fileName
    */
   public static void removeSp(String fileName) {
	   try{
		   SharedPreferences sp=context.getSharedPreferences(fileName, context.MODE_PRIVATE);
		   Editor ed=sp.edit();
		   ed.clear();
		   ed.commit();
	   }catch(Exception e){}
   }
	/**
	 * ��ȡcookie
	 * @return
	 */
	public static Cookie getCookie(){
		Map map=context.getSharedPreferences("cookieInfo", context.MODE_PRIVATE).getAll();;
		Iterator it=map.entrySet().iterator();
		BasicClientCookie cookie = null;
		while(it.hasNext()){
			Entry entry=(Entry) it.next();
			String name=entry.getKey().toString();
			String value=entry.getValue().toString();
			if("Version".equals(name)){
				cookie.setVersion(Integer.valueOf(value));
			}else if("Domain".equals(name)){
				cookie.setDomain(value);
			}else if("Path".equals(name)){
				cookie.setPath(value);
			}else{
				cookie =new BasicClientCookie(name, value);
			}
		}
		return cookie;
	}
	/**
	 * jsonתmap
	 * @param object
	 * @return
	 */
	public static HashMap<String, Object> getMapFromJson(JSONObject object){
		HashMap<String, Object> map=new HashMap<String, Object>();
		try {
			Iterator it=object.keys();
			while (it.hasNext()) {
				String key=String.valueOf(it.next());
				Object value=object.get(key);
				map.put(key, value);
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return map;
	}
	/**
	 * ��ʼ��Post����Map
	 * @param code ���÷�������
	 * @param dataMap �����б�
	 * @return
	 */
	public static Map initPostMap(String code,Map dataMap){
		HashMap map=new HashMap();
		map.put(APIModel.CODE, code);
		map.put(APIModel.DATA, dataMap);
		return map;
	}
}
