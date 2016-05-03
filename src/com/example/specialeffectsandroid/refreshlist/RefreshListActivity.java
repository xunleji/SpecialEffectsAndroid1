package com.example.specialeffectsandroid.refreshlist;

import com.example.specialeffectsandroid.R;
import com.example.specialeffectsandroid.refreshlist.PullToRefreshBase.OnRefreshListener;

import android.app.Activity;
import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

public class RefreshListActivity extends Activity {

	private PullToRefreshGridView mPullRefreshGridView;
	private ListView home_lv;
	private ListAdapter listAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.refreshlist);
		mPullRefreshGridView = (PullToRefreshGridView)findViewById(R.id.chatlv);
		home_lv = mPullRefreshGridView.getRefreshableView();
		mPullRefreshGridView.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh(int state) {
				if (state == PullToRefreshGridView.MODE_PULL_DOWN_TO_REFRESH) {
					reflush();
				} else {
					//加载更多
					loadMore();
				}
			}
		});
		home_lv.setDividerHeight(0);
//		home_lv.setOverScrollMode(View.OVER_SCROLL_NEVER);
		listAdapter = new ListAdapter(this);
		home_lv.setAdapter(listAdapter);
		home_lv.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int arg2,
					long arg3) {
				Log.e("onItemClick", "arg2="+arg2);
			}
		});
	}
	
	private void reflush(){
		listAdapter.updatelv(5);
		mPullRefreshGridView.onRefreshComplete();
	}

	private void loadMore(){
		listAdapter.updatelv(10);
		mPullRefreshGridView.onRefreshComplete();
	}
}
