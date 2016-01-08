package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.RecycleAddressAdapter;
import com.smarter.LoveLog.adapter.RecyclePersonAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class AddressManageActivity extends BaseFragmentActivity implements View.OnClickListener,RecycleAddressAdapter.OnCheckDefaultListener{
    String Tag= "AddressManageActivity";
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_address_manage_view);
        ButterKnife.bind(this);



        getDataIntent();
        intData();
        setListen();

    }

    private void setListen() {

    }

    private void intData() {
        initRecycleViewVertical();
    }





    // 创建数据集
    String[] dataset = new String[]{"张三","美女","拉丁","弟弟","火热","额额"};
    String[] dataValue=new String[]{"15083806689","15083806689","15083806689","15083806689","15083806689","15083806689"};
    Boolean[] isdefuals=new Boolean[]{true,false,false,false,false,false};
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
       adapter = new RecycleAddressAdapter(dataset,dataValue,isdefuals);
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


         }
    }


    @Override
    public void oncheckOK(Boolean[] ischeckArray) {
        isdefuals=ischeckArray;
        adapter.notifyDataSetChanged();
    }
}