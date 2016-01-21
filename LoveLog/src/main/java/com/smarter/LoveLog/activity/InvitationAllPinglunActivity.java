package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.MofanAdapter;
import com.smarter.LoveLog.adapter.RecyclePinglunAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.PaginationJson;
import com.smarter.LoveLog.model.category.InvitationDataActi;
import com.smarter.LoveLog.model.community.CollectData;
import com.smarter.LoveLog.model.community.CollectDataInfo;
import com.smarter.LoveLog.model.community.InvitationDataPinglunActi;
import com.smarter.LoveLog.model.community.Pinglun;
import com.smarter.LoveLog.model.community.PinglunData;
import com.smarter.LoveLog.model.community.PinglunDataInfo;
import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.model.community.RewardData;
import com.smarter.LoveLog.model.community.RewardDataInfo;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.home.NavIndexUrlData;
import com.smarter.LoveLog.model.jsonModel.ZanOrFaroviteParame;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.utills.DeviceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class InvitationAllPinglunActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "InvitationActivity";
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


    @Bind(R.id.progressLinear)
    LinearLayout progressLinear;

    @Bind(R.id.progreView)
    ImageView progreView;

    @Bind(R.id.backBUt)
    ImageView backBUt;

    @Bind(R.id.fasongText)
    TextView fasongText;

    @Bind(R.id.pinglunEdit)
    EditText pinglunEdit;









    private RecyclePinglunAdapter mAdapter;
    private int[] lit_int_resuour={R.mipmap.list1,R.mipmap.list2,R.mipmap.list1,R.mipmap.list2,R.mipmap.list1,R.mipmap.list2};


    int  loadingTag=2;//刷新flag   2 默认   1 下拉刷新  -1是上拉更多

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_pinglun_all_view);
        ButterKnife.bind(this);
        mContext=this;
        getDataIntent();
        setListen();

    }

    private void setListen() {
        backBUt.setOnClickListener(this);
        fasongText.setOnClickListener(this);

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
                initData(promotePostsData.getId());
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
                Log.d("InvitationActivityURL", "initial    more");
                initData(promotePostsData.getId());
//                        mRecyclerView.loadMoreComplete();
//                    }
//                }, 2000);

            }
        });




       if(promotePostDateList!=null&&promotePostDateList.size()>0){
           mAdapter = new RecyclePinglunAdapter(promotePostDateList);

           mRecyclerView.setAdapter(mAdapter);
       }

    }



    PromotePostsData promotePostsData;//上个activity传来的数据
    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            promotePostsData= (PromotePostsData) intent.getSerializableExtra("allpinglun");



            newWait();




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


            initData(promotePostsData.getId());

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


    List<Pinglun> promotePostDateList;//本类帖子 分类里所有数据
    public  int page=1;
    private void initData(final String id) {
        String url ="http://mapp.aiderizhi.com/?url=/comment/list";//

        Map<String, String> map = new HashMap<String, String>();




        if(loadingTag==-1){
            map = new HashMap<String, String>();
            PaginationJson paginationJson=new PaginationJson();
            paginationJson.setCount("10");
            paginationJson.setPage((++page)+"");
            String string = JSON.toJSONString(paginationJson);
            String  d="{\"id\":\""+id+"\",\"pagination\":"+string+" ,\"type\":\"2\"}";
            map.put("json", d);
            Log.d("pingluActivity", d + "》》》》");
        }
        if(loadingTag==2){//第一次加载数据
            map = new HashMap<String, String>();
            String oneString ="{\"type\":\"2\",\"id\":\""+id+"\"}";
            map.put("json",oneString);
            Log.d("pingluActivity", oneString + "》》》》");
        }



        RequestQueue mQueue = AppContextApplication.getInstance().getmRequestQueue();
       FastJsonRequest<InvitationDataPinglunActi> fastJsonCommunity=new FastJsonRequest<InvitationDataPinglunActi>(Request.Method.POST,url,InvitationDataPinglunActi.class,null,new Response.Listener<InvitationDataPinglunActi>()
        {
            @Override
            public void onResponse(InvitationDataPinglunActi pinglunActi) {

                DataStatus status=pinglunActi.getStatus();
                if(status.getSucceed()==1){

                   progressLinear.setVisibility(View.GONE);


                    if(loadingTag==-1){


                        List<Pinglun> p=pinglunActi.getData();
                        Log.d("pingluActivity", "" + promotePostDateList.size() + "1111++++promotePostDateList" );
                        for(int i=0;i<p.size();i++){
                            promotePostDateList.add(p.get(i));
                        }
                        Log.d("pingluActivity", "" + promotePostDateList.size() + "2222++++promotePostDateList" );






                        mRecyclerView.loadMoreComplete();
                    }
                    if(loadingTag==2){
                        promotePostDateList=pinglunActi.getData();
                        intData();//初始界面
                        mRecyclerView.refreshComplete();
                    }



//                    Log.d("pingluActivity", "" + status.getSucceed() + "++++succeed》》》》" + promotePostDateList.get(0).getCat_name());
                } else {
                    // 请求失败
                    progressLinear.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nodata));
                    networkInfo.setVisibility(View.VISIBLE);
                        // 请求失败
                        Log.d("pingluActivity", "" + status.getSucceed() + "++++success=0》》》》" );

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
                Log.d("pingluActivity", "errror" + volleyError.toString() + "++++》》》》" );
            }
        });

        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);





//客户端以json串的post请求方式进行提交,服务端返回json串
       /* CategoryJson categoryJson=new CategoryJson();
        PaginationJson paginationJson2=new PaginationJson();
        paginationJson2.setCount("5");
        paginationJson2.setPage("2");
        categoryJson.setId("4");
        categoryJson.setPagination(paginationJson2);


//        String string2 = JSON.toJSONString(paginationJson2);



        Map<String, String> mapTT = new HashMap<String, String>();
        mapTT.put("id", id);
        mapTT.put("pagination","{\"page\":\"3\",\"count\":\"3\"} ");
        JSONObject jsonObject = new JSONObject(mapTT);
//        Log.d("pingluActivity", "->>>>> " + string2);


        JsonRequest<JSONObject> jsonRequest = new JsonObjectRequest(Request.Method.POST,url, jsonObject,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("pingluActivity", "response -> " + response.toString());
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("pingluActivity", error.getMessage(), error);
            }
        })
        {
            //注意此处override的getParams()方法,在此处设置post需要提交的参数根本不起作用
            //必须象上面那样,构成JSONObject当做实参传入JsonObjectRequest对象里
            //所以这个方法在此处是不需要的
//    @Override
//    protected Map<String, String> getParams() {
//          Map<String, String> map = new HashMap<String, String>();
//            map.put("name1", "value1");
//            map.put("name2", "value2");

//        return params;
//    }

            @Override
            public Map<String, String> getHeaders() {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                headers.put("Content-Type", "application/json; charset=UTF-8");

                return headers;
            }
        };
        mQueue.add(jsonRequest);


*/





    /*   StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "http://mapp.aiderizhi.com/?url=/post/category",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Log.d("onResponse", "response -> " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("onResponse", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                // 在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();

                PaginationJson paginationJson=new PaginationJson();
                paginationJson.setCount("2");
                paginationJson.setPage("2");


                String string = JSON.toJSONString(paginationJson);
                String  d="{"+"\"pagination\":"+string+"}";
                Log.d("onResponse", "response >>>>>>>>>>>>>-> " + d);
                map.put("json", d);
                map.put("id", id);
//                map.put("id","22758");
//                map.put("type","2");
                return map;
            }
        };
        mQueue.add(stringRequest);*/






    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case  R.id.backBUt:
                 finish();
                 break;
             case  R.id.fasongText:

                 if(pinglunEdit.getText().toString()!=null&&!pinglunEdit.getText().toString().equals("")){
                     initIsLogonParame();
                 }else{
                     Toast.makeText(mContext, "评论不能为空" , Toast.LENGTH_SHORT).show();
                 }

                 break;
         }
    }


    private void initIsLogonParame() {
    String   url = "http://mapp.aiderizhi.com/?url=/comment/add ";//评论
        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
        if(isLogin){
            String  sessionString=SharedPreferences.getInstance().getString("session", "");
            SessionData sessionData = JSON.parseObject(sessionString, SessionData.class);
            if(sessionData!=null){


                if(promotePostsData!=null){

                  String param="{\"type\":\"2\",\"id\":\""+promotePostsData.getId()+"\",\"reply_id\":\"\",\"content\":\""+pinglunEdit.getText().toString()+"\",\"session\":{\"uid\":\""+sessionData.getUid()+"\",\"sid\":\""+sessionData.getSid()+"\"}}";
                    networkReward(param, url);
                }












            }

        }else{
            Toast.makeText(mContext, "未登录，请先登录", Toast.LENGTH_SHORT).show();
        }
    }





    /**
     * 帖子评论
     */
    PinglunData pinglunData;
    private void networkReward(String paramNet,String url) {

        Map<String, String> mapTou = new HashMap<String, String>();
        mapTou.put("json", paramNet);




        Log.d("pingluActivity", paramNet + "      ");


        FastJsonRequest<PinglunDataInfo> fastJsonCommunity = new FastJsonRequest<PinglunDataInfo>(Request.Method.POST, url, PinglunDataInfo.class, null, new Response.Listener<PinglunDataInfo>() {
            @Override
            public void onResponse(PinglunDataInfo pinglunDataInfo) {

                DataStatus status = pinglunDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    pinglunData = pinglunDataInfo.getData();
                    if(pinglunData!=null){


                         pinglunEdit.setText("");
                        loadingTag=2;//重新加载
                        initData(promotePostsData.getId());


                        Toast.makeText(mContext, ""+pinglunData.getMessage() , Toast.LENGTH_SHORT).show();
                        Log.d("pingluActivity", "pingluActivity 成功返回信息：   " + JSON.toJSONString(pinglunData)+ "++++succeed");
                    }


                } else {
                    // 请求失败
                    Log.d("pingluActivity", "succeded=0  pingluActivity 返回信息 " + JSON.toJSONString(status) + "");
                    Toast.makeText(mContext, "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("pingluActivity", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
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
