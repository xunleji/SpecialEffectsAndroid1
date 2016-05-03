package com.example.specialeffectsandroid.circlemenu;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.specialeffectsandroid.R;
import com.example.specialeffectsandroid.circlemenu.CircleImageView;
import com.example.specialeffectsandroid.circlemenu.CircleLayout;
import com.example.specialeffectsandroid.circlemenu.CircleLayout.OnItemClickListener;
import com.example.specialeffectsandroid.circlemenu.CircleLayout.OnItemSelectedListener;

public class CircleMenuActivity extends Activity implements OnItemSelectedListener, OnItemClickListener {
	
	TextView selectedTextView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.circlemenu);

		CircleLayout circleMenu = (CircleLayout) findViewById(R.id.main_circle_layout);
		circleMenu.setOnItemSelectedListener(this);
		circleMenu.setOnItemClickListener(this);

		selectedTextView = (TextView) findViewById(R.id.main_selected_textView);
		selectedTextView.setText(((CircleImageView) circleMenu.getSelectedItem()).getName());
	}

	@Override
	public void onItemSelected(View view, int position, long id, String name) {
		selectedTextView.setText(name);
	}

	@Override
	public void onItemClick(View view, int position, long id, String name) {
		Toast.makeText(getApplicationContext(), name, Toast.LENGTH_SHORT).show();
	}
}
