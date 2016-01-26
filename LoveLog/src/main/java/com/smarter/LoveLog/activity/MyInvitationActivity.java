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
import android.widget.ImageView;

import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.fragment.CommentReceiveFragment;
import com.smarter.LoveLog.fragment.CommentSendoutFragment;
import com.smarter.LoveLog.fragment.InvitationDraftFragment;
import com.smarter.LoveLog.fragment.InvitationPublishedFragment;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class MyInvitationActivity extends BaseFragmentActivity implements View.OnClickListener,OnTabSelectListener {
    String Tag= "MyInvitationActivity";
   @Bind(R.id.tl_2)
   SlidingTabLayout tabLayout_2;
    @Bind(R.id.view_pager)
    ViewPager vp;

    @Bind(R.id.back_but)
    ImageView back_but;

    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表

    private InvitationPublishedFragment invitationPublishedFragment;                          //已发布
    private InvitationDraftFragment invitationDraftFragment;            //草稿箱
    Activity mActivity;
    Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_invitation_view);
        ButterKnife.bind(this);
        mActivity=this;
        mContext=this;


        getDataIntent();
        intData();
        setListen();

    }

    private void setListen() {
        back_but.setOnClickListener(this);
    }

    private void intData() {
        //fragment List
        invitationPublishedFragment=new InvitationPublishedFragment();
        invitationDraftFragment=new InvitationDraftFragment();

        list_fragment=new ArrayList<Fragment>();
        list_fragment.add(invitationPublishedFragment);
        list_fragment.add(invitationDraftFragment);

        //tab title List
        list_title=new ArrayList<String>();
        list_title.add("已发布");
        list_title.add("草稿箱");



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
             case R.id.back_but:
                 finish();
                 break;

         }
    }



}
