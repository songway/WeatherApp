package com.weather.bll;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

import com.weather.dao.CityDao;
import com.weather.dao.ProvinceDao;
import com.weather.model.City;
import com.weather.model.Province;
import com.weather.utils.GlobalConstants;
import com.weather.utils.HttpUtils;
import com.weather.utils.SharedPreferencesUtil;
import com.weather.utils.WebServiceUtil;
import com.weather.view.WeaterApplication;

/**
 * 1.��ȡ����ʡ��
 * 2.ͨ��ʡ�ݻ�ȡ���г���
 * @author tarena
 *
 */
public class CityBiz {
		private Context context;

		private WeaterApplication app;

		public CityBiz(Activity context){
			this.context=context;
			app = (WeaterApplication) context.getApplication();
		}
		
		public ArrayList<City> getKeyCyties(String str){
			return new CityDao(context).querySupport(str);
		}
		public City getCityByName(String name){
			return new CityDao(context).queryByName(name);
		}
		/**
		 * @param cities
		 * �������ݿ�    ���� ����
		 */
		public void saveCities(ArrayList<City> cities){
			CityDao cityDao = new CityDao(context);
			if(cities !=null && cities.size()>0 ){
				for(City c :cities){
					cityDao.save(c);
				}
			}
		}
		
		
		/**
		 * @param provinces
		 * �������ݿ�    ���� ʡ��
		 * 
		 */
		public void saveProvince(ArrayList<Province> provinces){
			ProvinceDao provinceDao = new ProvinceDao(context); 
			if(provinces !=null && provinces.size()>0){
				for(Province p:provinces){
					provinceDao.save(p);
				}
			}
		}
		
		/**
		 * @return	����ʡ��
		 * ����˲�ѯ ���� ʡ��
		 */
		public void getProvinceByWeb(){
			
			new  Thread(){
			public void run() {
				List<Province> provinceList = WebServiceUtil.getProvinceList();
				
				Intent intent = new Intent(GlobalConstants.UPDATE_PROVINCE);
				context.sendBroadcast(intent);
			}
			
			}.start();
			
		}
		
		/**
		 * @param pro_id
		 * @return 
		 * ����˲�ѯ ��ǰ ʡ�ݵ� ����
		 */
		public void getCityByWeb(final String pro_id){
			new Thread(){
				public void run() {
					ArrayList<City> cities=null;
					InputStream is = HttpUtils.open(GlobalConstants.URI_QUERY_CITY, pro_id);
						 cities = CityParser.cityParser(is,pro_id);
						 app.setCities(cities);
						 Intent intent = new Intent(GlobalConstants.UPDATE_CITIES_POR_ID);
						 context.sendBroadcast(intent);
						 
					
				};
				
			}.start();
		
		}
		
		
		/**
		 * @return �������г���
		 */
		public ArrayList<City> getCityAll(){
			
			CityDao cityDao = new CityDao(context);
			ArrayList<City> cityAll=cityDao.queryAll();
			
			return cityAll;
		}
		
		
		/**
		 * @param id
		 * @return �������ݿ� ���������ʡ��
		 */
		public ArrayList<Province> getProvinceByLocal(){
			ArrayList<Province> provices=null;
			ProvinceDao provinceDao = new ProvinceDao(context); 
			provices=provinceDao.queryAll();
			return provices;
		}
		
		/**
		 * @param id
		 * @return �������ݿ� ����ĵ�ǰʡ�ݵĳ���
		 */
		public ArrayList<City> getCititesByProId(String id){
			ArrayList<City> cities=null;
			CityDao cityDao = new CityDao(context);
			cities=cityDao.query(id);
			return cities;
		}



		public void setupDatabase() {
			new Thread(){
				public void run() {
					CityDao cityDao = new CityDao(context);
					ProvinceDao provinceDao = new ProvinceDao(context);
					List<Province> provinceList = WebServiceUtil.getProvinceList();
					for(Province province :provinceList){
						provinceDao.save(province);
					}
					firstGetAndSave(provinceDao,cityDao);
					//�޸������ļ�,���ݿ��ʼ�����״̬
					new SharedPreferencesUtil(context).InitFinish();
					//������ݿ�ĳ�ʼ��
					context.sendBroadcast(new Intent(GlobalConstants.FINISH_DB));
				}

			}.start();
			
		}
		
		//��һ�β�ѯ���в����浽���ݿ�
		private void firstGetAndSave(ProvinceDao provinceDao ,CityDao cityDao) {
			ArrayList<Province> provinces = provinceDao.queryAll();
			for(Province province:provinces){
				List<City> cityListByProvince = WebServiceUtil.getCityListByProvince(province.getName());
				for(City c : cityListByProvince){
					c.setPro_id(province.getId());
					cityDao.save(c);
				}
			}
		};
}
