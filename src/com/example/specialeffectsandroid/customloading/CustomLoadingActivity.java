package com.example.specialeffectsandroid.customloading;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

import com.example.specialeffectsandroid.R;

public class CustomLoadingActivity extends Activity {

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		clipload.stop();
	}

	private ProgressWheel pwOne, pwTwo;
	private PieProgress mPieProgress1, mPieProgress2;
	boolean wheelRunning, pieRunning;
	int wheelProgress = 0, pieProgress = 0;
	private CustomClipLoading clipload;
	private ShapeLoadingDialog shapeLoadingDialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.ctload);

		clipload = (CustomClipLoading) findViewById(R.id.clipid);

		pwOne = (ProgressWheel) findViewById(R.id.progress_bar_one);
		pwOne.spin();
		pwTwo = (ProgressWheel) findViewById(R.id.progress_bar_two);
		new Thread(r).start();

		mPieProgress1 = (PieProgress) findViewById(R.id.pie_progress1);
		mPieProgress2 = (PieProgress) findViewById(R.id.pie_progress2);
		new Thread(indicatorRunnable).start();

		Button startBtn = (Button) findViewById(R.id.btn_start);
		startBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!wheelRunning) {
					wheelProgress = 0;
					pwTwo.resetCount();
					new Thread(r).start();
				}
			}
		});

		Button pieStartBtn = (Button) findViewById(R.id.btn_start2);
		pieStartBtn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (!pieRunning) {
					pieProgress = 0;
					new Thread(indicatorRunnable).start();
				}
			}
		});

		Button Btn = (Button) findViewById(R.id.btn_start3);
		Btn.setOnClickListener(new OnClickListener() {
			public void onClick(View v) {
				if (shapeLoadingDialog != null && shapeLoadingDialog.isShow()) {
					shapeLoadingDialog.dismiss();
				}
				shapeLoadingDialog = new ShapeLoadingDialog(
						CustomLoadingActivity.this);
				shapeLoadingDialog.setLoadingText("加载中...");
				shapeLoadingDialog.show();
			}
		});
	}

	final Runnable r = new Runnable() {
		public void run() {
			wheelRunning = true;
			while (wheelProgress < 361) {
				pwTwo.incrementProgress();
				wheelProgress++;
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			wheelRunning = false;
		}
	};

	final Runnable indicatorRunnable = new Runnable() {
		public void run() {
			pieRunning = true;
			while (pieProgress < 361) {
				mPieProgress1.setProgress(pieProgress);
				mPieProgress2.setProgress(pieProgress);
				pieProgress += 2;
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			pieRunning = false;
		}
	};

}
