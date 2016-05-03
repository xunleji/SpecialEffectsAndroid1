
package com.example.specialeffectsandroid.clipdrawables;

import java.util.Timer;
import java.util.TimerTask;

import com.example.specialeffectsandroid.R;

import android.app.Activity;
import android.graphics.drawable.ClipDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;

public class ClipDrawableViewActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clip_drawable_view);
        ImageView iv = (ImageView) findViewById(R.id.img_shanshui);
        final ClipDrawable cd = (ClipDrawable) iv.getDrawable();
        final Handler handler = new Handler() {

            @Override
            public void handleMessage(Message msg) {
                if (msg.what == 0x1233) {
                    cd.setLevel(cd.getLevel() + 200);
                }
            }
        };

        final Timer timer = new Timer();
        timer.schedule(new TimerTask() {

            @Override
            public void run() {
                Message msg = new Message();
                msg.what = 0x1233;
                handler.sendMessage(msg);
                if (cd.getLevel() >= 10000) {
                    timer.cancel();
                }
            }
        }, 0, 300);
    }
}
