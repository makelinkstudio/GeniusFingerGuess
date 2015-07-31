package com.bncq.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bncq.R;
import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;
import com.bncq.common.MainApplication;
import com.bncq.common.UIUtil;
import com.bncq.dao.TopicDao;
import com.bncq.model.TopicDetAdapter;
import com.bncq.model.PullToRefreshListView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class TopicDetailActivity extends Activity {
	//UI
	private ImageButton ibtn_topicDet_back;
	private EditText edt_topicDet_repCon;
	private Button btn_topicDet_rep;
	private LinearLayout llt_main_top;
	private LinearLayout llt_main_userInfo;
	private LinearLayout llt_bottom_layout;//底层操作栏
	//header 主话题
	private View topic_detail_header;
	private TextView  tvw_topicDet_Man;
	private TextView tvw_topicDet_con;
	private TextView tvw_topicDet_time;
	private TextView tvw_topicDet_RepNum;
	
	private PullToRefreshListView lvw_topicDet_item;
	private View lvw_footer;
	private TextView lvw_foot_more;
	private ProgressBar lvw_foot_progress;
	
	//变量
	private static TopicDao topicDao;
	private ArrayList<HashMap<String, Object>> adapterList;
	private int lvwSumData;
	private int preSumData;
	private Handler getLvHandler;
	private static Handler upTopicConHdl;
	private TopicDetAdapter topicDetAdapter;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topic_detail);
		//UI
		ibtn_topicDet_back=(ImageButton) findViewById(R.id.ibtn_topicDet_back);
		lvw_topicDet_item=(PullToRefreshListView)findViewById(R.id.lvw_topicDet_item);
		edt_topicDet_repCon=(EditText) findViewById(R.id.edt_topicDet_repCon);
		btn_topicDet_rep =(Button) findViewById(R.id.btn_topicDet_rep);
		
		topic_detail_header=View.inflate(getApplicationContext(), R.layout.topic_detail_header, null);//获取头部 主题内容
		tvw_topicDet_Man=(TextView) topic_detail_header.findViewById(R.id.tvw_topicDet_Man);
		tvw_topicDet_con=(TextView) topic_detail_header.findViewById(R.id.tvw_topicDet_con);
		tvw_topicDet_time=(TextView) topic_detail_header.findViewById(R.id.tvw_topicDet_time);
		tvw_topicDet_RepNum=(TextView) topic_detail_header.findViewById(R.id.tvw_topicDet_RepNum);
		//变量
		topicDao=new TopicDao();
		lvwSumData=MainApplication.PAGE_SIZE;
		preSumData=0;
		adapterList=new ArrayList<HashMap<String,Object>>();
		upTopicConHdl=new Handler();

		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
		//返回
		ibtn_topicDet_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				UIUtil.hideSoftInput(getWindow());
				Intent intent =new Intent(getApplication(),MainActivity.class);
				startActivity(intent);
				getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
				finish();
			}
		});
		//评论
		btn_topicDet_rep.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(!TextUtils.isEmpty(edt_topicDet_repCon.getText())){
					if(edt_topicDet_repCon.getText().length()<=150){
						UIUtil.hideSoftInput(getWindow());//隐藏键盘
						new Thread(topicRepRun).start();
					}else{
						UIUtil.showMsg("请输入少于150个字");
					}
				}else{
					   UIUtil.showMsg("请输入内容");
				}
			}
		});
		//加载数据
		initListView();
		getLvHandler=UIUtil.getLvHandler(lvw_topicDet_item, topicDetAdapter, lvw_foot_more, lvw_foot_progress, MainApplication.PAGE_SIZE,adapterList);
		loadLVWData(1, getLvHandler, UIUtil.LISTVIEW_ACTION_REFRESH);
		upTopicConHdl.post(upTopicConRun);
	}
	//销毁
	@Override
	protected void onDestroy() {
		super.onDestroy();
		upTopicConHdl.removeCallbacks(upTopicConRun);
		topicRepHand.removeCallbacks(topicRepRun);
	}
	// 初始化我的回复列表
	private void initListView() {
		topicDetAdapter = new TopicDetAdapter(getApplicationContext(), adapterList);
		lvw_footer = getLayoutInflater().inflate(R.layout.listview_footer, null);
		lvw_foot_progress = (ProgressBar) lvw_footer.findViewById(R.id.listview_foot_progress);
		lvw_foot_more = (TextView) lvw_footer.findViewById(R.id.listview_foot_more);
		lvw_topicDet_item.addHeaderView(topic_detail_header);
		lvw_topicDet_item.addFooterView(lvw_footer);
		lvw_topicDet_item.setAdapter(topicDetAdapter);
		lvw_topicDet_item.setTag(UIUtil.LISTVIEW_DATA_MORE);
		lvw_topicDet_item.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvw_topicDet_item.onScrollStateChanged(view, scrollState);
				// 数据为空--不用继续下面代码了
				if (adapterList.isEmpty())
					return;
				// 判断是否滚动到底部
				boolean scrollEnd = false;
				try {
					if (view.getPositionForView(lvw_footer) == view.getLastVisiblePosition())
						scrollEnd = true;
				} catch (Exception e) {
					    scrollEnd = false;
				}

				int lvDataState = Integer.parseInt(lvw_topicDet_item.getTag().toString());
				if (scrollEnd && lvDataState == UIUtil.LISTVIEW_DATA_MORE) {
					lvw_topicDet_item.setTag(UIUtil.LISTVIEW_DATA_MORE);
					lvw_foot_more.setText(R.string.load_ing);
					lvw_foot_progress.setVisibility(View.VISIBLE);
					// 当前pageIndex
					int pageIndex = lvwSumData / MainApplication.PAGE_SIZE;
					if (preSumData < lvwSumData) {// 判断是否是要 新刷数据，否则过滤重复执行
						loadLVWData(pageIndex, getLvHandler,UIUtil.LISTVIEW_ACTION_SCROLL);
						preSumData = lvwSumData;
					}
				}
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				lvw_topicDet_item.onScroll(view, firstVisibleItem, visibleItemCount,totalItemCount);
			}
		});
		// 刷新
		lvw_topicDet_item.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
					public void onRefresh() {
						lvwSumData = MainApplication.PAGE_SIZE;
						preSumData = 0;// 设置前数据量为0；
						loadLVWData(1, getLvHandler,UIUtil.LISTVIEW_ACTION_REFRESH);
					}
		});
	}
	// 访问网络获取回复数据列表线程
	private void loadLVWData(final int pageIndex, final Handler handler,final int action) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				boolean isRefresh = false;
				HashMap dataMap = new HashMap();
				dataMap.put("page", pageIndex);
				dataMap.put("u_topic_id", getIntent().getStringExtra("topic_id"));
				List params = CommonUtil.setParams(CommonUtil.initPostMap(APIModel.TOPIC_DET_LIST, dataMap),CommonUtil.ARRAY_LIST);
				ArrayList<HashMap<String, Object>> retList = topicDao.getTopicDetData(params);
				if (action == UIUtil.LISTVIEW_ACTION_REFRESH) {
					adapterList.clear();
				} else {
				}
				//adapterList.addAll(retList);
				lvwSumData += retList.size();
				msg.what = retList.size();
				msg.arg1 = action;
				msg.obj=retList;
				handler.sendMessage(msg);
			}
		}).start();
	}
	//处理话题数据更新
	private Runnable upTopicConRun=new Runnable() {
		@Override
		public void run() {
			 Bundle data=getIntent().getExtras();
			 tvw_topicDet_Man.setText(data.getString("topic_man"));
			 tvw_topicDet_con.setText(data.getString("topic_con"));
			 tvw_topicDet_time.setText(data.getString("topic_time"));
			 tvw_topicDet_RepNum.setText(data.getString("topic_num"));
		}
	};
	//处理回复线程
	private Handler topicRepHand=new Handler(){
		@Override
		public void handleMessage(Message msg){
			super.handleMessage(msg);
			Bundle data=msg.getData();
			boolean flag=data.getBoolean("flag");
			String retMsg=data.getString("msg");
			if(flag){
				lvwSumData = MainApplication.PAGE_SIZE;
				preSumData = 0;// 设置前数据量为0；
				loadLVWData(1, getLvHandler,UIUtil.LISTVIEW_ACTION_REFRESH);
				edt_topicDet_repCon.setText("");
			}
			UIUtil.showMsg(retMsg);
		}
	};
	//回复线程开启
	private Runnable topicRepRun=new Runnable() {
		@Override
		public void run() {
			Bundle data = new Bundle();
			Message msg = new Message();
			HashMap map = new HashMap();
			map.put("reply_con",edt_topicDet_repCon.getText());//回复内容
			map.put("u_topic_id",getIntent().getStringExtra("topic_id"));//话题ID
			List params = CommonUtil.setParams(CommonUtil.initPostMap(APIModel.TOPIC_REPLY, map),CommonUtil.ARRAY_LIST);// 初始化post参数
			data = topicDao.topicDetReply(params);
			msg.setData(data);
			topicRepHand.sendMessage(msg);
		}
	};
}
