package com.smarter.LoveLog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
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
import com.smarter.LoveLog.model.home.DataStatusOne;
import com.smarter.LoveLog.model.home.DataStatus;
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


    @Bind(R.id.helpLinear)
    LinearLayout helpLinear;

    @Bind(R.id.fackLinear)
    LinearLayout fackLinear;

    @Bind(R.id.back_but)
    ImageView back_but;







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
        helpLinear.setOnClickListener(this);
        fackLinear.setOnClickListener(this);
        back_but.setOnClickListener(this);
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


                 /**
                  * 手机号登录退出
                  */
                   Boolean isLogin =SharedPreferences.getInstance().getBoolean("islogin", false);
                       if(isLogin){
                           String  sessionString=SharedPreferences.getInstance().getString("session","");
                           SessionData sessionData = JSON.parseObject(sessionString,SessionData.class);

                            if(sessionData!=null){


                              /*  //因退出登录接口有问题。所有执行一下操作
                                Toast.makeText(getApplicationContext(), "成功退出" , Toast.LENGTH_SHORT).show();
                                SharedPreferences.getInstance().putBoolean("islogin",false);
                                finish();*/


                                networkLoginOut(sessionData.getUid(), sessionData.getSid());//
                                Log.d("SetActivity", "  Session  " + sessionData.getUid() + "      " + sessionData.getSid());




                            }
                       }else{
                           Toast.makeText(getApplicationContext(), "未登录，请先登录" , Toast.LENGTH_SHORT).show();
                       }

                 /**
                  * QQ登录退出
                  */



                 /**
                  * 新浪微博登录退出
                  */


              break;

             case  R.id.helpLinear:
                 //帮助
                 Intent intent = new Intent(this, HelpDataActivity.class);
                  /*  Bundle bundle = new Bundle();
                    bundle.putSerializable("PromotePostsData", (Serializable) pp);
                    intent.putExtras(bundle);*/
                 this.startActivity(intent);
                 break;
             case R.id.fackLinear:
                 //帮助
                 Intent intent2 = new Intent(this, FeedbackActivity.class);
                  /*  Bundle bundle = new Bundle();
                    bundle.putSerializable("PromotePostsData", (Serializable) pp);
                    intent.putExtras(bundle);*/
                 this.startActivity(intent2);
                 break;
             case  R.id.back_but:
                 finish();
                 break;

         }
    }




    public DataStatus loginMess;
    private void networkLoginOut(String uid,String sid) {
        String url = "http://mapp.aiderizhi.com/?url=/user/logout";//
        Map<String, String> mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"session\":{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"}}";
        mapTou.put("json", sessinStr);




        Log.d("SetActivity", sessinStr + "      ");


        FastJsonRequest<DataStatusOne> fastJsonCommunity = new FastJsonRequest<DataStatusOne>(Request.Method.POST, url, DataStatusOne.class, null, new Response.Listener<DataStatusOne>() {
            @Override
            public void onResponse(DataStatusOne loginDataInfo) {

                DataStatus status = loginDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    loginMess = status;
                    if(loginMess!=null){
                        Toast.makeText(getApplicationContext(), "成功退出" , Toast.LENGTH_SHORT).show();
                        SharedPreferences.getInstance().putBoolean("islogin",false);


                        Intent intent = new Intent();
                        intent.setAction("UpShopCarNum");
                        intent.putExtra("update", "ok");
                        getApplicationContext().sendBroadcast(intent);

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
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }

}
