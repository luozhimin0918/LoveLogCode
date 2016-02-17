package com.smarter.LoveLog.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.RecycleNotifacationAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.PaginationJson;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.model.notifacation.notificationActi;
import com.smarter.LoveLog.model.notifacation.notificationData;
import com.smarter.LoveLog.utills.DeviceUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class MyNotificationActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "MyNotification";
    Context  mContext;


    /**
     * RecyclerView
     */
    @Bind(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    @Bind(R.id.tv_top_title)
    TextView tv_top_title;

    EditText search_editText;


    @Bind(R.id.networkInfo)
    LinearLayout networkInfo;
    @Bind(R.id.errorInfo)
    ImageView errorInfo;
    @Bind(R.id.newLoading)
    LinearLayout newLoading;
    @Bind(R.id.loadingTextLinear)
    LinearLayout loadingTextLinear;
    @Bind(R.id.loadingText)
    TextView loadingText;





    @Bind(R.id.progressLinear)
    LinearLayout progressLinear;

    @Bind(R.id.progreView)
    ImageView progreView;

    @Bind(R.id.backBUt)
    ImageView backBUt;












    private RecycleNotifacationAdapter mAdapter;

    int  loadingTag=2;//刷新flag   2 默认   1 下拉刷新  -1是上拉更多

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification_my_view);
        ButterKnife.bind(this);
        mContext=this;
        getDataIntent();
        isLogiin();
        setListen();

    }

    SessionData sessionData;
    private void isLogiin() {

        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
        if(isLogin){
            String  sessionString=SharedPreferences.getInstance().getString("session", "");
            sessionData = JSON.parseObject(sessionString,SessionData.class);
            if(sessionData!=null){
                newWait();



            }

        }else{
            Toast.makeText(mContext, "未登录，请先登录", Toast.LENGTH_SHORT).show();
        }
    }


    private void setListen() {
        backBUt.setOnClickListener(this);

    }

    private void intData() {




        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);




        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                loadingTag = 2;//重新加载
                page=1;
                initData();
//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//
//                        mRecyclerView.refreshComplete();
//
//
//                    }
//
//                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {

//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
                loadingTag = -1;
                Log.d("MyNotificationURL", "initial    more");
                initData();
//                        mRecyclerView.loadMoreComplete();
//                    }
//                }, 2000);

            }
        });




       if(notificationDataList!=null&&notificationDataList.size()>0){
           mAdapter = new RecycleNotifacationAdapter(notificationDataList);

           mRecyclerView.setAdapter(mAdapter);
       }

    }



//    PromotePostsData promotePostsData;//上个activity传来的数据
    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
//            promotePostsData= (PromotePostsData) intent.getSerializableExtra("allpinglun");








        }


    }

    private void newWait() {
        if(DeviceUtil.checkConnection(mContext)){
            //加载动画
            progressLinear.setVisibility(View.VISIBLE);
            AnimationDrawable animationDrawable = (AnimationDrawable) progreView.getDrawable();
            animationDrawable.start();

            mRecyclerView.setVisibility(View.VISIBLE);
            networkInfo.setVisibility(View.GONE);


            initData();

        }else{
            errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nowifi));
            mRecyclerView.setVisibility(View.GONE);
            networkInfo.setVisibility(View.VISIBLE);
            newLoading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newWait();
                }
            });
        }
    }


    List<notificationData> notificationDataList;//
    public  int page=1;
    private void initData() {
        String url ="http://mapp.aiderizhi.com/?url=/message/notice";//

        Map<String, String> map = new HashMap<String, String>();




        if(loadingTag==-1){
            map = new HashMap<String, String>();
            PaginationJson paginationJson=new PaginationJson();
            paginationJson.setCount("10");
            paginationJson.setPage((++page)+"");
            String string = JSON.toJSONString(paginationJson);
            String  d="{\"pagination\":"+string+" ,\"session\":{\"uid\":\""+sessionData.getUid()+"\",\"sid\":\""+sessionData.getSid()+"\"}}";
            map.put("json", d);
            Log.d("MyNotification", d + "》》》》");
        }
        if(loadingTag==2){//第一次加载数据
            map = new HashMap<String, String>();
            String oneString ="{\"session\":{\"uid\":\""+sessionData.getUid()+"\",\"sid\":\""+sessionData.getSid()+"\"}}";
            map.put("json",oneString);
            Log.d("MyNotification", oneString + "》》》》");
        }



        RequestQueue mQueue = AppContextApplication.getInstance().getmRequestQueue();
       FastJsonRequest<notificationActi> fastJsonCommunity=new FastJsonRequest<notificationActi>(Request.Method.POST,url,notificationActi.class,null,new Response.Listener<notificationActi>()
        {
            @Override
            public void onResponse(notificationActi notificationActi) {

                DataStatus status=notificationActi.getStatus();
                if(status.getSucceed()==1){

                   progressLinear.setVisibility(View.GONE);


                    if(loadingTag==-1){


                        List<notificationData> p=notificationActi.getData();
                        Log.d("MyNotification", "" + notificationDataList.size() + "1111++++promotePostDateList" );
                        for(int i=0;i<p.size();i++){
                            notificationDataList.add(p.get(i));
                        }
                        Log.d("MyNotification", "" + notificationDataList.size() + "2222++++promotePostDateList" );






                        mRecyclerView.loadMoreComplete();
                    }
                    if(loadingTag==2){
                        notificationDataList=notificationActi.getData();

                        if(notificationDataList!=null&&notificationDataList.size()>0){
                            intData();//初始界面
                        }else{
                            progressLinear.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.GONE);
                            errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nodata));
                            networkInfo.setVisibility(View.VISIBLE);
                            newLoading.setVisibility(View.GONE);
                            loadingTextLinear.setVisibility(View.VISIBLE);
                            loadingText.setText("暂无通知");
                        }

                        mRecyclerView.refreshComplete();
                    }



//                    Log.d("MyNotification", "" + status.getSucceed() + "++++succeed》》》》" + promotePostDateList.get(0).getCat_name());
                } else {
                    // 请求失败
                    progressLinear.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nodata));
                    networkInfo.setVisibility(View.VISIBLE);

                    Toast.makeText(mContext,status.getError_desc(),Toast.LENGTH_SHORT).show();
                        // 请求失败
                        Log.d("MyNotification", "" + status.getSucceed() + "++++success=0》》》》" );

                }


            }
        } ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //未知错误
                progressLinear.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_default));
                networkInfo.setVisibility(View.VISIBLE);
                Log.d("MyNotification", "errror" + volleyError.toString() + "++++》》》》" );
            }
        });

        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);









    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case  R.id.backBUt:
                 finish();
                 break;

         }
    }










    // 手指上下滑动时的最小速度
    private static final int YSPEED_MIN = 1000;

    // 手指向右滑动时的最小距离
    private static final int XDISTANCE_MIN = 150;

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
                } else if (ySpeed < 100) {
                    if (distanceX > XDISTANCE_MIN
                            && (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN)
                            && ySpeed < YSPEED_MIN) {
                        finish();
                        overridePendingTransition(R.anim.in_from_left,
                                R.anim.out_to_right);
                    } else if (distanceX < -XDISTANCE_MIN
                            && (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN)
                            && ySpeed < YSPEED_MIN) {


//                        overridePendingTransition(R.anim.in_from_right,
//                                R.anim.out_to_left);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }





}
