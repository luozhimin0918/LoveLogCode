package com.smarter.LoveLog.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.model.community.Pinglun;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.ui.CircleNetworkImage;

import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecycleReceivePinglunAdapter extends RecyclerView.Adapter<RecycleReceivePinglunAdapter.ViewHolder>{





    // 数据集

    private List<Pinglun> pinglunList;
    RequestQueue mQueue;
    public RecycleReceivePinglunAdapter(List<Pinglun> pinglunList) {
        super();
        mQueue =  AppContextApplication.getInstance().getmRequestQueue();
        this.pinglunList=pinglunList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_comment_pinglun_receive_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 绑定数据到ViewHolder上
        User user=pinglunList.get(i).getUser();
        String UserimageUrl=user.getAvatar();
        viewHolder.imageTitle.setDefaultImageResId(R.mipmap.loadding);
        viewHolder.imageTitle.setErrorImageResId(R.mipmap.loadding);
        if(mQueue.getCache().get(UserimageUrl)==null){
            viewHolder.imageTitle.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        viewHolder.imageTitle.setImageUrl(UserimageUrl, AppContextApplication.getInstance().getmImageLoader());

        viewHolder.pinglunTitle.setText(pinglunList.get(i).getObject().getTitle());

        viewHolder.userName.setText(user.getName());
        viewHolder.AddTime.setText(pinglunList.get(i).getAdd_time());
        viewHolder.pingContent.setText(pinglunList.get(i).getContent());
    }

    @Override
    public int getItemCount() {
        return pinglunList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public CircleNetworkImage imageTitle;
        public TextView userName,AddTime,pingContent,pinglunTitle;
        public ViewHolder(View itemView) {
            super(itemView);
            imageTitle= (CircleNetworkImage) itemView.findViewById(R.id.imageTitle);
            userName= (TextView) itemView.findViewById(R.id.userName);
            AddTime= (TextView) itemView.findViewById(R.id.AddTime);
            pingContent= (TextView) itemView.findViewById(R.id.pingContent);
            pinglunTitle= (TextView) itemView.findViewById(R.id.pinglunTitle);
        }
    }
}