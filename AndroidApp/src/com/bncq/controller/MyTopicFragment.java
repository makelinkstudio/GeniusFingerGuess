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
import com.bncq.model.MyTopicAdapter;
import com.bncq.model.PullToRefreshListView;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ProgressBar;
import android.widget.TextView;

public class MyTopicFragment extends Fragment {

	//����
	private static TopicDao topicDao;
	private ArrayList<HashMap<String , Object>> adapterList;
	private int lvwSumData;
	private int preSumData;
	private Handler getLvHandler;
	private MyTopicAdapter myTopicAdapter;
	private boolean isInit; // �Ƿ���Կ�ʼ��������  
	//UI
	private PullToRefreshListView lvw_myTopic;
	private View lvw_footer;
	private TextView lvw_foot_more;
	private ProgressBar lvw_foot_progress;
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup,Bundle savedInstanceState) {
		//����
		topicDao=new TopicDao();
		lvwSumData=MainApplication.PAGE_SIZE;
		preSumData=0;
		adapterList=new ArrayList<HashMap<String,Object>>();
		isInit = true;  
		//UI
		View myTopicLayout= inflater.inflate(R.layout.mytopic_layout, viewGroup, false);
		lvw_myTopic=(PullToRefreshListView) myTopicLayout.findViewById(R.id.lvw_myTopic);
		return myTopicLayout;
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
			initListView();
			getLvHandler=UIUtil.getLvHandler(lvw_myTopic, myTopicAdapter, lvw_foot_more, lvw_foot_progress, MainApplication.PAGE_SIZE,adapterList);
			loadLVWData(1, getLvHandler, UIUtil.LISTVIEW_ACTION_SCROLL);
        }
	}
	//��ʼ���ҵĻ����б�
	private void initListView(){
	    myTopicAdapter=new MyTopicAdapter(getActivity(), adapterList);
	    lvw_footer=getActivity().getLayoutInflater().inflate(R.layout.listview_footer, null);
	    lvw_foot_progress=(ProgressBar) lvw_footer.findViewById(R.id.listview_foot_progress);
	    lvw_foot_more=(TextView) lvw_footer.findViewById(R.id.listview_foot_more);
		lvw_myTopic.addFooterView(lvw_footer);
	    lvw_myTopic.setAdapter(myTopicAdapter);
		lvw_myTopic.setTag(UIUtil.LISTVIEW_DATA_MORE);
		// item����¼�
		lvw_myTopic.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,int position, long id) {
				// TODO Auto-generated method stub
				try {
					TextView tvw_topic_topicId = (TextView) view.findViewById(R.id.tvw_topic_topicId);
					TextView tvw_topic_Title = (TextView) view.findViewById(R.id.tvw_topic_Title);
					TextView tvw_topic_User = (TextView) view.findViewById(R.id.tvw_topic_User);
					TextView tvw_topic_ComTime = (TextView) view.findViewById(R.id.tvw_topic_ComTime);
					TextView tvw_topic_ComNum = (TextView) view.findViewById(R.id.tvw_topic_ComNum);
					if (!TextUtils.isEmpty(tvw_topic_topicId.getText())) {
						String topic_id=tvw_topic_topicId.getText().toString();
						Intent intent =new Intent(getActivity(),TopicDetailActivity.class);
						intent.putExtra("topic_id",topic_id);
						intent.putExtra("topic_man", tvw_topic_User.getText().toString());
						intent.putExtra("topic_con", tvw_topic_Title.getText().toString());
						intent.putExtra("topic_time",tvw_topic_ComTime.getText().toString());
						intent.putExtra("topic_num", tvw_topic_ComNum.getText().toString());
						startActivity(intent);
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		lvw_myTopic.setOnScrollListener(new AbsListView.OnScrollListener() {
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				lvw_myTopic.onScrollStateChanged(view, scrollState);
				//����Ϊ��--���ü������������
				if(adapterList.isEmpty()) return;
				//�ж��Ƿ�������ײ�
				boolean scrollEnd =false;
				try {
					if(view.getPositionForView(lvw_footer)==view.getLastVisiblePosition())
						scrollEnd=true;
				} catch (Exception e) {
						scrollEnd=false;
				}
				
				int lvDataState =Integer.parseInt(lvw_myTopic.getTag().toString());
				if(scrollEnd&&lvDataState==UIUtil.LISTVIEW_DATA_MORE){
					lvw_myTopic.setTag(UIUtil.LISTVIEW_DATA_MORE);
					lvw_foot_more.setText(R.string.load_ing);
					lvw_foot_progress.setVisibility(View.VISIBLE);
					//��ǰpageIndex
					int pageIndex=lvwSumData/MainApplication.PAGE_SIZE;
					if(preSumData<lvwSumData){//�ж��Ƿ���Ҫ ��ˢ���ݣ���������ظ�ִ��
						loadLVWData(pageIndex, getLvHandler, UIUtil.LISTVIEW_ACTION_SCROLL);
						preSumData=lvwSumData;
					}
				}
			}
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,int visibleItemCount, int totalItemCount) {
				// TODO Auto-generated method stub
				lvw_myTopic.onScroll(view, firstVisibleItem, visibleItemCount, totalItemCount);
			}
		});
		//ˢ��
		lvw_myTopic.setOnRefreshListener(new PullToRefreshListView.OnRefreshListener() {
            public void onRefresh() {
            	lvwSumData=MainApplication.PAGE_SIZE;
            	preSumData=0;//����ǰ������Ϊ0��
            	loadLVWData(1, getLvHandler, UIUtil.LISTVIEW_ACTION_REFRESH);
            }
        });		
	}
	//���������ȡ���������б��߳�
	private void loadLVWData(final int pageIndex,final Handler handler,final int action){
		new Thread(new Runnable() {
			@Override
			public void run() {
				Message msg = new Message();
				boolean isRefresh=false;
				HashMap dataMap=new HashMap();
				dataMap.put("page", pageIndex);
				String user_id=CommonUtil.getValueSp("userInfo", "USER_ID");
				dataMap.put("uid", user_id);
				List params=CommonUtil.setParams(CommonUtil.initPostMap(APIModel.TOPIC_TOPIC_LIST, dataMap), CommonUtil.ARRAY_LIST);
				ArrayList<HashMap<String, Object>> retList=topicDao.getForumData(params);
				if(action==UIUtil.LISTVIEW_ACTION_REFRESH){
					adapterList.clear();
				}else{
				}
	            //adapterList.addAll(retList);
	            lvwSumData+=retList.size();
				msg.what=retList.size();
				msg.arg1=action;
				msg.obj=retList;
				handler.sendMessage(msg);
			}
		}).start();
	}
}
