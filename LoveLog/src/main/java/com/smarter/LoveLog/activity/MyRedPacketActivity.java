package com.smarter.LoveLog.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.TablayoutViewPagerAdapter;
import com.smarter.LoveLog.fragment.OrderAllFragment;
import com.smarter.LoveLog.fragment.OrderCompletedFragment;
import com.smarter.LoveLog.fragment.OrderObligationFragment;
import com.smarter.LoveLog.fragment.OrderWaitTakeOverFragment;
import com.smarter.LoveLog.fragment.RedpacketUnusedFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class MyRedPacketActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "MyRedPacketActivity";
   @Bind(R.id.tabLayout)
   TabLayout tabLayout;
    @Bind(R.id.view_pager)
    ViewPager view_pager;
    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表

    private RedpacketUnusedFragment redpacketUnusedFragment;                          //未使用fragment
    private RedpacketUnusedFragment redpacketUnusedFragment2;
    private RedpacketUnusedFragment redpacketUnusedFragment3;
    TablayoutViewPagerAdapter adapter;
    Activity mActivity;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_red_packet_view);
        ButterKnife.bind(this);
        mActivity=this;


        getDataIntent();
        intData();
        setListen();

    }

    private void setListen() {

    }

    private void intData() {
        //fragment List
        redpacketUnusedFragment=new RedpacketUnusedFragment();
        redpacketUnusedFragment2=new RedpacketUnusedFragment();
        redpacketUnusedFragment3=new RedpacketUnusedFragment();

        list_fragment=new ArrayList<Fragment>();
        list_fragment.add(redpacketUnusedFragment);

        list_fragment.add(redpacketUnusedFragment2);
        list_fragment.add(redpacketUnusedFragment3);

        //tab title List
        list_title=new ArrayList<String>();
        list_title.add("未使用");
        list_title.add("已过期");
        list_title.add("已使用(5)");


        //设置tablayout
        //设置TabLayout的模式
        tabLayout.setTabMode(TabLayout.MODE_FIXED);
        //为TabLayout添加tab名称
        for(int i=0;i<list_title.size();i++){
            tabLayout.addTab(tabLayout.newTab().setText(list_title.get(i)));
        }



        adapter = new TablayoutViewPagerAdapter(getSupportFragmentManager(),list_fragment,list_title);

        //viewpager加载adapter
        view_pager.setAdapter(adapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tabLayout.setupWithViewPager(view_pager);
        view_pager.setCurrentItem(1);



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
