package com.smarter.LoveLog.adapter;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
import com.android.volley.toolbox.NetworkImageView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.PaginationJson;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.model.orderMy.ShopCarOrderInfo;
import com.smarter.LoveLog.utills.ViewUtill;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecycleShopCarAdapter extends RecyclerView.Adapter<RecycleShopCarAdapter.ViewHolder> {


    RequestQueue mQueue;
    Boolean  isLogin;
    // 数据集
    List<ShopCarOrderInfo.DataEntity.GoodsListEntity> orderLists;

    //编辑完成的数据
    List<ShopCarOrderInfo.DataEntity.GoodsListEntity> tempGoodsLists;

    SessionData sessionData;

    Context mContxt;

    public RecycleShopCarAdapter(List<ShopCarOrderInfo.DataEntity.GoodsListEntity> orderLists,Context mContxt) {
        super();
        this.orderLists = orderLists;
        mQueue = AppContextApplication.getInstance().getmRequestQueue();
        this.tempGoodsLists=new ArrayList<ShopCarOrderInfo.DataEntity.GoodsListEntity>();
        this.mContxt=mContxt;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_shop_car_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
         isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
        if(isLogin){
            String sessionString = SharedPreferences.getInstance().getString("session", "");
            sessionData = JSON.parseObject(sessionString, SessionData.class);

        }

        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder viewHolder, final int i) {
        // 绑定数据到ViewHolder上
       final ShopCarOrderInfo.DataEntity.GoodsListEntity goodsListOne = orderLists.get(i);

        viewHolder.desInfo.setText(goodsListOne.getGoods_name());
        viewHolder.shopPrice.setText(goodsListOne.getGoods_price());
        viewHolder.shopCarNum.setText(goodsListOne.getGoods_number());
        viewHolder.ShopCarNumZhi.setText(goodsListOne.getGoods_number());

        viewHolder.ivAdapterGridPic.setDefaultImageResId(R.drawable.loading_small);
        viewHolder.ivAdapterGridPic.setErrorImageResId(R.drawable.loading_small);
        String UserimageUrl = "";
        if (goodsListOne.getImg_thumb() != null) {
            UserimageUrl = goodsListOne.getImg_thumb();
        }

        if (mQueue.getCache().get(UserimageUrl) == null) {
            viewHolder.ivAdapterGridPic.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        viewHolder.ivAdapterGridPic.setImageUrl(UserimageUrl, AppContextApplication.getInstance().getmImageLoader());




        if(goodsListOne.is_all_edit()){
            viewHolder.wanchenProgress.setVisibility(View.GONE);
            viewHolder.bianjiProgress.setVisibility(View.VISIBLE);
        }else{
            viewHolder.wanchenProgress.setVisibility(View.VISIBLE);
            viewHolder.bianjiProgress.setVisibility(View.GONE);
        }



        if(goodsListOne.is_all_select()){
            viewHolder.isImage.setBackgroundResource(R.mipmap.choiceon);
        }else{
            viewHolder.isImage.setBackgroundResource(R.mipmap.choice);
        }


        viewHolder.popAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(viewHolder.ShopCarNumZhi.getText().toString())+5;
                goodsListOne.setGoods_number(num + "");
                notifyDataSetChanged();


            }
        });
        viewHolder.popReduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int num = Integer.parseInt(viewHolder.ShopCarNumZhi.getText().toString())-5;
                if(num>=5){
                    goodsListOne.setGoods_number(num+"");
                    notifyDataSetChanged();

                }


            }
        });

        viewHolder.isImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(goodsListOne.is_all_select()){
                    goodsListOne.setIs_all_select(false);
                    if(isQuxuan){//当全选时，又取消了一个选项。回调
                        OnCheckDefaultListener.onAllselectToCanter(true);
                    }

                }else{
                    goodsListOne.setIs_all_select(true);


                    /**
                     * list一个个点击全选之后的回调
                     */
                    int numOrdeSele=0;
                    for(int o=0;o<orderLists.size();o++){
                        if(orderLists.get(o).is_all_select()){
                            ++numOrdeSele;
                        }
                    }
                    if(numOrdeSele==orderLists.size()){
                        OnCheckDefaultListener.onAllselectToCanter(false);
                        isQuxuan=true;//表示全选了
                    }

                }

                notifyDataSetChanged();
            }
        });


        viewHolder.deleteBut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isLogin) {
                    if (sessionData != null) {
                            initData(sessionData,orderLists.get(i),"delete");
                    }

                }
            }
        });


    }


    //回调开始
    public interface OnCheckDefaultListener {
        void oncheckOK(Boolean[] ischeckArray);
        void onAllselectToCanter(boolean isquxiaoQuxian);
        void onDeleteAll();
    }

    private OnCheckDefaultListener OnCheckDefaultListener;

    public void setOnCheckDefaultListener(OnCheckDefaultListener onCheckDefault) {
        this.OnCheckDefaultListener = onCheckDefault;
    }

    //结束
    @Override
    public int getItemCount() {
        return orderLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        @Bind(R.id.isImage)
        ImageView isImage;
        @Bind(R.id.iv_adapter_grid_pic)
        NetworkImageView ivAdapterGridPic;

        @Bind(R.id.desInfo)
        TextView desInfo;
        @Bind(R.id.shopPrice)
        TextView shopPrice;
        @Bind(R.id.shopCarNum)
        TextView shopCarNum;
        @Bind(R.id.ShopCarNumZhi)
        TextView ShopCarNumZhi;


        @Bind(R.id.wanchenProgress)
        LinearLayout wanchenProgress;
        @Bind(R.id.pop_reduce)
        ImageView popReduce;
        @Bind(R.id.pop_add)
        ImageView popAdd;
        @Bind(R.id.bianjiProgress)
        LinearLayout bianjiProgress;
        @Bind(R.id.pop_num)
        TextView popNum;
        @Bind(R.id.deleteBut)
        TextView deleteBut;



        ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
        }
    }

    /*public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView desInfo;
        public NetworkImageView iv_adapter_grid_pic;


        public ViewHolder(View itemView) {
            super(itemView);

            desInfo= (TextView) itemView.findViewById(R.id.desInfo);
            iv_adapter_grid_pic= (NetworkImageView) itemView.findViewById(R.id.iv_adapter_grid_pic);


        }
    }*/


    /**
     * 更新购物车数据
     * @param sessionDataOne
     */
    private void initData(SessionData sessionDataOne,final ShopCarOrderInfo.DataEntity.GoodsListEntity goodsListEntityOne, final String action) {


        String url = "http://mapp.aiderizhi.com/?url=/cart/"+action;//

        Map<String, String> map = new HashMap<String, String>();



            map = new HashMap<String, String>();
        String oneString="";
         if(action.equals("delete")){
             oneString = "{\"rec_id\":\""+goodsListEntityOne.getRec_id()+"\",\"session\":{\"uid\":\"" + sessionDataOne.getUid() + "\",\"sid\":\"" + sessionDataOne.getSid() + "\"}}";

         }else{
             oneString = "{\"rec_id\":\""+goodsListEntityOne.getRec_id()+"\",\"new_number\":\""+Integer.parseInt(goodsListEntityOne.getGoods_number())+"\",\"session\":{\"uid\":\"" + sessionDataOne.getUid() + "\",\"sid\":\"" + sessionDataOne.getSid() + "\"}}";

         }
        map.put("json", oneString);
            Log.d("recyShopCarAdapter", oneString + "》》》》");



        RequestQueue mQueue = AppContextApplication.getInstance().getmRequestQueue();
        FastJsonRequest<ShopCarOrderInfo> fastJsonCommunity = new FastJsonRequest<ShopCarOrderInfo>(Request.Method.POST, url, ShopCarOrderInfo.class, null, new Response.Listener<ShopCarOrderInfo>() {
            @Override
            public void onResponse(ShopCarOrderInfo myOrderInfo) {

                ShopCarOrderInfo.StatusEntity status = myOrderInfo.getStatus();
                if (status.getSucceed() == 1) {


                    if(action.equals("delete")){
                        orderLists.remove(goodsListEntityOne);

                        if(orderLists.size()==0){
                            OnCheckDefaultListener.onDeleteAll();
                        }else {
                            notifyDataSetChanged();
                        }

                    }



                    Log.d("recyShopCarAdapter", "" + status.getSucceed() + "++++succeed》》》》" );
                } else {

//                      Toast.makeText(mContxt,""+status.getError_desc(),Toast.LENGTH_SHORT).show();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //未知错误

                Log.d("recyShopCarAdapter", "errror" + volleyError.toString() + "++++》》》》");
            }
        });

        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);


    }


    Boolean  isQuxuan=false;//是否全选list
    public void myNotifiAdapter(boolean isAllSelect,boolean isAllEdit,boolean isSelectOrEdit){
                   for(int i=0;i<orderLists.size();i++){
                       if(isSelectOrEdit){
                           this.orderLists.get(i).setIs_all_select(isAllSelect);
                           isQuxuan=isAllSelect;
                       }else {
                           this.orderLists.get(i).setIs_all_edit(isAllEdit);
                       }



                   }



                    if(!isAllEdit){//完成编辑


                        if (isLogin) {
                            if (sessionData != null) {

                                        for(int t=0;t<orderLists.size();t++){

                                                initData(sessionData,orderLists.get(t),"update");
                                        }



                            }

                        }

                    }
                        notifyDataSetChanged();









    }
}