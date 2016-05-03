package com.example.specialeffectsandroid.homelcenterr;


import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.WindowManager;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;
import android.widget.Scroller;


public class HomeCenterLayout extends LinearLayout {

	private final static String TAG = "HomeCenterLayout";
	
	public final int MENU_border_Width = 40;
	
    private Scroller mScroller;
    
    private GestureDetector gestureDetector;
    
    private LinearLayout leftLayout, rightLayout, childLayout;

    private Context context;

    private boolean fling;
    
    private boolean mIsBeingDragged = false;
    
    private int mTouchSlop;
    /**
     * Position of the last motion event.
     */
    private float mLastMotionX, mLastMotionY;
    
    /**
     * ID of the active pointer. This is used to retain consistency during
     * drags/flings if multiple pointers are used.
     */
    private int mActivePointerId = INVALID_POINTER;
    
    /**
     * Sentinel value for no current active pointer.
     * Used by {@link #mActivePointerId}.
     */
    private static final int INVALID_POINTER = -1;
    
    int menuWidth = 0;
    int moveWidth = 0;
	
	
	public HomeCenterLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}

	public HomeCenterLayout(Context context) {
		super(context);
		initView(context);
	}


	public Scroller getScroller() {
		return mScroller;
	}

	public void initView(Context context) {
		this.context = context;
		this.menuWidth = MENU_border_Width;
		this.mScroller = new Scroller(context,AnimationUtils.loadInterpolator(context,
				android.R.anim.overshoot_interpolator));
		
        final ViewConfiguration configuration = ViewConfiguration.get(context);
        mTouchSlop = configuration.getScaledTouchSlop();
	}
	
	public void addChildView(View child) {
		this.childLayout.addView(child);
	}
   
	@Override
	protected void onLayout(boolean changed, int left, int top, int right,int bottom) {
	
		super.onLayout(changed, left, top, right, bottom);
//		Log.i(TAG + " onLayout", "on layout  "+changed+" "+left+" "+top+" "+right+" "+bottom);

		for (int i = 0; i < getChildCount(); i++) 
		{
			View child = getChildAt(i);
			child.layout(child.getLeft()+moveWidth,child.getTop(),child.getRight()+moveWidth,child.getBottom());
		}

	}
	        
	@Override
	public void computeScroll() {
		if (mScroller.computeScrollOffset()) {
           scrollTo(mScroller.getCurrX(), 0);
           postInvalidate();
		}
	}

    @Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		Log.i(TAG, "onInterceptTouchEvent------>"+ev.getAction());
        final int action = ev.getAction();
        if ((action == MotionEvent.ACTION_MOVE) && (mIsBeingDragged)) {
            return true;//拦截不传递给child view
        }
    	
        switch (action & MotionEvent.ACTION_MASK) {
        case MotionEvent.ACTION_DOWN: {
        	final float x = ev.getX();
        	final float y = ev.getY();
            if (!inChild((int)x, (int) y)) {
                mIsBeingDragged = false;
                break;
                //超出边界,return false传递给子view处理
            }

            /*
             * Remember location of down touch.
             * ACTION_DOWN always refers to pointer index 0.
             */
            mLastMotionX = x;
            mLastMotionY = y;
            mActivePointerId = ev.getPointerId(0);

            /*
            * If being flinged and user touches the screen, initiate drag;
            * otherwise don't.  mScroller.isFinished should be false when
            * being flinged.
            */
            mIsBeingDragged = !mScroller.isFinished();
            break;
        }
        case MotionEvent.ACTION_MOVE: {
            /*
             * mIsBeingDragged == false, otherwise the shortcut would have caught it. Check
             * whether the user has moved far enough from his original down touch.
             */

            /*
            * Locally do absolute value. mLastMotionY is set to the y value
            * of the down event.
            */
            final int activePointerId = mActivePointerId;
            if (activePointerId == INVALID_POINTER) {
                // If we don't have a valid id, the touch down wasn't on content.
                break;
            }

            final int pointerIndex = ev.findPointerIndex(activePointerId);
            final float x = ev.getX(pointerIndex);
            final float y = ev.getY(pointerIndex);
            final int xDiff = (int) Math.abs(x - mLastMotionX);
            final int yDiff = (int) Math.abs(y - mLastMotionY);
            if (xDiff > mTouchSlop && yDiff < xDiff) {
                mIsBeingDragged = true;
                //mLastMotionX = x;
                //mLastMotionY = y;
            }
            break;
        }
        case MotionEvent.ACTION_CANCEL:
        case MotionEvent.ACTION_UP:
            mIsBeingDragged = false;
            mActivePointerId = INVALID_POINTER;
            snapToDestination();
            break;
        }
        return mIsBeingDragged;
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {

		Log.i(TAG, "onTouchEvent ---->>>>>"+event.getAction());
        if (event.getAction() == MotionEvent.ACTION_DOWN && 
        		!inChild((int)event.getX(), (int)event.getY())) {
            // Don't handle edge touches immediately -- they may actually belong to one of our
            // descendants.
            return false;
        }
        
        switch(event.getAction() & MotionEvent.ACTION_MASK) {
        	case MotionEvent.ACTION_DOWN: {	
        		return true; //本VIEW消化掉
        		//break;
        	}
        	case MotionEvent.ACTION_MOVE: {	
        		/*if(mIsBeingDragged)*/ {
        			final int activePointerIndex = event.findPointerIndex(mActivePointerId);
        			
        			final float x = event.getX(activePointerIndex);
    	        	final float y = event.getY(activePointerIndex);
    
    	        	final int distanceX = (int) /*Math.abs*/-(x - mLastMotionX);

            		if( distanceX < 0 && getScrollX() < 0 && leftLayout != null) {
            			setBrotherVisibility(true);
                   }
            		else if(distanceX > 0 && getScrollX() > 0 && rightLayout != null) {
            			setBrotherVisibility(false);
            		}
            		else {
            		}
            		

            		scrollBy((int) distanceX, 0);
    	        	
                    mLastMotionX = x;
                    mLastMotionY = y;
        		}
        		break;
        	}
        	case MotionEvent.ACTION_UP: {
                mIsBeingDragged = false;
                mActivePointerId = INVALID_POINTER;
                snapToDestination();
        		break;
        	}
        	default:
        		return super.onTouchEvent(event);
        }
        return mIsBeingDragged;
	
	}
   
	@Override
	protected void onScrollChanged(int l, int t, int oldl, int oldt) {
		// TODO Auto-generated method stub
		super.onScrollChanged(l, t, oldl, oldt);
	}

	public void scrollToScreen() {
/*		if (getFocusedChild() != null && whichScreen != currentScreenIndex
		                && getFocusedChild() == getChildAt(currentScreenIndex)) {
		        getFocusedChild().clearFocus();
		}*/
		
		int scrollDistance = 0;//getWidth()*(getChildCount()-whichScreen) - getScrollX();
		

		if(Math.abs(getScrollX()) > getWidth()/2)
			scrollDistance = (getScrollX() > 0) ? getWidth()-menuWidth-getScrollX() : -(getWidth()-menuWidth-Math.abs(getScrollX()));
		else
			scrollDistance = -getScrollX();
					

//		Log.i(TAG, " scrollDistance = "+scrollDistance);
		mScroller.startScroll(getScrollX(), 0, scrollDistance, 0, Math.abs(scrollDistance) * 2);
		invalidate();
		
   }
       


	public boolean onFling(MotionEvent e1, MotionEvent e2,
			float velocityX, float velocityY) {
//		Log.d(TAG, "on onFling>>>");
		if (Math.abs(velocityX) > ViewConfiguration.get(context).getScaledMinimumFlingVelocity()) 
		{
			fling = true;
			snapToDestination();
		}
  
		return true;
	}
	
   private void snapToDestination() {
//       Log.i(TAG+" snapToDestination", "getScrollX() = "+getScrollX() + " getWidth()="+getWidth());
       scrollToScreen();
   }
   
   private boolean inChild(int x, int y) {
       if (getChildCount() > 0) {
           final int scrollX = mScroller.getCurrX();
           final View child = getChildAt(0);

           return !(scrollX + x < 0 
        		   || scrollX + x > getWidth()
        		   || y < 0
        		   || y> getHeight());

       }
       return false;
   }


    
   /**
    * @param String类型 "left", "right", "middle"/"other" 忽略大小写
    * */
	public void setSkipToWhichPage(String whichpg) {
	   int targetX = 0, moveDistance = 0;

	   if(whichpg.equalsIgnoreCase("left")) {
		   targetX = -(getViewWidthInPix(context) - menuWidth);
		   setBrotherVisibility(true);
	   }
	   else if(whichpg.equalsIgnoreCase("right")) {
		   targetX = getViewWidthInPix(context) - menuWidth;
		   setBrotherVisibility(false);
	   }
	   else 
		   ;
	   
	   moveDistance = targetX - getScrollX();
	   Log.i(VIEW_LOG_TAG, "targetX="+targetX+",moveDistance="+moveDistance);
	   mScroller.startScroll(getScrollX(), 0, moveDistance, 0, 0);
	   invalidate();
    }
	
	private static int viewWidthInPix = -1;
	private int getViewWidthInPix(Context context) {
		if(viewWidthInPix == -1 ) {
			WindowManager manager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
			viewWidthInPix = manager.getDefaultDisplay().getWidth();
		}
		return viewWidthInPix;
	}
	public void setBrotherLayout(LinearLayout left, LinearLayout right) {
		this.leftLayout = left;
		this.rightLayout = right;
	}
	
	private void setBrotherVisibility(boolean leftSide) {
        if(leftSide) {
            rightLayout.setVisibility(View.GONE);
            leftLayout.setVisibility(View.VISIBLE);
        }
        else {
            rightLayout.setVisibility(View.VISIBLE);
            leftLayout.setVisibility(View.GONE);
        }
	}



}
