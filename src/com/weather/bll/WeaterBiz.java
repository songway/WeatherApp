package com.weather.bll;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;


import com.weather.model.Weater;
import com.weather.utils.GlobalConstants;
import com.weather.utils.HttpUtils;
import com.weather.view.MainActivity;
import com.weather.view.WeaterApplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class WeaterBiz {
	
	private Activity context;
	private WeaterApplication app;

	public WeaterBiz(Activity context){
		this.context=context;
		app = (WeaterApplication) context.getApplication();
	}
	public WeaterBiz(){}
	public void getWeater(){
		getWeater("2013");
	}
		
	public  void  getWeater(final String content){
			new Thread(){
				@Override
				public void run() {
					//发送请求返回 天气信息
					 InputStream is = HttpUtils.open(GlobalConstants.URI_QUERY_WEATER,content);
					 
					 //解析,封装信息
					 ArrayList<Weater> weaters = WeaterParser.parser(is);
					 // 将当前一周天气全局缓存
					 app.setWeaters(weaters);
					 Log.i("info", "等待更新天气");
					 while(!app.isUpdateWeater()){
						 try {
							 sleep(500);
						 } catch (InterruptedException e) {
							 // TODO Auto-generated catch block
							 e.printStackTrace();
						 }
					 }
					 if(weaters ==null || weaters.size()==0){
						 return;
					 }
					 // 发送广播更新天钱
					 Intent intent = new Intent(GlobalConstants.UPDATE_WEATER);
					 context.sendBroadcast(intent);
				}
			}.start();
	}
}
	