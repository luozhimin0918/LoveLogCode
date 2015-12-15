package com.smarter.LoveLog.adapter;

/**
 * Created by Administrator on 2015/12/7.
 */
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.smarter.LoveLog.R;

import java.util.ArrayList;

/**
 * Created by jianghejie on 15/11/26.
 */
public class MyAdapter extends RecyclerView.Adapter<MyAdapter.ViewHolder> {
    public int[] datas = null;
    public MyAdapter(int[] datas) {
        this.datas = datas;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.xrecy_item,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int position) {
        viewHolder.imglist.setImageResource(datas[position]);
    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return datas.length;
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView imglist;
        public LinearLayout views;
        public ViewHolder(View view){
            super(view);
            imglist = (ImageView) view.findViewById(R.id.imglist);
            views=(LinearLayout) view.findViewById(R.id.view);
        }
    }
}

