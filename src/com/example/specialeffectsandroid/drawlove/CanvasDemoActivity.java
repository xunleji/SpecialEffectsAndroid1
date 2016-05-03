package com.example.specialeffectsandroid.drawlove;

import android.app.Activity;
import android.os.Bundle;

public class CanvasDemoActivity extends Activity {
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(new CanvasLove(this));
    }
}