package com.smarter.LoveLog.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.Volley;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.MofanAdapter;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.Weather;
import com.smarter.LoveLog.model.WeatherInfo;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class InvitationActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "InvitationActivity";
    Context  mContext;


    /**
     * RecyclerView
     */
    @Bind(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    private MofanAdapter mAdapter;
    private int[] lit_int_resuour={R.mipmap.list1,R.mipmap.list2,R.mipmap.list1,R.mipmap.list2,R.mipmap.list1,R.mipmap.list2};




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_view);
        ButterKnife.bind(this);
        mContext=this;

        getDataIntent();
        intData();
        setListen();

    }

    private void setListen() {

    }

    private void intData() {




        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.BallSpinFadeLoader);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable(){
                    public void run() {
                        String url="http://www.weather.com.cn/data/sk/101010100.html";
                        RequestQueue mQueue = Volley.newRequestQueue(mContext);
                        FastJsonRequest<Weather> fastJson=new FastJsonRequest<Weather>(url, Weather.class,
                                new Response.Listener<Weather>() {

                                    @Override
                                    public void onResponse(Weather weather) {
                                        // TODO Auto-generated method stub
                                        WeatherInfo weatherInfo = weather.getWeatherinfo();
                                        Log.d("HomeFragment", "" + weatherInfo.getCity() + ">>>" + weatherInfo.toString());
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


                        mRecyclerView.loadMoreComplete();
                    }
                }, 2000);

            }
        });


        mAdapter = new MofanAdapter(mContext,lit_int_resuour);

        mRecyclerView.setAdapter(mAdapter);
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

         }
    }





}