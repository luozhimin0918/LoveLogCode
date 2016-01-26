package com.smarter.LoveLog.adapter;

/**
 * Created by Administrator on 2015/12/7.
 */
import android.animation.Animator;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.activity.InvitationActivity;
import com.smarter.LoveLog.activity.InvitationDeatilActivity;
import com.smarter.LoveLog.activity.MainActivity;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.community.CollectData;
import com.smarter.LoveLog.model.community.CollectDataInfo;
import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.model.community.RewardData;
import com.smarter.LoveLog.model.community.RewardDataInfo;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.jsonModel.ZanOrFaroviteParame;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.ui.CircleNetworkImage;
import com.smarter.LoveLog.ui.popwindow.BabyPopWindow;
import com.smarter.LoveLog.ui.popwindow.AlertDialog;
import java.io.Serializable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by jianghejie on 15/11/26.
 */
public class MofanAdapter extends RecyclerView.Adapter<MofanAdapter.ViewHolder> {
    public int[] datas = null;
    public  Context mContext;
    List<PromotePostsData> promotePostsDataList;
    ViewGroup viewGroup;
    RequestQueue mQueue;
    public MofanAdapter(Context mContext,int[] datas) {
        this.datas = datas;
        this.mContext=mContext;
    }
    public MofanAdapter(Context mContext, List<PromotePostsData> promotePostsDataList) {
        this.promotePostsDataList = promotePostsDataList;
        this.mContext=mContext;
        mQueue =  AppContextApplication.getInstance().getmRequestQueue();
    }

    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.community_xrecy_item,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        this.viewGroup=viewGroup;
        return vh;
    }
    //将数据与界面进行绑定的操作

    PromotePostsData rewardItemData;
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int position) {



        viewHolder.title.setText(promotePostsDataList.get(position).getTitle());
        viewHolder.brief.setText(promotePostsDataList.get(position).getBrief());
        initPopuwindow();
        viewHolder.CommunityItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //挑战到宝贝搜索界面
                Intent intent = new Intent(mContext, InvitationDeatilActivity.class);
               Bundle bundle = new Bundle();
                PromotePostsData pp=promotePostsDataList.get(position);
                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                intent.putExtras(bundle);

                mContext.startActivity(intent);
            }
        });



        //头像
        viewHolder.imageTitle.setDefaultImageResId(R.mipmap.loadding);
        viewHolder.imageTitle.setErrorImageResId(R.mipmap.loadding);
        RequestQueue mQueue =  AppContextApplication.getInstance().getmRequestQueue();
        String UserimageUrl=promotePostsDataList.get(position).getUser().getAvatar();
        if(mQueue.getCache().get(UserimageUrl)==null){
            viewHolder.imageTitle.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        viewHolder.imageTitle.setImageUrl(UserimageUrl, AppContextApplication.getInstance().getmImageLoader());


        //imgList  多个图片list
        viewHolder.imglist.removeAllViews();
        String imglistString="";
        PromotePostsData promotePostsDataItem=promotePostsDataList.get(position);
        if(promotePostsDataItem.getImg().getCover()!=null&&!promotePostsDataItem.getImg().getCover().equals("")){
            imglistString=promotePostsDataItem.getImg().getCover();
        }


            View view = View.inflate(viewGroup.getContext(), R.layout.item_image_invitation, null);
              NetworkImageView img = (NetworkImageView) view.findViewById(R.id.iv_item);

            img.setDefaultImageResId(R.mipmap.loadding);
            img.setErrorImageResId(R.mipmap.loadding);
            if(mQueue.getCache().get(imglistString)==null){
                img.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
            }
            img.setImageUrl(imglistString, AppContextApplication.getInstance().getmImageLoader());
            viewHolder.imglist.addView(view);





        //精华，热门
        if(Integer.parseInt(promotePostsDataItem.getClick_count())>20000){
//            viewHolder.isHot.setVisibility(View.VISIBLE);
        }
        if(Integer.parseInt(promotePostsDataItem.getClick_count())>20000){
           // viewHolder.isJinghua.setVisibility(View.VISIBLE);
        }

       viewHolder.userName.setText(promotePostsDataItem.getUser().getName());
        viewHolder.userTime.setText(promotePostsDataItem.getAdd_time());

        //打赏
        viewHolder.reword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                integral01.setVisibility(View.VISIBLE);
                integral02.setVisibility(View.VISIBLE);
                integral03.setVisibility(View.VISIBLE);

                 rewardItemData=promotePostsDataList.get(position);
                    int[] location = new int[2];
                    viewHolder.reword.getLocationOnScreen(location);
                    isWifiPopupWindow.showAtLocation(viewHolder.reword, Gravity.NO_GRAVITY, location[0], location[1] - viewHolder.reword.getHeight() * 7);//
                    /**
                     * 动画
                     */
                    AnimationSet animationSet = new AnimationSet(true);
                    // Animation myAnimation= AnimationUtils.loadAnimation(mContext, R.anim.da_shan);
                    RotateAnimation rotateAnimation = new RotateAnimation(
                            270, 0, RotateAnimation.RELATIVE_TO_PARENT, 0.5f, RotateAnimation.RELATIVE_TO_PARENT, 0.4f);
                    rotateAnimation.setInterpolator(new LinearInterpolator());
                    rotateAnimation.setDuration(200);
                    rotateAnimation.setRepeatCount(0);
                    rotateAnimation.setFillAfter(true);
                    // rotateAnimation.setStartOffset(50);


                    /** 设置缩放动画 */
                    final ScaleAnimation scaleAnimation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f,
                            Animation.RELATIVE_TO_PARENT, 0.3f, Animation.RELATIVE_TO_PARENT, 0.4f);
                    scaleAnimation.setInterpolator(new LinearInterpolator());
                    scaleAnimation.setDuration(200);//设置动画持续时间
                    /** 常用方法 */
                    scaleAnimation.setRepeatCount(0);//设置重复次数
                    scaleAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
                    // scaleAnimation.setStartOffset(1000);//执行前的等待时间


//                    animationSet.addAnimation(rotateAnimation);
                    animationSet.addAnimation(scaleAnimation);


                    integral01.startAnimation(animationSet);
                    integral02.startAnimation(animationSet);
                    integral03.startAnimation(animationSet);
                    /**
                     * 动画后的黑色蒙层
                     */
                    BabyPopWindow.backgroundAlpha(mContext,0.2f);
            }
        });
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return promotePostsDataList.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView reword;
        TextView sharePic,title,brief,userName,userTime;
        CircleNetworkImage imageTitle;//头像
        LinearLayout  imglist,isJinghua,isHot;
        RelativeLayout CommunityItem;

        public ViewHolder(View view){
            super(view);
            CommunityItem= (RelativeLayout) view.findViewById(R.id.CommunityItem);
            imglist = (LinearLayout) view.findViewById(R.id.imglist);
            isJinghua = (LinearLayout) view.findViewById(R.id.isJinghua);
            isHot = (LinearLayout) view.findViewById(R.id.isHot);

            imageTitle=(CircleNetworkImage) view.findViewById(R.id.imageTitle);
            sharePic=(TextView) view.findViewById(R.id.sharePic);
            title=(TextView) view.findViewById(R.id.title);
            brief=(TextView) view.findViewById(R.id.brief);
            userName=(TextView) view.findViewById(R.id.userName);
            userTime=(TextView) view.findViewById(R.id.userTime);

            reword= (ImageView) view.findViewById(R.id.reword);
        }
    }





    PopupWindow  isWifiPopupWindow;
    ImageView integral01,integral02,integral03;
    LinearLayout popuLinear;
    int  populinerWidth=0;
    private void initPopuwindow() {
        // TODO Auto-generated method stub
        View popuView = LayoutInflater.from(mContext).inflate(R.layout.popuwindow_dashan_open,
                null);
        isWifiPopupWindow = new PopupWindow(popuView, RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);


        //在PopupWindow里面就加上下面代码，让键盘弹出时，不会挡住pop窗口。
        isWifiPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        isWifiPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口
        isWifiPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击窗口外边窗口消失
        isWifiPopupWindow.setOutsideTouchable(false);
        // 设置此参数获得焦点，否则无法点击
        isWifiPopupWindow.setFocusable(true);
        isWifiPopupWindow.update();

        //添加pop窗口关闭事件
        isWifiPopupWindow.setOnDismissListener(new poponDismissListener());


        integral01= (ImageView) popuView.findViewById(R.id.integral01);
        integral02= (ImageView) popuView.findViewById(R.id.integral02);
        integral03= (ImageView) popuView.findViewById(R.id.integral03);
        popuLinear= (LinearLayout) popuView.findViewById(R.id.popuLinear);
        populinerWidth=popuLinear.getWidth();
        popuLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWifiPopupWindow.dismiss();
            }
        });
        integral01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                new AlertDialog(mContext).builder().setTitle("提示")
                        .setMsg("此次打赏您将消耗5个积分")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                startAnimatSet(1);
                                String url = "http://mapp.aiderizhi.com/?url=/post/reward";//打赏
                                initIsLogonParame(url,"5");
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();

            }
        });
        integral02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimatSet(2);
                String url = "http://mapp.aiderizhi.com/?url=/post/reward";//打赏
                initIsLogonParame(url,"10");
            }
        });
        integral03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startAnimatSet(3);
                String url = "http://mapp.aiderizhi.com/?url=/post/reward";//打赏
                initIsLogonParame(url,"15");
            }
        });
    }

    private void startAnimatSet(int option) {




        // 设置缩放动画
        final ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.6f, 1.0f, 2.6f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setInterpolator(new LinearInterpolator());
        scaleAnimation.setDuration(1000);//设置动画持续时间
        //** 常用方法
        scaleAnimation.setRepeatCount(0);//设置重复次数
        scaleAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
//        scaleAnimation.setStartOffset(1000);//执行前的等待时间
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        AlphaAnimation inAlphaAnimation = new AlphaAnimation(1, 0);
        inAlphaAnimation.setRepeatCount(0);//设置重复次数
        inAlphaAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        inAlphaAnimation.setDuration(1000);
        inAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isWifiPopupWindow.dismiss();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        AnimationSet animationSet =new AnimationSet(false);
        animationSet.addAnimation(inAlphaAnimation);
        animationSet.addAnimation(scaleAnimation);

        BabyPopWindow.backgroundAlpha(mContext, 1.0f);


        if(option==1){
            integral02.setVisibility(View.INVISIBLE);
            integral03.setVisibility(View.INVISIBLE);
            integral01.startAnimation(animationSet);

        }
        if(option==2){
            integral01.setVisibility(View.INVISIBLE);
            integral03.setVisibility(View.INVISIBLE);
            integral02.startAnimation(animationSet);
        }
        if(option==3){
            integral01.setVisibility(View.INVISIBLE);
            integral02.setVisibility(View.INVISIBLE);
            integral03.startAnimation(animationSet);
        }

    }


    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     * @author cg
     *
     */
    class poponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            BabyPopWindow.backgroundAlpha(mContext, 1.0f);
        }

    }





    SessionData sessionData;
    private void initIsLogonParame(String url,String reward) {

        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
        if(isLogin){
            String  sessionString=SharedPreferences.getInstance().getString("session", "");
            sessionData = JSON.parseObject(sessionString, SessionData.class);
            if(sessionData!=null){

                ZanOrFaroviteParame zanOrFaroviteInfo=new ZanOrFaroviteParame();
                zanOrFaroviteInfo.setSession(sessionData);
                if(rewardItemData!=null){
                    zanOrFaroviteInfo.setPost_id(rewardItemData.getId());
                    zanOrFaroviteInfo.setReward(reward);

                    networkReward(JSON.toJSONString(zanOrFaroviteInfo), url);
                }












            }

        }else{
            Toast.makeText(mContext, "未登录，请先登录", Toast.LENGTH_SHORT).show();
        }
    }




    /**
     * 打赏
     */
    RewardData rewardData;
    private void networkReward(String paramNet,String url) {

        Map<String, String> mapTou = new HashMap<String, String>();
        mapTou.put("json", paramNet);




        Log.d("MonfanAdapter", paramNet + "      ");


        FastJsonRequest<RewardDataInfo> fastJsonCommunity = new FastJsonRequest<RewardDataInfo>(Request.Method.POST, url, RewardDataInfo.class, null, new Response.Listener<RewardDataInfo>() {
            @Override
            public void onResponse(RewardDataInfo rewardDataInfo) {

                DataStatus status = rewardDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    rewardData = rewardDataInfo.getData();
                    if(rewardData!=null){

                        Toast.makeText(mContext, "打赏成功" , Toast.LENGTH_SHORT).show();
                        Log.d("MonfanAdapter", "MonfanAdapter 成功返回信息：   " + JSON.toJSONString(rewardData)+ "++++succeed");
                    }


                } else {
                    // 请求失败
                    Log.d("MonfanAdapter", "succeded=0  MonfanAdapter 返回信息 " + JSON.toJSONString(status) + "");
                    Toast.makeText(mContext, "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("MonfanAdapter", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }
}

