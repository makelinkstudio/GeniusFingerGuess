package com.bncq.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import com.bncq.R;
import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;
import com.bncq.common.MainApplication;
import com.bncq.controller.MainActivity;
import com.bncq.dao.MessageDao;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;

public class MessageService extends Service {
	//��ȡ��Ϣ�߳� 
	private MessageThread msgThread=null;
	//����鿴 
	private Intent msgIntent=null;
	private PendingIntent msgPendingIntent=null;
	//֪ͨ����Ϣ
	private int msgNotificationID;
	private Notification msgNotification=null;
	private NotificationManager msgNotificationManager=null;
	//����
	private String msgTitle;
	private String msgCon;
	
	private MessageDao messageDao;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
    @Override    
    public void onCreate() {    
        super.onCreate();    
    }  
	@Override
	public int onStartCommand(Intent intent,int flags,int startId){
		//��ʼ��
		msgNotification=new Notification();
		//��Ϣͼ
		msgNotification.icon=R.drawable.jiandao;
		// ʹ������
		msgNotification.defaults=Notification.DEFAULT_SOUND;
		// �û����֪ͨ��ɾ����֪ͨ
		msgNotification.flags=Notification.FLAG_AUTO_CANCEL;
		msgNotification.audioStreamType= android.media.AudioManager.ADJUST_LOWER;  
		msgNotificationManager=(NotificationManager) MainApplication.getContext().getSystemService(Context.NOTIFICATION_SERVICE);
		msgIntent=new Intent(this,MainActivity.class);
		msgIntent.putExtra("msgJump", true);
		msgPendingIntent=PendingIntent.getActivity(this, 0, msgIntent,PendingIntent.FLAG_ONE_SHOT);
		//����
		messageDao=new MessageDao();
		msgNotificationID=1000;
		//�����߳�
		msgThread=new MessageThread();
		msgThread.isRunning=true;
		//msgThread.start();
		msgNotification.tickerText=msgTitle+" "+msgCon;
		msgNotification.setLatestEventInfo(MessageService.this,msgTitle, msgCon, msgPendingIntent);
		
		msgNotificationManager.notify(msgNotificationID, msgNotification); 
		return super.onStartCommand(intent, flags, startId);
	}
    @Override    
    public void onDestroy() {    
        super.onDestroy();    
    }  
	class MessageThread extends Thread {
		public boolean isRunning=true;
		public void run(){
			while (isRunning) {
				try {
					//��Ϣ10���� 
					Thread.sleep(3000);
					//��ȡ��������Ϣ 
					boolean flag=setNoticeMsg();
					if(flag){
						//����֪ͨ�� 
						msgNotification.tickerText=msgTitle+" "+msgCon;
						msgNotification.setLatestEventInfo(MessageService.this,msgTitle, msgCon, msgPendingIntent);
						
						msgNotificationManager.notify(msgNotificationID, msgNotification); 
						//ÿ��֪ͨ�֪꣬ͨid����һ�£�������Ϣ���ǵ� 
						msgNotificationID++;
					}
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}
	//���ʷ������������Ϣ��������Ϣ
	private boolean  setNoticeMsg(){
		boolean flag=false;
		try {
			//��ȡ����������
			Map map=new HashMap();
			map.put(APIModel.CODE,APIModel.MESSAGE_PUSH);
			List params=CommonUtil.setParams(map, CommonUtil.DIRECT_LIST);
			Bundle data=messageDao.getPushMsg(params);
			JSONArray jsonList=new JSONArray(data.getString(APIModel.DATA));
			//��ȡ���ع㲥�����¼
			String brt_inf="";//��Ϣ��¼
			for (int i=0;i<jsonList.length();i++){
				JSONObject obj=(JSONObject) jsonList.get(i);
				String brt_type=obj.getString("BRT_TYPE");
				String sp_keyName="";
				if("0".equals(brt_type)){
					brt_inf=CommonUtil.getValueSp("brt_data", "brt_push");//��ȡ������Ϣ��¼
					sp_keyName="brt_push";
				}else if("1".equals(brt_type)){
					brt_inf=CommonUtil.getValueSp("brt_data", "brt_win");//��ȡ�н���Ϣ��¼
					sp_keyName="brt_win";
				}else if("2".equals(brt_type)){
					brt_inf=CommonUtil.getValueSp("brt_data", "brt_upd");//��ȡ������Ϣ��¼
					sp_keyName="brt_upd";
				}
				//�ж��Ƿ���ڹ㲥��¼
				String brt_id=obj.getString("BROAST_ID");
				int count=0;
				JSONArray brt_array=new JSONArray();
				if(brt_inf!=null&&!"".equals(brt_inf)){
					brt_array=new JSONArray(brt_inf);
					for(int j=0;j<brt_array.length();j++){
						JSONObject array_obj=(JSONObject) brt_array.get(j);
						if(brt_id.equals(array_obj.get("BROAST_ID"))){
							count=1;
							break;
						}
					}
				}
				//��¼�����ڣ����Թ㲥
				if(count==0){
					flag=true;
					msgTitle=obj.getString("BRT_TITLE");
					msgCon=obj.getString("BRT_CON");
					brt_array.put(obj);
					CommonUtil.setValueSp("brt_data", sp_keyName, brt_array.toString());//��¼д�뻺��
					break;
				}
			}
		} catch (Exception e) {
			flag=false;
		}
		return flag;
	}
}
