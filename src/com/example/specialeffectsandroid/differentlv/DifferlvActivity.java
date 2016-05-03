package com.example.specialeffectsandroid.differentlv;

import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.specialeffectsandroid.R;

public class DifferlvActivity extends Activity {

	private ListView lv_runRank;
	private RunRankAdapter runRankAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.differlv);
		ArrayList<String> runRank = new ArrayList<String>();
		for (int i = 0; i < 500; i++) {
			runRank.add("dsdf");
		}

		runRankAdapter = new RunRankAdapter(this, runRank);
		lv_runRank = (ListView) findViewById(R.id.news_list);
		lv_runRank.setAdapter(runRankAdapter);
	}

}
