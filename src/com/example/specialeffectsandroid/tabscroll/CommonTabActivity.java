package com.example.specialeffectsandroid.tabscroll;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.view.View;

import java.util.ArrayList;
import java.util.Random;

import com.example.specialeffectsandroid.Diary;
import com.example.specialeffectsandroid.R;
import com.example.specialeffectsandroid.cornerimage.round.RoundTextView;
import com.example.specialeffectsandroid.tabscroll.listener.CustomTabEntity;
import com.example.specialeffectsandroid.tabscroll.listener.OnTabSelectListener;

public class CommonTabActivity extends FragmentActivity {

	private ArrayList<Fragment> fragments = new ArrayList<Fragment>();
	private ArrayList<Fragment> fragments2 = new ArrayList<Fragment>();

	private String[] titles = { "首页", "消息", "联系人", "更多" };
	private int[] iconUnselectIds = { R.drawable.tab_home_unselect,
			R.drawable.tab_speech_unselect, R.drawable.tab_contact_unselect,
			R.drawable.tab_more_unselect };
	private int[] iconSelectIds = { R.drawable.tab_home_select,
			R.drawable.tab_speech_select, R.drawable.tab_contact_select,
			R.drawable.tab_more_select };
	private ArrayList<CustomTabEntity> tabs = new ArrayList<CustomTabEntity>();
	private View decorView;
	private CommonTabLayout tl_2;
	private ViewPager vp_2;
	private CommonTabLayout tl_1;
	private CommonTabLayout tl_3;
	private CommonTabLayout tl_4;
	private CommonTabLayout tl_5;
	private CommonTabLayout tl_6;
	private CommonTabLayout tl_7;
	private CommonTabLayout tl_8;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_common_tab);

		for (String title : titles) {
			fragments.add(SimpleCardFragment.getInstance("Switch ViewPager "
					+ title));
			fragments2.add(SimpleCardFragment.getInstance("Switch Fragment "
					+ title));
		}

		for (int i = 0; i < titles.length; i++) {
			tabs.add(new TabEntity(titles[i], iconSelectIds[i],
					iconUnselectIds[i]));
		}

		decorView = getWindow().getDecorView();
		/** with nothing */
		tl_1 = ViewFindUtils.find(decorView, R.id.tl_1);
		vp_2 = ViewFindUtils.find(decorView, R.id.vp_2);
		vp_2.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
		/** with ViewPager */
		tl_2 = ViewFindUtils.find(decorView, R.id.tl_2);
		/** with Fragments */
		tl_3 = ViewFindUtils.find(decorView, R.id.tl_3);
		/** indicator固定宽度 */
		tl_4 = ViewFindUtils.find(decorView, R.id.tl_4);
		/** indicator固定宽度 */
		tl_5 = ViewFindUtils.find(decorView, R.id.tl_5);
		/** indicator矩形圆角 */
		tl_6 = ViewFindUtils.find(decorView, R.id.tl_6);
		/** indicator三角形 */
		tl_7 = ViewFindUtils.find(decorView, R.id.tl_7);
		/** indicator圆角色块 */
		tl_8 = ViewFindUtils.find(decorView, R.id.tl_8);

		tl_1.setTabData(tabs);
		tl_1.setOnTabSelectListener(ontabSelect);

		tl_2.setTabData(tabs);
		tl_2.setOnTabSelectListener(ontabSelect);

		tl_3.setTabData(tabs, getSupportFragmentManager(), R.id.fl_change,
				fragments2);
		tl_3.setOnTabSelectListener(ontabSelect);

		tl_4.setTabData(tabs);
		tl_4.setOnTabSelectListener(ontabSelect);

		tl_5.setTabData(tabs);
		tl_5.setOnTabSelectListener(ontabSelect);

		tl_6.setTabData(tabs);
		tl_6.setOnTabSelectListener(ontabSelect);

		tl_7.setTabData(tabs);
		tl_7.setOnTabSelectListener(ontabSelect);

		tl_8.setTabData(tabs);
		tl_8.setOnTabSelectListener(ontabSelect);

		// 显示未读红点
		tl_1.showDot(2);
		tl_3.showDot(1);
		tl_4.showDot(1);

		// 两位数
		tl_2.showMsg(0, 55);
		tl_2.setMsgMargin(0, -5, 5);

		// 三位数
		tl_2.showMsg(1, 100);
		tl_2.setMsgMargin(1, -5, 5);

		// 设置未读消息红点
		tl_2.showDot(2);
		RoundTextView rtv_2_2 = tl_2.getMsgView(2);
		if (rtv_2_2 != null) {
			rtv_2_2.setWidth(dp2px(7.5f));
		}

		// 设置未读消息背景
		tl_2.showMsg(3, 5);
		tl_2.setMsgMargin(3, 0, 5);
		RoundTextView rtv_2_3 = tl_2.getMsgView(3);
		if (rtv_2_3 != null) {
			rtv_2_3.getDelegate().setBackgroundColor(
					Color.parseColor("#6D8FB0"));
		}
		vp_2.setOnPageChangeListener(new OnPageChangeListener() {

			@Override
			public void onPageSelected(int position) {
				tl_1.setCurrentTab(position);
				tl_2.setCurrentTab(position);
				tl_3.setCurrentTab(position);
				tl_4.setCurrentTab(position);
				tl_5.setCurrentTab(position);
				tl_6.setCurrentTab(position);
				tl_7.setCurrentTab(position);
				tl_8.setCurrentTab(position);
			}

			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {

			}

			@Override
			public void onPageScrollStateChanged(int arg0) {

			}
		});
		tl_1.setCurrentTab(0);
	}

	private class MyPagerAdapter extends FragmentPagerAdapter {
		public MyPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public int getCount() {
			return fragments.size();
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}

		@Override
		public Fragment getItem(int position) {
			return fragments.get(position);
		}
	}

	protected int dp2px(float dp) {
		final float scale = getResources().getDisplayMetrics().density;
		return (int) (dp * scale + 0.5f);
	}

	Random random = new Random();

	private OnTabSelectListener ontabSelect = new OnTabSelectListener() {

		@Override
		public void onTabSelect(int position) {
			Diary.print("position==" + position);
			vp_2.setCurrentItem(position);
		}

		@Override
		public void onTabReselect(int position) {
			if (position == 0) {
				tl_2.showMsg(0, random.nextInt(100) + 1);
			}
		}
	};
}
