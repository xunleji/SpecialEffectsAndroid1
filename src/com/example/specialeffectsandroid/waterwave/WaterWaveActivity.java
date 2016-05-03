package com.example.specialeffectsandroid.waterwave;

import com.example.specialeffectsandroid.Diary;

import android.app.Activity;
import android.os.Bundle;
import android.util.DisplayMetrics;

public class WaterWaveActivity extends Activity {

	private WaterWaveView waterWave;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		waterWave = new WaterWaveView(this);
		setContentView(waterWave);
	}

}
