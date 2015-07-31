package com.bncq.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.bncq.R;
import com.bncq.common.DateUtil;
import com.bncq.common.MainApplication;

import android.app.Activity;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class NoticeAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private ArrayList<HashMap<String, Object>> listData;
	private static class ViewHolder{
		TextView tvw_notice_time;
		TextView tvw_notice_title;
		TextView tvw_notice_con;
	}
	public NoticeAdapter(Activity activity,ArrayList<HashMap<String, Object>> data) {
		this.layoutInflater=LayoutInflater.from(activity.getApplicationContext());
		listData=data;
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
			convertView=layoutInflater.inflate(R.layout.mynotice_listitem, null);
			holder=new ViewHolder();
			holder.tvw_notice_time=(TextView) convertView.findViewById(R.id.tvw_notice_time);
			holder.tvw_notice_title=(TextView) convertView.findViewById(R.id.tvw_notice_title);
			holder.tvw_notice_con=(TextView) convertView.findViewById(R.id.tvw_notice_con);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		//对item模板内容赋值
		holder.tvw_notice_time.setText(DateUtil.formatToForum(Long.valueOf(listData.get(position).get("TOPIC_TIME").toString())));
		holder.tvw_notice_title.setText(listData.get(position).get("USER_NAME").toString());
		holder.tvw_notice_con.setText(listData.get(position).get("TOPIC_CON").toString());
		return convertView;
	}

}
