package com.example.specialeffectsandroid.homelcenterr;


import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.example.specialeffectsandroid.R;

public class HomeCenterChildLayout extends LinearLayout{

	private ListView lv;
	
	public HomeCenterChildLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initLayout();
	}

	public HomeCenterChildLayout(Context context) {
		super(context);
		initLayout();
	}
	
	private void initLayout() {
		setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));
		View view = LayoutInflater.from(getContext()).inflate(R.layout.hc_child, null);
		lv = (ListView) view.findViewById(R.id.home_CenterLayout_child);
		lv.setCacheColorHint(Color.GRAY);
		ArrayAdapter<String> adapter = new ArrayAdapter<String>(
				getContext(), 
				android.R.layout.simple_expandable_list_item_1,
				getData());
		lv.setAdapter(adapter);
		
		addView(view);
	}
	
	private List<String> getData(){    
		List<String> data = new ArrayList<String>();    
		for(int i=0; i<30; i++) {
			data.add("测试数据"+(i+1)); 
		}

		return data;    
	}  

}
