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
	 * ��ʼ��context
	 */
	static{
		getContext();
	}

	/**
	 * ��ȡȫ��context
	 * @return
	 */
	public static Context getContext(){
		if(context==null){
			context=MainApplication.getContext();
		}
		return context;
	}
	/**
	 * ��ʾ��Ϣ
	 * @param context ����activity
	 * @param msg ��Ϣ
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
	 * ��ʾ��ʾ��ť
	 * @param msg
	 * @param pOnClick
	 * @param cOnClick
	 * @param context
	 */
	public static void showAlertDialog(String msg,OnClickListener pOnClick,OnClickListener cOnClick,Context context){
		Builder alertBuilder=new AlertDialog.Builder(context);
		alertBuilder.setTitle("ȷ��");
		alertBuilder.setMessage(msg);
		alertBuilder.setPositiveButton("ȷ��", pOnClick);
		alertBuilder.setNegativeButton("ȡ��", cOnClick);
		alertBuilder.show();
	}
	
	/**
	 * ����listView����ˢ�� ��������Ч������
	 * @param lvw PullToRefreshListView
	 * @param adapter ����Դ
	 * @param more ����textview
	 * @param progress ����
	 * @param pageSize ��ҳ��С
	 * @return
	 */
	public static Handler getLvHandler(final PullToRefreshListView lvw,final BaseAdapter adapter, final TextView more,final ProgressBar progress, final int pageSize,final ArrayList<HashMap<String, Object>> adapterList ) {
		return new Handler() {
			public void handleMessage(Message msg) {
				ArrayList<HashMap<String, Object>> retlist=(ArrayList<HashMap<String, Object>> ) msg.obj;
				adapterList.addAll(retlist);
				if (msg.what >= 0) {
					// listview���ݴ���
					if (msg.what < MainApplication.PAGE_SIZE) {//�������
						lvw.setTag(LISTVIEW_DATA_FULL);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_full);
					} else if (msg.what == MainApplication.PAGE_SIZE) {
						lvw.setTag(LISTVIEW_DATA_MORE);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_more);
					}
					//lvw.setAdapter(adapter);//ע��������
				} else if (msg.what == -1) {
					// ���쳣--��ʾ���س��� & ����������Ϣ
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
	 * ����listView��������Ч������ ������ˢ�� 
	 * @param lvw listview
	 * @param adapter ����Դ
	 * @param more ����textview
	 * @param progress ����
	 * @param pageSize ��ҳ��С
	 * @return
	 */
	public static Handler getLvHandler_noTop(final ListView lvw,final BaseAdapter adapter, final TextView more,final ProgressBar progress, final int pageSize) {
		return new Handler() {
			public void handleMessage(Message msg) {
				if (msg.what >= 0) {
					// listview���ݴ���
					if (msg.what < MainApplication.PAGE_SIZE) {//�������
						lvw.setTag(LISTVIEW_DATA_FULL);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_full);
					} else if (msg.what == MainApplication.PAGE_SIZE) {
						lvw.setTag(LISTVIEW_DATA_MORE);
						adapter.notifyDataSetChanged();
						more.setText(R.string.load_more);
					}
					lvw.setAdapter(adapter);//ע��������
				} else if (msg.what == -1) {
					// ���쳣--��ʾ���س��� & ����������Ϣ
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
					// listview���ݴ���
					if (msg.what < MainApplication.PAGE_SIZE) {//�������
						lvw.setTag(LISTVIEW_DATA_FULL);
						adapter.notifyDataSetChanged();
					} else if (msg.what == MainApplication.PAGE_SIZE) {
						lvw.setTag(LISTVIEW_DATA_MORE);
						adapter.notifyDataSetChanged();
					}
					//lvw.setAdapter(adapter);//ע��������
				} else if (msg.what == -1) {
					// ���쳣--��ʾ���س��� & ����������Ϣ
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
	 * �������뷨
	 * @param view
	 */
	public static void hideSoftInput(Activity activity){
		InputMethodManager imm =(InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(activity.getWindow().getDecorView().getWindowToken(), 0);
	}
	/**
	 * �������뷨
	 * @param view
	 */
	public static void hideSoftInput(Window window){
		InputMethodManager imm =(InputMethodManager) context.getSystemService(context.INPUT_METHOD_SERVICE);
		imm.hideSoftInputFromWindow(window.getDecorView().getWindowToken(), 0);
	}
	/**
	 * ͷ��������
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
			//tab��������ɫ���
			for(int i=0;i<listTextViews.size();i++){
				TextView onClickTextView=listTextViews.get(i);
				onClickTextView.setTextColor(context.getResources().getColor(color.tab_color));
			}
			TextView onClickTextView=listTextViews.get(index);
			onClickTextView.setTextColor(context.getResources().getColor(color.tab_color_show));
		}
	};
	/**
	 * ҳ���л�����
	 */
	public static class TabOnPageChangeListener implements OnPageChangeListener {
		private int offset;
		private int bmpW;
		private int currIndex;// ��ǰҳ�����
		private ImageView ivw_cq_cursor;
		private List<TextView> listTextViews;
		private  int one ;
		private  int two ;
		public TabOnPageChangeListener(int offset,int bmpW,ImageView ivw_cq_cursor,List<TextView> listTextViews){
			this.offset=offset;
			this.bmpW=bmpW;
			this.ivw_cq_cursor=ivw_cq_cursor;
			this.listTextViews=listTextViews;
			this.one = offset * 2 + bmpW;// ҳ��1 -> ҳ��2 ƫ����
			this.two = one * 2;// ҳ��1 -> ҳ��3 ƫ����
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
			animation.setFillAfter(true);// True:ͼƬͣ�ڶ�������λ��
			animation.setDuration(300);
			ivw_cq_cursor.startAnimation(animation);
			//tab��������ɫ���
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
	 *  ���س�ȭͼid
	 * @param value 0 ���� 1 ʯͷ 2 ��
	 * @param direct 0 ���� 1 ����
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
