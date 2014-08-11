package com.weather.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;

public class InternetUtil {
		private static final int CMNET=2;
		private static final int CMWAP=3;
		private static final int WIFI=1;
	
	 public static int getAPNType(Context context){ 
//04-19 06:53:39.000: E/android.os.Debug(3567): failed to load memtrack module: -2

	        int netType = -1;  

	        ConnectivityManager connMgr = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE); 

	        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo(); 

	         

	        if(networkInfo==null){ 

	            return netType; 

	        } 

	        int nType = networkInfo.getType(); 

	        if(nType==ConnectivityManager.TYPE_MOBILE){ 

	            Log.i("networkInfo.getExtraInfo()", "networkInfo.getExtraInfo() is "+networkInfo.getExtraInfo()); 

	            if(networkInfo.getExtraInfo().toLowerCase().equals("cmnet")){ 

	                netType = CMNET; 

	            } 

	            else{ 

	                netType = CMWAP; 

	            } 

	        } 

	        else if(nType==ConnectivityManager.TYPE_WIFI){ 

	            netType = WIFI; 

	        } 

	        return netType; 

	    } 
}
