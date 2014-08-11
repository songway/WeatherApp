package com.weather.utils;

 

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;
import org.xmlpull.v1.XmlPullParserException;

import com.weather.model.City;
import com.weather.model.Province;
  
public class WebServiceUtil  
{  
    // ����Web Service�������ռ�  
    static final String SERVICE_NS = "http://WebXml.com.cn/";  
    // ����Web Service�ṩ�����URL  
    static final String SERVICE_URL =   
        "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";  
  
    // ����Զ�� Web Service��ȡʡ���б�  
    public static List<Province> getProvinceList()  
    {  
     
         
        String methodName = "getRegionProvince";   //����й�ʡ�ݡ�ֱϽ�С���������֮��Ӧ��ID  
        // ����HttpTransportSE�������,�ö������ڵ���Web Service����  
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);  
        ht.debug = true;  
        // ʹ��SOAP1.1Э�鴴��Envelop����  
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(  
            SoapEnvelope.VER11);  
        // ʵ����SoapObject���󣬴�����Ҫ���õ�Web Service�������ռ䣬Web Service������  
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);  
        //�� soapObject��������ΪSoapSerializationEnvelope����Ĵ���SOAP��Ϣ  
        envelope.bodyOut = soapObject;  
        /** 
         *  ��Ϊʲô�����վ��ͨ��.NET�����ṩWeb Service�ģ� 
         *  ���������.Net�ṩ��Web Service���ֽϺõļ����� 
         */  
        envelope.dotNet = true;  
        try  
        {  
            // ����Web Service  
            ht.call(SERVICE_NS + methodName, envelope);  
            if (envelope.getResponse() != null)  
            {  
                // ��ȡ��������Ӧ���ص�SOAP��Ϣ  
                SoapObject result = (SoapObject) envelope.bodyIn;  
                SoapObject detail = (SoapObject) result.getProperty(methodName  
                    + "Result");  
                // ������������Ӧ��SOAP��Ϣ��  
                return parseProvince(detail);  
            }  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
        catch (XmlPullParserException e)  
        {  
            e.printStackTrace();  
        }  
        return null;  
    }  
  
    // ����ʡ�ݻ�ȡ�����б�  
    public static List<City> getCityListByProvince(String province)  
    {  
  
        String methodName = "getSupportCityString";    
        // ����HttpTransportSE�������  
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);  
        ht.debug = true;  
        // ʵ����SoapObject����  
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);  
        // ���һ���������  
        soapObject.addProperty("theRegionCode", province);  
        // ʹ��SOAP1.1Э�鴴��Envelop����  
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(  
            SoapEnvelope.VER11);  
        envelope.bodyOut = soapObject;  
        // ������.Net�ṩ��Web Service���ֽϺõļ�����  
        envelope.dotNet = true;  
        try  
        {  
            // ����Web Service  
            ht.call(SERVICE_NS + methodName, envelope);  
            if (envelope.getResponse() != null)  
            {  
                // ��ȡ��������Ӧ���ص�SOAP��Ϣ  
                SoapObject result = (SoapObject) envelope.bodyIn;  
                SoapObject detail = (SoapObject) result.getProperty(methodName  
                    + "Result");  
                // ������������Ӧ��SOAP��Ϣ��  
                return parseCity(detail);  
            }  
        }  
        catch (IOException e)  
        {  
            e.printStackTrace();  
        }  
        catch (XmlPullParserException e)  
        {  
            e.printStackTrace();  
        }  
        return null;  
    }  
    
    private static ArrayList<City> parseCity(SoapObject detail){
    	
    	ArrayList<City> result = new ArrayList<City>();  
       for (int i = 0; i < detail.getPropertyCount(); i++)  
       {  
    	   City city = new City();
    	  String name= detail.getProperty(i).toString().split(",")[0];
    	  String id= detail.getProperty(i).toString().split(",")[1];
    	  city.setName(name);
    	  city.setId(id);
           // ������ÿ��ʡ��  
           result.add(city);  
       }  
       return result;  
    }
      
    // ������������Ӧ��SOAP��Ϣ��  
    private static List<Province> parseProvince(SoapObject detail)  
    {  
        List<Province> result = new ArrayList<Province>();  
        for (int i = 0; i < detail.getPropertyCount(); i++)  
        {  
        	Province province = new Province();
        	String name=detail.getProperty(i).toString().split(",")[0];
        	String id=detail.getProperty(i).toString().split(",")[1];
        	province.setName(name);
        	province.setId(id);
            // ������ÿ��ʡ��  
            result.add(province);  
        }  
        return result;  
    }  
    // ���ݳ��л�ȡ���о����������  
    public static SoapObject getWeatherByCity(String cityName)  
    {  
     
        String methodName = "getWeather";  
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);  
        ht.debug = true;  
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(  
            SoapEnvelope.VER11);  
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);  
        soapObject.addProperty("theCityCode", cityName);  
        envelope.bodyOut = soapObject;  
        // ������.Net�ṩ��Web Service���ֽϺõļ�����  
        envelope.dotNet = true;  
        try  
        {  
            ht.call(SERVICE_NS + methodName, envelope);  
            SoapObject result = (SoapObject) envelope.bodyIn;  
            SoapObject detail = (SoapObject) result.getProperty(methodName  
                + "Result");  
           
            return detail;  
        }  
        catch (Exception e)  
        {  
            e.printStackTrace();  
        }  
        return null;  
    }  
}  
