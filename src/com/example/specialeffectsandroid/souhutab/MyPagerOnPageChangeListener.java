package com.example.specialeffectsandroid.souhutab;

import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;

public class MyPagerOnPageChangeListener implements OnPageChangeListener {

	private RadioBtnOnclick radioonclick;
	public MyPagerOnPageChangeListener(RadioBtnOnclick radioonclick) {
		super();
		this.radioonclick = radioonclick;
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onPageScrollStateChanged(int arg0) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onPageScrolled(int arg0, float arg1, int arg2) {

	}

	@Override
	public void onPageSelected(int position) {
		radioonclick.onclick(position);
	}

}
