package com.smarter.LoveLog.adapter;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.activity.AddressManageActivity;
import com.smarter.LoveLog.activity.CreateAddressActivity;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.address.AddressData;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.home.DataStatusOne;
import com.smarter.LoveLog.model.loginData.SessionData;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecycleAddressAdapter extends RecyclerView.Adapter<RecycleAddressAdapter.ViewHolder>{





    // 数据集
    private  List<AddressData> addrelit;

    RequestQueue mQueue;
    SessionData sessionData;
    Context mContext;

    public RecycleAddressAdapter(List<AddressData> addressDataList_InitRecycle,Context mContext) {
        super();
        mQueue= AppContextApplication.getInstance().getmRequestQueue();
        this.addrelit=addressDataList_InitRecycle;
        this.mContext=mContext;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_address_manage_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);



        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
        if(isLogin) {
            String sessionString = SharedPreferences.getInstance().getString("session", "");
           sessionData = JSON.parseObject(sessionString, SessionData.class);

        }
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        // 绑定数据到ViewHolder上
        viewHolder.nameTextView.setText(addrelit.get(i).getConsignee());
        viewHolder.phoneTextview.setText(addrelit.get(i).getMobile());
        viewHolder.addressShouhuo.setText(addrelit.get(i).getAddress());
        if(addrelit.get(i).getIs_default()==1){
            viewHolder.default_address.setVisibility(View.VISIBLE);
            viewHolder.default_text.setVisibility(View.GONE);
            viewHolder.isImage.setBackgroundResource(R.mipmap.choiceon);
        }else{
            viewHolder.default_address.setVisibility(View.GONE);
            viewHolder.default_text.setVisibility(View.VISIBLE);
            viewHolder.isImage.setBackgroundResource(R.mipmap.choice);

        }





     //删除地址
       viewHolder.deleteAddress.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               if (sessionData != null) {
                   String param =" {\"session\":{\"uid\":\""+sessionData.getUid()+"\",\"sid\":\""+sessionData.getSid()+"\"},\"id\":\""+addrelit.get(i).getId()+"\"}";
                   networkAddAddressInfo(param,i);
               }

           }
       });
      //编辑地址
        viewHolder.bianjAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //挑战到创建收货地址
                // 界面
                Intent intent2 = new Intent(mContext, CreateAddressActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("AddressData",addrelit.get(i));
                bundle.putString("xiugaiAddress", "修改收货地址");
                intent2.putExtras(bundle);
                mContext.startActivity(intent2);
            }
        });
        viewHolder.idDefaul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        for(int j=0;j<addrelit.size();j++){
                           addrelit.get(j).setIs_default(0);
                        }
                        addrelit.get(i).setIs_default(1);

                OnCheckDefaultListener.oncheckOK(addrelit);
            }
        });

    }


    //回调开始
    public interface OnCheckDefaultListener {
        void oncheckOK(List<AddressData>  ischeckArray);
    }
    private OnCheckDefaultListener OnCheckDefaultListener;

    public void setOnCheckDefaultListener(OnCheckDefaultListener onCheckDefault) {
        this.OnCheckDefaultListener=onCheckDefault;
    }
    //结束
    @Override
    public int getItemCount() {
        return addrelit.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nameTextView,phoneTextview,addressShouhuo,default_address,default_text,deleteAddress,bianjAddress;
        public LinearLayout idDefaul;
        public ImageView isImage;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            phoneTextview = (TextView) itemView.findViewById(R.id.phoneTextview);
            idDefaul= (LinearLayout) itemView.findViewById(R.id.idDefaul);
            addressShouhuo= (TextView) itemView.findViewById(R.id.addressShouhuo);
            default_address= (TextView) itemView.findViewById(R.id.default_address);
            default_text= (TextView) itemView.findViewById(R.id.default_text);
            deleteAddress= (TextView) itemView.findViewById(R.id.deleteAddress);
            bianjAddress =(TextView) itemView.findViewById(R.id.bianjAddress);
            isImage= (ImageView) itemView.findViewById(R.id.isImage);

        }
    }















    private void networkAddAddressInfo(String  param,final  int i) {

        String url = "http://mapp.aiderizhi.com/?url=/address/delete";//
        Map<String, String> map = new HashMap<String, String>();
        map.put("json", param);
        Log.d("RecycleAddressAdapter", param + "      ");


        FastJsonRequest<DataStatusOne> fastJsonCommunity = new FastJsonRequest<DataStatusOne>(Request.Method.POST, url, DataStatusOne.class, null, new Response.Listener<DataStatusOne>() {
            @Override
            public void onResponse(DataStatusOne dataStatusOne) {
                DataStatus dataStatus=dataStatusOne.getStatus();
                if (dataStatus.getSucceed() == 1) {

                     addrelit.remove(i);
                    OnCheckDefaultListener.oncheckOK(addrelit);
                    Toast.makeText(mContext,"删除成功",Toast.LENGTH_SHORT).show();

                    Log.d("RecycleAddressAdapter", "删除返回的信息：   " + JSON.toJSONString(dataStatus)+ "++++succeed");



                } else {

                    // 请求失败
                    Log.d("RecycleAddressAdapter", "succeded=00000  " + JSON.toJSONString(dataStatus) + "");

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("RecycleAddressAdapter", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //fastJsonCommunity.setTag(TAG);
        fastJsonCommunity.setParams(map);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }

}