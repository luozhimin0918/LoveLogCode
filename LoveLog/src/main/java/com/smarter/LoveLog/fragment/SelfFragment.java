package com.smarter.LoveLog.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.activity.InvitationDeatilActivity;
import com.smarter.LoveLog.activity.LoginActivity;
import com.smarter.LoveLog.activity.MyOrderFormActivity;
import com.smarter.LoveLog.activity.PersonalDataActivity;
import com.smarter.LoveLog.activity.QuaiRegitedActivity;
import com.smarter.LoveLog.activity.SetActivity;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.adapter.MyGridAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.model.community.User;
import com.smarter.LoveLog.model.loginData.SessionData;
import com.smarter.LoveLog.ui.CircleNetworkImage;
import com.smarter.LoveLog.ui.MyGridView;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;
import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

/**
 * Created by Administrator on 2015/11/30.
 */
public class SelfFragment extends Fragment implements View.OnClickListener {
    protected WeakReference<View> mRootView;
    private View view;

    Context mContext;
    @Bind(R.id.loginImg)
    CircleNetworkImage loginImg;
    @Bind(R.id.loginText)
    TextView loginText;
    @Bind(R.id.setBut)
    ImageView setBut;
    @Bind(R.id.kefuBut)
    LinearLayout kefuBut;

    @Bind(R.id.orderMySelf)
    RelativeLayout orderMySelf;






    MyGridView gridview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
            view = inflater.inflate(R.layout.self_fragment, null);
            mRootView = new WeakReference<View>(view);
            mContext=getContext();
           ButterKnife.bind(this,view);
            initData();
            setListen();
        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        Log.d("SelfFragment", "onCreateView");
        return mRootView.get();

    }

    private void setListen() {
        loginImg.setOnClickListener(this);
        setBut.setOnClickListener(this);
        kefuBut.setOnClickListener(this);
        orderMySelf.setOnClickListener(this);
    }

    private void initData() {
        gridview= (MyGridView) view.findViewById(R.id.gridview);
        gridview.setFocusable(false);

        gridview.setAdapter(new MyGridAdapter(getContext()));


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.loginImg:
                Boolean islogin=   SharedPreferences.getInstance().getBoolean("islogin", false);

                if( islogin){
                    //个人资料
                    Intent intent = new Intent(mContext, PersonalDataActivity.class);
                  /*  Bundle bundle = new Bundle();
                    bundle.putSerializable("PromotePostsData", (Serializable) pp);
                    intent.putExtras(bundle);*/
                    mContext.startActivity(intent);
                }else {
                    //登录
                    Intent intent = new Intent(mContext, LoginActivity.class);
                  /*  Bundle bundle = new Bundle();
                    bundle.putSerializable("PromotePostsData", (Serializable) pp);
                    intent.putExtras(bundle);*/
                    mContext.startActivity(intent);
                }

                break;
            case R.id.setBut:
                //挑战到宝贝搜索界面
                Intent intent2 = new Intent(mContext, SetActivity.class);
              /*  Bundle bundle = new Bundle();
                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                intent.putExtras(bundle);*/
                mContext.startActivity(intent2);
                break;
            case R.id.kefuBut:
                /**
                 * 启动客服聊天界面。
                 *
                 * @param context          应用上下文。
                 * @param conversationType 开启会话类型。
                 * @param targetId         客服 Id。
                 * @param title            客服标题。
                 */
                RongIM.getInstance().startConversation(getContext(), Conversation.ConversationType.APP_PUBLIC_SERVICE, "KEFU145033288579386", "客服");


                break;
            case R.id.orderMySelf:
                //挑战到宝贝搜索界面
                Intent intent3 = new Intent(mContext, MyOrderFormActivity.class);
              /*  Bundle bundle = new Bundle();
                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                intent.putExtras(bundle);*/
                mContext.startActivity(intent3);
                break;

        }
    }





    @Override
    public void onResume() {

        Boolean islogin=   SharedPreferences.getInstance().getBoolean("islogin", false);
        RequestQueue mQueue =  AppContextApplication.getInstance().getmRequestQueue();
        String UserimageUrl= null;
        String  userTitle= null;
        if(islogin){

            try {

                String  userString=SharedPreferences.getInstance().getString("user","");
                User user = JSON.parseObject(userString, User.class);
                UserimageUrl = user.getAvatar();
                userTitle = user.getName();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            loginText.setText(userTitle);
        }else{
            UserimageUrl= "";
             userTitle= "";
            loginText.setText("登录/注册");
        }

        if(mQueue.getCache().get(UserimageUrl)==null){
            loginImg.startAnimation(ImagePagerAdapter.getInAlphaAnimation(1000));
        }
        loginImg.setDefaultImageResId(R.mipmap.login);
        loginImg.setErrorImageResId(R.mipmap.login);
        loginImg.setImageUrl(UserimageUrl, AppContextApplication.getInstance().getmImageLoader());

        Log.d("SelfFragment", "onResume");
         super.onResume();

    }
}
