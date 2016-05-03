package com.example.specialeffectsandroid.animation;

import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.BounceInterpolator;

public class MyAnimView extends View {

	public static final float RADIUS = 50f;
	private Point currentPoint;
	private Paint mPaint;
	private String color;

	public MyAnimView(Context context) {
		super(context);
		// TODO Auto-generated constructor stub
	}

	public MyAnimView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
		mPaint.setColor(Color.BLUE);
	}

	public MyAnimView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		if (currentPoint == null) {
			currentPoint = new Point(RADIUS, RADIUS);
			drawCircle(canvas);
			startAnimation();
		} else {
			drawCircle(canvas);
		}
	}

	private void drawCircle(Canvas canvas) {
		float x = currentPoint.getX();
		float y = currentPoint.getY();
		canvas.drawCircle(x, y, RADIUS, mPaint);
	}

	private void startAnimation() {
		Point startPoint = new Point(RADIUS, RADIUS);
		Point endPoint = new Point(RADIUS, getHeight() - RADIUS);
		ValueAnimator anim = ValueAnimator.ofObject(new PointEvaluator(),
				startPoint, endPoint);
		anim.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
			@Override
			public void onAnimationUpdate(ValueAnimator animation) {
				currentPoint = (Point) animation.getAnimatedValue();
				invalidate();
			}
		});
		ObjectAnimator anim2 = ObjectAnimator.ofObject(this, "color",
				new ColorEvaluator(), "#0000FF", "#FF0000");
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(anim).with(anim2);
		animSet.setInterpolator(new BounceInterpolator());
		animSet.setDuration(2000);
		animSet.start();
	}

	/**
	 * @return the color
	 */
	public String getColor() {
		return color;
	}

	/**
	 * @param color
	 *            the color to set
	 */
	public void setColor(String color) {
		this.color = color;
		mPaint.setColor(Color.parseColor(color));
		invalidate();
	}

}
