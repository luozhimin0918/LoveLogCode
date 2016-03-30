package com.smarter.LoveLog.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.webkit.JavascriptInterface;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.utills.DeviceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/11/30.
 */
@SuppressLint("SetJavaScriptEnabled")
public class WebViewUrlActivity extends BaseFragmentActivity implements View.OnClickListener {
    String Tag = "WebViewUrl";
    Context mContext;

    @Bind(R.id.tv_top_title)
    TextView tv_top_title;


    @Bind(R.id.networkInfo)
    LinearLayout networkInfo;
    @Bind(R.id.errorInfo)
    ImageView errorInfo;
    @Bind(R.id.newLoading)
    LinearLayout newLoading;
    @Bind(R.id.loadingTextLinear)
    LinearLayout loadingTextLinear;
    @Bind(R.id.loadingText)
    TextView loadingText;


    @Bind(R.id.webview)
    WebView webview;


    @Bind(R.id.progressLinear)
    LinearLayout progressLinear;

    @Bind(R.id.progreView)
    ImageView progreView;

    @Bind(R.id.backBUt)
    ImageView backBUt;
    @Bind(R.id.CloseBUt)
    ImageView CloseBUt;




    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_webview_url_view);
        ButterKnife.bind(this);
        mContext = this;
        getDataIntent();

    }

    SessionData sessionData;

    private void isLogiin() {

        Boolean isLogin = SharedPreferences.getInstance().getBoolean("islogin", false);
        if (isLogin) {
            String sessionString = SharedPreferences.getInstance().getString("session", "");
            sessionData = JSON.parseObject(sessionString, SessionData.class);
            if (sessionData != null) {


            }

        } else {
            Toast.makeText(mContext, "未登录，请先登录", Toast.LENGTH_SHORT).show();
        }
    }


    String urlParam;//上个activity传来的数据

    private void getDataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            urlParam = intent.getStringExtra("param");

            if (urlParam != null && !urlParam.equals("")) {
//              urlParam="http://mapp.aiderizhi.com/?url=/post/content&id=22737";
                networkLoading();

            }


        }


    }

    private void networkLoading() {
        if (DeviceUtil.checkConnection(mContext)) {
            //加载动画
            progressLinear.setVisibility(View.VISIBLE);
            AnimationDrawable animationDrawable = (AnimationDrawable) progreView.getDrawable();
            animationDrawable.start();

            webview.setVisibility(View.GONE);
            networkInfo.setVisibility(View.GONE);
            createWebview(urlParam);
        } else {
            errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nowifi));
            webview.setVisibility(View.GONE);
            networkInfo.setVisibility(View.VISIBLE);
            newLoading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    networkLoading();
                }
            });
        }
    }


    boolean  isNextLoadUrl=false;
    @SuppressLint("AddJavascriptInterface")
    private void createWebview(String urlParam) {

        webview.getSettings().setJavaScriptEnabled(true);

        if (urlParam.startsWith("http")) {
            webview.loadUrl(urlParam);
        } else {
            webview.loadDataWithBaseURL(null, urlParam, "text/html", "utf-8", null);
        }

// 添加js交互接口类，并起别名 imagelistner
        webview.addJavascriptInterface(new JavascriptInterface(this), "imagelistner");
        webview.setWebViewClient(new WebViewClient() {


            @Override
            public void onPageFinished(WebView view, String url) {
                // TODO Auto-generated method stub
                webview.setVisibility(View.VISIBLE);
                progressLinear.setVisibility(View.GONE);
                view.getSettings().setJavaScriptEnabled(true);

                super.onPageFinished(view, url);
                // html加载完成之后，添加监听图片的点击js函数
                addImageClickListner();


            }

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                view.getSettings().setJavaScriptEnabled(true);
                super.onPageStarted(view, url, favicon);
            }

            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {

                if(isNextLoadUrl){
                    CloseBUt.setVisibility(View.VISIBLE);

                }else{
                    isNextLoadUrl=true;
                }


                view.loadUrl(url);   //在当前的webview中跳转到新的url
                return true;
            }
        });


        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d("ANDROID_LAB", "TITLE=" + title);
                tv_top_title.setText(title);
            }

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


    }

    // 注入js函数监听
    private void addImageClickListner() {
        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
      /*  webview.loadUrl("javascript:(function(){" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "        window.imagelistner.openImage(this.src);  " +
                "    }  " +
                "}" +
                "})()");*/


        // 这段js函数的功能就是，遍历所有的img几点，并添加onclick函数，在还是执行的时候调用本地接口传递url过去
        webview.loadUrl("javascript:(function(){" +
                " var srcs = [];" +
                "var objs = document.getElementsByTagName(\"img\"); " +
                "for(var i=0;i<objs.length;i++){" +
                "srcs[i] = objs[i].src; }" +


                "for(var i=0;i<objs.length;i++)  " +
                "{"
                + "    objs[i].onclick=function()  " +
                "    {  "
                + "  srcs.push(this.src);      window.imagelistner.openImage(srcs);  srcs.pop(); " +
                "    }  " +
                "}" +
                "})()");
    }



    @OnClick({R.id.backBUt, R.id.CloseBUt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.backBUt:
                if(isNextLoadUrl==false){
                    finish();
                }else {
                    webview.goBack();
                }

                break;
            case R.id.CloseBUt:
                finish();
                break;
        }
    }


    // js通信接口
    public class JavascriptInterface {

        private Context context;

        public JavascriptInterface(Context context) {
            this.context = context;
        }

        @android.webkit.JavascriptInterface
        public void openImage(String[] img) {
            if (urlParam.startsWith("http")) {

            } else {

                Intent intent = new Intent();
                intent.putExtra("images", img);
                intent.setClass(context, ShowAnoWebImageActivity.class);
                context.startActivity(intent);

            }

        }
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


//                        overridePendingTransition(R.anim.in_from_right,
//                                R.anim.out_to_left);
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
