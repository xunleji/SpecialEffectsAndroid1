package com.example.specialeffectsandroid.souhutab.view;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.example.specialeffectsandroid.R;
import com.example.specialeffectsandroid.souhutab.LvAdapter;

public class OneView extends RelativeLayout {

	private Context context;
	private View oneView, load;
	private ListView lv;
	private LvAdapter lvAdapter;
	private RelativeLayout rl;
	private int totalpage = 50;
	private int curpage = 1;

	public OneView(Context context) {
		super(context);
		this.context = context;
		init();
	}

	private void init() {
		oneView = LayoutInflater.from(context)
				.inflate(R.layout.souhutab1, null);
		oneView.setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.FILL_PARENT));
		oneView.setOnClickListener(null);
		addView(oneView);
		rl = (RelativeLayout) oneView.findViewById(R.id.rl);
		load = LayoutInflater.from(context).inflate(R.layout.loadview, null);
		lv = (ListView) oneView.findViewById(R.id.news_list);
		lv.setOnScrollListener(new OnScrollListener() {

			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				if (view.getLastVisiblePosition() == (curpage - 1)
						&& scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
					if (curpage + 10 < totalpage) {
						curpage += 10;
						lvAdapter.updata(curpage, false);
					} else {
						if (curpage >= totalpage) {
							Toast.makeText(context, "已经是最后一页", 200).show();
							return;
						} else {
							curpage += 9;
							lvAdapter.updata(curpage, true);
						}
					}
				}
			}

			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
			}
		});
		lvAdapter = new LvAdapter(context);
		lv.setAdapter(lvAdapter);
	}

	public void initdata() {
		rl.removeView(load);
		rl.addView(load);
		new Thread(new Runnable() {

			@Override
			public void run() {
				try {
					Thread.sleep(500);
					rl.post(new Runnable() {

						@Override
						public void run() {
							curpage = 11;
							lvAdapter.updata(curpage, false);
							rl.removeView(load);
						}
					});
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

}
