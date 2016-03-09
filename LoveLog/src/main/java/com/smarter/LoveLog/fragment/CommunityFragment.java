package com.smarter.LoveLog.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.activity.InvitationActivity;
import com.smarter.LoveLog.activity.InvitationDeatilActivity;
import com.smarter.LoveLog.activity.SendInvitationActivity;
import com.smarter.LoveLog.adapter.Adapter_GridView;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.adapter.MofanAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.community.CommunityDataFrag;
import com.smarter.LoveLog.model.community.CommunityDataInfo;
import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.home.NavIndexUrlData;
import com.smarter.LoveLog.model.home.SliderUrlData;
import com.smarter.LoveLog.model.jsonModel.ZanOrFaroviteParame;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.ui.AutoScrollViewPager;
import com.smarter.LoveLog.ui.MyGridView;
import com.smarter.LoveLog.utills.DeviceUtil;
import com.smarter.LoveLog.utills.ListUtils;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class CommunityFragment extends Fragment {
    protected WeakReference<View> mRootView;
    private View view;
    Context mContext;
    @Bind(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    @Bind(R.id.networkInfo)
    LinearLayout networkInfo;
    @Bind(R.id.errorInfo)
    ImageView errorInfo;
    @Bind(R.id.newLoading)
    LinearLayout newLoading;



    @Bind(R.id.progressLinear)
    LinearLayout progressLinear;

    @Bind(R.id.progreView)
    ImageView progreView;

    @Bind(R.id.tv_right_title)
    TextView tv_right_title;





    public MofanAdapter mAdapter;
    //首页轮播
    private AutoScrollViewPager viewPager;
    ViewGroup viewgroup;
    //分类的九宫格
    private MyGridView my_community_gridview;
    private Adapter_GridView adapter_GridView_classify;

    private ImageView[] imageViews=new ImageView[]{};//轮播圆点
    ImagePagerAdapter imagePagerAdapter;//首页轮播的界面的adapter
    List<SliderUrlData> sliderUrlDataList=new ArrayList<SliderUrlData>();  //首页轮播的界面的资源

    List<NavIndexUrlData> navIndexUrlDataList=new ArrayList<NavIndexUrlData>();//gridVIew数据
    private CommunityDataInfo communityDataInfo=null;//本页所有数据
    public static List<PromotePostsData>  promotePostsData=new ArrayList<PromotePostsData>();//推荐list最新话题

    Boolean  isLoadReresh=false;//是否是刷新
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
            view = inflater.inflate(R.layout.community_fragment, null);
            mRootView = new WeakReference<View>(view);
            mContext=this.getContext();

            ButterKnife.bind(this, view);

            for(int i=0;i<1;i++){
                SliderUrlData s=new SliderUrlData();


                sliderUrlDataList.add(s);
            }
            initFind();

        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        return mRootView.get();

    }
    public  void initData() {
        if(DeviceUtil.checkConnection(mContext)){

            //加载动画
            if(isLoadReresh==true){
                progressLinear.setVisibility(View.GONE);
            }else{
                progressLinear.setVisibility(View.VISIBLE);
            }

            AnimationDrawable animationDrawable = (AnimationDrawable) progreView.getDrawable();
            animationDrawable.start();


            mRecyclerView.setVisibility(View.VISIBLE);
            networkInfo.setVisibility(View.GONE);
            initNew();


        }else{
            errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nowifi));
            mRecyclerView.setVisibility(View.GONE);
            networkInfo.setVisibility(View.VISIBLE);

        }

    }
    SessionData sessionData;
    private void initNew() {
        String url = "http://mapp.aiderizhi.com/?url=/post/index";
        RequestQueue mQueue = AppContextApplication.getInstance().getmRequestQueue();
        FastJsonRequest<CommunityDataFrag> fastJsonCommunity=new FastJsonRequest<CommunityDataFrag>(Request.Method.POST,url,CommunityDataFrag.class,null,new Response.Listener<CommunityDataFrag>()
        {
            @Override
            public void onResponse(CommunityDataFrag communityDataFrag) {

                DataStatus status=communityDataFrag.getStatus();
                if(status.getSucceed()==1){

                    progressLinear.setVisibility(View.GONE);//消失加载进度

                    communityDataInfo=communityDataFrag.getData();


                    if( communityDataInfo!=null){

                            refresh();

                            Log.d("ddd", "trur");



                    }






                    Log.d("CommunityFragmentURL", "" + status.getSucceed() + "++++succeed》》》》"+communityDataInfo.getPromote_posts().get(1).getImg().getCover());
                }else{

                        // 请求失败
                        progressLinear.setVisibility(View.GONE);
                        mRecyclerView.setVisibility(View.GONE);
                        errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nodata));
                        networkInfo.setVisibility(View.VISIBLE);
                }


            }
        } ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //未知错误
                progressLinear.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);


                try {
                    errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_default));
                } catch (IllegalStateException e) {
                    e.printStackTrace();
                }
                networkInfo.setVisibility(View.VISIBLE);

            }
        });


        Map<String, String> map = new HashMap<String, String>();
        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
        if(isLogin){
            String  sessionString=SharedPreferences.getInstance().getString("session", "");
            sessionData = JSON.parseObject(sessionString, SessionData.class);
            if(sessionData!=null){

                ZanOrFaroviteParame zanOrFaroviteInfo=new ZanOrFaroviteParame();
                zanOrFaroviteInfo.setSession(sessionData);
                map.put("json", JSON.toJSONString(zanOrFaroviteInfo));
                fastJsonCommunity.setParams(map);

            }

        }

        mQueue.add(fastJsonCommunity);
    }

    private void refresh() {
        if(isViewPager){
            initViewPagerRefresh();
        }

        //GridView
        navIndexUrlDataList.clear();
        navIndexUrlDataList.addAll(communityDataInfo.getNav());
        adapter_GridView_classify.notifyDataSetChanged();//gridview刷新

        //listMofan
        promotePostsData.clear();
        promotePostsData.addAll(communityDataInfo.getPromote_posts());
        mAdapter.notifyDataSetChanged();
       //刷新完成
        mRecyclerView.refreshComplete();
        viewPager.startAutoScroll();
    }


    int  loadingTag=2;//刷新flag   2 默认   1 下拉刷新  -1是上拉更多
    private void initFind() {


        tv_right_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, SendInvitationActivity.class);
//                Bundle bundle = new Bundle();
//                PromotePostsData pp=promotePostsDataList.get(position);
//                bundle.putSerializable("PromotePostsData", (Serializable) pp);
//                intent.putExtras(bundle);

                mContext.startActivity(intent);
            }
        });

        newLoading.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });
        /**
         *
         */


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);//BallSpinFadeLoader
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallPulse);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
       mRecyclerView.setLoadingMoreEnabled(false);
        View header =   LayoutInflater.from(getContext()).inflate(R.layout.community_fragment_header,null);
        mRecyclerView.addHeaderView(header);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
//                initData();//刷新


                loadingTag=1;
                initData();
                loadingTag=2;
              /*  new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mRecyclerView.refreshComplete();
                        viewPager.startAutoScroll();


                    }

                }, 500);*/
            }

            @Override
            public void onLoadMore() {

                new Handler().postDelayed(new Runnable() {
                    public void run() {


                        mRecyclerView.loadMoreComplete();
                    }
                }, 500);

            }
        });

        mAdapter = new MofanAdapter(mContext,promotePostsData);



        mRecyclerView.setAdapter(mAdapter);
        /**
         * 轮播广告
         */
        viewPager = (AutoScrollViewPager) header.findViewById(R.id.viewPager_menu);
        viewgroup =(ViewGroup) header.findViewById(R.id.viewgroup);
        initViewPager();

        /**
         * 分类grid
         */
        my_community_gridview = (MyGridView) header.findViewById(R.id.my_community_gridview);
        initGridView();



        isLoadReresh=true;//之后就是刷新了
    }
    /**
     * 暂无用
     * 利用Volley获取JSON数据
     */
    private void getJSONByVolley() {
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        String JSONDataUrl = "http://pipes.yahooapis.com/pipes/pipe.run?_id=giWz8Vc33BG6rQEQo_NLYQ&_render=json";
        final ProgressDialog progressDialog = ProgressDialog.show(getContext(), "This is title", "...Loading...");

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                JSONDataUrl,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.d("HomeFragment",""+"Response is: "+ response);
                        System.out.println("response="+response);
                        if (progressDialog.isShowing()&&progressDialog!=null) {
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError arg0) {
                        Log.d("HomeFragment",""+"Response is:  error");
                        System.out.println("sorry,Error");
                    }
                });
        requestQueue.add(jsonObjectRequest);




        // Request a string response from the provided URL.

                /*
                    String url2 ="http://www.google.com";
                StringRequest stringRequest = new StringRequest(Request.Method.GET, url2,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Display the first 500 characters of the response string.
                                Log.d("HomeFragment",""+"Response is: "+ response.substring(0,500));

                            }
                        }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.d("HomeFragment",""+"Response is: "+ "That didn't work!");
                    }
                });
                // Add the request to the RequestQueue.
                mQueue.add(stringRequest);*/

        //   mQueue.start();
    }



    private void initGridView() {



        my_community_gridview.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter_GridView_classify = new Adapter_GridView(getActivity(),navIndexUrlDataList);
        my_community_gridview.setAdapter(adapter_GridView_classify);
        my_community_gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //挑战到宝贝搜索界面
                Intent intent = new Intent(getActivity(), InvitationActivity.class);
                Bundle bundle = new Bundle();
                NavIndexUrlData nn=navIndexUrlDataList.get(arg2);
                bundle.putSerializable("NavIndexUrlData", (Serializable) nn);
                intent.putExtras(bundle);
                startActivity(intent);
            }
        });
    }



    private void  initViewPagerRefresh(){


        sliderUrlDataList.clear();
        sliderUrlDataList.addAll(communityDataInfo.getSlider());
        imagePagerAdapter=new ImagePagerAdapter(mContext,sliderUrlDataList ).setInfiniteLoop(true);
        viewPager.setAdapter(imagePagerAdapter);
        imagePagerAdapter.notifyDataSetChanged();


       createViewPagerPoint();

    }

   boolean  isViewPager=false;
    private void  initViewPager(){

        imagePagerAdapter=new ImagePagerAdapter(mContext,sliderUrlDataList ).setInfiniteLoop(true);
        viewPager.setAdapter(imagePagerAdapter);

        viewPager.setInterval(2000);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(sliderUrlDataList));


        createViewPagerPoint();
        isViewPager=true;


    }

    /**
     * 创建小图像集合
     */
    private void createViewPagerPoint() {

        viewgroup.removeAllViews();//remove圆点
        //创建小图像集合
        imageViews=new ImageView[sliderUrlDataList.size()];
        RelativeLayout.LayoutParams params;
        for(int i=0;i<imageViews.length;i++){

            //圆点之间的空白
            ImageView  kong = new ImageView(mContext);
            params = new RelativeLayout.LayoutParams(25,0);

            kong.setLayoutParams(params);
            kong.setBackgroundColor(Color.parseColor("#000000" + ""));
            viewgroup.addView(kong);

            imageViews[i]=new ImageView(mContext);
            if(i==0){
                imageViews[i].setBackgroundResource(R.mipmap.play_display);
            }else{
                imageViews[i].setBackgroundResource(R.mipmap.play_hide);
            }


            viewgroup.addView(imageViews[i]);
        }
        viewPager.setOnPageChangeListener(new MyOnPageChangeListener());
    }

    public class MyOnPageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageSelected(int position) {

            int positions=position % ListUtils.getSize(sliderUrlDataList);
            Log.d("HomeFragment",positions+">>");
            for(int i=0;i<imageViews.length;i++){

                if(positions==i){
                    imageViews[i].setBackgroundResource(R.mipmap.play_display);
                }else{
                    imageViews[i].setBackgroundResource(R.mipmap.play_hide);
                }

            }
        }

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {}

        @Override
        public void onPageScrollStateChanged(int arg0) {}
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
        if(viewPager!=null){
            viewPager.startAutoScroll();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if(viewPager!=null){
            viewPager.stopAutoScroll();
        }
    }
}
