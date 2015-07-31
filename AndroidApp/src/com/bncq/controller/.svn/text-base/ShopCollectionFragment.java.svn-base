package com.bncq.controller;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.bncq.R;
import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;
import com.bncq.common.MainApplication;
import com.bncq.common.StringUtil;
import com.bncq.common.UIUtil;
import com.bncq.dao.ShopDao;
import com.bncq.model.PullToRefreshListView;
import com.bncq.model.ShopCollectionAdapter;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;



public class ShopCollectionFragment extends Fragment {
		// UI
		private PullToRefreshListView lvw_shopCollection;
		// listview
		private View lvw_footer;
		private TextView lvw_foot_more;
		private ProgressBar lvw_foot_progress;
		// 变量
		private ShopDao shopDao;
		private ArrayList<HashMap<String, Object>> adapterList;
		private int lvwSumData;
		private int preSumData;
		private ShopCollectionAdapter shopAdapter;
		private boolean isInit; // 是否可以开始加载数据
		private static Handler getLvHandler;
		@Override
		public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
			View indexLayout=inflater.inflate(R.layout.shop_collection_layout,container,false);
			//UI
			lvw_shopCollection=(PullToRefreshListView) indexLayout.findViewById(R.id.lvw_shopCollection);
		    //变量
		    shopDao=new ShopDao();
			lvwSumData=MainApplication.PAGE_SIZE;
			preSumData=0;
			adapterList=new ArrayList<HashMap<String,Object>>();
			isInit=true;
			return indexLayout;
		}
		@Override
		public void onResume() {
			// TODO Auto-generated method stub
			super.onResume();
			if(getUserVisibleHint()){
				loadData();
			}
		}
		@Override
		public void setUserVisibleHint(boolean isVisibleToUser) {
			// TODO Auto-generated method stub
			super.setUserVisibleHint(isVisibleToUser);
			// 每次切换fragment时调用的方法  
			if(getUserVisibleHint()){
					loadData();
			}
		}
		//获取话题数据
		private void loadData() {
			if (isInit) {  
	            isInit = false;//加载数据完成  
				//执行论坛数据线程
				initListView();
				getLvHandler=UIUtil.getLvHandler(lvw_shopCollection, shopAdapter, lvw_foot_more, lvw_foot_progress, MainApplication.PAGE_SIZE,adapterList);
				loadLVWData(1, getLvHandler, UIUtil.LISTVIEW_ACTION_SCROLL);
			}
		}
		// 初始化我的话题列表
		private void initListView() {
			shopAdapter = new ShopCollectionAdapter(getActivity(), adapterList);
			lvw_footer = getActivity().getLayoutInflater().inflate(R.layout.listview_footer, null);
			lvw_foot_progress = (ProgressBar) lvw_footer.findViewById(R.id.listview_foot_progress);
			lvw_foot_more = (TextView) lvw_footer.findViewById(R.id.listview_foot_more);
			lvw_shopCollection.addFooterView(lvw_footer);
			lvw_shopCollection.setAdapter(shopAdapter);
			lvw_shopCollection.setTag(UIUtil.LISTVIEW_DATA_MORE);
			//item点击事件
			lvw_shopCollection.setOnItemClickListener(new AdapterView.OnItemClickListener() {
				@Override
				public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
					// TODO Auto-generated method stub
					try {
						TextView tvw_shop_avdId=(TextView) view.findViewById(R.id.tvw_shop_avdId);
						if(StringUtil.stringiSNotEmpty(tvw_shop_avdId.getText())){
							String shop_avdId=tvw_shop_avdId.getText().toString();
							Intent intent =new Intent(getActivity(),ShopDetailActivity.class);
							intent.putExtra("shop_avdId", shop_avdId);
							startActivity(intent);
						}
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
			});
			lvw_shopCollection.setOnScrollListener(new AbsListView.OnScrollListener() {
				@Override
				public void onScrollStateChanged(AbsListView view, int scrollState) {
					lvw_shopCollection.onScrollStateChanged(view, scrollState);
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

					int lvDataState = Integer.parseInt(lvw_shopCollection.getTag().toString());
					if (scrollEnd && lvDataState == UIUtil.LISTVIEW_DATA_MORE) {
						lvw_shopCollection.setTag(UIUtil.LISTVIEW_DATA_MORE);
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
				public void onScroll(AbsListView view, int firstVisibleItem,
						int visibleItemCount, int totalItemCount) {
					// TODO Auto-generated method stub
					lvw_shopCollection.onScroll(view, firstVisibleItem, visibleItemCount,totalItemCount);
				}
			});
			// 刷新
			lvw_shopCollection.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
						public void onRefresh() {
							lvwSumData = MainApplication.PAGE_SIZE;
							preSumData = 0;// 设置前数据量为0；
							loadLVWData(1, getLvHandler,UIUtil.LISTVIEW_ACTION_REFRESH);
						}
			});
		}
		// 访问网络获取话题数据列表线程
		private void loadLVWData(final int pageIndex, final Handler handler,final int action) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					Message msg = new Message();
					boolean isRefresh = false;
					//post方法设置
					HashMap dataMap = new HashMap();
					dataMap.put("page", pageIndex);
					List params = CommonUtil.setParams(CommonUtil.initPostMap(APIModel.SHOP_COLLECTION_LIST, dataMap),CommonUtil.ARRAY_LIST);
					//返回结果
					ArrayList<HashMap<String, Object>> retList = shopDao.getShopCollectionData(params);
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
	
}
