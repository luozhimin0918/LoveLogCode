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
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.RecyclePinglunGoodsAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.ChatEmoji;
import com.smarter.LoveLog.model.PaginationJson;
import com.smarter.LoveLog.model.community.InvitationDataPinglunActi;
import com.smarter.LoveLog.model.community.PinglunData;
import com.smarter.LoveLog.model.community.PinglunDataInfo;
import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.model.goods.CmtGoods;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.utills.DeviceUtil;
import com.smarter.LoveLog.utills.FaceConversionUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Handler;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class GoodsAllPinglunSendActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "GoodsAllPinglunSendActivity";
    Context  mContext;




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

    @Bind(R.id.btn_send)
    TextView btn_send;

    @Bind(R.id.et_sendmessage)
    EditText pinglunEdit;











    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);



        setContentView(R.layout.activity_invitation_pinglun_all_send_view);
        ButterKnife.bind(this);
        mContext=this;
        getDataIntent();
        setListen();

    }

    private void setListen() {
        backBUt.setOnClickListener(this);
        btn_send.setOnClickListener(this);

    }

    private void intData() {





    }


    PromotePostsData promotePostsData;
    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            promotePostsData= (PromotePostsData) intent.getSerializableExtra("allpinglun");







        }


    }





    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case  R.id.backBUt:
                 finish();
                 break;
             case  R.id.btn_send:

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

                  String param="{\"type\":\"0\",\"id\":\""+promotePostsData.getId()+"\",\"reply_id\":\"\",\"content\":\""+pinglunEdit.getText().toString()+"\",\"session\":{\"uid\":\""+sessionData.getUid()+"\",\"sid\":\""+sessionData.getSid()+"\"}}";
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




        Log.d(Tag, paramNet + "      ");


        FastJsonRequest<PinglunDataInfo> fastJsonCommunity = new FastJsonRequest<PinglunDataInfo>(Request.Method.POST, url, PinglunDataInfo.class, null, new Response.Listener<PinglunDataInfo>() {
            @Override
            public void onResponse(PinglunDataInfo pinglunDataInfo) {

                DataStatus status = pinglunDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    pinglunData = pinglunDataInfo.getData();
                    if(pinglunData!=null){





                        Toast.makeText(mContext, ""+pinglunData.getMessage() , Toast.LENGTH_SHORT).show();
                        Log.d(Tag, "pingluActivity 成功返回信息：   " + JSON.toJSONString(pinglunData)+ "++++succeed");


                        finish();
                    }


                } else {
                    // 请求失败
                    Log.d(Tag, "succeded=0  pingluActivity 返回信息 " + JSON.toJSONString(status) + "");
                    Toast.makeText(mContext, "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(Tag, "errror" + volleyError.toString() + "");
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

                        //
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
