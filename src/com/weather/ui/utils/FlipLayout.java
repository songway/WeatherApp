/*
 * Created by Storm Zhang, Mar 31, 2014.
 */

package com.weather.ui.utils;

import android.content.Context;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Interpolator;
import android.view.animation.Transformation;
import android.widget.FrameLayout;

public class FlipLayout extends FrameLayout implements View.OnClickListener,
		Animation.AnimationListener {
	private static final int DURATION = 800;
	private static final Interpolator fDefaultInterpolator = new DecelerateInterpolator();

	private OnFlipListener mListener;
	private FlipAnimator mAnimation;
	private boolean mIsFlipped;
	private boolean mIsRotationReversed;

	private View mFrontView, mBackView;
	private Context context;
	private int windowWidth;
	private int windowHight;

	public FlipLayout(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		this.context=context;
		init();
	}

	public FlipLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		this.context=context;
		init();
	}

	public FlipLayout(Context context) {
		super(context);
		this.context=context;
		init();
	}

	private void init() {
		windowWidth = context.getResources().getDisplayMetrics().heightPixels;
		windowHight = context.getResources().getDisplayMetrics().widthPixels;
		mAnimation = new FlipAnimator();
		mAnimation.setAnimationListener(this);
		mAnimation.setInterpolator(fDefaultInterpolator);
		mAnimation.setDuration(DURATION);
		setOnClickListener(this);
	}
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		onTouchEvent(ev);
		return super.dispatchTouchEvent(ev);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
			switch (event.getAction()) {
			case MotionEvent.ACTION_DOWN:
				int  x = (int)event.getX();
				int  y = (int)event.getY();
				if(y>(windowWidth/10*7) ){
//					Log.i("info", "getY=="+y);
//					Log.i("info", "windowWidth/10*7=="+windowWidth/10*7);
					mAnimation.setVisibilitySwapped();
					startAnimation(mAnimation);
					if (mListener != null) {
						mListener.onClick(this);
					}
					return true;
				}
				break;

			}
		return super.onTouchEvent(event);
	}
	@Override
	protected void onFinishInflate() {
		super.onFinishInflate();

		if (getChildCount() > 2) {
			throw new IllegalStateException("FlipLayout can host only two direct children");
		}

		mFrontView = getChildAt(0);
		mBackView = getChildAt(1);
	}

	private void toggleView() {
		if (mFrontView == null || mBackView == null) {
			return;
		}

		if (mIsFlipped) {
			mFrontView.setVisibility(View.VISIBLE);
			mBackView.setVisibility(View.GONE);
		} else {
			mFrontView.setVisibility(View.GONE);
			mBackView.setVisibility(View.VISIBLE);
		}

		mIsFlipped = !mIsFlipped;
	}

	public void setOnFlipListener(OnFlipListener listener) {
		mListener = listener;
	}

	public void reset() {
		mIsFlipped = false;
		mIsRotationReversed = false;
		mFrontView.setVisibility(View.VISIBLE);
		mBackView.setVisibility(View.GONE);
	}

	@Override
	public void onClick(View v) {
//		mAnimation.setVisibilitySwapped();
//		startAnimation(mAnimation);
//		if (mListener != null) {
//			mListener.onClick(this);
//		}
	}

	public interface OnFlipListener {

		public void onClick(FlipLayout view);

		public void onFlipStart(FlipLayout view);

		public void onFlipEnd(FlipLayout view);
	}

	public class FlipAnimator extends Animation {
		private Camera camera;

		private float centerX;

		private float centerY;

		private boolean visibilitySwapped;

		public FlipAnimator() {
			setFillAfter(true);
		}

		public void setVisibilitySwapped() {
			visibilitySwapped = false;
		}

		@Override
		public void initialize(int width, int height, int parentWidth, int parentHeight) {
			super.initialize(width, height, parentWidth, parentHeight);
			camera = new Camera();
			this.centerX = width / 2;
			this.centerY = height / 2;
		}

		@Override
		protected void applyTransformation(float interpolatedTime, Transformation t) {
			// Angle around the y-axis of the rotation at the given time. It is
			// calculated both in radians and in the equivalent degrees.
			final double radians = Math.PI * interpolatedTime;
			float degrees = (float) (180.0 * radians / Math.PI);

			if (mIsRotationReversed) {
				degrees = -degrees;
			}

			// Once we reach the midpoint in the animation, we need to hide the
			// source view and show the destination view. We also need to change
			// the angle by 180 degrees so that the destination does not come in
			// flipped around. This is the main problem with SDK sample, it does
			// not
			// do this.
			if (interpolatedTime >= 0.5f) {
				if (mIsRotationReversed) {
					degrees += 180.f;
				} else {
					degrees -= 180.f;
				}

				if (!visibilitySwapped) {
					toggleView();
					visibilitySwapped = true;
				}
			}

			final Matrix matrix = t.getMatrix();

			camera.save();
			camera.translate(0.0f, 0.0f, (float) (150.0 * Math.sin(radians)));
			camera.rotateX(0);
			camera.rotateY(degrees);
			camera.rotateZ(0);
			camera.getMatrix(matrix);
			camera.restore();

			matrix.preTranslate(-centerX, -centerY);
			matrix.postTranslate(centerX, centerY);
		}
	}

	@Override
	public void onAnimationStart(Animation animation) {
		if (mListener != null) {
			mListener.onFlipStart(this);
		}
	}

	@Override
	public void onAnimationEnd(Animation animation) {
		if (mListener != null) {
			mListener.onFlipEnd(this);
		}
		mIsRotationReversed = !mIsRotationReversed;
	}

	@Override
	public void onAnimationRepeat(Animation animation) {
	}
}
