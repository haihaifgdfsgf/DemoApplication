package com.caohai.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by 曹海 on 2018/4/24.
 * QQ：185493676
 */

public class Bezier extends View {
    private Paint mPaint;
    private Path mPath;

    public Bezier(Context context) {
        super(context);
        init(context);
    }

    public Bezier(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public Bezier(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public Bezier(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context mContext) {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(5);
        //一阶
        mPath = new Path();
        mPath.moveTo(10, 10);
        mPath.lineTo(100, 100);
        //二阶
        mPath.quadTo(150, 0, 200, 100);
        //三阶
        mPath.cubicTo(250, 0, 350, 200, 400, 100);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawPath(mPath, mPaint);
    }
}
