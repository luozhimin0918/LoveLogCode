package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.adapter.RecyclePersonAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.loginData.LogingOutInfo;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.PersonalDataInfo;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.ui.CircleNetworkImage;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class PersonalDataActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "PersonalDataActivity";
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.imageTitle)
    CircleNetworkImage imageTitle;








    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal_data_view);
        ButterKnife.bind(this);



        getDataIntent();
        intData();
        setListen();

    }

    private void setListen() {

    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void intData() {

        Boolean isLogin =SharedPreferences.getInstance().getBoolean("islogin", false);
        if(isLogin){
            String  sessionString=SharedPreferences.getInstance().getString("session","");
            SessionData sessionData = JSON.parseObject(sessionString,SessionData.class);
            if(sessionData!=null){
//                String  userString=SharedPreferences.getInstance().getString("user","");
//                User user1 = JSON.parseObject(userString,User.class);
//                if(user1!=null&&user1.getName()!=null){
//                    initRecycleViewVertical();
//                }else {
                    networkPersonl(sessionData.getUid(),sessionData.getSid());
//                }

                Log.d("PersonalDataActivity","  Session  "+ sessionData.getUid() + "      "+sessionData.getSid());
            }

        }else{
            initRecycleViewVertical();
            Toast.makeText(getApplicationContext(), "未登录，请先登录" , Toast.LENGTH_SHORT).show();
        }


    }

    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            String  str = intent.getStringExtra("ObjectData");
           // Toast.makeText(this,str+"",Toast.LENGTH_LONG).show();
        }


    }

    public void initRecycleViewVertical(){

        //头像
        imageTitle.setDefaultImageResId(R.mipmap.login);
        imageTitle.setErrorImageResId(R.mipmap.login);
        String UserimageUrl="";
        if(user!=null&&user.getAvatar()!=null){
             UserimageUrl=user.getAvatar();
        }

        if(mQueue.getCache().get(UserimageUrl)==null){
            imageTitle.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        imageTitle.setImageUrl(UserimageUrl, AppContextApplication.getInstance().getmImageLoader());




        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 默认是Vertical，可以不写
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);

        // 创建数据集
        String[] dataset = new String[]{"用户名/昵称","绑定手机号","性别","会员等级","修改密码","收货地址"};
//        String[] dataValue=new String[]{"美羊羊","15083806689","男","V0初级会员","",""};
        String[] dataValue=new String[]{"","","","","",""};
        if(user!=null){
            dataValue[0]=user.getName();
            dataValue[1]=user.getMobile();
                        String  sex="";
                        if(user.getSex().equals("1")){
                            sex="男";
                        }
                        if(user.getSex().equals("2")){
                            sex="女";
                        }else{
                            sex="未知";
                        }
            dataValue[2]=sex;
                        String strLevel="";
                        if(user.getRank_level()<3){
                            strLevel="V0初级会员";
                        }else {
                            strLevel="V4高级会员";
                        }
            dataValue[3]=strLevel;
        }

        // 创建Adapter，并指定数据集
        RecyclePersonAdapter adapter = new RecyclePersonAdapter(dataset,dataValue);
        // 设置Adapter
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){


         }
    }







    User  user;
    private void networkPersonl(String uid,String sid) {
        String url = "http://mapp.aiderizhi.com/?url=/user/info";//
        Map<String, String> mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"session\":{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"}}";
        mapTou.put("json", sessinStr);




        Log.d("PersonalDataActivity", sessinStr + "      ");


        FastJsonRequest<PersonalDataInfo> fastJsonCommunity = new FastJsonRequest<PersonalDataInfo>(Request.Method.POST, url, PersonalDataInfo.class, null, new Response.Listener<PersonalDataInfo>() {
            @Override
            public void onResponse(PersonalDataInfo personalDataInfo) {

                DataStatus status = personalDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    user = personalDataInfo.getData();
                    if(user!=null){
                        SharedPreferences.getInstance().putString("user",JSON.toJSONString(user));
                        initRecycleViewVertical();//ok
                        Log.d("PersonalDataActivity", "用户信息：   " + JSON.toJSONString(user)+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("PersonalDataActivity", "succeded=00000  " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("PersonalDataActivity", "errror" + volleyError.toString() + "");
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
