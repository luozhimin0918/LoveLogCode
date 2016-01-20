package com.smarter.LoveLog.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.CheckBox;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;
import android.widget.LinearLayout;
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
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.adapter.RecyclePinglunAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.category.InvitationDataDeatil;
import com.smarter.LoveLog.model.community.CollectData;
import com.smarter.LoveLog.model.community.CollectDataInfo;
import com.smarter.LoveLog.model.community.Pinglun;
import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.community.ZanOrFaroDataInfo;
import com.smarter.LoveLog.model.community.ZanOrfaroData;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.jsonModel.ZanOrFaroviteParame;
import com.smarter.LoveLog.model.loginData.PersonalDataInfo;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.ui.CircleNetworkImage;
import com.smarter.LoveLog.ui.McoySnapPageLayout.McoyScrollView;
import com.smarter.LoveLog.ui.QCheckBox;
import com.smarter.LoveLog.utills.DeviceUtil;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class InvitationDeatilActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "InvitationDeatilActivity";
    Context  mContext;


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







    ImageView imageTopHeader;
    McoyScrollView invitation_Detail_scrollview;
    LinearLayout imglist;


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
        getDataIntent();
        intData();
        setListen();

    }

    private void setListen() {
        backBut.setOnClickListener(this);
        zanBut.setOnClickListener(this);
        collectBut.setOnClickListener(this);
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
        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            private int totalDy = 0;

            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                totalDy += dy;
                // setTranslation/Alpha here according to totalDy.

                if (imgTop != null && imgTop.getHeight() > 0) {
                    int height = imgTop.getHeight();
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
        });
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
        createImgListTop();//加载top图片


        //webVIew
        webview=(WebView) header.findViewById(R.id.webview);
        createWebview();

        imageTitle=(CircleNetworkImage) header.findViewById(R.id.imageTitle);
       createUseImag();

        if(promotePostsData.getIs_like().equals("1")){
            zanBut.setChecked(true);
        }
        zanBut.setText(promotePostsData.getLike_count());//点赞


        if(promotePostsData.getIs_collect().equals("1")){
            collectBut.setChecked(true);
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
        List<Pinglun> pinglun=promotePostsData.getCmt();
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
        webview.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub



                view.loadUrl("javascript:alert( $('#app_data').html() )");
                super.onPageFinished(view, url);




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
            img.setDefaultImageResId(R.mipmap.loadding);
            img.setErrorImageResId(R.mipmap.loadding);
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
            map.put("json", id);
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
         }
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






}
