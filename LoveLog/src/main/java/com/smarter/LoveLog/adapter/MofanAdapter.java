package com.smarter.LoveLog.adapter;

/**
 * Created by Administrator on 2015/12/7.
 */
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.smarter.LoveLog.R;

/**
 * Created by jianghejie on 15/11/26.
 */
public class MofanAdapter extends RecyclerView.Adapter<MofanAdapter.ViewHolder> {
    public int[] datas = null;
    public  Context mContext;
    public MofanAdapter(Context mContext,int[] datas) {
        this.datas = datas;
        this.mContext=mContext;
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.community_xrecy_item,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, int position) {
        viewHolder.imglist.setImageResource(datas[position]);
        initPopuwindow();
        viewHolder.reword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {




                int[] location = new int[2];
                viewHolder.reword.getLocationOnScreen(location);
                isWifiPopupWindow.showAtLocation(viewHolder.reword, Gravity.NO_GRAVITY, location[0], location[1] - viewHolder.reword.getHeight() * 5);

       AnimationSet animationSet =new AnimationSet(true);
               // Animation myAnimation= AnimationUtils.loadAnimation(mContext, R.anim.da_shan);
                RotateAnimation animationnew = new RotateAnimation(
                        0, 180, RotateAnimation.RELATIVE_TO_SELF, 0.5f, RotateAnimation.RELATIVE_TO_SELF, 0.5f);
               // animationnew.setInterpolator(new LinearInterpolator());
                animationnew.setDuration(200);
                animationnew.setRepeatCount(1);
                animationnew.setFillAfter(true);
             //   animationnew.setStartOffset(50);



                /** 设置缩放动画 */
                final ScaleAnimation animation =new ScaleAnimation(0.0f, 1.0f, 0.0f, 1.0f,
                        Animation.RELATIVE_TO_PARENT, 1.0f, Animation.RELATIVE_TO_PARENT, 1.0f);
                animation.setDuration(200);//设置动画持续时间
                /** 常用方法 */
                animation.setRepeatCount(1);//设置重复次数
                animation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
               // animation.setStartOffset(long startOffset);//执行前的等待时间



                animationSet.addAnimation(animationnew);
                animationSet.addAnimation(animation);


                integral01.startAnimation(animationSet);
                integral02.startAnimation(animationSet);
                integral03.startAnimation(animationSet);


            }
        });
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.length;
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imglist,reword,integral03,integral02,integral01;
        public LinearLayout views;

        public ViewHolder(View view){
            super(view);
            imglist = (ImageView) view.findViewById(R.id.imglist);
            integral03=(ImageView) view.findViewById(R.id.integral03);
            integral02=(ImageView) view.findViewById(R.id.integral02);
            integral01=(ImageView) view.findViewById(R.id.integral01);

            reword= (ImageView) view.findViewById(R.id.reword);
            views=(LinearLayout) view.findViewById(R.id.view);
        }
    }





    PopupWindow  isWifiPopupWindow;
    ImageView integral01,integral02,integral03;
    private void initPopuwindow() {
        // TODO Auto-generated method stub
        View popuView = LayoutInflater.from(mContext).inflate(R.layout.is_wifi_open,
                null);
        isWifiPopupWindow = new PopupWindow(popuView, RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        // 需要设置一下此参数，点击外边可消失
        isWifiPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击窗口外边窗口消失
        isWifiPopupWindow.setOutsideTouchable(false);
        // 设置此参数获得焦点，否则无法点击
        isWifiPopupWindow.setFocusable(true);
        isWifiPopupWindow.update();


        integral01= (ImageView) popuView.findViewById(R.id.integral01);
        integral02= (ImageView) popuView.findViewById(R.id.integral02);
        integral03= (ImageView) popuView.findViewById(R.id.integral03);

    }
}

