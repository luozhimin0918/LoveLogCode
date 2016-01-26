package com.smarter.LoveLog.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.model.community.Pinglun;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.notifacation.notificationData;
import com.smarter.LoveLog.ui.CircleNetworkImage;

import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecycleNotifacationAdapter extends RecyclerView.Adapter<RecycleNotifacationAdapter.ViewHolder>{





    // 数据集

    List<notificationData> notificationDataList;
    RequestQueue mQueue;
    public RecycleNotifacationAdapter(List<notificationData> notificationDataList) {
        super();
        mQueue =  AppContextApplication.getInstance().getmRequestQueue();
        this.notificationDataList=notificationDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_notification_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 绑定数据到ViewHolder上
        String UserimageUrl=notificationDataList.get(i).getIcon();
        viewHolder.imageTitle.setDefaultImageResId(R.mipmap.loadding);
        viewHolder.imageTitle.setErrorImageResId(R.mipmap.loadding);
        if(mQueue.getCache().get(UserimageUrl)==null){
            viewHolder.imageTitle.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        viewHolder.imageTitle.setImageUrl(UserimageUrl, AppContextApplication.getInstance().getmImageLoader());

        viewHolder.notiTitle.setText(notificationDataList.get(i).getTitle());
        viewHolder.AddTime.setText(notificationDataList.get(i).getAdd_time());
        viewHolder.notifiContent.setText(notificationDataList.get(i).getContent());



    }

    @Override
    public int getItemCount() {
        return notificationDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public NetworkImageView imageTitle;
        TextView notiTitle,AddTime,notifiContent;
        public ViewHolder(View itemView) {
            super(itemView);
            imageTitle= (NetworkImageView) itemView.findViewById(R.id.imageTitle);
            notiTitle= (TextView) itemView.findViewById(R.id.notiTitle);
            AddTime= (TextView) itemView.findViewById(R.id.AddTime);
            notifiContent= (TextView) itemView.findViewById(R.id.notifiContent);

        }
    }
}