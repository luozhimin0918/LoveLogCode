package com.smarter.LoveLog.http;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Message;
import android.support.v7.app.AlertDialog;
import android.util.Log;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.unionpay.UPPayAssistEx;

import org.json.JSONException;
import org.json.JSONObject;
import android.os.Handler;
import android.os.Handler.Callback;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Administrator on 2016/5/9.
 */
public class Unionpay {
    Context mContext;
    Activity mActivity;
    RequestQueue mQueue;
    private ProgressDialog mLoadingDialog = null;
    /*****************************************************************
     * mMode参数解释： "00" - 启动银联正式环境 "01" - 连接银联测试环境
     *****************************************************************/
    public final static String mMode = "01";
    private static final String TN_URL_01 = "http://101.231.204.84:8091/sim/getacptn";

    public Unionpay(Context mContext,Activity activity, RequestQueue  mQueue) {
        this.mContext =mContext;
        this.mActivity=activity;
        this.mQueue=mQueue;

    }
    Handler mHandler = new Handler() {
        public void handleMessage(Message msg) {
            Log.d("Unionpay", "response -> " + msg.what);
            switch (msg.what) {
                case 1:
                    if (mLoadingDialog.isShowing()) {
                        mLoadingDialog.dismiss();
                    }

                    String tn = "";
                    if (msg.obj == null || ((String) msg.obj).length() == 0) {
                        AlertDialog.Builder builder = new AlertDialog.Builder(mContext);
                        builder.setTitle("错误提示");
                        builder.setMessage("网络连接失败,请重试!");
                        builder.setNegativeButton("确定",
                                new DialogInterface.OnClickListener() {
                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });
                        builder.create().show();
                    } else {
                        tn = (String) msg.obj;
                        /*************************************************
                         * 步骤2：通过银联工具类启动支付插件
                         ************************************************/
                        UPPayAssistEx.startPay(mActivity, null, null, tn, mMode);
                    }

                    break;
            }
            super.handleMessage(msg);
        }
    };

    public void paytoUnion(){

        mLoadingDialog = ProgressDialog.show(mContext, // context
                "", // title
                "正在努力的获取tn中,请稍候...", // message
                true); // 进度是否是不确定的，这只和创建进度条有关

        /*************************************************
         * 步骤1：从网络开始,获取交易流水号即TN
         ************************************************/
        StringRequest stringRequest = new StringRequest(Request.Method.GET,TN_URL_01,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {




                            Message msg = mHandler.obtainMessage();
                            msg.what=1;
                            msg.obj = response;
                            mHandler.sendMessage(msg);

                        Log.d("Unionpay", "response -> " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("Unionpay", error.getMessage(), error);
            }
        }) {
            @Override
            protected Map<String, String> getParams() {
                //在这里设置需要post的参数
                Map<String, String> map = new HashMap<String, String>();
                map.put("name1", "value1");
                map.put("name2", "value2");
                return map;
            }
        };

//        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
//                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
//        stringRequest.setShouldCache(true);
        mQueue.add(stringRequest);
    }



}
