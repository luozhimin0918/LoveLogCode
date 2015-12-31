package com.smarter.LoveLog.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.smarter.LoveLog.R;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecycleOrderObligationAdapter extends RecyclerView.Adapter<RecycleOrderObligationAdapter.ViewHolder>{





    // 数据集
    private String[] mName;
    private String[] mPhoneNum;

    public RecycleOrderObligationAdapter(String[] names, String[] phones) {
        super();
        mName = names;
        mPhoneNum=phones;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_order_obgation_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        // 绑定数据到ViewHolder上
        viewHolder.commodityName.setText(mName[i]);
        viewHolder.information.setText(mPhoneNum[i]);



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
        return mName.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView commodityName,information;


        public ViewHolder(View itemView) {
            super(itemView);
            commodityName = (TextView) itemView.findViewById(R.id.commodityName);
            information = (TextView) itemView.findViewById(R.id.information);


        }
    }
}