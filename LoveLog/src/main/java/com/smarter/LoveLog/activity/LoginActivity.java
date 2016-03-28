package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.ConstantsQQ;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.http.FastJsonRequest;
import com.smarter.LoveLog.model.home.DataStatus;
import com.smarter.LoveLog.model.loginData.LoginDataActi;
import com.smarter.LoveLog.model.loginData.LoginDataInfo;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQToken;
import com.tencent.mm.sdk.constants.ConstantsAPI;
import com.tencent.mm.sdk.modelbase.BaseReq;
import com.tencent.mm.sdk.modelbase.BaseResp;
import com.tencent.mm.sdk.modelmsg.SendAuth;
import com.tencent.mm.sdk.openapi.IWXAPIEventHandler;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import java.util.HashMap;
import java.util.Map;

import butterknife.Bind;
import butterknife.ButterKnife;
import butterknife.OnClick;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Administrator on 2015/11/30.
 */
public class LoginActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag = "LoginActivity";
    @Bind(R.id.logon)
    LinearLayout logon;
    @Bind(R.id.linearSeePassword)
    LinearLayout linearSeePassword;
    @Bind(R.id.password)
    EditText password;
    @Bind(R.id.phone)
    EditText phone;
    @Bind(R.id.backBut)
    ImageView backBut;
    @Bind(R.id.regitstBut)
    TextView regitstBut;


    @Bind(R.id.forgetPass)
    LinearLayout forgetPass;


    Context mContext;

    RequestQueue mQueue;
    @Bind(R.id.weixinLogin)
    ImageView weixinLogin;
    @Bind(R.id.qqLogin)
    ImageView qqLogin;
    @Bind(R.id.xinlanLogin)
    ImageView xinlanLogin;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_view);
        ButterKnife.bind(this);
        getDataIntent();
        mContext = this;
        mQueue = AppContextApplication.getInstance().getmRequestQueue();
        intData();
        setListen();

    }

    private void setListen() {
        logon.setOnClickListener(this);
        backBut.setOnClickListener(this);
        regitstBut.setOnClickListener(this);
        linearSeePassword.setOnClickListener(this);
        forgetPass.setOnClickListener(this);
        password.addTextChangedListener(new EditChangedListener());
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void intData() {
        logon.setBackground(getResources().getDrawable(R.drawable.yuanjiao_love_red_bg_alpha));




        //这里的APP_ID请换成你应用申请的APP_ID，我这里使用的是DEMO中官方提供的测试APP_ID 222222
        mAppid = ConstantsQQ.APP_ID;
        //第一个参数就是上面所说的申请的APPID，第二个是全局的Context上下文，这句话实现了调用QQ登录
        mTencent = Tencent.createInstance(mAppid,getApplicationContext());
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        if (intent != null) {
            String str = intent.getStringExtra("ObjectData");
            // Toast.makeText(this,str+"",Toast.LENGTH_SHORT).show();
        }


    }

    Boolean isShowPassword = false;



    public LoginDataActi loginDataActi;

    private void LoginBut(final String user, final String pass, String uid, String sid) {
        String url = "http://mapp.aiderizhi.com/?url=/user/signin";//
        Map<String, String> mapTou = new HashMap<String, String>();
        String sessinStr = "{\"session\":{\"uid\":\"" + uid + "\",\"sid\":\"" + sid + "\"}}";
        mapTou.put("json", sessinStr);


        Map<String, String> map = new HashMap<String, String>();
        String value = "{\"account\":\"" + user + "\",\"password\":\"" + pass + "\"}";
        map.put("json", value);

        Log.d("loginActivity", sessinStr + "      " + value);


        FastJsonRequest<LoginDataInfo> fastJsonCommunity = new FastJsonRequest<LoginDataInfo>(Request.Method.POST, url, LoginDataInfo.class, mapTou, new Response.Listener<LoginDataInfo>() {
            @Override
            public void onResponse(LoginDataInfo loginDataInfo) {

                DataStatus status = loginDataInfo.getStatus();
                if (status.getSucceed() == 1) {
                    loginDataActi = loginDataInfo.getData();
                    if (loginDataActi != null) {
                        SharedPreferences.getInstance().putBoolean("islogin", true);
                        SharedPreferences.getInstance().putString("session", JSON.toJSONString(loginDataActi.getSession()));
                        SharedPreferences.getInstance().putString("usename", user);
                        SharedPreferences.getInstance().putString("password", pass);
                        SharedPreferences.getInstance().putString("user", JSON.toJSONString(loginDataActi.getUser()));
                        finish();
                        Log.d("loginActivity", "登录信息：   " + JSON.toJSONString(loginDataActi.getSession()) + "" + JSON.toJSONString(loginDataActi.getUser()) + "++++succeed");
                    }


                } else {

                    // 请求失败
                    Log.d("loginActivity", "succeded=0  " + JSON.toJSONString(status) + "");
                    Toast.makeText(getApplicationContext(), "" + status.getError_desc(), Toast.LENGTH_SHORT).show();

                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("loginActivity", "errror" + volleyError.toString() + "");
                Toast.makeText(mContext, "服务器出错啦。。。", Toast.LENGTH_SHORT).show();
            }
        });

        fastJsonCommunity.setParams(map);

        mQueue.add(fastJsonCommunity);
    }

    @OnClick({R.id.weixinLogin, R.id.qqLogin, R.id.xinlanLogin})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.weixinLogin:
                   weixinLoginMoth();
                break;
            case R.id.qqLogin:
                   tennetLoginMoth();
                break;
            case R.id.xinlanLogin:
                break;

            case R.id.linearSeePassword:
                if (!isShowPassword) {

                    //如果选中，显示密码
                    password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                    isShowPassword = true;
                } else {
                    password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                    isShowPassword = false;
                }
                break;
            case R.id.logon:
                LoginBut(phone.getText().toString(), password.getText().toString(), "", "");
                break;
            case R.id.backBut:
                finish();
                break;
            case R.id.regitstBut:
                //跳转到注册界面
                Intent intent = new Intent(mContext, QuaiRegitedActivity.class);
              /*  Bundle bundle = new Bundle();
                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                intent.putExtras(bundle);*/
                mContext.startActivity(intent);
                break;
            case R.id.forgetPass:
                //跳转到注册界面
                Intent intent2 = new Intent(mContext, FindPasswordActivity.class);
              /*  Bundle bundle = new Bundle();
                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                intent.putExtras(bundle);*/
                mContext.startActivity(intent2);
                break;

        }
    }




    class EditChangedListener implements TextWatcher {
        private CharSequence temp;//监听前的文本
        private int editStart;//光标开始位置
        private int editEnd;//光标结束位置
        private final int charMaxNum = 10;

        @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            if (s.length() == 5) {
                logon.setBackground(getResources().getDrawable(R.drawable.login_button));
                logon.setClickable(true);
            }
            if (s.length() < 5) {
                logon.setBackground(getResources().getDrawable(R.drawable.yuanjiao_love_red_bg_alpha));
                logon.setClickable(false);
            }
            Log.i(Tag, "输入文本之前的状态");
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            Log.i(Tag, "输入文字中的状态，count是一次性输入字符数");

        }

        @Override
        public void afterTextChanged(Editable s) {
            Log.i(Tag, "输入文字后的状态");
            /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */


        }
    }


    /**
     * 微信登录
     */
    private void weixinLoginMoth() {
        // send oauth request
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "wechat_love_log_test";
//        req.openId = getOpenId();

        MainActivity.api.sendReq(req);

    }

    /**
     * QQ登录
     */
    Tencent mTencent;
    public static String mAppid;
    private void tennetLoginMoth() {

        /**通过这句代码，SDK实现了QQ的登录，这个方法有三个参数，第一个参数是context上下文，第二个参数SCOPO 是一个String类型的字符串，表示一些权限
         官方文档中的说明：应用需要获得哪些API的权限，由“，”分隔。例如：SCOPE = “get_user_info,add_t”；所有权限用“all”
         第三个参数，是一个事件监听器，IUiListener接口的实例，这里用的是该接口的实现类 */
        mTencent.login(LoginActivity.this,"all", new BaseUiListener());
    }



    /**当自定义的监听器实现IUiListener接口后，必须要实现接口的三个方法，
     * onComplete  onCancel onError
     *分别表示第三方登录成功，取消 ，错误。*/
    public static String openidString;
    private class BaseUiListener implements IUiListener {

        public void onCancel() {
            // TODO Auto-generated method stub

        }

        public void onComplete(Object response) {
            // TODO Auto-generated method stub
            Toast.makeText(getApplicationContext(), "登录成功",Toast.LENGTH_SHORT).show();
            try {
                //获得的数据是JSON格式的，获得你想获得的内容
                //如果你不知道你能获得什么，看一下下面的LOG
                Log.e(Tag, "-------------" + response.toString());

                    openidString = ((JSONObject) response).getString("openid");


                Log.e(Tag, "-------------" + openidString);
                //access_token= ((JSONObject) response).getString("access_token");
                // expires_in = ((JSONObject) response).getString("expires_in");
            } catch (JSONException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
            /**到此已经获得OpneID以及其他你想获得的内容了
             QQ登录成功了，我们还想获取一些QQ的基本信息，比如昵称，头像什么的，这个时候怎么办？
             sdk给我们提供了一个类UserInfo，这个类中封装了QQ用户的一些信息，我么可以通过这个类拿到这些信息
             如何得到这个UserInfo类呢？  */
            QQToken qqToken = mTencent.getQQToken();
            UserInfo info = new UserInfo(getApplicationContext(), qqToken);
            //这样我们就拿到这个类了，之后的操作就跟上面的一样了，同样是解析JSON
        }

        @Override
        public void onError(UiError uiError) {

        }

    }




  /*  @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);

        setIntent(intent);
        MainActivity.api.handleIntent(intent, this);
    }
    @Override
    public void onReq(BaseReq baseReq) {

    }

    @Override
    public void onResp(BaseResp baseResp) {
        Log.d("loginActivity","onRespBase"+baseResp.errCode);


        if (baseResp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("提示");
            builder.setMessage(getString(R.string.pay_result_callback_msg, String.valueOf(baseResp.errCode)));
            builder.show();
        }

    }*/
}
