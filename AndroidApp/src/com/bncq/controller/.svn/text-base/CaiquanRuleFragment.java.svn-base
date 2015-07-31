package com.bncq.controller;

import com.bncq.R;
import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.CookieManager;
import android.webkit.CookieSyncManager;
import android.webkit.WebSettings;
import android.webkit.WebView;

public class CaiquanRuleFragment extends Fragment {
	private boolean isInit; // 是否可以开始加载数据  
	private WebView wv_InfoRule;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View cq_rule_layout=inflater.inflate(R.layout.caiquan_rule, container, false);
		isInit=true; // 是否可以开始加载数据  
		//webview设置
		wv_InfoRule=(WebView) cq_rule_layout.findViewById(R.id.wv_InfoRule);
		wv_InfoRule.getSettings().setJavaScriptEnabled(true);//设置WebView支持javascript  
		wv_InfoRule.getSettings().setUseWideViewPort(true);//设置是当前html界面自适应屏幕
		wv_InfoRule.getSettings().setLoadWithOverviewMode(true);
		wv_InfoRule.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		return cq_rule_layout;
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
	/** 
     * 初始化数据 
     */  
    private void loadData() {
    	if (isInit) {  
            isInit = false;//加载数据完成  
          //webview设置
            synCookie(getActivity(), APIModel.INFCQRULE_URL);
            wv_InfoRule.loadUrl(APIModel.INFCQRULE_URL);
    	}
    }
	//设置cookies
	private static void synCookie(Context context,String url){
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager=CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		cookieManager.removeSessionCookie();//移除
		try {
			Thread.sleep(500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String serverCookieString=APIModel.SERVER_SESSIONID+"="+CommonUtil.getValueSp("cookieInfo", APIModel.SERVER_SESSIONID);
		cookieManager.setCookie(url, serverCookieString);
		CookieSyncManager.getInstance().sync();
	}
}
