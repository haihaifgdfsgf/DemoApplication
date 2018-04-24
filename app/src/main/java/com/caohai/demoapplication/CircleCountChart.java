package com.caohai.demoapplication;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;
import android.widget.TextView;

/**
 * Created by 曹海 on 2017/12/18.
 * QQ：185493676
 */

public class CircleCountChart extends View {
    private Paint fontPaint;
    private Paint circlePaint;
    private Paint ringPaint;
    private Paint rectPaint;
    private int width;
    private int height;
    private int radius;
    private int arcWidth;
    private int endAngle = 0;
    private Thread mThread = null;
    private float textP = 0.0f;

    public CircleCountChart(Context context) {
        super(context);
        init();
    }

    public CircleCountChart(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CircleCountChart(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        fontPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        fontPaint.setColor(Color.parseColor("#ffffff"));
        fontPaint.setTextSize(30);
        circlePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        circlePaint.setColor(Color.parseColor("#2F4F4F"));
        circlePaint.setStyle(Paint.Style.FILL);
        ringPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        ringPaint.setColor(Color.parseColor("#2F4F4F"));
        ringPaint.setStrokeWidth(10);
        ringPaint.setStyle(Paint.Style.STROKE);
        rectPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        rectPaint.setColor(Color.parseColor("#2F4F4F"));
        rectPaint.setStyle(Paint.Style.STROKE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        width = measureWidth(widthMeasureSpec);
        height = measureHeight(heightMeasureSpec);
        setMeasuredDimension(width, height);
    }

    private int measureHeight(int heightMeasureSpec) {
        int heightResult = 200;
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        if (heightMode == MeasureSpec.EXACTLY) {
            return heightSize;
        } else {
            if (heightMode == MeasureSpec.AT_MOST) {
                heightSize = Math.min(heightResult, heightSize);
            }
            return heightSize;
        }
    }

    private int measureWidth(int widthMeasureSpec) {
        int widthResult = 200;
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            return widthSize;
        } else {
            if (widthMode == MeasureSpec.AT_MOST) {
                widthSize = Math.min(widthResult, widthSize);
            }
            return widthSize;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        // canvas.drawRect(0, 0, width, height, rectPaint);
        radius = width / 4;
        arcWidth = width / 10;
        ringPaint.setStrokeWidth(arcWidth);
        canvas.drawCircle(width / 2, height / 2, radius, circlePaint);
//        circlePaint.setColor(Color.parseColor("#00FFFF"));
//        canvas.drawCircle(width / 2, height / 2, 2, circlePaint);
        float textWidth = fontPaint.measureText(textP + "%");
        float x = width / 2 - textWidth / 2;
        Paint.FontMetrics metrics = fontPaint.getFontMetrics();
        //metrics.ascent为负数
        float dy = -(metrics.descent + metrics.ascent) / 2;
        float y = height / 2 + dy;
        canvas.drawText(textP + "%", x, y, fontPaint);
        RectF arc = new RectF(0 + arcWidth / 2, 0 + arcWidth / 2, width - arcWidth / 2, height - arcWidth / 2);
        canvas.drawArc(arc, -90, endAngle, false, ringPaint);
        if (mThread == null) {
            mThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    while (textP < 75) {
                        textP += 1.5;
                        endAngle = (int) (textP * 3.6);
                        postInvalidate();
                        try {
                            Thread.sleep(20);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
            mThread.start();
        }
    }
}
