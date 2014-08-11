package com.weather.model;

import java.util.ArrayList;

public class Province {
	private String id;
	private String name;
	private ArrayList<City> cities;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public ArrayList<City> getCities() {
		return cities;
	}
	public void setCities(ArrayList<City> cities) {
		this.cities = cities;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
	
}
