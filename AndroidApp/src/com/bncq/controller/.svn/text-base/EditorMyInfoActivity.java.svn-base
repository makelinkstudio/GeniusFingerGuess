package com.bncq.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bncq.R;
import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;
import com.bncq.common.DateUtil;
import com.bncq.common.StringUtil;
import com.bncq.common.UIUtil;
import com.bncq.dao.MyInfoDao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.InputType;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;

public class EditorMyInfoActivity extends  Activity{
	//变量
	private MyInfoDao myInfoDao;
	private String sexValue="0";
	private String preSexValue="0";
	private String preAge="";
	private String preQQ="";
	//UI
	private RadioGroup rgp_edt_sex;
	private RadioButton rbtn_sex_man;
	private RadioButton rbtn_set_female;
	private EditText edt_set_age;
	private EditText edt_set_qq;
	private TextView tvw_set_user;
	private TextView tvw_set_email;
	private TextView tvw_set_integral;
	private TextView tvw_set_regTime;
	private ImageButton ibtn_edtInfo_back;
	private TextView tvw_edtInfo_save;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.edt_myinfo_layout);
		//UI
		ibtn_edtInfo_back=(ImageButton)findViewById(R.id.ibtn_edtInfo_back);
		tvw_edtInfo_save=(TextView)findViewById(R.id.tvw_edtInfo_save);
		rgp_edt_sex=(RadioGroup)findViewById(R.id.rgp_edt_sex);
		rbtn_sex_man=(RadioButton) findViewById(R.id.rbtn_sex_man);
		rbtn_set_female=(RadioButton)findViewById(R.id.rbtn_set_female);
		edt_set_age=(EditText)findViewById(R.id.edt_set_age);
		edt_set_qq=(EditText) findViewById(R.id.edt_set_qq);
		tvw_set_user=(TextView) findViewById(R.id.tvw_set_user);
		tvw_set_email=(TextView) findViewById(R.id.tvw_set_email);
		tvw_set_integral=(TextView) findViewById(R.id.tvw_set_integral);
		tvw_set_regTime=(TextView) findViewById(R.id.tvw_set_regTime);
		//变量
		myInfoDao=new MyInfoDao();
		//返回按钮点击事件设置
		ibtn_edtInfo_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {//返回设置页面
				Intent intent =new Intent(getApplication(),MainActivity.class);
				startActivity(intent);
				finish();
			}
		});
		//性别控件点击事件设置
		rgp_edt_sex.setOnCheckedChangeListener(new  OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(RadioGroup rg, int arg1) {
				int selectButtonId=rg.getCheckedRadioButtonId();
				RadioButton  selectButton=(RadioButton) findViewById(selectButtonId);
				if("男".equals(selectButton.getText())){//设置性别值
					sexValue="0";
				}else{
					sexValue="1";
				}
			}
		});
		//输入法为数字
		edt_set_age.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				edt_set_age.setInputType(InputType.TYPE_CLASS_NUMBER);
				return false;
			}
		});
		//输入法为数字
		edt_set_qq.setOnTouchListener(new OnTouchListener() {
			@Override
			public boolean onTouch(View v, MotionEvent event) {
				edt_set_qq.setInputType(InputType.TYPE_CLASS_NUMBER);
				return false;
			}
		});
		//保存按钮点击事件设置
		tvw_edtInfo_save.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				if(!StringUtil.stringiSNotEmpty(edt_set_age.getText())){
					UIUtil.showMsg("请填写年龄");
					return ;
				}else if(!StringUtil.isNumber(edt_set_age.getText())){
					UIUtil.showMsg("请输入年龄数字");
					return ;
				}else if(edt_set_age.getText().length()>2){
					UIUtil.showMsg("请输入正确年龄");
					return ;
				}else if(!StringUtil.stringiSNotEmpty(edt_set_qq.getText())){
					UIUtil.showMsg("请填写QQ");
					return;
				}else if(!StringUtil.isNumber(edt_set_qq.getText())){
					UIUtil.showMsg("请填写正确QQ");
					return;
				}else if(edt_set_qq.getText().length()>16){
					UIUtil.showMsg("请填写正确QQ");
					return;
				}
				//判断是否需要更新
                if(sexValue.equals(preSexValue)&&preAge.equals(edt_set_age.getText().toString())&&preQQ.equals(edt_set_qq.getText().toString())){
					return;
				}
				new Thread(saveMyInfoRun).start();//保存个人设置信息
			}
		});
		//获取用户信息，更新UI
		new Thread(getMyInfoRunnable).start();
	}
	//销毁
	@Override
	protected void onDestroy() {
		super.onDestroy();
		myInfoHandler.removeCallbacks(getMyInfoRunnable);
		saveHandler.removeCallbacks(saveMyInfoRun);
	}
	//访问网络,获取用户信息
	private Runnable getMyInfoRunnable=new Runnable() {
		@Override
		public void run() {
			HashMap map=new HashMap();
			map.put(APIModel.CODE, APIModel.USER_INFO);
			List params=CommonUtil.setParams(map, CommonUtil.DIRECT_LIST);
			Bundle data= myInfoDao.getMyInfo(params);
			Message msg=new Message();
			msg.setData(data);
			myInfoHandler.sendMessage(msg);
		}
	};
	//处理 更新用户信息
    private Handler myInfoHandler=new Handler() {
			@Override
			public void handleMessage(Message msg) {
				try {
					Bundle data=msg.getData();
					boolean flag =data.getBoolean("flag");
					if (flag) {
						if(StringUtil.stringiSNotEmpty(data.get("USER_NAME"))){//用户名
							tvw_set_user.setText(data.getString("USER_NAME"));
						}
						if(StringUtil.stringiSNotEmpty(data.get("USER_EMAIL"))){//邮箱
							tvw_set_email.setText(data.getString("USER_EMAIL"));
						}
						if(StringUtil.stringiSNotEmpty(data.get("INTEGRAL"))){//积分
							tvw_set_integral.setText(data.getString("INTEGRAL"));
						}
						if(StringUtil.stringiSNotEmpty(data.get("REG_TIME"))){//注册时间
							tvw_set_regTime.setText(DateUtil.UnixTimeToDate(data.getString("REG_TIME")));
						}
						if ("1".equals(data.getString("SEX"))) {// 性别
							rbtn_set_female.setChecked(true);
							preSexValue="1";
							sexValue="1";
						} else {
							rbtn_sex_man.setChecked(true);
							preSexValue="0";
							sexValue="0";
						}
						if(StringUtil.stringiSNotEmpty(data.getString("AGE"))){
							edt_set_age.setText(data.getString("AGE").toString());// 年龄
							preAge=data.getString("AGE");
						}
						if(StringUtil.stringiSNotEmpty(data.getString("QQ"))){
							edt_set_qq.setText(data.getString("QQ").toString());// QQ
							preQQ=data.getString("QQ").toString();
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
	};
	//保存个人信息线程处理
	private  Handler saveHandler=new Handler(){
		@Override
		public void handleMessage(Message msg){
			Bundle data=msg.getData();
			Boolean flag=data.getBoolean("flag");
			if(flag){
				UIUtil.showMsg("更新成功");
				Intent intent =new Intent(getApplication(),MainActivity.class);
				startActivity(intent);
				finish();
			}else{
				UIUtil.showMsg("更新失败");
			}
		}
	};
	//保存个人信息线程
	private Runnable saveMyInfoRun=new Runnable() {
		@Override
		public void run() {
			Bundle data=new Bundle();
			Message msg=new Message();
			Map map=new HashMap();
			Map paramsMap=new HashMap();
			map.put("sex",sexValue);
			map.put("age", edt_set_age.getText());
			map.put("qq", edt_set_qq.getText());
			paramsMap.put(APIModel.CODE, APIModel.UPD_USER_INFO);
			paramsMap.put(APIModel.DATA, map);
			List params=CommonUtil.setParams(paramsMap, CommonUtil.ARRAY_LIST);
			data=myInfoDao.saveMyInfo(params);
			msg.setData(data);
			saveHandler.sendMessage(msg);
		}
	};
}
