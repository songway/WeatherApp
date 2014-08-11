package com.weather.model;

public class City {
	private String id;
	private String name;
	private String pro_id;
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
	public String getPro_id() {
		return pro_id;
	}
	public void setPro_id(String pro_id) {
		this.pro_id = pro_id;
	}
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
}
