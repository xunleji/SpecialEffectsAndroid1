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

public class TwoView extends RelativeLayout {

	private Context context;
	private View twoView, load;
	private ListView lv;
	private LvAdapter lvAdapter;
	private RelativeLayout rl;

	public TwoView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	private void init() {
		twoView = LayoutInflater.from(context)
				.inflate(R.layout.souhutab2, null);
		twoView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		twoView.setOnClickListener(null);
		addView(twoView);
		rl = (RelativeLayout) twoView.findViewById(R.id.rl2);
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
