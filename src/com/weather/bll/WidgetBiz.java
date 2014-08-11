package com.weather.bll;

import com.weather.utils.DateUtils;
import com.weather.view.R;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.RemoteViews;

public class WidgetBiz extends AppWidgetProvider{
	
	@Override
	public void onReceive(Context context, Intent intent) {
		// TODO Auto-generated method stub
		super.onReceive(context, intent);
	}

	@Override
	public void onDisabled(Context context) {
		// TODO Auto-generated method stub
		super.onDisabled(context);
	}

	@Override
	public void onEnabled(Context context) {
		// TODO Auto-generated method stub
		super.onEnabled(context);
	}

	@Override
	public void onUpdate(Context context, AppWidgetManager appWidgetManager,
			int[] appWidgetIds) {
		  
		        Log.e("appwidget", "--update--");  
		        // ����RemoteViews����  
		        RemoteViews views = new RemoteViews(context.getPackageName(),  
		                R.layout.widget);  
		       
		        views.setTextViewText(R.id.tv_widget_time,  
		                DateUtils.getCurrTime());  
		  //      appWidgetManager.notify();
		        // ��ˢ��UI��service�ı�Ҫ���������úã��˴�û��ʹ��Bundle�������ݣ�  
		        UpdateWidgetService.appWidgetManager = appWidgetManager;  
		        UpdateWidgetService.context = context;  
		        UpdateWidgetService.remoteViews = views;  
		        // ����ˢ��UI��Service  
		        Intent intent = new Intent(context, UpdateWidgetService.class);  
		        context.startService(intent);  
		super.onUpdate(context, appWidgetManager, appWidgetIds);
	}
		// ��С�ؼ���ɾ��ʱ���ø÷���ֹͣService  
		@Override  
		public void onDeleted(Context context, int[] appWidgetIds) {  
		    super.onDeleted(context, appWidgetIds);  
		    //log.i("appwidget", "--deleted--");  
		    Intent intent = new Intent(context, UpdateWidgetService.class);  
		    context.stopService(intent);  
		}  
}
