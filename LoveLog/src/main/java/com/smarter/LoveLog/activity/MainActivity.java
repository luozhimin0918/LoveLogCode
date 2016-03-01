package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import com.flyco.tablayout.CommonTabLayout;
import com.flyco.tablayout.listener.CustomTabEntity;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.fragment.SelfFragment;
import com.smarter.LoveLog.fragment.CommunityFragment;
import com.smarter.LoveLog.fragment.ShopCarFragment;
import com.smarter.LoveLog.fragment.HomeFragment;
import com.smarter.LoveLog.rongCloud.RongCloudEvent;
import com.smarter.LoveLog.ui.TabEntity;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import io.rong.imkit.RongIM;
import io.rong.imlib.RongIMClient;
import io.rong.imlib.model.Conversation;

/**
 * Created by Administrator on 2015/11/30.
 */
public class MainActivity extends BaseFragmentActivity  implements ShopCarFragment.OnShopCarLonginListener {
   Activity mActivity;
   public static   MainActivity mainActivity;
//    Fragment  fragment_flash_main,fragment_jw,fragment_kxthq,fragment_self;

    HomeFragment  homeFragment;
    CommunityFragment communityFragment;
    ShopCarFragment  shopCarFragment;
    SelfFragment  selfFragment;

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
        getDataIntent();


         initRound();


//        init();
//        setListen();
//        setTabSelection(0);
    }

    String MainTag ="";
    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            MainTag = intent.getStringExtra("main");
            if(MainTag==null){
                MainTag="";
            }
            initData();


        }



    }

    private void initRound() {


        /**
         * 启动单聊
         * context - 应用上下文。
         * targetUserId - 要与之聊天的用户 Id。
         * title - 聊天的标题，如果传入空值，则默认显示与之聊天的用户名称。
         */
               /* if (RongIM.getInstance() != null) {
                    RongIM.getInstance().startPrivateChat(MainActivity.this, "22222222", "hello");
                }*/
        /**
         * 启动客服聊天界面。
         *
         * @param context          应用上下文。
         * @param conversationType 开启会话类型。
         * @param targetId         客服 Id。
         * @param title            客服标题。
         */
      /*  RongIM.getInstance().startConversation(MainActivity.this, Conversation.ConversationType.APP_PUBLIC_SERVICE, "KEFU145033288579386", "客服");

*/


        /**
         * 启动客服聊天界面。
         *
         * @param context          应用上下文。
         * @param conversationType 会话类型，此处应该传 Conversation.ConversationType.APP_PUBLIC_SERVICE。
         * @param targetId         公众号 Id。
         * @param title            客服标题。
         */
//                RongIM.getInstance().startConversation(MainActivity.this, Conversation.ConversationType.APP_PUBLIC_SERVICE, "luozhimin", "罗志敏");


        connect(Token);
    }


    /**
     * 建立与融云服务器的连接
     *
     * @param token
     */
    String Token = "Ndy2PTG3/i/OQTdIxkIlfolWW0NIKFhANmdk75YpJ9L2vYqGIP2Ep5nO72KgdCUYUKv9fv2KU5W9J7tYGWxRPQ=="; //test dd
    //    String Token = "v0PjdIA3EKRyApOF2mqccbAdUWU/UP60b3vQW1NesNJAdWdGu1v1DSSvW9wqc6AoD0lsot/llkW+D+wrsitRvedKOmGO8aW9"; //test22222222
    private void connect(String token) {

        if (getApplicationInfo().packageName.equals(AppContextApplication.getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第二步,建立与服务器的连接
             */
            RongIM.connect(token, new RongIMClient.ConnectCallback() {

                /**
                 * Token 错误，在线上环境下主要是因为 Token 已经过期，您需要向 App Server 重新请求一个新的 Token
                 */
                @Override
                public void onTokenIncorrect() {

                    Log.d("MainActivity", "--onTokenIncorrect");
                }

                /**
                 * 连接融云成功
                 * @param userid 当前 token
                 */
                @Override
                public void onSuccess(String userid) {

                    Log.d("MainActivity", "--onSuccess" + userid);
                  /*  startActivity(new Intent(MainActivity.this, MainActivity.class));
                    finish();*/
                    RongCloudEvent.init(mActivity);
//                    RongCloudEvent.getInstance().setOtherListener();
                }

                /**
                 * 连接融云失败
                 * @param errorCode 错误码，可到官网 查看错误码对应的注释
                 */
                @Override
                public void onError(RongIMClient.ErrorCode errorCode) {

                    Log.d("MainActivity", "--onError" + errorCode);
                }
            });
        }
    }



    MyPagerAdapter myPagerAdapter;
    private void initData() {
        homeFragment = new HomeFragment();
        communityFragment = new CommunityFragment();
        shopCarFragment = new ShopCarFragment();
        selfFragment = new SelfFragment();

        shopCarFragment.setOnShopCarListener(this);//回调函数


        if(MainTag.endsWith("shopCar")){
            mFragments.add(shopCarFragment);
            main_zt_color.setBackgroundColor(Color.parseColor("#fc1359"));
            mTabLayout_2.setVisibility(View.GONE);
        }else {
            mFragments.add(homeFragment);
            mFragments.add(communityFragment);
            mFragments.add(shopCarFragment);
            mFragments.add(selfFragment);

            for (int i = 0; i < mTitles.length; i++) {
                mTabEntities.add(new TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]));
            }
            intTalayoug();
        }

        myPagerAdapter=new MyPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(myPagerAdapter);




    }




    Boolean  isOnTab=false;
    int  TempPostion=0;
    private void intTalayoug() {

        mTabLayout_2.setTabData(mTabEntities);
        mTabLayout_2.setOnTabSelectListener(new OnTabSelectListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onTabSelect(int position) {
                isOnTab=true;

                mViewPager.setCurrentItem(position);
                mViewPagerSetCurrent(position);
                myPagerAdapter.update(position);

                isOnTab=false;


                if(position!=2){
                    TempPostion=position;
                }

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


                if(isOnTab==false){
                    mTabLayout_2.setCurrentTab(position);
                    mViewPagerSetCurrent(position);
                    myPagerAdapter.update(position);
                }


                if(position!=2){
                    TempPostion=position;
                }

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

    @Override
    public void onBackShopCarOK(Boolean isBack) {

        if(isBack==false){
            mTabLayout_2.setCurrentTab(TempPostion);
            mViewPager.setCurrentItem(TempPostion,false);
            mViewPagerSetCurrent(TempPostion);
        }


    }


    private class MyPagerAdapter extends FragmentPagerAdapter {
        private List<String> tagList;
       FragmentManager fm;
        public MyPagerAdapter(FragmentManager fm) {

            super(fm);
            this.fm=fm;
            tagList=new ArrayList<String>();
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

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            tagList.add(makeFragmentName(container.getId(),position));
            return super.instantiateItem(container, position);
        }
       /* @Override
        public int getItemPosition(Object object) {
            View view = (View)object;
            int currentPage = mViewPager.getCurrentItem(); // Get current page index
            if (currentPage == (Integer)view.getTag()){
                return POSITION_NONE;
            }else{
                return POSITION_UNCHANGED;
            }
//      return POSITION_NONE;
        }*/
       public void update(int item) {
           Fragment fragment = null;
           if(tagList.size()>0){
                fragment = fm.findFragmentByTag(tagList.get(item));
           }

           if (fragment != null) {
               switch (item) {
                   case 0:

                       break;
                   case 1:
//                       ((CommunityFragment) fragment).initData();
                       break;
                   case 2:
                       try {
                           ((ShopCarFragment) fragment).isLogiin(true);
                       } catch (ClassCastException e) {
                           e.printStackTrace();
                       }
                       break;
                   default:
                       break;
               }
           }
       }
        public  String makeFragmentName(int viewId, int index) {
            return "android:switcher:" + viewId + ":" + index;
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
