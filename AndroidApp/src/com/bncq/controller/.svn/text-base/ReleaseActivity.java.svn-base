package com.bncq.controller;

import java.util.HashMap;

import com.bncq.R;
import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;
import com.bncq.common.UIUtil;
import com.bncq.dao.TopicDao;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ReleaseActivity extends Activity {
	//UI
	private ImageButton ibtn_release_back;
	private TextView tvw_release_rel;
	private EditText edt_release_con;
	private TextView tvw_word_left;
	private LinearLayout llt_main_top;
	private LinearLayout llt_main_userInfo;
	private LinearLayout llt_bottom_layout;//底层操作栏
	//变量
	private static FragmentManager fragmentManager;
	private TopicDao topicDao;
	private int leftNum;
	private Handler updateHandler;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.release_layout);
		//UI
		ibtn_release_back=(ImageButton)findViewById(R.id.ibtn_release_back);
		tvw_release_rel=(TextView)findViewById(R.id.tvw_release_rel);
		edt_release_con=(EditText) findViewById(R.id.edt_release_con);
		tvw_word_left=(TextView)findViewById(R.id.tvw_word_left);
		//变量
		updateHandler=new Handler();
		topicDao=new TopicDao();
		//弹出输入法自适应
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		//返回
		ibtn_release_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIUtil.hideSoftInput(getWindow());
				Intent intent =new Intent(getApplication(),MainActivity.class);
				startActivity(intent);
				getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
				finish();
			}
		});
		//发布
		tvw_release_rel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(leftNum>=0){
					new Thread(releaseRun).start();
				}else{
					UIUtil.showMsg("能不能精简点，我压力好大");
				}
			}
		});
		//输入内容监听
		edt_release_con.addTextChangedListener(new TextWatcher() {
			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) {
				// TODO Auto-generated method stub
			}
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,int after) {
				// TODO Auto-generated method stub
			}
			@Override
			public void afterTextChanged(Editable s) {
				// TODO Auto-generated method stub
				leftNum=150-s.length();
				updateHandler.post(new Runnable() {
					@Override
					public void run() {
						tvw_release_rel.setTextColor(getResources().getColor(R.color.gloden));
						tvw_word_left.setText(String.valueOf(leftNum));//更改可输入内容字数
					}
				});
			}
		});
	}
	//销毁
	@Override
	protected void onDestroy() {
		super.onDestroy();
		releaseHandler.removeCallbacks(releaseRun);
	}
	//处理发布话题线程
	private  Handler releaseHandler=new Handler(){
		@Override
		public void handleMessage(Message msg){
			Bundle data=msg.getData();
			boolean flag=data.getBoolean("flag");
			String retMsg=data.getString("msg");
			if(flag){
				UIUtil.hideSoftInput(getWindow());
				Intent intent =new Intent(getApplication(),MainActivity.class);
				startActivity(intent);
				getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
			}
			UIUtil.showMsg(retMsg);
		}
	};
	//开启发布话题线程
	private Runnable releaseRun=new Runnable() {
		@Override
		public void run() {
			Bundle data=new Bundle();
			Message msg=new Message();
			if(edt_release_con.getText()!=null){
				HashMap map=new HashMap();
				String topic_con=edt_release_con.getText().toString();
				map.put("topic_con", topic_con);
				data=topicDao.release(CommonUtil.setParams(CommonUtil.initPostMap(APIModel.TOPIC_RELEASE, map), CommonUtil.ARRAY_LIST));
			}
			msg.setData(data);
			releaseHandler.sendMessage(msg);
		}
	};
}
