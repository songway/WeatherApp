package com.weather.bll;

import java.io.InputStream;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Xml;

import com.weather.model.City;
import com.weather.model.Province;

public class CityParser {
	
	public static ArrayList<Province> provinceParser(InputStream is){
		ArrayList<Province> provinces=null;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, "utf-8");
			int envtType=parser.getEventType();
			
			Province province=null;
			provinces=new ArrayList<Province>(); 
			boolean iss=false;
			while(envtType !=XmlPullParser.END_DOCUMENT){
				String name = parser.getName();
				switch (envtType) {
				case  XmlPullParser.START_DOCUMENT:
					if(iss){
						
					String value=parser.nextText();
					province=new Province();
					String[] values=parser.nextText().split(",");
					province.setId(values[1]);
					province.setName(values[0]);
					}
					iss=true;
					break;
				case  XmlPullParser.START_TAG:
				//	String value=parser.nextText();
//					province=new Province();
//					String[] value=parser.nextText().split(",");
//					province.setId(value[1]);
//					province.setName(value[0]);
					break;
				case  XmlPullParser.END_TAG:
					
					break;

				default:
					break;
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return provinces;
	}
	
	public static ArrayList<City> cityParser(InputStream is,String pro_id){
		ArrayList<City> cities=null;
		XmlPullParser parser = Xml.newPullParser();
		try {
			parser.setInput(is, "utf-8");
			int envtType=parser.getEventType();
			
			City city=null;
			while(envtType !=XmlPullParser.END_DOCUMENT){
				
				switch (envtType) {
				case  XmlPullParser.START_DOCUMENT:
					cities=new ArrayList<City>(); 
					break;
				case  XmlPullParser.START_TAG:
					city=new City();
					String[] value=parser.nextText().split(",");
					city.setId(value[1]);
					city.setName(value[0]);
					city.setPro_id(pro_id);
					break;
				case  XmlPullParser.END_TAG:
					cities.add(city);
					break;

				default:
					break;
				}
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		return cities;
	}
}
