package com.smarter.LoveLog.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.adapter.RecyclePinglunGoodsAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.goods.CmtGoods;
import com.smarter.LoveLog.model.goods.GoodsData;
import com.smarter.LoveLog.model.goods.GoodsDataInfo;
import com.smarter.LoveLog.model.goods.Pictures;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.ui.McoySnapPageLayout.McoyProductContentPage;
import com.smarter.LoveLog.ui.McoySnapPageLayout.McoyProductDetailInfoPage;
import com.smarter.LoveLog.ui.McoySnapPageLayout.McoySnapPageLayout;
import com.smarter.LoveLog.ui.SyLinearLayoutManager;
import com.smarter.LoveLog.ui.popwindow.BabyPopWindow;
import com.smarter.LoveLog.ui.productViewPager.CirclePageIndicator;
import com.smarter.LoveLog.utills.DeviceUtil;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
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


    @Bind(R.id.networkInfo)
    LinearLayout networkInfo;
    @Bind(R.id.errorInfo)
    ImageView errorInfo;
    @Bind(R.id.newLoading)
    LinearLayout newLoading;
    @Bind(R.id.progressLinear)
    LinearLayout progressLinear;
    @Bind(R.id.progreView)
    ImageView progreView;



    McoyProductDetailInfoPage  topPage;
    McoyProductContentPage bottomPage;

    View  topView,bottomView;
    Context mContext;
    /**
     * topView
     */
    ViewPager pager;
    CirclePageIndicator indicator;
    TextView price_shanchu;

    TextView  tv_top_title;//产品标题
    TextView isLike;//喜欢
    TextView isShoping;//购买
    TextView goodsName;//
    TextView goodsBrief;//
    TextView shopPrice;
    TextView goods_number;
    XRecyclerView mRecyclerView;
    private GalleryPagerAdapter galleryAdapter;
    private List<Pictures> imageList = new ArrayList<Pictures>();


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
        tv_top_title= (TextView) findViewById(R.id.tv_top_title);

        /**
         * topVIew mcoy_produt_detail_layout
         */


        mRecyclerView= (XRecyclerView) topView.findViewById(R.id.recyclerview);
       /* LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

*/
        SyLinearLayoutManager layoutManager = new SyLinearLayoutManager(mContext);
        layoutManager.setOrientation(SyLinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.SysProgress);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);




        pager= (ViewPager) topView.findViewById(R.id.pager);
        indicator= (CirclePageIndicator) topView.findViewById(R.id.indicator);
        price_shanchu= (TextView) topView.findViewById(R.id.price_shanchu);
        isLike=(TextView) topView.findViewById(R.id.isLike);
        isShoping=(TextView) topView.findViewById(R.id.isShoping);
        goodsName=(TextView) topView.findViewById(R.id.goodsName);
        goodsBrief=(TextView) topView.findViewById(R.id.goodsBrief);
        shopPrice=(TextView) topView.findViewById(R.id.shopPrice);

        goods_number=(TextView) topView.findViewById(R.id.goods_number);
        /**
         * bootomVIew mcoy_product_content_page
         */
    }

    public static void setListViewHeightBasedOnChildren(XRecyclerView recyclerView) {
        RecyclerView.Adapter listAdapter = recyclerView.getAdapter();
        int totalHeight=0;
        if(recyclerView.getAdapter().getItemCount()>0){
            for(int i=1; i<recyclerView.getAdapter().getItemCount(); i++) {
//                View listItem=recyclerView.findViewHolderForAdapterPosition(i).itemView;
                View listItem=recyclerView.getRootView();
                listItem.measure(0, 0);
                totalHeight += listItem.getMeasuredHeight();

            }

            ViewGroup.LayoutParams params = recyclerView.getLayoutParams();
            params.height = totalHeight ;
            //recyclerView1.getDividerHeight()获取子项间分隔符占用的高度
            //params.height最后得到整个recyclerView1完整显示需要的高度
            recyclerView.setLayoutParams(params);
        }



    }

    private void initData() {
        tv_top_title.setText(goodsData.getGoods_name());

/**
 * topView
 */
        imageList=goodsData.getPictures();
        galleryAdapter = new GalleryPagerAdapter();
        pager.setAdapter(galleryAdapter);
        indicator.setViewPager(pager);
        indicator.setPadding(5, 5, 10, 5);

        isLike.setText(goodsData.getClick_count() + "人喜欢");
        isShoping.setText(goodsData.getCollect_count() + "人购买");

        goodsName.setText(goodsData.getGoods_name());
        goodsBrief.setText(goodsData.getGoods_brief());

        shopPrice.setText(goodsData.getShop_price());
        price_shanchu.setText(goodsData.getMarket_price());

        goods_number.setText(goodsData.getGoods_number() + "");

        price_shanchu.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//价格删除线




        /**
         * 加载完网页再加载评论
         */
        List<CmtGoods> cmtGoodsesList=goodsData.getCmt();
        // 创建Adapter，并指定数据集
        RecyclePinglunGoodsAdapter adapter = new RecyclePinglunGoodsAdapter(cmtGoodsesList);
        // 设置Adapter
        mRecyclerView.setAdapter(adapter);
        setListViewHeightBasedOnChildren(mRecyclerView);
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
    String  param;
    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            param= intent.getStringExtra("param");
            //   Toast.makeText(this,str+"",Toast.LENGTH_LONG).show();
            if(param!=null&&!param.equals("")){
                netInitData();
            }
        }


    }
   private void netInitData(){
       if(DeviceUtil.checkConnection(mContext)){
           //加载动画
           progressLinear.setVisibility(View.VISIBLE);
           AnimationDrawable animationDrawable = (AnimationDrawable) progreView.getDrawable();
           animationDrawable.start();

           mcoySnapPageLayout.setVisibility(View.GONE);
           networkInfo.setVisibility(View.GONE);
           initNew();

       }else{
           errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nowifi));
           mcoySnapPageLayout.setVisibility(View.GONE);
           networkInfo.setVisibility(View.VISIBLE);
           newLoading.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   netInitData();
               }
           });
       }
   }


   GoodsData goodsData;
    private void initNew() {

        String url = "http://mapp.aiderizhi.com/?url=/goods/detail";
        RequestQueue mQueue = AppContextApplication.getInstance().getmRequestQueue();
        Map<String, String> mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"id\":\""+param+"\"}";
        mapTou.put("json", sessinStr);

        Log.d("ProductDeatil", "" + sessinStr + "++++sessionSTR》》》》");
        FastJsonRequest<GoodsDataInfo> fastJsonCommunity = new FastJsonRequest<GoodsDataInfo>(Request.Method.POST, url, GoodsDataInfo.class, null, new Response.Listener<GoodsDataInfo>() {
            @Override
            public void onResponse(GoodsDataInfo goodsDataInfo) {

                DataStatus status = goodsDataInfo.getStatus();
                if(status.getSucceed()==1){


                    progressLinear.setVisibility(View.GONE);//网络加载成功
                    mcoySnapPageLayout.setVisibility(View.VISIBLE);
                    goodsData = goodsDataInfo.getData();
                    initData();
                    setListen();

                    Log.d("ProductDeatil", "" + status.getSucceed() + "++++succeed》》》》" );
                }else{
                    // 请求失败 无数据
                    progressLinear.setVisibility(View.GONE);
                    mcoySnapPageLayout.setVisibility(View.GONE);
                    errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nodata));
                    networkInfo.setVisibility(View.VISIBLE);
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //未知错误
                progressLinear.setVisibility(View.GONE);
                mcoySnapPageLayout.setVisibility(View.GONE);
                networkInfo.setVisibility(View.VISIBLE);
                try {
                    errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_default));
                } catch (Resources.NotFoundException e) {
                    e.printStackTrace();
                }
                Log.d("ProductDeatil", "Error  ProductDeatil>>>" );
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //fastJsonCommunity.setTag(TAG);
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);


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
            Log.d("ProductDeatilActivity", mQueue.getCache().get(imageList.get(position).getCover()) == null ? "null" : "bu null");
            if(mQueue.getCache().get(imageList.get(position).getCover())==null){
                item.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
            }
            item.setImageUrl(imageList.get(position).getCover(), AppContextApplication.getInstance().getmImageLoader());


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
//        ShareSDK.initSDK(this);
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
