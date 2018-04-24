package com.caohai.demoapplication;


import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;
import android.widget.Toast;

/**
 * Created by 曹海 on 2017/12/19.
 * QQ：185493676
 */

public class MyHorizontalScrollView extends HorizontalScrollView {
    private Context mContext;
    private scrollState mScrollState;
    public boolean isScroll = true;

    public MyHorizontalScrollView(Context context) {
        super(context);
        init(context);
    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    public MyHorizontalScrollView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    private void init(Context context) {
        mContext = context;
        smoothScrollBy(100, 0);
        postDelayed(new Runnable() {
            @Override
            public void run() {
                smoothScrollBy(1, 0);
            }
        }, 100);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        if (isScroll) {
            return super.onTouchEvent(ev);
        } else {
            return false;
        }

    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        int maxScrollX = getChildAt(0).getMeasuredWidth() - getMeasuredWidth();
        //滑到最左
        if (getScrollX() == 0) {
            isScroll = false;
            if (mScrollState != null) {
                mScrollState.left();
            }
        } else if (getScrollX() == maxScrollX) {  //滑到最右

        } else {  //滑到中间

        }
    }

    public void setScrollState(scrollState mScrollState) {
        this.mScrollState = mScrollState;

    }

    public interface scrollState {
        public void left();
    }
}
