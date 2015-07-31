package com.bncq.controller;

import com.bncq.R;
import com.bncq.common.CommonUtil;
import com.bncq.common.UIUtil;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;

public class MoreFragment extends Fragment {
	  //����
	private boolean isInit ;  
	//UI
	private TextView tvw_set_edt_userInfo;
	private TextView tvw_set_clean;
	private TextView tvw_set_update;
	private TextView tvw_set_team;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View myNoticeLayout=inflater.inflate(R.layout.more_layout, container,false);
		//UI
		tvw_set_edt_userInfo=(TextView) myNoticeLayout.findViewById(R.id.tvw_set_edt_userInfo);
		tvw_set_clean=(TextView) myNoticeLayout.findViewById(R.id.tvw_set_clean);
		tvw_set_team=(TextView) myNoticeLayout.findViewById(R.id.tvw_set_team);
		//����
		isInit = true;  
		//��ת���˱༭ҳ��
		tvw_set_edt_userInfo.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent =new Intent(getActivity(),EditorMyInfoActivity.class);
				startActivity(intent);
			}
		});
		//������
		tvw_set_clean.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				try {
					CommonUtil.removeSp("fun_mat");//��ȭ  ��¼������Ϣ
					UIUtil.showMsg("����ɹ�");
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
		tvw_set_team.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent=new Intent(getActivity().getApplicationContext(),AboutSoftwareActivity.class);
				startActivity(intent);
			}
		});
		return myNoticeLayout;
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
    		
    	}
    }
}
