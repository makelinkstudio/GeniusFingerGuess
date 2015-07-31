package com.bncq.controller;

import java.util.ArrayList;
import java.util.List;
import com.bncq.R;
import com.bncq.common.UIUtil;
import com.bncq.model.TabFragmentAdapter;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


public class SettingFragment extends Fragment{
	// UI
	private ViewPager vp_set_pager;// ҳ������
	private List<Fragment> listViews; // Tabҳ���б�
	private ImageView ivw_set_cursor;// ����ͼƬ
	private TextView tvw_set_myInfo, tvw_set_myNotice, tvw_set_more;// ҳ��ͷ��
	// ����
	private List<TextView> listTextViews;// �����б�
	private int offset = 0;// ����ͼƬƫ����
	private int bmpW;// ����ͼƬ���
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup,Bundle savedInstanceState) {
	    //UI
		View settingLayout= inflater.inflate(R.layout.setting_layout, viewGroup, false);
		vp_set_pager = (ViewPager) settingLayout.findViewById(R.id.vp_set_pager);
		ivw_set_cursor = (ImageView) settingLayout.findViewById(R.id.ivw_set_cursor);
		/**
		 * ��ʼ��ͷ��
		 */
		tvw_set_myInfo = (TextView) settingLayout.findViewById(R.id.tvw_set_myInfo);
		tvw_set_myNotice = (TextView)settingLayout. findViewById(R.id.tvw_set_myNotice);
		tvw_set_more = (TextView)settingLayout. findViewById(R.id.tvw_set_more);
		listTextViews=new ArrayList<TextView>();
		listTextViews.add(tvw_set_myInfo);
		listTextViews.add(tvw_set_myNotice);
		listTextViews.add(tvw_set_more);
		tvw_set_myInfo.setOnClickListener(new UIUtil.TabOnClickListener(0, vp_set_pager,listTextViews));
		tvw_set_myNotice.setOnClickListener(new UIUtil.TabOnClickListener(1, vp_set_pager,listTextViews));
		tvw_set_more.setOnClickListener(new UIUtil.TabOnClickListener(2, vp_set_pager,listTextViews));

		/**
		 * ��ʼ������
		 */
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.tab_cursor).getWidth();// ��ȡͼƬ���
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// ��ȡ�ֱ��ʿ��
		offset = (screenW / 3 - bmpW) / 2;// ����ƫ����
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		ivw_set_cursor.setImageMatrix(matrix);// ���ö�����ʼλ��
		/**
		 * ��ʼ��ViewPager
		 */
		vp_set_pager.setOffscreenPageLimit(1);
		listViews = new ArrayList<Fragment>();
		listViews.add(new MyInfoFragment());//�ҵ���Ϣ
		listViews.add(new MyNoticeFragment());//�ҵ���Ϣ
		listViews.add(new MoreFragment());//����
		vp_set_pager.setAdapter(new TabFragmentAdapter(getChildFragmentManager(),listViews));
		vp_set_pager.setCurrentItem(0);
		vp_set_pager.setOnPageChangeListener(new UIUtil.TabOnPageChangeListener(offset, bmpW, ivw_set_cursor,listTextViews));
		return settingLayout;
	}
}
