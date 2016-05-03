package com.example.specialeffectsandroid.tabscroll;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

public class SimpleHomeActivity extends FragmentActivity {

	private final String[] items = { "SlidingTabLayout", "CommonTabLayout" };
	private final Class<?>[] classes = { SlidingTabActivity.class,
			CommonTabActivity.class };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		ListView lv = new ListView(this);
		lv.setCacheColorHint(Color.TRANSPARENT);
		lv.setFadingEdgeLength(0);
		lv.setAdapter(new SimpleHomeAdapter(this, items));

		lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				Intent intent = new Intent(SimpleHomeActivity.this,
						classes[position]);
				startActivity(intent);
			}
		});

		setContentView(lv);
	}
}
