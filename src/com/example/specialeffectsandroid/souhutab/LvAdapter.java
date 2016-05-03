package com.example.specialeffectsandroid.souhutab;

import com.example.specialeffectsandroid.R;

import android.app.Activity;
import android.content.Context;
import android.drm.DrmStore.Action;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class LvAdapter extends BaseAdapter {

	private Context context;
	private int size = 0;
	private boolean islasts;

	public LvAdapter(Context context) {
		super();
		this.context = context;
		// TODO Auto-generated constructor stub
	}

	public void updata(int sizes, boolean islast) {
		this.size = sizes;
		this.islasts = islast;
		Log.e("LvAdapter", "islasts=" + islasts);
		((Activity) context).runOnUiThread(new Runnable() {

			@Override
			public void run() {
				notifyDataSetChanged();
			}
		});
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		View view;
		if (position < size - 1) {
			view = new TextView(context);
			((TextView) view).setText("position==" + position);
			((TextView) view).setTextColor(Color.parseColor("#c0c0c0"));
			((TextView) view).setPadding(0, ToolUntil.dip2px(context, 15), 0,
					ToolUntil.dip2px(context, 15));
			((TextView) view).setTextSize(18);
		} else {
			if(islasts){
				view = new TextView(context);
				((TextView) view).setText("position==" + position);
				((TextView) view).setTextColor(Color.parseColor("#c0c0c0"));
				((TextView) view).setPadding(0, ToolUntil.dip2px(context, 15), 0,
						ToolUntil.dip2px(context, 15));
				((TextView) view).setTextSize(18);
			}else{
				view = LayoutInflater.from(context).inflate(
						R.layout.load_more_item, null);
			}
		}
		return view;
	}

}
