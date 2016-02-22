package com.smarter.LoveLog.activity;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
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
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.adapter.RecyclePinglunAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.category.InvitationDataDeatil;
import com.smarter.LoveLog.model.community.CollectData;
import com.smarter.LoveLog.model.community.CollectDataInfo;
import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.model.community.RewardData;
import com.smarter.LoveLog.model.community.RewardDataInfo;
import com.smarter.LoveLog.model.community.ZanOrFaroDataInfo;
import com.smarter.LoveLog.model.community.ZanOrfaroData;
import com.smarter.LoveLog.model.goods.CmtGoods;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.jsonModel.ZanOrFaroviteParame;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.ui.CircleNetworkImage;
import com.smarter.LoveLog.ui.McoySnapPageLayout.McoyScrollView;
import com.smarter.LoveLog.ui.QCheckBox;
import com.smarter.LoveLog.ui.popwindow.AlertDialog;
import com.smarter.LoveLog.ui.popwindow.BabyPopWindow;
import com.smarter.LoveLog.utills.DeviceUtil;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import cn.sharesdk.onekeyshare.OnekeyShare;

/**
 * Created by Administrator on 2015/11/30.
 */
public class InvitationDeatilActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "InvitationDeatilActivity";
    Context  mContext;


    @Bind(R.id.frameLayout)
    FrameLayout frameLayout;

    @Bind(R.id.recyclerview)
    XRecyclerView mRecyclerView;
    @Bind(R.id.alphaBar)
    LinearLayout alphaBar;

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

    @Bind(R.id.backBut)
    ImageView backBut;
    @Bind(R.id.zanBut)
    QCheckBox zanBut;
    @Bind(R.id.collectBut)
    QCheckBox collectBut;

    @Bind(R.id.pinglunBut)
    TextView pinglunBut;

    @Bind(R.id.sharePic)
    TextView sharePic;






    @Bind(R.id.reword)
    TextView reword;


    @Bind(R.id.shareBut)
    ImageView shareBut;






    ImageView imageTopHeader;
    McoyScrollView invitation_Detail_scrollview;
    LinearLayout imglist;

    TextView allPinglun;
    TextView titleText,userName,AddTime;
    CircleNetworkImage imageTitle;
    NetworkImageView imgTop;//头图片
    WebView webview;


    PromotePostsData postsData;//上个页面传的item帖子数据
    PromotePostsData   promotePostsData;//传id网络获取的数据

    RequestQueue mQueue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_deatil_view);
        ButterKnife.bind(this);
        mContext=this;
        mQueue =  AppContextApplication.getInstance().getmRequestQueue();
        navigationBar();
        getDataIntent();
        intData();
        setListen();

    }

    private void navigationBar() {


        boolean  isHasNavigationBar = DeviceUtil.checkDeviceHasNavigationBar(mContext);
        if(isHasNavigationBar){
            FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            int hegit = DeviceUtil.getNavigationBarHeight(mContext);
//            Log.d("inTest","+++"+hegit);
            layoutParams.setMargins(0, 0, 0, hegit);//4个参数按顺序分别是左上右下

            frameLayout.setLayoutParams(layoutParams);
        }

    }

    private void setListen() {
        backBut.setOnClickListener(this);
        zanBut.setOnClickListener(this);
        collectBut.setOnClickListener(this);
        reword.setOnClickListener(this);
        pinglunBut.setOnClickListener(this);
        shareBut.setOnClickListener(this);
        sharePic.setOnClickListener(this);
        /*invitation_Detail_scrollview.setOnJDScrollListener(new McoyScrollView.OnJDScrollListener() {
            @Override
            public void onScroll(int x, int y, int oldx, int oldy) {
                if (imageTopHeader != null && imageTopHeader.getHeight() > 0) {
                    int height = imageTopHeader.getHeight();
                    if (y < height) {
                        int alpha = (int) (new Float(y) / new Float(height)
                                * 250);
                        Log.d("YJL", "" + alpha);
                        alphaBar.getBackground().setAlpha(alpha);
                    } else {
                        alphaBar.getBackground().setAlpha(255);
                    }
                }
            }
        });*/



       /* mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                totalDy += dy;
                // setTranslation/Alpha here according to totalDy.

                if (imglist != null && imglist.getHeight() > 0) {
                    int height = imglist.getHeight();
                    if (totalDy < 10) {
                        alphaBar.getBackground().setAlpha(0);
                    } else if (totalDy < height) {
                        int alpha = (int) (new Float(totalDy) / new Float(height)
                                * 250);

                        alphaBar.getBackground().setAlpha(alpha);
                    } else {
                        alphaBar.getBackground().setAlpha(255);
                    }
                    Log.d("YJL", "" + height + ">>>>>>>>>" + totalDy);
                }
            }
        });*/
    }

    private void intData() {
        alphaBar.getBackground().setAlpha(0);




    }

    private void initRecycle() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(mContext);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(layoutManager);
        mRecyclerView.setRefreshProgressStyle(ProgressStyle.SysProgress);
        mRecyclerView.setLaodingMoreProgressStyle(ProgressStyle.BallRotate);
        mRecyclerView.setArrowImageView(R.mipmap.iconfont_downgrey);

        View header =   LayoutInflater.from(mContext).inflate(R.layout.activity_invitation_deatil_top_view,null);
         imageTopHeader= (ImageView) header.findViewById(R.id.imageTopHeader);
        imgTop= (NetworkImageView) header.findViewById(R.id.imgTop);
        imglist= (LinearLayout) header.findViewById(R.id.imglist);

        allPinglun= (TextView) header.findViewById(R.id.allPinglun);
        allPinglun.setOnClickListener(this);


        createImgListTop();//加载top图片


        //webVIew
        webview=(WebView) header.findViewById(R.id.webview);
        createWebview();

        imageTitle=(CircleNetworkImage) header.findViewById(R.id.imageTitle);
       createUseImag();

        if(promotePostsData.getIs_like().equals("1")){
            zanBut.setChecked(true);
        }else{
            zanBut.setChecked(false);
        }
        zanBut.setText(promotePostsData.getLike_count());//点赞


        if(promotePostsData.getIs_collect().equals("1")){
            collectBut.setChecked(true);
        }else{
            collectBut.setChecked(false);
        }
        pinglunBut.setText(promotePostsData.getCmt_count()+"");//评论数
        collectBut.setText(promotePostsData.getCollect_count());//收藏

        userName=(TextView) header.findViewById(R.id.userName);
        userName.setText(promotePostsData.getUser().getName());

        AddTime=(TextView) header.findViewById(R.id.AddTime);
        AddTime.setText(promotePostsData.getAdd_time());
        titleText=(TextView) header.findViewById(R.id.titleText);
        titleText.setText(promotePostsData.getTitle());


        mRecyclerView.addHeaderView(header);
        mRecyclerView.setPullRefreshEnabled(false);
//        mRecyclerView.setOverScrollMode(View.OVER_SCROLL_ALWAY);
       /* mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
            @Override
            public void onRefresh() {

                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        mRecyclerView.refreshComplete();
                    }
                }, 1000);


            }

            @Override
            public void onLoadMore() {

                new Handler().postDelayed(new Runnable() {
                    public void run() {


                        mRecyclerView.loadMoreComplete();
                    }
                }, 2000);

            }
        });

*/


        /**
         * 加载完网页再加载评论
         */
        List<CmtGoods> pinglun=promotePostsData.getCmt();
        // 创建Adapter，并指定数据集
        RecyclePinglunAdapter adapter = new RecyclePinglunAdapter(pinglun);
        // 设置Adapter
        mRecyclerView.setAdapter(adapter);






    }

    private void createUseImag() {
        String UserimageUrl=promotePostsData.getUser().getAvatar();
        if(mQueue.getCache().get(UserimageUrl)==null){
           imageTitle.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        imageTitle.setImageUrl(UserimageUrl, AppContextApplication.getInstance().getmImageLoader());
    }

    private void createWebview() {
        webview.getSettings().setJavaScriptEnabled(true);
        // 添加js交互接口类，并起别名 imagelistner
        webview.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                view.getSettings().setJavaScriptEnabled(true);


                super.onPageFinished(view, url);
                addImageClickListner();



            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                view.getSettings().setJavaScriptEnabled(true);
                super.onPageStarted(view, url, favicon);
            }
        });


        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public boolean onJsAlert(WebView view, String url, String message, JsResult result) {
                if (message != null && !message.equals("")) {

                    try {
                        JSONObject object = new JSONObject(message);
                      /*  Log.i("infro", "" + message);
                        title = object.getString("title");
                        share = object.getString("weburl");
                        image = object.getString("thumb");
                        description = object.getString("description");
                        if (null != image && !image.equals("")) {
                            urlImage = new UMImage(
                                    AdWebActivity.this,
                                    new ImageDownLoader(getApplicationContext())
                                            .downloadImage(image));
                        }

                        accredit.setShareContent(title, url, description, mController, image);*/
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
                result.confirm();
                return true;
//                return super.onJsAlert(view, url, message, result);
            }
        });


        webview.loadUrl("http://mapp.aiderizhi.com/?url=/post/content&id=" + promotePostsData.getId());
    }


    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
        webview.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");
    }
    // js通信接口
    public class JavascriptInterface {

        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }
        @android.webkit.JavascriptInterface
        public void openImage(String img) {
            System.out.println(img);
            //
            Intent intent = new Intent();
            intent.putExtra("image", img);
            intent.setClass(context, ShowWebImageActivity.class);
            context.startActivity(intent);
            System.out.println(img);
        }
    }

    //头图片，多个两个图片
    private void createImgListTop() {

        imglist.removeAllViews();
        String imglistString="";
        PromotePostsData promotePostsDataItem=promotePostsData;
        if(promotePostsDataItem.getImg().getCover()!=null &&!promotePostsDataItem.getImg().getCover().equals("")){
            imglistString=promotePostsDataItem.getImg().getCover();
        }


            View view = View.inflate(this, R.layout.item_image_invitation, null);
            NetworkImageView img = (NetworkImageView) view.findViewById(R.id.iv_item);
            img.setDefaultImageResId(R.drawable.loading);
            img.setErrorImageResId(R.drawable.loading);
            if(mQueue.getCache().get(imglistString)==null){
                img.startAnimation(ImagePagerAdapter.getInAlphaAnimation(1000));
            }
            img.setImageUrl(imglistString, AppContextApplication.getInstance().getmImageLoader());
            imglist.addView(view);


    }

    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            postsData = (PromotePostsData) intent.getSerializableExtra("PromotePostsData");


            newLoad();
        }
   }

    private void newLoad() {

        if(DeviceUtil.checkConnection(mContext)){
            //加载动画
            progressLinear.setVisibility(View.VISIBLE);
            AnimationDrawable animationDrawable = (AnimationDrawable) progreView.getDrawable();
            animationDrawable.start();

            mRecyclerView.setVisibility(View.VISIBLE);
            networkInfo.setVisibility(View.GONE);
            initData(postsData.getId());
        }else{
            errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nowifi));
            mRecyclerView.setVisibility(View.GONE);
            networkInfo.setVisibility(View.VISIBLE);
            newLoading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    newLoad();
                }
            });
        }
    }


    private void initData(final String id) {
        String url = "http://mapp.aiderizhi.com/?url=/post/detail";//

        Map<String, String> map = new HashMap<String, String>();
        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
        if(isLogin){
            String  sessionString=SharedPreferences.getInstance().getString("session", "");
            sessionData = JSON.parseObject(sessionString,SessionData.class);
            if(sessionData!=null){

                ZanOrFaroviteParame zanOrFaroviteInfo=new ZanOrFaroviteParame();
                zanOrFaroviteInfo.setSession(sessionData);
                zanOrFaroviteInfo.setId(id);
                map.put("json", JSON.toJSONString(zanOrFaroviteInfo));

            }

        }else{
            map.put("id", id);
        }





        FastJsonRequest<InvitationDataDeatil> fastJsonCommunity = new FastJsonRequest<InvitationDataDeatil>(Request.Method.POST, url, InvitationDataDeatil.class, null, new Response.Listener<InvitationDataDeatil>() {
            @Override
            public void onResponse(InvitationDataDeatil invitationDataDeatil) {

                DataStatus status = invitationDataDeatil.getStatus();
                if (status.getSucceed() == 1) {

                    progressLinear.setVisibility(View.GONE);

                    promotePostsData = invitationDataDeatil.getData();
                    if(promotePostsData!=null){
                        initRecycle();
                    }

                    Log.d("invitationDeatil", "" + status.getSucceed() + "++++succeed" );
                } else {
                    // 请求失败
                    progressLinear.setVisibility(View.GONE);
                    mRecyclerView.setVisibility(View.GONE);
                    errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nodata));
                    networkInfo.setVisibility(View.VISIBLE);
                    // 请求失败
                    Log.d("invitationDeatil", "succeded=0>>>" + status.getSucceed() + "");

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                //未知错误
                progressLinear.setVisibility(View.GONE);
                mRecyclerView.setVisibility(View.GONE);
                errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_default));
                networkInfo.setVisibility(View.VISIBLE);
                Log.d("invitationDeatil", "errror" + volleyError.toString() + "");
            }
        });

        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);
    }





    @Override
    public void onClick(View v) {
        String url = "";//
         switch (v.getId()){
             case R.id.backBut:
                 finish();
                  break;
             case R.id.zanBut:
                 url = "http://mapp.aiderizhi.com/?url=/digg";//点赞
                    initIsLogonParame(url);
                 break;
             case R.id.collectBut:
                 url = "http://mapp.aiderizhi.com/?url=/collect";//收藏
                 initIsLogonParame(url);
                 break;
             case R.id.reword:
                 initPopuwindow();
                 showPopuwindow();
                 break;
             case R.id.allPinglun:
             case R.id.pinglunBut:

                 //挑战到所有评论界面//
                 //
                 Intent intent2 = new Intent(this, InvitationAllPinglunActivity.class);
                 Bundle bundle = new Bundle();
//                 bundle.putBoolean("allPinglun",true);dd
                 bundle.putSerializable("allpinglun",postsData);
                 intent2.putExtras(bundle);
                 this.startActivity(intent2);
                 break;
             case R.id.shareBut:
             case R.id.sharePic:

                 showShare(promotePostsData);

                 break;
         }
    }

    /**
     * 分享内容方法
     */
    private void showShare(PromotePostsData pro) {
//        ShareSDK.initSDK(this);
        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();
// 分享时Notification的图标和文字    2.5.9以后的版本不调用此方法
        //oks.setNotification(R.drawable.ic_launcher, getString(R.string.app_name));
        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
        oks.setTitle(pro.getShare().getTitle());
        // titleUrl是标题的网络链接，仅在人人网和QQ空间使用
        oks.setTitleUrl(pro.getShare().getUrl());
        // text是分享文本，所有平台都需要这个字段
        oks.setText(pro.getShare().getDesc());
        // imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//        oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
        //网络图片的url：所有平台
        oks.setImageUrl(pro.getShare().getThumb());//网络图片rul
        // url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(pro.getShare().getUrl());
        // comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("comment");
        // site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite("爱的日志");
        // siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(pro.getShare().getUrl());
// 启动分享GUI
        oks.show(mContext);
    }

    private  void showPopuwindow(){
        integral01.setVisibility(View.VISIBLE);
        integral02.setVisibility(View.VISIBLE);
        integral03.setVisibility(View.VISIBLE);

        int[] location = new int[2];
        reword.getLocationOnScreen(location);
        isWifiPopupWindow.showAtLocation(reword, Gravity.NO_GRAVITY, location[0], location[1]-(reword.getHeight()*4));//
        Log.d("test",reword.getHeight()+"");
        /**
         * 动画
         */
        AnimationSet animationSet = new AnimationSet(true);
        // Animation myAnimation= AnimationUtils.loadAnimation(mContext, R.anim.da_shan);
        RotateAnimation rotateAnimation = new RotateAnimation(
                270, 0, RotateAnimation.RELATIVE_TO_PARENT, 0.5f, RotateAnimation.RELATIVE_TO_PARENT, 0.4f);
        rotateAnimation.setInterpolator(new LinearInterpolator());
        rotateAnimation.setDuration(200);
        rotateAnimation.setRepeatCount(0);
        rotateAnimation.setFillAfter(true);
        // rotateAnimation.setStartOffset(50);


        /** 设置缩放动画 */
        final ScaleAnimation scaleAnimation = new ScaleAnimation(0.1f, 1.0f, 0.1f, 1.0f,
                Animation.RELATIVE_TO_PARENT, 0.3f, Animation.RELATIVE_TO_PARENT, 0.4f);
        scaleAnimation.setInterpolator(new LinearInterpolator());
        scaleAnimation.setDuration(200);//设置动画持续时间
        /** 常用方法 */
        scaleAnimation.setRepeatCount(0);//设置重复次数
        scaleAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        // scaleAnimation.setStartOffset(1000);//执行前的等待时间


//                    animationSet.addAnimation(rotateAnimation);
        animationSet.addAnimation(scaleAnimation);


        integral01.startAnimation(animationSet);
        integral02.startAnimation(animationSet);
        integral03.startAnimation(animationSet);
        /**
         * 动画后的黑色蒙层
         */
        BabyPopWindow.backgroundAlpha(mContext,0.55f);

    }





    SessionData   sessionData;
    private void initIsLogonParame(String url) {

        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
        if(isLogin){
            String  sessionString=SharedPreferences.getInstance().getString("session", "");
            sessionData = JSON.parseObject(sessionString,SessionData.class);
            if(sessionData!=null){

                ZanOrFaroviteParame zanOrFaroviteInfo=new ZanOrFaroviteParame();
                  zanOrFaroviteInfo.setSession(sessionData);
                  zanOrFaroviteInfo.setId(promotePostsData.getId());
                  zanOrFaroviteInfo.setType("2");



                         if(url.endsWith("digg")){
                             networkTieZan(JSON.toJSONString(zanOrFaroviteInfo),url);
                         }
                         if(url.endsWith("collect")){
                             networkTieCollect(JSON.toJSONString(zanOrFaroviteInfo),url);
                         }



            }

        }else{
            Toast.makeText(getApplicationContext(), "未登录，请先登录" , Toast.LENGTH_SHORT).show();
        }
    }



   /* public void initRecycleViewVertical(){

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 默认是Vertical，可以不写
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);

        // 创建数据集
        String[] dataset = new String[]{"用户名/昵称","绑定手机号","性别","会员等级","修改密码","收货地址"};
        String[] dataValue=new String[]{"美羊羊","15083806689","男","V0初级会员","",""};
        // 创建Adapter，并指定数据集
        RecyclePinglunAdapter adapter = new RecyclePinglunAdapter(dataset,dataValue);
        // 设置Adapter
        recyclerView.setAdapter(adapter);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }*/


    /**
     * 帖子点赞
     */
    ZanOrfaroData zanOrfaroData;
    private void networkTieZan(String paramNet,String url) {

        Map<String, String> mapTou = new HashMap<String, String>();
        mapTou.put("json", paramNet);




        Log.d("invitationDeatil", paramNet + "      ");


        FastJsonRequest<ZanOrFaroDataInfo> fastJsonCommunity = new FastJsonRequest<ZanOrFaroDataInfo>(Request.Method.POST, url, ZanOrFaroDataInfo.class, null, new Response.Listener<ZanOrFaroDataInfo>() {
            @Override
            public void onResponse(ZanOrFaroDataInfo zanOrFaroDataInfo) {

                DataStatus status = zanOrFaroDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    zanOrfaroData = zanOrFaroDataInfo.getData();
                    if(zanOrfaroData!=null){
                        zanBut.setText(zanOrfaroData.getLike_count());
                        zanBut.setChecked(true);
                        Toast.makeText(getApplicationContext(), "点赞成功", Toast.LENGTH_SHORT).show();
                        Log.d("invitationDeatil", "invitationDeatil 成功返回信息：   " + JSON.toJSONString(zanOrfaroData)+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("invitationDeatil", "succeded=0  invitationDeatil 返回信息 " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("invitationDeatil", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }






    /**
     * 帖子收藏
     */
    CollectData collectData;
    private void networkTieCollect(String paramNet,String url) {

        Map<String, String> mapTou = new HashMap<String, String>();
        mapTou.put("json", paramNet);




        Log.d("invitationDeatil", paramNet + "      ");


        FastJsonRequest<CollectDataInfo> fastJsonCommunity = new FastJsonRequest<CollectDataInfo>(Request.Method.POST, url, CollectDataInfo.class, null, new Response.Listener<CollectDataInfo>() {
            @Override
            public void onResponse(CollectDataInfo collectDataInfo) {

                DataStatus status = collectDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    collectData = collectDataInfo.getData();
                    if(collectData!=null){
                        collectBut.setText(collectData.getCollect_count());
                        if(collectData.getIs_collect().equals("1")){
                            collectBut.setChecked(true);
                        }else {
                            collectBut.setChecked(false);
                        }
                        Toast.makeText(getApplicationContext(), "" + collectData.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.d("invitationDeatil", "invitationDeatil 成功返回信息：   " + JSON.toJSONString(zanOrfaroData)+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("invitationDeatil", "succeded=0  invitationDeatil 返回信息 " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("invitationDeatil", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }










    PopupWindow isWifiPopupWindow;
    ImageView integral01,integral02,integral03;
    LinearLayout popuLinear;
    int  populinerWidth=0;
    private void initPopuwindow() {
        // TODO Auto-generated method stub
        View popuView = LayoutInflater.from(mContext).inflate(R.layout.popuwindow_dashan_open,
                null);
        isWifiPopupWindow = new PopupWindow(popuView, RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);


        //在PopupWindow里面就加上下面代码，让键盘弹出时，不会挡住pop窗口。
        isWifiPopupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        isWifiPopupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);
        //点击空白处时，隐藏掉pop窗口
        isWifiPopupWindow.setBackgroundDrawable(new BitmapDrawable());
        // 设置点击窗口外边窗口消失
        isWifiPopupWindow.setOutsideTouchable(false);
        // 设置此参数获得焦点，否则无法点击
        isWifiPopupWindow.setFocusable(true);
        isWifiPopupWindow.update();

        //添加pop窗口关闭事件
        isWifiPopupWindow.setOnDismissListener(new poponDismissListener());


        integral01= (ImageView) popuView.findViewById(R.id.integral01);
        integral02= (ImageView) popuView.findViewById(R.id.integral02);
        integral03= (ImageView) popuView.findViewById(R.id.integral03);
        popuLinear= (LinearLayout) popuView.findViewById(R.id.popuLinear);
        populinerWidth=popuLinear.getWidth();
        popuLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWifiPopupWindow.dismiss();
            }
        });
        integral01.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(1, "5");

            }
        });
        integral02.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(2, "10");
            }
        });
        integral03.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAlertDialog(3,"15");
            }
        });
    }



    private void  showAlertDialog(final int num,final String jifen){
        BabyPopWindow.backgroundAlpha(mContext,1.0f);

        new AlertDialog(mContext).builder().setTitle("提示")
                .setMsg("此次打赏您将消耗"+jifen+"个积分")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startAnimatSet(num);
                        String url = "http://mapp.aiderizhi.com/?url=/post/reward";//打赏
                        initIsLogonParame(url, jifen);
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                isWifiPopupWindow.dismiss();
            }
        }).show();

    }



    /**
     * 添加新笔记时弹出的popWin关闭的事件，主要是为了将背景透明度改回来
     * @author cg
     *
     */
    class poponDismissListener implements PopupWindow.OnDismissListener{

        @Override
        public void onDismiss() {
            // TODO Auto-generated method stub
            //Log.v("List_noteTypeActivity:", "我是关闭事件");
            BabyPopWindow.backgroundAlpha(mContext, 1.0f);
        }

    }


    private void startAnimatSet(int option) {




        // 设置缩放动画
        final ScaleAnimation scaleAnimation = new ScaleAnimation(1.0f, 2.6f, 1.0f, 2.6f,
                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,0.5f);
        scaleAnimation.setInterpolator(new LinearInterpolator());
        scaleAnimation.setDuration(1000);//设置动画持续时间
        //** 常用方法
        scaleAnimation.setRepeatCount(0);//设置重复次数
        scaleAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
//        scaleAnimation.setStartOffset(1000);//执行前的等待时间
        scaleAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {


            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        AlphaAnimation inAlphaAnimation = new AlphaAnimation(1, 0);
        inAlphaAnimation.setRepeatCount(0);//设置重复次数
        inAlphaAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        inAlphaAnimation.setDuration(1000);
        inAlphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                isWifiPopupWindow.dismiss();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        AnimationSet animationSet =new AnimationSet(false);
        animationSet.addAnimation(inAlphaAnimation);
        animationSet.addAnimation(scaleAnimation);

        BabyPopWindow.backgroundAlpha(mContext, 1.0f);


        if(option==1){
            integral02.setVisibility(View.INVISIBLE);
            integral03.setVisibility(View.INVISIBLE);
            integral01.startAnimation(animationSet);

        }
        if(option==2){
            integral01.setVisibility(View.INVISIBLE);
            integral03.setVisibility(View.INVISIBLE);
            integral02.startAnimation(animationSet);
        }
        if(option==3){
            integral01.setVisibility(View.INVISIBLE);
            integral02.setVisibility(View.INVISIBLE);
            integral03.startAnimation(animationSet);
        }

    }










    private void initIsLogonParame(String url,String reward) {

        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
        if(isLogin){
            String  sessionString=SharedPreferences.getInstance().getString("session", "");
            SessionData  sessionData = JSON.parseObject(sessionString, SessionData.class);
            if(sessionData!=null){

                ZanOrFaroviteParame zanOrFaroviteInfo=new ZanOrFaroviteParame();
                zanOrFaroviteInfo.setSession(sessionData);
                if(postsData!=null){
                    zanOrFaroviteInfo.setPost_id(postsData.getId());
                    zanOrFaroviteInfo.setReward(reward);

                    networkReward(JSON.toJSONString(zanOrFaroviteInfo), url);
                }












            }

        }else{
            Toast.makeText(mContext, "未登录，请先登录", Toast.LENGTH_SHORT).show();
        }
    }





    /**
     * 打赏
     */
    RewardData rewardData;
    private void networkReward(String paramNet,String url) {

        Map<String, String> mapTou = new HashMap<String, String>();
        mapTou.put("json", paramNet);




        Log.d("MonfanAdapter", paramNet + "      ");


        FastJsonRequest<RewardDataInfo> fastJsonCommunity = new FastJsonRequest<RewardDataInfo>(Request.Method.POST, url, RewardDataInfo.class, null, new Response.Listener<RewardDataInfo>() {
            @Override
            public void onResponse(RewardDataInfo rewardDataInfo) {

                DataStatus status = rewardDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    rewardData = rewardDataInfo.getData();
                    if(rewardData!=null){

                        Toast.makeText(mContext, "打赏成功" , Toast.LENGTH_SHORT).show();
                        Log.d("MonfanAdapter", "MonfanAdapter 成功返回信息：   " + JSON.toJSONString(rewardData)+ "++++succeed");
                    }


                } else {
                    // 请求失败
                    Log.d("MonfanAdapter", "succeded=0  MonfanAdapter 返回信息 " + JSON.toJSONString(status) + "");
                    Toast.makeText(mContext, "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("MonfanAdapter", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }














    // 手指上下滑动时的最小速度
    private static final int YSPEED_MIN = 1000;

    // 手指向右滑动时的最小距离
    private static final int XDISTANCE_MIN = 150;

    // 手指向上滑或下滑时的最小距离
    private static final int YDISTANCE_MIN = 100;

    // 记录手指按下时的横坐标。
    private float xDown;

    // 记录手指按下时的纵坐标。
    private float yDown;

    // 记录手指移动时的横坐标。
    private float xMove;

    // 记录手指移动时的纵坐标。
    private float yMove;

    // 用于计算手指滑动的速度。
    private VelocityTracker mVelocityTracker;



    /**
     * 创建VelocityTracker对象，并将触摸界面的滑动事件加入到VelocityTracker当中。
     *
     * @param event
     *
     */
    private void createVelocityTracker(MotionEvent event) {
        if (mVelocityTracker == null) {
            mVelocityTracker = VelocityTracker.obtain();
        }
        mVelocityTracker.addMovement(event);
    }

    /**
     * 回收VelocityTracker对象。
     */
    private void recycleVelocityTracker() {
        mVelocityTracker.recycle();
        mVelocityTracker = null;
    }

    /**
     *
     * @return 滑动速度，以每秒钟移动了多少像素值为单位。
     */
    private int getScrollVelocity() {
        mVelocityTracker.computeCurrentVelocity(1000);
        int velocity = (int) mVelocityTracker.getYVelocity();
        return Math.abs(velocity);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        createVelocityTracker(event);
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                xDown = event.getRawX();
                yDown = event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                xMove = event.getRawX();
                yMove = event.getRawY();
                // 滑动的距离
                int distanceX = (int) (xMove - xDown);
                int distanceY = (int) (yMove - yDown);
                // 获取顺时速度
                int ySpeed = getScrollVelocity();
                // 关闭Activity需满足以下条件：
                // 1.x轴滑动的距离>XDISTANCE_MIN
                // 2.y轴滑动的距离在YDISTANCE_MIN范围内
                // 3.y轴上（即上下滑动的速度）<XSPEED_MIN，如果大于，则认为用户意图是在上下滑动而非左滑结束Activity
                if (ySpeed > 100) {
                } else if (ySpeed < 100) {
                    if (distanceX > XDISTANCE_MIN
                            && (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN)
                            && ySpeed < YSPEED_MIN) {
                        finish();
                        overridePendingTransition(R.anim.in_from_left,
                                R.anim.out_to_right);
                    } else if (distanceX < -XDISTANCE_MIN
                            && (distanceY < YDISTANCE_MIN && distanceY > -YDISTANCE_MIN)
                            && ySpeed < YSPEED_MIN) {

                        //挑战到所有评论界面//
                        //
                        Intent intent2 = new Intent(this, InvitationAllPinglunActivity.class);
                        Bundle bundle = new Bundle();
//                 bundle.putBoolean("allPinglun",true);dd
                        bundle.putSerializable("allpinglun",postsData);
                        intent2.putExtras(bundle);
                        this.startActivity(intent2);
                        overridePendingTransition(R.anim.in_from_right,
                                R.anim.out_to_left);
                    }
                }

                break;
            case MotionEvent.ACTION_UP:
                recycleVelocityTracker();
                break;
            default:
                break;
        }
        return super.dispatchTouchEvent(event);
    }





}
