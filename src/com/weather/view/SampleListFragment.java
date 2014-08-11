package com.weather.view;

import java.util.ArrayList;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ListFragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.weather.bll.CityBiz;
import com.weather.bll.WeaterBiz;
import com.weather.model.City;
import com.weather.model.Province;
import com.weather.utils.GlobalConstants;
import com.weather.utils.LocationUtils;
import com.weather.utils.SharedPreferencesUtil;

public class SampleListFragment extends ListFragment {

	private String[] pExample;
	private ArrayList<Province> provinces;
	private ArrayList<City> cities;
	private ArrayList<City> keyCities;
	private ArrayAdapter<Province> adapter;
	private ArrayAdapter<City> adapter2;
	private CityBiz cbiz;
//	private AutoCompleteTextView actv;
	private EditText et;
	private Button btnSearch;
	private Button btnBack;
	private int clickType;
	private WeaterApplication app;
	private ArrayList<City> allCities;
	private ArrayAdapter<City> autoAdapter;
	private BaseActivity ba;
	private Button btnLocation;
	private LocationManager locManager;
	private Handler handler;
	private LocationBroadReceiver receiver;
	private ArrayList<City> regionCities;
	private static final int SELCET_PROVINCE=0;
	private static final int SELCET_CITY=1;
	private static final int SELECT_KEY=3;

	public ArrayAdapter<Province> getAdapter() {
		return adapter;
	}
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		clickType=0;
		View view = inflater.inflate(R.layout.list, null);
//		actv =(AutoCompleteTextView)view.findViewById(R.id.auto_search_city);
		et = (EditText)view.findViewById(R.id.et_search_city);
		btnSearch = (Button)view.findViewById(R.id.btn_search);
		btnBack = (Button)view.findViewById(R.id.btn_back);
		btnLocation=(Button)view.findViewById(R.id.btn_location);
		return view;
	}

	public void onActivityCreated(Bundle savedInstanceState) {
		handler = new Handler(){
			@Override
			public void handleMessage(Message msg) {
				switch (msg.what) {
				case LocationUtils.MESSAGE_WHAT_GETCITY_NORMAL:
					String cityName = (String) msg.obj;
					if(cityName!=null){
						Toast.makeText(getActivity(), "自动定位到:"+cityName, Toast.LENGTH_SHORT).show();
						cityName = LocationUtils.filterCityName(cityName);
						City c =getCityByName(cityName.trim());
						if(c!=null){
						sendSelectedCityBroadCast(c);
						}
					}else{
						Toast.makeText(getActivity(), "定位失败", Toast.LENGTH_SHORT).show();
					}
					break;
				case LocationUtils.MESSAGE_WHAT_GETCITY_ERROR:
					Toast.makeText(getActivity(), "定位失败", Toast.LENGTH_SHORT).show();
					break;
				case LocationUtils.MESSAGE_WHAT_OPENGPS_NORMAL:
					//GPS设置正常开启后,后去经纬度发送请求获取城市
					LocationUtils.getCityLocation(getActivity(), handler);
					break;
				case LocationUtils.MESSAGE_WHAT_OPENGPS_ERROR:
					
					break;
				}
			}

		
		};

		receiver = new LocationBroadReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(GlobalConstants.UPDATE_PROVINCE);
		filter.addAction(GlobalConstants.CHECK_GPS);
		filter.addAction(GlobalConstants.LOCATE_CITY);
		getActivity().registerReceiver(receiver, filter);
		super.onActivityCreated(savedInstanceState);
		
		setData();
		addListener();
		
		
		
		adapter =new ArrayAdapter<Province>(getActivity(),android.R.layout.simple_list_item_1,provinces);
		regionCities = new ArrayList<City>();
		
		setListAdapter(adapter);
	}

	private void setData() {
		ba = (BaseActivity) getActivity();
		cbiz= new CityBiz(getActivity());
		app = (com.weather.view.WeaterApplication)getActivity().getApplication();
		
		provinces=ba.getProvinces();
//		provinces=cbiz.getProvinceByLocal();
		if(provinces==null){
			provinces= new ArrayList<Province>();
		}
		Log.i("info", "provinces"+provinces.toString());
		
		
	}
	
	//更新list数据
	public void updateList(ArrayList<Province> provinces){
		if(provinces!=null)this.provinces=provinces;
		adapter =new ArrayAdapter<Province>(getActivity(),android.R.layout.simple_list_item_1,provinces);
		setListAdapter(adapter);
	}
	
	
//	//自动完成文本框初始化数据
//	public  void setAutoAllCities(){
//		this.allCities=ba.getAllCities();
//		if(allCities==null)allCities=cbiz.getCityAll();
////		Log.i("info", "allCitites"+this.allCities.toString());
//		if(allCities!=null){
//		autoAdapter = new ArrayAdapter<City>(getActivity(), android.R.layout.simple_list_item_1,allCities);
//		//设置自动完成文本框
//		actv.setAdapter(autoAdapter);
//		}
//	}

	private void addListener() {
		et.addTextChangedListener(new TextWatcher() {
			

			@Override
			public void onTextChanged(CharSequence arg0, int arg1, int arg2, int arg3) {
			}
			public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
					int arg3) {
			}
			public void afterTextChanged(Editable arg0) {
				String keyword = et.getText().toString().trim();
				Log.i("info",keyword);
				if(keyword!=null){
					//通过关键字获取城市列表
					keyCities =cbiz.getKeyCyties(keyword);
					if(keyCities!=null){
					Log.i("info","keyCities"+keyCities.toString());
					adapter2 =new ArrayAdapter<City>(getActivity(),android.R.layout.simple_list_item_1,keyCities);
					setListAdapter(adapter2);
					clickType=SELECT_KEY;
					}
				}
			}
		});
		
		btnBack.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				setListAdapter(adapter);
				clickType=SELCET_PROVINCE;
			}});
		btnSearch.setOnClickListener(new OnClickListener() {
			public void onClick(View arg0) {
				String scity = et.getText().toString().trim();
				Log.i("info", "scity"+scity);
				if(scity!=null){
					City c = getCityByName(scity);
					if(c!=null){
					//发送广播更新天气
					sendSelectedCityBroadCast(c);
					}
				}
			}});
		btnLocation.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//自动定位
				AutoLocation();
			}
		});
	}
	
	//自动定位
	private void AutoLocation() {
		//检测GPS设置
		LocationUtils.openGPSSettings(getActivity(), handler);
	}
	
   //通过城市名称获取城市对象
	private City getCityByName(String scity) {
		return new CityBiz(getActivity()).getCityByName(scity);
	}


	
	@Override
	public void onListItemClick(ListView lv, View v, int position, long id) {
		switch (clickType) {
		case SELCET_PROVINCE:
			Toast.makeText(getActivity(), provinces.get(position).toString()+"省",Toast.LENGTH_LONG).show();
			cities= cbiz.getCititesByProId(provinces.get(position).getId());
			adapter2 =new ArrayAdapter<City>(getActivity(),android.R.layout.simple_list_item_1,cities);
			setListAdapter(adapter2);
			clickType=SELCET_CITY;
			break;
			
		case SELCET_CITY:
			Toast.makeText(getActivity(), cities.get(position).toString()+"市", Toast.LENGTH_SHORT).show();
			City c =cities.get(position);
		
			//发送更新天气
			sendSelectedCityBroadCast(c);
			break;
		case SELECT_KEY:
			if(keyCities!=null){
			c = keyCities.get(position);
			//发送更新天气
			sendSelectedCityBroadCast(c);
			et.setText("");
			}
			break;
		}
	}
	
	//更新天气
	public void sendSelectedCityBroadCast(City c){
		//设置全局变量的当前城市
		app.setCurCity(c);
		//slidingMenu收缩
		BaseActivity ba = (BaseActivity) getActivity();
		ba.getSlidingMenu().showContent();
		
		Log.i("info", "updateWeather"+c.getId());

		//更新天气
		WeaterBiz wbiz = new WeaterBiz(getActivity());
		wbiz.getWeater(c.getId());
		
		//设置偏好
		SharedPreferencesUtil spu = new SharedPreferencesUtil(getActivity());
		spu.setCurrCity(c.getId());
	}
	
	
/*	private class SampleItem {
		public String tag;
		public int iconRes;
		public SampleItem(String tag, int iconRes) {
			this.tag = tag; 
			this.iconRes = iconRes;
		}
	}

	public class SampleAdapter extends ArrayAdapter<SampleItem> {

		public SampleAdapter(Context context) {
			super(context, 0);
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(getContext()).inflate(R.layout.row, null);
			}
			ImageView icon = (ImageView) convertView.findViewById(R.id.row_icon);
			icon.setImageResource(getItem(position).iconRes);
			TextView title = (TextView) convertView.findViewById(R.id.row_title);
			title.setText(getItem(position).tag);

			return convertView;
		}

	}*/
	
	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		getActivity().unregisterReceiver(receiver);
		super.onDestroy();
	}
	
	public class LocationBroadReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			// TODO Auto-generated method stub
			String action = intent.getAction();
			Log.i("info", "action"+action);
			if(GlobalConstants.UPDATE_PROVINCE.equals(action)){
//				WeaterApplication app = (WeaterApplication)getActivity().getApplication();
//				provinces=app.getProvince();
//				Log.i("info", "provinces"+provinces.toString());
//				adapter =new ArrayAdapter<Province>(getActivity(),android.R.layout.simple_list_item_1,provinces);
//				setListAdapter(adapter);
				
				
			}else if(GlobalConstants.CHECK_GPS.equals(action)){
				//GPS设置正常开启后,后去经纬度发送请求获取城市
				LocationUtils.getCityLocation(context);
				
			} else if (GlobalConstants.LOCATE_CITY.equals(action)) {
				String state = intent.getStringExtra("locate_state");

				if (!"ERROR".equals(state)) {
					String cityName = state;
					if (cityName != null) {
						Toast.makeText(context, "自动定位到:" + cityName,
								Toast.LENGTH_SHORT).show();
						cityName = LocationUtils.filterCityName(cityName);
						City c = getCityByName(cityName.trim());
						if (c != null) {
							sendSelectedCityBroadCast(c);
						}
					}
				}
//				else{
//					Toast.makeText(context, "定位失败:",
//							Toast.LENGTH_SHORT).show();
//				}
			}
		}
		

	}
	
	
}
