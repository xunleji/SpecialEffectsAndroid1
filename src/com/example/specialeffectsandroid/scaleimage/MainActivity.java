package com.example.specialeffectsandroid.scaleimage;

import com.example.specialeffectsandroid.R;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.scaleiv);
		MyImageView myImageView = (MyImageView)findViewById(R.id.imageview);
		myImageView.setImageDrawable(getResources().getDrawable(R.drawable.img10));
	}
}
