package com.smarter.LoveLog.adapter;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.goods.CmtGoods;
import com.smarter.LoveLog.ui.CircleNetworkImage;

import java.util.List;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecyclePinglunGoodsAdapter extends RecyclerView.Adapter<RecyclePinglunGoodsAdapter.ViewHolder>{





    // 数据集

    private List<CmtGoods> goodsList;
    RequestQueue mQueue;
    Context mContext;
    public RecyclePinglunGoodsAdapter(List<CmtGoods> goodsList,Context Context) {
        super();
        mQueue =  AppContextApplication.getInstance().getmRequestQueue();
        this.mContext=Context;
        this.goodsList=goodsList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_goods_pinglun_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {
        // 绑定数据到ViewHolder上
        User user=goodsList.get(i).getUser();
        String UserimageUrl=user.getAvatar();
        viewHolder.imageTitle.setDefaultImageResId(R.drawable.loading);
        viewHolder.imageTitle.setErrorImageResId(R.drawable.loading);
        if(mQueue.getCache().get(UserimageUrl)==null){
            viewHolder.imageTitle.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        viewHolder.imageTitle.setImageUrl(UserimageUrl, AppContextApplication.getInstance().getmImageLoader());



        viewHolder.userName.setText(user.getName());
        viewHolder.AddTime.setText(goodsList.get(i).getAdd_time());
        viewHolder.pingContent.setText(goodsList.get(i).getContent());
        int cmtRank;
        viewHolder.ratingBar.removeAllViews();
        try {
            cmtRank =  Integer.parseInt(goodsList.get(i).getCmt_rank());
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);

                params.setMargins(5, 0, 0, 0);

            if(cmtRank>0){
                for (int j=0;j<cmtRank;j++){
                    ImageView imageView=new ImageView(mContext);
                    imageView.setImageResource(R.mipmap.favorite_pressed);
                    imageView.setLayoutParams(params);
                    viewHolder.ratingBar.addView(imageView);
                }

            }

        } catch (NumberFormatException e) {
            e.printStackTrace();
        }

    }

    @Override
    public int getItemCount() {
        return goodsList.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public CircleNetworkImage imageTitle;
        public TextView userName,AddTime,pingContent;
        LinearLayout ratingBar;
        public ViewHolder(View itemView) {

            super(itemView);
            imageTitle= (CircleNetworkImage) itemView.findViewById(R.id.imageTitle);
            userName= (TextView) itemView.findViewById(R.id.userName);
            AddTime= (TextView) itemView.findViewById(R.id.AddTime);
            pingContent= (TextView) itemView.findViewById(R.id.pingContent);
            ratingBar= (LinearLayout) itemView.findViewById(R.id.ratingBar);
        }
    }
}