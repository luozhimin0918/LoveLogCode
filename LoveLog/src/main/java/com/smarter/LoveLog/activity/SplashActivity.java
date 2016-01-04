package com.smarter.LoveLog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;

import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.SharedPreferences;

import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class SplashActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "SplashActivity";
/*  @Bind(R.id.group)
  public RadioGroup group1;*/







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_view);
        ButterKnife.bind(this);



        getDataIntent();
        intData();
        setListen();

    }

    private void setListen() {

    }

    private void intData() {
        boolean firstTimeUse = SharedPreferences.getInstance().getBoolean("first-time-use", true);

       if(firstTimeUse){
           initLaunchLogo();
       }
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




    private void initLaunchLogo() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
            }
        }, 500);
    }



}
