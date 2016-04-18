package com.jcodecraeer.xrecyclerview;

/**
 * Created by Administrator on 2016/3/18.
 */

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.PathEffect;
import android.graphics.RectF;
import android.view.MotionEvent;
import android.view.View;


public class ZiMuView extends View {

    private Paint xPaint;



    public ZiMuView(Context context) {
        super(context);

        // TODO Auto-generated constructor stub
    }

    public void SetInfo(float mProgress) {


    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);


        xPaint = new Paint();
        PathEffect effects = new DashPathEffect(new float[] { 5, 5, 5, 5 }, 1);
        xPaint.setPathEffect(effects);
        xPaint.setStyle(Paint.Style.STROKE);
        xPaint.setAntiAlias(true);
        xPaint.setStrokeWidth(5);
        xPaint.setColor(Color.parseColor("#fc1359"));


        int xInt=20;
        canvas.drawLine(xInt+2, mHeight - 30, xInt + 10+2, 30, xPaint);
        canvas.drawLine(xInt + 10, 30, xInt + 20+1, mHeight - 30, xPaint);
        canvas.drawLine(xInt+20-1,mHeight-30,xInt+30,30,xPaint);
        canvas.drawLine(xInt+30-2,30,xInt+40-2,mHeight-30,xPaint);










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

