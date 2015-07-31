package com.bncq.service;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.bncq.R;

import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.DialogInterface.OnClickListener;
import android.net.Uri;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;

public class UpdateManager {

		private Context context;
		//提示语
		private String updateMsg="";
		//返回安装包url
		private String apkUrl="";
		
		private Dialog noticeDialog;
		
		private Dialog downloadDialog;
		//安装包下载路径
		private static final String savePath="/sdcard/bncqUpdate/";
		
		private static final String saveFileName=savePath+"UpdateDemoRelease.apk";
		
		//进度条 与通知ui 刷新的handler 和msg 常量
		private ProgressBar mProgress;
		
		private static final int DOWN_UPDATE =1;
		
		private static final int DOWN_OVER=2;
		
		private int progress;  
		
		private Thread downLoadThread;
		
		private boolean interceptFlag=false;
		
		private Handler mHandler=new Handler(){
			public void handleMessage(android.os.Message msg) {
				switch (msg.what) {
				case DOWN_UPDATE:
					mProgress.setProgress(progress);
					break;
				case DOWN_OVER:
					installApk();
					break;
				default:
					break;
				}
			};
		};
		
		public UpdateManager(Context context){
			this.context=context;
		}
		
		public void checkUpdateInfo(){
			showNoticeDialog();
		}
		
		private  void showNoticeDialog(){
			Builder builder=new Builder(context);
			builder.setTitle("软件版本更新");
			builder.setMessage(updateMsg);
			builder.setPositiveButton("下载", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					 dialog.dismiss();
					 showDownloadDialog();
				}
			});
			builder.setNegativeButton("以后再说", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
				}
			});
			noticeDialog=builder.create();
			noticeDialog.show();
		}
		private  void showDownloadDialog(){
			Builder builder=new Builder(context);
			builder.setTitle("软件版本更新");
			final LayoutInflater inflater=LayoutInflater.from(context);
			View view=inflater.inflate(R.layout.progress, null);
			
			builder.setView(view);
			builder.setNegativeButton("取消", new OnClickListener() {
				@Override
				public void onClick(DialogInterface dialog, int which) {
					dialog.dismiss();
					interceptFlag=true;
				}
			});
			downloadDialog=builder.create();
			downloadDialog.show();
			downloadApk();
		}
		 /** 
	     * 下载apk 
	     */  
		private void downloadApk(){
			downLoadThread=new Thread(downmApkRun);
			downLoadThread.start();
		}
		private Runnable downmApkRun=new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					URL url=new URL(apkUrl);
					HttpURLConnection conn=(HttpURLConnection) url.openConnection();
					conn.connect();
					int length=conn.getContentLength();
					InputStream is=conn.getInputStream();
					
					File file=new File(savePath);
					if (!file.exists()) {
						file.mkdir();
					}
					String apkFileName=saveFileName;
					File apkFile=new File(apkFileName);
					if(!apkFile.exists()){
						apkFile.createNewFile();
					}
					FileOutputStream fos=new FileOutputStream(apkFile);
					
					int count=0;
					byte buf[]=new byte[1024];
					do{
						int numread=is.read(buf);
						count+=numread;
						progress=(int) ((float)(count /length)*100);
						mHandler.sendEmptyMessage(DOWN_UPDATE);
						if(numread<=0){
							//下载完通知安装
							mHandler.sendEmptyMessage(DOWN_OVER);
							break;
						}
						fos.write(buf,0,numread);
					}while(!interceptFlag);//点击取消下载
					fos.close();
					is.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		};
		//安装apk
		private void installApk(){
			File apkFile=new File(saveFileName);
			if(!apkFile.exists()){
				return;
			}
			Intent intent=new Intent(Intent.ACTION_VIEW);
			intent.setDataAndType(Uri.parse("file://"+apkFile.toString()),"application/vnd.android.package-archive");
			context.startActivity(intent);
		}
}
