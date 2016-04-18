package com.jcodecraeer.xrecyclerview;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.jcodecraeer.xrecyclerview.progressindicator.AVLoadingIndicatorView;

public class LoadingMoreFooter extends LinearLayout {

    private SimpleViewSwithcer progressCon;
    private Context mContext;
    public final static int STATE_LAODING = 0;
    public final static int STATE_COMPLETE = 1;
    public final static int STATE_NOMORE = 2;
    private TextView mText;
    CircleView circleView;
    private Animation mRotateAllAnim;
	public LoadingMoreFooter(Context context) {
		super(context);
		initView(context);
	}

	/**
	 * @param context
	 * @param attrs
	 */
	public LoadingMoreFooter(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView(context);
	}
    public void initView(Context context ){
        mContext = context;
        setGravity(Gravity.CENTER);
        setLayoutParams(new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        progressCon = new SimpleViewSwithcer(context);

        LinearLayout.LayoutParams vieLayoutParams =new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        vieLayoutParams.setMargins(0,25,0,25);
        progressCon.setLayoutParams(vieLayoutParams);


       /* AVLoadingIndicatorView progressView = new  AVLoadingIndicatorView(this.getContext());
        progressView.setIndicatorColor(0xffB5B5B5);
        progressView.setIndicatorId(ProgressStyle.BallSpinFadeLoader);
        progressCon.setView(progressView);*/


        mRotateAllAnim=new RotateAnimation(0.0f, 360.0f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
                0.5f);
        mRotateAllAnim.setDuration(300);
        mRotateAllAnim.setRepeatCount(-1);
        mRotateAllAnim.setFillAfter(true);



        ZiMuView ziMuView=new ZiMuView(mContext);
        circleView=new CircleView(getContext());
        circleView.SetInfo(60f);
        progressCon.addView(ziMuView);
        progressCon.addView(circleView);


        addView(progressCon);
        mText = new TextView(context);
        mText.setTextColor(Color.parseColor("#fc1359"));
        mText.setText("正在加载...");
        mText.setVisibility(GONE);

        LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParams.setMargins( (int)getResources().getDimension(R.dimen.textandiconmargin),0,0,0 );

        mText.setLayoutParams(layoutParams);
        addView(mText);
    }

    public void setProgressStyle(int style) {
       /* if(style == ProgressStyle.SysProgress){
            progressCon.setView(new ProgressBar(mContext, null, android.R.attr.progressBarStyle));
        }else{
            AVLoadingIndicatorView progressView = new  AVLoadingIndicatorView(this.getContext());
            progressView.setIndicatorColor(0xffB5B5B5);
            progressView.setIndicatorId(style);
            progressCon.setView(progressView);
        }*/
    }

    public void  setState(int state) {
        switch(state) {
            case STATE_LAODING:
                progressCon.setVisibility(View.VISIBLE);
                circleView.startAnimation(mRotateAllAnim);//正在刷新旋转画的圆
                mText.setText(mContext.getText(R.string.listview_loading));
                this.setVisibility(View.VISIBLE);
                    break;
            case STATE_COMPLETE:
                mText.setText(mContext.getText(R.string.listview_loading));
                circleView.clearAnimation();
                this.setVisibility(View.GONE);
                break;
            case STATE_NOMORE:
                mText.setText(mContext.getText(R.string.nomore_loading));
                progressCon.setVisibility(View.GONE);
                circleView.clearAnimation();
                this.setVisibility(View.VISIBLE);
                break;
        }

    }
}
