package com.weather.view;

import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.NotificationCompat.Builder;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.RemoteViews;
import android.widget.TextView;
import android.widget.Toast;

import com.weather.adapter.MainPageAdapter;
import com.weather.bll.CityBiz;
import com.weather.bll.WeaterBiz;
import com.weather.model.Weater;
import com.weather.utils.CouldyView;
import com.weather.utils.GlobalConstants;
import com.weather.utils.InternetUtil;
import com.weather.utils.LocationUtils;
import com.weather.utils.SharedPreferencesUtil;

public class MainActivity extends BaseActivity implements
SwipeRefreshLayout.OnRefreshListener  {
	private WeaterApplication app;
	private InnerReceiver receiver;//�㲥������
	private TextView cityField,currentTemp;
	private ViewPager vp_main;
	private DayView dayView;
	private WeekView weekView;
	private ArrayList<Fragment> fragments;
	private MainPageAdapter adapter;
	private CouldyView cv_couldy;
	private WeaterBiz weaterBiz;
	private NotificationManager notiManager;
	private Builder builder;
	private RemoteViews views;
	private TextView tvTitleCity;
	private TextView tvTitleDate;
	private ImageView ivTileMenuLeft, ivTitleMore;
	private SwipeRefreshLayout swipeLayout;
	private long exitTime=0;
	private PopupWindow popupWindow;
	private Animation animForward, animBackward;
	private View view;
	private ProgressDialog pd;
	private LinearLayout llset;
	private LinearLayout llexit;
	private Button btncancel;
	@Override
	public void onCreate(Bundle savedInstanceState) {
		setupWindow();
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		app=(WeaterApplication) getApplication();
		setupView();
		//ע��㲥
		register();
		weaterBiz = new WeaterBiz(this);
		//�Ƿ�������
		if(InternetUtil.getAPNType(this) ==-1){
			Toast.makeText(this, "û������,��ʱ���ܸ���", 0).show();
		}else{
			
			//���ݿ��ʼ��״̬
			int init=new SharedPreferencesUtil(this).getInitState();
			  //-1����ʡ�����ݿ�Ϊ��ʱ
			if(init==-1){
				new CityBiz(this).setupDatabase();
				pd =new ProgressDialog(this);
				pd.setTitle("����������������...");
				pd.setCancelable(false);
				pd.show();
			}
			
			 String currCity = new SharedPreferencesUtil(this).getCurrCity();
			 if(!currCity.equals("-1")){
				 
				 weaterBiz.getWeater(currCity) ;
			 }else{
				 //�Զ���λ
				 if(!pd.isShowing()){
				 LocationUtils.openGPSSettings(this);
				 }
			 }
			
			
		}
		// ��ʼ��popupwindow����
		animForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
		animBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_back);
		// popupwindow
		addListener();
	}
	private void addListener() {
		// TODO Auto-generated method stub
		ivTitleMore.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				// ��ʾpopupwindow
				showWindow(v);

				// ���õ������
				v.startAnimation(animForward);

			}
		});

		// ������
		ivTileMenuLeft.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {

				getSlidingMenu().toggle();
			}
		});
	}
	protected void showWindow(View v) {
		if (popupWindow == null) {

			LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
			view = inflater.inflate(R.layout.pop_view, null);
//			llset = (LinearLayout)view.findViewById(R.id.pop_ll_set);
//			llexit = (LinearLayout)view.findViewById(R.id.pop_ll_exit);
			btncancel =(Button)view.findViewById(R.id.btn_cancel);
			
			popupWindow = new PopupWindow(view, 200, 250);

			popupWindow.setOnDismissListener(new OnDismissListener() {

				@Override
				public void onDismiss() {

					ivTitleMore.startAnimation(animBackward);
				}
			});

		}
		// ʹ��۽�
		popupWindow.setFocusable(true);
		// ����������������ʧ
		popupWindow.setOutsideTouchable(true);

		// �����Ϊ�˵��������Back��Ҳ��ʹ����ʧ
		popupWindow.setBackgroundDrawable(new BitmapDrawable());
		WindowManager windowManager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
		// ��ʾ��λ��Ϊ:��Ļ�Ŀ�ȵ�һ��-PopupWindow�ĸ߶ȵ�һ��
		int xPos = windowManager.getDefaultDisplay().getWidth() / 2
				- popupWindow.getWidth() / 2;
		popupWindow.showAsDropDown(v, xPos, 0);
		
		
		//���ü����˳�����
		btncancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				finish();
				System.exit(0);
			}
		});

	}
	
	
	
	
	public void onRefresh() {
	    new Handler().postDelayed(new Runnable() {
	        public void run() {
	            swipeLayout.setRefreshing(false);
	        }
	    }, 5000);
	}
	private void setupWindow(){
		//�ޱ���
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		//��Ļ����
		 getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
		 //
		 requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
	}
	private void setupData() {
		ArrayList<Weater> weaters = app.getWeaters();
		if(weaters ==null || weaters.size()==0){
			return ;
		}
		Weater weater = weaters.get(0);
		
		tvTitleCity.setText(weater.getCity());
		tvTitleDate.setText(weater.getDate());
		Log.i("info", weater.getCity());
		dayView.setupView();
		weekView.setupView();
		//��ʼ��֪ͨ��
		InitNotification();
	}

	private void InitNotification() {
		//֪ͨ�������
		notiManager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
		//������
		builder = new Builder(this).setAutoCancel(false);
		views = new RemoteViews(getPackageName(), R.layout.notif_current_layout);
		
		//����󴥷��¼�
		Intent notifiIntent = new Intent(this, this.getClass());
		notifiIntent.setAction(Intent.ACTION_MAIN);
		notifiIntent.addCategory(Intent.CATEGORY_LAUNCHER);
		
		PendingIntent pendingIntent = 
				PendingIntent.getActivity(this, 0, notifiIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		
		builder.setContentIntent(pendingIntent);
		
}
	private void setupView() {
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		swipeLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_container);
		swipeLayout.setOnRefreshListener(this);
		swipeLayout.setColorScheme(android.R.color.holo_blue_bright,
				android.R.color.holo_green_light,
				android.R.color.holo_orange_light,
				android.R.color.holo_red_light);
		ivTitleMore = (ImageView) findViewById(R.id.iv_main_title_more);
		ivTileMenuLeft = (ImageView) findViewById(R.id.iv_main_title_Slidingmenu);
//		animForward = AnimationUtils.loadAnimation(this, R.anim.rotate_forward);
//		animBackward = AnimationUtils.loadAnimation(this, R.anim.rotate_back);

	//	addListener();
		
		
		
		initMainViewPager();
//		// ������
				tvTitleCity = (TextView) findViewById(R.id.tv_main_title_city);
				tvTitleDate = (TextView) findViewById(R.id.tv_main_title_date);
				ivTileMenuLeft = (ImageView) findViewById(R.id.iv_main_title_Slidingmenu);
//		
				
	}
	
	
	//���ΰ��·��ؼ��˳�
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if(keyCode==KeyEvent.KEYCODE_BACK){
			if(!getSlidingMenu().isMenuShowing()){
				if(System.currentTimeMillis()-exitTime>2000){
					Toast.makeText(this, "�ٰ�һ���˳�����", Toast.LENGTH_SHORT).show();
					exitTime=System.currentTimeMillis();
				}else{
					finish();
					System.exit(0);
				}
			}
			return false;
		}
		return super.onKeyDown(keyCode, event);
	}
	
//04-19 06:13:01.620: W/System.err(3341): java.net.UnknownHostException: Unable to resolve host "webservice.webxml.com.cn": No address associated with hostname
	/**
	 * viewpager�����ҳ��
	 */
	private void initMainViewPager() {
		vp_main = (ViewPager) findViewById(R.id.vp_main);
		dayView = new DayView();
		weekView = new WeekView();
		fragments = new ArrayList<Fragment>();
		fragments.add(dayView);
		fragments.add(weekView);
		adapter = new MainPageAdapter(getSupportFragmentManager(), fragments);
		vp_main.setAdapter(adapter);
		
	}


	@Override
	protected void onDestroy() {
		//ע���㲥
		unregisterReceiver(receiver);
		super.onDestroy();
	}
	

	
	private void register(){
		receiver = new InnerReceiver();
		IntentFilter filter = new IntentFilter();
		filter.addAction(GlobalConstants.UPDATE_WEATER);
		filter.addAction(GlobalConstants.LOCATE_CITY);
		filter.addAction(GlobalConstants.CHECK_GPS);
		filter.addAction(GlobalConstants.FINISH_DB);
		registerReceiver(receiver, filter);
	}
	
 	class	InnerReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
				String action = intent.getAction();
				Log.i("info", "MainActivity-action"+action);
			if(action.equals(GlobalConstants.UPDATE_WEATER)){
//				Toast.makeText(MainActivity.this,"ss", 0).show();
				setupData();
				sendNotification();
			}else if(GlobalConstants.LOCATE_CITY.equals(action)){
				if("ERROR".equals(intent.getStringExtra("locate_state"))){
				 //��λʧ��
				 weaterBiz.getWeater();
				}
			}else if(GlobalConstants.CHECK_GPS.equals(action)){
				//GPS��������������,��ȥ��γ�ȷ��������ȡ����
				LocationUtils.getCityLocation(context);
			}else if(GlobalConstants.FINISH_DB.equals(action)){
				pd.dismiss();
			}
			
		}
 		
 	}
//	at android.support.v4.widget.SwipeRefreshLayout.startRefresh(SwipeRefreshLayout.java:441)

	public void sendNotification() {
		if(app.getWeaters() ==null || app.getWeaters().size()==0){
			return;
		}
		Weater w=app.getWeaters().get(0);
		if (w != null) {
			Log.i("info", w.getHumidity());
			Log.i("info", w.getWeater());
			Log.i("info", w.getTemperature());
			Log.i("info", w.getWind_direction_power());
			Log.i("info", w.getWeather_pic_1());
			
			String name = w.getWeather_pic_1();
			name = "w_" + name.substring(0, name.indexOf(".gif"));
			Log.i("info", "name: " + name);

			// ��ȡ��ԴͼƬID
			int drId = getResources().getIdentifier(name, "drawable",
					getPackageName());
			Log.i("info", "id:" + drId);

			views.setTextViewText(R.id.humidity, "ʪ��:" + w.getHumidity());
			views.setTextViewText(R.id.jour, "����:" + w.getWeater());
			views.setTextViewText(R.id.temp, "�¶�:" + w.getTemperature());
			views.setTextViewText(R.id.wind,
					" " + w.getCity());
			views.setImageViewResource(R.id.icon, drId);

			builder.setSmallIcon(drId).setTicker(w.getWeater());

			Notification noti = builder.build();
			noti.contentView = views;
			notiManager.notify(1, noti);
		}
	}
	
	
}