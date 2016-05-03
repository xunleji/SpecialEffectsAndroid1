package com.example.specialeffectsandroid.doubleseekbar;

import com.example.specialeffectsandroid.R;
import com.example.specialeffectsandroid.doubleseekbar.RangeSeekBar.OnRangeSeekBarChangeListener;

import android.app.Activity;
import android.os.Bundle;

public class MainActivity extends Activity {

	private RangeSeekBar<Number> seekBar;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.schedule);
		seekBar = (RangeSeekBar<Number>) findViewById(R.id.betweens);
		seekBar.setOnRangeSeekBarChangeListener(new OnRangeSeekBarChangeListener<Number>() {

			@Override
			public void rangeSeekBarValuesChanged(
					RangeSeekBar<Number> rangeSeekBar, Number minValue,
					Number maxValue) {
				// TODO Auto-generated method stub
				System.out.println("minValue:"+minValue+"   maxValue:"+maxValue);
			}
		});
	}

}
