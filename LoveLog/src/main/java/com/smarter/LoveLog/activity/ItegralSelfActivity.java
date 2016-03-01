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
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.adapter.RecycleItegralSelfAdapter;
import com.smarter.LoveLog.adapter.RecyclePersonAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.itegralself.ItegralDataInfo;
import com.smarter.LoveLog.model.itegralself.Itegraldata;
import com.smarter.LoveLog.model.jsonModel.PersonParamInfo;
import com.smarter.LoveLog.model.loginData.PersonalDataInfo;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.ui.CircleNetworkImage;
import com.smarter.LoveLog.ui.popwindow.ActionSheetDialog;
import com.smarter.LoveLog.utills.TestUtil;
import com.smarter.LoveLog.utills.ViewUtill;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class ItegralSelfActivity extends BaseFragmentActivity implements View.OnClickListener,RecyclePersonAdapter.OnItemClickListener{
    String Tag= "ItegralSelfActivity";
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;



    @Bind(R.id.backBUt)
    ImageView backBUt;

    @Bind(R.id.jifenZong)
    TextView jifenZong;

    @Bind(R.id.itegralNone)
    ImageView itegralNone;














    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_itegral_self_data_view);
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
        if(isLogin){
            String  sessionString=SharedPreferences.getInstance().getString("session", "");
           sessionData = JSON.parseObject(sessionString,SessionData.class);
            if(sessionData!=null){

                    networkPersonl(sessionData.getUid(),sessionData.getSid());

                Log.d(Tag,"  Session  "+ sessionData.getUid() + "      "+sessionData.getSid());
            }

        }else{
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








        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 默认是Vertical，可以不写
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);


        // 创建Adapter，并指定数据集
        RecycleItegralSelfAdapter adapter = new RecycleItegralSelfAdapter(itegraldata.getList());
        // 设置Adapter
        recyclerView.setAdapter(adapter);
//        adapter.setOnItemClickListener(this);


        jifenZong.setText(itegraldata.getPay_points());

        if(itegraldata.getList().size()>0&&itegraldata!=null){
            itegralNone.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }else{
            itegralNone.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        }
    }








    @Override
    public void onClick(View v) {
         switch (v.getId()){

             case  R.id.backBUt:
                 finish();
                 break;
         }
    }











    /**
     * 获取积分数据
     */
    Itegraldata itegraldata;
    private void networkPersonl(String uid,String sid) {
        String url = "http://mapp.aiderizhi.com/?url=/user/point";//
        Map<String, String> mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"session\":{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"}}";
        mapTou.put("json", sessinStr);




        Log.d(Tag, sessinStr + "      ");


        FastJsonRequest<ItegralDataInfo> fastJsonCommunity = new FastJsonRequest<ItegralDataInfo>(Request.Method.POST, url, ItegralDataInfo.class, null, new Response.Listener<ItegralDataInfo>() {
            @Override
            public void onResponse(ItegralDataInfo itegralDataInfo) {

                DataStatus status = itegralDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    itegraldata = itegralDataInfo.getData();
                    if(itegraldata!=null){
                        initRecycleViewVertical();//oks
                        Log.d(Tag, "积分信息：   " + JSON.toJSONString(itegraldata)+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d(Tag, "succeded=00000  " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                    if(status.getError_code()==1000){
                        SharedPreferences.getInstance().putBoolean("islogin",false);
                        ViewUtill.ShowAlertDialog(getApplicationContext());
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d(Tag, "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //fastJsonCommunity.setTag(TAG);
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }






    //个人资料修改Item点击处理
    @Override
    public void onItemClickAdapter(int ischeckArray) {

    }
}
