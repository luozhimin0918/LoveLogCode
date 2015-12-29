package com.smarter.LoveLog.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarter.LoveLog.R;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecyclePinglunAdapter extends RecyclerView.Adapter<RecyclePinglunAdapter.ViewHolder>{





    // 数据集
    private String[] mTitleset;
    private String[] mValueGetText;
    public RecyclePinglunAdapter(String[] dataset, String[] valueGet) {
        super();
        mTitleset = dataset;
        mValueGetText=valueGet;
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
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 绑定数据到ViewHolder上

    }

    @Override
    public int getItemCount() {
        return mTitleset.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{


        public ViewHolder(View itemView) {
            super(itemView);

        }
    }
}