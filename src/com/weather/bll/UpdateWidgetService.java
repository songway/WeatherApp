package com.weather.bll;

import java.util.ArrayList;

import com.weather.model.Weater;
import com.weather.utils.DateUtils;
import com.weather.utils.GlobalConstants;
import com.weather.view.R;
import com.weather.view.WeaterApplication;

import android.R.integer;
import android.app.Service;
import android.appwidget.AppWidgetManager;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.util.Log;
import android.widget.RemoteViews;

public class UpdateWidgetService extends Service{

	private ComponentName componentName;
	public static AppWidgetManager appWidgetManager;
	public static Context context;
	public static RemoteViews remoteViews;
	public Thread workThread;
	public boolean isWork;
	private WeaterApplication app;
	@Override
	public IBinder onBind(Intent intent) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public void onCreate() {
		Log.e("info", "UpdateUIService");
		app = (WeaterApplication) getApplication();
		register();
		updateWidgetWeater();
		super.onCreate();
	}
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		isWork=true;
		Log.e("info", "onStartCommand");
			workThread=new Thread(){

				@Override
				public void run() {
					while(isWork){
					try {
//						String name="ab_ic_location";
//						ApplicationInfo appInfo = getApplicationInfo();
//						//得到该图片的id(name 是该图片的名字，"drawable" 是该图片存放的目录，appInfo.packageName是应用程序的包)
//						int resID = getResources().getIdentifier(name, "drawable", appInfo.packageName);
//						Log.e("info", DateUtils.getCurrTime()+"//////////"+resID);
//						
						
						remoteViews.setTextViewText(R.id.tv_widget_time,DateUtils.getCurrTime());
						remoteViews.setTextViewText(R.id.tv_widget_time_ab,DateUtils.getAP());
						remoteViews.setTextViewText(R.id.tv_widget_month,DateUtils.getMonth());
						remoteViews.setTextViewText(R.id.tv_widget_week,DateUtils.getWeek());
						if(app!=null &&app.getWeaters()!=null &&app.getWeaters().size() >0){
							Weater weater = app.getWeaters().get(0);
							remoteViews.setTextViewText(R.id.tv_widget_curr_tem,weater.getCurr_temperature());
							remoteViews.setTextViewText(R.id.tv_widget_tem_h,weater.getTemperature_l());
							remoteViews.setTextViewText(R.id.tv_widget_tem_l,weater.getTemperature_h());
						}
						
						
						
						
						componentName = new ComponentName(context,  
				                WidgetBiz.class);  
				        // 调用AppWidgetManager将remoteViews添加到ComponentName中  
						
				        appWidgetManager.updateAppWidget(componentName, remoteViews);  
						sleep(60000);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					}
				}

			
			};
			workThread.start();
		return super.onStartCommand(intent, flags, startId);
	}
	public void quit(){
		isWork=false;
	}
	
	@Override
	public void onDestroy() {
		quit();
		stopSelf();
		super.onDestroy();
	}
	public void  register(){
		InnerReceiver receiver = new InnerReceiver();
		IntentFilter filter =new  IntentFilter();
		filter.addAction(GlobalConstants.UPDATE_WEATER);
		registerReceiver(receiver, filter);
	}
	class InnerReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context arg0, Intent intent) {
				String action = intent.getAction();
			if(action.equals(GlobalConstants.UPDATE_WEATER)){
				UpdateWidgetService.this.updateWidgetWeater();
			
			}
			
		}

	}
	private void updateWidgetWeater() {
		ArrayList<Weater> weaters = app.getWeaters();
		if(weaters==null ||weaters.size()<=0){
			return;
		}
		Weater weater = weaters.get(0);
		remoteViews.setTextViewText(R.id.tv_widget_curr_tem,weater.getCurr_temperature());
		remoteViews.setTextViewText(R.id.tv_widget_tem_h,weater.getTemperature_h());
		remoteViews.setTextViewText(R.id.tv_widget_tem_l,weater.getTemperature_l());
		componentName = new ComponentName(context,  
                WidgetBiz.class);  
        // 调用AppWidgetManager将remoteViews添加到ComponentName中  
		
        appWidgetManager.updateAppWidget(componentName, remoteViews);
	}

	
}
