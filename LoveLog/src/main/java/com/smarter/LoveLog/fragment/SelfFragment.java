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
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.activity.InvitationDeatilActivity;
import com.smarter.LoveLog.activity.LoginActivity;
import com.smarter.LoveLog.activity.PersonalDataActivity;
import com.smarter.LoveLog.activity.QuaiRegitedActivity;
import com.smarter.LoveLog.activity.SetActivity;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.adapter.MyGridAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.SharedPreferences;
import com.smarter.LoveLog.model.community.PromotePostsData;
import com.smarter.LoveLog.ui.CircleNetworkImage;
import com.smarter.LoveLog.ui.MyGridView;

import java.io.Serializable;
import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

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
        Log.d("SelfFragment","onCreateView");
        return mRootView.get();

    }

    private void setListen() {
        loginImg.setOnClickListener(this);
        setBut.setOnClickListener(this);
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


        }
    }


    @Override
    public void onResume() {

        Boolean islogin=   SharedPreferences.getInstance().getBoolean("islogin", false);
//        Boolean islogin=   false;
        if(islogin){
            RequestQueue mQueue =  AppContextApplication.getInstance().getmRequestQueue();

            String UserimageUrl= null;
            String  userTitle= null;
            try {
                UserimageUrl = AppContextApplication.LoginInfoAll.getUser().getAvatar();
                userTitle = AppContextApplication.LoginInfoAll.getUser().getName();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }


            if(mQueue.getCache().get(UserimageUrl)==null){
                loginImg.startAnimation(ImagePagerAdapter.getInAlphaAnimation(1000));
            }
            loginImg.setDefaultImageResId(R.mipmap.login);
            loginImg.setErrorImageResId(R.mipmap.login);
            loginImg.setImageUrl(UserimageUrl, AppContextApplication.getInstance().getmImageLoader());

            loginText.setText(userTitle);
        }



        Log.d("SelfFragment", "onResume");
                super.onResume();

    }
}
