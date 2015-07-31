package com.bncq.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONException;
import org.json.JSONObject;

import com.bncq.R;
import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;
import com.bncq.common.MainApplication;
import com.bncq.dao.CaiQuanDao;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class UpdateMyInfoManager {
	
	private static TextView tvw_cq_user;
	private static TextView tvw_cq_userInfo;
	private static TextView tvw_cq_userJF;
	
	private Context context;
	private static LayoutInflater layoutInflater;
	private  static View mainView;
	public  UpdateMyInfoManager(Context context) {
		this.context=context;
		layoutInflater=LayoutInflater.from(context);
	}
	
	public  void updateMyInfo(){
		View view=layoutInflater.inflate(R.layout.activity_main, null);
		tvw_cq_user=(TextView) view.findViewById(R.id.tvw_cq_user);
		tvw_cq_userInfo=(TextView) view.findViewById(R.id.tvw_cq_userInfo);
		tvw_cq_userJF=(TextView) view.findViewById(R.id.tvw_cq_userJF);
		new Thread(getCQUserInfo).start();
		Timer timer=new Timer();
		TimerTask task=new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				getCQUserHandler.removeCallbacks(getCQUserInfo);
			}
		};
		timer.schedule(task, 8000);
	}

	// 获取猜拳用户基本信息
	private  Runnable getCQUserInfo = new Runnable() {
		@Override
		public void run() {
			Bundle data = new Bundle();
			Message msg = new Message();
			Map map = new HashMap();
			map.put(APIModel.CODE, APIModel.CQ_GETUSERINF);
			List params = CommonUtil.setParams(map, CommonUtil.DIRECT_LIST);
			CaiQuanDao caiQuanDao=new CaiQuanDao();
			data = caiQuanDao.getCQUsesrInfo(params);
			msg.setData(data);
			getCQUserHandler.sendMessage(msg);
		}
	};
	// 处理猜拳用户基本信息UI更新
	private  Handler getCQUserHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			boolean flag = data.getBoolean("flag");
			if (flag) {
				try {
					JSONObject obj = new JSONObject(data.getString(APIModel.DATA));
					JSONObject winRetObj = new JSONObject(obj.getString("RECORD"));
					tvw_cq_user.setText(obj.getString("USER_NAME"));
					tvw_cq_userInfo.setText("LV "
							+ winRetObj.getString("WIN_COUNT") + "胜 "
							+ winRetObj.getString("DRAW_COUNT") + "平 "
							+ winRetObj.getString("LOST_COUNT") + "负");
					String value=(String) tvw_cq_userJF.getText();
					tvw_cq_userJF.setText("积分：" + obj.getString("INTEGRAL")
							+ " 经验：");
					Intent intent = new Intent();  
			        intent.setAction("action.refreshMyInfo");  
			        context.sendBroadcast(intent);
				} catch (JSONException e) {
					e.printStackTrace();
				}
			}
		};
	};
}
