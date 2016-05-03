package com.example.specialeffectsandroid.bubble;

import com.example.specialeffectsandroid.Diary;
import com.example.specialeffectsandroid.R;

import android.app.Activity;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnTouchListener;
import android.widget.FrameLayout;

public class BubbleActivity extends Activity {

	private FrameLayout fl;
	private ExplosionView exv1;
	private AnimationDrawable exa1;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
		fl = new FrameLayout(this);
		fl.setBackgroundResource(R.drawable.bg);

		exv1 = new ExplosionView(this);
		exv1.setVisibility(View.INVISIBLE);
		exv1.setBackgroundResource(R.anim.explosion);
		fl.addView(exv1);
		fl.setOnTouchListener(new LayoutListener());
		setContentView(fl);
	}

	class LayoutListener implements OnTouchListener {

		public boolean onTouch(View v, MotionEvent event) {
			// first u have to stop the animation,or if the animation
			// is starting ,u can start it again!
			switch (event.getAction()) {
			case MotionEvent.ACTION_UP:
				exa1 = (AnimationDrawable) exv1.getBackground();
				exa1.stop();
				float x = event.getX();
				float y = event.getY();
				Diary.print("x===" + x + "y==" + y);
				exv1.setLocation((int) y - 20, (int) x - 20);
				exv1.setVisibility(View.VISIBLE);
				exa1.start();
				break;
			default:
				break;
			}
			return true;
		}

	}

}
