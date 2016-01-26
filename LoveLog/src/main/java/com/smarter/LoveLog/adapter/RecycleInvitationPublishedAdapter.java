package com.smarter.LoveLog.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.model.community.Pinglun;
import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.ui.CircleNetworkImage;

import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecycleInvitationPublishedAdapter extends RecyclerView.Adapter<RecycleInvitationPublishedAdapter.ViewHolder>{



ViewGroup viewGroup;

    // 数据集

    private List<PromotePostsData> promotePostsDataList;
    RequestQueue mQueue;
    public RecycleInvitationPublishedAdapter(List<PromotePostsData> promotePostsDataList) {
        super();
        mQueue =  AppContextApplication.getInstance().getmRequestQueue();
        this.promotePostsDataList=promotePostsDataList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_invitation_published_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        this.viewGroup=viewGroup;
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 绑定数据到ViewHolder上
        viewHolder.pinglunTitle.setText(promotePostsDataList.get(i).getTitle());
        viewHolder.pinglunConnet.setText(promotePostsDataList.get(i).getBrief());
        viewHolder.addTime.setText(promotePostsDataList.get(i).getAdd_time());
        viewHolder.pinglunNum.setText(promotePostsDataList.get(i).getCmt_count());

        //imgList  多个图片list
        viewHolder.imglist.removeAllViews();
        String imglistString="";
        PromotePostsData promotePostsDataItem=promotePostsDataList.get(i);
        if(promotePostsDataItem.getImg().getCover()!=null&&!promotePostsDataItem.getImg().getCover().equals("")){
            imglistString=promotePostsDataItem.getImg().getCover();
        }


        View view = View.inflate(viewGroup.getContext(), R.layout.item_image_invitation_published, null);
        NetworkImageView img = (NetworkImageView) view.findViewById(R.id.iv_item);

        img.setDefaultImageResId(R.mipmap.loadding);
        img.setErrorImageResId(R.mipmap.loadding);
        if(mQueue.getCache().get(imglistString)==null){
            img.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        img.setImageUrl(imglistString, AppContextApplication.getInstance().getmImageLoader());
        viewHolder.imglist.addView(view);

    }

    @Override
    public int getItemCount() {
        return promotePostsDataList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

      LinearLayout imglist;
        TextView pinglunTitle,pinglunConnet,addTime,pinglunNum;
        public ViewHolder(View itemView) {
            super(itemView);

            imglist= (LinearLayout) itemView.findViewById(R.id.imglist);
            pinglunTitle=(TextView) itemView.findViewById(R.id.pinglunTitle);
            pinglunConnet=(TextView) itemView.findViewById(R.id.pinglunConnet);
            addTime=(TextView) itemView.findViewById(R.id.addTime);
            pinglunNum=(TextView) itemView.findViewById(R.id.pinglunNum);
        }
    }
}