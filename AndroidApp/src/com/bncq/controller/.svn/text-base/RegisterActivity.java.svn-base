package com.bncq.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bncq.R;
import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;
import com.bncq.common.StringUtil;
import com.bncq.common.UIUtil;
import com.bncq.dao.LoginRegisterDao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends Activity {
	private TextView tvw_register_back;
	private Button btn_register_register;
	private EditText edt_register_user; 
	private EditText edt_register_email;
	private EditText edt_register_passwd; 
	private EditText edt_register_passwd_again;
	//变量
	private LoginRegisterDao loginRegisterDao; 
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.register_layout);
		edt_register_user=(EditText) findViewById(R.id.edt_register_user);
		edt_register_email=(EditText) findViewById(R.id.edt_register_email);
		edt_register_passwd=(EditText) findViewById(R.id.edt_register_passwd);
		edt_register_passwd_again=(EditText) findViewById(R.id.edt_register_passwd_again);
		btn_register_register=(Button) findViewById(R.id.btn_register_register);
		tvw_register_back=(TextView) findViewById(R.id.tvw_register_back);
		//变量
		 loginRegisterDao=new LoginRegisterDao();
		//返回登录界面
		tvw_register_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getApplication(),LoginActivity.class);
				startActivity(intent);
				finish();
			}
		});
		btn_register_register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				   String user=edt_register_user.getText().toString().trim();
				   String email=edt_register_email.getText().toString().trim();
				   String psdFirst=edt_register_passwd.getText().toString().trim();
				   String psdSec=edt_register_passwd_again.getText().toString().trim();
				   if(!StringUtil.stringiSNotEmpty(user)){
					   UIUtil.showMsg("账号不能为空");
					   return;
				   }else if(!StringUtil.stringiSNotEmpty(email)){
					   UIUtil.showMsg("邮箱不能为空");
					   return ;
				   }
				   else if(!StringUtil.checkEmail(email)){
					   UIUtil.showMsg("请输入正确邮箱格式");
					   return ;
				   }
				   else if(!StringUtil.stringiSNotEmpty(psdFirst)){
					   UIUtil.showMsg("请输入密码");
					   return ;
				   }else if(!StringUtil.stringiSNotEmpty(psdSec)){
					   UIUtil.showMsg("请再次输入密码");
					   return ;
				   }
				   //判断密码输入是否正确
				   else if(!psdFirst.equals(psdSec)){
					   UIUtil.showMsg("两次输入密码不一致");
					   return ;
				   }
				   new Thread(registerRun).start();
			}
		});
	}
    //处理
	private  Handler registerHandler=new Handler(){
		   @Override
		   public void  handleMessage(Message msg) {
			   super.handleMessage(msg);
			   Bundle data=msg.getData();
			   boolean flag=data.getBoolean("flag");
			   String retMsg=data.getString("msg");
			   if(flag){	//成功后跳转主界面
				   	 Intent intent=new Intent(getApplication(),MainActivity.class);
				   	 startActivity(intent);
				   	 finish();
			   }
			   UIUtil.showMsg(retMsg);// 返回提交后 信息
		   }
	   };
	  //新建线程访问网络
	  private Runnable registerRun =new Runnable() {
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Message msg=new Message();
			Bundle data=new Bundle();
			Map map=new HashMap();
			map.put("username", edt_register_user.getText());
			map.put("email", edt_register_email.getText());
			map.put("passwd", edt_register_passwd.getText().toString());
			List params=CommonUtil.setParams(CommonUtil.initPostMap(APIModel.USER_REGIST, map), CommonUtil.ARRAY_LIST);
			Map retMap=loginRegisterDao.register(params);//注册
			data.putBoolean("flag", (Boolean) retMap.get("flag"));
			data.putString("msg", retMap.get("msg").toString());
			msg.setData(data);
			registerHandler.sendMessage(msg);
		}
	};
}
