package com.weather.utils;

public class GlobalConstants {		
		//URI										
		public static final String URI_QUERY_PROVINCE="http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx/getRegionProvince?theUserID=839d2e375d154c30a15c58f24c2a3d06";
		public static final String URI_QUERY_CITY="http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx/getSupportCityDataset?theUserID=839d2e375d154c30a15c58f24c2a3d06&theRegionCode=";
	
		public static final String URI_QUERY_WEATER="http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather?theUserID=839d2e375d154c30a15c58f24c2a3d06&theCityCode=";
		public static final String URI_QUERY_WEATER_DEFAULT="http://webservice.webxml.com.cn/WebServices/WeatherWS.asmx/getWeather";
		
		
		
		//�㲥
		//����ʡ��
		public static final String UPDATE_PROVINCE="com.weater.UPDATE_PROVINCE";
		//���³��и���ʡ��ID
		public static final String UPDATE_CITIES_POR_ID="com.weater.UPDATE_CITIES_POR_ID";
		//�������ó���
		public static final String UPDATE_CITIES_ALL="com.weater.UPDATE_CITIES_ALL";
		
		//��ɸ������ݿ�
		public static final String FINISH_DB="com.weather.FINISH_DB";
		
		
		
		//��������
		public static final String UPDATE_WEATER="com.weater.UPDATE_WEATER";
		
		
		//��ѡ�����
		public static final String SELECTED_CITY="com.weater.SELECTED_CITY";
		
		//���GPS����
		public static final String CHECK_GPS="com.weater.CHECK_GPS";
		//��λ��ȡ��������
		public static final String LOCATE_CITY="com.weater.LOCATE_CITY";
		
//		//ֹͣ���沿������
//		public static final String WIDGET_STOP_SERVICE="com.weater.LOCATE_CITY";
}
