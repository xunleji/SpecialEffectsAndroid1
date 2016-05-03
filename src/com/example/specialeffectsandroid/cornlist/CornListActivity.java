package com.example.specialeffectsandroid.cornlist;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.example.specialeffectsandroid.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.SimpleAdapter;

public class CornListActivity extends Activity {

	private ArrayList<HashMap<String, String>> map_list1;
	private CornerListView mListView;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.circle_list);
		
		getDataSource1();
		
		SimpleAdapter adapter1 = new SimpleAdapter(this, map_list1,
				R.layout.simple_list_item_1, new String[] { "item" },
				new int[] { R.id.item_title });
		mListView = (CornerListView) findViewById(R.id.list1);
		mListView.setAdapter(adapter1);
	}
	
	public ArrayList<HashMap<String, String>> getDataSource1() {

		map_list1 = new ArrayList<HashMap<String, String>>();
		HashMap<String, String> map1 = new HashMap<String, String>();
		HashMap<String, String> map2 = new HashMap<String, String>();
		HashMap<String, String> map3 = new HashMap<String, String>();
		HashMap<String, String> map4 = new HashMap<String, String>();

		map1.put("item", "设置1");
		map2.put("item", "设置2");
		map3.put("item", "设置3");
		map4.put("item", "设置4");

		map_list1.add(map1);
		map_list1.add(map2);
		map_list1.add(map3);
		map_list1.add(map4);

		return map_list1;
	}

}
