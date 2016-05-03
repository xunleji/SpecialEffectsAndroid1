package com.example.specialeffectsandroid.diffitem;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import com.example.specialeffectsandroid.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

public class TestAndroidActivity extends Activity {

	EditText msgContent;
	ListView listView;
	BaseAdapter adapter;
	ArrayList<Map<String, String>> data = new ArrayList<Map<String, String>>();

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.diffitem);
		initViews();
	}

	private void initViews() {
		msgContent = (EditText) findViewById(R.id.content);
		Button btnSend = (Button) findViewById(R.id.send);
		btnSend.setOnClickListener(onClickListener);
		Button btnReceive = (Button) findViewById(R.id.receive);
		btnReceive.setOnClickListener(onClickListener);
		Button btnPic = (Button) findViewById(R.id.pic);
		btnPic.setOnClickListener(onClickListener);
		listView = (ListView) findViewById(R.id.list);
		adapter = new TestAdapter();
		listView.setAdapter(adapter);
	}

	private OnClickListener onClickListener = new OnClickListener() {

		public void onClick(View v) {
			Map<String, String> item;
			item = new HashMap<String, String>();
			switch (v.getId()) {
			case R.id.pic:
				item.put("type", TYPE_PIC + "");
				item.put("content", "图片");
				break;
			case R.id.send:
				item.put("type", TYPE_SEND + "");
				item.put("content", "发送消息");
				break;
			case R.id.receive:
			default:
				item.put("type", TYPE_RECEIVE + "");
				item.put("content", "收到消息");
				break;
			}
			data.add(item);
			adapter.notifyDataSetChanged();
			msgContent.setText("");
		}
	};

	class TestAdapter extends BaseAdapter {

		public int getCount() {
			return data.size();
		}

		public Object getItem(int position) {
			if (position < getCount()) {
				return data.get(position);
			}
			return null;
		}

		public long getItemId(int position) {

			return 0;
		}

		public View getView(int position, View convertView, ViewGroup parent) {
			System.out.println("getView::" + position);
			int type = TYPE_SEND;
			try {
				type = Integer.parseInt(data.get(position).get("type"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			ViewHolder holder = null;
			if (convertView == null) {
				System.out.println("getView::convertView is null");
				holder = new ViewHolder();
				switch (type) {
				case TYPE_SEND:
					convertView = View.inflate(getBaseContext(),
							R.layout.listitem_send, null);
					holder.text = (TextView) convertView
							.findViewById(R.id.message);
					break;
				case TYPE_RECEIVE:
					convertView = View.inflate(getBaseContext(),
							R.layout.listitem_receive, null);
					holder.text = (TextView) convertView
							.findViewById(R.id.message);
					break;
				case TYPE_PIC:
					convertView = new ImageView(getBaseContext());
					((ImageView) convertView).setImageResource(R.drawable.ic_launcher);
					break;
				}
				convertView.setTag(holder);
			} else {
				System.out.println("getView::convertView not null");
				holder = (ViewHolder) convertView.getTag();
			}

			if (type != TYPE_PIC) {
				String msg = data.get(position).get("content");
				holder.text.setText(msg);
			}
			return convertView;
		}

		public int getItemViewType(int position) {
			int type = super.getItemViewType(position);
			try {
				type = Integer.parseInt(data.get(position).get("type"));
			} catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("getItemViewType::" + position + " is " + type);
			return type;
		}

		public int getViewTypeCount() {
			System.out.println("getViewTypeCount is " + 3);
			return 3;
		}

		class ViewHolder {
			TextView text;
		}
	}

	/**
	 * 发送的消息
	 */
	private static final int TYPE_SEND = 0;
	/**
	 * 收到的消息
	 */
	private static final int TYPE_RECEIVE = TYPE_SEND + 1;
	/**
	 * 图片
	 */
	private static final int TYPE_PIC = TYPE_RECEIVE + 1;

}