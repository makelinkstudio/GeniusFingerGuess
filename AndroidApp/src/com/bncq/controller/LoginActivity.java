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
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends Activity{
	private EditText edtUserCode;
	private EditText edtPassword;
	private CheckBox cbx_login_remPass;
	private TextView tvw_login_lossPass;
	private Button btn_login_Login;
	private TextView btn_login_register;
	//����
	private LoginRegisterDao loginRegisterDao;
	 @Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.login_layout);
		//��ʼ��
		edtUserCode=(EditText) findViewById(R.id.edtUserCode);
		edtPassword=(EditText) findViewById(R.id.edtPassword);
		cbx_login_remPass=(CheckBox) findViewById(R.id.cbx_login_remPass);
		tvw_login_lossPass=(TextView) findViewById(R.id.tvw_login_lossPass);
		btn_login_Login=(Button) findViewById(R.id.btn_login_Login);
		btn_login_register=(TextView) findViewById(R.id.tvw_login_register);
		loginRegisterDao=new LoginRegisterDao();
		//�����¼
		btn_login_Login.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//����ֵУ��
				String user=edtUserCode.getText().toString().trim();
				String passwd=edtPassword.getText().toString().trim();
				if(!StringUtil.stringiSNotEmpty(user)){
					UIUtil.showMsg("�û�������Ϊ��");
					return;
				}else if(!StringUtil.stringiSNotEmpty(passwd)){
					UIUtil.showMsg("���벻��Ϊ��");
					return;
				}
				else if(user.contains("@")){
					if(!StringUtil.checkEmail(user)){
						UIUtil.showMsg("�����ʽ����ȷ");
						return;
					}
				}
				//�ж��Ƿ��ס����
				if(cbx_login_remPass.isChecked()){
					SharedPreferences sp=getSharedPreferences("userInfo", MODE_PRIVATE);//������ȡ�Ƿ��ס����
					Editor editor=sp.edit();
					editor.putBoolean("remindPass", true);
					editor.commit();
				}
				new Thread(setLoginRun).start();
			}
		});
		//ע��
		btn_login_register.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//��ת��ע�����
				Intent intent=new Intent(getApplication(),RegisterActivity.class);
				startActivity(intent);
			}
		});
	}
	private  Handler setLoginhandler=new Handler(){
			@Override
		    public  void handleMessage(Message msg) {
		        super.handleMessage(msg);
		        Bundle data = msg.getData();//��ȡ������Ϣ
		        Boolean flag = data.getBoolean("flag");
		        String retMsg=data.getString("msg");
		        if(flag){			//�ɹ�����ת������Ϣҳ��
		        	Intent intent=new Intent(getApplication(),MainActivity.class);
		        	startActivity(intent);
		        	finish();
		        }
		        UIUtil.showMsg(retMsg);	//��¼�ɹ������ʾ
		    }
		};
		//�¿��߳�
	private	Runnable setLoginRun=new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				 Message msg = new Message();
			     Bundle data = new Bundle();
				 Map map=new HashMap();
				 map.put("user", edtUserCode.getText());
				 map.put("passwd", edtPassword.getText().toString());
				 List params=CommonUtil.setParams(CommonUtil.initPostMap(APIModel.USER_LOGIN, map), CommonUtil.ARRAY_LIST);//��ֵ��¼
			     Map retMap=loginRegisterDao.setLogin(params);
			     data.putBoolean("flag",(Boolean) retMap.get("flag"));
			     data.putString("msg",retMap.get("msg").toString());
			     msg.setData(data);
			     setLoginhandler.sendMessage(msg);
			}
		};	 
}
