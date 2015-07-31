package com.bncq.dao;

import java.util.Iterator;
import java.util.List;
import org.json.JSONObject;
import android.os.Bundle;
import com.bncq.common.APIModel;
import com.bncq.common.HttpClientUtil;

public class MyInfoDao {

	/**
	 * 获取用户数据
	 * @param params
	 * @return
	 */
	public Bundle getMyInfo(List params) {
		boolean flag=false;
		Bundle retBdl=new Bundle();
		String msg="";
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){ //获取个人信息成功
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
     * 更新个人信息
     * @param params
     * @return
     */
	public Bundle saveMyInfo(List params){
		boolean flag=false;
		Bundle retBundle=new Bundle();
		String msg="";
		try {
			JSONObject retObj=HttpClientUtil.getRespond(params);
			if(APIModel.SUCCEE.equals(retObj.getString(APIModel.RETCODE))){ //更新个人信息成功
				msg="保存成功";
				flag=true;
			}else{
				msg="更新失败";
			}
		}catch (Exception e) {
			msg="更新失败";
		}
		retBundle.putBoolean("flag", flag);
		retBundle.putString("msg", msg);
		return retBundle;
	}
}
