package com.bncq.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.bncq.R;
import com.bncq.common.DateUtil;
import android.app.Activity;
import android.text.TextPaint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyTopicAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private ArrayList<HashMap<String, Object>> listData;
	private static class ViewHolder{
		TextView tvw_topic_topicId;
		TextView tvw_topic_Title;
		TextView tvw_topic_User;
		TextView tvw_topic_ComTime;
		TextView tvw_topic_ComNum;
	}
	public MyTopicAdapter(Activity activity,ArrayList<HashMap<String, Object>> data) {
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
			convertView=layoutInflater.inflate(R.layout.topic_listitem, null);
			holder=new ViewHolder();
			holder.tvw_topic_topicId=(TextView) convertView.findViewById(R.id.tvw_topic_topicId);
			holder.tvw_topic_Title=(TextView) convertView.findViewById(R.id.tvw_topic_Title);
			holder.tvw_topic_User=(TextView) convertView.findViewById(R.id.tvw_topic_User);
			holder.tvw_topic_ComTime=(TextView) convertView.findViewById(R.id.tvw_topic_ComTime);
			holder.tvw_topic_ComNum=(TextView) convertView.findViewById(R.id.tvw_topic_ComNum);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		//对item模板内容赋值
		holder.tvw_topic_topicId.setText(listData.get(position).get("U_TOPIC_ID").toString());
		holder.tvw_topic_Title.setText(listData.get(position).get("TOPIC_CON").toString());
		holder.tvw_topic_User.setText(listData.get(position).get("USER_NAME").toString());
		holder.tvw_topic_ComTime.setText(DateUtil.formatToForum(Long.valueOf(listData.get(position).get("TOPIC_TIME").toString())));
		holder.tvw_topic_ComNum.setText(listData.get(position).get("REPLY_COUNT").toString());
		return convertView;
	}

}
