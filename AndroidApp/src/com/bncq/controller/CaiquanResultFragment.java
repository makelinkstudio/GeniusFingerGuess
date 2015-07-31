package com.bncq.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bncq.R;
import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;
import com.bncq.common.MainApplication;
import com.bncq.common.UIUtil;
import com.bncq.dao.ShopDao;
import com.bncq.model.CaiquanResultAdapter;
import com.bncq.model.PullToRefreshListView;
import com.bncq.model.ShopIndexAdapter;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.AbsListView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class CaiquanResultFragment extends Fragment {
	private PullToRefreshListView lvw_cq_result;
	private View lvw_footer;
	private TextView lvw_foot_more;
	private ProgressBar lvw_foot_progress;
	
	private boolean isInit; // �Ƿ���Կ�ʼ��������  
	private CaiquanResultAdapter caiquanResultAdapter;
	private static Handler getLvHandler;
	private ArrayList<HashMap<String, Object>> adapterList;
	private int lvwSumData;
	private int preSumData;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View cq_result_layout=inflater.inflate(R.layout.caiquan_result, container, false);
		lvw_cq_result=(PullToRefreshListView) cq_result_layout.findViewById(R.id.lvw_cq_result);
		isInit = true; 
		lvwSumData=MainApplication.PAGE_SIZE;
		preSumData=0;
		adapterList=new ArrayList<HashMap<String,Object>>();
		return cq_result_layout;
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
		// ÿ���л�fragmentʱ���õķ���  
		if(getUserVisibleHint()){
				loadData();
		}
	}
	/** 
     * ��ʼ������ 
     */  
    private void loadData() {  
        if (isInit) {  
            isInit = false;//�����������  
          //ִ����̳�����߳�
			initListView();
			getLvHandler=UIUtil.getLvHandler(lvw_cq_result, caiquanResultAdapter, lvw_foot_more, lvw_foot_progress, MainApplication.PAGE_SIZE,adapterList);
			loadLVWData(1, getLvHandler, UIUtil.LISTVIEW_ACTION_SCROLL);
        }  
    } 
 // ��ʼ���ҵĻ����б�
 	private void initListView() {
 		caiquanResultAdapter = new CaiquanResultAdapter(getActivity(), adapterList);
 		lvw_footer = getActivity().getLayoutInflater().inflate(R.layout.listview_footer, null);
 		lvw_foot_progress = (ProgressBar) lvw_footer.findViewById(R.id.listview_foot_progress);
 		lvw_foot_more = (TextView) lvw_footer.findViewById(R.id.listview_foot_more);
 		lvw_cq_result.addFooterView(lvw_footer);
 		lvw_cq_result.setAdapter(caiquanResultAdapter);
 		lvw_cq_result.setTag(UIUtil.LISTVIEW_DATA_MORE);

 		lvw_cq_result.setOnScrollListener(new AbsListView.OnScrollListener() {
 			@Override
 			public void onScrollStateChanged(AbsListView view, int scrollState) {
 				lvw_cq_result.onScrollStateChanged(view, scrollState);
 				// ����Ϊ��--���ü������������
 				if (adapterList.isEmpty())
 					return;
 				// �ж��Ƿ�������ײ�
 				boolean scrollEnd = false;
 				try {
 					if (view.getPositionForView(lvw_footer) == view.getLastVisiblePosition())
 						scrollEnd = true;
 				} catch (Exception e) {
 					scrollEnd = false;
 				}

 				int lvDataState = Integer.parseInt(lvw_cq_result.getTag().toString());
 				if (scrollEnd && lvDataState == UIUtil.LISTVIEW_DATA_MORE) {
 					lvw_cq_result.setTag(UIUtil.LISTVIEW_DATA_MORE);
 					lvw_foot_more.setText(R.string.load_ing);
 					lvw_foot_progress.setVisibility(View.VISIBLE);
 					// ��ǰpageIndex
 					int pageIndex = lvwSumData / MainApplication.PAGE_SIZE;
 					if (preSumData < lvwSumData) {// �ж��Ƿ���Ҫ ��ˢ���ݣ���������ظ�ִ��
 						loadLVWData(pageIndex, getLvHandler,UIUtil.LISTVIEW_ACTION_SCROLL);
 						preSumData = lvwSumData;
 					}
 				}
 			}

 			@Override
 			public void onScroll(AbsListView view, int firstVisibleItem,
 					int visibleItemCount, int totalItemCount) {
 				// TODO Auto-generated method stub
 				lvw_cq_result.onScroll(view, firstVisibleItem, visibleItemCount,totalItemCount);
 			}
 		});
 		// ˢ��
 		lvw_cq_result.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
 					public void onRefresh() {
 						lvwSumData = MainApplication.PAGE_SIZE;
 						preSumData = 0;// ����ǰ������Ϊ0��
 						loadLVWData(1, getLvHandler,UIUtil.LISTVIEW_ACTION_REFRESH);
 					}
 		});
 	}
 	// ���������ȡ���������б��߳�
 	private void loadLVWData(final int pageIndex, final Handler handler,final int action) {
 		new Thread(new Runnable() {
 			@Override
 			public void run() {
 				Message msg = new Message();
 				boolean isRefresh = false;
 				//post��������
 				HashMap dataMap = new HashMap();
 				dataMap.put("page", pageIndex);
 				List params = CommonUtil.setParams(CommonUtil.initPostMap(APIModel.SHOP_LIST, dataMap),CommonUtil.ARRAY_LIST);
 				//���ؽ��
 				ArrayList<HashMap<String, Object>> retList = new ArrayList<HashMap<String,Object>>();
 				HashMap<String, Object> map=new HashMap<String, Object>();
 				retList.add(map);
 				retList.add(map);
 				retList.add(map);
 				retList.add(map);
 				retList.add(map);
 				
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
