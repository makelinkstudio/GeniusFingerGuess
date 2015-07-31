package com.bncq.model;

import java.util.ArrayList;
import java.util.HashMap;

import com.bncq.R;
import com.bncq.common.DateUtil;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

 
public class TopicDetAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private ArrayList<HashMap<String, Object>> listData;
	private static class ViewHolder{
		TextView tvw_topicDet_RepMan;
		TextView tvw_topicDet_RepCon;
		TextView tvw_topicDet_RepTime;
		TextView tvw_topic_reply_layer;
	}
	public TopicDetAdapter(Context context,ArrayList<HashMap<String, Object>> data) {
		this.layoutInflater=LayoutInflater.from(context);
		listData=data;
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return listData.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder holder;
		if(convertView==null){
			convertView=layoutInflater.inflate(R.layout.topic_detail_listitem, null);
			holder=new ViewHolder();
			holder.tvw_topicDet_RepMan=(TextView) convertView.findViewById(R.id.tvw_topicDet_RepMan);
			holder.tvw_topicDet_RepCon=(TextView) convertView.findViewById(R.id.tvw_topicDet_RepCon);
			holder.tvw_topicDet_RepTime=(TextView) convertView.findViewById(R.id.tvw_topicDet_RepTime);
			holder.tvw_topic_reply_layer=(TextView) convertView.findViewById(R.id.tvw_topic_reply_layer);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tvw_topicDet_RepMan.setText(listData.get(position).get("USER_NAME").toString());
		holder.tvw_topicDet_RepCon.setText(listData.get(position).get("REPLY_CON").toString());
		holder.tvw_topicDet_RepTime.setText(DateUtil.formatToForum(Long.valueOf(listData.get(position).get("REPLY_TIME").toString())));
		holder.tvw_topic_reply_layer.setText(position+1+convertView.getResources().getString(R.string.topic_reply_symbol));
		return convertView;
	}

}
