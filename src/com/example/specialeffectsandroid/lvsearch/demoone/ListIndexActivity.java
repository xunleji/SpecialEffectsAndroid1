package com.example.specialeffectsandroid.lvsearch.demoone;

import java.io.File;
import java.util.ArrayList;

import android.app.Activity;
import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.ListView;
import android.widget.TextView;

import com.example.specialeffectsandroid.Diary;
import com.example.specialeffectsandroid.R;

/**
 * 列表索引View
 * 
 * @author xujuan
 * 
 */
public class ListIndexActivity extends Activity {

	private ListView listView;
	private IndexView indexView;
	/**
	 * 列表数据
	 */
	ArrayList<User> itemArray;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_listindex_main);
		initView();
		showFile();
	}

	private void showFile() {
		new Thread(new Runnable() {

			@Override
			public void run() {
				File file = new File("mnt/sdcard/");
				Diary.print("filelen=" + file.length());
				getFile(file);

			}
		}).start();
	}

	private void getFile(File file) {
		File[] files = file.listFiles();
		for (File f : files) {
			if (f.isDirectory()) {
				Diary.print("isDirectory=" + f.getName());
				getFile(f);
			} else {
				Diary.print("fname=" + f.getName());
			}
		}
	}

	private void initView() {
		listView = (ListView) findViewById(R.id.listView);
		indexView = (IndexView) findViewById(R.id.index);
		TextView selectIndexView = (TextView) findViewById(R.id.select_index);
		indexView.init(listView, selectIndexView);
		itemArray = getListData();
		ItemAdapter itemAdapter = new ItemAdapter(this, itemArray);
		listView.setAdapter(itemAdapter);

		listView.setOnScrollListener(new AbsListView.OnScrollListener() {
			int position;

			/**
			 * 滚动状态改变时调用
			 */
			@Override
			public void onScrollStateChanged(AbsListView view, int scrollState) {
				// 不滚动时保存当前滚动到的位置
				if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
				}

			}

			/**
			 * 滚动时调用
			 */
			@Override
			public void onScroll(AbsListView view, int firstVisibleItem,
					int visibleItemCount, int totalItemCount) {
				if (null != indexView) {
					position = listView.getFirstVisiblePosition();
					int i = 0;
					for (String type : ItemAdapter.IndexArrar) {
						if (type.equals(((User) listView.getAdapter().getItem(
								position)).getType())) {
							i++;
							break;
						}
						i++;
					}
					indexView.changeTextViewState(i, false);
					System.out.println("位置:" + i);
				}
			}
		});
	}

	private ArrayList<User> getListData() {
		ArrayList<User> arrayList = new ArrayList<User>();
		for (String type : ItemAdapter.IndexArrar)
			for (int i = 0; i < 10; i++) {
				User user = new User();
				user.setType(type);
				user.setName(type + "_item" + i);
				arrayList.add(user);
			}
		return arrayList;
	}

}
