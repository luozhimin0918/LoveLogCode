package com.smarter.LoveLog.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.jcodecraeer.xrecyclerview.ProgressStyle;
import com.jcodecraeer.xrecyclerview.XRecyclerView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.activity.ShowWebImageActivity;
import com.smarter.LoveLog.adapter.RecycleOrderAllAdapter;
import com.smarter.LoveLog.adapter.RecycleRedpacketUnusedAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.PaginationJson;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.model.redpacket.RedList;
import com.smarter.LoveLog.model.redpacket.RedPacketInfo;
import com.smarter.LoveLog.ui.FoundWebView;
import com.smarter.LoveLog.ui.McoySnapPageLayout.McoyScrollView;
import com.smarter.LoveLog.ui.McoySnapPageLayout.McoySnapPageLayout;
import com.smarter.LoveLog.utills.DeviceUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class GoodsImgTextFragment extends Fragment{
    protected WeakReference<View> mRootView;
    private View view;
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
    FoundWebView webview;


    @Bind(R.id.progressLinear)
    LinearLayout progressLinear;

    @Bind(R.id.progreView)
    ImageView progreView;
    Context mContext;
   String  url="";
    McoyScrollView mcoyScrollView;
    McoySnapPageLayout mcoySnapPageLayout;

    public GoodsImgTextFragment(){

    }
    public GoodsImgTextFragment(McoySnapPageLayout mcoySnapPageLayout,McoyScrollView mcoyScrollView,String url) {
        this.url = url;
        this.mcoyScrollView=mcoyScrollView;
        this.mcoySnapPageLayout=mcoySnapPageLayout;
    }

    @Nullable



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
            view = inflater.inflate(R.layout.goods_img_text_fragment, null);
            mRootView = new WeakReference<View>(view);
            mContext=getContext();
            ButterKnife.bind(this, view);
            networkLoading();
        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        return mRootView.get();

    }


    private void networkLoading() {
        if(DeviceUtil.checkConnection(mContext)){
            //加载动画
            progressLinear.setVisibility(View.VISIBLE);
            AnimationDrawable animationDrawable = (AnimationDrawable) progreView.getDrawable();
            animationDrawable.start();

            webview.setVisibility(View.GONE);
            networkInfo.setVisibility(View.GONE);
            createWebview(url);
        }else{
            errorInfo.setImageDrawable(getResources().getDrawable(R.mipmap.error_nowifi));
            webview.setVisibility(View.GONE);
            networkInfo.setVisibility(View.VISIBLE);
            newLoading.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    networkLoading() ;
                }
            });
        }
    }

    //手指按下的点为(x1, y1)手指离开屏幕的点为(x2, y2)
    float x1 = 0;
    float x2 = 0;
    float y1 = 0;
    float y2 = 0;
    @SuppressLint("AddJavascriptInterface")
    private void createWebview(String urlParam) {

        webview.getSettings().setJavaScriptEnabled(true);
        webview.loadUrl(urlParam);
// 添加js交互接口类，并起别名 imagelistner
        webview.addJavascriptInterface(new JavascriptInterface(mContext), "imagelistner");
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
        });


        webview.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                super.onReceivedTitle(view, title);
                Log.d("ANDROID_LAB", "TITLE=" + title);
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

       webview.setOnTouchListener(new View.OnTouchListener() {

           @Override
           public boolean onTouch(View v, MotionEvent event) {
               // TODO Auto-generated method stub


               //继承了Activity的onTouchEvent方法，直接监听点击事件
               if (event.getAction() == MotionEvent.ACTION_DOWN) {
                   //当手指按下的时候
                   x1 = event.getX();
                   y1 = event.getY();
               }
               if (event.getAction() == MotionEvent.ACTION_UP) {
                   //当手指离开的时候
                   x2 = event.getX();
                   y2 = event.getY();
                   if (y1 - y2 > 50) {
//                        Toast.makeText(MainActivity.this, "向上滑", Toast.LENGTH_SHORT).show();
                   } else if (y2 - y1 > 50) {
                       if (webview.getScrollY() == 0&&event.getAction() == MotionEvent.ACTION_UP) {
                           mcoySnapPageLayout.snapToPrev();
                           //                    Toast.makeText(getContext(), "已经顶端", Toast.LENGTH_SHORT).show();
                       }
//                        Toast.makeText(MainActivity.this, "向下滑", Toast.LENGTH_SHORT).show();
                   } else if (x1 - x2 > 50) {
//                        Toast.makeText(MainActivity.this, "向左滑", Toast.LENGTH_SHORT).show();
                   } else if (x2 - x1 > 50) {
//                        Toast.makeText(MainActivity.this, "向右滑", Toast.LENGTH_SHORT).show();
                   }
               }




               if(mcoyScrollView!=null&&mcoySnapPageLayout !=null){

                   if (event.getAction() == MotionEvent.ACTION_UP){

                       mcoyScrollView.requestDisallowInterceptTouchEvent(false);


                   }else{
                       mcoyScrollView.requestDisallowInterceptTouchEvent(true);

                   }
                  /* if (webview.getScrollY() == 0&&event.getAction() == MotionEvent.ACTION_UP) {
                       mcoySnapPageLayout.snapToPrev();
                       //                    Toast.makeText(getContext(), "已经顶端", Toast.LENGTH_SHORT).show();
                   }*/


               }



               return false;
           }


       });





        webview.setOnCustomScroolChangeListener(new FoundWebView.ScrollInterface() {
            @Override
            public void onSChanged(int l, int t, int oldl, int oldt) {

// TODO Auto-generated method stub
                float webcontent = webview.getContentHeight() * webview.getScale();//webview的高度
                float webnow = webview.getHeight() + webview.getScrollY();//当前webview的高度
                if (webview.getContentHeight() * webview.getScale() - (webview.getHeight() + webview.getScrollY()) == 0) {

//已经处于底端

//                    Toast.makeText(getContext(), "已经处于底端", Toast.LENGTH_SHORT).show();
                } else {

//                    Toast.makeText(getContext(), "中间", Toast.LENGTH_SHORT).show();
                }
//已经处于顶端
                if (webview.getScrollY() == 0) {
//                    Toast.makeText(getContext(), "已经顶端", Toast.LENGTH_SHORT).show();
                }

//                Log.d("test", webview.getScrollY() + "-----");

            }
        });

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


}
