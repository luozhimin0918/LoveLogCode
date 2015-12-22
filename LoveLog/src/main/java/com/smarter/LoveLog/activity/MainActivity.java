package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.fragment.SelfFragment;
import com.smarter.LoveLog.fragment.YwFragment;
import com.smarter.LoveLog.fragment.ShopCarFragment;
import com.smarter.LoveLog.fragment.HomeFragment;

import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2015/11/30.
 */
public class MainActivity extends BaseFragmentActivity  {
    Activity mActivity;
    private FragmentManager fragmentManager;
    private FragmentTransaction transaction;
    private LinearLayout  main_zt_color;
    private int cuntenpage = 0;
    Fragment  fragment_flash_main,fragment_jw,fragment_kxthq,fragment_self;

    private RadioGroup group;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ShareSDK.initSDK(this);//shareSdk在项目的入口Activity，在其onCreate方法中插入下面的代码进行初始化：（这个方法越早调用越好）


        mActivity=this;
        setContentView(R.layout.activity_main);


        init();
        setListen();
        setTabSelection(0);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ShareSDK.stopSDK(this);//在项目出口Activity的onDestroy方法中第一行插入下面的代码：
    }

    private void setListen() {

    }

    /**
     * 初始化数据
     */
    private void init() {
        fragmentManager = getSupportFragmentManager();
        main_zt_color = (LinearLayout) findViewById(R.id.main_zt_color);
        group = (RadioGroup) findViewById(R.id.group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_home:   setTabSelection(0); break;
                    case R.id.foot_bar_community:setTabSelection(1);break;
                    case R.id.foot_bar_im:  setTabSelection(2); break;
                    case R.id.foot_bar_interest:  setTabSelection(3); break;

                }

            }
        });
    }



    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void setTabSelection(int index) {
        // 重置按钮
        // 开启一个Fragment事务
        transaction = fragmentManager.beginTransaction();
        // 先隐藏掉所有的Fragment，以防止有多个Fragment显示在界面上的情况
        hideFragments(transaction);
        switch (index) {
            case 0:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                cuntenpage = 0;
                main_zt_color.setBackgroundColor(Color.parseColor("#fc1359"));
                if (fragment_flash_main == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    fragment_flash_main = new HomeFragment();
                    transaction.add(R.id.frame_content, fragment_flash_main);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(fragment_flash_main);
                }
                break;
            case 1:
                // 当点击了消息tab时，改变控件的图片和文字颜色
                cuntenpage = 1;
                main_zt_color.setBackgroundColor(Color.parseColor("#fc1359"));
                if (fragment_jw == null) {
                    // 如果MessageFragment为空，则创建一个并添加到界面上
                    fragment_jw = new YwFragment();
                    transaction.add(R.id.frame_content, fragment_jw);
                } else {
                    // 如果MessageFragment不为空，则直接将它显示出来
                    transaction.show(fragment_jw);
                }
                break;
            case 2:
                // 当点击了动态tab时，改变控件的图片和文字颜色
                cuntenpage = 2;
                main_zt_color.setBackgroundColor(Color.parseColor("#fc1359"));
                if (fragment_kxthq == null) {
                    fragment_kxthq = new ShopCarFragment();
                    transaction.add(R.id.frame_content, fragment_kxthq);
                } else {
                    transaction.show(fragment_kxthq);
                }

                break;

            case 3:
                cuntenpage = 3;
                main_zt_color.setBackground(getResources().getDrawable(R.drawable.repeat_bg));
                if (fragment_self == null) {
                    // 如果SettingFragment为空，则创建一个并添加到界面上
                    fragment_self = new SelfFragment();
                    transaction.add(R.id.frame_content, fragment_self);
                } else {
                    // 如果SettingFragment不为空，则直接将它显示出来
                    transaction.show(fragment_self);
                }
                break;
        }
        transaction.commitAllowingStateLoss();
    }
    private void hideFragments(FragmentTransaction transaction) {
        if (fragment_flash_main != null) {
            transaction.hide(fragment_flash_main);
        }
        if (fragment_kxthq != null) {
            transaction.hide(fragment_kxthq);
        }
        if (fragment_jw != null) {
            transaction.hide(fragment_jw);
        }
        if (fragment_self != null) {
            transaction.hide(fragment_self);
        }

    }

}
