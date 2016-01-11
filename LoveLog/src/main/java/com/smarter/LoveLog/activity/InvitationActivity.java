package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.MofanAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.fragment.CommunityFragment;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.category.InvitationDataActi;
import com.smarter.LoveLog.model.community.CommunityDataFrag;
import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.home.NavIndexUrlData;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    @Bind(R.id.tv_top_title)
    TextView tv_top_title;
    @Bind(R.id.search_editText)
    EditText search_editText;




    private MofanAdapter mAdapter;
    private int[] lit_int_resuour={R.mipmap.list1,R.mipmap.list2,R.mipmap.list1,R.mipmap.list2,R.mipmap.list1,R.mipmap.list2};




    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_view);
        ButterKnife.bind(this);
        mContext=this;

        getDataIntent();
//        intData();
        setListen();

    }

    private void setListen() {
        search_editText.addTextChangedListener(new EditChangedListener());
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

                new Handler().postDelayed(new Runnable() {
                    public void run() {

                        mRecyclerView.refreshComplete();


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

       if(promotePostDateList!=null&&promotePostDateList.size()>0){
           mAdapter = new MofanAdapter(mContext,promotePostDateList);

           mRecyclerView.setAdapter(mAdapter);
       }

    }



    NavIndexUrlData navIndexUrlData;//上个activity传来的数据
    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
             navIndexUrlData= (NavIndexUrlData) intent.getSerializableExtra("NavIndexUrlData");
            tv_top_title.setText(navIndexUrlData.getName());//设置titlebar 标题
            initData(navIndexUrlData.getId());
//            Toast.makeText(this, navIndexUrlData.getName() + "", Toast.LENGTH_LONG).show();
        }


    }


    List<PromotePostsData> promotePostDateList;//本类帖子 分类里所有数据
    List<PromotePostsData> FinalpromotePostDateList;//本类帖子 分类里所有数据
    int page=0;
    private void initData(String id) {
         String url ="http://mapp.aiderizhi.com/?url=/post/category";//
        RequestQueue mQueue = AppContextApplication.getInstance().getmRequestQueue();
        FastJsonRequest<InvitationDataActi> fastJsonCommunity=new FastJsonRequest<InvitationDataActi>(Request.Method.POST,url,InvitationDataActi.class,null,new Response.Listener<InvitationDataActi>()
        {
            @Override
            public void onResponse(InvitationDataActi invitationDataActi) {

                DataStatus status=invitationDataActi.getStatus();
                if(status.getSucceed()==1){
                    promotePostDateList=invitationDataActi.getData();
                    FinalpromotePostDateList=invitationDataActi.getData();
                    intData();//初始界面

                    Log.d("InvitationActivityURL", "" + status.getSucceed() + "++++succeed》》》》" + promotePostDateList.get(0).getCat_name());
                } else {
                    if(promotePostDateList!=null) {
                        intData();//初始界面
                    }else{
                        // 请求失败
                        Log.d("InvitationActivityURL", "" + status.getSucceed() + "++++succeed》》》》" );
                    }
                }


            }
        } ,new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        });
        Map<String, String> map = new HashMap<String, String>();
        map.put("id", id);
//        map.put("pagination","{\"page\":\"5\",\"count\":10}");
//        map.put("page","2");
//        map.put("count","1");
        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);
    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){

         }
    }



    class EditChangedListener implements TextWatcher {
        private CharSequence temp;//监听前的文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置
        private final int charMaxNum = 10;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            promotePostDateList=FinalpromotePostDateList;//回到原来的全部数据
            Log.i(Tag, "输入文本之前的状态   ");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
             List<PromotePostsData> postsDatas=new ArrayList<PromotePostsData>();
                    for(int p=0;p<promotePostDateList.size();p++){
                           if(promotePostDateList.get(p).getTitle().equals(s.toString())||promotePostDateList.get(p).getUser().getName().equals(s.toString())){
                               postsDatas.add(promotePostDateList.get(p));
                           }else {

                               Log.i(Tag, "———————————————"+s);
                           }
                    }
            mAdapter = new MofanAdapter(mContext,postsDatas);
            mRecyclerView.setAdapter(mAdapter);
//            mAdapter.notifyDataSetChanged();
            Log.i(Tag, "输入文字中的状态，count是一次性输入字符数——————————————————————"+s);

        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.i(Tag, "输入文字后的状态");
            /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */


        }
    }


}
