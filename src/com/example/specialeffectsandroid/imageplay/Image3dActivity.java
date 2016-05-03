package com.example.specialeffectsandroid.imageplay;

import com.example.specialeffectsandroid.R;
import com.example.specialeffectsandroid.imageplay.View3DSwitchView.OnImageSwitchListener;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

public class Image3dActivity extends Activity {

	private View3DSwitchView imageSwitchView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.imageloads);
		imageSwitchView = (View3DSwitchView) findViewById(R.id.image_switch_view);
		for (int i = 0; i < 5; i++) {
			View view = LayoutInflater.from(this).inflate(
					R.layout.vip_gallery_item, null);
			imageSwitchView.addView(view);
		}
		imageSwitchView.setOnImageSwitchListener(new OnImageSwitchListener() {
			@Override
			public void onImageSwitch(int currentImage) {
				Log.e("TAG", "current image is " + currentImage);
			}
		});
		imageSwitchView.setCurrentImage(1);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		imageSwitchView.clear();
	}

}
