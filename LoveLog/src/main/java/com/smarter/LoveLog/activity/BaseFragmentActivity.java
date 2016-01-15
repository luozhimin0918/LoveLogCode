package com.smarter.LoveLog.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Window;
import android.view.WindowManager;

import com.android.volley.RequestQueue;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.AppContextApplication;

/**
 * Created by Administrator on 2015/11/30.
 */
public class BaseFragmentActivity extends AppCompatActivity {
    RequestQueue  mQueue;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
                                                                  //  requestWindowFeature(Window.FEATURE_NO_TITLE);
        // 添加Activity到堆栈
        AppContextApplication.getInstance().addActivity(this);

        // 透明状态栏
       getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // 透明导航栏
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);


// 修改状态栏颜色，4.4+生效
      /*  if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            setTranslucentStatus();
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.top_bar_bgColor);//通知栏所需颜色*/
        this.setTheme(R.style.BrowserThemeDefault);
        mQueue =  AppContextApplication.getInstance().getmRequestQueue();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity从堆栈中移除
        AppContextApplication.getInstance().finishActivity(this);
    }

    protected void setTranslucentStatus() {
        Window window = getWindow();
        // Translucent status bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        // Translucent navigation bar
        window.setFlags(
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION,
                WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
    }
}
