package com.example.specialeffectsandroid.fanye;

import android.app.Activity;
import android.os.Bundle;

public class FanyeActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(new PageWidget(this));
	}

}
