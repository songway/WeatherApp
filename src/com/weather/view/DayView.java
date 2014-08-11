package com.weather.view;

import java.util.ArrayList;

import android.content.pm.ApplicationInfo;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.weather.adapter.WeaterHintAdapter;
import com.weather.model.Weater;
import com.weather.ui.utils.FlipLayout;
import com.weather.utils.BitmapUtils;
import com.weather.utils.DateUtils;
import com.weather.utils.ParseTempNumber;

public class DayView extends Fragment {
	
	private View v;
	private WeaterApplication app;
	private ArrayList<Weater> weaters;
	private Weater weater;
	private ImageView ivTemp1;
	private ImageView ivTemp2;
	private TextView tvContentWind;
	private TextView tvContentClothes;
	private TextView tvContentLife;
	private ImageView ivContentWheatherIcon;
	private FlipLayout fl;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		
	super.onCreate(savedInstanceState);
 
	}
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		v = inflater.inflate(R.layout.fragment_day, container, false);
		app = (WeaterApplication) getActivity().getApplication();
		Log.i("info", "创建");
		app.setUpdateWeater(true);
		
		return v;
	}
	public void setupView() {
		if(app==null){
			return;
		}
		weaters = app.getWeaters();
		weater = weaters.get(0);
		ivTemp1 = (ImageView) v.findViewById(R.id.iv_main_content_temp1);
		ivTemp2 = (ImageView) v.findViewById(R.id.iv_main_content_temp2);
		tvContentWind = (TextView) v.findViewById(R.id.tv_main_content_windy);

		//设置 提示指数
				ListView tv_all_zhishu =(ListView) v.findViewById(R.id.tv_all_zhishu);
				Log.i("info","weater.getWeatherHint()="+weater.getWeatherHint().size());
				WeaterHintAdapter adapter = new WeaterHintAdapter(getActivity(), weater.getWeatherHint());
				tv_all_zhishu.setAdapter(adapter);
		
				String current_sate = weater.getCurr_state();

		// 设置天气图标
			ivContentWheatherIcon = (ImageView)v.findViewById(R.id.iv_main_content_weatherIcon);
				int imgId = getImgId(weater.getWeather_pic_1());
				//System.out.println("w_"+(weater.getWeather_pic_1().substring(0,index) ));
				Bitmap bitmap = BitmapUtils.zoomImg(getResources(), imgId, 50, 50);
		
		ivContentWheatherIcon.setImageBitmap(bitmap);

		// 设置温度
		Bitmap[] arr = ParseTempNumber.getTempNumber(getResources(), current_sate);

		Log.i("info", "mainContent--温度:" + "" + current_sate);
		if (arr[1] != null && arr[0] != null) {
			// 31°
			ivTemp1.setImageBitmap(arr[0]);
			ivTemp2.setImageBitmap(arr[1]);
		} else {

			//ivTemp1.setBackground(null);

			ivTemp2.setImageBitmap(arr[0]);
		}

		// 设置风向
		String windText = parseWindText(current_sate);

		Log.i("info", "maincontent--风向:" + windText);
		tvContentWind.setText(windText);		
	
	}

	private void addListen() {
		
		
	}
	private String parseWindText(String current_sate) {

		String[] arr = current_sate.split("；");
		String temp = arr[1];
		String[] arr2 = temp.split("：");
		String result = arr2[1];

		return result;
	}
	public int getImgId(String name){
		int index = name.indexOf(".");
		name="w_"+(name.substring(0,index) );
		ApplicationInfo appInfo = getActivity().getApplicationInfo();
		//得到该图片的id(name 是该图片的名字，"drawable" 是该图片存放的目录，appInfo.packageName是应用程序的包)
		int resID = getResources().getIdentifier(name, "drawable", appInfo.packageName);
		Log.e("info", DateUtils.getCurrTime()+"//////////"+resID);
		
		return resID;
		
				
	}

}
