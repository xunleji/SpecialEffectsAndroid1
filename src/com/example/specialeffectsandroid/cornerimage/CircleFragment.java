package com.example.specialeffectsandroid.cornerimage;

import com.example.specialeffectsandroid.R;
import com.example.specialeffectsandroid.cornerimage.round.RoundTextView;
import com.example.specialeffectsandroid.cornerimage.round.RoundViewDelegate;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class CircleFragment extends Fragment {

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.cornimage, container, false);
		final RoundTextView rtv_3 = (RoundTextView) view
				.findViewById(R.id.rtv_3);
		rtv_3.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				RoundViewDelegate delegate = rtv_3.getDelegate();
				delegate.setBackgroundColor(
						delegate.getBackgroundColor() == Color
								.parseColor("#ffffff") ? Color
								.parseColor("#F6CE59") : Color
								.parseColor("#ffffff")).update();
			}
		});
		return view;
	}

}
