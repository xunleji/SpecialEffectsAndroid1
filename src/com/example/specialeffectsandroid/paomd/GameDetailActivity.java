
package com.example.specialeffectsandroid.paomd;

import com.example.specialeffectsandroid.R;

import android.app.Activity;
import android.os.Bundle;
import android.widget.EditText;

public class GameDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.game_detail);
        EditText text = (EditText) findViewById(R.id.index);

        int index = (Integer) getIntent().getExtras().getSerializable("commIndex");
        text.setText("当前打开的是第" + index + "个游戏！");
    }

}
