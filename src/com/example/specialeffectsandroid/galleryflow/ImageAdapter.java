package com.example.specialeffectsandroid.galleryflow;


import com.example.specialeffectsandroid.R;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;

public class ImageAdapter extends BaseAdapter {

	int mGalleryItemBackground;
	private Context mContext;
	private Integer[] mImageIds = { R.drawable.gtl2, R.drawable.gtl3,
			R.drawable.gtl8, R.drawable.gtl5, R.drawable.gtl6, R.drawable.gtl7,
			R.drawable.gtl9 };

	public ImageAdapter(Context c) {
		mContext = c;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return  Integer.MAX_VALUE;
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position%mImageIds.length;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position%mImageIds.length;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ImageView i = new ImageView(mContext);
		i.setImageBitmap(BitmapFactory.decodeResource(mContext.getResources(),
				mImageIds[position%mImageIds.length]));
		i.setLayoutParams(new GalleryFlow.LayoutParams(dip2px(mContext, 100),
				dip2px(mContext, 120)));
		i.setScaleType(ScaleType.FIT_XY);
		// 设置的抗锯齿
		BitmapDrawable drawable = (BitmapDrawable) i.getDrawable();
		drawable.setAntiAlias(true);
		return i;
	}

	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}
}
