package com.smarter.LoveLog.adapter;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import com.smarter.LoveLog.R;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecyclePersonAdapter extends RecyclerView.Adapter<RecyclePersonAdapter.ViewHolder>{





    // 数据集
    private String[] mTitleset;
    private String[] mValueGetText;
    public RecyclePersonAdapter(String[] dataset,String[] valueGet) {
        super();
        mTitleset = dataset;
        mValueGetText=valueGet;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_personal, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 绑定数据到ViewHolder上
        viewHolder.titleText.setText(mTitleset[i]);
        viewHolder.valueGetText.setText(mValueGetText[i]);
    }

    @Override
    public int getItemCount() {
        return mTitleset.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView titleText;
        public TextView valueGetText;
        public ViewHolder(View itemView) {
            super(itemView);
            titleText = (TextView) itemView.findViewById(R.id.titleText);
            valueGetText=(TextView)itemView.findViewById(R.id.valueGetText);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    setOnItemClickListener.onItemClickAdapter(getPosition());
                    Log.d("RecyclePersonAdapter", "当前点击的位置：" + getPosition());
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