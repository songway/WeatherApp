package com.weather.view;

import java.util.ArrayList;

import android.graphics.Canvas;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.animation.Interpolator;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.CanvasTransformer;
import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu.OnOpenedListener;
import com.jeremyfeinstein.slidingmenu.lib.app.SlidingFragmentActivity;
import com.weather.bll.CityBiz;
import com.weather.model.City;
import com.weather.model.Province;

public class BaseActivity extends SlidingFragmentActivity {
	private CanvasTransformer mTransformer;
	private int mTitleRes;
	protected ListFragment mFrag;
	private SlidingMenu sm;
	private CityBiz cbiz;
	private ArrayList<Province> provinces;
	private ArrayList<City> allCities;
	
	private static Interpolator interp = new Interpolator() {
		@Override
		public float getInterpolation(float t) {
			t -= 1.0f;
			return t * t * t + 1.0f;
		}		
	};

	public BaseActivity() {
		mTransformer = new CanvasTransformer() {
			@Override
			public void transformCanvas(Canvas canvas, float percentOpen) {
				canvas.translate(0, canvas.getHeight()*(1-interp.getInterpolation(percentOpen)));
			}			
		};
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// set the Behind View
		setBehindContentView(R.layout.menu_frame);
		if (savedInstanceState == null) {
			FragmentTransaction t = this.getSupportFragmentManager().beginTransaction();
			mFrag = new SampleListFragment();
			t.replace(R.id.menu_frame, mFrag);
			t.commit();
		} else {
			mFrag = (ListFragment)this.getSupportFragmentManager().findFragmentById(R.id.menu_frame);
		}

		// customize the SlidingMenu
		sm = getSlidingMenu();
		sm.setShadowWidthRes(R.dimen.shadow_width);
		sm.setShadowDrawable(R.drawable.shadow);
		sm.setBehindOffsetRes(R.dimen.slidingmenu_offset);
		sm.setFadeDegree(0.35f);
		sm.setTouchModeAbove(SlidingMenu.TOUCHMODE_MARGIN);
		//sm.setAlpha(100);
		
		setSlidingActionBarEnabled(true);
		sm.setBehindScrollScale(0.0f);
		sm.setBehindCanvasTransformer(mTransformer);
		sm.setOnOpenedListener(new OnOpenedListener() {
			
			@Override
			public void onOpened() {
				Log.i("info", "open");
				SampleListFragment sf = (SampleListFragment)mFrag;
				cbiz= new CityBiz(BaseActivity.this);
				if(provinces==null||provinces.size()==0){
					provinces=cbiz.getProvinceByLocal();
					if(provinces!=null){
						//¸üÐÂlv
						sf.updateList(provinces);
					}
				}
			}
		});
//		getSupportActionBar().setDisplayHomeAsUpEnabled(true);
	}

	public ArrayList<Province> getProvinces() {
		return provinces;
	}

	public ArrayList<City> getAllCities() {
		return allCities;
	}


	
	

}
