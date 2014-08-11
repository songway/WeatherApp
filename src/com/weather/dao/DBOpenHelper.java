package com.weather.dao;

import com.weather.utils.SharedPreferencesUtil;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DBOpenHelper extends SQLiteOpenHelper {

	private Context context;
	
	public DBOpenHelper(Context context) {
		super(context,"weater.db", null, 3);
		this.context=context;
	}


	@Override
	public void onCreate(SQLiteDatabase db) {
		Log.i("init", "初始化数据库");
		db.execSQL("CREATE TABLE  city(  id varchar(20) , name varchar(20), pro_id VARCHAR(12) )");
		db.execSQL("CREATE TABLE province(  id varchar(20) , name varchar(20))");
		new SharedPreferencesUtil(context).setInitState();
	}

	@Override
	public void onUpgrade(SQLiteDatabase arg0, int arg1, int arg2) {
		// TODO Auto-generated method stub
		
	}

}
