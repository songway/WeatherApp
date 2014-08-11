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
    // 定义Web Service的命名空间  
    static final String SERVICE_NS = "http://WebXml.com.cn/";  
    // 定义Web Service提供服务的URL  
    static final String SERVICE_URL =   
        "http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx";  
  
    // 调用远程 Web Service获取省份列表  
    public static List<Province> getProvinceList()  
    {  
     
         
        String methodName = "getRegionProvince";   //获得中国省份、直辖市、地区和与之对应的ID  
        // 创建HttpTransportSE传输对象,该对象用于调用Web Service操作  
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);  
        ht.debug = true;  
        // 使用SOAP1.1协议创建Envelop对象  
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(  
            SoapEnvelope.VER11);  
        // 实例化SoapObject对象，传入所要调用的Web Service的命名空间，Web Service方法名  
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);  
        //将 soapObject对象设置为SoapSerializationEnvelope对象的传出SOAP消息  
        envelope.bodyOut = soapObject;  
        /** 
         *  因为什么这个网站是通过.NET对外提供Web Service的， 
         *  因此设置与.Net提供的Web Service保持较好的兼容性 
         */  
        envelope.dotNet = true;  
        try  
        {  
            // 调用Web Service  
            ht.call(SERVICE_NS + methodName, envelope);  
            if (envelope.getResponse() != null)  
            {  
                // 获取服务器响应返回的SOAP消息  
                SoapObject result = (SoapObject) envelope.bodyIn;  
                SoapObject detail = (SoapObject) result.getProperty(methodName  
                    + "Result");  
                // 解析服务器响应的SOAP消息。  
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
  
    // 根据省份获取城市列表  
    public static List<City> getCityListByProvince(String province)  
    {  
  
        String methodName = "getSupportCityString";    
        // 创建HttpTransportSE传输对象  
        HttpTransportSE ht = new HttpTransportSE(SERVICE_URL);  
        ht.debug = true;  
        // 实例化SoapObject对象  
        SoapObject soapObject = new SoapObject(SERVICE_NS, methodName);  
        // 添加一个请求参数  
        soapObject.addProperty("theRegionCode", province);  
        // 使用SOAP1.1协议创建Envelop对象  
        SoapSerializationEnvelope envelope = new SoapSerializationEnvelope(  
            SoapEnvelope.VER11);  
        envelope.bodyOut = soapObject;  
        // 设置与.Net提供的Web Service保持较好的兼容性  
        envelope.dotNet = true;  
        try  
        {  
            // 调用Web Service  
            ht.call(SERVICE_NS + methodName, envelope);  
            if (envelope.getResponse() != null)  
            {  
                // 获取服务器响应返回的SOAP消息  
                SoapObject result = (SoapObject) envelope.bodyIn;  
                SoapObject detail = (SoapObject) result.getProperty(methodName  
                    + "Result");  
                // 解析服务器响应的SOAP消息。  
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
           // 解析出每个省份  
           result.add(city);  
       }  
       return result;  
    }
      
    // 解析服务器响应的SOAP消息。  
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
            // 解析出每个省份  
            result.add(province);  
        }  
        return result;  
    }  
    // 根据城市获取城市具体天气情况  
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
        // 设置与.Net提供的Web Service保持较好的兼容性  
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
