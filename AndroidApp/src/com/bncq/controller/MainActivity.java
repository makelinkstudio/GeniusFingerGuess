package com.bncq.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import com.bncq.R;
import com.bncq.R.color;
import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;
import com.bncq.dao.CaiQuanDao;
import com.bncq.dao.LoginRegisterDao;
import com.bncq.service.MessageService;
import com.bncq.service.UpdateManager;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.animation.ObjectAnimator;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.TextView;

public class MainActivity extends FragmentActivity  implements OnClickListener{	
		//顶部
		private TextView tvw_cq_user;//用户
		private TextView tvw_cq_userInfo;//等级 胜率
		private TextView tvw_cq_userJF;//积分栏
		//首页界面布局
		private View shopLayout;
		//猜拳界面布局
		private View caiquanLayout;
		//发现界面布局
		private View topicLayout;
		//我 界面布局
		private View myInfoLayout;
		//首页标题控件
		private TextView shopText;
		//首页标题控件
		private TextView caiquanText;
		//首页标题控件
		private TextView topicText;
		//首页标题控件
		private TextView myInfoText;
		
		//变量
		private FragmentManager fragmentManager;
		private LoginRegisterDao loginRegisterDao;
		private CaiQuanDao caiQuanDao;
		private int preSelectNum=0;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			//requestWindowFeature(Window.FEATURE_NO_TITLE);//没有原生title
			//判断是否登录
			SharedPreferences sp=getSharedPreferences("userInfo", MODE_PRIVATE);
			boolean isRemind=sp.getBoolean("remindPass", false);
			if(isRemind){//校验是否过期
				new Thread(isLoginRun).start();
				//缓和加载主页
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}else{//没有记住密码则每次都要登录
				Intent intent =new Intent(getApplication(),LoginActivity.class);
				startActivity(intent);
				finish();
			}
			//开始加载主页
			setContentView(R.layout.activity_main);
			// 初始化布局元素 
			initViews();
			//清理缓存
			removeData();
			//注册广播
			registerBroadcase();
			//获取顶部用户信息
			new Thread(getCQUserInfo).start();
		    // 第一次启动时选中第0个tab  
			setTabSelection(0);
		}
		//通知栏跳转
		@Override
		protected void onNewIntent(Intent intent) {
			super.onNewIntent(intent);
			boolean flag=intent.getBooleanExtra("msgJump", false);
			if(flag){
				setTabSelection(3);
			}
		}
		@Override
		protected void onStart() {
			//这里来检测版本是否需要更新  
//			UpdateManager  mUpdateManager = new UpdateManager(this);  
//	        mUpdateManager.checkUpdateInfo();  
			//startService(new Intent(this,MessageService.class));
			super.onStart();
		}
		@Override
		protected void onStop() {
			super.onStop();
			stopService(new Intent(this,MessageService.class));
		}
		@Override
		protected void onDestroy() {
		// TODO Auto-generated method stub
			super.onDestroy();
			stopService(new Intent(this,MessageService.class));//结束服务
			getCQUserHandler.removeCallbacks(getCQUserInfo);
			unregisterReceiver(mRefreshMyInfo);//取消注册广播
			finish();
		}
		@Override
		public void onBackPressed() {
		// TODO Auto-generated method stub
			super.onBackPressed();//主页直接退出
		}
		//初始化底部变量
		private void initViews() {
			//顶部组件
			tvw_cq_user = (TextView)findViewById(R.id.tvw_cq_user);
			tvw_cq_userInfo = (TextView)findViewById(R.id.tvw_cq_userInfo);
			tvw_cq_userJF = (TextView)findViewById(R.id.tvw_cq_userJF);
			//底部组件
			shopLayout=findViewById(R.id.shop_layout);
			caiquanLayout=findViewById(R.id.caiquan_layout);
			topicLayout=findViewById(R.id.topic_layout);
			myInfoLayout=findViewById(R.id.setting_layout);
			shopText = (TextView) findViewById(R.id.shop_text);
			caiquanText = (TextView) findViewById(R.id.caiquan_text);
			topicText = (TextView) findViewById(R.id.topic_text);
			myInfoText = (TextView) findViewById(R.id.setting_text);
			//设置监听
			shopLayout.setOnClickListener(this);
			caiquanLayout.setOnClickListener(this);
			topicLayout.setOnClickListener(this);
			myInfoLayout.setOnClickListener(this);
			//变量
			fragmentManager=getSupportFragmentManager();
			caiQuanDao=new CaiQuanDao();
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.caiquan_layout:
				// 当点击了消息tab时，选中第1个tab  
				if(preSelectNum!=0){
					setTabSelection(0);
					preSelectNum=0;
				}
				break;
			case R.id.shop_layout:
				 // 当点击了联系人tab时，选中第2个tab 
				if(preSelectNum!=1){
					setTabSelection(1);
					preSelectNum=1;
				}
				break;
			case R.id.topic_layout:
				// 当点击了动态tab时，选中第3个tab  
				if(preSelectNum!=2){
					setTabSelection(2);
					preSelectNum=2;
				}
				break;
			case R.id.setting_layout:
				// 当点击了设置tab时，选中第4个tab  
				if(preSelectNum!=3){
					setTabSelection(3);
					preSelectNum=3;
				}
				break;
			default:
				break;
			}
		}
		 /** 
		   * 根据传入的index参数来设置选中的tab页。 
		   *  
		   * @param index 
	       *   每个tab页对应的下标。0表示首页，1表示猜拳，2表示发现，3表示设置。 
	    */ 
		private void setTabSelection(int index){
		    // 每次选中之前先清楚掉上次的选中状态 
			clearSelection();
			// 开启一个Fragment事务 
			FragmentTransaction transaction=fragmentManager.beginTransaction();
			// 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况  
			hideFragments(transaction);
			switch (index) {
			case 0:
				// 当点击了消息tab时，改变控件的图片和文字颜色  
				caiquanText.setTextColor(getResources().getColor(color.black));
				//设置选中 fragment 标记
				Fragment caiquanFragment=this.getSupportFragmentManager().findFragmentByTag("caiquan");
				if(caiquanFragment==null){
					// 如果MessageFragment为空，则创建一个并添加到界面上  
					caiquanFragment=new CaiquanFragment();
					transaction.add(R.id.content, caiquanFragment,"caiquan");
				}else{
					transaction.show(caiquanFragment);
				}
				break;
				
			case 1:
				// 当点击了消息tab时，改变控件的图片和文字颜色  
				shopText.setTextColor(getResources().getColor(color.black));
				//设置选中 fragment 标记
				Fragment shopFragment=this.getSupportFragmentManager().findFragmentByTag("shop");
				if(shopFragment==null){
					// 如果indexFragment为空，则创建一个并添加到界面上  
					shopFragment=new ShopFragment();
					transaction.add(R.id.content, shopFragment,"shop");
				}else{
					transaction.show(shopFragment);
				}
				break;
			case 2:
				// 当点击了消息tab时，改变控件的图片和文字颜色  
				topicText.setTextColor(getResources().getColor(color.black));
				//设置选中 fragment 标记
				Fragment topicFragment=this.getSupportFragmentManager().findFragmentByTag("topic");
				if(topicFragment==null){
					// 如果MessageFragment为空，则创建一个并添加到界面上  
					topicFragment=new TopicFragment();
					transaction.add(R.id.content, topicFragment,"topic");
				}else{
					transaction.show(topicFragment);
				}
				break;
			case 3:
				// 当点击了消息tab时，改变控件的图片和文字颜色  
				myInfoText.setTextColor(getResources().getColor(color.black));
				//设置选中 fragment 标记
				Fragment settingFragment=this.getSupportFragmentManager().findFragmentByTag("setting");
				if(settingFragment==null){
					 // 如果myInfoFragment为空，则创建一个并添加到界面上  
					settingFragment=new SettingFragment();
					transaction.add(R.id.content, settingFragment,"setting");
				}else{
					//transaction.setCustomAnimations(R.animator.slide_normal, R.animator.slide_normal);//淡入淡出效果后，回归正常显示
					transaction.show(settingFragment);
				}
				break;

			default:
				break;
			}
			transaction.commit();
		}

	    /**
	     *  清除掉所有的选中状态
	     *  */
	    private void clearSelection() {
			shopText.setTextColor(getResources().getColor(color.tab_color));
			caiquanText.setTextColor(getResources().getColor(color.tab_color));
			topicText.setTextColor(getResources().getColor(color.tab_color));
			myInfoText.setTextColor(getResources().getColor(color.tab_color));
		}
	    
	    /**
		 * 将所有的Fragment都置为隐藏状态。 
		 * 
		 * @param transaction
		 *            用于对Fragment执行操作的事务 
		 */
		private void hideFragments(FragmentTransaction transaction) {
			//底部tab
			Fragment shopFragment=fragmentManager.findFragmentByTag("shop");
			Fragment caiquanFragment=fragmentManager.findFragmentByTag("caiquan");
			Fragment topicFragment=fragmentManager.findFragmentByTag("topic");
			Fragment settingFragment=fragmentManager.findFragmentByTag("setting");
			//移出额外Fragment
			//removeFragment(transaction);
			if(shopFragment!=null){
				transaction.hide(shopFragment);
			}
			if(caiquanFragment!=null){
				transaction.remove(caiquanFragment);
			}
			if(topicFragment!=null){
				transaction.hide(topicFragment);
			}
			if(settingFragment!=null){
				transaction.hide(settingFragment);
			}

		}
	
		//校验是否登录成功
	    private Runnable isLoginRun=new Runnable() {
			@Override
			public void run() {
				loginRegisterDao=new LoginRegisterDao();
				boolean flag=loginRegisterDao.isLogin();
				if(!flag){
					Intent intent=new Intent(getApplication(),LoginActivity.class);
					startActivity(intent);//跳转主界面
					finish();
				}
			}
		};
		//清理缓存
		private void removeData(){
			CommonUtil.removeSp("fragment");
		}
		//获取猜拳用户基本信息
		private Runnable getCQUserInfo=new Runnable() {
			@Override
			public void run() {
				Bundle data=new Bundle();
				Message msg=new Message();
				Map map=new HashMap();
				map.put(APIModel.CODE, APIModel.CQ_GETUSERINF);
				List params=CommonUtil.setParams(map, CommonUtil.DIRECT_LIST);
				data=caiQuanDao.getCQUsesrInfo(params);
				msg.setData(data);
				getCQUserHandler.sendMessage(msg);
			}
		};
		//处理猜拳用户基本信息UI更新
		private Handler getCQUserHandler=new Handler(){
			@Override
			public void handleMessage(Message msg) {
				Bundle data=msg.getData();
				boolean flag=data.getBoolean("flag");
				if(flag){
					try {
						JSONObject obj=new JSONObject(data.getString(APIModel.DATA));
						JSONObject winRetObj=new JSONObject(obj.getString("RECORD"));
						tvw_cq_user.setText(obj.getString("USER_NAME"));
						tvw_cq_userInfo.setText("LV "+winRetObj.getString("WIN_COUNT")+"胜 "+winRetObj.getString("DRAW_COUNT")+"平 "+winRetObj.getString("LOST_COUNT")+"负");
						tvw_cq_userJF.setText(obj.getString("INTEGRAL"));
					} catch (JSONException e) {
						e.printStackTrace();
					}
				}
			};
		};
		//注册更新个人信息广播
		private void registerBroadcase(){
			IntentFilter intentFilter=new IntentFilter();
			intentFilter.addAction("action.refreshMyInfo");
			registerReceiver(mRefreshMyInfo, intentFilter);
		}
		//个人信息更新广播
		private BroadcastReceiver mRefreshMyInfo=new BroadcastReceiver(){
			@Override
			public void onReceive(Context context, Intent intent) {
				// TODO Auto-generated method stub
				String action=intent.getAction();
				if("action.refreshMyInfo".equals(action)){
					Bundle data=intent.getExtras();
					tvw_cq_userJF.setText("dddd");
				}
			}
		};
}
