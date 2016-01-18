package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.RecycleAddressAdapter;
import com.smarter.LoveLog.adapter.RecyclePersonAdapter;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.address.AddressData;
import com.smarter.LoveLog.model.address.AddressDataInfo;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.PersonalDataInfo;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.utills.TestUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class AddressManageActivity extends BaseFragmentActivity implements View.OnClickListener,RecycleAddressAdapter.OnCheckDefaultListener{
    String Tag= "AddressManageActivity";
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Bind(R.id.addAddressTop)
    TextView addAddressTop;
    @Bind(R.id.backButon)
    ImageView backButon;









    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manage_view);
        ButterKnife.bind(this);



        getDataIntent();
        setListen();

    }

    @Override
    protected void onResume() {
        intData();

        super.onResume();
    }

    private void setListen() {
        addAddressTop.setOnClickListener(this);
        backButon.setOnClickListener(this);
    }

    private void intData() {




        Boolean isLogin =SharedPreferences.getInstance().getBoolean("islogin", false);
        if(isLogin){
            String  sessionString=SharedPreferences.getInstance().getString("session","");
            SessionData sessionData = JSON.parseObject(sessionString, SessionData.class);
            if(sessionData!=null){

                networkAddreessInfo(sessionData.getUid(), sessionData.getSid());
                Log.d("AddressManageActivity","  Session  "+ sessionData.getUid() + "      "+sessionData.getSid());
            }

        }

    }





    // 创建数据集
//    String[] dataset = new String[]{"张三","美女","拉丁","弟弟","火热","额额"};
//    String[] dataValue=new String[]{"15083806689","15083806689","15083806689","15083806689","15083806689","15083806689"};
//    Boolean[] isdefuals=new Boolean[]{true,false,false,false,false,false};
    // 创建Adapter，并指定数据集
    RecycleAddressAdapter adapter;
    public void initRecycleViewVertical(){

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 默认是Vertical，可以不写
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);


        // 创建Adapter，并指定数据集
       adapter = new RecycleAddressAdapter(addressDataList,this);
        adapter.setOnCheckDefaultListener(this);
        // 设置Adapter
        recyclerView.setAdapter(adapter);

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
             case  R.id.addAddressTop:
                 //挑战到创建收货地址
                 // 界面
                 Intent intent2 = new Intent(this, CreateAddressActivity.class);
               Bundle bundle = new Bundle();
                bundle.putBoolean("isCreateAddress",true);
                intent2.putExtras(bundle);
                 this.startActivity(intent2);
                 break;
             case R.id.backButon:
                 finish();
                 break;
         }
    }


    @Override
    public void oncheckOK(List<AddressData> addressDataLis) {
        addressDataList=addressDataLis;
        adapter.notifyDataSetChanged();
    }





    List<AddressData> addressDataList;
    private void networkAddreessInfo(String uid,String sid) {
        String url = "http://mapp.aiderizhi.com/?url=/address/list";//
        Map<String, String> mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"session\":{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"}}";
        mapTou.put("json", sessinStr);




        Log.d("AddressManageActivity", sessinStr + "      ");


        FastJsonRequest<AddressDataInfo> fastJsonCommunity = new FastJsonRequest<AddressDataInfo>(Request.Method.POST, url, AddressDataInfo.class, null, new Response.Listener<AddressDataInfo>() {
            @Override
            public void onResponse(AddressDataInfo addressDataInfo) {

                DataStatus status = addressDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    addressDataList = addressDataInfo.getData();
                    if(addressDataList!=null&&addressDataList.size()>0){
                        SharedPreferences.getInstance().putString("address-list", JSON.toJSONString(addressDataList));
                        initRecycleViewVertical();//ok
                        Log.d("AddressManageActivity", "地址list信息：   " + JSON.toJSONString(addressDataList)+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("AddressManageActivity", "succeded=00000  " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("AddressManageActivity", "errror" + volleyError.toString() + "");
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
