package com.bncq.dao;

import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;
import android.os.Bundle;
import com.bncq.common.APIModel;
import com.bncq.common.HttpClientUtil;

public class MyInfoDao {

	/**
	 * ��ȡ�û�����
	 * @param params
	 * @return
	 */
	public Bundle getMyInfo(List params) {
		boolean flag=false;
		Bundle retBdl=new Bundle();
		String msg="";
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){ //��ȡ������Ϣ�ɹ�
				flag=true;
				JSONObject obj=retObj.getJSONObject(APIModel.DATA);
				Iterator it=obj.keys();
				while (it.hasNext()) {
					String key=(String) it.next();
					retBdl.putString(key,obj.getString(key));
				}
			}
		}catch (Exception e) {
		}
		retBdl.putBoolean("flag", flag);
		return retBdl;
	}
    /**
     * ���¸�����Ϣ
     * @param params
     * @return
     */
	public Bundle saveMyInfo(List params){
		boolean flag=false;
		Bundle retBundle=new Bundle();
		String msg="";
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){ //���¸�����Ϣ�ɹ�
				msg="����ɹ�";
				flag=true;
			}else{
				msg="����ʧ��";
			}
		}catch (Exception e) {
			msg="����ʧ��";
		}
		retBundle.putBoolean("flag", flag);
		retBundle.putString("msg", msg);
		return retBundle;
	}
}
