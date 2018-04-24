package com.caohai.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

public class MainActivity extends AppCompatActivity {
    private float PULL_MAX_HEIGHT = 400;
    private LinearLayout rootView;
    private TouchPullView pullView;
    private float startY;
    

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
                        break;
                }
                return false;
            }
        });
    }


}
