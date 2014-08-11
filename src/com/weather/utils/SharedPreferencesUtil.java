package com.weather.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

public class SharedPreferencesUtil {
		public static final int INITDATABASE_STATE_FINISH=2;
		private Context context;
		public SharedPreferencesUtil(Context context) {
			this.context =context;
		}
		private SharedPreferences sp;
	private SharedPreferences getSP(){
			return sp=context.getSharedPreferences("weaterproperties",Context.MODE_PRIVATE);
	}
	
	
	/**
	 * @param id ����id
	 * ���õ�ǰ����
	 */
	public void setCurrCity(String id){
		Editor editor = getSP().edit();
		editor.putString("cityId", id);
		editor.commit();
	}
	
	/**
	 * @return ��ǰ����id
	 */
	public String getCurrCity(){
		
		return getSP().getString("cityId", "-1");
	}
	
	
	
	
	
	
	
	
	
	
	public void setInitState(){
		Editor edit = getSP().edit();
		edit.putInt("database", 1);
		edit.commit();
	}
	public int getInitState(){
		
		return getSP().getInt("database", -1);
	}
	
	public void InitFinish(){
		Editor edit = getSP().edit();
		edit.putInt("database",INITDATABASE_STATE_FINISH);
		edit.commit();
	}
}
