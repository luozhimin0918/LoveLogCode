package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
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
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.category.InvitationDataDeatil;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.LoginDataActi;
import com.smarter.LoveLog.model.loginData.LoginDataInfo;
import com.smarter.LoveLog.ui.popwindow.BabyPopWindow;
import com.smarter.LoveLog.ui.productViewPager.AutoLoopViewPager;
import com.smarter.LoveLog.ui.productViewPager.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/11/30.
 */
public class LoginActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "LoginActivity";
    @Bind(R.id.logon)
    LinearLayout logon;
    @Bind(R.id.linearSeePassword)
    LinearLayout linearSeePassword;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.backBut)
    ImageView backBut;
    @Bind(R.id.regitstBut)
    TextView regitstBut;


    @Bind(R.id.forgetPass)
    LinearLayout forgetPass;





   Context mContext;

   RequestQueue mQueue;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_view);
        ButterKnife.bind(this);
        getDataIntent();
        mContext=this;
        mQueue =  AppContextApplication.getInstance().getmRequestQueue();
        intData();
        setListen();

    }

    private void setListen() {
        logon.setOnClickListener(this);
        backBut.setOnClickListener(this);
        regitstBut.setOnClickListener(this);
        linearSeePassword.setOnClickListener(this);
        forgetPass.setOnClickListener(this);
        password.addTextChangedListener(new EditChangedListener());
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void intData() {
        logon.setBackground(getResources().getDrawable(R.drawable.yuanjiao_love_red_bg_alpha));
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            String  str = intent.getStringExtra("ObjectData");
           // Toast.makeText(this,str+"",Toast.LENGTH_SHORT).show();
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
             case  R.id.logon:
                 LoginBut(phone.getText().toString(),password.getText().toString(),"","");
                 break;
             case R.id.backBut:
                 finish();
                 break;
             case  R.id.regitstBut:
                 //跳转到注册界面
                 Intent intent = new Intent(mContext, QuaiRegitedActivity.class);
              /*  Bundle bundle = new Bundle();
                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                intent.putExtras(bundle);*/
                 mContext.startActivity(intent);
                 break;
             case  R.id.forgetPass:
                 //跳转到注册界面
                 Intent intent2 = new Intent(mContext, FindPasswordActivity.class);
              /*  Bundle bundle = new Bundle();
                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                intent.putExtras(bundle);*/
                 mContext.startActivity(intent2);
                 break;

         }
    }

    public LoginDataActi  loginDataActi;
    private void LoginBut( String user,String pass,String uid,String sid) {
        String url = "http://mapp.aiderizhi.com/?url=/user/signin";//
        Map<String, String>   mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"session\":{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"}}";
        mapTou.put("json", sessinStr);


        Map<String, String> map = new HashMap<String, String>();
        String  value="{\"account\":\""+user+"\",\"password\":\""+pass+"\"}";
        map.put("json", value);

        Log.d("loginActivity", sessinStr + "      "+value );


        FastJsonRequest<LoginDataInfo> fastJsonCommunity = new FastJsonRequest<LoginDataInfo>(Request.Method.POST, url, LoginDataInfo.class, mapTou, new Response.Listener<LoginDataInfo>() {
            @Override
            public void onResponse(LoginDataInfo loginDataInfo) {

                DataStatus status = loginDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    loginDataActi = loginDataInfo.getData();
                    if(loginDataActi!=null){
                        SharedPreferences.getInstance().putBoolean("islogin", true);
                        SharedPreferences.getInstance().putString("session", JSON.toJSONString(loginDataActi.getSession()));
                        SharedPreferences.getInstance().putString("user",JSON.toJSONString(loginDataActi.getUser()));
                       finish();
                        Log.d("loginActivity", "登录信息：   "+JSON.toJSONString(loginDataActi.getSession())+"" + JSON.toJSONString(loginDataActi.getUser())+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("loginActivity", "succeded=0  " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(),""+status.getError_desc(),Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("loginActivity", "errror" + volleyError.toString() + "");
                Toast.makeText(mContext,"服务器出错啦。。。",Toast.LENGTH_SHORT).show();
            }
        });

        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);
    }

    class EditChangedListener implements TextWatcher {
        private CharSequence temp;//监听前的文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置
        private final int charMaxNum = 10;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if(s.length()==5){
                logon.setBackground(getResources().getDrawable(R.drawable.login_button));
                logon.setClickable(true);
            }
            if(s.length()<5){
                logon.setBackground(getResources().getDrawable(R.drawable.yuanjiao_love_red_bg_alpha));
                logon.setClickable(false);
            }
                Log.i(Tag, "输入文本之前的状态");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

                Log.i(Tag, "输入文字中的状态，count是一次性输入字符数");

        }

        @Override
        public void afterTextChanged(Editable s) {
                Log.i(Tag, "输入文字后的状态");
            /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */


        }
    }

}
