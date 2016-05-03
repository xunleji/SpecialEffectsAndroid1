package com.example.specialeffectsandroid.lockpass;

import android.app.Activity;
import android.os.Bundle;

public class LockPassActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		setContentView(new LockPassView(this));
		setContentView(new LockPassLineView(this));
	}

}
