package com.example.specialeffectsandroid.homelcenterr;

import com.example.specialeffectsandroid.R;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

public class QQmini2Activity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homecenter);
        
        
        HomeLeftLayout leftLayout = (HomeLeftLayout) findViewById(R.id.homeLeft);
        HomeRightLayout rightLayout = (HomeRightLayout) findViewById(R.id.homeRight);
        HomeCenterLayout centerLayout = (HomeCenterLayout) findViewById(R.id.homeCenter);
        
        leftLayout.setVisibility(View.GONE);
        rightLayout.setVisibility(View.GONE);
        centerLayout.setBrotherLayout(leftLayout, rightLayout);
    }
}