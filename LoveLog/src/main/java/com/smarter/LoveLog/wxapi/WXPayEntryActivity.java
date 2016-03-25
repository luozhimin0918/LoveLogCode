package com.smarter.LoveLog.wxapi;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.activity.BaseFragmentActivity;
import com.smarter.LoveLog.activity.MainActivity;
import com.smarter.LoveLog.db.ConstantsWeixin;
import com.smarter.LoveLog.http.ZhifuPay;
import com.smarter.LoveLog.utills.DeviceUtil;
import com.tencent.mm.sdk.constants.Build;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;



import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

/**
 * Created by Administrator on 2015/11/30.
 */
public class WXPayEntryActivity extends BaseFragmentActivity implements IWXAPIEventHandler {
    String Tag = "WXPayEntryActivity";


    private static final String TAG = "MicroMsg.SDKSample.WXPayEntryActivity";

//    private IWXAPI api;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_result_weixin_view);
        ButterKnife.bind(this);


//        api = WXAPIFactory.createWXAPI(this, ConstantsWeixin.APP_ID);
        MainActivity.api.handleIntent(getIntent(), this);




    }

    @Override
    protected void onResume() {
        super.onResume();
    }



    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        MainActivity.api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @SuppressLint("LongLogTag")
    @Override
    public void onResp(BaseResp resp) {
//        Log.d(TAG,"onPayCode"+ resp.errCode);
        Log.d(TAG,"onRespBase"+resp.errCode);


        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(resp.errCode)));
            builder.show();
        }
    }







}
