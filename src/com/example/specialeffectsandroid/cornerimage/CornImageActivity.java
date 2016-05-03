package com.example.specialeffectsandroid.cornerimage;

import com.example.specialeffectsandroid.R;
import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.widget.ArrayAdapter;

@SuppressLint("NewApi")
public class CornImageActivity extends FragmentActivity implements
		ActionBar.OnNavigationListener {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);

		getActionBar().setNavigationMode(ActionBar.NAVIGATION_MODE_LIST);

		getActionBar().setListNavigationCallbacks(
				ArrayAdapter.createFromResource(getActionBar()
						.getThemedContext(), R.array.action_list,
						android.R.layout.simple_spinner_dropdown_item), this);
	}

	@Override
	public boolean onNavigationItemSelected(int itemPosition, long itemId) {

		Fragment newFragment;
		switch (itemPosition) {
		default:
		case 0:
			newFragment = RoundedFragment.getInstance(false);
			break;
		case 1:
			newFragment = RoundedFragment.getInstance(true);
			break;
		case 2:
			newFragment = new PicassoFragment();
			break;
		case 3:
			newFragment = new ColorFragment();
			break;
		case 4:
			newFragment = new CircleFragment();
			break;
		}

		getSupportFragmentManager().beginTransaction().replace(android.R.id.content, newFragment);

		return true;
	}
}
