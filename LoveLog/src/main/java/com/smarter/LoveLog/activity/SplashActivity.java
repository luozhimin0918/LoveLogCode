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
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.ScaleAnimation;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import  com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.BitmapCache;
import com.smarter.LoveLog.db.Data;
import com.smarter.LoveLog.db.DataCleanManager;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.ui.productViewPager.AutoLoopViewPager;
import com.smarter.LoveLog.ui.productViewPager.CirclePageIndicator;

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


           initGuideGallery();

       }else{

           initLaunchLogo();

       }
    }
    private int[] images = {
            R.mipmap.newer01,
            R.mipmap.newer02,
            R.mipmap.newer03,
            R.mipmap.newer04

    };



    public  Boolean isScrolling=false;
    public  Boolean isStartIntent=false;
    private void initGuideGallery() {
        btnHome.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, MainActivity.class);
                mContext.startActivity(intent);
                SharedPreferences.getInstance().putBoolean("first-time-use", false);
                finish();
                overridePendingTransition(R.anim.in_from_right,
                        R.anim.out_to_left);
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
                }else if(position==images.length-1){

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
                if (position == images.length -1) {
                    btnHome.setVisibility(View.VISIBLE);
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




    private void initLaunchLogo() {

        viewFragment.setVisibility(View.GONE);
        welcomeBg.setVisibility(View.VISIBLE);


        RequestQueue mQueue = Volley.newRequestQueue(mContext);
        netWorkImageView.setDefaultImageResId(R.mipmap.welcome);
        netWorkImageView.setErrorImageResId(R.mipmap.welcome);
        netWorkImageView.setImageUrl("http://ys.rili.com.cn/images/image/201401/0111174780.jpg",  new ImageLoader(mQueue, new BitmapCache()));
           /*if(mQueue.getCache().get("http://ys.rili.com.cn/images/image/201401/0111174780.jpg")!=null){
               textData.setText(new String(mQueue.getCache().get("http://ys.rili.com.cn/images/image/201401/0111174780.jpg").data).toString());
           }*/
        try {
            textData.setText(DataCleanManager.getTotalCacheSize(mContext));
        } catch (Exception e) {
            e.printStackTrace();
        }



        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                welcomeBg.setVisibility(View.GONE);
                fraglay.setVisibility(View.VISIBLE);




                /** 设置缩放动画 */
                final ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 1.4f, 1.0f, 1.4f,
                        Animation.RELATIVE_TO_SELF, 0.3f, Animation.RELATIVE_TO_SELF, 0.4f);
                scaleAnimation.setInterpolator(new LinearInterpolator());
                scaleAnimation.setDuration(2000);//设置动画持续时间
                /** 常用方法 */
                scaleAnimation.setRepeatCount(0);//设置重复次数
                scaleAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
                 scaleAnimation.setStartOffset(1000);//执行前的等待时间
                 scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
                     @Override
                     public void onAnimationStart(Animation animation) {

                     }

                     @Override
                     public void onAnimationEnd(Animation animation) {

                         Intent intent = new Intent(mContext, MainActivity.class);
                         mContext.startActivity(intent);
                         finish();

                     }

                     @Override
                     public void onAnimationRepeat(Animation animation) {

                     }
                 });
                netWorkImageView.startAnimation(scaleAnimation);
            }
        }, 1500);
    }



    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return images.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView item = new ImageView(SplashActivity.this);
            item.setScaleType(ImageView.ScaleType.CENTER_CROP);
            item.setImageResource(images[position]);
            container.addView(item);
            return item;
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

}
