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

public class FourView extends RelativeLayout {

	private Context context;
	private View fourView, load;
	private ListView lv;
	private LvAdapter lvAdapter;
	private RelativeLayout rl;

	public FourView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	private void init() {
		fourView = LayoutInflater.from(context).inflate(R.layout.souhutab4,
				null);
		fourView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		fourView.setOnClickListener(null);
		addView(fourView);
		rl = (RelativeLayout) fourView.findViewById(R.id.rl4);
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
