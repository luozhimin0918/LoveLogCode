package com.smarter.LoveLog.adapter;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarter.LoveLog.R;
import com.smarter.LoveLog.model.itegralself.ItegralDataList;

import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecycleItegralSelfAdapter extends RecyclerView.Adapter<RecycleItegralSelfAdapter.ViewHolder>{





    // 数据集
    List<ItegralDataList> itegralDataLists;
    public RecycleItegralSelfAdapter(List<ItegralDataList> itegralDataLists) {
        super();
        this.itegralDataLists=itegralDataLists;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_itegralself_list, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 绑定数据到ViewHolder上
            viewHolder.changeDesc.setText(itegralDataLists.get(i).getChange_desc()+"(商品号:"+itegralDataLists.get(i).getLog_id()+")");
            viewHolder.changeTime.setText(itegralDataLists.get(i).getChange_time());
           viewHolder.prefixValue.setText(itegralDataLists.get(i).getPrefix()+itegralDataLists.get(i).getValue());
    }

    @Override
    public int getItemCount() {
        return itegralDataLists.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView changeDesc;
        public TextView changeTime;
        public TextView prefixValue;
        public ViewHolder(View itemView) {
            super(itemView);
            changeDesc = (TextView) itemView.findViewById(R.id.changeDesc);
            changeTime=(TextView)itemView.findViewById(R.id.changeTime);
            prefixValue=(TextView)itemView.findViewById(R.id.prefixValue);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    setOnItemClickListener.onItemClickAdapter(getPosition());
                    Log.d("RecycleItegralAda", "当前点击的位置：" + getPosition());
                }
            });
        }
    }



    //回调开始
    public interface OnItemClickListener {
        void onItemClickAdapter(int ischeckArray);
    }
    public static OnItemClickListener setOnItemClickListener;

    public void setOnItemClickListener(OnItemClickListener onCheckDefault) {
        this.setOnItemClickListener=onCheckDefault;
    }
    //结束
}