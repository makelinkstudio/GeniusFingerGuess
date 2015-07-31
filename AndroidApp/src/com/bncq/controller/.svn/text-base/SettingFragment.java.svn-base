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
	private ViewPager vp_set_pager;// 页卡内容
	private List<Fragment> listViews; // Tab页面列表
	private ImageView ivw_set_cursor;// 动画图片
	private TextView tvw_set_myInfo, tvw_set_myNotice, tvw_set_more;// 页卡头标
	// 变量
	private List<TextView> listTextViews;// 标题列表
	private int offset = 0;// 动画图片偏移量
	private int bmpW;// 动画图片宽度
	
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup viewGroup,Bundle savedInstanceState) {
	    //UI
		View settingLayout= inflater.inflate(R.layout.setting_layout, viewGroup, false);
		vp_set_pager = (ViewPager) settingLayout.findViewById(R.id.vp_set_pager);
		ivw_set_cursor = (ImageView) settingLayout.findViewById(R.id.ivw_set_cursor);
		/**
		 * 初始化头标
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
		 * 初始化动画
		 */
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.tab_cursor).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenW = dm.widthPixels;// 获取分辨率宽度
		offset = (screenW / 3 - bmpW) / 2;// 计算偏移量
		Matrix matrix = new Matrix();
		matrix.postTranslate(offset, 0);
		ivw_set_cursor.setImageMatrix(matrix);// 设置动画初始位置
		/**
		 * 初始化ViewPager
		 */
		vp_set_pager.setOffscreenPageLimit(1);
		listViews = new ArrayList<Fragment>();
		listViews.add(new MyInfoFragment());//我的信息
		listViews.add(new MyNoticeFragment());//我的消息
		listViews.add(new MoreFragment());//更多
		vp_set_pager.setAdapter(new TabFragmentAdapter(getChildFragmentManager(),listViews));
		vp_set_pager.setCurrentItem(0);
		vp_set_pager.setOnPageChangeListener(new UIUtil.TabOnPageChangeListener(offset, bmpW, ivw_set_cursor,listTextViews));
		return settingLayout;
	}
}
