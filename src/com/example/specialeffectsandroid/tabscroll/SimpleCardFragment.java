package com.example.specialeffectsandroid.tabscroll;

import com.example.specialeffectsandroid.R;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

public class SimpleCardFragment extends Fragment {
	private String title;

	public static SimpleCardFragment getInstance(String title) {
		SimpleCardFragment sf = new SimpleCardFragment();
		sf.title = title;
		return sf;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View v = inflater.inflate(R.layout.fr_simple_card, null);
		TextView card_title_tv = (TextView) v.findViewById(R.id.card_title_tv);
		card_title_tv.setText(title);
		return v;
	}
}