package com.weather.utils;

import com.weather.view.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PixelFormat;
import android.graphics.PorterDuff;
import android.graphics.PorterDuff.Mode;
import android.util.AttributeSet;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;

@SuppressLint("WrongCall")
public class CouldyView extends SurfaceView implements Callback, Runnable {

	private Context mContext;

	private SurfaceHolder surfaceHolder;
	private boolean flag = false;// 线程标识
	private Bitmap bitmap_bg, bitmap_bg1, bitmap_bg2;// 背景图

	private int mBitposX_1 = 0;// 图片的位置
	private int mBitposX_2 = 0;// 图片的位置
	private int mBitposX_5 = 0;// 图片的位置
	private int mBitposX_1_2 = 0;// 图片的位置
	private int mBitposX_2_2 = 0;// 图片的位置
	private int mBitposX_5_2 = 0;// 图片的位置

	private Canvas mCanvas;

	private Thread thread;

	private final int BITMAP_STEP_cloudy_1 = 2;// 背景画布移动步伐.
	private final int BITMAP_STEP_cloudy_2 = 1;// 背景画布移动步伐.
	private final int BITMAP_STEP_cloudy_5 = 3;// 背景画布移动步伐.

	private Bitmap bitmap_bg1_2;
	private Bitmap bitmap_bg2_2;
	private Bitmap bitmap_bg3;
	private int windowHeight;
	private int win1dowWidth;

	public CouldyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		flag = true;
		isRun = false;
		this.mContext = context;
		setFocusable(true);
		setFocusableInTouchMode(true);
		surfaceHolder = getHolder();
		setZOrderOnTop(true);// 设置画布 背景透明
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
		getHolder().addCallback(this);
		Log.i("info", "CouldyView");
		// networkInfo.getExtraInfo() is epc.tmobile.com
		thread = new Thread(this);
		thread.start();
	}

	/***
	 * 进行绘制.
	 */
	protected void onDraw() {
		drawBG();
		updateBG();
	}
	//Fatal signal 11 (SIGSEGV) at 0x4ddb7698 (code=1), thread 25165 (Thread-189)

	/***
	 * 更新背景.
	 */
	public void updateBG() {
		/** 图片滚动效果 **/

		if (mBitposX_1 >= win1dowWidth) {
			mBitposX_1 = -win1dowWidth * 2;

		}
		if (mBitposX_1_2 >= win1dowWidth) {
			mBitposX_1_2 = -win1dowWidth * 2;
		}
		if (mBitposX_2 >= win1dowWidth) {
			mBitposX_2 = -(win1dowWidth + 150);
		}

		if (mBitposX_2_2 >= win1dowWidth) {
			mBitposX_2_2 = -(win1dowWidth + 150);
		}
		if (mBitposX_5 >= win1dowWidth * 2) {

			mBitposX_5 = -win1dowWidth * 2;

		}
		// if (mBitposX_5_2>=win1dowWidth) {
		// mBitposX_5_2=-win1dowWidth;
		//
		// }

		mBitposX_1 += BITMAP_STEP_cloudy_1;
		mBitposX_2 += BITMAP_STEP_cloudy_2;

		mBitposX_1_2 += BITMAP_STEP_cloudy_1;
		mBitposX_2_2 += BITMAP_STEP_cloudy_2;
		mBitposX_5 += BITMAP_STEP_cloudy_5;

	}

	/***
	 * 绘制背景
	 */
	public void drawBG() {
		// Log.i("info", "surface--------drawBG");
		// // 还有在draw方法中绘制背景颜色的时候以下面的方式进行绘制就可以实现SurfaceView的背景透明化
		if (mCanvas == null) {
			return;
		}
		mCanvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
		mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);// 清屏幕.
		Paint paint = new Paint();
		paint.setAlpha(200);
		// mCanvas.drawBitmap(bitmap_bg, 0, 0, null);// 绘制当前屏幕背景

		mCanvas.drawBitmap(bitmap_bg2, mBitposX_2, windowHeight - 130, paint);// 绘制当前屏幕背景
		mCanvas.drawBitmap(bitmap_bg2_2, mBitposX_2_2, windowHeight - 130,
				paint);// 绘制当前屏幕背景

		mCanvas.drawBitmap(bitmap_bg1, mBitposX_1, windowHeight - 110, paint);// 绘制当前屏幕背景
		mCanvas.drawBitmap(bitmap_bg1_2, mBitposX_1_2, windowHeight - 110,
				paint);// 绘制当前屏幕背景

		mCanvas.drawBitmap(bitmap_bg3, mBitposX_5, windowHeight - 220, paint);// 绘制当前屏幕背景
		// mCanvas.drawBitmap(bitmap_bg3_2, mBitposX_5_2,windowHeight-200,
		// null);// 绘制当前屏幕背景

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		Log.i("info", "surfaceCreated----------------");
		windowHeight = this.getHeight();
		win1dowWidth = this.getWidth();
		mBitposX_1_2 = -win1dowWidth;
		mBitposX_2_2 = -win1dowWidth;
		mBitposX_5 = -win1dowWidth;
		Log.i("info", "surfaceCreated");
		/***
		 * 将图片缩放到屏幕的3/2倍.
		 */
		bitmap_bg = BitmapUtils.zoomImg(this.getResources(),
				R.drawable.bg1_cloudy_day, win1dowWidth, windowHeight);

		bitmap_bg1 = BitmapUtils.zoomImg(this.getResources(),
				R.drawable.cloudy_cloud1, win1dowWidth, 130);
		this.bitmap_bg1_2 = BitmapUtils.zoomImg(this.getResources(),
				R.drawable.cloudy_cloud1, win1dowWidth, 130);

		bitmap_bg2 = BitmapUtils.zoomImg(this.getResources(),
				R.drawable.cloudy_cloud2, win1dowWidth * 2, 130);
		bitmap_bg2_2 = BitmapUtils.zoomImg(this.getResources(),
				R.drawable.cloudy_cloud2, win1dowWidth * 2, 130);

		bitmap_bg3 = BitmapUtils.zoomImg(this.getResources(),
				R.drawable.cloudy_cloud5, win1dowWidth, 50);

	}

	@Override
	public void run() {
		while (flag) {
			while(isRun) {

				// canvas object must be the same instance that was previously
				// returned by lockCanvas

				synchronized (surfaceHolder) {
					mCanvas = surfaceHolder.lockCanvas();
					onDraw();
					if (mCanvas != null) {
						surfaceHolder.unlockCanvasAndPost(mCanvas);
					}
					try {
						Thread.sleep(100);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}
			stop();
		}

	}
		//是否运动
	private boolean isRun;

	public void start() {
		synchronized (thread) {
			if (!isRun) {
				isRun = true;
				thread.notify();
			}
		}
	}

	public void stop() {
		synchronized (thread) {
			isRun = false;
			if (isRun) {
				try {
					thread.wait();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		flag = false;
		start();
	}

}
