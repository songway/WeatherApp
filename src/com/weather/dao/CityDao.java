package com.weather.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.weather.model.City;
import com.weather.model.Province;

public class CityDao {
	private DBOpenHelper dbOpenHelper;

	public CityDao(Context context) {
		this.dbOpenHelper = new DBOpenHelper(context);
	}
	public void save(City c){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put("id", c.getId());
		values.put("name",c.getName());
		values.put("pro_id", c.getPro_id());
		db.insert("city", null, values);
		
	}
	
	/**
	 * @param str
	 * @return ≤È—Ø∆•≈‰≥« –
	 */
	public ArrayList<City> querySupport(String str){
		ArrayList<City> cities=null;
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		Cursor cursor = db.query("city", null,"name like ?", new String[]{str+"%"}, null, null, null);
		if(cursor==null ||cursor.getCount()<=0){
			return null;
		}
		return toCity(cursor);
	}
	
	
	private ArrayList<City> toCity(Cursor cursor) {
		ArrayList<City> cities;
		cities = new ArrayList<City>();
		while(cursor.moveToNext()){
			City city = new City();
			String id=cursor.getString(cursor.getColumnIndex("id"));
			String name=cursor.getString(cursor.getColumnIndex("name"));
			String pro_id=cursor.getString(cursor.getColumnIndex("pro_id"));
			city.setId(id);
			city.setName(name);
			city.setPro_id(pro_id);
			cities.add(city);
		}
		return cities;
	}
	
	public City queryByName(String name){
		City c=null;
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("city", null,"name=?", new String[]{name}, null, null, null);
		if(cursor!=null && cursor.getCount()>0){
			cursor.moveToNext();
			c=new City();
			String id=cursor.getString(cursor.getColumnIndex("id"));
			name=cursor.getString(cursor.getColumnIndex("name"));
			String pro_id=cursor.getString(cursor.getColumnIndex("pro_id"));
			c.setId(id);
			c.setName(name);
			c.setPro_id(pro_id);
		}
		return c;
	}
	public ArrayList<City> query(String pro_id){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("city", null,"pro_id=?", new String[]{pro_id}, null, null, null);
		
		if(cursor==null ||cursor.getCount()<=0){
			return null;
		}
		
		ArrayList<City> cities = new ArrayList<City>();
		while(cursor.moveToNext()){
			City city = new City();
			String id=cursor.getString(cursor.getColumnIndex("id"));
			String name=cursor.getString(cursor.getColumnIndex("name"));
			city.setId(id);
			city.setName(name);
			city.setPro_id(pro_id);
			cities.add(city);
		}
		return cities;
	}
	public ArrayList<City> queryAll(){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("city", null,null,null, null, null, null);
		
		if(cursor==null ||cursor.getCount()<=0){
			return null;
		}
		
		
		return toCity( cursor);
	}
	
//	public ArrayList<City> queryByPage(){
//		
//	}
	
}
