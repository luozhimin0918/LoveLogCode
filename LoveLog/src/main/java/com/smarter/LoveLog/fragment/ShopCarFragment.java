package com.smarter.LoveLog.fragment;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.activity.LoginActivity;
import com.smarter.LoveLog.adapter.RecycleShopCarAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.PaginationJson;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.model.orderMy.ShopCarOrderInfo;
import com.smarter.LoveLog.ui.popwindow.AlertDialog;
import com.smarter.LoveLog.utills.DeviceUtil;
import com.smarter.LoveLog.utills.ViewUtill;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/11/30.
 */
public class ShopCarFragment extends Fragment implements RecycleShopCarAdapter.OnCheckDefaultListener {
    protected WeakReference<View> mRootView;
    @Bind(R.id.tv_right_title)
    TextView tvRightTitle;
    @Bind(R.id.isImage)
    ImageView isImage;
    @Bind(R.id.buy_now)
    TextView buyNow;
    private View view;


    @Bind(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    @Bind(R.id.networkInfo)
    LinearLayout networkInfo;
    @Bind(R.id.errorInfo)
    ImageView errorInfo;
    @Bind(R.id.newLoading)
    LinearLayout newLoading;

   /* @Bind(R.id.loadingTextLinear)
    LinearLayout loadingTextLinear;
    @Bind(R.id.loadingText)
    TextView loadingText;*/


    @Bind(R.id.progressLinear)
    LinearLayout progressLinear;

    @Bind(R.id.progreView)
    ImageView progreView;

    @Bind(R.id.carLinear)
    LinearLayout carLinear;

    @Bind(R.id.xuanfuBar)
    LinearLayout xuanfuBar;


    Context mContext;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
            view = inflater.inflate(R.layout.shop_car_fragment, null);
            mRootView = new WeakReference<View>(view);
            mContext = getContext();
            ButterKnife.bind(this, view);
            initRecycleViewVertical();


        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        ButterKnife.bind(this, mRootView.get());
        return mRootView.get();

    }


    @Override
    public void onResume() {
        super.onResume();
        isLogiin(false);
    }

    SessionData sessionData;
    Boolean isLoginTag = false;

    public void isLogiin(Boolean isFistOnTab) {

        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
        if (isLogin) {
            String sessionString = SharedPreferences.getInstance().getString("session", "");
            sessionData = JSON.parseObject(sessionString, SessionData.class);
            if (sessionData != null && isFistOnTab == false) {
                newWait();


            }

        } else {


            if (isFistOnTab) {
                new Handler().postDelayed(new Runnable() {
                    public void run() {

//                        isLoginTag=true;
                        //登录
                        Intent intent = new Intent(mContext, LoginActivity.class);
                                  /*  Bundle bundle = new Bundle();
                                    bundle.putSerializable("PromotePostsData", (Serializable) pp);
                                    intent.putExtras(bundle);*/
                        mContext.startActivity(intent);


                    }

                }, 100);            //refresh data here
               /* new AlertDialog(mContext).builder().setTitle("提示")
                        .setMsg("您未登录，请登录")
                        .setPositiveButton("确认", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                                isLoginTag=true;
                                //登录
                                Intent intent = new Intent(mContext, LoginActivity.class);
                                  *//*  Bundle bundle = new Bundle();
                                    bundle.putSerializable("PromotePostsData", (Serializable) pp);
                                    intent.putExtras(bundle);*//*
                                mContext.startActivity(intent);

                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onShopCarLonginListener.onBackShopCarOK(false);
                    }
                }).show();


                mRecyclerView.setVisibility(View.GONE);
                networkInfo.setVisibility(View.VISIBLE);
                carLinear.setVisibility(View.GONE);
                xuanfuBar.setVisibility(View.GONE);
                */


//            Toast.makeText(mContext, "未登录，请先登录", Toast.LENGTH_SHORT).show();
            } else {

                if (isLoginTag) {//isLoginTag为true时返回点购物车之前的页面
                    onShopCarLonginListener.onBackShopCarOK(false);
                    isLoginTag = false;
                }

                mRecyclerView.setVisibility(View.GONE);
                networkInfo.setVisibility(View.VISIBLE);
                carLinear.setVisibility(View.GONE);
                xuanfuBar.setVisibility(View.GONE);

            }


        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

    @OnClick({R.id.tv_right_title, R.id.isImage, R.id.buy_now})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_right_title:
                 if(idBianji){
                     idBianji=false;
                     tvRightTitle.setText("完成");
                     adapter.myNotifiAdapter(false, true,false);
                 }else{
                     idBianji=true;
                     tvRightTitle.setText("编辑");
                     adapter.myNotifiAdapter(false, false,false);

                 }


                break;
            case R.id.isImage:
                if(isQuanxuna){
                    adapter.myNotifiAdapter(true,false,true);
                    isQuanxuna=false;
                    isImage.setBackgroundResource(R.mipmap.choiceon);



                }else{
                    adapter.myNotifiAdapter(false,false,true);
                    isQuanxuna=true;
                  isImage.setBackgroundResource(R.mipmap.choice);

                }


                break;
            case R.id.buy_now:

                break;
        }
    }


    //回调开始
    public interface OnShopCarLonginListener {
        void onBackShopCarOK(Boolean isBack);
    }

    private OnShopCarLonginListener onShopCarLonginListener;

    public void setOnShopCarListener(OnShopCarLonginListener onShopCarLongin) {
        this.onShopCarLonginListener = onShopCarLongin;
    }

    private void newWait() {
        if (DeviceUtil.checkConnection(mContext)) {
            //加载动画
            progressLinear.setVisibility(View.VISIBLE);
            AnimationDrawable animationDrawable = (AnimationDrawable) progreView.getDrawable();
            animationDrawable.start();

            mRecyclerView.setVisibility(View.VISIBLE);
            networkInfo.setVisibility(View.GONE);
            carLinear.setVisibility(View.GONE);
            initData(sessionData);

        } else {
            errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nowifi));
            mRecyclerView.setVisibility(View.GONE);
            networkInfo.setVisibility(View.VISIBLE);

        }
    }


    List<ShopCarOrderInfo.DataEntity.GoodsListEntity> orderListList = new ArrayList<ShopCarOrderInfo.DataEntity.GoodsListEntity>();//
    public int page = 1;
    int loadingTag = 2;//刷新flag   2 默认   1 下拉刷新  -1是上拉更多

    private void initData(SessionData sessionDataOne) {

        String url = "http://mapp.aiderizhi.com/?url=/cart/list";//

        Map<String, String> map = new HashMap<String, String>();


        if (loadingTag == -1) {
            map = new HashMap<String, String>();
            PaginationJson paginationJson = new PaginationJson();
            paginationJson.setCount("10");
            paginationJson.setPage((++page) + "");
            String string = JSON.toJSONString(paginationJson);
            String d = "{\"pagination\":" + string + " ,\"type\":\"shipped\",\"session\":{\"uid\":\"" + sessionDataOne.getUid() + "\",\"sid\":\"" + sessionDataOne.getSid() + "\"}}";//type all为所有的订单
            map.put("json", d);
            Log.d("ShopCarFragment", d + "》》》》");
        }
        if (loadingTag == 2) {//第一次加载数据
            map = new HashMap<String, String>();
            String oneString = "{\"session\":{\"uid\":\"" + sessionDataOne.getUid() + "\",\"sid\":\"" + sessionDataOne.getSid() + "\"}}";
            map.put("json", oneString);
            Log.d("ShopCarFragment", oneString + "》》》》");
        }


        RequestQueue mQueue = AppContextApplication.getInstance().getmRequestQueue();
        FastJsonRequest<ShopCarOrderInfo> fastJsonCommunity = new FastJsonRequest<ShopCarOrderInfo>(Request.Method.POST, url, ShopCarOrderInfo.class, null, new Response.Listener<ShopCarOrderInfo>() {
            @Override
            public void onResponse(ShopCarOrderInfo myOrderInfo) {

                ShopCarOrderInfo.StatusEntity status = myOrderInfo.getStatus();
                if (status.getSucceed() == 1) {

                    progressLinear.setVisibility(View.GONE);


                    if (loadingTag == -1) {


                        List<ShopCarOrderInfo.DataEntity.GoodsListEntity> p = myOrderInfo.getData().getGoods_list();
                        Log.d("ShopCarFragment", "" + orderListList.size() + "1111++++orderListList");
                        for (int i = 0; i < p.size(); i++) {
                            orderListList.add(p.get(i));
                        }
                        Log.d("ShopCarFragment", "" + orderListList.size() + "2222++++orderListList");


                        loadingTag = 2;

                        mRecyclerView.loadMoreComplete();
                    }
                    if (loadingTag == 2) {



                        orderListList.clear();
                        orderListList.addAll(myOrderInfo.getData().getGoods_list());

                        if (orderListList != null && orderListList.size() > 0) {
                            adapter.notifyDataSetChanged();
                            xuanfuBar.setVisibility(View.VISIBLE);
                            tvRightTitle.setVisibility(View.VISIBLE);

                        } else {
                            progressLinear.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.GONE);
                            networkInfo.setVisibility(View.GONE);
                            carLinear.setVisibility(View.VISIBLE);

                            xuanfuBar.setVisibility(View.GONE);
                            tvRightTitle.setVisibility(View.INVISIBLE);
                        }


                        mRecyclerView.refreshComplete();
                    }


//                    Log.d("ShopCarFragment", "" + status.getSucceed() + "++++succeed》》》》" + promotePostDateList.get(0).getCat_name());
                } else {
                    // 请求失败
                    progressLinear.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nodata));
                    networkInfo.setVisibility(View.VISIBLE);
                    // 请求失败

                    Toast.makeText(mContext, status.getError_desc(), Toast.LENGTH_SHORT).show();
                    Log.d("ShopCarFragment", "" + status.getSucceed() + "++++success=0》》》》");


                    if (status.getError_code() == 1000) {
                        SharedPreferences.getInstance().putBoolean("islogin", false);
                        ViewUtill.ShowAlertDialog(mContext);
                    }

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //未知错误
                progressLinear.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_default));
                networkInfo.setVisibility(View.VISIBLE);
                Log.d("ShopCarFragment", "errror" + volleyError.toString() + "++++》》》》");
            }
        });

        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);


    }





    // 创建Adapter，并指定数据集
    RecycleShopCarAdapter adapter;
    boolean  idBianji=true;//是否编辑
    boolean  isQuanxuna=true;//是否全选

    public void initRecycleViewVertical() {


        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);


        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                if(isQuanxuna==false||idBianji==false){
                     Toast.makeText(mContext,"编辑中不可刷新",Toast.LENGTH_SHORT).show();
                    mRecyclerView.refreshComplete();
                }else{
                    loadingTag = 2;//重新加载
                    page = 1;
                    initData(sessionData);
                }

//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
//
//                        mRecyclerView.refreshComplete();
//
//
//                    }
//
//                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {

//                new Handler().postDelayed(new Runnable() {
//                    public void run() {
                loadingTag = -1;
                Log.d("ShopCarFragment", "initial    more");
                initData(sessionData);
//                        mRecyclerView.loadMoreComplete();
//                    }
//                }, 2000);

            }
        });


        if (orderListList != null) {
            adapter = new RecycleShopCarAdapter(orderListList,mContext);
            adapter.setOnCheckDefaultListener(this);
            mRecyclerView.setAdapter(adapter);
        }

        newLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
                if (!isLogin) {
                    new AlertDialog(mContext).builder().setTitle("提示")
                            .setMsg("您未登录，请登录")
                            .setPositiveButton("确认", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {

//                                    isLoginTag=true;
                                    //登录
                                    Intent intent = new Intent(mContext, LoginActivity.class);
//                                      Bundle bundle = new Bundle();
//                                    bundle.putSerializable("PromotePostsData", (Serializable) pp);
//                                    intent.putExtras(bundle);
                                    mContext.startActivity(intent);

                                }
                            }).setNegativeButton("取消", new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            onShopCarLonginListener.onBackShopCarOK(false);
                        }
                    }).show();
                } else {

                    isLogiin(false);

                }


            }
        });


    }

    @Override
    public void oncheckOK(Boolean[] ischeckArray) {

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onAllselectToCanter(boolean isquxiaoQuxian) {
          if(isquxiaoQuxian) {



              isImage.setBackgroundResource(R.mipmap.choice);
              isQuanxuna = true;
          }else{
              isImage.setBackgroundResource(R.mipmap.choiceon);
              isQuanxuna = false;
          }



     }

    @Override
    public void onDeleteAll() {
        progressLinear.setVisibility(View.GONE);
        networkInfo.setVisibility(View.GONE);
        carLinear.setVisibility(View.VISIBLE);

        xuanfuBar.setVisibility(View.GONE);
        tvRightTitle.setVisibility(View.INVISIBLE);
    }


}
