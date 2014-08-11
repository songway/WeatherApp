package com.weather.adapter;

import java.util.ArrayList;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.view.ViewGroup;

public class MainPageAdapter extends FragmentPagerAdapter{

	private ArrayList<Fragment> fragments;

	public MainPageAdapter(FragmentManager fm,ArrayList<Fragment> fragments) {
		super(fm);
		this.fragments=fragments;
		// TODO Auto-generated constructor stub
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return fragments.size();
	}

	@Override
	public Fragment getItem(int arg0) {
		// TODO Auto-generated method stub
		return fragments.get(arg0);
	}



}
