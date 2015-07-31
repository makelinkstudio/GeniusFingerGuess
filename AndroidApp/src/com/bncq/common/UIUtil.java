package com.bncq.common;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import android.R.integer;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface.OnClickListener;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bncq.R;
import com.bncq.R.color;
import com.bncq.controller.MainActivity;
import com.bncq.model.PullToRefreshListView;

public class UIUtil {
	public final static int LISTVIEW_DATA_MORE = 0x01;
	public final static int LISTVIEW_ACTION_REFRESH = 0x02;
	public final static int LISTVIEW_ACTION_SCROLL = 0x03;
	public final static int LISTVIEW_ACTION_CHANGE_CATALOG = 0x04;
	
	public final static int LISTVIEW_DATA_LOADING = 0x02;
	public final static int LISTVIEW_DATA_FULL = 0x03;
	public final static int LISTVIEW_DATA_EMPTY = 0x04;
	
	private static Toast toast;
	private  static Context context;
    
	/**
	 * 初始化context
	 */
	static{
		getContext();
	}

	/**
	 * 获取全局context
	 * @return
	 */
	public static Context getContext(){
		if(context==null){
			context=MainApplication.getContext();
		}
		return context;
	}
	/**
	 * 提示信息
	 * @param context 所在activity
	 * @param msg 信息
	 */
	public static void showMsg(String msg) {
		    if(toast==null){
		    	toast=new Toast(context);
		    	toast=Toast.makeText(context, msg, Toast.LENGTH_SHORT);
				toast.setGravity(Gravity.CENTER, 0,0);
		    }else{
		    	toast.setText(msg);
		    }
		    toast.show();
	}
	/**
	 * 显示提示按钮
	 * @param msg
	 * @param pOnClick
	 * @param cOnClick
	 * @param context
	 */
	public static void showAlertDialog(String msg,OnClickListener pOnClick,OnClickListener cOnClick,Context context){
		Builder alertBuilder=new AlertDialog.Builder(context);
		alertBuilder.setTitle("确认");
		alertBuilder.setMessage(msg);
		alertBuilder.setPositiveButton("确认", pOnClick);
		alertBuilder.setNegativeButton("取消", cOnClick);
		alertBuilder.show();
	}
	
	/**
	 * 公共listView下拉刷新 上拉加载效果处理
	 * @param lvw PullToRefreshListView
	 * @param adapter 数据源
	 * @param more 更多textview
	 * @param progress 过程
	 * @param pageSize 分页大小
	 * @return
	 */
	public static Handler getLvHandler(final PullToRefreshListView lvw,final BaseAdapter adapter, final TextView more,final ProgressBar progress, final int pageSize,final ArrayList<HashMap<String, Object>> adapterList ) {
		return new Handler() {
			public void handleMessage(Message msg) {
				ArrayList<HashMap<String, Object>> retlist=(ArrayList<HashMap<String, Object>> ) msg.obj;
				adapterList.addAll(retlist);
				if (msg.what >= 0) {
					// listview数据处理
					if (msg.what < MainApplication.PAGE_SIZE) {//加载完成
						lvw.setTag(LISTVIEW_DATA_FULL);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_full);
					} else if (msg.what == MainApplication.PAGE_SIZE) {
						lvw.setTag(LISTVIEW_DATA_MORE);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_more);
					}
					//lvw.setAdapter(adapter);//注入适配器
				} else if (msg.what == -1) {
					// 有异常--显示加载出错 & 弹出错误消息
					lvw.setTag(LISTVIEW_DATA_MORE);
					more.setText(R.string.load_error);
				}
				if (adapter.getCount() == 0) {
					lvw.setTag(LISTVIEW_DATA_EMPTY);
					more.setText(R.string.load_empty);
				}
				progress.setVisibility(ProgressBar.GONE);
				if (msg.arg1 == LISTVIEW_ACTION_REFRESH) {
					lvw.onRefreshComplete(context.getString(R.string.pull_to_refresh_update)+ DateUtil.getNowDate("yyyy-MM-dd HH:mm:ss"));
					lvw.setSelection(0);
				} else if (msg.arg1 == LISTVIEW_ACTION_CHANGE_CATALOG) {
					lvw.onRefreshComplete();
					lvw.setSelection(0);
				}
			}
		};
	}
	/**
	 * 公共listView上拉加载效果处理 无下拉刷新 
	 * @param lvw listview
	 * @param adapter 数据源
	 * @param more 更多textview
	 * @param progress 过程
	 * @param pageSize 分页大小
	 * @return
	 */
	public static Handler getLvHandler_noTop(final ListView lvw,final BaseAdapter adapter, final TextView more,final ProgressBar progress, final int pageSize) {
		return new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what >= 0) {
					// listview数据处理
					if (msg.what < MainApplication.PAGE_SIZE) {//加载完成
						lvw.setTag(LISTVIEW_DATA_FULL);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_full);
					} else if (msg.what == MainApplication.PAGE_SIZE) {
						lvw.setTag(LISTVIEW_DATA_MORE);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_more);
					}
					lvw.setAdapter(adapter);//注入适配器
				} else if (msg.what == -1) {
					// 有异常--显示加载出错 & 弹出错误消息
					lvw.setTag(LISTVIEW_DATA_MORE);
					more.setText(R.string.load_error);
				}
				if (adapter.getCount() == 0) {
					lvw.setTag(LISTVIEW_DATA_EMPTY);
					more.setText(R.string.load_empty);
				}
				progress.setVisibility(ProgressBar.GONE);
			}
		};
	}
	public static Handler getLvHandler_noEnd(final PullToRefreshListView lvw,final BaseAdapter adapter,final ArrayList<HashMap<String, Object>> adapterList ) {
		return new Handler() {
			public void handleMessage(Message msg) {
				ArrayList<HashMap<String, Object>> retlist=(ArrayList<HashMap<String, Object>> ) msg.obj;
				adapterList.addAll(retlist);
				if (msg.what >= 0) {
					// listview数据处理
					if (msg.what < MainApplication.PAGE_SIZE) {//加载完成
						lvw.setTag(LISTVIEW_DATA_FULL);
						adapter.notifyDataSetChanged();
					} else if (msg.what == MainApplication.PAGE_SIZE) {
						lvw.setTag(LISTVIEW_DATA_MORE);
						adapter.notifyDataSetChanged();
					}
					//lvw.setAdapter(adapter);//注入适配器
				} else if (msg.what == -1) {
					// 有异常--显示加载出错 & 弹出错误消息
					lvw.setTag(LISTVIEW_DATA_MORE);
				}
				if (adapter.getCount() == 0) {
					lvw.setTag(LISTVIEW_DATA_EMPTY);
				}
				if (msg.arg1 == LISTVIEW_ACTION_REFRESH) {
					lvw.onRefreshComplete(context.getString(R.string.pull_to_refresh_update)+ DateUtil.getNowDate("yyyy-MM-dd HH:mm:ss"));
					lvw.setSelection(0);
				} else if (msg.arg1 == LISTVIEW_ACTION_CHANGE_CATALOG) {
					lvw.onRefreshComplete();
					lvw.setSelection(0);
				}
			}
		};
	}
	/**
	 * 隐藏输入法
	 * @param view
	 */
	public static void hideSoftInput(Activity activity){
		InputMethodManager imm =(InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
	}
	/**
	 * 隐藏输入法
	 * @param view
	 */
	public static void hideSoftInput(Window window){
		InputMethodManager imm =(InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(window.getDecorView().getWindowToken(), 0);
	}
	/**
	 * 头标点击监听
	 */
	public static class TabOnClickListener implements View.OnClickListener {
		private int index = 0;
		private ViewPager mPager;
		private List<TextView> listTextViews;
		public TabOnClickListener(int i,ViewPager mPager,List<TextView> listTextViews) {
			index = i;
			this.mPager=mPager;
			this.listTextViews=listTextViews;
		}

		@Override
		public void onClick(View v) {
			mPager.setCurrentItem(index);
			//tab卡字体颜色变更
			for(int i=0;i<listTextViews.size();i++){
				TextView onClickTextView=listTextViews.get(i);
				onClickTextView.setTextColor(context.getResources().getColor(color.tab_color));
			}
			TextView onClickTextView=listTextViews.get(index);
			onClickTextView.setTextColor(context.getResources().getColor(color.tab_color_show));
		}
	};
	/**
	 * 页卡切换监听
	 */
	public static class TabOnPageChangeListener implements OnPageChangeListener {
		private int offset;
		private int bmpW;
		private int currIndex;// 当前页卡编号
		private ImageView ivw_cq_cursor;
		private List<TextView> listTextViews;
		private  int one ;
		private  int two ;
		public TabOnPageChangeListener(int offset,int bmpW,ImageView ivw_cq_cursor,List<TextView> listTextViews){
			this.offset=offset;
			this.bmpW=bmpW;
			this.ivw_cq_cursor=ivw_cq_cursor;
			this.listTextViews=listTextViews;
			this.one = offset * 2 + bmpW;// 页卡1 -> 页卡2 偏移量
			this.two = one * 2;// 页卡1 -> 页卡3 偏移量
			this.currIndex=0;
		}
		@Override
		public void onPageSelected(int arg0) {
			Animation animation = null;
			switch (arg0) {
			case 0:
				if (currIndex == 1) {
					animation = new TranslateAnimation(one, 0, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, 0, 0, 0);
				}
				break;
			case 1:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, one, 0, 0);
				} else if (currIndex == 2) {
					animation = new TranslateAnimation(two, one, 0, 0);
				}
				break;
			case 2:
				if (currIndex == 0) {
					animation = new TranslateAnimation(offset, two, 0, 0);
				} else if (currIndex == 1) {
					animation = new TranslateAnimation(one, two, 0, 0);
				}
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			ivw_cq_cursor.startAnimation(animation);
			//tab卡字体颜色变更
			for(int i=0;i<listTextViews.size();i++){
				TextView onClickTextView=listTextViews.get(i);
				onClickTextView.setTextColor(context.getResources().getColor(color.tab_color));
			}
			TextView onClickTextView=listTextViews.get(arg0);
			onClickTextView.setTextColor(context.getResources().getColor(color.tab_color_show));
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {
		}
	}
	/**
	 *  返回出拳图id
	 * @param value 0 剪刀 1 石头 2 布
	 * @param direct 0 向右 1 向左
	 * @return
	 */
	public static int selPunImgId(String value, int direct) {
		int id = R.drawable.jian_left;
		switch (Integer.valueOf(value)) {
		case 0:
			if (direct == 0) {
				id = R.drawable.jian_right;
			} else {
				id = R.drawable.jian_left;
			}
			break;
		case 1:
			if (direct == 0) {
				id = R.drawable.shi_right;
			} else {
				id = R.drawable.shi_left;
			}
			break;
		case 2:
			if (direct == 0) {
				id = R.drawable.bao_right;
			} else {
				id = R.drawable.bao_left;
			}
			break;
		default:
			break;
		}
		return id;
	}
}
