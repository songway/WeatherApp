package com.weather.view;

import java.util.ArrayList;

import com.weather.model.Weater;
import com.weather.utils.BitmapUtils;
import com.weather.utils.DateUtils;
import com.weather.utils.ParseTempNumber;

import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

public class WeekView extends Fragment {
	private View v;
	private WeaterApplication app;

	private ParseTempNumber parse;
	private Weater weater;
	// second.xml视图中的控件
	private ImageView ivSecWheatherIcon, ivSecMonIcon, ivSecTuesIcon,
			ivSecWndesIcon, ivSecThurIcon, ivSecFriIcon, ivSecSaturIcon,
			ivSecSunIcon;// 周一至周日天气图标

	private TextView tvSecMonTempScope, tvSecTuesTempScope,
			tvSecWndesTempScope, tvSecThurTempScope, tvSecFriTempScope,
			tvSecSaturTempScope, tvSecSunTempScope, tvSecTemp;// 周一至周日温度范围
	private ArrayList<Weater> weaters;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
	
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		app = (WeaterApplication) getActivity().getApplication();
		
		app.setUpdateWeater(true);
		v = inflater.inflate(R.layout.fragment_week,container, false);
	parse = new ParseTempNumber();
		
		ivSecWheatherIcon = (ImageView) v
				.findViewById(R.id.iv_second_view_weatherIcon);
		tvSecTemp = (TextView) v.findViewById(R.id.tv_second_view_temp);

		//
		ivSecMonIcon = (ImageView) v
				.findViewById(R.id.iv_second_view_monWheatherIcon);
		ivSecTuesIcon = (ImageView) v
				.findViewById(R.id.iv_second_view_tusWheatherIcon);
		ivSecWndesIcon = (ImageView) v
				.findViewById(R.id.iv_second_view_wednWheatherIcon);
		ivSecThurIcon = (ImageView) v
				.findViewById(R.id.iv_second_view_thursWheatherIcon);
		ivSecFriIcon = (ImageView) v
				.findViewById(R.id.iv_second_view_friWheatherIcon);
		ivSecSaturIcon = (ImageView) v
				.findViewById(R.id.iv_second_view_saturWheatherIcon);
		ivSecSunIcon = (ImageView) v
				.findViewById(R.id.iv_second_view_sunWheatherIcon);
		//
		tvSecMonTempScope = (TextView) v
				.findViewById(R.id.tv_second_view_monTempScope);
		tvSecTuesTempScope = (TextView) v
				.findViewById(R.id.tv_second_view_tusTempScope);
		tvSecWndesTempScope = (TextView) v
				.findViewById(R.id.tv_second_view_wednTempScope);
		tvSecThurTempScope = (TextView) v
				.findViewById(R.id.tv_second_view_thursTempScope);
		tvSecFriTempScope = (TextView) v
				.findViewById(R.id.tv_second_view_friTempScope);
		tvSecSaturTempScope = (TextView) v
				.findViewById(R.id.tv_second_view_saturTempScope);
		tvSecSunTempScope = (TextView) v
				.findViewById(R.id.tv_second_view_sunTempScope);
	
		return v;
	}

	public void setupView() {
		if(app ==null){
			return ;
		}
		weaters = app.getWeaters();
		weater = weaters.get(0);
		String current_sate = weater.getCurr_state();

		// 设置天气图标
		
		int resID = getImgId(weater.getWeather_pic_1());
		Bitmap bm = BitmapUtils.zoomImg(getResources(), resID, 30, 30);

		ivSecWheatherIcon.setImageBitmap(bm);
		// 设置温度
		String tempNum = parseText(current_sate);
		tvSecTemp.setText(weater.getWeater());

		// 设置显示周一到周日的图标 天气

		setWeekData();

	}

	private void setWeekData() {

		Weater w = weaters.get(0);
		String s1 = w.getTemperature();// 16℃/22℃
		int wn = DateUtils.parseWeek(w.getDate());// 获得当前时间是星期几
		
		int resID = getImgId(w.getWeather_pic_1());
		Bitmap bm = BitmapUtils.zoomImg(getResources(), resID, 30, 30);
		
		String tempScope = s1.replace("/", "~");
		if (1 == wn) {
			ivSecSunIcon.setImageBitmap(bm);
			tvSecSunTempScope.setText(tempScope);
		} else if (2 == wn) {
			ivSecMonIcon.setImageBitmap(bm);
			tvSecMonTempScope.setText(tempScope);
		} else if (3 == wn) {
			ivSecTuesIcon.setImageBitmap(bm);
			tvSecTuesTempScope.setText(tempScope);
		} else if (4 == wn) {
			ivSecWndesIcon.setImageBitmap(bm);
			tvSecWndesTempScope.setText(tempScope);
		} else if (5 == wn) {
			ivSecThurIcon.setImageBitmap(bm);
			tvSecThurTempScope.setText(tempScope);
		} else if (6 == wn) {
			ivSecFriIcon.setImageBitmap(bm);
			tvSecFriTempScope.setText(tempScope);
		} else if (7 == wn) {
			ivSecSaturIcon.setImageBitmap(bm);
			tvSecSaturTempScope.setText(tempScope);
		}

		for (int i = 1; i < weaters.size(); i++) {

			Weater weater = weaters.get(i);
			// 星期日--1, 星期一 --2,...,星期六--7;
			int weekNum = DateUtils.parseWeek(weater.getTemperature());
			
			String name=weater.getWeather_pic_1();
				 resID = getImgId(name);
			Bitmap bm1 = BitmapUtils.zoomImg(getResources(), resID, 30, 30);
			String temp = weater.getWind_direction_power();

			String tempScope1 = temp.replace("/", "~");
			switch (weekNum) {
			case 1:// 星期日

				ivSecSunIcon.setImageBitmap(bm1);
				tvSecSunTempScope.setText(tempScope1);
				break;
			case 2:// 星期一
				ivSecMonIcon.setImageBitmap(bm1);
				tvSecMonTempScope.setText(tempScope1);

				break;
			case 3:// 星期二
				ivSecTuesIcon.setImageBitmap(bm1);
				tvSecTuesTempScope.setText(tempScope1);

				break;
			case 4:// 星期三
				ivSecWndesIcon.setImageBitmap(bm1);
				tvSecWndesTempScope.setText(tempScope1);

				break;
			case 5:// 星期四
				ivSecThurIcon.setImageBitmap(bm1);
				tvSecThurTempScope.setText(tempScope1);

				break;
			case 6:// 星期五

				ivSecFriIcon.setImageBitmap(bm1);
				tvSecFriTempScope.setText(tempScope1);
				break;
			case 7:// 星期六

				ivSecSaturIcon.setImageBitmap(bm1);
				tvSecSaturTempScope.setText(tempScope1);
				break;

			}

		}

	}
	//android.support.v4.widget.SwipeRefreshLayout.startRefresh(SwipeRefreshLayout.java:441)

	public int getImgId(String name){
		int index = name.indexOf(".");
		name="w_"+(name.substring(0,index) );
		ApplicationInfo appInfo = getActivity().getApplicationInfo();
		//得到该图片的id(name 是该图片的名字，"drawable" 是该图片存放的目录，appInfo.packageName是应用程序的包)
		int resID = getResources().getIdentifier(name, "drawable", appInfo.packageName);
		Log.e("info", DateUtils.getCurrTime()+"//////////"+resID);
		
		return resID;
		
				
	}

	private String parseText(String text) {
		String s1 = text.substring(text.indexOf("：") + 1, text.indexOf("℃"));

		String resNum = s1.substring((s1.indexOf("：") + 1));

		return resNum;
	}
}