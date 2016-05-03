
package com.example.specialeffectsandroid.fltext;

import java.util.Timer;
import java.util.TimerTask;

import com.example.specialeffectsandroid.R;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.TextView;


public class FrameLayoutActivity extends Activity {

    private int currentColor = 0;

    TextView[] views = new TextView[7];

    final int[] names = new int[] {
            R.id.text1, R.id.text2, R.id.text3,
            R.id.text4, R.id.text5, R.id.text6, R.id.text7
    };

    final int[] colors = new int[] {
            R.color.color1, R.color.color2,
            R.color.color3, R.color.color4, R.color.color5, R.color.color6,
            R.color.color7
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.frame_layout);
        for (int i = 0; i < 7; i++) {
            views[i] = (TextView) findViewById(names[i]);
        }
        // 系统消息接收器
        final Handler handler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 0x1122) {
                    // 一次改变7个TextView的背景颜色
                    for (int i = 0; i < 7 - currentColor; i++) {
                        views[i].setBackgroundResource(colors[i + currentColor]);
                    }
                    for (int i = 7 - currentColor, j = 0; i < 7; i++, j++) {
                        views[i].setBackgroundResource(colors[j]);
                    }
                }
                super.handleMessage(msg);
            }
        };
        // 定义一个时间程序定时改变currentColor的值
        new Timer().schedule(new TimerTask() {

            @Override
            public void run() {

                currentColor++;
                if (currentColor >= 6) {
                    currentColor = 0;
                }
                // 给系统发送一条消息
                Message msg = new Message();
                msg.what = 0x1122;
                handler.sendMessage(msg);
            }

        }, 0, 100);
    }
}
