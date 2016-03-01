package com.smarter.LoveLog.activity;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Paint;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.NetworkImageView;
import com.flyco.tablayout.SlidingTabLayout;
import com.flyco.tablayout.listener.OnTabSelectListener;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.adapter.RecyclePinglunGoodsAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.fragment.GoodsImgTextFragment;
import com.smarter.LoveLog.fragment.GoodsPinglunFragment;
import com.smarter.LoveLog.fragment.RedpacketHaveExpiredFragment;
import com.smarter.LoveLog.fragment.RedpacketUnusedFragment;
import com.smarter.LoveLog.fragment.RedpacketUsedFragment;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.community.CollectData;
import com.smarter.LoveLog.model.community.CollectDataInfo;
import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.model.goods.CmtGoods;
import com.smarter.LoveLog.model.goods.GoodsData;
import com.smarter.LoveLog.model.goods.GoodsDataInfo;
import com.smarter.LoveLog.model.goods.Pictures;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.jsonModel.ZanOrFaroviteParame;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.ui.CustomViewPager;
import com.smarter.LoveLog.ui.McoySnapPageLayout.McoyProductContentPage;
import com.smarter.LoveLog.ui.McoySnapPageLayout.McoyProductDetailInfoPage;
import com.smarter.LoveLog.ui.McoySnapPageLayout.McoyScrollView;
import com.smarter.LoveLog.ui.McoySnapPageLayout.McoySnapPageLayout;
import com.smarter.LoveLog.ui.QCheckBox;
import com.smarter.LoveLog.ui.SyLinearLayoutManager;
import com.smarter.LoveLog.ui.popwindow.AlertDialog;
import com.smarter.LoveLog.ui.popwindow.BabyPopWindow;
import com.smarter.LoveLog.ui.productViewPager.CirclePageIndicator;
import com.smarter.LoveLog.utills.DeviceUtil;
import com.smarter.LoveLog.utills.ViewUtill;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.onekeyshare.OnekeyShare;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by Administrator on 2015/11/30.
 */
public class ProductDeatilActivity extends BaseFragmentActivity implements View.OnClickListener,BabyPopWindow.OnItemClickListener,OnTabSelectListener {


    @Bind(R.id.flipLayout)
    McoySnapPageLayout mcoySnapPageLayout;
    @Bind(R.id.pro_share)
    ImageView pro_share;
    @Bind(R.id.buy_now)
    TextView buy_now;
    @Bind(R.id.back_but)
    ImageView back_but;

    @Bind(R.id.xuanfuBar)
    LinearLayout xuanfuBar;




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

    @Bind(R.id.kefuBut)
    QCheckBox kefuBut;
    @Bind(R.id.collectBut)
    QCheckBox collectBut;

    @Bind(R.id.pro_car)
    ImageView pro_car;

    /**
     * topView
     */
    ViewPager pager;
    CirclePageIndicator indicator;
    TextView price_shanchu;

    TextView  tv_top_title;//产品标题

    RelativeLayout toDetailPicRelative,youhuiTimeRelative;
    TextView isLike;//喜欢
    TextView isShoping;//购买
    TextView goodsName;//
    TextView goodsBrief;//
    TextView shopPrice;
    TextView goods_number;
    RecyclerView mRecyclerView;
    private GalleryPagerAdapter galleryAdapter;
    private List<Pictures> imageList = new ArrayList<Pictures>();
    RelativeLayout  pingLinear,yiSelected;

    /**
     * bottomView
     */

    SlidingTabLayout tabLayout_2;
    CustomViewPager vp;
    private List<Fragment> list_fragment;                                //定义要装fragment的列表
    private List<String> list_title;                                     //tab名称列表

    private GoodsImgTextFragment goodsImgTextFragment;                          //未使用fragment
    private GoodsImgTextFragment goodsImgTextFragmentAttr;
    private GoodsPinglunFragment goodsImgTextFragmentPinglun;

    McoyScrollView productDetail_scrollview;

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
        pro_car.setOnClickListener(this);
        pro_share.setOnClickListener(this);
        buy_now.setOnClickListener(this);
        back_but.setOnClickListener(this);
        pingLinear.setOnClickListener(this);
        yiSelected.setOnClickListener(this);
        kefuBut.setOnClickListener(this);
        collectBut.setOnClickListener(this);
        toDetailPicRelative.setOnClickListener(this);
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


        mRecyclerView= (RecyclerView) topView.findViewById(R.id.recyclerview);


        SyLinearLayoutManager layoutManager = new SyLinearLayoutManager(mContext);
        layoutManager.setOrientation(SyLinearLayoutManager.VERTICAL);

        mRecyclerView.setLayoutManager(layoutManager);





        pager= (ViewPager) topView.findViewById(R.id.pager);
        indicator= (CirclePageIndicator) topView.findViewById(R.id.indicator);
        yiSelected=(RelativeLayout) topView.findViewById(R.id.yiSelected);

        price_shanchu= (TextView) topView.findViewById(R.id.price_shanchu);
        isLike=(TextView) topView.findViewById(R.id.isLike);
        isShoping=(TextView) topView.findViewById(R.id.isShoping);
        goodsName=(TextView) topView.findViewById(R.id.goodsName);
        goodsBrief=(TextView) topView.findViewById(R.id.goodsBrief);
        shopPrice=(TextView) topView.findViewById(R.id.shopPrice);

        goods_number=(TextView) topView.findViewById(R.id.goods_number);

        pingLinear= (RelativeLayout) topView.findViewById(R.id.pingLinear);
        toDetailPicRelative= (RelativeLayout) topView.findViewById(R.id.toDetailPicRelative);
        youhuiTimeRelative= (RelativeLayout) topView.findViewById(R.id.youhuiTimeRelative);
        /**
         * bootomVIew mcoy_product_content_page
         */
        tabLayout_2= (SlidingTabLayout) bottomView.findViewById(R.id.tl_2);
        vp= (CustomViewPager) bottomView.findViewById(R.id.view_pager);
        productDetail_scrollview= (McoyScrollView) bottomView.findViewById(R.id.productDetail_scrollview);


    }



    private void initData() {
        tv_top_title.setText(goodsData.getGoods_name());
        xuanfuBar.setVisibility(View.VISIBLE);

        if(goodsData.getIs_collect().equals("1")){
            collectBut.setChecked(true);
        }else {
            collectBut.setChecked(false);
        }


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

        goodsName.setText(goodsData.getGoods_name());//商品名称


        if(goodsData.getGoods_brief().equals("")){//商品描述
            goodsBrief.setVisibility(View.GONE);
        }else{
            goodsBrief.setText(goodsData.getGoods_brief());
        }


        shopPrice.setText(goodsData.getShop_price());//商品价格
        price_shanchu.setText(goodsData.getMarket_price());
        price_shanchu.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);//价格删除线


        if(goodsData.getPromote().getStart_date().equals("")||goodsData.getPromote().getEnd_date().equals("")){//优惠倒计时
            youhuiTimeRelative.setVisibility(View.GONE);
        }


        goods_number.setText(goodsData.getGoods_number() + "");//商品库存






        /**
         * 加载完网页再加载评论
         */
        List<CmtGoods> cmtGoodsesList=goodsData.getCmt();
        // 创建Adapter，并指定数据集
        Collections.reverse(cmtGoodsesList);
        RecyclePinglunGoodsAdapter adapter = new RecyclePinglunGoodsAdapter(cmtGoodsesList,mContext);
        // 设置Adapter
        mRecyclerView.setAdapter(adapter);
/**
 * bottomView
 */

        popWindow = new BabyPopWindow(this,goodsData);
        popWindow.setOnItemClickListener(this);



        String  desc="http://mapp.aiderizhi.com/?url=/goods/desc&id="+goodsData.getId();
        String  attr="http://mapp.aiderizhi.com/?url=/goods/attr&id="+goodsData.getId();
//        String  attr="http://mapp.aiderizhi.com/?url=/post/content&id=22737";

        goodsImgTextFragment=new GoodsImgTextFragment(mcoySnapPageLayout,productDetail_scrollview,desc);
        goodsImgTextFragmentAttr=new GoodsImgTextFragment(mcoySnapPageLayout,productDetail_scrollview,attr);
        goodsImgTextFragmentPinglun=new GoodsPinglunFragment();

        list_fragment=new ArrayList<Fragment>();
        list_fragment.add(goodsImgTextFragment);

        list_fragment.add(goodsImgTextFragmentAttr);
        list_fragment.add(goodsImgTextFragmentPinglun);

        //tab title List
        list_title=new ArrayList<String>();
        list_title.add("图文详情");
        list_title.add("规格参数");
        list_title.add("买家点评");


        vp.setAdapter(new MyPagerAdapter(getSupportFragmentManager()));

        tabLayout_2.setViewPager(vp);
        tabLayout_2.setOnTabSelectListener(this);
//        tabLayout_2.showDot(0);
        vp.setCurrentItem(0);
//        tabLayout_2.showMsg(1, 5);
//        tabLayout_2.setMsgMargin(1, 12.0f, 10.0f);

    }


    int viewPagerNum=0;
    @Override
    public void onTabSelect(int position) {


        if(position!=2){
            viewPagerNum=position;
        }

        if(position==2){
            //挑战到所有评论界面//
            //
            Intent intent2 = new Intent(this, GoodsAllPinglunActivity.class);
            Bundle bundle = new Bundle();
            PromotePostsData promotePostsData=new PromotePostsData();
            promotePostsData.setId(goodsData.getId());
            bundle.putSerializable("allpinglun",promotePostsData);
            intent2.putExtras(bundle);
            this.startActivity(intent2);
        }
    }

    @Override
    public void onTabReselect(int position) {


    }

    private class MyPagerAdapter extends FragmentPagerAdapter {
        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public int getCount() {
            return list_fragment.size();
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return list_title.get(position);
        }

        @Override
        public Fragment getItem(int position) {
            return list_fragment.get(position);
        }
    }


    @Override
    protected void onResume() {
        super.onResume();
        vp.setCurrentItem(viewPagerNum,false);
    }

    @Override
    public void onClick(View v) {
        String url = "";//
        switch (v.getId()){
            case   R.id.buy_now:
            case   R.id.yiSelected:
                BabyPopWindow.backgroundAlpha(mContext,0.2f);
                popWindow.showAsDropDown(v);
                break;
            case R.id.toDetailPicRelative:
                 mcoySnapPageLayout.snapToNext();
                break;
            case R.id.pro_share:
                showShare();
                break;
            case  R.id.back_but:
                finish();
                break;
            case R.id.pingLinear:

                //挑战到所有评论界面//
                //
                Intent intent2 = new Intent(this, GoodsAllPinglunActivity.class);
                Bundle bundle = new Bundle();
                PromotePostsData promotePostsData=new PromotePostsData();
                promotePostsData.setId(goodsData.getId());
                bundle.putSerializable("allpinglun",promotePostsData);
                intent2.putExtras(bundle);
                this.startActivity(intent2);
                break;
            case R.id.kefuBut:
                Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
                if(isLogin) {
                    /**
                     * 启动客服聊天界面。
                     *
                     * @param context          应用上下文。
                     * @param conversationType 开启会话类型。
                     * @param targetId         客服 Id。
                     * @param title            客服标题。
                     */
                    RongIM.getInstance().startConversation(mContext, Conversation.ConversationType.APP_PUBLIC_SERVICE, "KEFU145033288579386", "客服");


                }else{
                    ViewUtill.ShowAlertDialog(mContext);
                }


                break;
            case  R.id.collectBut:
                url = "http://mapp.aiderizhi.com/?url=/collect";//收藏
                initIsLogonParame(url);
                break;
            case  R.id.pro_car:
                //登录
                Intent intent = new Intent(mContext, MainActivity.class);
                Bundle bundle2 = new Bundle();
                bundle2.putSerializable("main","shopCar");
                intent.putExtras(bundle2);
                mContext.startActivity(intent);
                break;

        }
    }

    private void initIsLogonParame(String url) {
        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
        if(isLogin){
            String  sessionString=SharedPreferences.getInstance().getString("session", "");
            sessionData = JSON.parseObject(sessionString,SessionData.class);
            if(sessionData!=null){

                ZanOrFaroviteParame zanOrFaroviteInfo=new ZanOrFaroviteParame();
                zanOrFaroviteInfo.setSession(sessionData);
                zanOrFaroviteInfo.setId(param);
                zanOrFaroviteInfo.setType("0");




                if(url.endsWith("collect")){
                    networkTieCollect(JSON.toJSONString(zanOrFaroviteInfo),url);
                }



            }

        }else{
            ViewUtill.ShowAlertDialog(mContext);
//            Toast.makeText(getApplicationContext(), "未登录，请先登录", Toast.LENGTH_SHORT).show();
        }
    }



    /**
     * 帖子收藏
     */
    CollectData collectData;
    private void networkTieCollect(String paramNet,String url) {

        Map<String, String> mapTou = new HashMap<String, String>();
        mapTou.put("json", paramNet);




        Log.d("ProductDeatilActivity", paramNet + "      ");


        FastJsonRequest<CollectDataInfo> fastJsonCommunity = new FastJsonRequest<CollectDataInfo>(Request.Method.POST, url, CollectDataInfo.class, null, new Response.Listener<CollectDataInfo>() {
            @Override
            public void onResponse(CollectDataInfo collectDataInfo) {

                DataStatus status = collectDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    collectData = collectDataInfo.getData();
                    if(collectData!=null){
                        if(collectData.getIs_collect().equals("1")){
                            collectBut.setChecked(true);
                        }else {
                            collectBut.setChecked(false);
                        }
                        Toast.makeText(getApplicationContext(), "" + collectData.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("ProductDeatilActivity", "ProductDeatilActivity 成功返回信息：   " + JSON.toJSONString(collectData)+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("ProductDeatilActivity", "succeded=0  ProductDeatilActivity 返回信息 " + JSON.toJSONString(status) + "");
                    if(status.getError_code()==1000){
                        SharedPreferences.getInstance().putBoolean("islogin",false);
                        ViewUtill.ShowAlertDialog(mContext);
                    }

                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("ProductDeatilActivity", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
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
    SessionData sessionData;
    private void initNew() {

        String url = "http://mapp.aiderizhi.com/?url=/goods/detail";
        RequestQueue mQueue = AppContextApplication.getInstance().getmRequestQueue();
        Map<String, String> mapTou = new HashMap<String, String>();



        String  sessinStr="";

        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
        if(isLogin){
            String  sessionString=SharedPreferences.getInstance().getString("session", "");
            sessionData = JSON.parseObject(sessionString, SessionData.class);
            if(sessionData!=null){

                ZanOrFaroviteParame zanOrFaroviteInfo=new ZanOrFaroviteParame();
                zanOrFaroviteInfo.setSession(sessionData);
                zanOrFaroviteInfo.setId(param);
                sessinStr=JSON.toJSONString(zanOrFaroviteInfo);
                mapTou.put("json", sessinStr);

            }

        }else{
               sessinStr ="{\"id\":\""+param+"\"}";
              mapTou.put("json", sessinStr);
        }





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




            item.setDefaultImageResId(R.drawable.loading);
            item.setErrorImageResId(R.drawable.loading);


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
                    item.setImageResource(R.mipmap.loading);
                }
            });
            RequestQueue mQueue =  AppContextApplication.getInstance().getmRequestQueue();
            mQueue.add(imageRequest);*/












            container.addView(item);

            final int pos = position;
            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                  /*  Intent intent = new Intent(ProductDeatilActivity.this, ProductDeatilActivity.class);
                    intent.putExtra("position", pos);
                    startActivity(intent);*/
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
