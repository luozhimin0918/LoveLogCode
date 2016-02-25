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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.fragment.OrderAllFragment;
import com.smarter.LoveLog.fragment.OrderAwaitCmtFragment;
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
    @Bind(R.id.tv_top_title)
    TextView tv_top_title;

    @Bind(R.id.tl_2)
    SlidingTabLayout tabLayout_2;
    @Bind(R.id.view_pager)
    ViewPager vp;
    @Bind(R.id.back_but)
    ImageView back_but;


    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表

    private OrderAllFragment orderAllFragment;                          //全部fragment
    private OrderaWaitPayFragment orderaWaitPayFragment;            //待付款fragment
    private OrderAwaitShipFragment orderAwaitShipFragment;        //待发货fragment
    private OrderShippedFragment orderShippedFragment;              //待收货fragment
    private OrderAwaitCmtFragment orderAwaitCmtFragment;       //待评价fragment
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

        setListen();

    }

    private void setListen() {
        back_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void intData() {


        //fragment List
        orderAllFragment=new OrderAllFragment();
        orderaWaitPayFragment=new OrderaWaitPayFragment();
        orderAwaitShipFragment=new OrderAwaitShipFragment();
        orderShippedFragment=new OrderShippedFragment();
        orderAwaitCmtFragment=new OrderAwaitCmtFragment();
        //tab Fragment list
        list_fragment=new ArrayList<Fragment>();
        //tab title List
        list_title=new ArrayList<String>();

        if(orderTag.endsWith("waitPay")){
            list_fragment.add(orderaWaitPayFragment);
            list_title.add("待付款");
            tabLayout_2.setVisibility(View.GONE);
            tv_top_title.setText("待付款订单");
        }
        if(orderTag.endsWith("shipped")){
            list_fragment.add(orderShippedFragment);
            list_title.add("待收货");
            tabLayout_2.setVisibility(View.GONE);
            tv_top_title.setText("待收货订单");
        }

        if(orderTag.equals("")){
            list_fragment.add(orderAllFragment);
            list_fragment.add(orderaWaitPayFragment);
            list_fragment.add(orderAwaitShipFragment);
            list_fragment.add(orderShippedFragment);
            list_fragment.add(orderAwaitCmtFragment);

            list_title.add("全部");
            list_title.add("待付款");
            list_title.add("待发货");
            list_title.add("待收货");
            list_title.add("待评价");
        }



        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabLayout_2.setViewPager(vp);
        tabLayout_2.setOnTabSelectListener(this);
        vp.setCurrentItem(0);
//        tabLayout_2.showDot(0);
//        tabLayout_2.showMsg(1, 5);
//        tabLayout_2.setMsgMargin(1, 0.0f, 10.0f);

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



    String orderTag ="";
    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            orderTag = intent.getStringExtra("order");
            if(orderTag!=null){
                intData();
            }


        }



    }


    @Override
    public void onClick(View v) {
         switch (v.getId()){


         }
    }



}
