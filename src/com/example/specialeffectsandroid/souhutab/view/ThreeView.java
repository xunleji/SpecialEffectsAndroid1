package com.example.specialeffectsandroid.souhutab.view;

import com.example.specialeffectsandroid.R;
import com.example.specialeffectsandroid.souhutab.LvAdapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.RelativeLayout.LayoutParams;

public class ThreeView extends RelativeLayout {

	private Context context;
	private View threeView, load;
	private ListView lv;
	private LvAdapter lvAdapter;
	private RelativeLayout rl;

	public ThreeView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	private void init() {
		threeView = LayoutInflater.from(context).inflate(R.layout.souhutab3,
				null);
		threeView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		threeView.setOnClickListener(null);
		addView(threeView);
		rl = (RelativeLayout) threeView.findViewById(R.id.rl3);
		load = LayoutInflater.from(context).inflate(R.layout.loadview, null);
	}

	public void initdata() {
		rl.removeView(load);
		rl.addView(load);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(500);
					((Activity) context).runOnUiThread(new Runnable() {

						@Override
						public void run() {
							rl.removeView(load);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
