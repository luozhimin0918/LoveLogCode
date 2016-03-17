package com.smarter.LoveLog.fragment;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
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
import com.smarter.LoveLog.adapter.RecycleOrderAllAdapter;
import com.smarter.LoveLog.adapter.RecycleRedpacketUnusedAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.PaginationJson;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.model.redpacket.RedList;
import com.smarter.LoveLog.model.redpacket.RedPacketInfo;
import com.smarter.LoveLog.utills.DeviceUtil;
import com.smarter.LoveLog.utills.ViewUtill;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class RedpacketUnusedFragment extends Fragment implements RecycleOrderAllAdapter.OnCheckDefaultListener {
    protected WeakReference<View> mRootView;
    private View view;


    @Bind(R.id.recyclerview)
    XRecyclerView mRecyclerView;

    @Bind(R.id.networkInfo)
    LinearLayout networkInfo;
    @Bind(R.id.errorInfo)
    ImageView errorInfo;
    @Bind(R.id.newLoading)
    LinearLayout newLoading;

    @Bind(R.id.loadingTextLinear)
    LinearLayout loadingTextLinear;
    @Bind(R.id.loadingText)
    TextView loadingText;


    @Bind(R.id.progressLinear)
    LinearLayout progressLinear;

    @Bind(R.id.progreView)
    ImageView progreView;

    Context mContext;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
            view = inflater.inflate(R.layout.comment_receive_fragment, null);
            mRootView = new WeakReference<View>(view);
            mContext=getContext();
            ButterKnife.bind(this,view);
            isLogiin();
        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        return mRootView.get();

    }


    private void newWait() {
        if(DeviceUtil.checkConnection(mContext)){
            //加载动画
            progressLinear.setVisibility(View.VISIBLE);
            AnimationDrawable animationDrawable = (AnimationDrawable) progreView.getDrawable();
            animationDrawable.start();

            mRecyclerView.setVisibility(View.VISIBLE);
            networkInfo.setVisibility(View.GONE);

          initData(sessionData);

        }else{
            errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nowifi));
            mRecyclerView.setVisibility(View.GONE);
            networkInfo.setVisibility(View.VISIBLE);
            newLoading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newWait();
                }
            });
        }
    }
    SessionData sessionData;
    private void isLogiin() {

        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
        if(isLogin){
            String  sessionString=SharedPreferences.getInstance().getString("session", "");
            sessionData = JSON.parseObject(sessionString,SessionData.class);
            if(sessionData!=null){
                newWait();



            }

        }else{
            Toast.makeText(mContext, "未登录，请先登录", Toast.LENGTH_SHORT).show();
        }
    }


//        initRecycleViewVertical();


    List<RedList> redListList;//
    public  int page=1;
    int  loadingTag=2;//刷新flag   2 默认   1 下拉刷新  -1是上拉更多
    private void initData(SessionData sessionDataOne) {
        String url ="http://mapp.aiderizhi.com/?url=/user/bonus";//

        Map<String, String> map = new HashMap<String, String>();




        if(loadingTag==-1){
            map = new HashMap<String, String>();
            PaginationJson paginationJson=new PaginationJson();
            paginationJson.setCount("10");
            paginationJson.setPage((++page)+"");
            String string = JSON.toJSONString(paginationJson);
            String  d="{\"pagination\":"+string+" ,\"type\":\"1\",\"session\":{\"uid\":\""+sessionDataOne.getUid()+"\",\"sid\":\""+sessionDataOne.getSid()+"\"}}";//type 2为收到的评论
            map.put("json", d);
            Log.d("CommentReceiveFragment", d + "》》》》");
        }
        if(loadingTag==2){//第一次加载数据
            map = new HashMap<String, String>();
            String oneString ="{\"type\":\"1\",\"session\":{\"uid\":\""+sessionDataOne.getUid()+"\",\"sid\":\""+sessionDataOne.getSid()+"\"}}";
            map.put("json",oneString);
            Log.d("CommentReceiveFragment", oneString + "》》》》");
        }



        RequestQueue mQueue = AppContextApplication.getInstance().getmRequestQueue();
        FastJsonRequest<RedPacketInfo> fastJsonCommunity=new FastJsonRequest<RedPacketInfo>(Request.Method.POST,url,RedPacketInfo.class,null,new Response.Listener<RedPacketInfo>()
        {
            @Override
            public void onResponse(RedPacketInfo redPacketInfo) {

                DataStatus status=redPacketInfo.getStatus();
                if(status.getSucceed()==1){

                    progressLinear.setVisibility(View.GONE);


                    if(loadingTag==-1){


                        List<RedList> p=redPacketInfo.getData().getList();
                        Log.d("CommentReceiveFragment", "" + redListList.size() + "1111++++promotePostDateList" );
                        for(int i=0;i<p.size();i++){
                            redListList.add(p.get(i));
                        }
                        Log.d("CommentReceiveFragment", "" + redListList.size() + "2222++++promotePostDateList" );






                        mRecyclerView.loadMoreComplete();
                    }
                    if(loadingTag==2){
                        redListList=redPacketInfo.getData().getList();


                        if(redListList!=null&&redListList.size()>0){
                            intView();//初始界面
                        }else{
                            progressLinear.setVisibility(View.GONE);
                            mRecyclerView.setVisibility(View.GONE);
                            errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nodata));
                            networkInfo.setVisibility(View.VISIBLE);
                            newLoading.setVisibility(View.GONE);
                            loadingTextLinear.setVisibility(View.VISIBLE);
                            loadingText.setText("您没有相关红包");
                        }
                        mRecyclerView.refreshComplete();
                    }



//                    Log.d("CommentReceiveFragment", "" + status.getSucceed() + "++++succeed》》》》" + promotePostDateList.get(0).getCat_name());
                } else {
                    // 请求失败
                    progressLinear.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nodata));
                    networkInfo.setVisibility(View.VISIBLE);
                    // 请求失败
                    Log.d("CommentReceiveFragment", "" + status.getSucceed() + "++++success=0》》》》" );

                    if(status.getError_code()==1000){
                        SharedPreferences.getInstance().putBoolean("islogin",false);
                        ViewUtill.ShowAlertDialog(mContext);
                    }

                }


            }
        } ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //未知错误
                progressLinear.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_default));
                networkInfo.setVisibility(View.VISIBLE);
                Log.d("CommentReceiveFragment", "errror" + volleyError.toString() + "++++》》》》" );
            }
        });

        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);










    }

    private RecycleRedpacketUnusedAdapter mAdapter;
    private void intView() {




        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);




        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {
                loadingTag = 2;//重新加载
                page=1;
                initData(sessionData);
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
                Log.d("CommentReceiveFragment", "initial    more");
                initData(sessionData);
//                        mRecyclerView.loadMoreComplete();
//                    }
//                }, 2000);

            }
        });




        if(redListList!=null&&redListList.size()>0){
            mAdapter = new RecycleRedpacketUnusedAdapter(redListList);

            mRecyclerView.setAdapter(mAdapter);
        }

    }


   /* // 创建数据集
    String[] dataset = new String[]{"张三","美女","拉丁","弟弟","火热","额额"};
    String[] dataValue=new String[]{"15083806689","15083806689","15083806689","15083806689","15083806689","15083806689"};
    // 创建Adapter，并指定数据集
    RecycleOrderAllAdapter adapter;
    public void initRecycleViewVertical(){

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        // 默认是Vertical，可以不写
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);


        // 创建Adapter，并指定数据集
        adapter = new RecycleOrderAllAdapter(dataset,dataValue);
        adapter.setOnCheckDefaultListener(this);
        // 设置Adapter
        recyclerView.setAdapter(adapter);

    }*/

    @Override
    public void oncheckOK(Boolean[] ischeckArray) {
//        adapter.notifyDataSetChanged();
    }
}
