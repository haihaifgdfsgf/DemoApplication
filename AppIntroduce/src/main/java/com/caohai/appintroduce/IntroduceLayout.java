package com.caohai.appintroduce;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;

/**
 * Created by 曹海 on 2017/12/26.
 * QQ：185493676
 */

public class IntroduceLayout extends LinearLayout implements NestedScrollingParent {
    private NestedScrollingParentHelper mParentHelper;
    private LinearLayout headLayout;
    private int headHeight;

    public IntroduceLayout(Context context) {
        super(context);
        init();
    }

    public IntroduceLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public IntroduceLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mParentHelper = new NestedScrollingParentHelper(this);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        headLayout = (LinearLayout) getChildAt(0);

        //拿到ImageView的高度
        headLayout.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (headHeight <= 0) {
                    headHeight = headLayout.getMeasuredHeight();
                }
            }
        });

    }

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        return (nestedScrollAxes & ViewCompat.SCROLL_AXIS_VERTICAL) != 0;
    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int axes) {
        mParentHelper.onNestedScrollAccepted(child, target, axes);
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
//        //if (showHead(dy) || hideHead(dy)/*这里根据业务逻辑来判断*/) {
//        Log.i("caohaimm", "dy:" + dy);
//        scrollBy(0, -dy);
//        consumed[1] = dy;
//        // }
        boolean hiddenTop = dy > 0 && getScrollY() < headHeight;
        boolean showTop = dy < 0 && getScrollY() > 0 && !ViewCompat.canScrollVertically(target, -1);
        if (hiddenTop || showTop) {
            if ((getScrollY() + dy) < 0) {
                dy = 0;//Math.abs(headHeight) - Math.abs(getScrollY());
            }
            if (getScrollY() + dy > headHeight) {
                //dy = getScrollY() - headHeight;
                scrollTo(0, headHeight);
            } else {
                scrollBy(0, dy);
            }

            consumed[1] = dy;
        }
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public void onStopNestedScroll(View child) {
        mParentHelper.onStopNestedScroll(child);
    }

    @Override
    public int getNestedScrollAxes() {
        return mParentHelper.getNestedScrollAxes();
    }

    private boolean hideHead(int dy) {
        //上拉的时候，判断是不是要隐藏图片
        if (dy < 0) {
            if (getScrollY() < headHeight) {
                //判断只要上移的部分，没有超过ImageView，那么就让父容器继续滑动
                return true;
            }
        }

        return false;
    }

    private boolean showHead(int dy) {
        //下拉的时候，判断是不是要显示图片
        if (dy > 0) {
//            if (nsc.getScrollY() == 0) {
//                return true;
//            }
            if (getScrollY() == 0) {
                return false;
            }
            return true;
        }

        return false;
    }
}
