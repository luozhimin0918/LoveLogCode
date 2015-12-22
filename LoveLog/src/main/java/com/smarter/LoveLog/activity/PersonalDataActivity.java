package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.RecyclePersonAdapter;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class PersonalDataActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "PersonalDataActivity";
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;






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
        initRecycleViewVertical();
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
        String[] dataset = new String[]{"用户名/昵称","绑定手机号","性别","会员等级","修改密码","收货地址"};
      String[] dataValue=new String[]{"美羊羊","15083806689","男","V0初级会员","",""};
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





}
