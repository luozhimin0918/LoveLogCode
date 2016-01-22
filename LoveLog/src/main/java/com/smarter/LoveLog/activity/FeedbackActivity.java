package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.feedback.FeedDataInfo;
import com.smarter.LoveLog.model.feedback.FeedMess;
import com.smarter.LoveLog.model.help.HelpData;
import com.smarter.LoveLog.model.help.HelpDataInfo;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.SessionData;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class FeedbackActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "FeedbackActivity";

    @Bind(R.id.feedTitleText)
    EditText feedTitleText;

    @Bind(R.id.feedBackContent)
    EditText feedBackContent;

    @Bind(R.id.numberContent)
    EditText numberContent;

    @Bind(R.id.backBUt)
    ImageView backBUt;

    @Bind(R.id.tv_right_title)
    TextView tv_right_title;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed_back_view);
        ButterKnife.bind(this);



        getDataIntent();

        setListen();

    }

    private void setListen() {
        backBUt.setOnClickListener(this);
        tv_right_title.setOnClickListener(this);
    }
    SessionData sessionData;
    private void intData() {

        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);

        String  sessionString=SharedPreferences.getInstance().getString("session", "");
        if(isLogin){
            try {
                sessionData = JSON.parseObject(sessionString, SessionData.class);
            } catch (Exception e) {
                e.printStackTrace();
            }

        }

        if(sessionData.getSid()!=null&&!sessionData.getSid().equals("")){
            networkPersonl(sessionData.getUid(),sessionData.getSid());
        }else{
            networkPersonl("","");
        }






    }



    /**
     * 反馈
     */
    FeedMess feedMess;
    private void networkPersonl(String uid,String sid) {
        String url = "http://mapp.aiderizhi.com/?url=/feedback";//
        Map<String, String> mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"session\":{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"},\"title\":\""+feedTitleText.getText().toString()+"\",\"content\":\""+feedBackContent.getText().toString()+"\",\"contact\":\""+numberContent.getText().toString()+"\"}";
        mapTou.put("json", sessinStr);




        Log.d(Tag, sessinStr + "      ");


        FastJsonRequest<FeedDataInfo> fastJsonCommunity = new FastJsonRequest<FeedDataInfo>(Request.Method.POST, url, FeedDataInfo.class, null, new Response.Listener<FeedDataInfo>() {
            @Override
            public void onResponse(FeedDataInfo feedDataInfo) {

                DataStatus status = feedDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    feedMess=feedDataInfo.getData();
                    Toast.makeText(getApplicationContext(),feedMess.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d(Tag, "反馈信息：   " + JSON.toJSONString(status)+ "++++succeed");


                } else {

                    // 请求失败
                    Log.d(Tag, "succeded=00000  " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(Tag, "errror" + volleyError.toString() + "");
                Toast.makeText(getApplicationContext(),"未知错误", Toast.LENGTH_SHORT).show();
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
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
             case R.id.backBUt:
                 finish();
                 break;
             case  R.id.tv_right_title:
                 intData();
                 break;

         }
    }





}
