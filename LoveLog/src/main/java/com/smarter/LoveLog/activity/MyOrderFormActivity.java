package com.smarter.LoveLog.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.fragment.OrderAllFragment;
import com.smarter.LoveLog.fragment.OrderAwaitShipFragment;
import com.smarter.LoveLog.fragment.OrderShippedFragment;
import com.smarter.LoveLog.fragment.OrderaWaitPayFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class MyOrderFormActivity extends BaseFragmentActivity implements View.OnClickListener,OnTabSelectListener {
    String Tag= "MyOrderFormActivity";
   @Bind(R.id.tl_2)
   SlidingTabLayout tabLayout_2;
    @Bind(R.id.view_pager)
    ViewPager vp;
    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表

    private OrderAllFragment orderAllFragment;                          //全部fragment
    private OrderaWaitPayFragment orderaWaitPayFragment;            //待付款fragment
    private OrderAwaitShipFragment orderAwaitShipFragment;        //待发货fragment
    private OrderShippedFragment orderShippedFragment;              //待收货fragment
    Activity mActivity;
    Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_form_view);
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
        orderAllFragment=new OrderAllFragment();
        orderaWaitPayFragment=new OrderaWaitPayFragment();
        orderAwaitShipFragment=new OrderAwaitShipFragment();
        orderShippedFragment=new OrderShippedFragment();
        list_fragment=new ArrayList<Fragment>();
        list_fragment.add(orderAllFragment);
        list_fragment.add(orderaWaitPayFragment);
        list_fragment.add(orderAwaitShipFragment);
        list_fragment.add(orderShippedFragment);
        //tab title List
        list_title=new ArrayList<String>();
        list_title.add("全部");
        list_title.add("待付款");
        list_title.add("待发货");
        list_title.add("待收货");


        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabLayout_2.setViewPager(vp);
        tabLayout_2.setOnTabSelectListener(this);
        tabLayout_2.showDot(0);
        vp.setCurrentItem(0);
        tabLayout_2.showMsg(1, 5);
        tabLayout_2.setMsgMargin(1, 0.0f, 10.0f);

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
