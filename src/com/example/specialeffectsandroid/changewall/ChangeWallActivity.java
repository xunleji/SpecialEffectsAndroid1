package com.example.specialeffectsandroid.changewall;

import com.example.specialeffectsandroid.R;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class ChangeWallActivity extends Activity {
	// 定义AlarmManager对象
	AlarmManager aManager;
	Button start, stop;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.changewall);
		start = (Button) findViewById(R.id.start);
		stop = (Button) findViewById(R.id.stop);
		aManager = (AlarmManager) getSystemService(Service.ALARM_SERVICE);
		// 指定启动ChangeService组件
		Intent intent = new Intent(ChangeWallActivity.this, ChangeService.class);
		// 创建PendingIntent对象
		final PendingIntent pi = PendingIntent.getService(
				ChangeWallActivity.this, 0, intent, 0);
		start.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				// 设置每隔5秒执行pi代表的组件一次
				aManager.setRepeating(AlarmManager.RTC_WAKEUP, 0, 5000, pi);
				start.setEnabled(false);
				stop.setEnabled(true);
				Toast.makeText(ChangeWallActivity.this, "壁纸定时更换启动成功啦", 5000)
						.show();
			}
		});
		stop.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				start.setEnabled(true);
				stop.setEnabled(false);
				// 取消对pi的调度
				aManager.cancel(pi);
			}
		});

	}
}
