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
import com.smarter.LoveLog.model.ChatEmoji;
import com.smarter.LoveLog.utills.FaceConversionUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class MyCommentActivity extends BaseFragmentActivity implements View.OnClickListener,OnTabSelectListener {
    String Tag= "MyCommentActivity";
   @Bind(R.id.tl_2)
   SlidingTabLayout tabLayout_2;
    @Bind(R.id.view_pager)
    ViewPager vp;

    @Bind(R.id.back_but)
    ImageView back_but;

    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表

    private CommentReceiveFragment commentReceiveFragment;                          //收到的评论fragment
    private CommentSendoutFragment commentSendoutFragment;            //发出的评论fragment
    Activity mActivity;
    Context mContext;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_comment_view);
        ButterKnife.bind(this);
        mActivity=this;
        mContext=this;

        new Thread(new Runnable() {
            @Override
            public void run() {
                /** 表情集合 */
                List<List<ChatEmoji>> emojis  = FaceConversionUtil.getInstace().emojiLists;
                if(emojis.size()<=0){
                    FaceConversionUtil.getInstace().getFileText(getApplication());
                }

            }
        }).start();

        getDataIntent();
        intData();
        setListen();

    }

    private void setListen() {
        back_but.setOnClickListener(this);
    }

    private void intData() {
        //fragment List
        commentReceiveFragment=new CommentReceiveFragment();
        commentSendoutFragment=new CommentSendoutFragment();

        list_fragment=new ArrayList<Fragment>();
        list_fragment.add(commentReceiveFragment);
        list_fragment.add(commentSendoutFragment);

        //tab title List
        list_title=new ArrayList<String>();
        list_title.add("收到的评论");
        list_title.add("发出的评论");



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
