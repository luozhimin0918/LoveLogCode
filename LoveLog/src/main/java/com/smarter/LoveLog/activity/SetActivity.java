package com.smarter.LoveLog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.LoginDataActi;
import com.smarter.LoveLog.model.loginData.LoginDataInfo;
import com.smarter.LoveLog.model.loginData.SessionData;

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
                         SessionData  sessionData = AppContextApplication.LoginInfoAll.getSession();
                        if(sessionData!=null){
                            networkLoginOut(sessionData.getUid(),sessionData.getSid());
                            Log.d("SetActivity","  Session  "+ sessionData.getUid() + "      "+sessionData.getSid());
                        }

                   }

                 break;

         }
    }


    public LoginDataActi loginDataActi;
    private void networkLoginOut(String uid,String sid) {
        String url = "http://mapp.aiderizhi.com/?url=/user/logout";//
        Map<String, String> mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"session\":{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"}}";
        mapTou.put("json", sessinStr);




        Log.d("SetActivity", sessinStr + "      ");


        FastJsonRequest<LoginDataInfo> fastJsonCommunity = new FastJsonRequest<LoginDataInfo>(Request.Method.POST, url, LoginDataInfo.class, mapTou, new Response.Listener<LoginDataInfo>() {
            @Override
            public void onResponse(LoginDataInfo loginDataInfo) {

                DataStatus status = loginDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    loginDataActi = loginDataInfo.getData();
                    if(loginDataActi!=null){
//                        AppContextApplication.getInstance().setLoginInfoAll(loginDataActi);//放到全局
                        Toast.makeText(getApplicationContext(), "成功退出" , Toast.LENGTH_SHORT).show();
                        SharedPreferences.getInstance().putBoolean("islogin",false);
//                        SharedPreferences.getInstance().getBoolean("first-time-use", true);
//                        finish();
                        Log.d("SetActivity", "退出信息：   " + JSON.toJSONString(loginDataActi.getUser())+ "++++succeed");
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


        mQueue.add(fastJsonCommunity);
    }

}
