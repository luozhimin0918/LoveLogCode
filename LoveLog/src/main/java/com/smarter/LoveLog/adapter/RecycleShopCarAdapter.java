package com.smarter.LoveLog.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.model.orderMy.OrderList;
import com.smarter.LoveLog.model.orderMy.ShopCarOrderInfo;

import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecycleShopCarAdapter extends RecyclerView.Adapter<RecycleShopCarAdapter.ViewHolder>{



    RequestQueue mQueue;

    // 数据集
    List<ShopCarOrderInfo.DataEntity.GoodsListEntity> orderLists;

    public RecycleShopCarAdapter(List<ShopCarOrderInfo.DataEntity.GoodsListEntity> orderLists) {
        super();
        this.orderLists=orderLists;
        mQueue =  AppContextApplication.getInstance().getmRequestQueue();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_shop_car_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        // 绑定数据到ViewHolder上
        ShopCarOrderInfo.DataEntity.GoodsListEntity  goodsListOne=orderLists.get(i);
        viewHolder.desInfo.setText(goodsListOne.getGoods_name());

        viewHolder.iv_adapter_grid_pic.setDefaultImageResId(R.drawable.loading_small);
        viewHolder.iv_adapter_grid_pic.setErrorImageResId(R.drawable.loading_small);
        String UserimageUrl="";
        if(goodsListOne.getImg_thumb()!=null){
            UserimageUrl=goodsListOne.getImg_thumb();
        }

        if(mQueue.getCache().get(UserimageUrl)==null){
            viewHolder.iv_adapter_grid_pic.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        viewHolder.iv_adapter_grid_pic.setImageUrl(UserimageUrl, AppContextApplication.getInstance().getmImageLoader());




    }


    //回调开始
    public interface OnCheckDefaultListener {
        void oncheckOK(Boolean[] ischeckArray);
    }
    private OnCheckDefaultListener OnCheckDefaultListener;

    public void setOnCheckDefaultListener(OnCheckDefaultListener onCheckDefault) {
        this.OnCheckDefaultListener=onCheckDefault;
    }
    //结束
    @Override
    public int getItemCount() {
        return orderLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView desInfo;
        public NetworkImageView iv_adapter_grid_pic;


        public ViewHolder(View itemView) {
            super(itemView);

            desInfo= (TextView) itemView.findViewById(R.id.desInfo);

            iv_adapter_grid_pic= (NetworkImageView) itemView.findViewById(R.id.iv_adapter_grid_pic);


        }
    }
}