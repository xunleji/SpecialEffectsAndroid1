package com.example.specialeffectsandroid.zhuanpan;

import java.util.ArrayList;

import com.example.specialeffectsandroid.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.View.OnTouchListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageButton;

public class WheelMain extends Activity {

	private WheelView panView;
	private GestureDetector gestureDetector;
	private static final int FLING_MIN_DISTANCE = 80;
	private static final int FLING_MIN_VELOCITY = 100;
	public static final int ROTATEITEMSELECT_FINISH = 0;
	public ArrayList<String> arrayList;
	private float surfacViewWidth = 0;
	private float surfacViewHeight = 0;


	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.wheel);

		gestureDetector = new GestureDetector(this, new MyGestureDetector());
		panView = (WheelView) this.findViewById(R.id.myView);
		panView.setOnTouchListener(new MyOnTouchListener());
		panView.setLongClickable(true);
		panView.start();

		surfacViewHeight = panView.getHeight();
		surfacViewWidth = panView.getWidth();
	}
	
	

	public void begin(boolean bool, float sp) {
		this.panView.setDirection(bool, sp);
		panView.rotateEnable();
	}

	

	private class MyOnTouchListener implements OnTouchListener {
		@Override
		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			switch (event.getAction()) {

			case MotionEvent.ACTION_DOWN:

				break;

			case MotionEvent.ACTION_MOVE:
				break;

			case MotionEvent.ACTION_UP:
				break;
			}
			gestureDetector.onTouchEvent(event);
			return true;
		}
	}

	private class MyGestureDetector extends SimpleOnGestureListener {

		/******************************************************************************
		 *  用户按下触摸屏、快速移动后松开即触发这个事件 e1：第1个ACTION_DOWN
		 *  MotionEvent e2：最后一个ACTION_MOVE MotionEvent velocityX：X轴上的移动速度，像素/秒
		 *  velocityY：Y轴上的移动速度，像素/秒 触发条件 ：
		 *  X轴的坐标位移大于FLING_MIN_DISTANCE，且移动速度大于FLING_MIN_VELOCITY个像素/秒  
		 ******************************************************************************/
		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {

			/*
			 * float f1 = (int) e1.getX(); float f2 = (int) e1.getY(); float f3
			 * = (int) e2.getX(); float f4 = (int) e2.getY(); float distance
			 * =Math.abs( f1 - f2 + f3 - f4);
			 * 
			 * //小于移动的距离 if (distance <=FLING_MIN_DISTANCE) { return false; }
			 * float f5 = Math.abs(velocityX)+ Math.abs(velocityY);
			 */

			float xDistance = Math.abs(e1.getX() - e2.getX());
			float yDistance = Math.abs(e1.getY() - e2.getY());

			if (xDistance >= FLING_MIN_DISTANCE
					|| yDistance >= FLING_MIN_DISTANCE) {
				// 判断是一个滑行的手势了
				if (Math.abs(velocityX) + Math.abs(velocityY) > FLING_MIN_VELOCITY) {
					float sAngle = (float) computeAngleFromCentre(e1.getX(),
							e1.getY());
					float dAngle = (float) computeAngleFromCentre(e2.getX(),
							e2.getY());

					float result = dAngle - sAngle;
					if (result < 0) {
						// 顺时针
						System.out.println("顺时针转动，xAngle is " + sAngle
								+ " ,yAngle is" + dAngle + ",result is "
								+ result);
						begin(true, Math.abs(result));
					} else {
						System.out.println("逆时针转动，xAngle is " + sAngle
								+ " ,yAngle is" + dAngle + ",result is "
								+ result);
						begin(false, Math.abs(result));
					}
				}
			}
			return false;
		}
	}

	public double computeAngleFromCentre(float x, float y) {
		return Math.atan2(this.surfacViewWidth - x, this.surfacViewHeight - y)
				* 180 / Math.PI;
	}
}