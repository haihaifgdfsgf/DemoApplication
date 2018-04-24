package com.caohai.demoapplication;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;

public class TwoActivity extends Activity {
    private MyHorizontalScrollView mMyHorizontalScrollView;
    private SlideCloseLayout mSlideCloseLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            TwoActivity.this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }//设置状态栏透明
        TwoActivity.this.requestWindowFeature(Window.FEATURE_NO_TITLE); //去掉标题栏
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = TwoActivity.this.getWindow();
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
        setContentView(R.layout.activity_two);
        mMyHorizontalScrollView = (MyHorizontalScrollView) findViewById(R.id.horizontal);
        mSlideCloseLayout = (SlideCloseLayout) findViewById(R.id.SlideCloseLayout);
        mMyHorizontalScrollView.setScrollState(new MyHorizontalScrollView.scrollState() {
            @Override
            public void left() {
                Toast.makeText(TwoActivity.this, "zui+zuo", Toast.LENGTH_SHORT).show();
                mSlideCloseLayout.requestDisallowInterceptTouchEvent(false);
            }
        });

    }
}
