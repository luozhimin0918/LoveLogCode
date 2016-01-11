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
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.RelativeLayout;

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
import com.smarter.LoveLog.adapter.Adapter_GridView;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.adapter.MofanAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.community.CommunityDataFrag;
import com.smarter.LoveLog.model.community.CommunityDataInfo;
import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.home.NavIndexUrlData;
import com.smarter.LoveLog.model.home.SliderUrlData;
import com.smarter.LoveLog.ui.AutoScrollViewPager;
import com.smarter.LoveLog.ui.MyGridView;
import com.smarter.LoveLog.utills.ListUtils;

import org.json.JSONObject;

import java.io.Serializable;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
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
    private MofanAdapter mAdapter;
    //首页轮播
    private AutoScrollViewPager viewPager;
    ViewGroup viewgroup;
    //分类的九宫格
    private MyGridView my_community_gridview;
    private Adapter_GridView adapter_GridView_classify;




    /* 分类九宫格的资源文件*/
//    private int[] pic_path_classify = { R.mipmap.notice, R.mipmap.sup, R.mipmap.beautiful, R.mipmap.all};
//   private String[]  pic_title={"公告","体验说","美课堂","大杂烩"};
    private int[] lit_int_resuour={R.mipmap.list1,R.mipmap.list2,R.mipmap.list1,R.mipmap.list2,R.mipmap.list1,R.mipmap.list2};
    /**存储首页轮播的界面*/
    private ImageView[] imageViews;
    /**首页轮播的界面的资源*/
    List<SliderUrlData> sliderUrlDataList;
    private CommunityDataInfo communityDataInfo=null;//本页所有数据
    public static List<PromotePostsData>  promotePostsData;//推荐list最新话题
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
            view = inflater.inflate(R.layout.community_fragment, null);
            mRootView = new WeakReference<View>(view);
            mContext=this.getContext();

            ButterKnife.bind(this, view);
            initData();
            // getJSONByVolley();
        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        return mRootView.get();

    }
    private void initData() {
        String url = "http://mapp.aiderizhi.com/?url=/post/index";
        RequestQueue mQueue = AppContextApplication.getInstance().getmRequestQueue();
        FastJsonRequest<CommunityDataFrag> fastJsonCommunity=new FastJsonRequest<CommunityDataFrag>(Request.Method.POST,url,CommunityDataFrag.class,null,new Response.Listener<CommunityDataFrag>()
        {
            @Override
            public void onResponse(CommunityDataFrag communityDataFrag) {

                DataStatus status=communityDataFrag.getStatus();
                if(status.getSucceed()==1){
                    communityDataInfo=communityDataFrag.getData();
                    initFind();//初始界面

                    Log.d("CommunityFragmentURL", "" + status.getSucceed() + "++++succeed》》》》"+communityDataInfo.getPromote_posts().get(1).getImg().getCover());
                }else{
                     if(communityDataInfo!=null) {
                        initFind();//初始界面
                     }else{
                        // 请求失败
                     }
                }


            }
        } ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        Map<String, String> map = new HashMap<String, String>();
        map.put("params1", "value1");
        map.put("params2", "value2");
        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);
    }
    private void initFind() {
        /**
         *
         */


        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallPulse);//BallSpinFadeLoader
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.SemiCircleSpin);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);

        View header =   LayoutInflater.from(getContext()).inflate(R.layout.community_fragment_header,null);
        mRecyclerView.addHeaderView(header);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        mRecyclerView.refreshComplete();
                        viewPager.startAutoScroll();


                    }

                }, 1000);            //refresh data here
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

        promotePostsData=communityDataInfo.getPromote_posts();
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

        final  List<NavIndexUrlData> navIndexUrlDataList=communityDataInfo.getNav();;//GridView


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




    private void  initViewPager(){


        sliderUrlDataList=communityDataInfo.getSlider();

        viewPager.setAdapter(new ImagePagerAdapter(mContext,sliderUrlDataList ).setInfiniteLoop(true));

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
