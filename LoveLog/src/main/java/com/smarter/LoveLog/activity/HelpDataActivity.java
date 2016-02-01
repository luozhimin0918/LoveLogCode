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
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.adapter.RecyclePersonAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.help.HelpData;
import com.smarter.LoveLog.model.help.HelpDataInfo;
import com.smarter.LoveLog.model.help.HelpDataList;
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
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class HelpDataActivity extends BaseFragmentActivity implements View.OnClickListener,RecyclePersonAdapter.OnItemClickListener{
    String Tag= "HelpDataActivity";
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;


    @Bind(R.id.backBUt)
    ImageView backBUt;










    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_data_view);
        ButterKnife.bind(this);



        getDataIntent();

        setListen();

    }

    @Override
    protected void onResume() {
        super.onResume();
        intData();
    }

    private void setListen() {
        backBUt.setOnClickListener(this);
    }
    SessionData sessionData;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void intData() {

        Boolean isLogin =SharedPreferences.getInstance().getBoolean("islogin", false);

        String  sessionString=SharedPreferences.getInstance().getString("session", "");
        try {
            sessionData = JSON.parseObject(sessionString,SessionData.class);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(sessionData==null){
            sessionData=new SessionData();
            sessionData.setSid("");
            sessionData.setUid("");
        }

                    networkPersonl(sessionData.getUid(),sessionData.getSid());

                Log.d("HelpDataActivity","  Session  "+ sessionData.getUid() + "      "+sessionData.getSid());



    }

    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            String  str = intent.getStringExtra("ObjectData");
           // Toast.makeText(this,str+"",Toast.LENGTH_LONG).show();
        }


    }

    public void initRecycleViewVertical(){




        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 默认是Vertical，可以不写
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);

        // 创建数据集
        List<HelpDataList> helpDataLists=helpData.getList();
        String[]   dataset = new String[helpDataLists.size()];
        String[]   dataValue=new String[helpDataLists.size()];




            for(int i=0;i<helpDataLists.size();i++){
                dataset[i]=helpDataLists.get(i).getName();
            }


        // 创建Adapter，并指定数据集
        RecyclePersonAdapter adapter = new RecyclePersonAdapter(dataset,dataValue);
        // 设置Adapter
        recyclerView.setAdapter(adapter);
        adapter.setOnItemClickListener(this);
    }







    private final int PIC_FROM_CAMERA = 1;
    private final int PIC_FROM＿LOCALPHOTO = 0;
    private Uri photoUri;
    @Override
    public void onClick(View v) {
         switch (v.getId()){

             case  R.id.backBUt:
                 finish();
                 break;
         }
    }











    /**
     * 获取help信息
     */
    HelpData helpData;
    private void networkPersonl(String uid,String sid) {
        String url = "http://mapp.aiderizhi.com/?url=/help/index";//
        Map<String, String> mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"session\":{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"}}";
        mapTou.put("json", sessinStr);




        Log.d("HelpDataActivity", sessinStr + "      ");


        FastJsonRequest<HelpDataInfo> fastJsonCommunity = new FastJsonRequest<HelpDataInfo>(Request.Method.POST, url, HelpDataInfo.class, null, new Response.Listener<HelpDataInfo>() {
            @Override
            public void onResponse(HelpDataInfo helpDataInfo) {

                DataStatus status = helpDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    helpData = helpDataInfo.getData();
                    if(helpData!=null&&helpData.getList().size()>0){
                        initRecycleViewVertical();//ok
                        Log.d("HelpDataActivity", "帮助信息：   " + JSON.toJSONString(helpData)+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("HelpDataActivity", "succeded=00000  " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("HelpDataActivity", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //fastJsonCommunity.setTag(TAG);
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }


    /**
     * 上传头像
     *//*
    User  userTou;
    private void networkUpTouMig(String paramNet) {
        String url = "http://mapp.aiderizhi.com/?url=/user/modify";//
        Map<String, String> mapTou = new HashMap<String, String>();
        mapTou.put("json", paramNet);




        Log.d("HelpDataActivity", paramNet + "      ");


        FastJsonRequest<PersonalDataInfo> fastJsonCommunity = new FastJsonRequest<PersonalDataInfo>(Request.Method.POST, url, PersonalDataInfo.class, null, new Response.Listener<PersonalDataInfo>() {
            @Override
            public void onResponse(PersonalDataInfo personalDataInfo) {

                DataStatus status = personalDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    userTou = personalDataInfo.getData();
                    if(userTou!=null){
                        intData();
//                        SharedPreferences.getInstance().putString("user",JSON.toJSONString(user));
//                        initRecycleViewVertical();//ok
                        Log.d("HelpDataActivity", "modift 成功返回信息：   " + JSON.toJSONString(userTou)+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("HelpDataActivity", "succeded=0  modift 返回信息 " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("HelpDataActivity", "errror" + volleyError.toString() + "");
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

    //个人资料修改Item点击处理
    @Override
    public void onItemClickAdapter(int ischeckArray) {
            Intent intent2 = new Intent(this, HelpDataTwoActivity.class);
            Bundle bundle = new Bundle();
            bundle.putSerializable("twoHelpdata",helpData.getList().get(ischeckArray));
            intent2.putExtras(bundle);
            this.startActivity(intent2);

    }






}
