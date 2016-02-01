package com.smarter.LoveLog.adapter;

/**
 * Created by Administrator on 2015/12/3.
 */
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.activity.HelpDataActivity;
import com.smarter.LoveLog.activity.HelpDataThreeWebActivity;
import com.smarter.LoveLog.activity.HelpDataTwoActivity;
import com.smarter.LoveLog.activity.InvitationActivity;
import com.smarter.LoveLog.activity.InvitationDeatilActivity;
import com.smarter.LoveLog.activity.MainActivity;
import com.smarter.LoveLog.activity.ProductDeatilActivity;
import com.smarter.LoveLog.activity.WebViewUrlActivity;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.model.help.HelpData;
import com.smarter.LoveLog.model.help.HelpDataList;
import com.smarter.LoveLog.model.home.NavIndexUrlData;
import com.smarter.LoveLog.model.home.SliderUrlData;


/**
 * ImagePagerAdapter
 *
 * @author <a href="http://www.trinea.cn" target="_blank">Trinea</a> 2014-2-23
 */
public class ImagePagerAdapter extends RecyclingPagerAdapter {

    //    private List<String> imageIdList;
    private int           size;
    private Context       context;
    private boolean       isInfiniteLoop;
    private List<SliderUrlData> sliderUrlDataList;

    /* public ImagePagerAdapter(Context context, List<String> imageIdList) {
        this.context = context;
        this.imageIdList = imageIdList;
        this.size =imageIdList.size();
        isInfiniteLoop = false;
    }*/

    public ImagePagerAdapter(){

    }
    public ImagePagerAdapter(Context context,List<SliderUrlData> sliderUrlDataList) {

        this.context = context;
        this.sliderUrlDataList = sliderUrlDataList;
        this.size =sliderUrlDataList.size();
        isInfiniteLoop = false;
    }


    @Override
    public int getCount() {
        // Infinite loop
        return isInfiniteLoop ? Integer.MAX_VALUE :size;
    }

    /**
     * get really position
     *
     * @param position
     * @return
     */
    private int getPosition(int position) {
        return isInfiniteLoop ? position % size : position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup container) {
       final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = holder.imageView = new NetworkImageView(context);
            view.setTag(holder);
        } else {
            holder = (ViewHolder)view.getTag();
        }

        ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
        holder.imageView.setLayoutParams(params);
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_XY);




        holder.imageView.setDefaultImageResId(R.mipmap.loadding);
        holder.imageView.setErrorImageResId(R.mipmap.loadding);


        RequestQueue mQueue =  AppContextApplication.getInstance().getmRequestQueue();
        String imageUrl=sliderUrlDataList.get(getPosition(position)).getImage_url();
        Log.d("ImagePagerAdapter", mQueue.getCache().get(imageUrl) == null ? "null" : "bu null");
        if(mQueue.getCache().get(imageUrl)==null){
            holder.imageView.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        holder.imageView.setImageUrl(imageUrl, AppContextApplication.getInstance().getmImageLoader());



        holder.imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               SliderUrlData slider=sliderUrlDataList.get(getPosition(position));
                actionTo(context,slider.getAction(), slider.getParam());

            }
        });
        return view;
    }

    public  void actionTo(Context context,String action,String param) {


           if(action.equals("url")){
            Intent intent = new Intent(context, WebViewUrlActivity.class);
            intent.putExtra("param", param);
            context.startActivity(intent);
           }

           if(action.equals("post_detail")){
               Intent intent = new Intent(context, InvitationDeatilActivity.class);
               PromotePostsData postsData=new PromotePostsData();
               postsData.setId(param);
               intent.putExtra("PromotePostsData",postsData);
               context.startActivity(intent);

           }
        if(action.equals("post_category")){
            Intent intent = new Intent(context, InvitationActivity.class);
            NavIndexUrlData navIndexUrlData=new NavIndexUrlData();
            navIndexUrlData.setId(param);
            navIndexUrlData.setName("帖子列表");
            intent.putExtra("NavIndexUrlData",navIndexUrlData);
            context.startActivity(intent);

        }
        if(action.equals("product_detail")||action.equals("goods_detail")){
            Intent intent = new Intent(context, ProductDeatilActivity.class);
           /* PromotePostsData postsData=new PromotePostsData();
            postsData.setId(param);
            intent.putExtra("PromotePostsData",postsData);*/
            context.startActivity(intent);

        }

        if(action.equals("help_index")){
            Intent intent = new Intent(context, HelpDataActivity.class);

            context.startActivity(intent);

        }

        if(action.equals("help_category")){
            Intent intent = new Intent(context, HelpDataTwoActivity.class);
            HelpDataList helpDataList=new HelpDataList();
            helpDataList.setId(param);
            helpDataList.setName("帮助分类");
            intent.putExtra("twoHelpdata",helpDataList);
            context.startActivity(intent);

        }

        if(action.equals("help_detail")){
            Intent intent = new Intent(context, HelpDataThreeWebActivity.class);
            HelpDataList helpDataList=new HelpDataList();
            helpDataList.setId(param);
            intent.putExtra("threeWebHelpdata",helpDataList);
            context.startActivity(intent);

        }

        if(action.equals("post_index")){
            MainActivity.mainActivity.onDoMainListener();
        }


    }






    private static class ViewHolder {

        NetworkImageView imageView;
    }

    /**
     * @return the isInfiniteLoop
     */
    public boolean isInfiniteLoop() {
        return isInfiniteLoop;
    }

    /**
     * @param isInfiniteLoop the isInfiniteLoop to set
     */
    public ImagePagerAdapter setInfiniteLoop(boolean isInfiniteLoop) {
        this.isInfiniteLoop = isInfiniteLoop;
        return this;
    }

    public static AlphaAnimation getInAlphaAnimation(long durationMillis) {
        AlphaAnimation inAlphaAnimation = new AlphaAnimation(0, 1);
        inAlphaAnimation.setDuration(durationMillis);
        return inAlphaAnimation;
    }
}

