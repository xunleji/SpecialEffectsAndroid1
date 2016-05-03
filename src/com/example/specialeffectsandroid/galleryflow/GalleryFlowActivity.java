package com.example.specialeffectsandroid.galleryflow;

import com.example.specialeffectsandroid.R;
import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;

public class GalleryFlowActivity extends Activity {

	private GalleryFlow gf;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.galleryflow);
		gf = (GalleryFlow)findViewById(R.id.galley);
		// CoverFlow cf = new CoverFlow(this);
		// cf.setBackgroundResource(R.drawable.shape);
		gf.setAdapter(new ImageAdapter(this));
		// ImageAdapter imageAdapter = new ImageAdapter(this);
		// cf.setAdapter(imageAdapter);
		// cf.setAlphaMode(false);
		// cf.setCircleMode(false);
		gf.setSelection(Integer.MAX_VALUE / 7);
		gf.setFadingEdgeLength(0);
		gf.setCallbackDuringFling(false);
	}

}
