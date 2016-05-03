package com.example.specialeffectsandroid.scrollscreen;

import com.example.specialeffectsandroid.R;
import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.widget.TextView;
import android.widget.ViewFlipper;

public class ScrollScreenActivity extends Activity {

	private ViewFlipper viewFlipper;
	private String[] descriptionsArray;
	private String[] titleArray;
	private int selectedPosition;
	private TextView textViewTitle;
	private TextView textViewContent;
	private FriendlyScrollView scroll;
	private LayoutInflater mInflater;
	private GestureDetector gestureDetector;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.common_info_list_view);
		InitUI();
	}

	private void InitUI() {

		viewFlipper = (ViewFlipper) findViewById(R.id.viewflipper_data);
		mInflater = LayoutInflater.from(this);
		fillDate();
		viewFlipper.addView(getContentView());
	}

	private View getContentView() {
		View contentView = new View(this);
		contentView = mInflater.inflate(R.layout.common_info_item_view, null);

		textViewTitle = (TextView) contentView.findViewById(R.id.text_title);
		textViewContent = (TextView) contentView.findViewById(R.id.text_detail);

		textViewTitle.setText(titleArray[selectedPosition]);
		textViewTitle.setPadding(10, 0, 10, 0);
		textViewContent.setText(descriptionsArray[selectedPosition]);
		textViewContent.setPadding(10, 5, 10, 5);

		scroll = (FriendlyScrollView) contentView.findViewById(R.id.scroll);
		scroll.setOnTouchListener(onTouchListener);
		scroll.setGestureDetector(gestureDetector);
		return contentView;
	}

	private View.OnTouchListener onTouchListener = new View.OnTouchListener() {

		public boolean onTouch(View v, MotionEvent event) {
			// TODO Auto-generated method stub
			return gestureDetector.onTouchEvent(event);
		}
	};

	private void fillDate() {
		selectedPosition = 0;
		titleArray = getResources().getStringArray(R.array.title_array);
		descriptionsArray = getResources().getStringArray(
				R.array.description_array);
		gestureDetector = new GestureDetector(new CommonGestureListener());
	}

	public class CommonGestureListener extends SimpleOnGestureListener {

		@Override
		public boolean onDown(MotionEvent e) {
			// TODO Auto-generated method stub
			Log.d("QueryViewFlipper", "====> Jieqi: do onDown...");
			return false;
		}

		@Override
		public void onShowPress(MotionEvent e) {
			// TODO Auto-generated method stub
			Log.d("QueryViewFlipper", "====> Jieqi: do onShowPress...");
			super.onShowPress(e);
		}

		@Override
		public void onLongPress(MotionEvent e) {
			// TODO Auto-generated method stub
			Log.d("QueryViewFlipper", "----> Jieqi: do onLongPress...");
		}

		@Override
		public boolean onSingleTapConfirmed(MotionEvent e) {
			// TODO Auto-generated method stub
			Log.d("QueryViewFlipper", "====> Jieqi: do onSingleTapConfirmed...");
			return false;
		}

		@Override
		public boolean onSingleTapUp(MotionEvent e) {
			// TODO Auto-generated method stub
			Log.d("QueryViewFlipper", "====> Jieqi: do onSingleTapUp...");
			return false;
		}

		@Override
		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
			// TODO Auto-generated method stub
			Log.d("QueryViewFlipper", "====> Jieqi: do onFling...");
			if (e1.getX() - e2.getX() > 100 && Math.abs(velocityX) > 50) {
				// 向左
				selectedPosition = selectedPosition + 1 < titleArray.length ? (selectedPosition + 1)
						: 0;
				viewFlipper.addView(getContentView());
				viewFlipper.setInAnimation(AnimationControl
						.inFromRightAnimation());
				viewFlipper.setOutAnimation(AnimationControl
						.outToLeftAnimation());
				viewFlipper.showNext();
				viewFlipper.removeViewAt(0);
			} else if (e2.getX() - e1.getX() > 100 && Math.abs(velocityX) > 50) {
				// 向右
				selectedPosition = selectedPosition > 0 ? (selectedPosition - 1)
						: (titleArray.length - 1);
				viewFlipper.addView(getContentView());
				viewFlipper.setInAnimation(AnimationControl
						.inFromLeftAnimation());
				viewFlipper.setOutAnimation(AnimationControl
						.outToRightAnimation());
				viewFlipper.showNext();
				viewFlipper.removeViewAt(0);
			}
			return true;
		}

		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			// TODO Auto-generated method stub
			Log.d("QueryViewFlipper", "====> Jieqi: do onScroll...");
			return super.onScroll(e1, e2, distanceX, distanceY);
		}

	}

}
