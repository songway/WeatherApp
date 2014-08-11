package com.weather.utils;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import com.weather.view.SampleListFragment;

import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.provider.Settings;
import android.util.Log;
import android.widget.Toast;

public class LocationUtils {
	public static final int MESSAGE_WHAT_GETCITY_ERROR=0;
	public static final int MESSAGE_WHAT_GETCITY_NORMAL=1;
	public static final int MESSAGE_WHAT_OPENGPS_ERROR=2;
	public static final int MESSAGE_WHAT_OPENGPS_NORMAL=3;
	
	
	
	//检测是否打开GPS设置
	public static void openGPSSettings(Context context) {  
        LocationManager alm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);  
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {  
            Toast.makeText(context, "GPS模块正常", Toast.LENGTH_SHORT).show();  
            //发送广播
            Intent intent =new Intent(GlobalConstants.CHECK_GPS);
            context.sendBroadcast(intent);
//            getCityLocation(context,handler);
//            Message.obtain(handler, MESSAGE_WHAT_OPENGPS_NORMAL).sendToTarget();
            return;  
        }  
        Toast.makeText(context, "请开启GPS！", Toast.LENGTH_SHORT).show();  
//        Message.obtain(handler, MESSAGE_WHAT_OPENGPS_ERROR).sendToTarget();
        
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);  
        context.startActivity(intent);
//        context.startActivityForResult(intent,0); //此为设置完成后返回到获取界面  
  
    } 
	
	//通过GPS获取经纬度
	public static void getCityLocation(final Context context) {
		// TODO Auto-generated method stub
		final LocationManager locaManager =
				(LocationManager)context
				.getSystemService(Context.LOCATION_SERVICE);
		String GPSProvider = LocationManager.GPS_PROVIDER;
		Location location = locaManager.getLastKnownLocation(GPSProvider);
		if(location!=null){
		getCityByGPS(location,context);
		}
		//设置及时更新监听
		locaManager.requestLocationUpdates(GPSProvider, 3000, 10, new LocationListener() {
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			}
			public void onProviderDisabled(String arg0) {
			}
			
			public void onProviderEnabled(String provider) {
				Location location=locaManager.getLastKnownLocation(provider);
				getCityByGPS(location,context);
			}
			public void onLocationChanged(Location location) {
				getCityByGPS(location,context);
			}
		});
		
	}
	//获取城市名称
	public static void getCityByGPS(Location location,Context context) {
		if(location!=null){
		double longitude =location.getLongitude();
        double latitude =location.getLatitude();
        Log.i("info", "longitude:"+longitude+",latitude:"+latitude);
        LocationUtils.getAddressBylonlat(longitude, latitude,context);
		}
		else{
			Toast.makeText(context, "定位出错", Toast.LENGTH_SHORT).show();
			Intent intent = new Intent(GlobalConstants.LOCATE_CITY);
			intent.putExtra("locate_state", "ERROR");
			context.sendBroadcast(intent);
		}
	}
	
	//过滤城市名
	public static String filterCityName(String cityName) {
		if(cityName.indexOf("市")>0){
			cityName=cityName.substring(0,cityName.indexOf("市") );
		}
		if(cityName.indexOf("省")>0){
			cityName=cityName.substring(0,cityName.indexOf("省") );
		}
		return cityName;
	}
	
	//根据经纬度发送请求获取地址
	public static String getAddressBylonlat(final double longitude,final double latitude,final Context context){
		new Thread(){
			public void run() {
				try {
					
					URI uri =
							new URI("http://maps.google.com/maps/api/geocode/json?latlng="+latitude+","+longitude+"&language=zh-CN&sensor=true");
					Log.i("info", "uri"+uri.toString());
					//定义一个HttpClient，指定地址发送请求
					HttpClient client =new DefaultHttpClient();
					
					
					HttpGet httpget =new HttpGet(uri);
					
					//执行请求
					HttpResponse response =client.execute(httpget);
					HttpEntity entity = response.getEntity();
					
					//获取服务器响应的字符串
					InputStreamReader stream =new InputStreamReader(entity.getContent(), "utf-8");
					int b;
					StringBuffer sb =new StringBuffer();
					while((b=stream.read())!=-1){
						sb.append((char)b);
					}
					Log.i("info", "sb:"+sb.toString());
					//把服务器字符串转为JSONobject
					JSONObject jsonobj =new JSONObject(sb.toString());
					if(jsonobj!=null){
						//解析响应结果的地址
						String address= jsonobj.getJSONArray("results").getJSONObject(0).getString("formatted_address");
						Log.i("info", "address:"+address);
						JSONObject jo = jsonobj.getJSONArray("results").getJSONObject(0);
//						String provinces=jo.getJSONArray("address_components").getJSONObject(3).getString("long_name");
//						Log.i("info", "provinces"+jo.getJSONArray("address_components").getJSONObject(3).getString("short_name"));
						
						JSONArray arr = jo.getJSONArray("address_components");
						for(int i=0;i<arr.length();i++){
							String types = arr.getJSONObject(i).getJSONArray("types").getString(0);
							Log.i("info", "types: "+types);
							if("locality".equals(types)){
								String provinces =arr.getJSONObject(i).getString("short_name");
//								Message.obtain(handler, MESSAGE_WHAT_GETCITY_NORMAL, provinces).sendToTarget();
								Intent intent = new Intent(GlobalConstants.LOCATE_CITY);
								intent.putExtra("locate_state", provinces);
								context.sendBroadcast(intent);
							}
						}
						
						
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
//					Message.obtain(handler, MESSAGE_WHAT_GETCITY_ERROR).sendToTarget();;
					Intent intent = new Intent(GlobalConstants.LOCATE_CITY);
					intent.putExtra("locate_state", "ERROR");
					context.sendBroadcast(intent);
				}
				
			};
		}.start();
		
		return null;
	}
	
	
	/* 
     * 对json字符串进行过滤,防止乱码和黑框 
     */  
    public static String JsonFilter(String jsonstr){  
        return jsonstr.substring(jsonstr.indexOf("{")).replace("\r\n","\n");   
    }  
    
    
    
    
    /**
     * ================================================
     * Handler 处理
     * ================================================
     * */
    
	//检测是否打开GPS设置
	public static void openGPSSettings(Context context,Handler handler) {  
        LocationManager alm = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);  
        if (alm.isProviderEnabled(android.location.LocationManager.GPS_PROVIDER)) {  
            Toast.makeText(context, "GPS模块正常", Toast.LENGTH_SHORT).show();  
//            getCityLocation(context,handler);
            Message.obtain(handler, MESSAGE_WHAT_OPENGPS_NORMAL).sendToTarget();
            return;  
        }  
        Toast.makeText(context, "请开启GPS！", Toast.LENGTH_SHORT).show();  
        Message.obtain(handler, MESSAGE_WHAT_OPENGPS_ERROR).sendToTarget();
        
        Intent intent = new Intent(Settings.ACTION_SECURITY_SETTINGS);  
//        context.startActivityForResult(intent,0); //此为设置完成后返回到获取界面  
  
    } 
	
	//通过GPS获取经纬度
	public static void getCityLocation(final Context context,final Handler handler) {
		// TODO Auto-generated method stub
		final LocationManager locaManager =
				(LocationManager)context
				.getSystemService(Context.LOCATION_SERVICE);
		String GPSProvider = LocationManager.GPS_PROVIDER;
		Location location = locaManager.getLastKnownLocation(GPSProvider);
		if(location!=null){
		getCityByGPS(location,context,handler);
		}
		//设置及时更新监听
		locaManager.requestLocationUpdates(GPSProvider, 3000, 10, new LocationListener() {
			public void onStatusChanged(String arg0, int arg1, Bundle arg2) {
			}
			public void onProviderDisabled(String arg0) {
			}
			
			public void onProviderEnabled(String provider) {
				Location location=locaManager.getLastKnownLocation(provider);
				getCityByGPS(location,context,handler);
			}
			public void onLocationChanged(Location location) {
				getCityByGPS(location,context,handler);
			}
		});
		
	}
	//获取城市名称
	public static void getCityByGPS(Location location,Context context,Handler handler) {
		if(location!=null){
		double longitude =location.getLongitude();
        double latitude =location.getLatitude();
        Log.i("info", "longitude:"+longitude+",latitude:"+latitude);
        LocationUtils.getAddressBylonlat(longitude, latitude, handler);
		}
		else{
			Toast.makeText(context, "定位出错", Toast.LENGTH_SHORT).show();
		}
	}
	
	
	//根据经纬度发送请求获取地址
	public static String getAddressBylonlat(final double longitude,final double latitude,final Handler handler){
		new Thread(){
			public void run() {
				try {
					
					URI uri =
							new URI("http://maps.google.com/maps/api/geocode/json?latlng="+latitude+","+longitude+"&language=zh-CN&sensor=true");
					Log.i("info", "uri"+uri.toString());
					//定义一个HttpClient，指定地址发送请求
					HttpClient client =new DefaultHttpClient();
					
					
					HttpGet httpget =new HttpGet(uri);
					
					//执行请求
					HttpResponse response =client.execute(httpget);
					HttpEntity entity = response.getEntity();
					
					//获取服务器响应的字符串
					InputStreamReader stream =new InputStreamReader(entity.getContent(), "utf-8");
					int b;
					StringBuffer sb =new StringBuffer();
					while((b=stream.read())!=-1){
						sb.append((char)b);
					}
					Log.i("info", "sb:"+sb.toString());
					//把服务器字符串转为JSONobject
					JSONObject jsonobj =new JSONObject(sb.toString());
					if(jsonobj!=null){
						//解析响应结果的地址
						String address= jsonobj.getJSONArray("results").getJSONObject(0).getString("formatted_address");
						Log.i("info", "address:"+address);
						JSONObject jo = jsonobj.getJSONArray("results").getJSONObject(0);
//						String provinces=jo.getJSONArray("address_components").getJSONObject(3).getString("long_name");
//						Log.i("info", "provinces"+jo.getJSONArray("address_components").getJSONObject(3).getString("short_name"));
						
						JSONArray arr = jo.getJSONArray("address_components");
						for(int i=0;i<arr.length();i++){
							String types = arr.getJSONObject(i).getJSONArray("types").getString(0);
							Log.i("info", "types: "+types);
							if("locality".equals(types)){
								String provinces =arr.getJSONObject(i).getString("short_name");
								Message.obtain(handler, MESSAGE_WHAT_GETCITY_NORMAL, provinces).sendToTarget();
							}
						}
						
						
					}
					
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					Message.obtain(handler, MESSAGE_WHAT_GETCITY_ERROR).sendToTarget();;
				}
				
			};
		}.start();
		
		return null;
	}
    
}
