package com.caohai.myapplication;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v4.view.NestedScrollingParent;
import android.support.v4.view.NestedScrollingParentHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by 曹海 on 2018/4/23.
 * QQ：185493676
 */

public class CustomLayout extends ViewGroup implements NestedScrollingParent {
    private static final String TAG = "CustomLayout";
    private float startY = 0;
    private boolean isIntercept = true;
    NestedScrollingParentHelper nsp;

    public CustomLayout(Context context) {
        super(context);
        init();
    }

    public CustomLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        init();
    }

    public CustomLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        //创建一个Helper类
        nsp = new NestedScrollingParentHelper(this);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float mY = event.getY();
                float dy = startY - mY;
                if (getScrollY() >= 0) {
                    isIntercept = false;
                    scrollTo(0, 0);
                } else {
                    isIntercept = true;
                }
                if ((getScrollY() == 0 && dy < 0) || getScrollY() < 0) {
                    scrollBy(0, (int) dy);
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

    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        //参数里target是实现了NestedScrolling机制的子元素，这个子元素可以不是父容器的直接子元素
        //child是包含了target的View，这个View是父容器的直接子元素
        if (target instanceof ChildCustomLayout) {
            return true;
        }

        return false;
    }

    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {
        //dy是子View传过来的，来询问父容器是不是要消费他，要的话，就把dy放进consumed数组,表示我消费了
        //其中consumed数组，consumed[0]表示x方向的距离，consumed[1]表示y方向的距离
        //if (showImg(dy) || hideImg(dy)/*这里根据业务逻辑来判断*/) {
        Log.i("mytag", "onNestedPreScroll:" + dy);
        scrollBy(0, (int) (dy / 100000.0));
        consumed[1] = dy / 100000;
        //}
    }

//    private boolean hideImg(int dy) {
//        //上拉的时候，判断是不是要隐藏图片
//        if (dy < 0) {
//            if (getScrollY() < ivHeight) {
//                //判断只要上移的部分，没有超过ImageView，那么就让父容器继续滑动
//                return true;
//            }
//        }
//
//        return false;
//    }

//    private boolean showImg(int dy) {
//        //下拉的时候，判断是不是要显示图片
//        if (dy > 0) {
//            if (nsc.getScrollY() == 0) {
//                return true;
//            }
//        }
//
//        return false;
//    }

//    //scrollBy内部调用scrollTo,我们父容器不能滑出去，也不能滑的太下面，我们要修正这些情况
//    @Override
//    public void scrollTo(@Px int x, @Px int y) {
//        if (y > ivHeight) {
//            y = ivHeight;
//        } else if (y < 0) {
//            y = 0;
//        }
//
//        super.scrollTo(x, y);
//    }

    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        nsp.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        nsp.onStopNestedScroll(target);
    }

    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        return false;
    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return 0;
    }

}
