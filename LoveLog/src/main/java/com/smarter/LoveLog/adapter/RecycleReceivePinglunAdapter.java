package com.smarter.LoveLog.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.SpannableString;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.goods.CmtGoods;
import com.smarter.LoveLog.ui.CircleNetworkImage;
import com.smarter.LoveLog.utills.FaceConversionUtil;

import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecycleReceivePinglunAdapter extends RecyclerView.Adapter<RecycleReceivePinglunAdapter.ViewHolder>{





    // 数据集

    private List<CmtGoods> pinglunList;
    RequestQueue mQueue;
    Context mContext;
    public RecycleReceivePinglunAdapter(List<CmtGoods> pinglunList,Context mContext) {
        super();
        mQueue =  AppContextApplication.getInstance().getmRequestQueue();
        this.pinglunList=pinglunList;
        this.mContext=mContext;
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
        viewHolder.imageTitle.setDefaultImageResId(R.drawable.loading);
        viewHolder.imageTitle.setErrorImageResId(R.drawable.loading);
        if(mQueue.getCache().get(UserimageUrl)==null){
            viewHolder.imageTitle.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        viewHolder.imageTitle.setImageUrl(UserimageUrl, AppContextApplication.getInstance().getmImageLoader());

        viewHolder.pinglunTitle.setText(pinglunList.get(i).getObject().getTitle());

        viewHolder.userName.setText(user.getName());
        viewHolder.AddTime.setText(pinglunList.get(i).getAdd_time());

        SpannableString spannableString = FaceConversionUtil.getInstace().getExpressionString(mContext, pinglunList.get(i).getContent());
        viewHolder.pingContent.setText(spannableString);
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