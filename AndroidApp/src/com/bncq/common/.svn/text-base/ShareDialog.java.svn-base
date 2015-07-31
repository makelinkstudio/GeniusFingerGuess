package com.bncq.common;
 
import java.io.File;

import android.app.Activity;
import android.content.Intent;
 
public class ShareDialog {
	private Activity activity;
	public ShareDialog(Activity activity){
		this.activity=activity;
	}
	
	public void ShowShare(String title,String content,String imgUrl){
		Intent intent=new Intent(Intent.ACTION_SEND);    
		if(imgUrl==null&&"".equals(imgUrl)){
			  intent.setType("text/plain");    // ´¿ÎÄ±¾ 
		}else{
			intent.setType("image/*");    
			intent.putExtra(Intent.EXTRA_STREAM, imgUrl);
		}
        intent.putExtra(Intent.EXTRA_SUBJECT, title);    
        intent.putExtra(Intent.EXTRA_TEXT, content);    
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);    
        activity.startActivity(Intent.createChooser(intent, "·ÖÏí"));  
	}
}
