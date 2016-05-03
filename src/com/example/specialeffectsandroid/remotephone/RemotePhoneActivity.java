package com.example.specialeffectsandroid.remotephone;

import java.io.File;
import java.net.InetAddress;
import com.example.specialeffectsandroid.R;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class RemotePhoneActivity extends Activity {

	private TextView tvstatus, tvip, tvtext;
	private ImageView ivwifi;
	private Button btn;
	private Context context;
	protected MyLog myLog = new MyLog(this.getClass().getName());
	public Handler handler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case 0:
				removeMessages(0);
				updateUI();
				break;
			case 1:
				removeMessages(1);
			}
		}
	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.remotephone);
		context = this;
		Context myContext = Globals.getContext();
		if (myContext == null) {
			myContext = getApplicationContext();
			if (myContext == null) {
				throw new NullPointerException("Null context!?!?!?");
			}
			Globals.setContext(myContext);
		}
		tvstatus = (TextView) findViewById(R.id.statuss);
		ivwifi = (ImageView) findViewById(R.id.wifi_state_image);
		ivwifi.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent intent = new Intent(
						android.provider.Settings.ACTION_WIFI_SETTINGS);
				startActivity(intent);
			}
		});
		tvtext = (TextView) findViewById(R.id.statustv);
		tvip = (TextView) findViewById(R.id.statusip);
		btn = (Button) findViewById(R.id.statubtn);
		btn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Globals.setLastError(null);
				File chrootDir = new File(Defaults.chrootDir);
				if (!chrootDir.isDirectory())
					return;
				Intent intent = new Intent(context, FTPServerService.class);
				Globals.setChrootDir(chrootDir);
				if (!FTPServerService.isRunning()) {
					if (Environment.MEDIA_MOUNTED.equals(Environment
							.getExternalStorageState())) {
						context.startService(intent);
					}
				} else {
					context.stopService(intent);
				}
			}
		});
		updateUI();
		UiUpdater.registerClient(handler);
	}

	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		UiUpdater.registerClient(handler);
		updateUI();
	}

	@Override
	protected void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		UiUpdater.registerClient(handler);
		updateUI();
		IntentFilter filter = new IntentFilter();
		filter.addAction(WifiManager.WIFI_STATE_CHANGED_ACTION);
		filter.addAction(ConnectivityManager.CONNECTIVITY_ACTION);
		registerReceiver(wifiReceiver, filter);
	}

	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		UiUpdater.unregisterClient(handler);
		unregisterReceiver(wifiReceiver);
	}

	@Override
	protected void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		UiUpdater.unregisterClient(handler);
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		UiUpdater.unregisterClient(handler);
	}

	private void updateUI() {
		WifiManager wifiMgr = (WifiManager) context
				.getSystemService(Context.WIFI_SERVICE);
		WifiInfo info = wifiMgr.getConnectionInfo();
		String wifiId = info != null ? info.getSSID() : null;
		boolean isWifiReady = FTPServerService.isWifiEnabled();
		tvstatus.setText(isWifiReady ? wifiId
				: getString(R.string.no_wifi_hint));
		ivwifi.setImageResource(isWifiReady ? R.drawable.wifi_state4
				: R.drawable.wifi_state0);
		boolean running = FTPServerService.isRunning();
		if (running) {
			myLog.l(Log.DEBUG, "updateUi: server is running", true);
			InetAddress address = FTPServerService.getWifiIp();
			if (address != null) {
				String port = ":" + FTPServerService.getPort();
				tvip.setText("ftp://" + address.getHostAddress()
						+ (FTPServerService.getPort() == 21 ? "" : port));
			} else {
				Intent intent = new Intent(context, FTPServerService.class);
				context.stopService(intent);
				tvip.setText("");
			}
		}
		if (!isWifiReady) {
			if (FTPServerService.isRunning()) {
				Intent intent = new Intent(context, FTPServerService.class);
				context.stopService(intent);
			}
		}
		tvip.setVisibility(running ? View.VISIBLE : View.INVISIBLE);
		tvtext.setText(running ? getString(R.string.instruction)
				: getString(R.string.instruction_pre));
		btn.setText(running ? getString(R.string.stop_server)
				: getString(R.string.start_server));
	}

	BroadcastReceiver wifiReceiver = new BroadcastReceiver() {
		public void onReceive(Context ctx, Intent intent) {
			myLog.l(Log.DEBUG, "Wifi status broadcast received");
			updateUI();
		}
	};

}
