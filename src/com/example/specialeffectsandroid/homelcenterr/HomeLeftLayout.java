package com.example.specialeffectsandroid.homelcenterr;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

public class HomeLeftLayout extends LinearLayout{

	public HomeLeftLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayout();
	}

	public HomeLeftLayout(Context context) {
		super(context);
		initLayout();
	}

	private void initLayout() {
		setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		setBackgroundColor(Color.BLUE);
	}
}
