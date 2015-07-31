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

public class CaiquanFragment extends Fragment {
	//UI
	private ViewPager vp_cq_pager;//页卡内容
	private List<Fragment> listViews; // Tab页面列表
	private ImageView ivw_cq_cursor;// 动画图片
	private TextView tvw_cq_game, tvw_cq_rule, tvw_cq_result;// 页卡头标
	//变量
	private List<TextView> listTextViews;//标题列表
	private int offset = 0;// 动画图片偏移量
	private int bmpW;// 动画图片宽度
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View caiquanLayout=inflater.inflate(R.layout.caiquan_layout,container,false);
		vp_cq_pager = (ViewPager) caiquanLayout.findViewById(R.id.vp_cq_pager);
		ivw_cq_cursor = (ImageView) caiquanLayout.findViewById(R.id.ivw_cq_cursor);
		//变量
		/**
		 * 初始化头标
		 */
		tvw_cq_game = (TextView) caiquanLayout.findViewById(R.id.tvw_cq_game);
		tvw_cq_rule = (TextView)caiquanLayout. findViewById(R.id.tvw_cq_rule);
		tvw_cq_result = (TextView)caiquanLayout. findViewById(R.id.tvw_cq_result);
		listTextViews=new ArrayList<TextView>();
		listTextViews.add(tvw_cq_game);
		listTextViews.add(tvw_cq_rule);
		listTextViews.add(tvw_cq_result);
		tvw_cq_game.setOnClickListener(new UIUtil.TabOnClickListener(0, vp_cq_pager,listTextViews));
		tvw_cq_rule.setOnClickListener(new UIUtil.TabOnClickListener(1, vp_cq_pager,listTextViews));
		tvw_cq_result.setOnClickListener(new UIUtil.TabOnClickListener(2, vp_cq_pager,listTextViews));

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
		ivw_cq_cursor.setImageMatrix(matrix);// 设置动画初始位置
		/**
		 * 初始化ViewPager
		 */
		vp_cq_pager.setOffscreenPageLimit(1);
		listViews = new ArrayList<Fragment>();
		listViews.add(new CaiquanGameFragment());
		listViews.add(new CaiquanRuleFragment());
		listViews.add(new CaiquanResultFragment());
		vp_cq_pager.setAdapter(new TabFragmentAdapter(getChildFragmentManager(),listViews));
		vp_cq_pager.setCurrentItem(0);
		vp_cq_pager.setOnPageChangeListener(new UIUtil.TabOnPageChangeListener(offset, bmpW, ivw_cq_cursor,listTextViews));
		return caiquanLayout;
	}
}
 
