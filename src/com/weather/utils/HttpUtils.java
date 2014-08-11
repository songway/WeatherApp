package com.weather.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import android.util.Log;




public class HttpUtils {




	public static InputStream open(String strUrl, String id) {

		InputStream in = null;
		try {
			// 1,相关的类
			HttpClient httpclient = new DefaultHttpClient();
			if(id !=null){
				strUrl = strUrl+id;
			}
			HttpGet httpGet = new HttpGet(strUrl);
			HttpResponse httpResponse;
			
			//使用execute方法发送HTTP GET请求，并返回HttpResponse对象
			httpResponse = httpclient.execute(httpGet);
			try {
				//http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx/getRegionProvince
				
				int statusCode = httpResponse.getStatusLine().getStatusCode();
				
				if(statusCode == HttpStatus.SC_OK){
					byte[] weaterInfo=EntityUtils.toByteArray(httpResponse.getEntity());
					String string = new String(weaterInfo);
					in = new ByteArrayInputStream(weaterInfo);
				}else{
					//response = "返回码"+statusCode;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		
			return in;

}
	
	
	
	
	
	}


