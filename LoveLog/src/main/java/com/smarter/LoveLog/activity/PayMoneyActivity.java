package com.smarter.LoveLog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smarter.LoveLog.db.ConstantsWeixin;
import com.tencent.mm.sdk.constants.Build;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.http.ZhifuPay;
import com.smarter.LoveLog.utills.DeviceUtil;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelpay.PayReq;
import com.tencent.mm.sdk.openapi.IWXAPI;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.sdk.openapi.WXAPIFactory;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * Created by Administrator on 2015/11/30.
 */
public class PayMoneyActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag = "PayMoneyActivity";
    @Bind(R.id.backBUt)
    ImageView backBUt;
    @Bind(R.id.tv_top_title)
    TextView tvTopTitle;
    @Bind(R.id.tv_right_title)
    TextView tvRightTitle;
    @Bind(R.id.zhifuPay)
    LinearLayout zhifuPay;
    @Bind(R.id.weixinPay)
    LinearLayout weixinPay;
    @Bind(R.id.yinglianPay)
    LinearLayout yinglianPay;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pay_money_view);
        ButterKnife.bind(this);


        getDataIntent();


    }

    @Override
    protected void onResume() {
        super.onResume();
    }





    /**
     * 支付宝支付
     */
    private void payZhifu() {


        if(chekIsConnection()){
            ZhifuPay zhifuPay=new ZhifuPay(this,PayMoneyActivity.this);
            zhifuPay.payTo();
        }

    }
    /**
     * 微信支付
     */
    private void payWeixin() {


        if(chekIsConnection()){
            weixinPay();
        }

    }


    // APP_ID 替换为你的应用从官方网站申请到的合法appId

    private void weixinPay() {
        weixinPay.setEnabled(false);


        String url = "http://wxpay.weixin.qq.com/pub_v2/app/app_pay.php?plat=android";
        boolean isPaySupported = MainActivity.api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
        if(isPaySupported){
            networkWeixinDiandang(url);
            weixinPay.setEnabled(true);
        }else{
            Toast.makeText(PayMoneyActivity.this, "当前微信版本不支持支付", Toast.LENGTH_SHORT).show();
        }


    }


    private void networkWeixinDiandang(String url) {


        StringRequest stringRequest = new StringRequest(Request.Method.GET,url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject json=new JSONObject(response);
                            if(null != json && !json.has("retcode") ){
                                PayReq req = new PayReq();
//                                req.appId = ConstantsWeixin.APP_ID;  // 测试用appId
                                req.appId			= json.getString("appid");
                                req.partnerId		= json.getString("partnerid");
                                req.prepayId		= json.getString("prepayid");
                                req.nonceStr		= json.getString("noncestr");
                                req.timeStamp		= json.getString("timestamp");
                                req.packageValue	= json.getString("package");
                                req.sign			= json.getString("sign");
                                req.extData			= "app data"; // optional
                                Toast.makeText(PayMoneyActivity.this, "正常调起支付", Toast.LENGTH_SHORT).show();
                                // 在支付之前，如果应用没有注册到微信，应该先调用IWXMsg.registerApp将应用注册到微信
                                MainActivity.api.sendReq(req);
                            }else{
                                Log.d("PAY_GET", "返回错误"+json.getString("retmsg"));
                                Toast.makeText(PayMoneyActivity.this, "返回错误"+json.getString("retmsg"), Toast.LENGTH_SHORT).show();
                            }


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                        Log.d("PayMoneyActivity", "response -> " + response);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.e("PayMoneyActivity", error.getMessage(), error);
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

        stringRequest.setRetryPolicy(new DefaultRetryPolicy(5000,
                DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT));
        stringRequest.setShouldCache(true);
        mQueue.add(stringRequest);
    }

    private Boolean chekIsConnection(){
        if(DeviceUtil.checkConnection(this)){
           return true;
        } else {
            Toast.makeText(getApplicationContext(), "连接不到网络", Toast.LENGTH_SHORT).show();
            return false;

        }

    }



    private void getDataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
//            goodsData = (GoodsData) intent.getSerializableExtra("goods");
//            // Toast.makeText(this,str+"",Toast.LENGTH_LONG).show();


        }


    }


    @OnClick({R.id.zhifuPay, R.id.weixinPay, R.id.yinglianPay,R.id.backBUt})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.zhifuPay:
                payZhifu();
                break;
            case R.id.weixinPay:
                payWeixin();
                break;
            case R.id.yinglianPay:
                break;
            case  R.id.backBUt:
                finish();
                break;
        }
    }



}
