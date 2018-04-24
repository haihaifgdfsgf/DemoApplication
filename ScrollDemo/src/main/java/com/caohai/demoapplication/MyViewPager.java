package com.caohai.demoapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Scroller;

/**
 * Created by 曹海 on 2017/12/19.
 * QQ：185493676
 */

public class MyViewPager extends ViewGroup {
    private int screeWidth;
    private float startX;
    private int childCount;
    private Scroller mScroller;

    public MyViewPager(Context context) {
        super(context);
        init(context);
    }

    public MyViewPager(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public MyViewPager(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        screeWidth = wm.getDefaultDisplay().getWidth();
        mScroller = new Scroller(mContext);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int count = getChildCount();
        childCount = count;
        View child;
        for (int i = 0; i < count; i++) {
            child = getChildAt(i);
            child.layout(screeWidth * i, t, screeWidth * (i + 1), b);

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int count = getChildCount();
        for (int i = 0; i < count; ++i) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            }
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                break;
            case MotionEvent.ACTION_MOVE:
                float x = event.getX();
                int dx = (int) (startX - x);
                if ((getScrollX() == 0 && dx < 0) || (getScrollX() == screeWidth * (childCount - 1) && dx > 0)) {
                    dx = 0;
                }
                startX = x;
                scrollBy(dx, 0);
                break;
            case MotionEvent.ACTION_UP:
                float upDx = getScrollX() % screeWidth;
                int p = getScrollX() / screeWidth;
                if (Math.abs(upDx) > screeWidth / 2) {
                    // scrollBy(screeWidth * (p + 1) - getScrollX(), 0);
                    mScroller.startScroll(getScrollX(), 0, screeWidth * (p + 1) - getScrollX(), 0, 300);
                    invalidate();
                } else {
                    //  scrollBy(screeWidth * p - getScrollX(), 0);
                    mScroller.startScroll(getScrollX(), 0, screeWidth * p - getScrollX(), 0, 300);
                    invalidate();
                }

                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();
        if (mScroller.computeScrollOffset()) {
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        }
    }
}
