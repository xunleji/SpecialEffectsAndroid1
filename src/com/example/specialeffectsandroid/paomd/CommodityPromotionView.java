
package com.example.specialeffectsandroid.paomd;

import java.util.ArrayList;
import java.util.HashMap;

import com.example.specialeffectsandroid.R;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageSwitcher;
import android.widget.ImageView;
import android.widget.LinearLayout;

/**
 * 功能描述：跑马灯view
 * 
 * @author
 * @date
 */
public class CommodityPromotionView implements Runnable {

    /** 活动专区图片缓存 **/
    @SuppressLint("UseSparseArrays")
    public static HashMap<Integer, Drawable> pictureCache = new HashMap<Integer, Drawable>();

    /** 活动专区数据模型集合 **/
    private ArrayList<Integer> commercialsList = new ArrayList<Integer>();

    /** 自动切换图片的时间间隔 */
    public static final int TIME_INTERVAL = 3000;

    /** 当前图片指针 */
    public int commIndex = 0;

    /** 数据来源网络 */
    private boolean isOnline = true;

    /** 线程开关 */
    private boolean on_off = true;

    /** down动作 */
    private boolean isDown = false;

    /** up动作唤醒线程 */
    private boolean isDoUp = false;

    /** 手动操作正在进行 */
    private boolean isDoing = false;

    /** 锁定屏幕 */
    private boolean lockTouch;

    /** 切换效果0:3d；1：非3d */
    private int type = 1;

    private ImageSwitcher mSwitcher;
    private LinearLayout commLayout;
    private Thread currentThread;

    private PaomadengActivity context;

    GestureDetector gestureDetector;

    /** 3d Animation */
    private Rotate3d leftAnimation, rightAnimation;

    /** 非3d Animation */
    Animation[] animations = new Animation[4];
    int[] animIds = new int[] {
            R.anim.left_in, R.anim.left_out,
            R.anim.right_in, R.anim.right_out
    };

    /**
     * @param context
     * @param switcher
     * @param layout 图片上面的指示灯layout
     * @param type 切换效果0:3d；1：非3d
     */
    public CommodityPromotionView(PaomadengActivity context, ImageSwitcher switcher,
            LinearLayout layout, int type) {
        this.context = context;
        this.mSwitcher = switcher;
        this.commLayout = layout;
        this.type = type;
        this.currentThread = new Thread(this);
        // 初始化图片数据
        initCommercialsList();
        // 初始化活动专区 控件
        initActivities();
        // 初始化活动专区
        initCommercial();
    }

    private void initCommercialsList() {
        commercialsList.add(R.drawable.lijiang);
        commercialsList.add(R.drawable.shuangta);
        commercialsList.add(R.drawable.shui);
        commercialsList.add(R.drawable.qiao);
        commercialsList.add(null);
    }

    /**
     * 初始化活动专区 控件
     */
    private void initActivities() {

        final Animation.AnimationListener listener = new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                lockTouch = true;// 上锁
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                lockTouch = false;// 解锁
            }
        };
        if (type == 0) {
            int width = context.getWindowManager().getDefaultDisplay().getWidth();
            int centerX = width / 2;// 旋转中心轴
            leftAnimation = new Rotate3d(0, -90, centerX, 0);
            rightAnimation = new Rotate3d(90, 0, centerX, 0);
            leftAnimation.setFillAfter(true);
            leftAnimation.setDuration(500);
            rightAnimation.setFillAfter(true);
            rightAnimation.setDuration(500);
            leftAnimation.setAnimationListener(listener);
            rightAnimation.setAnimationListener(listener);
            mSwitcher.setInAnimation(rightAnimation);
            mSwitcher.setOutAnimation(leftAnimation);
        } else {
            for (int i = 0; i < 4; i++) {
                animations[i] = AnimationUtils.loadAnimation(context, animIds[i]);
                // 这个可以解决滑动的时候花屏（瞬间切换多张图片导致）的问题
                animations[i].setAnimationListener(listener);
            }

        }

        // 活动专区 手势监听实现类
        gestureDetector = new GestureDetector(new IGestureListener(
                new IGestureListener.TouchFlingActionListener() {
                    @Override
                    public void previous() {// 向左滑动
                        doNext(false); // 显示下一张
                    }

                    @Override
                    public void next() {// 向右滑动
                        doPrevious(false);// 显示上一张
                    }

                    @Override
                    public void startActivity() {
                        doStartActivity();
                    }
                }));

    }

    /**
     * 初始化活动专区
     */
    private void initCommercial() {
        ImageView iv = null;
        for (int i = 0; i < size(); i++) {
            iv = new ImageView(context);
            iv.setTag(i);
            commLayout.addView(iv);
        }

        // 必须保留点击监听 否则手势监听OnFilng()监听不到
        mSwitcher.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        });

        mSwitcher.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                boolean flag = false;

                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        isDown = true;
                        isDoing = true;
                        break;
                    case MotionEvent.ACTION_UP:
                        isDown = false;
                        doUp();
                        break;
                    case MotionEvent.ACTION_CANCEL:
                        isDown = false;
                        doUp();
                        break;

                }
                if (!lockTouch) {
                    flag = gestureDetector.onTouchEvent(event);
                }
                return flag;
            }

            private void doUp() {
                isDoUp = true;
                notifyCPV();
            }

        });

        setImage(getCacheDrawable(commIndex));
    }

    /**
     * 启动自动翻页线程
     */
    public void start() {
        on_off = true;
        if (this.currentThread != null && !this.currentThread.isAlive()) {
            this.currentThread.start();
        }
        notifyCPV();
    }

    public void stop() {
        on_off = false;
        switchImage();
    }

    /**
     * 唤醒自动切换线程
     */
    private void notifyCPV() {
        synchronized (currentThread) {
            currentThread.notify();
        }
    }

    private void setImage(Drawable drawable) {
        if (drawable != null) {
            mSwitcher.setImageDrawable(drawable);
        } else {
            if (isOnline) {
                mSwitcher.setImageDrawable(new ExceptionDrawable(context, context
                        .getString(R.string.loading)));
            } else {
                mSwitcher.setImageDrawable(new ExceptionDrawable(context, context
                        .getString(R.string.no_data)));
            }
        }
        // 点亮对应的灯
        showActiveImage();
    }

    /**
     * 从左往右
     */
    public void doPrevious(boolean isAuto) {
        commIndex--;
        if (commIndex < 0) {
            commIndex = size() - 1;
        }
        if (type == 0) {
            leftAnimation.reverseTransformation(true);
            rightAnimation.reverseTransformation(true);
        } else {
            mSwitcher.setInAnimation(animations[2]);
            mSwitcher.setOutAnimation(animations[3]);
        }

        Drawable drawable = getCacheDrawable(commIndex);
        setImage(drawable);

    }

    /**
     * 从右往左
     */
    public void doNext(boolean isAuto) {
        commIndex++;
        // 当前是最后一张图片的时候下一张显示第一张
        if (commIndex > size() - 1) {
            commIndex = 0;
        }
        if (type == 0) {
            leftAnimation.reverseTransformation(false);
            rightAnimation.reverseTransformation(false);
        } else {
            mSwitcher.setInAnimation(animations[0]);
            mSwitcher.setOutAnimation(animations[1]);
        }

        Drawable drawable = getCacheDrawable(commIndex);
        setImage(drawable);

    }

    /**
     * 功能描述：点击图片功能
     * 
     * @param
     * @return
     * @throw
     */
    public void doStartActivity() {
        // 带着参数跳转到对应列表界面
        Intent i = new Intent(context, GameDetailActivity.class);
        Bundle b = new Bundle();
        b.putSerializable("commIndex", commIndex);
        i.putExtras(b);
        context.startActivity(i);
    }

    /**
     * 获取缓存图片
     * 
     * @param key url地址
     * @return Drawable
     */
    public Drawable getCacheDrawable(int key) {
        if (pictureCache == null || commercialsList.get(key) == null) {
            return null;
        }
        if (pictureCache.get(key) == null) {
            pictureCache.put(key, context.getResources().getDrawable(commercialsList.get(key)));
        }
        return pictureCache.get(key);
    }

    public int size() {
        return (commercialsList == null ? 0 : commercialsList.size());
    }

    public boolean isEmpty() {
        return (commercialsList == null);
    }

    @Override
    public void run() {
        try {
            while (true) {
                if (on_off) { // 线程开关
                    synchronized (currentThread) {
                        currentThread.wait(TIME_INTERVAL);// 循环切换时间间隔
                    }
                    if (isDown) {
                        synchronized (currentThread) {
                            currentThread.wait();// 等待下次切换的过程出现down动作
                        }
                    }
                    if (isDoUp) { // doUp唤醒线程的情况下不做事情
                        isDoUp = false;
                        continue;
                    }
                    if (isDoing) { // 手动操作刚结束，暂停一个周期给用户一个缓冲
                        synchronized (currentThread) {
                            currentThread.wait(TIME_INTERVAL);
                        }
                        isDoing = false;
                    }
                    switchImage();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 改变当前选项
     */
    private void switchImage() {
    	context.post(new Runnable() {
            @Override
            public void run() {
                doNext(true); // 自动的是从右往左滑动
            }
        });
    }

    /**
     * 点亮指定的灯
     */
    private void showActiveImage() {
        if (!isEmpty() && commIndex < size()) {
            if (size() > 1) {
                closeAllNight(size());// 关灯
                View v = commLayout.findViewWithTag(commIndex);
                if (v != null) {
                    v.setBackgroundResource(R.drawable.u20_active);// 点亮
                }
            }
        } else {
            mSwitcher.startAnimation(AnimationUtils.loadAnimation(context, R.anim.shake));// 当下标到头的时候
                                                                                          // 播放动画
                                                                                          // 表示
        }
    }

    public void closeAllNight(int size) {
        if (commLayout != null) {
            for (int i = 0; i < size; i++) {
                View v = commLayout.findViewWithTag(i);
                if (v != null) {
                    v.setBackgroundResource(R.drawable.u20_not_active);
                }
            }
        }
    }
}
