package com.example.specialeffectsandroid.biaoqian;

import java.util.ArrayList;
import java.util.TooManyListenersException;

import android.app.Activity;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.specialeffectsandroid.R;

/**
 * ViewPager实现屏幕左右滑动
 * @author xujuan
 *
 */
public class ViewPagerTest extends Activity {

	ViewPager viewPager;
	ArrayList<View> list;
	ViewGroup main,group;
	ImageView imageView;
	ImageView[] imageViews;
	private TextView t1, t2, t3, t4, t5;// 页卡头标
	private int offset = 0;// 动画图片偏移量
	private int currIndex = 0;// 当前页卡编号
	private int bmpW;// 动画图片宽度
	private ImageView cursor;// 动画图片
	LayoutInflater inflater ;
	int screenW ;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);
		inflater = getLayoutInflater();
		main = (ViewGroup)inflater.inflate(R.layout.viewpager, null);
		initTop();
		initImage();
		inintViewPager();
	}
	
	private void initTop(){
		t1 = (TextView)main.findViewById(R.id.text1);
		t2 = (TextView)main.findViewById(R.id.text2);
		t3 = (TextView)main.findViewById(R.id.text3);
		t4 = (TextView)main.findViewById(R.id.text4);
		t5 = (TextView)main.findViewById(R.id.text5);

		t1.setOnClickListener(new MyOnClickListener(0));
		t2.setOnClickListener(new MyOnClickListener(1));
		t3.setOnClickListener(new MyOnClickListener(2));
		t4.setOnClickListener(new MyOnClickListener(3));
		t5.setOnClickListener(new MyOnClickListener(4));
	}
	
	private void initImage(){
		cursor = (ImageView)main.findViewById(R.id.cursor);
		bmpW = BitmapFactory.decodeResource(getResources(), R.drawable.ab).getWidth();// 获取图片宽度
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		screenW = dm.widthPixels;// 获取分辨率宽度
		offset = screenW/5 -bmpW ;// 计算偏移量
//		Matrix matrix = new Matrix();
//		matrix.postTranslate(offset, 0);
//		cursor.setImageMatrix(matrix);// 设置动画初始位置
	}
	
	public void inintViewPager(){	
		list = new ArrayList<View>();
		list.add(inflater.inflate(R.layout.viewpageritem1, null));
		list.add(inflater.inflate(R.layout.viewpageritem2, null));
		list.add(inflater.inflate(R.layout.viewpageritem3, null));
		list.add(inflater.inflate(R.layout.viewpageritem4, null));
		list.add(inflater.inflate(R.layout.viewpageritem5, null));
		
		imageViews = new ImageView[list.size()];
		viewPager = (ViewPager)main.findViewById(R.id.viewPager);
		group = (ViewGroup)main.findViewById(R.id.viewGroup);
		
		for(int i = 0;i<list.size();i++){
			imageView = new ImageView(ViewPagerTest.this);
			imageView.setLayoutParams(new LayoutParams(dip2px(this, 10), dip2px(this, 10)));
			imageView.setPadding(dip2px(this, 10), 0,dip2px(this, 10), 0);
			imageViews[i] = imageView;
			if(i == 0 ){
				imageViews[i].setBackgroundResource(R.drawable.guide_dot_white);
			}else{
				imageViews[i].setBackgroundResource(R.drawable.guide_dot_black);
			}
			group.addView(imageView);
		}
		
		setContentView(main);
		viewPager.setAdapter(new MyAdapter());
		viewPager.setOnPageChangeListener(new MyListener());
	}
	
	class MyAdapter extends PagerAdapter{

		private int count = list.size();
		
		@Override
		public void destroyItem(View arg0, int arg1, Object arg2) {
			// TODO Auto-generated method stub
//			if (arg1 >= count) {
//			    int newPosition = arg1%count;   
//			    arg1 = newPosition;
//			    ((ViewPager)arg0).removeView(list.get(arg1));
//			}
//			if(arg1 <0){
//				arg1 = -arg1;
//			    ((ViewPager)arg0).removeView(list.get(arg1));
//			}
			((ViewPager)arg0).removeView(list.get(arg1));
		}

		@Override
		public void finishUpdate(View arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return list.size();
		}
		
		//实例化
		@Override
		public Object instantiateItem(View arg0, int arg1) {
			// TODO Auto-generated method stub
//			if (arg1 >= list.size()) {
//			    int newPosition = arg1%count;		                
//			    arg1 = newPosition;
//			    count++;
//			}
//			if(arg1 <0){
//			   arg1 = -arg1;
//			   count--;
//			}
//			try {
//				((ViewPager)arg0).addView(list.get(arg1), 0);
//			} catch (Exception e) {}
			((ViewPager) arg0).addView(list.get(arg1));
			return list.get(arg1);

		}
			
		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			// TODO Auto-generated method stub
			return arg0 == arg1;
		}

		@Override
		public void restoreState(Parcelable arg0, ClassLoader arg1) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public Parcelable saveState() {
			// TODO Auto-generated method stub
			return null;
		}

		@Override
		public void startUpdate(View arg0) {
			// TODO Auto-generated method stub
			
		}
		
	}
	
	public class MyOnClickListener implements OnClickListener{

		private int index = 0;
		
		public MyOnClickListener(int i) {
			super();
			index = i;
			// TODO Auto-generated constructor stub
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			viewPager.setCurrentItem(index);
		}
		
	}
	
	class MyListener implements OnPageChangeListener{

//		int one = offset*4+bmpW;
//		int two = one*4;
		
		@Override
		public void onPageScrollStateChanged(int arg0) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int arg0) {
			for(int i = 0;i<imageViews.length;i++){
				imageViews[arg0].setBackgroundResource(R.drawable.guide_dot_white);
				if(arg0!=i){
					imageViews[i].setBackgroundResource(R.drawable.guide_dot_black);
				}
			}
			Animation animation = null;
			switch (arg0) {
			case 0:	
				animation = new TranslateAnimation(offset, 0, 0, 0);
				break;
			case 1:
				animation = new TranslateAnimation(bmpW, screenW/5, 0, 0);
				break;
			case 2:
				animation = new TranslateAnimation(screenW/5, screenW/5*2, 0, 0);
				break;
			case 3:		
				animation = new TranslateAnimation(screenW/5*2, screenW/5*3, 0, 0);
				break;
			case 4:
				animation = new TranslateAnimation(screenW/5*3, screenW/5*4, 0, 0);
				break;
			}
			currIndex = arg0;
			animation.setFillAfter(true);// True:图片停在动画结束位置
			animation.setDuration(300);
			cursor.startAnimation(animation);
			
		}
	}
	
	//px转换成dip    
	private int dip2px(Context context, float dipValue){                      
		final float scale = context.getResources().getDisplayMetrics().density;                      
		return (int)(dipValue * scale + 0.5f);                   
	} 

}
