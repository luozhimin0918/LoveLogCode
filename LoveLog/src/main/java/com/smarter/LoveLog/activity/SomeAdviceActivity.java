package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioGroup;

import com.smarter.LoveLog.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class SomeAdviceActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "SomeAdviceActivity";
/*  @Bind(R.id.group)
  public RadioGroup group1;*/







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_some_advice_view);
        ButterKnife.bind(this);



        getDataIntent();
        intData();
        setListen();

    }

    private void setListen() {

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


         }
    }





}
