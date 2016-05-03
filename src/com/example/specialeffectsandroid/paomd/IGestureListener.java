
package com.example.specialeffectsandroid.paomd;

import android.view.MotionEvent;
import android.view.GestureDetector.OnGestureListener;

public class IGestureListener implements OnGestureListener {

    private TouchFlingActionListener onListener;

    public IGestureListener(TouchFlingActionListener listener) {
        onListener = listener;
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        if (e1.getX() - e2.getX() > 5) {
            onListener.previous();
        } else if (e1.getX() - e2.getX() < -5) {
            onListener.next();
        }
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        if (e1 != null && e2 != null) {
            if (e1.getX() - e2.getX() > 50) {
                onListener.previous();
            } else if (e1.getX() - e2.getX() < -50) {
                onListener.next();
            }
        }
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        onListener.startActivity();
        return false;
    }

    public interface TouchFlingActionListener {
        public void next();

        public void previous();

        public void startActivity();
    }
}
