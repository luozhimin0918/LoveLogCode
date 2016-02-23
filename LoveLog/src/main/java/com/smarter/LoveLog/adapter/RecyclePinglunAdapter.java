package com.smarter.LoveLog.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.address.AddressData;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.community.ZanOrFaroDataInfo;
import com.smarter.LoveLog.model.community.ZanOrfaroData;
import com.smarter.LoveLog.model.goods.CmtGoods;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.jsonModel.ZanOrFaroviteParame;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.ui.CircleNetworkImage;
import com.smarter.LoveLog.ui.QCheckBox;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecyclePinglunAdapter extends RecyclerView.Adapter<RecyclePinglunAdapter.ViewHolder>{





    // 数据集

    private List<CmtGoods> pinglunList;
    RequestQueue mQueue;
    Context mContext;
    public RecyclePinglunAdapter(List<CmtGoods> pinglunList,Context mContext) {
        super();
        mQueue =  AppContextApplication.getInstance().getmRequestQueue();
        this.pinglunList=pinglunList;
        this.mContext=mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_invitation_pinglun_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        // 绑定数据到ViewHolder上
        User user=pinglunList.get(i).getUser();
        String UserimageUrl=user.getAvatar();
        viewHolder.imageTitle.setDefaultImageResId(R.drawable.loading);
        viewHolder.imageTitle.setErrorImageResId(R.drawable.loading);
        if(mQueue.getCache().get(UserimageUrl)==null){
            viewHolder.imageTitle.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        viewHolder.imageTitle.setImageUrl(UserimageUrl, AppContextApplication.getInstance().getmImageLoader());



        viewHolder.userName.setText(user.getName());
        viewHolder.AddTime.setText(pinglunList.get(i).getAdd_time());
        viewHolder.pingContent.setText(pinglunList.get(i).getContent());

        if(pinglunList.get(i).getIs_digg().equals("1")){
            viewHolder.zanBut.setChecked(true);
        }else{
            viewHolder.zanBut.setChecked(false);
        }
        viewHolder.zanBut.setText(pinglunList.get(i).getDigg_count());
        viewHolder.zanBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String url = "http://mapp.aiderizhi.com/?url=/comment/digg";//点赞
                initIsLogonParame(url,pinglunList.get(i).getCmt_id());
            }
        });
    }


    SessionData sessionData;
    private void initIsLogonParame(String url,String cmtId) {

        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
        if(isLogin){
            String  sessionString=SharedPreferences.getInstance().getString("session", "");
            sessionData = JSON.parseObject(sessionString, SessionData.class);
            if(sessionData!=null){

                ZanOrFaroviteParame zanOrFaroviteInfo=new ZanOrFaroviteParame();
                zanOrFaroviteInfo.setSession(sessionData);
                zanOrFaroviteInfo.setId(cmtId);
//                zanOrFaroviteInfo.setType("2");




                    networkTieZan(JSON.toJSONString(zanOrFaroviteInfo),url,cmtId);




            }

        }else{
            Toast.makeText(mContext, "未登录，请先登录", Toast.LENGTH_SHORT).show();
        }
    }




    /**
     * 帖子点赞
     */
    ZanOrfaroData zanOrfaroData;
    private void networkTieZan(String paramNet,String url, final String cmtId) {

        Map<String, String> mapTou = new HashMap<String, String>();
        mapTou.put("json", paramNet);




        Log.d("RecyclePinglunAdap", paramNet + "      ");


        FastJsonRequest<ZanOrFaroDataInfo> fastJsonCommunity = new FastJsonRequest<ZanOrFaroDataInfo>(Request.Method.POST, url, ZanOrFaroDataInfo.class, null, new Response.Listener<ZanOrFaroDataInfo>() {
            @Override
            public void onResponse(ZanOrFaroDataInfo zanOrFaroDataInfo) {

                DataStatus status = zanOrFaroDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    zanOrfaroData = zanOrFaroDataInfo.getData();
                    if(zanOrfaroData!=null){

                        for(int i=0;i<pinglunList.size();i++){
                            if(pinglunList.get(i).getCmt_id().equals(cmtId)){
                                pinglunList.get(i).setDigg_count(zanOrfaroData.getTotal());
                                pinglunList.get(i).setIs_digg("1");
                            }
                        }
                        notifyDataSetChanged();
                        Toast.makeText(mContext, "点赞成功", Toast.LENGTH_SHORT).show();
                        Log.d("RecyclePinglunAdap", "RecyclePinglunAdap 成功返回信息：   " + JSON.toJSONString(zanOrfaroData)+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("RecyclePinglunAdap", "succeded=0  RecyclePinglunAdap 返回信息 " + JSON.toJSONString(status) + "");
                    Toast.makeText(mContext, "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("RecyclePinglunAdap", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }


    @Override
    public int getItemCount() {
        return pinglunList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public CircleNetworkImage imageTitle;
        public TextView userName,AddTime,pingContent;
        public QCheckBox zanBut;
        public ViewHolder(View itemView) {
            super(itemView);
            imageTitle= (CircleNetworkImage) itemView.findViewById(R.id.imageTitle);
            userName= (TextView) itemView.findViewById(R.id.userName);
            AddTime= (TextView) itemView.findViewById(R.id.AddTime);
            pingContent= (TextView) itemView.findViewById(R.id.pingContent);
            zanBut= (QCheckBox) itemView.findViewById(R.id.zanBut);
        }
    }
}