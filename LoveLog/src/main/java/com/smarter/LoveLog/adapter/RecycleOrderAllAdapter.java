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

import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecycleOrderAllAdapter extends RecyclerView.Adapter<RecycleOrderAllAdapter.ViewHolder>{



    RequestQueue mQueue;

    // 数据集
    List<OrderList> orderLists;

    public RecycleOrderAllAdapter(List<OrderList> orderLists) {
        super();
        this.orderLists=orderLists;
        mQueue =  AppContextApplication.getInstance().getmRequestQueue();
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_order_all_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        // 绑定数据到ViewHolder上
        OrderList  orderList=orderLists.get(i);
        viewHolder.commodityName.setText(orderList.getOrder_sn());
        viewHolder.desInfo.setText(orderList.getOrder_info().getSubject());
        viewHolder.shiMoney.setText(orderList.getOrder_info().getOrder_amount());

        viewHolder.iv_adapter_grid_pic.setDefaultImageResId(R.drawable.loading_small);
        viewHolder.iv_adapter_grid_pic.setErrorImageResId(R.drawable.loading_small);
        String UserimageUrl="";
        if(orderList.getGoods_list().get(0).getImg().getThumb()!=null){
            UserimageUrl=orderList.getGoods_list().get(0).getImg().getThumb();
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

        public TextView commodityName,information,desInfo,shiMoney;
        public NetworkImageView iv_adapter_grid_pic;


        public ViewHolder(View itemView) {
            super(itemView);
            commodityName = (TextView) itemView.findViewById(R.id.commodityName);
            information = (TextView) itemView.findViewById(R.id.information);
            desInfo= (TextView) itemView.findViewById(R.id.desInfo);
            shiMoney= (TextView) itemView.findViewById(R.id.shiMoney);

            iv_adapter_grid_pic= (NetworkImageView) itemView.findViewById(R.id.iv_adapter_grid_pic);


        }
    }
}