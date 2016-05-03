package com.example.specialeffectsandroid.refreshlist;

import com.example.specialeffectsandroid.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter {

	private Context context;
	private int size = 0;
	public ListAdapter(Context context) {
		super();
		this.context = context;
		size = 5;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return size;
	}

	@Override
	public Object getItem(int position) {
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
		TextView tv;
		if(convertView==null){
			convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);
			tv = (TextView)convertView.findViewById(R.id.tv);
			convertView.setTag(tv);
		}else{
			tv = (TextView)convertView.getTag();
		}
		tv.setText("louiskoo"+position);
		return convertView;
	}
	
	public void updatelv(int sizes){
		this.size = sizes;
		this.notifyDataSetChanged();
	}

}
