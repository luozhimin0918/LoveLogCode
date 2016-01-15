package com.smarter.LoveLog.activity;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.loginData.LogingOutInfo;
import com.smarter.LoveLog.model.loginData.LogingOutMess;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.utills.TestUtil;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class SetActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "SetActivity";
   @Bind(R.id.loginOut)
   LinearLayout loginOut;

  RequestQueue mQueue;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_view);
        ButterKnife.bind(this);

        mQueue =  AppContextApplication.getInstance().getmRequestQueue();

        getDataIntent();
        intData();
        setListen();

    }

    private void setListen() {
        loginOut.setOnClickListener(this);
    }

    private void intData() {
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
             case  R.id.loginOut:
                   Boolean isLogin =SharedPreferences.getInstance().getBoolean("islogin", false);
                       if(isLogin){
                           String  sessionString=SharedPreferences.getInstance().getString("session","");
                           SessionData sessionData = JSON.parseObject(sessionString,SessionData.class);

                            if(sessionData!=null){
                                networkLoginOut(sessionData.getUid(),sessionData.getSid());
    //                            uploadImg(sessionData.getUid(),sessionData.getSid());
                                Log.d("SetActivity","  Session  "+ sessionData.getUid() + "      "+sessionData.getSid());
                            }
                       }else{
                           Toast.makeText(getApplicationContext(), "未登录，请先登录" , Toast.LENGTH_SHORT).show();
                       }

              break;

         }
    }

    private void uploadImg(String uid,String sid) {
        Bitmap bitmap=TestUtil. drawableToBitamp(getResources().getDrawable(R.mipmap.error_default)) ;

        byte[]  bytes =TestUtil.getBitmapByte(bitmap);
        String srt2="";
        try {
            srt2=new String(bytes,"UTF-8");
//            srt2= TestUtil.encodeBase64(bytes);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
//        String url = "http://mapp.aiderizhi.com/?url=/user/modify";//
                String url = "http://mapp.aiderizhi.com/?url=/user/info";//
//                String url = "http://mapp.aiderizhi.com/?url=/user/logout";//
//        String url = "http://mapp.aiderizhi.com/?url=/post/category";//


        Map<String, String> mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"action\":\"avatar\",\"avatar\":\""+srt2+"\"}";
//        mapTou.put("json", sessinStr);


        Map<String, String> mapTou2 = new HashMap<String, String>();
        String  sessinStr2 ="{\"session\":{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"}}";
//        String  sessinStr2 ="{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"}";
//        String  sessinStr2="{\"id\":\"2\",\"pagination\":{\"count\":\"6\",\"page\":\"2\"}}";
        mapTou2.put("json", sessinStr2);
//        mapTou2.put("session",sessinStr2);
//          mapTou2.put("uid",uid);
//          mapTou2.put("sid",sid);



        Log.d("TestUtil", "" + "              " +sessinStr2);
        TestUtil.VolleyGetRospone(mapTou,mapTou2, Request.Method.POST,url);
    }


    public LogingOutMess loginMess;
    private void networkLoginOut(String uid,String sid) {
        String url = "http://mapp.aiderizhi.com/?url=/user/logout";//
        Map<String, String> mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"session\":{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"}}";
        mapTou.put("json", sessinStr);




        Log.d("SetActivity", sessinStr + "      ");


        FastJsonRequest<LogingOutInfo> fastJsonCommunity = new FastJsonRequest<LogingOutInfo>(Request.Method.POST, url, LogingOutInfo.class, mapTou, new Response.Listener<LogingOutInfo>() {
            @Override
            public void onResponse(LogingOutInfo loginDataInfo) {

                DataStatus status = loginDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    loginMess = loginDataInfo.getData();
                    if(loginMess!=null){
                        Toast.makeText(getApplicationContext(), "成功退出" , Toast.LENGTH_SHORT).show();
                        SharedPreferences.getInstance().putBoolean("islogin",false);
                        finish();
                        Log.d("SetActivity", "退出信息：   " + JSON.toJSONString(loginMess)+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("SetActivity", "succeded=0  " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("SetActivity", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //fastJsonCommunity.setTag(TAG);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }

}
