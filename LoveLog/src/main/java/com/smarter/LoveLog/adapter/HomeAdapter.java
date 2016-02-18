package com.smarter.LoveLog.adapter;

/**
 * Created by Administrator on 2015/12/7.
 */
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.model.home.Ad;
import com.smarter.LoveLog.model.home.AdIndexUrlData;

import java.util.List;

/**
 * Created by jianghejie on 15/11/26.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    private List<AdIndexUrlData> adIndexUrlDataList;
    Context mContext;
    ViewGroup viewGroup;
    public HomeAdapter(Context mContext,List<AdIndexUrlData> adIndexUrlDataList ) {
        this.adIndexUrlDataList=adIndexUrlDataList;
        this.mContext=mContext;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
//        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_xrecy_item,viewGroup);
        View view = View.inflate(viewGroup.getContext(), R.layout.home_xrecy_item, null);
        ViewHolder vh = new ViewHolder(view);
        this.viewGroup=viewGroup;
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,final int position) {

        //imgList
        viewHolder.imglist.removeAllViews();
        String imglistString="";
        imglistString=adIndexUrlDataList.get(position).getIndex_com().getImage_url();
        RequestQueue mQueue =  AppContextApplication.getInstance().getmRequestQueue();
        final Ad ad=adIndexUrlDataList.get(position).getIndex_com();
        View view;
        if(position==adIndexUrlDataList.size()-1){
            view = View.inflate(viewGroup.getContext(), R.layout.item_image_home_com_ad, null);
        }else{
            view = View.inflate(viewGroup.getContext(), R.layout.item_image_home_com_produt, null);
        }

        NetworkImageView img = (NetworkImageView) view.findViewById(R.id.iv_item);

        img.setDefaultImageResId(R.drawable.loading);
        img.setErrorImageResId(R.drawable.loading);
        if(mQueue.getCache().get(imglistString)==null){
            img.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        img.setImageUrl(imglistString, AppContextApplication.getInstance().getmImageLoader());
        viewHolder.imglist.addView(view);
        viewHolder.imglist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImagePagerAdapter().actionTo(mContext,ad.getAction(),ad.getParam());
            }
        });




   /*  final RequestQueue mQueue =  AppContextApplication.getInstance().getmRequestQueue();
//        viewHolder.imglist.setImageResource(R.mipmap.loadding);
        String imageUrl=adIndexUrlDataList.get(position).getIndex_com().getImage_url();
        ImageRequest imageRequest = new ImageRequest(imageUrl,
                new Response.Listener<Bitmap>() {
                    @Override
                    public void onResponse(Bitmap bitmap) {
                        // TODO Auto-generated method stub


                        viewHolder.imglist.setScaleType(ImageView.ScaleType.FIT_XY);
                        viewHolder.imglist.setImageBitmap(bitmap);

                    }
                }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError arg0) {
                // TODO Auto-generated method stub
                viewHolder.imglist.setImageResource(R.mipmap.loadding);
            }
        });



        if(mQueue.getCache().get(imageUrl)==null){
            viewHolder.imglist.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }

        mQueue.add(imageRequest);
*/



    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return adIndexUrlDataList.size();
    }
    //自定义的ViewHolder，持有每个Item的的所有界面元素
    public static class ViewHolder extends RecyclerView.ViewHolder {
        public LinearLayout views,imglist;
        public ViewHolder(View view){
            super(view);
            imglist = (LinearLayout) view.findViewById(R.id.imglist);
        }
    }
}

