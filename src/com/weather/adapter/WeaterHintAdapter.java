package com.weather.adapter;

import java.util.HashMap;
import java.util.Set;

import com.weather.view.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class WeaterHintAdapter extends BaseAdapter{
	private HashMap<String ,String> hints;
	private Context context;
	String[] hintsName;
	public WeaterHintAdapter(Context context,HashMap hints){
		this.context=context;
		this.hints=hints;
		setHintName();
	}
	private void setHintName() {
		Set<String> keySet = hints.keySet();
		hintsName=new String[keySet.size()]; 
		int i=0;
		for(String hintName:keySet){
			hintsName[i]=hintName;
			i++;
		}
		
	}
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return hints.size();
	}

	@Override
	public String getItem(int position) {
		// TODO Auto-generated method stub
		return hintsName[position];
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View v, ViewGroup arg2) {
			LayoutInflater inflater = LayoutInflater.from(context);
			v= inflater.inflate(R.layout.cur_state_zhishu_item, null, false);
	TextView tv_hintname=(TextView)v.findViewById(R.id.tv_hintname);
	TextView tv_hintContent=(TextView)v.findViewById(R.id.tv_hintContent);
	tv_hintname.setText(hintsName[position]+" :");
	tv_hintContent.setText(hints.get(hintsName[position]));
		return v;
	}
	
	
	

}
