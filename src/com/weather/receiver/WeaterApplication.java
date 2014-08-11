package com.weather.receiver;

import java.util.ArrayList;

import android.app.Application;

import com.weather.model.Weater;

public class WeaterApplication extends Application {
	
	
	
	
	private ArrayList<Weater> weaters;
	
	public ArrayList<Weater> getWeaters() {
		return weaters;
	}

	public void setWeaters(ArrayList<Weater> weaters) {
		this.weaters = weaters;
	}
	
	
}
