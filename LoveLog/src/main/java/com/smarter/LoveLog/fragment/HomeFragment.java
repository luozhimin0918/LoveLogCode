package com.smarter.LoveLog.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
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
import android.widget.*;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.android.volley.toolbox.Volley;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.activity.MainActivity;
import com.smarter.LoveLog.activity.MessageCenterActivity;
import com.smarter.LoveLog.activity.ProductDeatilActivity;
import com.smarter.LoveLog.adapter.Adapter_GridView;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.adapter.HomeAdapter;
import com.smarter.LoveLog.adapter.MofanAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.home.Ad;
import com.smarter.LoveLog.model.home.AdIndexUrlData;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.home.HomeDataFrag;
import com.smarter.LoveLog.model.home.HomeDataInfo;
import com.smarter.LoveLog.model.home.NavIndexUrlData;
import com.smarter.LoveLog.model.home.SliderUrlData;
import com.smarter.LoveLog.ui.AutoScrollViewPager;
import com.smarter.LoveLog.ui.MyGridView;
import com.smarter.LoveLog.utills.DeviceUtil;
import com.smarter.LoveLog.utills.ListUtils;
import org.json.JSONObject;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/30.
 */
public class HomeFragment extends Fragment  {
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

    @Bind(R.id.messageBut)
    ImageView messageBut;

    @Bind(R.id.munuBut)
    ImageView munuBut;










    private HomeAdapter mAdapter;

    //首页轮播
    private AutoScrollViewPager viewPager;
   ViewGroup viewgroup;
    /**存储首页轮播的界面*/
    private ImageView[] imageViews;

    //分类的九宫格
    private MyGridView gridView_classify;
    private Adapter_GridView adapter_GridView_classify;
        /* 分类九宫格的资源文件*/
    private int[] pic_path_classify = { R.mipmap.icon01, R.mipmap.icon02, R.mipmap.icon03, R.mipmap.icon04, R.mipmap.icon05, R.mipmap.icon06, R.mipmap.icon07, R.mipmap.icon08 };
//    private String[]  pic_title={"首单立减","会员礼包","三人团","真伪查询","红包返现","减免运费","安全保障","支付通道"};
//    private List<String> imageRecycleList;
    NetworkImageView  topAd1,topAd2,topAd3;

    List<SliderUrlData>  sliderUrlDataList;//轮播
    List<NavIndexUrlData> navIndexUrlDataList=new ArrayList<NavIndexUrlData>();//GridView
    List<AdIndexUrlData> ad;//广告
    Boolean  isLoadReresh=false;//是否是刷新

    HomeDataInfo  homeDataInfo=null;//homeFragment所有数据
     public static ImagePagerAdapter imagePagerAdapter;//首页轮播的界面的adapter



    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
            view = inflater.inflate(R.layout.home_fragment, null);
            mRootView = new WeakReference<View>(view);
            mContext=this.getContext();

            ButterKnife.bind(this, view);
                initData();
                setListen();





           // getJSONByVolley();
        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        return mRootView.get();

    }

    private void setListen() {

        messageBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //反馈
                Intent intent2 = new Intent(mContext, MessageCenterActivity.class);
                  /*  Bundle bundle = new Bundle();
                    bundle.putSerializable("PromotePostsData", (Serializable) pp);
                    intent.putExtras(bundle);*/
                mContext.startActivity(intent2);
            }
        });
        munuBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(),"敬请期待",Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void initData() {

        if(DeviceUtil.checkConnection(mContext)){
            //加载动画
            progressLinear.setVisibility(View.VISIBLE);
            AnimationDrawable     animationDrawable = (AnimationDrawable) progreView.getDrawable();
            animationDrawable.start();

            mRecyclerView.setVisibility(View.VISIBLE);
            networkInfo.setVisibility(View.GONE);
            initNew();

        }else{
            errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nowifi));
            mRecyclerView.setVisibility(View.GONE);
            networkInfo.setVisibility(View.VISIBLE);
            newLoading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    initData();
                }
            });
        }


    }

    private void initNew() {

        String url = "http://mapp.aiderizhi.com/?url=/home/data";
        RequestQueue mQueue = AppContextApplication.getInstance().getmRequestQueue();
        FastJsonRequest<HomeDataFrag> fastJsonHome = new FastJsonRequest<HomeDataFrag>(url, HomeDataFrag.class,
                new Response.Listener<HomeDataFrag>() {

                    @Override
                    public void onResponse(HomeDataFrag homeDataFrag) {
                        // TODO Auto-generated method stub

                        DataStatus  status=homeDataFrag.getStatus();
                        if(status.getSucceed()==1){


                            progressLinear.setVisibility(View.GONE);//网络加载成功

                            homeDataInfo=homeDataFrag.getData();
                            if( isLoadReresh==true){
    //                        if(communityDataFrag.getData().equals(communityDataInfo)){
//                                refresh();
    //                            Log.d("ddd", "trur" );
    //                        }
                            }


                            if( isLoadReresh==false&&homeDataInfo!=null){
                                Log.d("ddd", "false" );
                                initFind();//初始界面
                            }

                            Log.d("HomeFragmentURL", "" + status.getSucceed() + "++++succeed》》》》" + homeDataInfo.getSlider().get(0).getImage_url());
                        }else{
                            // 请求失败 无数据
                            progressLinear.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.GONE);
                            errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nodata));
                            networkInfo.setVisibility(View.VISIBLE);
                        }


                    }
                }, new Response.ErrorListener() {

            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                //未知错误
                progressLinear.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                networkInfo.setVisibility(View.VISIBLE);
                try {
                    errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_default));
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                Log.d("HomeFragmentURL", "Error  HomeDataFrag>>>" );
            }
        });

        mQueue.add(fastJsonHome);
    }
    private void refresh() {
        initViewPagerRefresh();
        //gridvie
        navIndexUrlDataList=homeDataInfo.getNav();//GridView
        adapter_GridView_classify = new Adapter_GridView(getActivity(),navIndexUrlDataList);
        gridView_classify.setAdapter(adapter_GridView_classify);
        adapter_GridView_classify.notifyDataSetChanged();//gridview刷新
        //listHome
        ad= homeDataInfo.getAd();
        List<AdIndexUrlData> adIndexTopAd=new ArrayList<AdIndexUrlData>();
        for(int i=0;i<ad.size();i++){
            if(ad.get(i).getIndex_com()!=null){
                adIndexTopAd.add(ad.get(i));
            }
        }
        mAdapter = new HomeAdapter(mContext,adIndexTopAd);
        mAdapter.notifyDataSetChanged();
        //刷新完成
        mRecyclerView.refreshComplete();
        viewPager.startAutoScroll();
    }
    private void  initViewPagerRefresh(){
        sliderUrlDataList=homeDataInfo.getSlider();
        imagePagerAdapter=new ImagePagerAdapter(mContext,sliderUrlDataList ).setInfiniteLoop(true);
        viewPager.setAdapter(imagePagerAdapter);
        imagePagerAdapter.notifyDataSetChanged();



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



    private void initFind() {



        /**
         *
         */


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallPulse);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);

       View header =   LayoutInflater.from(getContext()).inflate(R.layout.home_fragment_header,null);

        mRecyclerView.addHeaderView(header);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    public void run() {
//                         initNew();
                        mRecyclerView.refreshComplete();
                        viewPager.startAutoScroll();
                    }
                }, 1000);


            }

            @Override
            public void onLoadMore() {

               new Handler().postDelayed(new Runnable() {
                    public void run() {


                          mRecyclerView.loadMoreComplete();
                    }
                }, 2000);

            }

        });


        /**
         * 主list
         */
        ad= homeDataInfo.getAd();
        List<AdIndexUrlData> adIndexTopAdCom=new ArrayList<AdIndexUrlData>();
        for(int i=0;i<ad.size();i++){
            if(ad.get(i).getIndex_com()!=null){
                adIndexTopAdCom.add(ad.get(i));
            }
        }
        mAdapter = new HomeAdapter(mContext,adIndexTopAdCom);

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
        gridView_classify = (MyGridView) header.findViewById(R.id.my_gridview);
        initGridView();

        /**
         * top 广告
         */
        topAd1= (NetworkImageView) header.findViewById(R.id.topAd1);
        topAd2= (NetworkImageView) header.findViewById(R.id.topAd2);
        topAd3= (NetworkImageView) header.findViewById(R.id.topAd3);

        initTopAd();
        isLoadReresh=true;//之后就是刷新了

    }

    private void initTopAd() {
         List<AdIndexUrlData> adIndexTopAdHot=new ArrayList<AdIndexUrlData>();
        for(int i=0;i<ad.size();i++){
            if(ad.get(i).getIndex_com()==null){
                adIndexTopAdHot.add(ad.get(i));
            }
        }


        List<NetworkImageView> networkImageViewList=new ArrayList<NetworkImageView>();
        networkImageViewList.add(topAd1);
        networkImageViewList.add(topAd2);
        networkImageViewList.add(topAd3);

        for(int j=0;j<adIndexTopAdHot.size();j++){
            networkImageViewList.get(j).setDefaultImageResId(R.drawable.loading);
            networkImageViewList.get(j).setErrorImageResId(R.drawable.loading);
            RequestQueue mQueue =  AppContextApplication.getInstance().getmRequestQueue();
            String imageUrl=adIndexTopAdHot.get(j).getIndex_hot().getImage_url();

           final Ad  ad=adIndexTopAdHot.get(j).getIndex_hot();

            Log.d("ImagePagerAdapter", mQueue.getCache().get(imageUrl) == null ? "null" : "bu null");
            if(mQueue.getCache().get(imageUrl)==null){
                networkImageViewList.get(j).startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
            }
            networkImageViewList.get(j).setImageUrl(imageUrl, AppContextApplication.getInstance().getmImageLoader());
            networkImageViewList.get(j).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    imagePagerAdapter.actionTo(mContext,ad.getAction(),ad.getParam());
                }
            });
        }



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
        navIndexUrlDataList=homeDataInfo.getNav();
        gridView_classify.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter_GridView_classify = new Adapter_GridView(getActivity(), navIndexUrlDataList);
        gridView_classify.setAdapter(adapter_GridView_classify);
        gridView_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

                imagePagerAdapter.actionTo(mContext, navIndexUrlDataList.get(arg2).getAction(), navIndexUrlDataList.get(arg2).getParam());
               /* //挑战到宝贝搜索界面
                Intent intent = new Intent(getActivity(), ProductDeatilActivity.class);
                startActivity(intent);*/
            }
        });
    }

    private void  initViewPager(){

        sliderUrlDataList=homeDataInfo.getSlider();
      imagePagerAdapter =new ImagePagerAdapter(mContext, sliderUrlDataList).setInfiniteLoop(true);
        viewPager.setAdapter(imagePagerAdapter);

        viewPager.setInterval(2000);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(sliderUrlDataList));

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
