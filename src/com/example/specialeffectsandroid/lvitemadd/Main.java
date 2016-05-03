package com.example.specialeffectsandroid.lvitemadd;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import com.example.specialeffectsandroid.R;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;

public class Main extends FragmentActivity {
	ListView lv;

	@SuppressLint("NewApi")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		setContentView(R.layout.fragment_stack);
		lv = (ListView) findViewById(R.id.lvMain);
		lv.setOverScrollMode(View.OVER_SCROLL_NEVER);
		ArrayList<SampleItem> list = new ArrayList<SampleItem>();
		SampleItem info;
		for (int i = 0; i < 50; i++) {
			info = new SampleItem(i + "");
			list.add(info);
		}
		SampleAdapter adapter = new SampleAdapter(this, list);

		lv.setAdapter(adapter);
	}

	private class SampleItem {
		public String tag;

		public SampleItem(String tag) {
			this.tag = tag;
		}
	}

	public class SampleAdapter extends BaseAdapter {
		private LayoutInflater mLayoutInflater;
		private ArrayList<SampleItem> list;
		private HashMap<Integer, SlidingLayout> slidingMap;
		private int opendPosition = -1;

		public SampleAdapter(Context context, ArrayList<SampleItem> list) {
			mLayoutInflater = LayoutInflater.from(context);
			this.list = list;
			slidingMap = new HashMap<Integer, SlidingLayout>();
		}

		@Override
		public int getCount() {
			return list.size();
		}

		@Override
		public Object getItem(int position) {
			return list.get(position);
		}

		@Override
		public long getItemId(int position) {
			return position;
		}

		public View getView(final int position, View convertView,
				ViewGroup parent) {
			ViewHolder mHolder = null;
			if (convertView == null) {
				convertView = this.mLayoutInflater.inflate(R.layout.row, null);
				mHolder = new ViewHolder();
				mHolder.btn = (Button) convertView.findViewById(R.id.btn);
				mHolder.layout = (SlidingLayout) convertView
						.findViewById(R.id.layout);
				convertView.setTag(mHolder);

			} else {
				mHolder = (ViewHolder) convertView.getTag();
			}

			final SampleItem info = list.get(position);
			slidingMap.put(position, mHolder.layout);
			mHolder.btn.setText("click_" + info.tag);
			mHolder.btn.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Set<Integer> keySet = slidingMap.keySet();
					for (Integer i : keySet) {
						if (opendPosition != position) {
							slidingMap.get(i).setDefault();
						}
					}
					if (slidingMap.get(position).toggle()) {
						opendPosition = position;
					} else {
						opendPosition = -1;
					}

				}
			});
			// 滑动的时候重新生成的view 保持打开的状态。
			if (opendPosition == position) {
				mHolder.layout.open();
			} else {
				mHolder.layout.setDefault();
			}

			return convertView;
		}

		public class ViewHolder {
			Button btn;
			SlidingLayout layout;
		}

	}

}
