package com.smarter.LoveLog.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.activity.ProductDeatil;
import com.smarter.LoveLog.adapter.Adapter_GridView;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.adapter.MyAdapter;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.Weather;
import com.smarter.LoveLog.model.WeatherInfo;
import com.smarter.LoveLog.ui.AutoScrollViewPager;
import com.smarter.LoveLog.ui.MyGridView;
import com.smarter.LoveLog.utills.ListUtils;
import org.json.JSONObject;


import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2015/11/30.
 */
public class HomeFragment extends Fragment {
    protected WeakReference<View> mRootView;
    private View view;
    Context mContext;

    @Bind(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    private MyAdapter mAdapter;

    //首页轮播
    private AutoScrollViewPager viewPager;
     /**首页轮播的界面的资源*/
    private List<Integer> imageIdList;
   ViewGroup viewgroup;
    /**存储首页轮播的界面*/
    private ImageView[] imageViews;

    //分类的九宫格
    private MyGridView gridView_classify;
    private Adapter_GridView adapter_GridView_classify;
        /* 分类九宫格的资源文件*/
    private int[] pic_path_classify = { R.mipmap.icon01, R.mipmap.icon02, R.mipmap.icon03, R.mipmap.icon04, R.mipmap.icon05, R.mipmap.icon06, R.mipmap.icon07, R.mipmap.icon08 };
    private int[] lit_int_resuour={R.mipmap.list1,R.mipmap.list2};
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
            view = inflater.inflate(R.layout.home_fragment, null);
            mRootView = new WeakReference<View>(view);
            mContext=this.getContext();

            ButterKnife.bind(this, view);
            initFind();
           // getJSONByVolley();
        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        return mRootView.get();

    }

    private void initFind() {
        /**
         *
         */


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);

       View header =   LayoutInflater.from(getContext()).inflate(R.layout.home_fragment_header,null);
        View footer= LayoutInflater.from(getContext()).inflate(R.layout.home_fragment_foot,null);
        mRecyclerView.addHeaderView(header);
        mRecyclerView.addFootView(footer);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable(){
                    public void run() {
                         String url="http://www.weather.com.cn/data/sk/101010100.html";
                        RequestQueue mQueue = Volley.newRequestQueue(getContext());
                        FastJsonRequest<Weather> fastJson=new FastJsonRequest<Weather>(url, Weather.class,
                                new Response.Listener<Weather>() {

                                    @Override
                                    public void onResponse(Weather weather) {
                                        // TODO Auto-generated method stub
                                        WeatherInfo weatherInfo = weather.getWeatherinfo();
                                        Log.d("HomeFragment",""+weatherInfo.getCity()+">>>"+weatherInfo.toString());
                                        mRecyclerView.refreshComplete();
                                    }
                                }, new Response.ErrorListener() {

                            @Override
                            public void onErrorResponse(VolleyError arg0) {
                                // TODO Auto-generated method stub
                                mRecyclerView.refreshComplete();
                            }
                        });
                        mQueue.add(fastJson);






                    }

                }, 1000);            //refresh data here
            }

            @Override
            public void onLoadMore() {

                    new Handler().postDelayed(new Runnable() {
                        public void run() {


                          //  mRecyclerView.loadMoreComplete();
                        }
                    }, 2000);

            }
        });


        mAdapter = new MyAdapter(lit_int_resuour);

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
        gridView_classify.setSelector(new ColorDrawable(Color.TRANSPARENT));
        adapter_GridView_classify = new Adapter_GridView(getActivity(), pic_path_classify);
        gridView_classify.setAdapter(adapter_GridView_classify);
        gridView_classify.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
                //挑战到宝贝搜索界面
                Intent intent = new Intent(getActivity(), ProductDeatil.class);
                startActivity(intent);
            }
        });
    }

    private void  initViewPager(){

        imageIdList = new ArrayList<Integer>();
        imageIdList.add( R.mipmap.menu_viewpager_2);
        imageIdList.add( R.mipmap.menu_viewpager_3);
        imageIdList.add(R.mipmap.menu_viewpager_1);
        imageIdList.add(R.mipmap.menu_viewpager_4);
        imageIdList.add(R.mipmap.menu_viewpager_5 );
        viewPager.setAdapter(new ImagePagerAdapter(mContext, imageIdList).setInfiniteLoop(true));

        viewPager.setInterval(2000);
        viewPager.startAutoScroll();
        viewPager.setCurrentItem(Integer.MAX_VALUE / 2 - Integer.MAX_VALUE / 2 % ListUtils.getSize(imageIdList));

        //创建小图像集合
        imageViews=new ImageView[imageIdList.size()];
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

            int positions=position % ListUtils.getSize(imageIdList);
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
        viewPager.startAutoScroll();
    }

    @Override
    public void onPause() {
        super.onPause();
        viewPager.stopAutoScroll();
    }
}
