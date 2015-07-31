package com.bncq.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Timer;
import java.util.TimerTask;

import com.bncq.R;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class CaiquanResultAdapter extends BaseAdapter {

	private LayoutInflater layoutInflater;
	private ArrayList<HashMap<String, Object>> listData;
	private Activity activity;
	private static class ViewHolder{
		TextView tvw_mat_name;
		ImageView ivw_camp_a;
		ImageView ivw_bu_mid;

	}
	public CaiquanResultAdapter(Activity activity,ArrayList<HashMap<String, Object>> data) {
		this.layoutInflater=LayoutInflater.from(activity.getApplicationContext());
		this.listData=data;
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
			convertView=layoutInflater.inflate(R.layout.caiquan_result_listitem, null);
			holder=new ViewHolder();
			holder.tvw_mat_name=(TextView) convertView.findViewById(R.id.tvw_mat_name);
			holder.ivw_camp_a=(ImageView) convertView.findViewById(R.id.ivw_camp_a);
		    holder.ivw_bu_mid=(ImageView) convertView.findViewById(R.id.ivw_bu_mid);
			convertView.setTag(holder);
		}else{
			holder=(ViewHolder) convertView.getTag();
		}
		holder.tvw_mat_name.setText("普通12点场");
		holder.ivw_camp_a.setImageResource(R.drawable.camp_a_off);
		punchMidTurn(holder.ivw_bu_mid);
		return convertView;
	}
	/**
	 * 图标转动
	 * @param view  对象
	 */
	private void punchMidTurn(final View view) {
		view.setTag(true);
		// 设置自定义监听 对象
		ViewTreeObserver observer = view.getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			private Boolean isMeasured = true;

			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				if (isMeasured) {
					final float cX = view.getMeasuredWidth() / 2.0f;
					final float cY = view.getMeasuredHeight() / 2.0f;
					if (cX > 0f) {
						isMeasured = false;
						//setPunchMidTurnAnima(view, cX, cY);
					
					}
					setPunchMidTurnAnima(view, cX, cY);
				}
			}
		});
	}
	/**
	 * 图标转动动画
	 * @param view 对象
	 * @param cX 对象宽／2
	 * @param cY 对象高／2
	 */
	private void setPunchMidTurnAnima(final View view, float cX, float cY) {
		RotateAnimation rotateAnim = new RotateAnimation(cX, cY,RotateAnimation.ROTATE_DECREASE);
		rotateAnim.setFillAfter(true);
		// 初始化 自定义 动画 设置循环动画
		rotateAnim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				boolean flag=(Boolean) view.getTag();
				if(flag){
					view.startAnimation(animation);
				}
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
		});
		view.startAnimation(rotateAnim);
	}
}

