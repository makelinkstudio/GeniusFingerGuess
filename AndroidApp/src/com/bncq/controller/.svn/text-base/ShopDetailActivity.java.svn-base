package com.bncq.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bncq.R;
import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;
import com.bncq.common.DateUtil;
import com.bncq.common.MainApplication;
import com.bncq.common.ShareDialog;
import com.bncq.common.StringUtil;
import com.bncq.common.UIUtil;
import com.bncq.dao.ShopDao;
import com.bncq.service.AsynImageLoader;

import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ShopDetailActivity extends Activity {
	
	private ImageButton ibtn_shopDet_back;
	private TextView tvw_shop_avdId;
	private ImageView ivw_shop_goods;
	private TextView tvw_shop_name;
	private TextView tvw_shop_businName;
	private TextView tvw_shop_integral;
	private TextView tvw_shop_endTime;
	private TextView tvw_shop_appoint;
	private TextView tvw_shop_collect;
	private TextView tvw_shop_buy;
	private TextView tvw_shop_share;
	private ProgressBar pbr_shop_probar;
	private AsynImageLoader asynImageLoader;
	private ShopDao shopDao;
	private String avd_jump_url;
	private String img_url;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_detail_layout);
		tvw_shop_avdId=(TextView)findViewById(R.id.tvw_shop_avdId);
		ivw_shop_goods=(ImageView)findViewById(R.id.ivw_shop_goods);
		tvw_shop_name=(TextView)findViewById(R.id.tvw_shop_name);
		tvw_shop_businName=(TextView)findViewById(R.id.tvw_shop_businName);
		tvw_shop_integral=(TextView)findViewById(R.id.tvw_shop_integral);
		tvw_shop_endTime=(TextView)findViewById(R.id.tvw_shop_endTime);
		tvw_shop_appoint=(TextView)findViewById(R.id.tvw_shop_appoint);
		tvw_shop_collect=(TextView)findViewById(R.id.tvw_shop_collect);
		pbr_shop_probar=(ProgressBar)findViewById(R.id.pbr_shop_probar);
		ibtn_shopDet_back=(ImageButton) findViewById(R.id.ibtn_shopDet_back);
		tvw_shop_buy=(TextView) findViewById(R.id.tvw_shop_buy);
		tvw_shop_share=(TextView) findViewById(R.id.tvw_shop_share);
		
		//变量
		asynImageLoader=new AsynImageLoader();
		shopDao=new ShopDao();
		//设置商品id
		Intent intent=getIntent();
		String avd_id=intent.getStringExtra("shop_avdId");
		if(StringUtil.stringiSNotEmpty(avd_id)){
			tvw_shop_avdId.setText(avd_id);
		}
		//返回主页面
		ibtn_shopDet_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(getApplication(),MainActivity.class);//跳转回主程序
				startActivity(intent);
				finish();//结束当前
			}
		});
		//跳转至商品主页面
		tvw_shop_buy.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(ShopDetailActivity.this,ShopItemIndexActivity.class);
				intent.putExtra("AVD_JUMP_URL", avd_jump_url);
				startActivity(intent);
			}
		});
		final Activity activity=this;
		tvw_shop_share.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				ShareDialog shareDialog =new ShareDialog(activity);
				shareDialog.ShowShare("title","content",APIModel.BASEURL+img_url);
			}
		});
		new Thread(shopInfoRun).start();
	}
	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		shopInfoHdl.removeCallbacks(shopInfoRun);
		super.onDestroy();
	}
	private Handler shopInfoHdl=new Handler(){
		public void handleMessage(android.os.Message msg) {
			Bundle data=msg.getData();
			if(data.size()==0){return;}
			tvw_shop_name.setText(data.get("BUSIN_NAME").toString()+"【提供】");
			tvw_shop_businName.setText(data.get("AVD_TITLE").toString());
			tvw_shop_integral.setText(MainApplication.getContext().getResources().getString(R.string.shop_integral)+data.get("AVD_INTEGRAL").toString());
			String app_count=data.get("APPOINT_COUNT").toString();
		    tvw_shop_endTime.setText(MainApplication.getContext().getResources().getString(R.string.shop_endTime)+DateUtil.UnixTimeToDate(data.get("AVD_ETIME").toString()));
			//异步加载图片
			asynImageLoader.showImageAsyn(ivw_shop_goods, data.get("AVD_IMG_URL").toString(), pbr_shop_probar);
			img_url=data.get("AVD_IMG_URL").toString();
			avd_jump_url=data.getString("AVD_JUMP_URL");
			boolean appState=Boolean.valueOf(data.get("APPOINT_STATUS").toString());
			boolean colState=Boolean.valueOf(data.get("COLLECT_STATUS").toString());
			//设置预约 收藏显示
			if(appState){
				tvw_shop_appoint.setText(MainApplication.getContext().getResources().getString(R.string.shop_appointed)+" "+app_count);//已经预约
			}else{
				tvw_shop_appoint.setText(MainApplication.getContext().getResources().getString(R.string.shop_appoint)+" "+app_count);//未预约
			}
			if(!colState){
				tvw_shop_collect.setText(MainApplication.getContext().getResources().getString(R.string.shop_collected));//已经收藏
			}else{
				tvw_shop_collect.setText(MainApplication.getContext().getResources().getString(R.string.shop_collect));//未收藏
			}
			//点击预约事件
			tvw_shop_appoint.setOnClickListener(new appointClickListener());
			tvw_shop_collect.setOnClickListener(new collectClickListener());
		};
	};
	private Runnable shopInfoRun=new Runnable() {
		@Override
		public void run() {
			//post方法设置
			HashMap dataMap = new HashMap();
			dataMap.put("avd_id", tvw_shop_avdId.getText());
			List params = CommonUtil.setParams(CommonUtil.initPostMap(APIModel.SHOP_ITEM_DETAIL, dataMap),CommonUtil.ARRAY_LIST);
			Bundle data=shopDao.getShopDetailData(params);
			Message msg=new Message();
			msg.setData(data);
			shopInfoHdl.sendMessage(msg);
		}
	};
	//预约点击事件
	private class appointClickListener implements OnClickListener{
		private TextView tvw_shop_appoint;
		@Override
		public void onClick(View v) {
			 tvw_shop_appoint=(TextView) v.findViewById(v.getId());
			String appointString=tvw_shop_appoint.getText().toString();//预约状态
			if(appointString.equals(MainApplication.getContext().getResources().getString(R.string.shop_appoint))){//预约状态
				UIUtil.showAlertDialog("确认预约？", new positiveClick(), null,getApplicationContext());
			}else{
				UIUtil.showMsg("你已经预约过了");
			}
		}
		//确定事件
		private class positiveClick implements android.content.DialogInterface.OnClickListener{
			@Override
			public void onClick(DialogInterface dialog, int which) {
				String avd_id=tvw_shop_avdId.getText().toString();
				if(StringUtil.stringiSNotEmpty(avd_id)){
					ACThread appointThread=new ACThread(tvw_shop_appoint,0);
					appointThread.start();
				}
				dialog.dismiss();
			}
		};
		//取消事件
		private class negativeClick implements android.content.DialogInterface.OnClickListener{
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		};
	};
	//收藏点击事件
	private class collectClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			TextView tvw_shop_collect=(TextView) v.findViewById(v.getId());
			String collectString=tvw_shop_collect.getText().toString();//收藏状态
			int  type=1;
			if(collectString.equals(MainApplication.getContext().getResources().getString(R.string.shop_collect))){//收藏状态
				type=1;
			}else{
				type=2;
			}
			String avd_id=tvw_shop_avdId.getText().toString();
			if(StringUtil.stringiSNotEmpty(avd_id)){
				ACThread appointThread=new ACThread(tvw_shop_collect,type);
				appointThread.start();
			}
		}
	};
	//构建用户预约线程
	private class ACThread extends Thread{
		private TextView tvw_ac;
		private int type;
		public ACThread(TextView tvw_ac,int type) {
			this.tvw_ac=tvw_ac;
			this.type=type;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			Map map=new HashMap();
			map.put("avd_id", tvw_shop_avdId.getText());
			String codeString="";
			Bundle data=new Bundle();
			if(type==0){
				List params=CommonUtil.setParams(CommonUtil.initPostMap(APIModel.AVD_APPOINT, map), CommonUtil.ARRAY_LIST);
				data=shopDao.setAppoint(params);
			}else if(type==1){
				List params=CommonUtil.setParams(CommonUtil.initPostMap(APIModel.AVD_COLLECT, map), CommonUtil.ARRAY_LIST);
				data=shopDao.setCollect(params);
			}else if(type==2){
				List params=CommonUtil.setParams(CommonUtil.initPostMap(APIModel.AVD_CANCEL_COLLECT, map), CommonUtil.ARRAY_LIST);
				data=shopDao.setCancelCollect(params);
			}
			Message msg=new Message();
			msg.setData(data);
			msg.what=type;
			msg.obj=tvw_ac;
			acHandler.sendMessage(msg);
		}
	};
	//用户预约 返回预约情况
	private Handler acHandler=new Handler(){
		public void handleMessage(Message msg) {
			Bundle data=msg.getData();
			Boolean flag=data.getBoolean("flag");
			if(flag){
				TextView tvw_ac=(TextView) msg.obj;
				int type=msg.what;
				if(type==0){
					tvw_ac.setText(MainApplication.getContext().getResources().getString(R.string.shop_appointed));//已经预约
				}else if(type==1){
					tvw_ac.setText(MainApplication.getContext().getResources().getString(R.string.shop_collected));//已经收藏
				}else if(type==2){
					tvw_ac.setText(MainApplication.getContext().getResources().getString(R.string.shop_collect));//收藏
				}
			}
			UIUtil.showMsg(data.getString("msg"));
		};
	};
}
