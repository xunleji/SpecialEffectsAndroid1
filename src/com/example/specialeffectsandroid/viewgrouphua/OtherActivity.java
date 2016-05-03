package com.example.specialeffectsandroid.viewgrouphua;

import android.app.Activity;
import android.os.Bundle;
import android.widget.Button;

public class OtherActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Button btn = new Button(this);
		setContentView(btn);
	}
	
}
