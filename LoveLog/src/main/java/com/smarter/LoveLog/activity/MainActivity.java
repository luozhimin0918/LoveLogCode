package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.fragment.SelfFragment;
import com.smarter.LoveLog.fragment.CommunityFragment;
import com.smarter.LoveLog.fragment.ShopCarFragment;
import com.smarter.LoveLog.fragment.HomeFragment;
import com.smarter.LoveLog.ui.TabEntity;

import java.util.ArrayList;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;

/**
 * Created by Administrator on 2015/11/30.
 */
public class MainActivity extends BaseFragmentActivity  {
   Activity mActivity;
   public static   MainActivity mainActivity;
    Fragment  fragment_flash_main,fragment_jw,fragment_kxthq,fragment_self;


    @Bind(R.id.main_zt_color)
     LinearLayout  main_zt_color;

    @Bind(R.id.vp_2)
     ViewPager mViewPager;
    @Bind(R.id.tl_2)
    CommonTabLayout mTabLayout_2;

    private ArrayList<Fragment> mFragments = new ArrayList<Fragment>();
    private ArrayList<CustomTabEntity> mTabEntities = new ArrayList<CustomTabEntity>();


    private String[] mTitles = {"首页", "社区", "购物车", "我的"};
    private int[] mIconUnselectIds = {
            R.mipmap.home, R.mipmap.community,
            R.mipmap.car, R.mipmap.self};
    private int[] mIconSelectIds = {
            R.mipmap.home_selected, R.mipmap.community_selected,
            R.mipmap.car_selected, R.mipmap.self_selected};
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        ShareSDK.initSDK(this);//shareSdk在项目的入口Activity，在其onCreate方法中插入下面的代码进行初始化：（这个方法越早调用越好）


        mActivity=this;
        mainActivity=this;
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);



        //
          initData();
//        init();
//        setListen();
//        setTabSelection(0);
    }

    private void initData() {
        fragment_flash_main = new HomeFragment();
        fragment_jw = new CommunityFragment();
        fragment_kxthq = new ShopCarFragment();
        fragment_self = new SelfFragment();

        mFragments.add(fragment_flash_main);
        mFragments.add(fragment_jw);
        mFragments.add(fragment_kxthq);
        mFragments.add(fragment_self);

        for (int i = 0; i < mTitles.length; i++) {
            mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
        }

        mViewPager.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));



        intTalayoug();
    }

    private void intTalayoug() {

        mTabLayout_2.setTabData(mTabEntities);
        mTabLayout_2.setOnTabSelectListener(new OnTabSelectListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabSelect(int position) {
                mViewPager.setCurrentItem(position);
                mViewPagerSetCurrent(position);

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabReselect(int position) {


            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onPageSelected(int position) {
                mTabLayout_2.setCurrentTab(position);
                mViewPagerSetCurrent(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        mViewPager.setCurrentItem(0);
        mViewPagerSetCurrent(0);




    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void mViewPagerSetCurrent(int i) {
        if (i == mFragments.size() - 1) {
            main_zt_color.setBackground(getResources().getDrawable(R.drawable.repeat_bg));
        } else {
            main_zt_color.setBackgroundColor(Color.parseColor("#fc1359"));
        }
    }

    public  void onDoMainListener() {
        mViewPager.setCurrentItem(1);
        mViewPagerSetCurrent(1);
    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles[position];
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }
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
   /* private void init() {
        fragmentManager = getSupportFragmentManager();

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


*/
   /* @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
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
                    fragment_jw = new CommunityFragment();
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

    }*/


    /*//回调开始
    public interface OnMainActivityListener {
        void  onDoMainListener();
    }
    public  static OnMainActivityListener onMainActivityListener;

    public void setMainListener(OnMainActivityListener onMainActivityListener1) {
        this.onMainActivityListener=onMainActivityListener1;
    }*/

}
