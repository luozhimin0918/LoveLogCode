package com.smarter.LoveLog.adapter;

/**
 * Created by Administrator on 2015/12/1.
 */
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.model.home.NavIndexUrlData;
import com.smarter.LoveLog.ui.CircleNetworkImage;

import java.util.Collections;
import java.util.List;

public class Adapter_GridView extends BaseAdapter {
    private Context context;
    private int[] data;
    private String[] titleData;
    List<NavIndexUrlData> navIndexUrlDataList;
    public Adapter_GridView(Context context,List<NavIndexUrlData> navUrlList){

        this.context=context;
        this.data=data;
        this.titleData=titleData;
        this.navIndexUrlDataList=navUrlList;
        Collections.reverse(navIndexUrlDataList);
    }

    @Override
    public int getCount() {
        return navIndexUrlDataList.size();
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(final int position, View currentView, ViewGroup arg2) {
      HolderView holderView;
        if (currentView==null) {
            holderView=new HolderView();
            currentView=LayoutInflater.from(context).inflate(R.layout.adapter_grid_home, null);
            holderView.iv_pic=(CircleNetworkImage) currentView.findViewById(R.id.iv_adapter_grid_pic);
            holderView.titleData= (TextView) currentView.findViewById(R.id.titleData);
            currentView.setTag(holderView);
        }else {
            holderView=(HolderView) currentView.getTag();
        }



//        holderView.iv_pic.setScaleType(ImageView.ScaleType.FIT_XY);




        holderView.iv_pic.setDefaultImageResId(R.mipmap.loading_normal);
        holderView.iv_pic.setErrorImageResId(R.mipmap.loading_normal);


        RequestQueue mQueue =  AppContextApplication.getInstance().getmRequestQueue();
        String imageUrl=navIndexUrlDataList.get(position).getIcon();
        Log.d("ImagePagerAdapter", mQueue.getCache().get(imageUrl) == null ? "null" : "bu null");
        if(mQueue.getCache().get(imageUrl)==null){
            holderView.iv_pic.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        holderView.iv_pic.setImageUrl(imageUrl, AppContextApplication.getInstance().getmImageLoader());
        holderView.titleData.setText(navIndexUrlDataList.get(position).getName());


        return currentView;
    }


    private  static class HolderView {

        CircleNetworkImage iv_pic;
        TextView titleData;

    }

}

