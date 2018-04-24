package com.caohai.demoapplication;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.hitomi.cslibrary.CrazyShadow;
import com.hitomi.cslibrary.base.CrazyShadowDirection;

public class MainActivity extends AppCompatActivity {
    private MyViewGroup mMyViewGroup;
    private Button mButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mMyViewGroup = (MyViewGroup) findViewById(R.id.my_view_group);
        mButton = (Button) findViewById(R.id.button);
        new CrazyShadow.Builder()
                .setContext(this)
                .setDirection(CrazyShadowDirection.ALL)
                .setShadowRadius(20)
                .setCorner(20)
                .setBackground(Color.parseColor("#008800"))
                .setImpl(CrazyShadow.IMPL_FLOAT)
                .setBaseShadowColor(Color.parseColor("#008800"))
                .setDirection(CrazyShadowDirection.BOTTOM)
                .action(mButton);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mMyViewGroup.setScrollerStart(-600);
                startActivity(new Intent(MainActivity.this, TwoActivity.class));
            }
        });
    }
}
