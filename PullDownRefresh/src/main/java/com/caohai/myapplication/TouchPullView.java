package com.caohai.myapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by 曹海 on 2018/4/24.
 * QQ：185493676
 */

public class TouchPullView extends View {
    private Paint circlePaint;
    private int circleRadius;
    private float circlePX, circlePY;
    private float mProgress;
    private float mDragHeight = 350;
    private float maxCircleRadius = 80;

    private Paint pointP;
    private Paint smallP;
    private float smallRadius;

    private float startX, startY;
    private float endX, endY;
    private float controlX, controlY;
    private Paint bezierPaint;
    private Path bezierPath;
    private float tagWidth = 200;
    private float tagAngle = 120;
    private float tagHeight = 8;

    public TouchPullView(Context context) {
        super(context);
        init(context);
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context);
    }

    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context);
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public TouchPullView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        init(context);
    }

    private void init(Context mContext) {
        circleRadius = 60;
        smallRadius = 50;
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#000000"));
        paint.setStyle(Paint.Style.FILL);
        circlePaint = paint;

        Paint s = new Paint(Paint.ANTI_ALIAS_FLAG);
        s.setColor(Color.parseColor("#aa0000"));
        s.setStyle(Paint.Style.FILL);
        smallP = s;
        pointP = new Paint(Paint.ANTI_ALIAS_FLAG);
        pointP.setStyle(Paint.Style.FILL);
        pointP.setColor(Color.parseColor("#ffffff"));
        pointP.setStrokeWidth(5);


        Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#000000"));
        paint.setStyle(Paint.Style.FILL);
        bezierPaint = mPaint;
        bezierPath = new Path();
    }

    /**
     * 尽量不进行运算
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        if (mProgress < 1) {
            canvas.drawPath(bezierPath, bezierPaint);
        }
        canvas.drawPoint(startX, startY, pointP);
        canvas.drawPoint(controlX, controlY, pointP);
        canvas.drawPoint(endX, endY, pointP);
        canvas.drawCircle(circlePX, circlePY, circleRadius, circlePaint);
        canvas.drawCircle(circlePX, circlePY, smallRadius, smallP);

    }

    /**
     * 当大小发生变化时调用
     * 绘制时有依赖控件的宽高的运算放到此方法
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        sizeChange();
    }

    private void sizeChange() {
        circlePX = getWidth() >> 1;
        circlePY = getHeight() - circleRadius;
        float currentAngle = (float) Math.toRadians(getValueLine(0, tagAngle, mProgress));
        startX = getValueLine(0, tagWidth, mProgress);
        startY = 0;
        endY = (float) (circleRadius * Math.cos(currentAngle) + circlePY);
        endX = (float) (circlePX - circleRadius * Math.sin(currentAngle));
        controlY = getValueLine(0, tagHeight, mProgress);
        controlX = (float) (endX - (endY - controlY) / Math.tan(currentAngle));
        bezierPath.reset();
        bezierPath.moveTo(startX, startY);
        bezierPath.quadTo(controlX, controlY, endX, endY);
        bezierPath.lineTo(circlePX + circlePX - endX, endY);
        bezierPath.quadTo(circlePX + circlePX - controlX, controlY, circlePX + circlePX - startX, startY);

    }

    private float getValueLine(float start, float end, float progress) {
        return start + (end - start) * progress;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int minWidth = 2 * circleRadius + getPaddingRight() + getPaddingLeft();
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int widthMeasur = 0;

        int minHeight = (int) (mDragHeight * mProgress) + getPaddingTop() + getPaddingBottom();
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        int heightMeasur = 0;

        if (widthMode == MeasureSpec.EXACTLY) {
            widthMeasur = width;
        } else if (widthMode == MeasureSpec.AT_MOST) {
            widthMeasur = Math.min(width, minWidth);
        } else {
            widthMeasur = minWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            heightMeasur = height;
        } else if (heightMode == MeasureSpec.AT_MOST) {
            heightMeasur = Math.min(height, minHeight);
        } else {
            heightMeasur = minHeight;
        }
        setMeasuredDimension(widthMeasur, heightMeasur);
    }

    public void setProgress(float progress) {
        Log.i("mytag", "progress:" + progress);
        mProgress = progress;
//        circleRadius = (int) (maxCircleRadius * mProgress);
        //请求重新测量
        requestLayout();
    }
}
