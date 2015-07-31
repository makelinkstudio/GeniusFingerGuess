package com.bncq.model;

import java.util.Timer;
import java.util.TimerTask;

import com.bncq.R;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.text.format.Time;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class shakeAnimation {
	private boolean mNeedShake = false;
	private boolean mStartShake = false;

	private static final int ICON_WIDTH = 80;
	private static final int ICON_HEIGHT = 94;
	private static final int ANIMATION_DURATION = 80;

	private int mCount = 0;
	private float mDensity;
	
	public shakeAnimation(Window window){
		DisplayMetrics dm = new DisplayMetrics();
		window.getWindowManager().getDefaultDisplay().getMetrics(dm);
        if (dm != null) {
            mDensity = dm.density;
        }
	}

	public void shakeAnimation(final View v) {
			mNeedShake=true;
		    float rotate = 1.8f;
	        final RotateAnimation mra = new RotateAnimation(rotate, -rotate, ICON_WIDTH * mDensity / 2, ICON_HEIGHT * mDensity / 2);
	        final RotateAnimation mrb = new RotateAnimation(-rotate, rotate, ICON_WIDTH * mDensity / 2, ICON_HEIGHT * mDensity / 2);

	        mra.setDuration(ANIMATION_DURATION);
	        mrb.setDuration(ANIMATION_DURATION);

	        mra.setAnimationListener(new AnimationListener() {
	            @Override
	            public void onAnimationEnd(Animation animation) {
	                if (mNeedShake) {
	                    mra.reset();
	                    v.startAnimation(mrb);
	                }
	            }

	            @Override
	            public void onAnimationRepeat(Animation animation) {

	            }

	            @Override
	            public void onAnimationStart(Animation animation) {

	            }

	        });

	        mrb.setAnimationListener(new AnimationListener() {
	            @Override
	            public void onAnimationEnd(Animation animation) {
	                if (mNeedShake) {
	                    mrb.reset();
	                    v.startAnimation(mra);
	                }
	            }

	            @Override
	            public void onAnimationRepeat(Animation animation) {

	            }

	            @Override
	            public void onAnimationStart(Animation animation) {

	            }

	        });
	        v.startAnimation(mra);
	}
	
	public void stopAnimation(){
		mNeedShake=false;
	}
}
