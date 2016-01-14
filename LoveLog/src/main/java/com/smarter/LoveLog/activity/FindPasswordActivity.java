package com.smarter.LoveLog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import butterknife.Bind;
import butterknife.ButterKnife;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.LoginDataActi;
import com.smarter.LoveLog.model.register.RegisterInfo;
import com.smarter.LoveLog.model.sms.SmsData;
import com.smarter.LoveLog.model.sms.SmsInfo;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/30.
 */
public class FindPasswordActivity extends BaseFragmentActivity implements View.OnClickListener{

    @Bind(R.id.iv_shao)
    ImageView iv_shao;
    @Bind(R.id.tv_right_title)
    TextView tv_right_title;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.yanzhengWord)
    EditText yanzhengWord;

    @Bind(R.id.linearYanzhenma)
    LinearLayout linearYanzhenma;
    @Bind(R.id.password)
    EditText password;

    @Bind(R.id.linearSeePassword)
    LinearLayout linearSeePassword;

    @Bind(R.id.resetPass)
    LinearLayout resetPass;




  RequestQueue mQueue;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_findpword_view);
        ButterKnife.bind(this);
        mQueue =  AppContextApplication.getInstance().getmRequestQueue();
        getDataIntent();

        intData();
        setListen();

    }

    private void setListen() {
        iv_shao.setOnClickListener(this);
        tv_right_title.setOnClickListener(this);
        linearYanzhenma.setOnClickListener(this);
        resetPass.setOnClickListener(this);
        linearSeePassword.setOnClickListener(this);
    }

    private void intData() {

    }

    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            String  str = intent.getStringExtra("ObjectData");
//            Toast.makeText(this,str+"",Toast.LENGTH_LONG).show();
        }


    }

    Boolean  isShowPassword=false;
    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.linearSeePassword:
                 if(!isShowPassword){

                     //如果选中，显示密码
                     password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                     isShowPassword=true;
                 }else{
                     password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                     isShowPassword=false;
                 }
                 break;
            case R.id.iv_shao:
            case R.id.tv_right_title:
                finish();
            break;



            case R.id.linearYanzhenma:
                 if(phone.getText()!=null&&!phone.getText().equals("")){
                     String url = "http://mapp.aiderizhi.com/?url=/sms";//
                     String strParam="{\"mobile\":\""+phone.getText()+"\",\"type\":\"reset_pwd\"}";
                     Log.d("findPasswordActivity", "sms 参数" + strParam + "      ");
                     networkVolleySmsData( url, strParam, "", "");
                 }
                 break;
             case R.id.resetPass:
                 String param ="{\"mobile\":\""+phone.getText()+"\",\"vcode\":\""+yanzhengWord.getText()+"\",\"password\":\""+password.getText()+"\"}";
                 String url = "http://mapp.aiderizhi.com/?url=/user/reset_pwd";//
                 //  TestUtil.VolleyGetRospone(map, Request.Method.POST,url);

                 networkVolleyRestPass(url, param, "", "");
                 break;


         }
    }





    public SmsData smsData;//验证码信息
    private void networkVolleySmsData(String url,String param,String uid,String sid) {

        Map<String, String> mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"session\":{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"}}";
        mapTou.put("json", sessinStr);


        Map<String, String> map = new HashMap<String, String>();
        map.put("json", param);

        Log.d("findPasswordActivity", "Session : " + sessinStr + "      ");


        FastJsonRequest<SmsInfo> fastJsonCommunity = new FastJsonRequest<SmsInfo>(Request.Method.POST, url, SmsInfo.class, mapTou, new Response.Listener<SmsInfo>() {
            @Override
            public void onResponse(SmsInfo smsInfo) {

                DataStatus status = smsInfo.getStatus();

                if (status.getSucceed() == 1) {
                    smsData=smsInfo.getData();
                    Log.d("findPasswordActivity", "验证码返回信息  " +  "++++succeed"+ JSON.toJSONString(smsInfo));

                    Log.d("findPasswordActivity", "验证码" + smsData.getVcode() + "++++succeed" );



                } else {

                    // 请求失败
                    Log.d("findPasswordActivity", "验证码失败返回信息  " + JSON.toJSONString(status));
                    Toast.makeText(getApplicationContext(),""+status.getError_desc(),Toast.LENGTH_SHORT).show();

                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("findPasswordActivity", "errror" + volleyError.toString() + "");
            }
        });

        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);
    }







    public LoginDataActi regiterData;//找回密码返回信息
    private void networkVolleyRestPass(String url,String param,String uid,String sid) {

        Map<String, String> mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"session\":{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"}}";
        mapTou.put("json", sessinStr);


        Map<String, String> map = new HashMap<String, String>();
        map.put("json", param);

        Log.d("findPasswordActivity", "Session : " + sessinStr + "      ");


        FastJsonRequest<RegisterInfo> fastJsonCommunity = new FastJsonRequest<RegisterInfo>(Request.Method.POST, url, RegisterInfo.class, mapTou, new Response.Listener<RegisterInfo>() {
            @Override
            public void onResponse(RegisterInfo registerInfo) {

                DataStatus status = registerInfo.getStatus();

                if (status.getSucceed() == 1) {
                    regiterData=registerInfo.getData();
                    AppContextApplication.getInstance().setLoginInfoAll(regiterData);//放到全局
                    Log.d("findPasswordActivity", "找回密码返回信息  " + regiterData.getUser().getMobile() + "++++succeed" + JSON.toJSONString(regiterData));
                    Toast.makeText(getApplicationContext(),"重置密码成功",Toast.LENGTH_SHORT).show();
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                            finish();
                        }
                    }, 1000);


                } else {

                    // 请求失败
                    Log.d("findPasswordActivity", "找回密码失败返回信息  " + JSON.toJSONString(status));
                    Toast.makeText(getApplicationContext(),""+status.getError_desc(),Toast.LENGTH_SHORT).show();
                }




            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("findPasswordActivity", "errror" + volleyError.toString() + "");
            }
        });

        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);
    }



}
