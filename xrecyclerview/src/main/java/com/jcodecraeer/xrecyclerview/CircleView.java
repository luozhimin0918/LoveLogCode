package com.jcodecraeer.xrecyclerview;

/**
 * Created by Administrator on 2016/3/18.
 */
import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.Point;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.util.Log;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;


public class CircleView extends View {

    private Paint xPaint;
    float mProgress=0f;



    public CircleView(Context context) {
        super(context);

        // TODO Auto-generated constructor stub
    }

    public void SetInfo(float mProgress) {

        this.mProgress=mProgress;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        xPaint = new Paint();
        xPaint.setStyle(Paint.Style.STROKE);
        xPaint.setAntiAlias(true);
        xPaint.setColor(Color.RED);
        xPaint.setTextSize(25);


        Paint p = new Paint();
        p.setStrokeWidth(3);
        p.setAntiAlias(true);
        p.setColor(Color.parseColor("#fc1359"));
        p.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF(0 + 10, 0 + 10, mWidth - 10, mHeight - 10);
        canvas.drawArc(rectF, -80, 360f * mProgress / 100f, false, p);
//        mProgress+=5;
//        postInvalidateDelayed(10);









    }



    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:


//                        postInvalidate();



                break;

            case MotionEvent.ACTION_MOVE:

                break;
            case MotionEvent.ACTION_UP:

                break;
            default:
                break;
        }

        return true;
    }

    int mWidth,mHeight;
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // TODO Auto-generated method stub
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        if (widthMode == MeasureSpec.EXACTLY) {
            mWidth = widthSize;
        } else {
            mWidth = 80;
        }
        if (heightMode == MeasureSpec.EXACTLY) {
            mHeight = heightSize;
        } else {
            mHeight = 80;
        }
        setMeasuredDimension(mWidth, mHeight);

    }

}

