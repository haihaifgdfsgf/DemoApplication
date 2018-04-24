package com.caohai.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 曹海 on 2018/4/23.
 * QQ：185493676
 */

public class ChildCustomLayout extends ViewGroup implements NestedScrollingChild {
    private static final String TAG = "CustomLayout";
    private float startY = 0;
    private boolean isIntercept = true;
    private NestedScrollingChildHelper nscp;
    //这两个数组用来接收父容器传过来的参数
    int[] consumed;
    int[] offsetWindow;

    public ChildCustomLayout(Context context) {
        super(context);
    }

    public ChildCustomLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ChildCustomLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float mY = event.getY();
                float dy = (startY - mY) * 100000;
                if (getScrollY() >= 0) {
                    isIntercept = false;
                    scrollTo(0, 0);
                    //开启NestedScrolling机制，如果找到了匹配的父容器，那么就与父容器配合消费掉滑动距离
                    if (startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)) {
                        //dy是我们传过去的滑动的距离，父容器可以根据逻辑来选择要不要消费，消费多少
                        dispatchNestedPreScroll(0, (int) dy, consumed, offsetWindow);
                        //scrollBy(0, (int) dy / 100000);
                        Log.i("mytag", "onNestedChildScroll:" + dy);

                    }
                } else {
                    isIntercept = true;
                }
                if ((getScrollY() == 0 && dy < 0) || getScrollY() < 0) {
                    scrollBy(0, (int) dy / 100000);

                }
                startY = mY;
                break;
            case MotionEvent.ACTION_UP:
                startY = 0;
                break;
        }
        return true;
    }

    /**
     * 要求所有的孩子测量自己的大小，然后根据这些孩子的大小完成自己的尺寸测量
     */
    @SuppressLint("NewApi")
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 计算出所有的childView的宽和高
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        //测量并保存layout的宽高(使用getDefaultSize时，wrap_content和match_perent都是填充屏幕)
        //稍后会重新写这个方法，能达到wrap_content的效果
        setMeasuredDimension(getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec),
                getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec));
    }

    /**
     * 为所有的子控件摆放位置.
     */
    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        final int count = getChildCount();
        int childMeasureWidth = 0;
        int childMeasureHeight = 0;
        int layoutWidth = 0;    // 容器已经占据的宽度
        int layoutHeight = 0;   // 容器已经占据的宽度
        int maxChildHeight = 0; //一行中子控件最高的高度，用于决定下一行高度应该在目前基础上累加多少
        for (int i = 0; i < count; i++) {
            View child = getChildAt(i);
            //注意此处不能使用getWidth和getHeight，这两个方法必须在onLayout执行完，才能正确获取宽高
            childMeasureWidth = child.getMeasuredWidth();
            childMeasureHeight = child.getMeasuredHeight();
            if (layoutWidth < getWidth()) {
                //如果一行没有排满，继续往右排列
                left = layoutWidth;
                right = left + childMeasureWidth;
                top = layoutHeight;
                bottom = top + childMeasureHeight;
            } else {
                //排满后换行
                layoutWidth = 0;
                layoutHeight += maxChildHeight;
                maxChildHeight = 0;

                left = layoutWidth;
                right = left + childMeasureWidth;
                top = layoutHeight;
                bottom = top + childMeasureHeight;
            }

            layoutWidth += childMeasureWidth;  //宽度累加
            if (childMeasureHeight > maxChildHeight) {
                maxChildHeight = childMeasureHeight;
            }

            //确定子控件的位置，四个参数分别代表（左上右下）点的坐标值
            child.layout(left, top, right, bottom);
        }
    }

    //这里使用单例模式提供Helper，我发现如果没有单例模式，机制就会失效
    //原因我大致的猜到了，但是我还不能具体的表达出来，如果有人知道，请在评论区留下言
    private NestedScrollingChildHelper getNscp() {
        if (nscp == null) {
            nscp = new NestedScrollingChildHelper(this);
            nscp.setNestedScrollingEnabled(true);
            return nscp;
        } else {
            return nscp;
        }
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {
        getNscp().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {
        return getNscp().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {
        return getNscp().startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {
        getNscp().stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {
        return getNscp().hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        Log.i("mytag", "dispatchNestedScrollmmmmmmmmmmmmm");
        return getNscp().dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        return getNscp().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getNscp().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getNscp().dispatchNestedPreFling(velocityX, velocityY);
    }
}
