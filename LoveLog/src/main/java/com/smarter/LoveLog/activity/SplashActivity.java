package com.smarter.LoveLog.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import  com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.BitmapCache;
import com.smarter.LoveLog.db.Data;
import com.smarter.LoveLog.db.DataCleanManager;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.jsonModel.GuideImgData;
import com.smarter.LoveLog.model.jsonModel.GuideImgInfo;
import com.smarter.LoveLog.model.jsonModel.StartImgData;
import com.smarter.LoveLog.model.jsonModel.StartImgInfo;
import com.smarter.LoveLog.model.loginData.PersonalDataInfo;
import com.smarter.LoveLog.ui.productViewPager.AutoLoopViewPager;
import com.smarter.LoveLog.ui.productViewPager.CirclePageIndicator;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class SplashActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "SplashActivity";
    @Bind(R.id.netWorkImageView)
    public NetworkImageView netWorkImageView;
    @Bind(R.id.textData)
    public TextView textData;
    @Bind(R.id.welcomeBg)
    public ImageView welcomeBg;
    @Bind(R.id.fraglay)
    public FrameLayout fraglay;
    @Bind(R.id.tiaoguoBut)
    public TextView tiaoguoBut;


    @Bind(R.id.viewFragment)
    public FrameLayout viewFragment;


    @Bind(R.id.pager)
    public ViewPager pager;
    @Bind(R.id.btnHome)
    public Button btnHome;
    @Bind(R.id.indicator)
    public CirclePageIndicator indicator;
    Context  mContext;
    GalleryPagerAdapter adapter;



    // 手指上下滑动时的最小速度
    private static final int YSPEED_MIN = 1000;

    // 手指向右滑动时的最小距离
    private static final int XDISTANCE_MIN = 100;

    // 手指向上滑或下滑时的最小距离
    private static final int YDISTANCE_MIN = 100;

    // 记录手指按下时的横坐标。
    private float xDown;

    // 记录手指按下时的纵坐标。
    private float yDown;

    // 记录手指移动时的横坐标。
    private float xMove;

    // 记录手指移动时的纵坐标。
    private float yMove;

    // 用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;
    private String direction = "c";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_view);
        ButterKnife.bind(this);
        mContext=this;


        getDataIntent();
        intData();
        setListen();

    }

    private void setListen() {

    }

    private void intData() {

        boolean firstTimeUse = SharedPreferences.getInstance().getBoolean("first-time-use", true);

       if(firstTimeUse){

           networkGUide();


       }else{

           initLaunchLogo();

       }
    }




    public  Boolean isScrolling=false;
    public  Boolean isStartIntent=false;
    private void initGuideGallery() {



       btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
                overridePendingTransition(R.anim.in_from_right,
                        R.anim.out_to_left);

                SharedPreferences.getInstance().putBoolean("first-time-use", false);
                finish();

            }
        });

        adapter = new GalleryPagerAdapter();
        pager.setAdapter(adapter);
        indicator.setViewPager(pager);

        indicator.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                if(position==0){
                    if(positionOffsetPixels==0&&isScrolling){

                    }
                }else if(position==guideList.size()-1){

                    if(positionOffsetPixels==0&&isScrolling&&isStartIntent==false){
                        isStartIntent=true;

                       Intent intent = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.in_from_right,
                                R.anim.out_to_left);
                        SharedPreferences.getInstance().putBoolean("first-time-use", false);
                    }
                }




                Log.d("SplashActivity", "positionOffset:" + positionOffset + "    positionOffsetPixels:" + positionOffsetPixels + "");
            }

            @Override
            public void onPageSelected(int position) {
                if (position == guideList.size() -1) {
//                    btnHome.setVisibility(View.VISIBLE);//待写
//                    btnHome.startAnimation(fadeIn);
                } else {
                    btnHome.setVisibility(View.GONE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

                if (state == 1) {
                                   isScrolling = true;
                } else {
                                   isScrolling = false;
                }

            }
        });
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            String  str = intent.getStringExtra("ObjectData");
           // Toast.makeText(this,str+"",Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public void onClick(View v) {
         switch (v.getId()){


         }
    }

    boolean   isTiaoguo=false;
    boolean   isClickAdBut=false;
private  void  loadingImage(){

    if(startImgDataList!=null){

        netWorkImageView.setDefaultImageResId(R.mipmap.welcome);
        netWorkImageView.setErrorImageResId(R.mipmap.welcome);
        netWorkImageView.setScaleType(ImageView.ScaleType.FIT_XY);
        netWorkImageView.setImageUrl(startImgDataList.get(0).getImg_url(), AppContextApplication.getInstance().getmImageLoader());

        try {
            textData.setText(DataCleanManager.getTotalCacheSize(mContext));//缓存get大小
        } catch (Exception e) {
            e.printStackTrace();
        }


        if(startImgDataList.get(0).getType().equals("ad")){
            tiaoguoBut.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isTiaoguo=true;
                    Intent intent = new Intent(mContext, MainActivity.class);
                    mContext.startActivity(intent);
                    finish();
                }
            });

            netWorkImageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    isClickAdBut=true;
                    Intent intent = new Intent(mContext, WebViewUrlActivity.class);
                    intent.putExtra("param", startImgDataList.get(0).getLink_url());
                    mContext.startActivity(intent);
                }
            });
        }



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                welcomeBg.setVisibility(View.GONE);
                fraglay.setVisibility(View.VISIBLE);
                startAnima();

            }
        }, 1000);

    }

}

    private void startAnima() {
        // 设置缩放动画
//        final ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.2f, 1.0f, 1.2f,
//                Animation.RELATIVE_TO_SELF, 0.3f, Animation.RELATIVE_TO_SELF, 0.4f);
//        scaleAnimation.setInterpolator(new LinearInterpolator());
//        scaleAnimation.setDuration(2000);//设置动画持续时间
//        //** 常用方法
//        scaleAnimation.setRepeatCount(0);//设置重复次数
//        scaleAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
//        scaleAnimation.setStartOffset(1000);//执行前的等待时间
//        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
//            @Override
//            public void onAnimationStart(Animation animation) {
//
//            }
//
//            @Override
//            public void onAnimationEnd(Animation animation) {
//
//                Intent intent = new Intent(mContext, MainActivity.class);
//                mContext.startActivity(intent);
//                finish();
//
//            }
//
//            @Override
//            public void onAnimationRepeat(Animation animation) {
//
//            }
//        });

        AlphaAnimation inAlphaAnimation = new AlphaAnimation(0, 1);
        inAlphaAnimation.setRepeatCount(0);//设置重复次数
        inAlphaAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        inAlphaAnimation.setDuration(1000);
        inAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        if (!isTiaoguo&&!isClickAdBut) {
                            Intent intent = new Intent(mContext, MainActivity.class);
                            mContext.startActivity(intent);
                            finish();
                        }
                    }

                }, 2000);


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
//        netWorkImageView.startAnimation(scaleAnimation);

            netWorkImageView.startAnimation(inAlphaAnimation);
    }


    private void initLaunchLogo() {
        welcomeBg.setVisibility(View.VISIBLE);
        fraglay.setVisibility(View.GONE);
        networkStart();









    }


    private  void  loadingGuide(){

        if(guideList!=null){
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    TranslateAnimation Animation=new TranslateAnimation(0, -1500, 0, 0);

                    Animation.setDuration(1000);//设置动画持续时间为3秒
                    Animation.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {

                        }

                        @Override
                        public void onAnimationEnd(Animation animation) {
                            welcomeBg.setVisibility(View.GONE);
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {

                        }
                    });
                    welcomeBg.startAnimation(Animation);


                }
            }, 2000);

            initGuideGallery();
        }

    }


    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return guideList.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
          NetworkImageView  netImageView=new NetworkImageView(SplashActivity.this);
//            netImageView.setDefaultImageResId(R.mipmap.welcome);
//            netImageView.setErrorImageResId(R.mipmap.welcome);
            netImageView.setScaleType(ImageView.ScaleType.FIT_XY);
            netImageView.setImageUrl(guideList.get(position).getImg(), AppContextApplication.getInstance().getmImageLoader());

            if(position==guideList.size()-1){
                netImageView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(intent);
                        finish();
                        overridePendingTransition(R.anim.in_from_right,
                                R.anim.out_to_left);
                        SharedPreferences.getInstance().putBoolean("first-time-use", false);
                    }
                });
            }
          /*  ImageView item = new ImageView(SplashActivity.this);
            item.setScaleType(ImageView.ScaleType.CENTER_CROP);
            item.setImageResource(images[position]);*/
            container.addView(netImageView);
            return netImageView;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                yDown = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                yMove = event.getRawY();
                // 滑动的距离
                int distanceX = (int) (xMove - xDown);
                int distanceY = (int) (yMove - yDown);
                // 获取顺时速度
                int ySpeed = getScrollVelocity();
                // 关闭Activity需满足以下条件：
                // 1.x轴滑动的距离>XDISTANCE_MIN
                // 2.y轴滑动的距离在YDISTANCE_MIN范围内
                // 3.y轴上（即上下滑动的速度）<XSPEED_MIN，如果大于，则认为用户意图是在上下滑动而非左滑结束Activity
                if (ySpeed > 100) {
                    direction = "center";
                } else if (ySpeed < 100) {
                    if (distanceX > XDISTANCE_MIN
                            && (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN)
                            && ySpeed < YSPEED_MIN) {
                        direction = "R";
                    } else if (distanceX < -XDISTANCE_MIN
                            && (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN)
                            && ySpeed < YSPEED_MIN) {
                        direction = "L";


                     /*  if(btnHome.getVisibility()==View.VISIBLE){
                            Intent intent = new Intent(mContext, MainActivity.class);
                           mContext.startActivity(intent);
                           finish();
                           overridePendingTransition(R.anim.in_from_right,
                                   R.anim.out_to_left);
                       }
                   */


                    }
                }

                break;
            case MotionEvent.ACTION_UP:

                 if (direction.contains("L"))
                 recycleVelocityTracker();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }
    /**
     * 创建VelocityTracker对象，并将触摸界面的滑动事件加入到VelocityTracker当中。
     *
     * @param event
     *
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }
    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }
    /**
     *
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getYVelocity();
        return Math.abs(velocity);
    }


    @Override
    protected void onResume() {
        super.onResume();

         new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                 if(isClickAdBut){
                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
                finish();
                }

            }
        }, 150);

    }

    /**
     * 获取Start
     */
    List<StartImgData> startImgDataList;
    private void networkStart() {

           String  url = "http://mapp.aiderizhi.com/?url=/start";//

        FastJsonRequest<StartImgInfo> fastJsonCommunity = new FastJsonRequest<StartImgInfo>(Request.Method.POST, url, StartImgInfo.class, null, new Response.Listener<StartImgInfo>() {
            @Override
            public void onResponse(StartImgInfo startImgInfo) {

                DataStatus status = startImgInfo.getStatus();
                if (status.getSucceed() == 1) {
                    startImgDataList = startImgInfo.getData();
                    if(startImgDataList!=null&&startImgDataList.size()>0){
                        loadingImage();
                        Log.d("SplashActivity", "Splash：start   " + JSON.toJSONString(startImgDataList.get(0))+ "++++succeed");
                    }


                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(mContext, MainActivity.class);
                            mContext.startActivity(intent);
                            finish();
                        }
                    }, 2000);



                    // 请求失败
                    Log.d("SplashActivity", "Splash  succeded=00000  " + JSON.toJSONString(status) + "");
//                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(intent);
                        finish();
                    }
                }, 1000);
                Log.d("SplashActivity", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(3000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }











    /**
     * 获取引导页
     */
    List<GuideImgData> guideList;
    private void networkGUide() {

        String  url = "http://mapp.aiderizhi.com/?url=/guide";//

        FastJsonRequest<GuideImgInfo> fastJsonCommunity = new FastJsonRequest<GuideImgInfo>(Request.Method.POST, url, GuideImgInfo.class, null, new Response.Listener<GuideImgInfo>() {
            @Override
            public void onResponse(GuideImgInfo guideImginfo) {

                DataStatus status = guideImginfo.getStatus();
                if (status.getSucceed() == 1) {
                    guideList = guideImginfo.getData();
                    if(guideList!=null){
                        loadingGuide();
                        Log.d("SplashActivity", "Splash：start   " + JSON.toJSONString(guideList)+ "++++succeed");
                    }


                } else {
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent = new Intent(mContext, MainActivity.class);
                            mContext.startActivity(intent);
                            finish();
                        }
                    }, 2000);



                    // 请求失败
                    Log.d("SplashActivity", "Splash  succeded=00000  " + JSON.toJSONString(status) + "");
//                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {



                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        mContext.startActivity(intent);
                        finish();
                    }
                }, 1000);
                Log.d("SplashActivity", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }



}
