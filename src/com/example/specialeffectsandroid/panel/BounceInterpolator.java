package com.example.specialeffectsandroid.panel;


import android.view.animation.Interpolator;

public class BounceInterpolator implements Interpolator {
	
	private int type;
	public BounceInterpolator(int type) {
		super();
		this.type = type;
	}

	@Override
	public float getInterpolation(float t) {
		if (type == EasingType.IN) {
			return in(t);
		} else if (type == EasingType.OUT) {
			return out(t);
		} else if (type == EasingType.INOUT) {
			return inout(t);
		}
		return 0;
	}

	private float inout(float t) {
		if (t < 0.5f) {
			return in(t*2) * .5f;
		} else {
			return out(t*2-1) * .5f + .5f;
		}
	}

	private float out(float t) {
		if (t < (1/2.75)) {
			return 7.5625f*t*t;
		} else if (t < 2/2.75) {
			return 7.5625f*(t-=(1.5/2.75))*t + .75f;
		} else if (t < 2.5/2.75) {
			return 7.5625f*(t-=(2.25/2.75))*t + .9375f;
		} else {
			return 7.5625f*(t-=(2.625/2.75))*t + .984375f;
		}
	}

	private float in(float t) {
		return 1 - out(1-t);
	}

}
