package com.caohai.scrollandlistview;

import android.content.Context;
import android.support.annotation.Px;
import android.support.v4.view.NestedScrollingChild;
import android.support.v4.view.NestedScrollingChildHelper;
import android.support.v4.view.ViewCompat;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.AbsListView;
import android.widget.ListView;

/**
 * Created by 曹海 on 2017/12/20.
 * QQ：185493676
 */

public class MyListView extends ListView implements AbsListView.OnScrollListener, NestedScrollingChild {
    /**
     * 0没有滑动，1向下滑动，2向上滑动
     */
    private int direction = 0;
    private boolean isScroll = true;
    private float startY = 0;
    private ListViewScroll mListViewScroll;


    NestedScrollingChildHelper nscp;
    int lastY;

    //这两个数组用来接收父容器传过来的参数
    int[] consumed;
    int[] offsetWindow;

    int showHeight;

    public MyListView(Context context) {
        super(context);
        init();
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //this.setOnScrollListener(this);
    }

//    @Override
//    public boolean dispatchTouchEvent(MotionEvent ev) {
//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_DOWN:
//                startY = ev.getY();
//                getParent().requestDisallowInterceptTouchEvent(true);
//                break;
//            case MotionEvent.ACTION_MOVE:
//                float dy = ev.getY();
//                if (dy - startY > 0) {
//                    direction = 1;
//                } else {
//                    direction = 2;
//                }
//                if (mListViewScroll != null) {
//                    mListViewScroll.listViewScrolling(direction);
//                }
////                if (isScroll) {
////                    getParent().requestDisallowInterceptTouchEvent(false);
////                }
//                // ((MyScrollView) getParent()).setIntercept(isScroll);
//                startY = dy;
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
//        return super.dispatchTouchEvent(ev);
//    }

    @Override
    public void onScrollStateChanged(AbsListView view, int scrollState) {

    }

    @Override
    public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
        if (firstVisibleItem == 0) {
            if (direction == 1) {
                isScroll = false;
            } else if (direction == 2) {
                isScroll = true;
            } else {
                // isScroll = true;
            }
        } else if ((firstVisibleItem + visibleItemCount) == totalItemCount) {
            if (direction == 1) {
                isScroll = true;
            } else if (direction == 2) {
                isScroll = false;
            } else {
                // isScroll = true;
            }
        } else {
            isScroll = true;
        }
    }

    public void setListViewScroll(ListViewScroll listViewScroll) {
        mListViewScroll = listViewScroll;
    }

    interface ListViewScroll {
        public void listViewScrolling(int derection);
    }


    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                lastY = (int) event.getRawY();
                break;

            case MotionEvent.ACTION_MOVE:
                int y = (int) event.getRawY();
                int dy = y - lastY;
                lastY = y;

                //开启NestedScrolling机制，如果找到了匹配的父容器，那么就与父容器配合消费掉滑动距离
                if (startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL)) {
                    //dy是我们传过去的滑动的距离，父容器可以根据逻辑来选择要不要消费，消费多少
                    boolean isFu = dispatchNestedPreScroll(0, dy, consumed, offsetWindow);
                    if (!isFu) {
                        scrollBy(0, -dy);
                    }
                }

                break;

            case MotionEvent.ACTION_UP:
                break;
        }

        return true;
    }

    //scrollBy内部调用scrollTo,我们不能滑出去，也不能滑的太下面，我们要修正这些情况
    @Override
    public void scrollTo(@Px int x, @Px int y) {
        int maxY = getMeasuredHeight() - showHeight;
        if (y > maxY) {
            y = maxY;
        } else if (y < 0) {
            y = 0;
        }
        super.scrollTo(x, y);
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
