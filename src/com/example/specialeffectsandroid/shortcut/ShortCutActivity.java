package com.example.specialeffectsandroid.shortcut;

import com.example.specialeffectsandroid.R;
import com.example.specialeffectsandroid.SpecialEffectsAndroid1;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;

public class ShortCutActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		Button btn = new Button(this);
		btn.setLayoutParams(new LayoutParams(200,100));
		setContentView(btn);
		addShortcut();
	}
	
	private void addShortcut(){
		Intent addIntent = new Intent("com.android.launcher.action.INSTALL_SHORTCUT");
		Parcelable icon = 
			Intent.ShortcutIconResource.fromContext(this, R.drawable.cat);//图标
		//创建点击快捷方式后操作Intent,该处当点击创建的快捷方式后，再次启动该程序
		Intent myIntent = new Intent(this, SpecialEffectsAndroid1.class);
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_NAME, "快捷方式");//快捷方式的标题
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_ICON_RESOURCE, icon);//快捷方式的图标
		addIntent.putExtra(Intent.EXTRA_SHORTCUT_INTENT, myIntent);//快捷方式的动作
		sendBroadcast(addIntent);//发送广播
	}

}
