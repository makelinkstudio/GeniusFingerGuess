package com.bncq.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

import org.json.JSONObject;

import com.bncq.R;
import com.bncq.common.APIModel;
import com.bncq.common.CommonUtil;
import com.bncq.common.DateUtil;
import com.bncq.common.StringUtil;
import com.bncq.common.UIUtil;
import com.bncq.dao.CaiQuanDao;
import com.bncq.model.RotateAnimation;
import com.bncq.model.shakeAnimation;
import com.bncq.model.RotateAnimation.InterpolatedTimeListener;
import com.bncq.service.UpdateManager;
import com.bncq.service.UpdateMyInfoManager;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ObjectAnimator;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewTreeObserver;
import android.view.ViewTreeObserver.OnGlobalLayoutListener;
import android.view.ViewTreeObserver.OnPreDrawListener;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class CaiquanGameFragment extends Fragment implements InterpolatedTimeListener {
	//UI
	private TextView tw_cq_now_Mat;
	private TextView tw_cq_next_mat;
 	private TextView tvw_timer;//����ʱ
 	//ͼƬ����
 	private ImageView ivw_camp_a;
 	private ImageView ivw_camp_b;
 	private ImageView ivw_jian_water;
 	private ImageView ivw_jian_mid;
 	private ImageView ivw_shi_water;
 	private ImageView ivw_shi_mid;
 	private ImageView ivw_bu_water;
 	private ImageView ivw_bu_mid;
	// ��ʼ���������ҳ
	private  View promptView;
	private TextView tvw_mat_name;
	private  TextView tvw_comfirm;
	private  TextView tvw_win_camp;
	private  TextView tvw_lose_camp;
	private  ImageView ivw_win_punch;
	private  ImageView ivw_lose_punch;
	
	//����
	private  TextView tvw_win_integral;
	private  TextView tvw_you_result;
	private  TextView tvw_lose_integral;
	private  TextView tvw_add_integral;
	private  PopupWindow pww_result;
    //����
	private static CaiQuanDao caiQuanDao;
	private boolean isInit; // �Ƿ���Կ�ʼ��������  
	private static String user_camp;
	private static int user_punch;
	private static String punName;
	private View punchView;
	private static String mat_id;//���α��ID
	private static String mat_status;//����״̬
	private static String mat_End_Time;//����ʱ��
	private static Long sysTime;//ϵͳʱ��
	private  boolean isGetResult;//�Ƿ�ִ��ѭ��
	private Long setTime;//����ʱ����
	private boolean isShowTime;//�Ƿ�ֹͣѭ��
	private boolean isSelectCamp;//�Ƿ����ѡ��camp
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
		//UI
		View cq_game_layout=inflater.inflate(R.layout.caiquan_game, container, false);
		tw_cq_now_Mat=(TextView) cq_game_layout.findViewById(R.id.tw_cq_now_Mat);
		tw_cq_next_mat=(TextView) cq_game_layout.findViewById(R.id.tw_cq_next_mat);
	    tvw_timer=(TextView) cq_game_layout.findViewById(R.id.tvw_timer);
	    //ͼƬ����
	    ivw_camp_a=(ImageView) cq_game_layout.findViewById(R.id.ivw_camp_a);
	    ivw_camp_b=(ImageView) cq_game_layout.findViewById(R.id.ivw_camp_b);
		ivw_jian_water=(ImageView) cq_game_layout.findViewById(R.id.ivw_jian_water);
		ivw_jian_mid=(ImageView) cq_game_layout.findViewById(R.id.ivw_jian_mid);
		ivw_shi_water=(ImageView) cq_game_layout.findViewById(R.id.ivw_shi_water);
		ivw_shi_mid=(ImageView) cq_game_layout.findViewById(R.id.ivw_shi_mid);
		ivw_bu_water=(ImageView) cq_game_layout.findViewById(R.id.ivw_bu_water);
		ivw_bu_mid=(ImageView) cq_game_layout.findViewById(R.id.ivw_bu_mid);
	    
		//����
		caiQuanDao=new CaiQuanDao();
		List params=new ArrayList();
		isInit = true;  
		user_camp="-1";
		user_punch=-1;
		punName="����";
		//�Ƿ���ʾʱ��
		isShowTime=false;
		//�Ƿ����ѡ����Ӫ
		isSelectCamp=false;
		//AB��Ӫ����¼�
		ivw_camp_a.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isSelectCamp){
					ivw_camp_a.setImageResource(R.drawable.camp_a_on);
					ivw_camp_b.setImageResource(R.drawable.camp_b_on);
					ivw_camp_a.setImageResource(R.drawable.camp_a_off);
					if(user_punch==-1){
						shakePun();
					}
					user_camp="A";
				}
			}
		});
		ivw_camp_b.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				if(isSelectCamp){
					ivw_camp_a.setImageResource(R.drawable.camp_a_on);
					ivw_camp_b.setImageResource(R.drawable.camp_b_on);
					ivw_camp_b.setImageResource(R.drawable.camp_b_off);
					if(user_punch==-1){
						shakePun();
					}
					user_camp="B";
				}
			}
		});
		ivw_jian_mid.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				joinPunch(0,v);
			}
		});
		ivw_shi_mid.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				joinPunch(1,v);
			}
		});
		ivw_bu_mid.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				joinPunch(2,v);
			}
		});
		
		return cq_game_layout;
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
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
	}
	//�����߳�
	@Override
	public void onDestroy() {
		//�ع�����
		isShowTime=false;
		isGetResult=false;
		getInfohandler.removeCallbacks(getInfoRun);
		setUPHandler.removeCallbacks(setUPRun);
		handintegralHDL.removeCallbacks(handIntegralRun);
		timeHandler.removeCallbacks(timeUpRun);
		shakeHdl.removeCallbacks(shakeRun);
		if(pww_result!=null){
			if(pww_result.isShowing()){
				pww_result.dismiss();
			}
		}
		super.onDestroy();
	}
	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		// ÿ���л�fragmentʱ���õķ���  
		if(getUserVisibleHint()){
				loadData();
		}else{
			if(pww_result!=null){
				if(pww_result.isShowing()){
					pww_result.dismiss();
				}
			}
		}
	}
	/** 
     * ��ʼ������ 
     */  
    private void loadData() {
    	if (isInit) {  
            isInit = false;//�����������  
    	   //���ص�ǰ������Ϣ������ʱ��web����
           new Thread(getInfoRun).start();
            //�ӳٽ�������̼߳���
			TimerTask task =new TimerTask() {
				@Override
				public void run() {
		            isGetResult=true;
					new Thread(handIntegralRun).start();
				}
			};
			Timer timer=new Timer();
			timer.schedule(task, 2000);
			//punchWaterAnima(ivw_jian_water,1f);
        }  
    	
    } 
	private void joinPunch(int value,View view){
		if(!"-1".equals(user_camp)){
			user_punch=value;
			punchView=view;
			if(-1!=user_punch){
				new Thread(setUPRun).start();
			}else{
				UIUtil.showMsg("��ѡ������");
			}
		}else{
			UIUtil.showMsg("��ѡ����Ӫ");
		}
	}
	//�̴߳���
	//��ȡ�û���ȭ��Ϣ
	private Runnable getInfoRun=new Runnable() {
		public void run() {
			Bundle data=new Bundle();
			Bundle info_data=new Bundle();
			Bundle up_data=new Bundle();
			Message msg=new Message();
			try {
				//��ȡ������Ϣ  ���÷��ʲ���
	            Map map=new HashMap();
	    		map.put(APIModel.CODE, APIModel.CQ_INF_ALL);
	    		List params=CommonUtil.setParams(map, CommonUtil.DIRECT_LIST);
				info_data=caiQuanDao.getCQAllInfo(params);
				//�ɹ���ȡ��ǰ������Ϣ
				if(info_data.getBoolean("flag")){
					//��ȡϵͳʱ��
					getSysTime();
					//��ȡ��ȭ��Ҫ��Ϣ
					JSONObject obj_Now=new JSONObject(info_data.getString("THIS_MAT_INFO"));
					JSONObject obj_Next=new JSONObject(info_data.getString("NEXT_MAT_INFO"));
					//���ó�����Ϣ
					mat_status=info_data.getString("THIS_MAT_STATUS");//����״̬
					data.putString("MAT_CODE_CH_NOW", obj_Now.getString("MAT_CODE_CH"));//������
					//�ж��Ƿ�����һ��
					if(!"false".equals(obj_Next.getString("MAT_TYPE"))){
						data.putString("MAT_CODE_CH_NEXT", obj_Next.getString("MAT_CODE_CH"));//��һ������
					}else{
						data.putString("MAT_CODE_CH_NEXT", getResources().getString(R.string.cq_now_no_game));//���޳���
					}
					//����mat_id ��ȡ�û���ȭ��Ϣ
					mat_id=obj_Now.getString("MAT_ID");
					mat_End_Time=obj_Now.getString("END_TIME");
					params.clear();
				    map.clear();
					map.put("mat_id", mat_id);
					params=CommonUtil.setParams(CommonUtil.initPostMap(APIModel.CQ_UP_INFO, map), CommonUtil.ARRAY_LIST);
					up_data=caiQuanDao.getCQUserPunchInfo(params);
					//�û���ȭ����
					data.putAll(up_data);
					msg.setData(data);
					getInfohandler.sendMessage(msg);
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	};
	//���²�ȭUI
	private  Handler getInfohandler=new Handler(){
			@Override
			public void handleMessage(Message msg){
				Bundle data=msg.getData();
				try {
					//��ǰ������
					if(StringUtil.stringiSNotEmpty(data.getString("MAT_CODE_CH_NOW"))){
						tw_cq_now_Mat.setText(data.getString("MAT_CODE_CH_NOW").trim());
					}
					//��һ������
					if(StringUtil.stringiSNotEmpty(data.getString("MAT_CODE_CH_NEXT"))){
						tw_cq_next_mat.setText(data.getString("MAT_CODE_CH_NEXT").trim());
					}
					//�û�ѡ���ȭ���
					if(data.getBoolean("flag")){
							//��ʼ��������ȭ��Ϣ
						    user_camp=data.getString("U_CAMP");
						    user_punch=Integer.valueOf(data.getString("U_PUNCH"));
						    //��ʼ��ͼƬ����
						    ivw_camp_a.setBackgroundResource(R.drawable.camp_a_on);
						    ivw_camp_b.setBackgroundResource(R.drawable.camp_b_on);
						    ivw_jian_mid.setTag(false);
						    ivw_shi_mid.setTag(false);
						    ivw_bu_mid.setTag(false);
						    //���� ͼƬ����Ч��
						    if("A".equals(user_camp)){
						    	ivw_camp_a.setImageResource(R.drawable.camp_a_off);
						    }else if("B".equals(user_camp)){
						    	ivw_camp_b.setImageResource(R.drawable.camp_b_off);
						    }
							if(user_punch==0){
								ivw_jian_mid.setTag(true);
								punchView=ivw_jian_mid;
							}else if(user_punch==1){
								ivw_shi_mid.setTag(true);
								punchView=ivw_shi_mid;
							}else if(user_punch==2){
								ivw_bu_mid.setTag(true);
								punchView=ivw_bu_mid;
							}
							punchMidTurn(punchView);
					}else{
						//����ζ��ȭ������ʾ
						Timer time =new Timer();
						time.schedule(new TimerTask() {
							@Override
							public void run() {
								new Thread(shakeRun).start();
							}
						}, 900);
					}
					//����ʱ�߳�
					TimerTask task =new TimerTask() {
						@Override
						public void run() {
							new Thread(timeUpRun).start();
						}
					};
					Timer timer=new Timer();
					timer.schedule(task, 10);
		         
				} catch (Exception e) {
					Log.e("log",e.getMessage());
				}
			}
	};
	// �����ȭ
	private Runnable setUPRun = new Runnable() {
		@Override
		public void run() {
			Bundle data = new Bundle();
			Message msg = new Message();
			// ���ò���
			Map map = new HashMap();
			Map paramsMap = new HashMap();
			map.put("mat_id", mat_id);
			map.put("u_camp", user_camp);
			map.put("u_punch", user_punch);
			List params = CommonUtil.setParams(CommonUtil.initPostMap(APIModel.CQ_SET_UP, map), CommonUtil.ARRAY_LIST);
			//�û���ȭ
			data = caiQuanDao.setUserPunch(params);
			msg.setData(data);
			setUPHandler.sendMessage(msg);
		}
	};
	// �����û�ѡ���ȭ�߳�
	private  Handler setUPHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			Bundle data = msg.getData();
			boolean flag = data.getBoolean("flag");
			String retMsg = data.getString("msg");
			try {
				if (flag) {
					ivw_jian_mid.setTag(false);
					ivw_shi_mid.setTag(false);
					ivw_bu_mid.setTag(false);
					punchView.setTag(true);
					punchMidTurn(punchView);
					//���¶������˻�����Ϣ
					UpdateMyInfoManager updateMyInfoManager=new UpdateMyInfoManager(getActivity().getApplicationContext());
					updateMyInfoManager.updateMyInfo();
				}
				UIUtil.showMsg(retMsg);
			} catch (Exception e) {
				
			}
		}
	};
 //���µ���ʱ�߳�
  private 	Handler timeHandler =new Handler(){
 		public void handleMessage(Message msg) {
 			try {
 				if(setTime>0){
 					setTime=setTime-1;
 					tvw_timer.setText(DateUtil.getCountDownTimeShort(String.valueOf(setTime)));
 				}else if(setTime==0){
 					isShowTime=false;
 					Timer timer=new Timer();
 					//���³�����Ϣ
 					TimerTask task=new TimerTask() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							new Thread(getInfoRun).start();
						}
					};
 					timer.schedule(task, 500);
 					//���������
 					TimerTask task2=new TimerTask() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							isGetResult=true;//����ʼ��ѯ����ѭ��
							new Thread(handIntegralRun).start();
						}
					};
					timer.schedule(task2, 1000);
 				}else{
 					isShowTime=false;
 				}
			} catch (Exception e) {
				isShowTime=false;
			}
 	   };
 	};
 	//ִ��ѭ��������ʱ
 	private Runnable timeUpRun=new Runnable() {
 		@Override
 		public void run() {
 			// TODO Auto-generated method stub
 			intiTime();
 			while(isShowTime){
 				try {
 					Thread.sleep(1000);
 					if(isShowTime){
 						timeHandler.sendEmptyMessage(0);
 					}
 				} catch (Exception e) {
 					isShowTime=false;
 				}
 			}
 		}
	};
	//��ѯ �Ƿ����Ϸ���
	private  Runnable handIntegralRun=new Runnable() {
		@Override
		public void run() {
				try {
					while (isGetResult) {
						String isShowRes=CommonUtil.getValueSp("fun_mat", "isShowRes");
						if(isShowRes!=null&&"true".equals(isShowRes)){
							isGetResult=false;
							continue;
						}
						//���ϵͳʱ�� ���Ƿ��Ѿ������ѯ���׼��ʱ��
						Map map=new HashMap();
						Bundle data=new Bundle();
						List paramList=CommonUtil.setParams(CommonUtil.initPostMap(APIModel.CQ_INF_ALL, map), CommonUtil.ARRAY_LIST);
						data=caiQuanDao.getCQAllInfo(paramList);
						JSONObject obj_Now=new JSONObject(data.getString("THIS_MAT_INFO"));
						String mat_now_id=obj_Now.getString("MAT_ID");
						long begin_time=obj_Now.getLong("BEGIN_TIME");
						long end_time=obj_Now.getLong("END_TIME");
						if(StringUtil.stringiSNotEmpty(end_time)){
							long min_time=sysTime-end_time;
							//�¿��� ǰ10���ӣ�����һ�γ��ν��
							if(min_time<0&&sysTime>=begin_time&&sysTime<begin_time+DateUtil.RANGE_MINUTE){
								map.clear();
								data.clear();
								paramList.clear();
								//��ȡ��һ����mat_id
								map.put("mat_id", mat_now_id);
								paramList=CommonUtil.setParams(CommonUtil.initPostMap(APIModel.CQ_GET_LASTMAT_INFO, map), CommonUtil.ARRAY_LIST);
								data=caiQuanDao.getCQLastMat(paramList);
								//��ѯ��һ���ν��
								if(data.getBoolean("flag")){
									String pre_mat_id=data.getString("MAT_ID");
									String pre_mat_name=data.getString("MAT_CODE_CH");
									map.clear();
									data.clear();
									paramList.clear();
									map.put("mat_id", pre_mat_id);
									paramList=CommonUtil.setParams(CommonUtil.initPostMap(APIModel.CQ_GETRESULT, map), CommonUtil.ARRAY_LIST);
									data=caiQuanDao.getCQResult(paramList);
									if(data.getBoolean("flag")&&data.getBoolean("is_user_join")){
										initPopWin();
										Message msg=new Message();
										data.putString("pre_mat_name",pre_mat_name);
										msg.setData(data);
										handintegralHDL.sendMessage(msg);
									}else if(data.getBoolean("is_user_join")){
										isGetResult=false;
									}
								}
							}else{
								isGetResult=false;
							}
							Thread.sleep(5000);
							sysTime+=5L;//����ֹͣʱ��
						}
					}
				} catch (Exception e) {
					isGetResult=false;
					e.printStackTrace();
				}
		}
	};
	//����  ������UI
	private  Handler handintegralHDL=new Handler(){
		public void handleMessage(Message msg) {
			try {
				Bundle data=msg.getData();
				tvw_mat_name.setText(data.getString("pre_mat_name"));
				tvw_win_camp.setText(data.get("WIN_CAMP").toString()+getResources().getString(R.string.cq_zhenxing));
				tvw_lose_camp.setText(data.get("LOST_CAMP").toString()+getResources().getString(R.string.cq_zhenxing));
				ivw_win_punch.setImageResource(UIUtil.selPunImgId(data.get("WIN_PUNCH").toString(), 0));
				ivw_lose_punch.setImageResource(UIUtil.selPunImgId(data.get("LOST_PUNCH").toString(), 1));
				//����
				tvw_win_integral.setText(data.getString("WIN_INTEGRAL"));
				tvw_lose_integral.setText(data.getString("LOST_INTEGRAL"));
				tvw_add_integral.setText(data.getString("USER_PUNCH_INTEFRAL_CHANGE"));
				isGetResult=false;//��ֹѭ��
				CommonUtil.setValueSp("fun_mat", "isShowRes", "true");//�Ѿ���ʾ���
				//��ʾ������
				showPopWin();
			} catch (Exception e) {
			}
		};
	};
	//��ȭ������ʾ
	private Runnable shakeRun=new Runnable() {
		@Override
		public void run() {
			shakeHdl.sendEmptyMessage(0);
		}
	};
	//��ȭ������ʾ
	private Handler shakeHdl=new Handler(){
		 public void handleMessage(Message msg) {
			  shakeCamp();
		 };
	};
	// ��ʼ��ʱ��
	private void intiTime() {
		isShowTime = true;
		String end_time = mat_End_Time;
		if (StringUtil.stringiSNotEmpty(mat_End_Time)) {
			long temp_time = Long.valueOf(end_time);
			temp_time += DateUtil.RANGE_MINUTE;
			setTime = temp_time - sysTime;
			// Log.e("log", isShowTime+" "+setTime+"  "+temp_time+" "+sysTime);
		} else {
			isShowTime = false;
		}
	}
	//��ȡ��ǰϵͳʱ��
	private  void getSysTime(){
		//��ȡ��ǰϵͳʱ��
		Map map=new HashMap();
		map.put(APIModel.CODE, APIModel.CQ_GETSYSTIME);
		Bundle time_data=caiQuanDao.getSysTime(CommonUtil.setParams(map, CommonUtil.DIRECT_LIST));
		if(time_data.getBoolean("flag")){
			sysTime=time_data.getLong("sys_time");
		}
	}
	//��ʼ����������
	private  void initPopWin(){
		promptView =getActivity().getLayoutInflater().inflate(R.layout.caiquan_game_prompt, null);
		// UI
		tvw_mat_name= (TextView) promptView.findViewById(R.id.tvw_mat_name);
		tvw_comfirm = (TextView) promptView.findViewById(R.id.tvw_comfirm);
		tvw_win_camp = (TextView) promptView.findViewById(R.id.tvw_win_camp);
		tvw_lose_camp = (TextView) promptView.findViewById(R.id.tvw_lose_camp);
		ivw_win_punch = (ImageView) promptView.findViewById(R.id.ivw_win_punch);
		ivw_lose_punch = (ImageView) promptView.findViewById(R.id.ivw_lose_punch);
		tvw_win_integral = (TextView) promptView.findViewById(R.id.tvw_win_integral);
		tvw_you_result = (TextView) promptView.findViewById(R.id.tvw_you_result);
		tvw_lose_integral = (TextView) promptView.findViewById(R.id.tvw_lose_integral);
		tvw_add_integral = (TextView) promptView.findViewById(R.id.tvw_add_integral);
	}
	//��ʾ���������
	private void  showPopWin(){
		try {
			DisplayMetrics metrics=new DisplayMetrics();
			getActivity().getWindowManager().getDefaultDisplay().getMetrics(metrics);
			int width=metrics.widthPixels*9/10;
			int height=metrics.heightPixels/2;
			pww_result=new PopupWindow(promptView,width,android.view.ViewGroup.LayoutParams.WRAP_CONTENT);
			pww_result.setOutsideTouchable(false);
			pww_result.setFocusable(false);
			pww_result.setBackgroundDrawable(new  ColorDrawable(-0000));
			pww_result.setAnimationStyle(R.style.popupWindowAnimation);
			pww_result.showAtLocation(this.getView(), Gravity.CENTER, 0, 0);
			// ������ٵ���
			tvw_comfirm.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					pww_result.dismiss();
				}
			});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	//����
	/**
	 * ͼ��ת��
	 * @param view  ����
	 */
	private void punchMidTurn(final View view) {
		view.setTag(true);
		// �����Զ������ ����
		ViewTreeObserver observer = view.getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			private Boolean isMeasured = true;

			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				if (isMeasured) {
					float cX = view.getMeasuredWidth() / 2.0f;
					float cY = view.getMeasuredHeight() / 2.0f;
					if (cX > 0f) {
						setPunchMidTurnAnima(view, cX, cY);
						isMeasured = false;
					}
				}
			}
		});
	}
	/**
	 * ͼ��ת������
	 * @param view ����
	 * @param cX �����2
	 * @param cY ����ߣ�2
	 */
	private void setPunchMidTurnAnima(final View view, float cX, float cY) {
		RotateAnimation rotateAnim = new RotateAnimation(cX, cY,RotateAnimation.ROTATE_DECREASE);
		rotateAnim.setInterpolatedTimeListener(this);
		rotateAnim.setFillAfter(true);
		// ��ʼ�� �Զ��� ���� ����ѭ������
		rotateAnim.setAnimationListener(new AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) {
			}

			@Override
			public void onAnimationEnd(Animation animation) {
				boolean flag=(Boolean) view.getTag();
				if(flag){
					view.startAnimation(animation);
				}
			}
			@Override
			public void onAnimationRepeat(Animation animation) {
			}
		});
		view.startAnimation(rotateAnim);
	}
	/**
	 * עˮ���� ʵ��
	 * @param view ����
	 */
	private void punchWaterAnima(final View view,final float rate){
		ViewTreeObserver observer=view.getViewTreeObserver();
		observer.addOnGlobalLayoutListener(new OnGlobalLayoutListener() {
			private Boolean isMeasured = true;
			@Override
			public void onGlobalLayout() {
				// TODO Auto-generated method stub
				if (isMeasured) {
					float cX = view.getMeasuredWidth();
					float cY = view.getMeasuredHeight();
					if (cX > 0f) {
						float yValue=cY*rate;
						setPunchWaterAnima(view,cY,yValue);
						isMeasured = false;
					}
				}
			}
		});
	}
	/**
	 * ˮλ�ƶ�����
	 * @param view �ƶ�����
	 * @param cY  ��Բ�߶�
	 * @param yValue �ƶ��ٷֱ�
	 */
	private void setPunchWaterAnima(final View view,float cY,float yValue){
		view.setTag(true);
		ObjectAnimator objAniY=ObjectAnimator.ofFloat(view, "translationY", cY,cY-yValue);
		objAniY.setDuration(4000);
		final ObjectAnimator objAniXR=ObjectAnimator.ofFloat(view, "translationX", -10F, 10f);
		final ObjectAnimator objAniXL=ObjectAnimator.ofFloat(view, "translationX", 10f, -10f);
		objAniXR.setDuration(300);
		objAniXL.setDuration(300);
		objAniXR.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				boolean isRLAniflag=(Boolean) view.getTag();
				if(isRLAniflag){
					objAniXL.start();
				}
				super.onAnimationEnd(animation);
			}
		});
		objAniXL.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				boolean isRLAniflag=(Boolean) view.getTag();
				if(isRLAniflag){
					objAniXR.start();
				}
				super.onAnimationEnd(animation);
			}
		});
		objAniY.addListener(new AnimatorListenerAdapter() {
			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				Handler handler =new Handler();
				handler.postDelayed(new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						view.setTag(false);
					}
				},2000);
				super.onAnimationEnd(animation);
			}
		});
		objAniY.start();
		objAniXR.start();
	}
	/**
	 * ��Ӫ ��������
	 */
	private void shakeCamp(){
		ivw_camp_a.setImageResource(R.drawable.camp_a_anima_list);
		AnimationDrawable objA=(AnimationDrawable) ivw_camp_a.getDrawable();
		ivw_camp_b.setImageResource(R.drawable.camp_b_anima_list);
		AnimationDrawable objB=(AnimationDrawable) ivw_camp_b.getDrawable();
		objA.start();
		objB.start();
		Timer timer=new Timer();
		TimerTask task=new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				isSelectCamp=true;
			}
		};
		timer.schedule(task, 2050);
	}
	/**
	 * ������ʾ��ȭ
	 * @param view
	 */
	private void shakePun(){
		final shakeAnimation shakeAnimation=new shakeAnimation(getActivity().getWindow());
		shakeAnimation.shakeAnimation(ivw_jian_mid);
		shakeAnimation.shakeAnimation(ivw_shi_mid);
		shakeAnimation.shakeAnimation(ivw_bu_mid);
		Timer timer=new Timer();
		TimerTask task=new TimerTask() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				shakeAnimation.stopAnimation();
			}
		};
		timer.schedule(task, 3000);
	}

	@Override
	public void interpolatedTime(float interpolatedTime) {
		// TODO Auto-generated method stub
		
	}
}
