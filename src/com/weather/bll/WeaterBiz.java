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
					//�������󷵻� ������Ϣ
					 InputStream is = HttpUtils.open(GlobalConstants.URI_QUERY_WEATER,content);
					 
					 //����,��װ��Ϣ
					 ArrayList<Weater> weaters = WeaterParser.parser(is);
					 // ����ǰһ������ȫ�ֻ���
					 app.setWeaters(weaters);
					 Log.i("info", "�ȴ���������");
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
					 // ���͹㲥������Ǯ
					 Intent intent = new Intent(GlobalConstants.UPDATE_WEATER);
					 context.sendBroadcast(intent);
				}
			}.start();
	}
}
	