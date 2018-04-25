package com.caohai.myapplication;

import android.animation.ValueAnimator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.BounceInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private float PULL_MAX_HEIGHT = 400;
    private LinearLayout rootView;
    private TouchPullView pullView;
    private float startY;
    ValueAnimator animator;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initEvent();
    }

    private void initView() {
        rootView = (LinearLayout) findViewById(R.id.root_view);
        pullView = (TouchPullView) findViewById(R.id.touch_pull_view);
    }

    private void initEvent() {
        rootView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        startY = event.getY();
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        float moveY = event.getY();
                        float dY = moveY - startY;
                        if (dY > 0) {
                            float progress = dY >= PULL_MAX_HEIGHT ? 1 : dY / PULL_MAX_HEIGHT;
                            pullView.setProgress(progress);
                        }
                        return true;
                    default:
                        float smoveY = event.getY();
                        float sdY = smoveY - startY;
                        float progress = sdY >= PULL_MAX_HEIGHT ? 1 : sdY / PULL_MAX_HEIGHT;
                        if (animator != null) {
                            animator.cancel();
                        }
                        animator = ValueAnimator.ofFloat(progress, 0);
                        animator.setDuration(200);
                        animator.setInterpolator(new BounceInterpolator());
                        // animator.setInterpolator(new DecelerateInterpolator());
                        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation) {
                                /**
                                 * 通过这样一个监听事件，我们就可以获取
                                 * 到ValueAnimator每一步所产生的值。
                                 *
                                 * 通过调用getAnimatedValue()获取到每个时间因子所产生的Value。
                                 * */
                                pullView.setProgress((Float) animation.getAnimatedValue());
                            }
                        });
                        animator.start();

                        break;
                }
                return false;
            }
        });
    }


}
