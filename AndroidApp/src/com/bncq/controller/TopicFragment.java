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

public class TopicFragment extends Fragment {
	//UI
	private ViewPager vp_tp_pager;//ҳ������
	private List<Fragment> listViews; // Tabҳ���б�
	private ImageView ivw_tp_cursor;// ����ͼƬ
	private TextView tvw_tp_index, tvw_tp_myTp, tvw_tp_myRy;// ҳ��ͷ��
	//����
	private List<TextView> listTextViews;//�����б�
	private int offset = 0;// ����ͼƬƫ����
	private int bmpW;// ����ͼƬ���
	@Override
	public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
		View topicLayout=inflater.inflate(R.layout.topic_layout,container,false);
		vp_tp_pager = (ViewPager) topicLayout.findViewById(R.id.vp_tp_pager);
		ivw_tp_cursor = (ImageView) topicLayout.findViewById(R.id.ivw_tp_cursor);
		/**
		 * ��ʼ��ͷ��
		 */
		tvw_tp_index = (TextView) topicLayout.findViewById(R.id.tvw_tp_index);
		tvw_tp_myTp = (TextView)topicLayout. findViewById(R.id.tvw_tp_myTp);
		tvw_tp_myRy = (TextView)topicLayout. findViewById(R.id.tvw_tp_myRy);
		listTextViews=new ArrayList<TextView>();
		listTextViews.add(tvw_tp_index);
		listTextViews.add(tvw_tp_myTp);
		listTextViews.add(tvw_tp_myRy);
		tvw_tp_index.setOnClickListener(new UIUtil.TabOnClickListener(0, vp_tp_pager,listTextViews));
		tvw_tp_myTp.setOnClickListener(new UIUtil.TabOnClickListener(1, vp_tp_pager,listTextViews));
		tvw_tp_myRy.setOnClickListener(new UIUtil.TabOnClickListener(2, vp_tp_pager,listTextViews));

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
		ivw_tp_cursor.setImageMatrix(matrix);// ���ö�����ʼλ��
		/**
		 * ��ʼ��ViewPager
		 */
		vp_tp_pager.setOffscreenPageLimit(1);
		listViews = new ArrayList<Fragment>();
		listViews.add(new TopicIndexFragment());
		listViews.add(new MyTopicFragment());
		listViews.add(new MyCommentFragment());
		vp_tp_pager.setAdapter(new TabFragmentAdapter(getChildFragmentManager(),listViews));
		vp_tp_pager.setCurrentItem(0);
		vp_tp_pager.setOnPageChangeListener(new UIUtil.TabOnPageChangeListener(offset, bmpW, ivw_tp_cursor,listTextViews));
		return topicLayout;
	}
}
 
