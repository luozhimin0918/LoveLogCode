package com.smarter.LoveLog.activity;

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.Toast;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.TablayoutViewPagerAdapter;
import com.smarter.LoveLog.fragment.OrderAllFragment;
import com.smarter.LoveLog.fragment.OrderCompletedFragment;
import com.smarter.LoveLog.fragment.OrderObligationFragment;
import com.smarter.LoveLog.fragment.OrderWaitTakeOverFragment;

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
    private OrderObligationFragment orderObligationFragment;            //待收款fragment
    private OrderWaitTakeOverFragment orderWaitTakeOverFragment;        //待收货fragment
    private OrderCompletedFragment orderCompletedFragment;              //已完成fragment
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
        orderObligationFragment=new OrderObligationFragment();
        orderWaitTakeOverFragment=new OrderWaitTakeOverFragment();
        orderCompletedFragment=new OrderCompletedFragment();
        list_fragment=new ArrayList<Fragment>();
        list_fragment.add(orderAllFragment);
        list_fragment.add(orderObligationFragment);
        list_fragment.add(orderWaitTakeOverFragment);
        list_fragment.add(orderCompletedFragment);
        //tab title List
        list_title=new ArrayList<String>();
        list_title.add("全部");
        list_title.add("待收款");
        list_title.add("待收货");
        list_title.add("已完成");

       /* //设置tablayout
        //设置TabLayout的模式
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        //为TabLayout添加tab名称
        for(int i=0;i<list_title.size();i++){
            tabLayout.addTab(tabLayout.newTab().setText(list_title.get(i)));
        }



        adapter = new TablayoutViewPagerAdapter(mContext,getSupportFragmentManager(),list_fragment,list_title);

        //viewpager加载adapter
        view_pager.setAdapter(adapter);
        //tab_FindFragment_title.setViewPager(vp_FindFragment_pager);
        //TabLayout加载viewpager
        tabLayout.setupWithViewPager(view_pager);


        view_pager.setCurrentItem(1);
*/
        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));
        tabLayout_2.setViewPager(vp);
        tabLayout_2.setOnTabSelectListener(this);
        tabLayout_2.showDot(1);

        tabLayout_2.showMsg(2, 5);
        tabLayout_2.setMsgMargin(2, 0, 10);

    }

    @Override
    public void onTabSelect(int position) {
        Toast.makeText(mContext, "onTabSelect&position--->" + position, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onTabReselect(int position) {
        Toast.makeText(mContext, "onTabReselect&position--->" + position, Toast.LENGTH_SHORT).show();
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
