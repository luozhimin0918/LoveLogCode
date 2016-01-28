package com.smarter.LoveLog.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.NetworkImageView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.ui.McoySnapPageLayout.McoyProductContentPage;
import com.smarter.LoveLog.ui.McoySnapPageLayout.McoyProductDetailInfoPage;
import com.smarter.LoveLog.ui.McoySnapPageLayout.McoySnapPageLayout;
import com.smarter.LoveLog.ui.popwindow.BabyPopWindow;
import com.smarter.LoveLog.ui.productViewPager.AutoLoopViewPager;
import com.smarter.LoveLog.ui.productViewPager.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2015/11/30.
 */
public class ProductDeatilActivity extends BaseFragmentActivity implements View.OnClickListener,BabyPopWindow.OnItemClickListener {


    @Bind(R.id.flipLayout)
    McoySnapPageLayout mcoySnapPageLayout;
    @Bind(R.id.pro_share)
    ImageView pro_share;
    @Bind(R.id.buy_now)
    TextView buy_now;
    @Bind(R.id.back_but)
    ImageView back_but;



    McoyProductDetailInfoPage  topPage;
    McoyProductContentPage bottomPage;

    View  topView,bottomView;
    Context mContext;
    /**
     * topView
     */
    AutoLoopViewPager pager;
    CirclePageIndicator indicator;
    TextView price_shanchu;

    private int[] imageViewIds;
    private GalleryPagerAdapter galleryAdapter;
    private List<String> imageList = new ArrayList<String>(Arrays.asList(
                                    "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg",
                                    "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg",
                                    "http://pic.nipic.com/2008-07-11/20087119630716_2.jpg"));


    /**
     * bottomView
     */


    /**弹出商品订单信息详情*/
    private BabyPopWindow popWindow;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_deatil);
        ButterKnife.bind(this);
        mContext=this;
        initView();



        FindView();
        getDataIntent();

        initData();
        setListen();

    }

    private void setListen() {
        pro_share.setOnClickListener(this);
        buy_now.setOnClickListener(this);
        back_but.setOnClickListener(this);
    }


    private void initView() {
        topPage = new McoyProductDetailInfoPage(ProductDeatilActivity.this, getLayoutInflater().inflate(R.layout.mcoy_produt_detail_layout, null));
        topView=topPage.getRootView();
//        ButterKnife.bind(this,topPage.getRootView());
        bottomPage = new McoyProductContentPage(ProductDeatilActivity.this, getLayoutInflater().inflate(R.layout.mcoy_product_content_page, null));
        bottomView=bottomPage.getRootView();
//        ButterKnife.bind(this,bottomPage.getRootView());

        mcoySnapPageLayout.setSnapPages(topPage, bottomPage);

    }
    private void FindView() {
        /**
         * topVIew mcoy_produt_detail_layout
         */
        pager= (AutoLoopViewPager) topView.findViewById(R.id.pager);
        indicator= (CirclePageIndicator) topView.findViewById(R.id.indicator);
        price_shanchu= (TextView) topView.findViewById(R.id.price_shanchu);
        /**
         * bootomVIew mcoy_product_content_page
         */
    }

    private void initData() {

/**
 * topView
 */
        imageViewIds = new int[] { R.mipmap.house_background, R.mipmap.house_background_1, R.mipmap.house_background_2};
        galleryAdapter = new GalleryPagerAdapter();
        pager.setAdapter(galleryAdapter);
//        pager.setInterval(2000);
        indicator.setViewPager(pager);
        indicator.setPadding(5, 5, 10, 5);
        price_shanchu.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//价格删除线
/**
 * bottomView
 */

        popWindow = new BabyPopWindow(this);
        popWindow.setOnItemClickListener(this);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.buy_now:
                BabyPopWindow.backgroundAlpha(mContext,0.2f);
                popWindow.showAsDropDown(v);
                break;
            case R.id.pro_share:
                showShare();
                break;
            case  R.id.back_but:
                finish();
                break;

        }
    }

    @Override
    public void onClickOKPop() {

    }
    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            String  str = intent.getStringExtra("ObjectData");
            //   Toast.makeText(this,str+"",Toast.LENGTH_LONG).show();
        }


    }


    //轮播图适配器
    public class GalleryPagerAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return imageList.size();
        }//imageViewIds

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
           final NetworkImageView item = new NetworkImageView(ProductDeatilActivity.this);


            ViewGroup.LayoutParams params = new ViewGroup.LayoutParams(-1, -1);
            item.setLayoutParams(params);
            item.setScaleType(ImageView.ScaleType.FIT_XY);




            item.setDefaultImageResId(R.mipmap.loadding);
            item.setErrorImageResId(R.mipmap.loadding);


            RequestQueue mQueue =  AppContextApplication.getInstance().getmRequestQueue();
            Log.d("ProductDeatilActivity", mQueue.getCache().get(imageList.get(position)) == null ? "null" : "bu null");
            if(mQueue.getCache().get(imageList.get(position))==null){
                item.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
            }
            item.setImageUrl(imageList.get(position), AppContextApplication.getInstance().getmImageLoader());


            /*ImageRequest imageRequest = new ImageRequest(imageList.get(position),
                    new Response.Listener<Bitmap>() {
                        @Override
                        public void onResponse(Bitmap bitmap) {
                            // TODO Auto-generated method stub
                            item.setImageBitmap(bitmap);

                        }
                    }, 0, 0, Bitmap.Config.ARGB_8888, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError arg0) {
                    // TODO Auto-generated method stub
                    item.setImageResource(R.mipmap.loadding);
                }
            });
            RequestQueue mQueue =  AppContextApplication.getInstance().getmRequestQueue();
            mQueue.add(imageRequest);*/












            container.addView(item);

            final int pos = position;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ProductDeatilActivity.this, ProductDeatilActivity.class);
                    intent.putExtra("position", pos);
                    startActivity(intent);
                }
            });

            return item;
        }

        @Override
        public void destroyItem(ViewGroup collection, int position, Object view) {
            collection.removeView((View) view);
        }
    }




    /**
     * 分享内容方法
     */
    private void showShare() {
        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字    2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(getString(R.string.share));
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl("http://sharesdk.cn");
        // text是分享文本，所有平台都需要这个字段
        oks.setText("我是分享文本，啦啦啦~");
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl("http://sharesdk.cn");
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl("http://sharesdk.cn");
// 启动分享GUI
        oks.show(this);
    }
}
