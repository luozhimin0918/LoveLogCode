package com.smarter.LoveLog.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.nfc.Tag;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;
import butterknife.Bind;
import butterknife.ButterKnife;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.ui.popwindow.BabyPopWindow;
import com.smarter.LoveLog.ui.productViewPager.AutoLoopViewPager;
import com.smarter.LoveLog.ui.productViewPager.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2015/11/30.
 */
public class LoginActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "LoginActivity";
    @Bind(R.id.logon)
    LinearLayout logon;
    @Bind(R.id.linearSeePassword)
    LinearLayout linearSeePassword;
    @Bind(R.id.password)
    EditText password;





    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_login_view);
        ButterKnife.bind(this);
        getDataIntent();

        intData();
        setListen();

    }

    private void setListen() {
        linearSeePassword.setOnClickListener(this);
        password.addTextChangedListener(new EditChangedListener());
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    private void intData() {
        logon.setBackground(getResources().getDrawable(R.drawable.yuanjiao_love_red_bg_alpha));
    }

    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            String  str = intent.getStringExtra("ObjectData");
           // Toast.makeText(this,str+"",Toast.LENGTH_LONG).show();
        }


    }

   Boolean  isShowPassword=false;
    @Override
    public void onClick(View v) {
         switch (v.getId()){
             case R.id.linearSeePassword:
                    if(!isShowPassword){

                        //如果选中，显示密码
                        password.setTransformationMethod(HideReturnsTransformationMethod.getInstance());
                        isShowPassword=true;
                    }else{
                        password.setTransformationMethod(PasswordTransformationMethod.getInstance());
                        isShowPassword=false;
                    }
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
            if(s.length()==6){
                logon.setBackground(getResources().getDrawable(R.drawable.login_button));
                logon.setClickable(true);
            }
            if(s.length()<6){
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

}
