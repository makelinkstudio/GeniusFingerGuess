package com.bncq.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONObject;

import com.bncq.R;
import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;
import com.bncq.common.DateUtil;
import com.bncq.common.UIUtil;
import com.bncq.dao.LoginRegisterDao;
import com.bncq.dao.MyInfoDao;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.ContactsContract.CommonDataKinds.Nickname;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class MyInfoFragment extends Fragment{
	//变量
	private LoginRegisterDao loginRegisterDao;
	private boolean isInit ;  
	//UI
	private Button btn_set_loginOut;
	private Button btn_show_game;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup,Bundle savedInstanceState) {
		View myInfoLayout= inflater.inflate(R.layout.myinfo_layout, viewGroup, false);
	    btn_show_game=(Button) myInfoLayout.findViewById(R.id.btn_show_game);
	    //变量
	    isInit = true;  
		loginRegisterDao=new LoginRegisterDao();
	    btn_set_loginOut=(Button) myInfoLayout.findViewById(R.id.btn_set_loginOut);
		btn_set_loginOut.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				new Thread(logOutRun).start();
			}
		});
		//跳转游戏
		btn_show_game.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent =new Intent(getActivity().getApplication(),Bnjz_MainActivity.class);
				startActivity(intent);
			}
		});
		return  myInfoLayout;
	}
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		if(getUserVisibleHint()){
			loadData();
		}
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		// 每次切换fragment时调用的方法  
		if(getUserVisibleHint()){
				loadData();
		}
	}
	//初始化数据
	private void loadData() {
		//加载信息
		if (isInit) {  
            isInit = false;//加载数据完成  
		}
	}
	//退出登陆
	private Runnable logOutRun=new Runnable() {
		@Override
		public void run() {
			Message msg=new Message();
			Map map=new HashMap();
			map.put(APIModel.CODE,APIModel.USER_LOGOUT);
			List params=CommonUtil.setParams(map, CommonUtil.DIRECT_LIST);
			loginRegisterDao.logOut(params);
			logOutHandler.sendMessage(msg);
		}
	};
	private Handler logOutHandler=new Handler(){
		public void handleMessage(Message msg) {
			Intent intent =new Intent(getActivity(),LoginActivity.class);
			startActivity(intent);
			getActivity().finish();
		};
	};
}
