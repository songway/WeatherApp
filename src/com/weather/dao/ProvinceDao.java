package com.weather.dao;

import java.util.ArrayList;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.weather.model.Province;

public class ProvinceDao {
		
	private DBOpenHelper dbOpenHelper;

	public ProvinceDao(Context context) {
		this.dbOpenHelper = new DBOpenHelper(context);
	}
	public ArrayList<Province> queryAll(){
		SQLiteDatabase db = dbOpenHelper.getReadableDatabase();
		Cursor cursor = db.query("province", null, null, null, null, null, null);
		if(cursor==null ||cursor.getCount()<=0){
			return null;
		}
		ArrayList<Province> provinces = new ArrayList<Province>();
		while(cursor.moveToNext()){
			Province province = new Province();
			String id=cursor.getString(cursor.getColumnIndex("id"));
			String name=cursor.getString(cursor.getColumnIndex("name"));
			province.setId(id);
			province.setName(name);
			provinces.add(province);
		}
		db.close();
		return provinces;
	}
	
	public void save(Province p){
		SQLiteDatabase db = dbOpenHelper.getWritableDatabase();
		try{
			
			db.beginTransaction();
			ContentValues values = new ContentValues();
			values.put("id", p.getId());
			values.put("name",p.getName());
			db.insert("province", null, values);
			db.setTransactionSuccessful();
		}finally{
			
			db.endTransaction();
		}
		db.close();
	}
	
}
