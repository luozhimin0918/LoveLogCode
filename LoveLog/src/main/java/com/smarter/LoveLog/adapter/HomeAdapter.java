package com.smarter.LoveLog.adapter;

/**
 * Created by Administrator on 2015/12/7.
 */
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

import java.util.List;

/**
 * Created by jianghejie on 15/11/26.
 */
public class HomeAdapter extends RecyclerView.Adapter<HomeAdapter.ViewHolder> {
    public int[] datas = null;
    private List<String> imageIdList;
    public HomeAdapter(int[] datas,List<String> imageIdList ) {
        this.datas = datas;
        this.imageIdList=imageIdList;
    }
    //创建新View，被LayoutManager所调用
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.home_xrecy_item,viewGroup,false);
        ViewHolder vh = new ViewHolder(view);
        return vh;
    }
    //将数据与界面进行绑定的操作
    @Override
    public void onBindViewHolder(final ViewHolder viewHolder,final int position) {





      final RequestQueue mQueue =  AppContextApplication.getInstance().getmRequestQueue();
        viewHolder.imglist.setImageResource(R.mipmap.loadding);
        ImageRequest imageRequest = new ImageRequest(imageIdList.get(position),
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



        if(mQueue.getCache().get(imageIdList.get(position))==null){
            viewHolder.imglist.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }

        mQueue.add(imageRequest);


    }
    //获取数据的数量
    @Override
    public int getItemCount() {
        return imageIdList.size();
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

