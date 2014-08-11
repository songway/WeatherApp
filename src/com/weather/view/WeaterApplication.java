package com.weather.view;

import java.util.ArrayList;

import android.app.Application;

import com.weather.model.City;
import com.weather.model.Province;
import com.weather.model.Weater;

public class WeaterApplication extends Application {
	
	
	
	
	private ArrayList<Weater> weaters;
	private ArrayList<City> cities;
	private City curCity;
	private ArrayList<Province> provinces;
	private boolean isUpdateWeater=false;
	
	public boolean isUpdateWeater() {
		return isUpdateWeater;
	}

	public void setUpdateWeater(boolean isUpdateWeater) {
		this.isUpdateWeater = isUpdateWeater;
	}

	public City getCurCity() {
		return curCity;
	}

	public void setCurCity(City curCity) {
		this.curCity = curCity;
	}

	public ArrayList<Province> getProvinces() {
		return provinces;
	}

	public void setProvinces(ArrayList<Province> provinces) {
		this.provinces = provinces;
	}

	public ArrayList<City> getCities() {
		return cities;
	}

	public void setCities(ArrayList<City> cities) {
		this.cities = cities;
	}

	

	public ArrayList<Weater> getWeaters() {
		if(weaters ==null){
			weaters=new ArrayList<Weater>();
		}
		return weaters;
	}

	public void setWeaters(ArrayList<Weater> weaters) {
		this.weaters = weaters;
	}
	
	
}
