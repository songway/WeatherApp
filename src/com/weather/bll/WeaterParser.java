package com.weather.bll;



import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import com.weather.model.Weater;

import android.util.Log;

public class WeaterParser {
	

	
	public static ArrayList<Weater> parser(InputStream is)
	{
		

	
		ArrayList<Weater> weaters=null;
		try
		{
			
			XmlPullParserFactory factory=XmlPullParserFactory.newInstance();
			XmlPullParser parser=factory.newPullParser();
			parser.setInput(is, "utf-8");
			
			
			
			int eventType=parser.getEventType();
			int i=0;
			Weater weater =null;
			while(eventType!=XmlPullParser.END_DOCUMENT)
			{	
				String name = parser.getName();
				switch(eventType)
				{
				case XmlPullParser.START_DOCUMENT:
					weaters=new ArrayList<Weater>();
					break;
				case XmlPullParser.START_TAG:
					if(name.equals("ArrayOfString")){
						break;
					}
					String value=parser.nextText();
					if(i==0){
						weater=new Weater();
						weater.setProvince(value);
					}else if(i==1){
						weater.setCity(value);
						Log.i("info", value);
					}else if(i==2){
						weater.setCityId(value);
						Log.i("info", value);
					}else if(i==3){
						weater.setCurr_time(value);
					}else if(i==4){
						weater.setCurr_state(value);
						String[] string = value.split("£»");
						String[] strings =string[0].split("£º");
						weater.setCurr_temperature(strings[2]);
					}else if(i==5){
						 String[] string = value.split("£»");
						String[] strings =string[1].split("£º");
						
						weater.setAir(strings[0]);
						weater.setHumidity(strings[1]);
					}else if(i==6){
						HashMap<String, String> weatherHint = new HashMap<String ,String>();
							String[] strings = value.split("¡£");
							for(String str:strings){
								String[] hint = str.split("£º");
								weatherHint.put(hint[0], hint[1]);
								
							}
							weater.setWeatherHint(weatherHint);
					}else if(i==7){
						String[] strings = value.split(" ");
						weater.setDate(strings[0]);
						weater.setWeater(strings[1]);
					}else if(i==8){
						 weater.setTemperature(value);
						 String[] strings = value.split("/");
						 weater.setTemperature_h(strings[0]);
						 weater.setTemperature_l(strings[1]);
						 
					}else if(i==9){
						weater.setWind_direction_power(value);
					}else if(i==10){
						 weater.setWeather_pic_1(value);
					}else if(i==11){
						weater.setWeather_pic_1(value);
					}
					else{
						 if((i-11)%5==0){
							 weater=new Weater();
							 weater.setDate(value);
						 }else if((i-6)%5==1){
							 weater.setTemperature(value);
						 }else if((i-6)%5==2){
							 weater.setWind_direction_power(value);
						 }else if((i-6)%5==3){
							 weater.setWeather_pic_1(value);
						 }else if((i-6)%5==4){
							 weater.setWeather_pic_1(value);
						 }
						 
					}
					
					if(i==11 ||(i-11)%5==4){
						weaters.add(weater);
						weater=new Weater();
					}
					i++;
					break;
					
				case XmlPullParser.END_TAG:
					
					break;
					
				}
				eventType=parser.next();
			}

		}catch(Exception e)
		{
			e.printStackTrace();
		}finally
		{
			
		}
		return weaters;
	
	}

}
