package com.example.specialeffectsandroid.souhutab;

import java.util.ArrayList;

import com.example.specialeffectsandroid.R;
import com.example.specialeffectsandroid.souhutab.view.FiveView;
import com.example.specialeffectsandroid.souhutab.view.FourView;
import com.example.specialeffectsandroid.souhutab.view.MyScrollLayout;
import com.example.specialeffectsandroid.souhutab.view.OnViewChangeListener;
import com.example.specialeffectsandroid.souhutab.view.OneView;
import com.example.specialeffectsandroid.souhutab.view.ThreeView;
import com.example.specialeffectsandroid.souhutab.view.TwoView;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.AbsListView;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.AbsListView.OnScrollListener;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class SouHuTabActivity extends Activity implements
		OnCheckedChangeListener {

	private RadioGroup mRadioGroup;
	private RadioButton mRadioButton[];
	private ImageView mImageView;
	private HorizontalScrollView mHorizontalScrollView;//
	private float mCurrentCheckedRadioLeft;//
	private int curindex = 0;
	private RelativeLayout rl[];
	private OneView oneview;
	private TwoView twoview;
	private ThreeView threeview;
	private FourView fourview;
	private FiveView fiveview;
	private LayoutParams params = new LayoutParams(LayoutParams.FILL_PARENT,
			LayoutParams.FILL_PARENT);
	private ArrayList<View> mViews;
	private ViewPager mViewPager; //

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.souhutab);
		iniController();
		mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();
	}

	// 初始化控件
	private void iniController() {
		mRadioButton = new RadioButton[5];
		rl = new RelativeLayout[5];
		mRadioGroup = (RadioGroup) findViewById(R.id.radioGroup);
		mRadioButton[0] = (RadioButton) findViewById(R.id.btn1);
		mRadioButton[1] = (RadioButton) findViewById(R.id.btn2);
		mRadioButton[2] = (RadioButton) findViewById(R.id.btn3);
		mRadioButton[3] = (RadioButton) findViewById(R.id.btn4);
		mRadioButton[4] = (RadioButton) findViewById(R.id.btn5);
		mImageView = (ImageView) findViewById(R.id.img1);
		mHorizontalScrollView = (HorizontalScrollView) findViewById(R.id.horizontalScrollView);
		mRadioButton[curindex].setTextColor(Color.parseColor("#990000"));
		mRadioGroup.setOnCheckedChangeListener(this);
		mViewPager = (ViewPager) findViewById(R.id.pager);
		mViewPager.setCurrentItem(0);
		oneview = new OneView(this);
		twoview = new TwoView(this);
		threeview = new ThreeView(this);
		fourview = new FourView(this);
		fiveview = new FiveView(this);
		mViews = new ArrayList<View>();
		mViews.add(oneview);
		mViews.add(twoview);
		mViews.add(threeview);
		mViews.add(fourview);
		mViews.add(fiveview);
		mViewPager.setAdapter(new MyPagerAdapter(mViews));
		oneview.initdata();
		mViewPager.setOnPageChangeListener(new MyPagerOnPageChangeListener(
				new RadioBtnOnclick() {

					@Override
					public void onclick(int pos) {
						mRadioButton[pos].performClick();
						curindex = pos;
						switch (pos) {
						case 0:
							oneview.initdata();
							break;
						case 1:
							twoview.initdata();
							break;
						case 2:
							threeview.initdata();
							break;
						case 3:
							fourview.initdata();
							break;
						case 4:
							fiveview.initdata();
							break;
						default:
							break;
						}
					}
				}));
	}

	// 获取当前tab最底下红线的位置
	private float getCurrentCheckedRadioLeft() {
		// TODO Auto-generated method stub
		if (mRadioButton[0].isChecked()) {
			return ToolUntil.dip2px(this, 0);
		} else if (mRadioButton[1].isChecked()) {
			return ToolUntil.dip2px(this, 80);
		} else if (mRadioButton[2].isChecked()) {
			return ToolUntil.dip2px(this, 160);
		} else if (mRadioButton[3].isChecked()) {
			return ToolUntil.dip2px(this, 240);
		} else if (mRadioButton[4].isChecked()) {
			return ToolUntil.dip2px(this, 320);
		}
		return 0f;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		AnimationSet _AnimationSet = new AnimationSet(true);
		TranslateAnimation _TranslateAnimation = null;
		if (checkedId == R.id.btn1) {
			curindex = 0;
			_TranslateAnimation = new TranslateAnimation(
					mCurrentCheckedRadioLeft, ToolUntil.dip2px(this, 0), 0f, 0f);
		} else if (checkedId == R.id.btn2) {
			curindex = 1;
			_TranslateAnimation = new TranslateAnimation(
					mCurrentCheckedRadioLeft, ToolUntil.dip2px(this, 80), 0f,
					0f);
		} else if (checkedId == R.id.btn3) {
			curindex = 2;
			_TranslateAnimation = new TranslateAnimation(
					mCurrentCheckedRadioLeft, ToolUntil.dip2px(this, 160), 0f,
					0f);
		} else if (checkedId == R.id.btn4) {
			curindex = 3;
			_TranslateAnimation = new TranslateAnimation(
					mCurrentCheckedRadioLeft, ToolUntil.dip2px(this, 240), 0f,
					0f);
		} else if (checkedId == R.id.btn5) {
			curindex = 4;
			_TranslateAnimation = new TranslateAnimation(
					mCurrentCheckedRadioLeft, ToolUntil.dip2px(this, 320), 0f,
					0f);
		}
		for (int i = 0; i < 5; i++) {
			if (i == curindex)
				mRadioButton[i].setTextColor(Color.parseColor("#990000"));
			else
				mRadioButton[i].setTextColor(Color.parseColor("#ffffff"));
		}
		_AnimationSet.addAnimation(_TranslateAnimation);
		_AnimationSet.setFillBefore(false);
		_AnimationSet.setFillAfter(true);
		_AnimationSet.setDuration(50);
		mViewPager.setCurrentItem(curindex);
		mImageView.startAnimation(_AnimationSet);
		mCurrentCheckedRadioLeft = getCurrentCheckedRadioLeft();
		mHorizontalScrollView.smoothScrollTo((int) mCurrentCheckedRadioLeft
				- ToolUntil.dip2px(this, 80), 0);
	}
}
