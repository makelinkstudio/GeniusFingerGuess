package com.bncq.model;

import java.util.List;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

public class TabFragmentAdapter  extends FragmentStatePagerAdapter{
	  private List<Fragment> listFragments;
		
	  public TabFragmentAdapter(FragmentManager fm) {
		  super(fm);
	  }
	  public TabFragmentAdapter(FragmentManager fm,List<Fragment> fragments) {
		   super(fm);
		   listFragments=fragments;
      }
	  @Override
	  public void notifyDataSetChanged() {
	   // TODO Auto-generated method stub
	   super.notifyDataSetChanged();
	  }

	  @Override
	  public Object instantiateItem(ViewGroup arg0, int arg1) {
	   // TODO Auto-generated method stub
	   return super.instantiateItem(arg0, arg1);
	  }

	  @Override
	  public Fragment getItem(int arg0) {
	   // TODO Auto-generated method stub

	   return listFragments.get(arg0);
	  }

	  @Override
	  public int getCount() {
	   // TODO Auto-generated method stub
	   return listFragments.size();
	  }

	  @Override
	  public int getItemPosition(Object object) {
	   // TODO Auto-generated method stub
	   return POSITION_NONE;
	  }
}
