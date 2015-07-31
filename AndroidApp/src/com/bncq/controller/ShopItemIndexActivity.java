package com.bncq.controller;

import com.bncq.R;

import android.R.string;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

public class ShopItemIndexActivity extends Activity {
	private WebView wvw_shop_item_index;
	private ImageButton ibtn_shop_item_back;
	private ProgressBar pbr_shop_item;
	private LinearLayout llt_shop_item_error;
	private TextView tvw_shop_item_open;
	private String AVD_JUMP_URL;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.shop_item_index_layout);
		ibtn_shop_item_back=(ImageButton) findViewById(R.id.ibtn_shop_item_back);
		wvw_shop_item_index=(WebView) findViewById(R.id.wvw_shop_item_index);
		pbr_shop_item=(ProgressBar) findViewById(R.id.pbr_shop_item);
		llt_shop_item_error=(LinearLayout) findViewById(R.id.llt_shop_item_error);
		tvw_shop_item_open=(TextView) findViewById(R.id.tvw_shop_item_open);
		//�����쳣 ��ʾ ����
		if(llt_shop_item_error.getVisibility()==View.VISIBLE){
			llt_shop_item_error.setVisibility(View.GONE);
		}
		//��ҳ����
		wvw_shop_item_index.getSettings().setJavaScriptEnabled(true);
		wvw_shop_item_index.getSettings().setUseWideViewPort(true);
		wvw_shop_item_index.getSettings().setLoadWithOverviewMode(true);
		wvw_shop_item_index.getSettings().setCacheMode(WebSettings.LOAD_NO_CACHE);
		wvw_shop_item_index.setOverScrollMode(View.OVER_SCROLL_NEVER);
		//���λ�ȡ
		Intent intent=getIntent();
	    AVD_JUMP_URL=intent.getStringExtra("AVD_JUMP_URL");
	    //��ֹ�����ⲿ�����
	    wvw_shop_item_index.setWebViewClient(new WebViewClient(){
	        @Override
	        public boolean shouldOverrideUrlLoading(WebView view, String url) {
	        	wvw_shop_item_index.setVisibility(View.VISIBLE);
	        	if(url.indexOf("http://")!=0){
	        		return true;
	        	}
	            view.loadUrl(url);
	            return true;
	        }
	        //�쳣ʱ�򱨴�����
	        @Override
	        public void onReceivedError(WebView view, int errorCode,
	        		String description, String failingUrl) {
	        	// TODO Auto-generated method stub
	        	wvw_shop_item_index.setVisibility(View.GONE);
	        	llt_shop_item_error.setVisibility(View.VISIBLE);
	        	super.onReceivedError(view, errorCode, description, failingUrl);
	        }
	    });
	    //����������
	    wvw_shop_item_index.setWebChromeClient(new WebChromeClient(){
	    	@Override
	    	public void onProgressChanged(WebView view, int newProgress) {
	    		// TODO Auto-generated method stub
	    		if(newProgress==100){
	    			pbr_shop_item.setVisibility(View.GONE);
	    		}else{
	    			pbr_shop_item.setVisibility(View.VISIBLE);
	    			 pbr_shop_item.setProgress(newProgress);
	    		}
	    		super.onProgressChanged(view, newProgress);
	    	}
	    });
	    //����ҳ��
	    wvw_shop_item_index.loadUrl(AVD_JUMP_URL);
	    //����
		ibtn_shop_item_back.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
			}
		});
		//�����ⲿ�����
		tvw_shop_item_open.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent =new Intent(Intent.ACTION_VIEW,Uri.parse(AVD_JUMP_URL));
				startActivity(intent);
			}
		});
		//�����Ļˢ��
		llt_shop_item_error.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
    			llt_shop_item_error.setVisibility(View.GONE);
				pbr_shop_item.setVisibility(View.VISIBLE);
				wvw_shop_item_index.loadUrl(AVD_JUMP_URL);
			}
		});
	}
	//�����¼� ����back �������ǰһҳ
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK&&wvw_shop_item_index.canGoBack()){
			wvw_shop_item_index.goBack();
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}
}
