package com.example.specialeffectsandroid.homelcenterr;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

public class HomeRightLayout extends LinearLayout {

	public HomeRightLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayout();
	}

	public HomeRightLayout(Context context) {
		super(context);
		initLayout();
	}

	private void initLayout() {
		setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		setBackgroundColor(Color.GREEN);
	}
	
}
