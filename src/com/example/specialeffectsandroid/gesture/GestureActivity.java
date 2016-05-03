package com.example.specialeffectsandroid.gesture;

import java.util.ArrayList;

import com.example.specialeffectsandroid.R;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.gesture.Gesture;
import android.gesture.GestureLibraries;
import android.gesture.GestureLibrary;
import android.gesture.GestureOverlayView;
import android.gesture.Prediction;
import android.gesture.GestureOverlayView.OnGesturePerformedListener;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioGroup.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class GestureActivity extends Activity {

	private Button btn1, btn2;
	GestureOverlayView gestureView;
	GestureLibrary gestureLibrary;
	
	private RelativeLayout rl;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.gestureview);
		rl = (RelativeLayout)findViewById(R.id.gesture);
		btn1 = (Button) findViewById(R.id.button1);
		btn2 = (Button) findViewById(R.id.button2);
		btn1.setOnClickListener(on);
		btn2.setOnClickListener(on);
	}

	private OnClickListener on = new OnClickListener() {

		@Override
		public void onClick(View v) {
			if (v == btn1) {
				gestureView = new GestureOverlayView(GestureActivity.this);
				// 设置手势的绘制颜色
				gestureView.setGestureColor(Color.RED);
				// 设置手势的绘制宽度
				gestureView.setGestureStrokeWidth(4);
				rl.removeView(gestureView);
				rl.addView(gestureView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
				gestureView
						.addOnGesturePerformedListener(new OnGesturePerformedListener() {
							@Override
							public void onGesturePerformed(
									GestureOverlayView overlay,
									final Gesture gesture) {
								// 加载save.xml界面布局代表的视图
								View saveDialog = getLayoutInflater().inflate(
										R.layout.save, null);
								// 获取saveDialog里的show组件
								ImageView imageView = (ImageView) saveDialog
										.findViewById(R.id.show);
								// 获取saveDialog里的gesture_name组件
								final EditText gestureName = (EditText) saveDialog
										.findViewById(R.id.gesture_name);
								// 根据Gesture包含的手势创建一个位图
								Bitmap bitmap = gesture.toBitmap(128, 128, 10,
										0xFFFF0000);
								imageView.setImageBitmap(bitmap);
								Builder buidler = new AlertDialog.Builder(
										GestureActivity.this);
								buidler.setView(saveDialog);
								buidler.setPositiveButton("保存",
										new DialogInterface.OnClickListener() {

											@Override
											public void onClick(
													DialogInterface dialog,
													int which) {
												// 获取指定文件对应的手势库
												GestureLibrary gestureLib = GestureLibraries
														.fromFile("/sdcard/mygestures");
												// 添加手势
												gestureLib.addGesture(
														gestureName.getText()
																.toString(),
														gesture);
												// 保存手势库
												gestureLib.save();
											}
										});
								buidler.setNegativeButton("取消", null);
								buidler.create().show();
							}
						});
			} else if (v == btn2) {
				gestureView = new GestureOverlayView(GestureActivity.this);
				// 设置手势的绘制颜色
				gestureView.setGestureColor(Color.RED);
				// 设置手势的绘制宽度
				gestureView.setGestureStrokeWidth(4);
				rl.removeView(gestureView);
				rl.addView(gestureView, new LayoutParams(LayoutParams.FILL_PARENT,LayoutParams.FILL_PARENT));
				gestureLibrary = GestureLibraries
						.fromFile("/sdcard/mygestures");
				if (gestureLibrary.load()) {
					Toast.makeText(GestureActivity.this, "手势文件装载成功！", 8000)
							.show();
				} else {
					Toast.makeText(GestureActivity.this, "手势文件装载失败！", 8000)
							.show();
				}
				gestureView
						.addOnGesturePerformedListener(new OnGesturePerformedListener() {
							@Override
							public void onGesturePerformed(
									GestureOverlayView overlay, Gesture gesture) {
								// 识别用户刚刚所绘制的手势
								ArrayList<Prediction> predictions = gestureLibrary
										.recognize(gesture);
								ArrayList<String> result = new ArrayList<String>();
								// 遍历所有找到的Prediction对象
								for (Prediction pred : predictions) {
									// 只有相似度大于2.0的手势才会被输出
									if (pred.score > 10.0) {
										result.add("与手势【" + pred.name + "】相似度为"
												+ pred.score);
									}
								}
								if (result.size() > 0) {
									ArrayAdapter adapter = new ArrayAdapter(
											GestureActivity.this,
											android.R.layout.simple_dropdown_item_1line,
											result.toArray());
									// 使用一个带List的对话框来显示所有匹配的手势
									new AlertDialog.Builder(
											GestureActivity.this)
											.setAdapter(adapter, null)
											.setPositiveButton("确定", null)
											.show();
								} else {
									Toast.makeText(GestureActivity.this,
											"无法找到能匹配的手势！", 8000).show();
								}
							}
						});
			}
		}
	};

}
