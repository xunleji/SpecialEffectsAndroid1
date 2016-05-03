
package com.example.specialeffectsandroid.paomd;

import com.example.specialeffectsandroid.R;
import com.hp.hpl.sparta.xpath.PositionEqualsExpr;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.ViewSwitcher.ViewFactory;

public class PaomadengActivity extends Activity {

    private LinearLayout commLayout;
    private ImageSwitcher mSwitcher;
    private CommodityPromotionView mCpv;

    private LinearLayout commLayout2;
    private ImageSwitcher mSwitcher2;
    private CommodityPromotionView mCpv2;
    private Handler handler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.paomaden_view);
        handler = new Handler();
        commLayout = (LinearLayout) this.findViewById(R.id.linearlayout_actives);
        mSwitcher = (ImageSwitcher) this.findViewById(R.id.imageSwitcher);
        mSwitcher.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                final ImageView iv = new ImageView(PaomadengActivity.this);
                iv.setScaleType(ScaleType.FIT_XY);
                int height = getWindowManager().getDefaultDisplay().getHeight();
                iv.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT,
                        height * 15 / 100));
                return iv;
            }
        });
        // 启动跑马灯线程
        mCpv = new CommodityPromotionView(this, mSwitcher, commLayout, 0);

        // =========================================================================

        commLayout2 = (LinearLayout) this.findViewById(R.id.linearlayout_actives2);
        mSwitcher2 = (ImageSwitcher) this.findViewById(R.id.imageSwitcher2);
        mSwitcher2.setFactory(new ViewFactory() {
            @Override
            public View makeView() {
                final ImageView iv = new ImageView(PaomadengActivity.this);
                iv.setScaleType(ScaleType.FIT_XY);
                int height = getWindowManager().getDefaultDisplay().getHeight();
                iv.setLayoutParams(new ImageSwitcher.LayoutParams(LayoutParams.FILL_PARENT,
                        height * 15 / 100));
                return iv;
            }
        });
        // 启动跑马灯线程
        mCpv2 = new CommodityPromotionView(this, mSwitcher2, commLayout2, 1);
        
    }

    @Override
    protected void onResume() {
        super.onResume();
        mCpv.start();
        mCpv2.start();
    }

    @Override
    public void onPause() {
        super.onPause();
        mCpv.stop();
        mCpv2.stop();
    }
    
    /**
     * 统一 post 接口
     */
    public void post(final Runnable action) {
        handler.post(new Runnable() {
            @Override
            public void run() {
                if (isFinishing()) {
                    return;
                }
                action.run();
            }
        });
    }


}
