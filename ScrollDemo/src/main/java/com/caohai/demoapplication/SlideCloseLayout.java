package com.caohai.demoapplication;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.URLUtil;
import android.widget.Scroller;

/**
 * Created by 曹海 on 2017/12/19.
 * QQ：185493676
 */

public class SlideCloseLayout extends ViewGroup {
    private int screeWidth;
    private float startX;
    private int childCount;
    private Scroller mScroller;
    private Context mContext;
    private boolean isClose = false;

    public SlideCloseLayout(Context context) {
        super(context);
        init(context);
    }

    public SlideCloseLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public SlideCloseLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context mContext) {
        WindowManager wm = (WindowManager) mContext.getSystemService(Context.WINDOW_SERVICE);
        screeWidth = wm.getDefaultDisplay().getWidth();
        mScroller = new Scroller(mContext);
        this.mContext = mContext;
        //scrollBy(screeWidth, 0);
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
                if ((getScrollX() >= 0 && dx > 0)) {
                    dx = 0;
                }
                startX = x;
                //滑动
                scrollBy(dx, 0);
                break;
            case MotionEvent.ACTION_UP:
                //当手指离开屏幕时判断当前内容滑动的位置
                //如果滑动位置小于屏幕宽度的一半，则回到原始状态，反之。
                //通过Scroller类平滑移动
                float upDx = getScrollX();
                if (Math.abs(upDx) > screeWidth / 2) {
                    isClose = true;//滑动到最右边，可以关闭activity
                    mScroller.startScroll(getScrollX(), 0, -(screeWidth - getScrollX()), 0, 300);
                    invalidate();
                } else {
                    mScroller.startScroll(getScrollX(), 0, -getScrollX(), 0, 100);
                    invalidate();
                }

                break;
        }
        return true;
    }

    @Override
    public void computeScroll() {
        super.computeScroll();

        if (mScroller.computeScrollOffset()) {//返回true正在滚动，false滚动完成
            scrollTo(mScroller.getCurrX(), mScroller.getCurrY());
            postInvalidate();
        } else {
            if (isClose) {//true代表滚动到最右边可以关闭activity
                if (mContext != null) {
                    ((Activity) mContext).finish();
                }
            }
        }
    }
}
