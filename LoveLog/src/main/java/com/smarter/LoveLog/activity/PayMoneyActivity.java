package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.toolbox.NetworkImageView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.model.goods.GoodsData;
import com.smarter.LoveLog.model.loginData.SessionData;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class PayMoneyActivity extends BaseFragmentActivity implements View.OnClickListener {
    String Tag = "PayMoneyActivity";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_money_view);
        ButterKnife.bind(this);


        getDataIntent();

        setListen();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setListen() {

    }

    SessionData sessionData;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void intData() {

        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
        if (isLogin) {
            String sessionString = SharedPreferences.getInstance().getString("session", "");
            sessionData = JSON.parseObject(sessionString, SessionData.class);
            if (sessionData != null) {

//                networkPersonl(sessionData.getUid(), sessionData.getSid());

                Log.d("PayMoneyActivity", "  Session  " + sessionData.getUid() + "      " + sessionData.getSid());
            }

        } else {
            Toast.makeText(getApplicationContext(), "未登录，请先登录", Toast.LENGTH_SHORT).show();
        }


    }

    GoodsData goodsData;

    private void getDataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            goodsData = (GoodsData) intent.getSerializableExtra("goods");
            // Toast.makeText(this,str+"",Toast.LENGTH_LONG).show();
            if (goodsData != null) {
            }
        }


    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {


        }
    }


    /**
     * 获取个人资料
     *//*
    User  user;
    private void networkPersonl(String uid,String sid) {
        String url = "http://mapp.aiderizhi.com/?url=/user/info";//
        Map<String, String> mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"session\":{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"}}";
        mapTou.put("json", sessinStr);




        Log.d("PayMoneyActivity", sessinStr + "      ");


        FastJsonRequest<PersonalDataInfo> fastJsonCommunity = new FastJsonRequest<PersonalDataInfo>(Request.Method.POST, url, PersonalDataInfo.class, null, new Response.Listener<PersonalDataInfo>() {
            @Override
            public void onResponse(PersonalDataInfo personalDataInfo) {

                DataStatus status = personalDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    user = personalDataInfo.getData();
                    if(user!=null){
                        SharedPreferences.getInstance().putString("user",JSON.toJSONString(user));
                        initRecycleViewVertical();//ok
                        Log.d("PayMoneyActivity", "用户信息：   " + JSON.toJSONString(user)+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("PayMoneyActivity", "succeded=00000  " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("PayMoneyActivity", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //fastJsonCommunity.setTag(TAG);
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }
*/


}
