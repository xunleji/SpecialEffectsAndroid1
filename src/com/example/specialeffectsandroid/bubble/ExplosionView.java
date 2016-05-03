package com.example.specialeffectsandroid.bubble;

import com.example.specialeffectsandroid.LvAdapter;

import android.content.Context;
import android.widget.ImageView;

public class ExplosionView extends ImageView {

	private Context context;

	public ExplosionView(Context context) {
		super(context);
		this.context = context;
	}

	public void setLocation(int top, int left) {
		//设置动画大小
		this.setFrame(left, top, left + LvAdapter.dip2px(context, 40), top + LvAdapter.dip2px(context, 40));
	}

}
