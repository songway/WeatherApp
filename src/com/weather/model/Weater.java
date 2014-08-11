package com.weather.model;



import java.util.HashMap;

public class Weater {
	private String  province;//1.省份
	private String 	city;//2.城市
	private String cityId;//3.城市id
	private String curr_time;//4.当前时间
	private String curr_state; //5.天气状态
	private String curr_temperature;//当前温度
	private String  air; //6.空气
	private String ultraviolet ;//6.紫外线
	private HashMap<String ,String> weatherHint; //天气提示
	private String  date; //7日期
	private String  weater; //7天气
	private String temperature;//温度
	private String temperature_h;//最高温度
	private String temperature_l;//最低温度
	private String wind_direction_power;//风向/风力
	
	private String humidity;//湿度
	private	String weather_pic_1; 
	private	String weather_pic_2 ;
	
	
	
		public String getTemperature_h() {
		return temperature_h;
	}

	public void setTemperature_h(String temperature_h) {
		this.temperature_h = temperature_h;
	}

	public String getTemperature_l() {
		return temperature_l;
	}

	public void setTemperature_l(String temperature_l) {
		this.temperature_l = temperature_l;
	}

		public String getCurr_temperature() {
		return curr_temperature;
	}

	public void setCurr_temperature(String curr_temperature) {
		this.curr_temperature = curr_temperature;
	}

		public String getWeater() {
		return weater;
	}

	public void setWeater(String weater) {
		this.weater = weater;
	}

		public String getProvince() {
		return province;
	}

	public void setProvince(String province) {
		this.province = province;
	}

	public String getCityId() {
		return cityId;
	}

	public void setCityId(String cityId) {
		this.cityId = cityId;
	}

	public String getCurr_time() {
		return curr_time;
	}

	public void setCurr_time(String curr_time) {
		this.curr_time = curr_time;
	}

	public String getCurr_state() {
		return curr_state;
	}

	public void setCurr_state(String curr_state) {
		this.curr_state = curr_state;
	}

	public String getAir() {
		return air;
	}

	public void setAir(String air) {
		this.air = air;
	}

	public String getUltraviolet() {
		return ultraviolet;
	}

	public void setUltraviolet(String ultraviolet) {
		this.ultraviolet = ultraviolet;
	}

		public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}



	public String getTemperature() {
		return temperature;
	}

	public void setTemperature(String temperature) {
		this.temperature = temperature;
	}

	public String getWind_direction_power() {
		return wind_direction_power;
	}

	public void setWind_direction_power(String wind_direction_power) {
		this.wind_direction_power = wind_direction_power;
	}

	public String getHumidity() {
		return humidity;
	}

	public void setHumidity(String humidity) {
		this.humidity = humidity;
	}

	public String getWeather_pic_1() {
		return weather_pic_1;
	}

	public void setWeather_pic_1(String weather_pic_1) {
		this.weather_pic_1 = weather_pic_1;
	}

	public String getWeather_pic_2() {
		return weather_pic_2;
	}

	public void setWeather_pic_2(String weather_pic_2) {
		this.weather_pic_2 = weather_pic_2;
	}

	public HashMap<String, String> getWeatherHint() {
		return weatherHint;
	}

	public void setWeatherHint(HashMap<String, String> weatherHint) {
		this.weatherHint = weatherHint;
	}

	
}
