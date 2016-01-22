package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.JsResult;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.RecyclePersonAdapter;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.help.HelpData;
import com.smarter.LoveLog.model.help.HelpDataInfo;
import com.smarter.LoveLog.model.help.HelpDataList;
import com.smarter.LoveLog.model.help.HelpDataWeb;
import com.smarter.LoveLog.model.help.HelpDataWebInfo;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.SessionData;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class HelpDataThreeWebActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "HelpDataThreeWebActivity";



    @Bind(R.id.backBUt)
    ImageView backBUt;
    @Bind(R.id.tv_top_title)
    TextView tv_top_title;
    @Bind(R.id.webview)
    WebView webview;

    @Bind(R.id.WebTexts)
    TextView WebTexts;












    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help_data_web_view);
        ButterKnife.bind(this);



        getDataIntent();

        setListen();

    }

    @Override
    protected void onResume() {
        super.onResume();
        intData();
    }

    private void setListen() {
        backBUt.setOnClickListener(this);
    }
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void intData() {
        if(helpDataList!=null){
            networkPersonl("","",helpDataList.getId());
        }





    }
    HelpDataList helpDataList;
    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
           helpDataList = (HelpDataList) intent.getSerializableExtra("threeWebHelpdata");
            if(helpDataList!=null){
                tv_top_title.setText(helpDataList.getName());
//                createWebview();
            }
        }


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


        webview.loadUrl("http://mapp.aiderizhi.com/?url=/help/detail&id=" + helpDataList.getId());


    }



    /**
     * 获取helpweb
     */
    HelpDataWeb helpDataWeb;
    private void networkPersonl(String uid,String sid,String id) {
        String url = "http://mapp.aiderizhi.com/?url=/help/detail";//
        Map<String, String> mapTou = new HashMap<String, String>();
        String  sessinStr ="{\"session\":{\"uid\":\""+uid+"\",\"sid\":\""+sid+"\"},\"id\":"+id+"}";
        mapTou.put("json", sessinStr);




        Log.d("HelpDataThreeWeb", sessinStr + "      ");


        FastJsonRequest<HelpDataWebInfo> fastJsonCommunity = new FastJsonRequest<HelpDataWebInfo>(Request.Method.POST, url, HelpDataWebInfo.class, null, new Response.Listener<HelpDataWebInfo>() {
            @Override
            public void onResponse(HelpDataWebInfo helpDataWebInfo) {

                DataStatus status = helpDataWebInfo.getStatus();
                if (status.getSucceed() == 1) {
                    helpDataWeb = helpDataWebInfo.getData();
                    if(helpDataWeb!=null){
                        WebTexts.setText(Html.fromHtml(helpDataWeb.getContent()));
                        Log.d("HelpDataThreeWeb", "帮助Web：   " + JSON.toJSONString(helpDataWeb)+ "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("HelpDataThreeWeb", "succeded=00000  " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("HelpDataThreeWeb", "errror" + volleyError.toString() + "");
            }
        });
        fastJsonCommunity.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        //fastJsonCommunity.setTag(TAG);
        fastJsonCommunity.setParams(mapTou);
        fastJsonCommunity.setShouldCache(true);
        mQueue.add(fastJsonCommunity);
    }





    @Override
    public void onClick(View v) {
         switch (v.getId()){

             case  R.id.backBUt:
                 finish();
                 break;
         }
    }

















}
