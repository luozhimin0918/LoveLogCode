package com.smarter.LoveLog.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.fragment.RedpacketUnusedFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class MyRedPacketActivity extends BaseFragmentActivity implements View.OnClickListener,OnTabSelectListener{
    String Tag= "MyRedPacketActivity";
   @Bind(R.id.tl_2)
   SlidingTabLayout tabLayout_2;
    @Bind(R.id.view_pager)
    ViewPager vp;
    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表

    private RedpacketUnusedFragment redpacketUnusedFragment;                          //未使用fragment
    private RedpacketUnusedFragment redpacketUnusedFragment2;
    private RedpacketUnusedFragment redpacketUnusedFragment3;
    Activity mActivity;
    Context mContext;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_red_packet_view);
        ButterKnife.bind(this);
        mActivity=this;
        mContext=this;

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
        list_title.add("已使用");


        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabLayout_2.setViewPager(vp);
        tabLayout_2.setOnTabSelectListener(this);
        tabLayout_2.showDot(0);
        vp.setCurrentItem(0);
        tabLayout_2.showMsg(1, 5);
        tabLayout_2.setMsgMargin(1, 10.0f, 10.0f);



    }

    @Override
    public void onTabSelect(int position) {
//        Toast.makeText(mContext, "onTabSelect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabReselect(int position) {
//        Toast.makeText(mContext, "onTabReselect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return list_fragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list_title.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
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



}
