package com.example.specialeffectsandroid.carousel;

import com.example.specialeffectsandroid.R;
import com.example.specialeffectsandroid.carousel.CarouselAdapter.OnItemClickListener;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class CarouselActivity extends Activity {
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.carousel);
		Carousel carousel = (Carousel) findViewById(R.id.carousel);
		carousel.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(CarouselAdapter<?> parent, View view,
					int position, long id) {
				Toast.makeText(CarouselActivity.this, "Position=" + position,
						Toast.LENGTH_SHORT).show();
			}

		});
	}
}
