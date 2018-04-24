package com.caohai.demoapplication;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Scroller;

/**
 * Created by 曹海 on 2017/12/19.
 * QQ：185493676
 */

public class MyViewGroup extends ViewGroup {
    private Scroller mScroller;

    public MyViewGroup(Context context) {
        super(context);
        mScroller = new Scroller(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs) {
        super(context, attrs);
        mScroller = new Scroller(context);
    }

    public MyViewGroup(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mScroller = new Scroller(context);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        int height = 0;
        int count = getChildCount();
        View child;
        for (int i = 0; i < count; i++) {
            child = getChildAt(i);
            child.layout(0, height, child.getMeasuredWidth(), height + child.getMeasuredHeight());
            height += child.getMeasuredHeight();

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int count = getChildCount();
        for (int i = 0; i < count; ++i) {
            View child = getChildAt(i);
            if (child.getVisibility() != GONE) {
                measureChild(child, widthMeasureSpec, heightMeasureSpec);
            }
        }
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);

    }

    public void setScrollerStart(int dy) {
        mScroller.startScroll(0, 0, 0, dy, 2000);
        invalidate();
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
