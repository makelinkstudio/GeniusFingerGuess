package com.bncq.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bncq.R;
import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;
import com.bncq.common.DateUtil;
import com.bncq.common.MainApplication;
import com.bncq.common.StringUtil;
import com.bncq.common.UIUtil;
import com.bncq.dao.ShopDao;
import com.bncq.service.AsynImageLoader;

import android.app.Activity;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ShopMyApptAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private ArrayList<HashMap<String, Object>> listData;
	private AsynImageLoader asynImageLoader;
	private String avd_id;
	private ShopDao shopDao;
	private Activity activity;
	private static class ViewHolder{
		LinearLayout lt_shop_frame;
		TextView tvw_shop_avdId;
		ImageView ivw_shop_goods;
		TextView tvw_shop_name;
		TextView tvw_shop_businName;
		TextView tvw_shop_integral;
		TextView tvw_shop_timeLeft;
		TextView tvw_shop_appoint;
		TextView tvw_shop_collect;
		ProgressBar pbr_shop_probar;
	}
	public ShopMyApptAdapter(Activity activity,ArrayList<HashMap<String, Object>> data) {
		this.layoutInflater=LayoutInflater.from(activity.getApplicationContext());
		this.listData=data;
		this.asynImageLoader=new AsynImageLoader();
		this.shopDao=new ShopDao();
		this.activity=activity;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView==null){
			convertView=layoutInflater.inflate(R.layout.shop_myappt_listitem, null);
			holder=new ViewHolder();
			holder.lt_shop_frame=(LinearLayout) convertView.findViewById(R.id.lt_shop_frame);
			holder.tvw_shop_avdId=(TextView) convertView.findViewById(R.id.tvw_shop_avdId);
			holder.ivw_shop_goods=(ImageView) convertView.findViewById(R.id.ivw_shop_goods);
			holder.tvw_shop_name=(TextView) convertView.findViewById(R.id.tvw_shop_name);
			holder.tvw_shop_businName=(TextView) convertView.findViewById(R.id.tvw_shop_businName);
			holder.tvw_shop_integral=(TextView) convertView.findViewById(R.id.tvw_shop_integral);
			holder.tvw_shop_timeLeft=(TextView) convertView.findViewById(R.id.tvw_shop_timeLeft);
			holder.tvw_shop_appoint=(TextView) convertView.findViewById(R.id.tvw_shop_appoint);
			holder.tvw_shop_collect=(TextView) convertView.findViewById(R.id.tvw_shop_collect);
			holder.pbr_shop_probar=(ProgressBar) convertView.findViewById(R.id.pbr_shop_probar);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		//调整高度，适配
		int height =(int) (activity.getWindowManager().getDefaultDisplay().getHeight()*0.6);
		holder.lt_shop_frame.setLayoutParams(new AbsListView.LayoutParams(LayoutParams.MATCH_PARENT, height));
		//对item模板内容赋值
		holder.tvw_shop_avdId.setText(listData.get(position).get("AVD_ID").toString());
		holder.tvw_shop_name.setText(listData.get(position).get("AVD_TITLE").toString());
		holder.tvw_shop_businName.setText(listData.get(position).get("BUSIN_NAME").toString());
		holder.tvw_shop_integral.setText(MainApplication.getContext().getResources().getString(R.string.shop_integral)+listData.get(position).get("AVD_INTEGRAL").toString());
		String app_count=listData.get(position).get("APPOINT_COUNT").toString();
		holder.tvw_shop_timeLeft.setText(MainApplication.getContext().getResources().getString(R.string.shop_timeLeft)+DateUtil.getCountDownTime(listData.get(position).get("AVD_COUNTDOWN").toString()));
		//异步加载图片
		asynImageLoader.showImageAsyn(holder.ivw_shop_goods, listData.get(position).get("AVD_IMG_URL").toString(),holder.pbr_shop_probar);
		boolean appState=(Boolean) listData.get(position).get("APPOINT_STATUS");
		boolean colState=(Boolean) listData.get(position).get("COLLECT_STATUS");
		//设置预约 收藏显示
		if(appState){
			holder.tvw_shop_appoint.setText(MainApplication.getContext().getResources().getString(R.string.shop_appointed)+" "+app_count);//已经预约
		}else{
			holder.tvw_shop_appoint.setText(MainApplication.getContext().getResources().getString(R.string.shop_appoint)+" "+app_count);//未预约
		}
		if(!colState){
			holder.tvw_shop_collect.setText(MainApplication.getContext().getResources().getString(R.string.shop_collected));//已经收藏
		}else{
			holder.tvw_shop_collect.setText(MainApplication.getContext().getResources().getString(R.string.shop_collect));//未收藏
		}
		//点击预约事件
		holder.tvw_shop_appoint.setOnClickListener(new appointClickListener(position));
		holder.tvw_shop_collect.setOnClickListener(new collectClickListener(position));
		return convertView;
	}
	//预约点击事件
	private class appointClickListener implements OnClickListener{
		private int position;
		public appointClickListener(int position) {
			this.position=position;//数据位置
		}
		@Override
		public void onClick(View v) {
			UIUtil.showMsg("你已经预约过了");
		}
	};
	//收藏点击事件
	private class collectClickListener implements OnClickListener{
		private int position;
		public collectClickListener(int position) {
			this.position=position;//数据位置
		}
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
			avd_id=listData.get(position).get("AVD_ID").toString();
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
			map.put("avd_id", avd_id);
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

