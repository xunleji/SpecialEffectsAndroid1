package com.example.specialeffectsandroid.playgif;

import com.example.specialeffectsandroid.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;

public class gifActivity extends Activity {
	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gifmain);
		Button btn = (Button) findViewById(R.id.Button01);
		Button btn2 = (Button) findViewById(R.id.Button02);
		final TypegifView view = (TypegifView) findViewById(R.id.gifView1);

		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				view.setStop();
			}
		});

		btn2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				view.setStart();
			}
		});
	}
}
