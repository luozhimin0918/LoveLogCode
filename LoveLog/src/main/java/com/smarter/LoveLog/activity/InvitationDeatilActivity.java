package com.smarter.LoveLog.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import org.json.JSONException;
import org.json.JSONObject;
import android.widget.LinearLayout;
import android.widget.TextView;
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
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.category.InvitationDataActi;
import com.smarter.LoveLog.model.category.InvitationDataDeatil;
import com.smarter.LoveLog.model.community.Pinglun;
import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.ui.CircleNetworkImage;
import com.smarter.LoveLog.ui.McoySnapPageLayout.McoyScrollView;
import com.smarter.LoveLog.ui.ProgressWebView;


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
    ImageView imageTopHeader;
    McoyScrollView invitation_Detail_scrollview;
    LinearLayout imglist;


    TextView titleText,userName,AddTime;
    CircleNetworkImage imageTitle;
    NetworkImageView imgTop;//头图片
    ProgressWebView webview;


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

//        initRecycleViewVertical();//评论刷新



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


        imageTitle=(CircleNetworkImage) header.findViewById(R.id.imageTitle);
       createUseImag();


        userName=(TextView) header.findViewById(R.id.userName);
        userName.setText(promotePostsData.getUser().getName());

        AddTime=(TextView) header.findViewById(R.id.AddTime);
        AddTime.setText(promotePostsData.getAdd_time());
        titleText=(TextView) header.findViewById(R.id.titleText);
        titleText.setText(promotePostsData.getTitle());


        //webVIew
        webview=(ProgressWebView) header.findViewById(R.id.webview);
        createWebview();

//        invitation_Detail_scrollview= (McoyScrollView) header.findViewById(R.id.invitation_Detail_scrollview);

        mRecyclerView.addHeaderView(header);
        mRecyclerView.setLoadingListener(new XRecyclerView.LoadingListener() {
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

        // 创建数据集
//        String[] dataset = new String[]{"用户名/昵称","绑定手机号","性别","会员等级","修改密码","收货地址"};
//        String[] dataValue=new String[]{"美羊羊","15083806689","男","V0初级会员","",""};
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
                super.onPageFinished(view, url);
                view.loadUrl("javascript:alert( $('#app_data').html() )");
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


        webview.loadUrl("http://mapp.aiderizhi.com/?url=/post/content&id="+promotePostsData.getId());
    }

    //头图片，多个两个图片
    private void createImgListTop() {
        imglist.removeAllViews();
        String[] imglistString=new String [1];
        PromotePostsData promotePostsDataItem=promotePostsData;
        if(promotePostsDataItem.getImg().getCover()!=null &&!promotePostsDataItem.getImg().getCover().equals("")){
            imglistString[0]=promotePostsDataItem.getImg().getCover();
        }
       /* if(promotePostsDataItem.getImg().getThumb()!=null&&!promotePostsDataItem.getImg().getThumb().equals("")){
            imglistString[1]=promotePostsDataItem.getImg().getThumb();
        }*/

        for(int i=0;i<imglistString.length;i++){
//            NetworkImageView networkImageViewListOne  = new NetworkImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.FILL_PARENT, 650);

            if(i==1){
                params.setMargins(0,5,0,0);
            }
            imgTop.setLayoutParams(params);
            imgTop.setScaleType(ImageView.ScaleType.FIT_XY);
            imgTop.setDefaultImageResId(R.mipmap.loadding);
            imgTop.setErrorImageResId(R.mipmap.loadding);

            if(mQueue.getCache().get(imglistString[i])==null){
                imgTop.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
            }
            imgTop.setImageUrl(imglistString[i], AppContextApplication.getInstance().getmImageLoader());
//            imglist.addView(networkImageViewListOne);

        }

    }

    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            postsData = (PromotePostsData) intent.getSerializableExtra("PromotePostsData");
            initData(postsData.getId());
        }
   }




    private void initData(final String id) {
        String url = "http://mapp.aiderizhi.com/?url=/post/detail";//

        Map<String, String> map = new HashMap<String, String>();

            map.put("id", id);




        FastJsonRequest<InvitationDataDeatil> fastJsonCommunity = new FastJsonRequest<InvitationDataDeatil>(Request.Method.POST, url, InvitationDataDeatil.class, null, new Response.Listener<InvitationDataDeatil>() {
            @Override
            public void onResponse(InvitationDataDeatil invitationDataDeatil) {

                DataStatus status = invitationDataDeatil.getStatus();
                if (status.getSucceed() == 1) {
                    promotePostsData = invitationDataDeatil.getData();
                    if(promotePostsData!=null){
                        initRecycle();
                    }

                    Log.d("invitationDeatil", "" + status.getSucceed() + "++++succeed" );
                } else {

                    // 请求失败
                    Log.d("invitationDeatil", "succeded=0>>>" + status.getSucceed() + "");

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("invitationDeatil", "errror" + volleyError.toString() + "");
            }
        });

        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);
    }
    @Override
    public void onClick(View v) {
         switch (v.getId()){

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


}
