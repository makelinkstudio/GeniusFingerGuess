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
	private boolean isInit; // �Ƿ���Կ�ʼ��������  
	private WebView wv_InfoRule;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		View cq_rule_layout=inflater.inflate(R.layout.caiquan_rule, container, false);
		isInit=true; // �Ƿ���Կ�ʼ��������  
		//webview����
		wv_InfoRule=(WebView) cq_rule_layout.findViewById(R.id.wv_InfoRule);
		wv_InfoRule.getSettings().setJavaScriptEnabled(true);//����WebView֧��javascript  
		wv_InfoRule.getSettings().setUseWideViewPort(true);//�����ǵ�ǰhtml��������Ӧ��Ļ
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
          //webview����
            synCookie(getActivity(), APIModel.INFCQRULE_URL);
            wv_InfoRule.loadUrl(APIModel.INFCQRULE_URL);
    	}
    }
	//����cookies
	private static void synCookie(Context context,String url){
		CookieSyncManager.createInstance(context);
		CookieManager cookieManager=CookieManager.getInstance();
		cookieManager.setAcceptCookie(true);
		cookieManager.removeSessionCookie();//�Ƴ�
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
