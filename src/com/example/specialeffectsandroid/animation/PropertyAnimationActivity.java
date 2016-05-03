package com.example.specialeffectsandroid.animation;

import com.example.specialeffectsandroid.Diary;
import com.example.specialeffectsandroid.R;
import com.nineoldandroids.animation.Animator;
import com.nineoldandroids.animation.AnimatorListenerAdapter;
import com.nineoldandroids.animation.AnimatorSet;
import com.nineoldandroids.animation.ObjectAnimator;
import com.nineoldandroids.animation.ValueAnimator;
import com.nineoldandroids.animation.ValueAnimator.AnimatorUpdateListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 属性动画测试
 * 
 * @author xujuan
 * 
 */
public class PropertyAnimationActivity extends Activity {

	private Button btn;
	private TextView tv;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_property_main);
		tv = (TextView) findViewById(R.id.textView1);
		btn = (Button) findViewById(R.id.animaitontestbtn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// ValueAnimaitonTest();
				// ObjectAnimationTest();
				MoreAnimaitonTest();
			}
		});
	}

	private void ValueAnimaitonTest() {
		ValueAnimator valueAnimation = ValueAnimator.ofFloat(0f, 1f);
		valueAnimation.setDuration(500);
		valueAnimation.setStartDelay(1000);
		valueAnimation.addUpdateListener(new AnimatorUpdateListener() {

			@Override
			public void onAnimationUpdate(ValueAnimator arg0) {
				// 想要对某元素做动画效果需在这里面实现
				Diary.print("arg0=" + arg0.getAnimatedValue());
			}
		});
		valueAnimation.start();
	}

	/**
	 * alpha：渐变 rotation：旋转 translationX：沿X轴平移 translationY：沿Y轴平移 scaleX：沿X轴缩放
	 * scaleY：沿Y轴缩放
	 * 
	 */
	private void ObjectAnimationTest() {
		// ObjectAnimator objectAnimation = ObjectAnimator.ofFloat(tv, "alpha",
		// 1f, 0f, 1f);
		// ObjectAnimator objectAnimation = ObjectAnimator.ofFloat(tv,
		// "rotation",
		// 0f, 180f, 360f, 180f, 0f);//角度
		// ObjectAnimator objectAnimation = ObjectAnimator.ofFloat(tv,
		// "translationX",
		// 0f, 180f,0f);//位置
		// ObjectAnimator objectAnimation = ObjectAnimator.ofFloat(tv,
		// "translationY",
		// 0f, 180f,0f);//位置
		// ObjectAnimator objectAnimation = ObjectAnimator.ofFloat(tv, "scaleX",
		// 1f, 2f,1f);//倍数
		ObjectAnimator objectAnimation = ObjectAnimator.ofFloat(tv, "scaleY",
				1f, 2f, 1f);// 倍数
		objectAnimation.addListener(new AnimatorListenerAdapter() {

			@Override
			public void onAnimationEnd(Animator animation) {
				// TODO Auto-generated method stub
				super.onAnimationEnd(animation);
			}
		});
		objectAnimation.setDuration(8000);
		objectAnimation.setStartDelay(500);
		objectAnimation.start();
	}

	/**
	 * 动画组合使用
	 * 
	 */
	private void MoreAnimaitonTest() {
		ObjectAnimator moveIn = ObjectAnimator.ofFloat(tv, "translationX",
				0f, 100f);
		ObjectAnimator rotate = ObjectAnimator
				.ofFloat(tv, "rotation", 0f, 360f);
		ObjectAnimator fadeInOut = ObjectAnimator.ofFloat(tv, "alpha", 1f, 0f,
				1f);
		AnimatorSet animSet = new AnimatorSet();
		animSet.play(rotate).with(fadeInOut).after(moveIn);
		animSet.setDuration(5000);
		animSet.start();
	}

}
