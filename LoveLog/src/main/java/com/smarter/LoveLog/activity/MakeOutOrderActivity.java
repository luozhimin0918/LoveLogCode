package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.adapter.RecyclePersonAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.goods.GoodsData;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.jsonModel.PersonParamInfo;
import com.smarter.LoveLog.model.loginData.PersonalDataInfo;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.ui.CircleNetworkImage;
import com.smarter.LoveLog.ui.popwindow.ActionSheetDialog;
import com.smarter.LoveLog.utills.TestUtil;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class MakeOutOrderActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag = "MakeOutOrderActivity";
    @Bind(R.id.iv_adapter_grid_pic)
    NetworkImageView iv_adapter_grid_pic;
    @Bind(R.id.goodDes)
    TextView goodDes;
    @Bind(R.id.backBUt)
    ImageView backBUt;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_out_order_data_view);
        ButterKnife.bind(this);


        getDataIntent();

        setListen();

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    private void setListen() {
        backBUt.setOnClickListener(this);
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

                Log.d("MakeOutOrderActivity", "  Session  " + sessionData.getUid() + "      " + sessionData.getSid());
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
            if(goodsData!=null){
                initRecycleViewVertical();
            }
        }


    }

    public void initRecycleViewVertical() {

        //产品图片
        iv_adapter_grid_pic.setDefaultImageResId(R.drawable.loading_small);
        iv_adapter_grid_pic.setErrorImageResId(R.drawable.loading_small);
        String UserimageUrl="";
        if(goodsData.getImg().getThumb()!=null){
            UserimageUrl=goodsData.getImg().getThumb();
        }

        if(mQueue.getCache().get(UserimageUrl)==null){
            iv_adapter_grid_pic.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        iv_adapter_grid_pic.setImageUrl(UserimageUrl, AppContextApplication.getInstance().getmImageLoader());




        goodDes.setText(goodsData.getGoods_name());
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {

             case  R.id.backBUt:
                 finish();
                 break;

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




        Log.d("MakeOutOrderActivity", sessinStr + "      ");


        FastJsonRequest<PersonalDataInfo> fastJsonCommunity = new FastJsonRequest<PersonalDataInfo>(Request.Method.POST, url, PersonalDataInfo.class, null, new Response.Listener<PersonalDataInfo>() {
            @Override
            public void onResponse(PersonalDataInfo personalDataInfo) {

                DataStatus status = personalDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    user = personalDataInfo.getData();
                    if(user!=null){
                        SharedPreferences.getInstance().putString("user",JSON.toJSONString(user));
                        initRecycleViewVertical();//ok
                        Log.d("MakeOutOrderActivity", "用户信息：   " + JSON.toJSONString(user)+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("MakeOutOrderActivity", "succeded=00000  " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("MakeOutOrderActivity", "errror" + volleyError.toString() + "");
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
