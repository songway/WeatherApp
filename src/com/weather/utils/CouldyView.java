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
	private boolean flag = false;// �̱߳�ʶ
	private Bitmap bitmap_bg, bitmap_bg1, bitmap_bg2;// ����ͼ

	private int mBitposX_1 = 0;// ͼƬ��λ��
	private int mBitposX_2 = 0;// ͼƬ��λ��
	private int mBitposX_5 = 0;// ͼƬ��λ��
	private int mBitposX_1_2 = 0;// ͼƬ��λ��
	private int mBitposX_2_2 = 0;// ͼƬ��λ��
	private int mBitposX_5_2 = 0;// ͼƬ��λ��

	private Canvas mCanvas;

	private Thread thread;

	private final int BITMAP_STEP_cloudy_1 = 2;// ���������ƶ�����.
	private final int BITMAP_STEP_cloudy_2 = 1;// ���������ƶ�����.
	private final int BITMAP_STEP_cloudy_5 = 3;// ���������ƶ�����.

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
		setZOrderOnTop(true);// ���û��� ����͸��
		getHolder().setFormat(PixelFormat.TRANSLUCENT);
		getHolder().addCallback(this);
		Log.i("info", "CouldyView");
		// networkInfo.getExtraInfo() is epc.tmobile.com
		thread = new Thread(this);
		thread.start();
	}

	/***
	 * ���л���.
	 */
	protected void onDraw() {
		drawBG();
		updateBG();
	}
	//Fatal signal 11 (SIGSEGV) at 0x4ddb7698 (code=1), thread 25165 (Thread-189)

	/***
	 * ���±���.
	 */
	public void updateBG() {
		/** ͼƬ����Ч�� **/

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
	 * ���Ʊ���
	 */
	public void drawBG() {
		// Log.i("info", "surface--------drawBG");
		// // ������draw�����л��Ʊ�����ɫ��ʱ��������ķ�ʽ���л��ƾͿ���ʵ��SurfaceView�ı���͸����
		if (mCanvas == null) {
			return;
		}
		mCanvas.drawColor(Color.TRANSPARENT, Mode.CLEAR);
		mCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);// ����Ļ.
		Paint paint = new Paint();
		paint.setAlpha(200);
		// mCanvas.drawBitmap(bitmap_bg, 0, 0, null);// ���Ƶ�ǰ��Ļ����

		mCanvas.drawBitmap(bitmap_bg2, mBitposX_2, windowHeight - 130, paint);// ���Ƶ�ǰ��Ļ����
		mCanvas.drawBitmap(bitmap_bg2_2, mBitposX_2_2, windowHeight - 130,
				paint);// ���Ƶ�ǰ��Ļ����

		mCanvas.drawBitmap(bitmap_bg1, mBitposX_1, windowHeight - 110, paint);// ���Ƶ�ǰ��Ļ����
		mCanvas.drawBitmap(bitmap_bg1_2, mBitposX_1_2, windowHeight - 110,
				paint);// ���Ƶ�ǰ��Ļ����

		mCanvas.drawBitmap(bitmap_bg3, mBitposX_5, windowHeight - 220, paint);// ���Ƶ�ǰ��Ļ����
		// mCanvas.drawBitmap(bitmap_bg3_2, mBitposX_5_2,windowHeight-200,
		// null);// ���Ƶ�ǰ��Ļ����

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
		 * ��ͼƬ���ŵ���Ļ��3/2��.
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
		//�Ƿ��˶�
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
