package com.smarter.LoveLog.db;

import android.app.Activity;
import android.app.ActivityManager;
import android.app.Application;
import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.Volley;
import com.smarter.LoveLog.model.loginData.LoginDataActi;
import com.umeng.socialize.PlatformConfig;

import java.util.LinkedList;

import io.rong.imkit.RongIM;

/**
 * Created by Administrator on 2016/1/4.
 */
public class AppContextApplication extends Application {
    private static AppContextApplication app;
    private static LinkedList<Activity> activityStack;

    private ImageLoader mImageLoader;
    private RequestQueue mRequestQueue;



     //
    public AppContextApplication() {
        app = this;

    }

    public static synchronized AppContextApplication getInstance() {
        if (app == null) {
            app = new AppContextApplication();
        }
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();


        //创建RequestQueue，可发送异步请求
        mRequestQueue= Volley.newRequestQueue(this);
        //创建ImageLoader,用于将图片存入缓存和从缓存中取出图片
        mImageLoader=new ImageLoader(mRequestQueue,new BitmapCache());


        /**
         * OnCreate 会被多个进程重入，这段保护代码，确保只有您需要使用 RongIM 的进程和 Push 进程执行了 init。
         * io.rong.push 为融云 push 进程名称，不可修改。
         */
//        if (getApplicationInfo().packageName.equals(getCurProcessName(getApplicationContext())) ||
//                "io.rong.push".equals(getCurProcessName(getApplicationContext()))) {

            /**
             * IMKit SDK调用第一步 初始化
             */
            RongIM.init(this);
//        }


        /**
         * 各个平台的配置，建议放在全局Application或者程序入口
         */
        PlatformConfig.setWeixin("wx33496a68219a3769", "9928dececd0520b0cb6cde3aac4f40d1");
        PlatformConfig.setQQZone("1105000062", "uK9qmMOsoKeudlRv");
        PlatformConfig.setSinaWeibo("3044356740", "8c7505d85cfb487bb2c0b51988fe97ac");



    }

    /**
     * 添加Activity到堆栈
     */
    public void addActivity(Activity activity) {
        if (activityStack == null) {
            activityStack = new LinkedList<Activity>();
        }
        activityStack.add(activity);
    }

    /**
     * 获取当前Activity（堆栈中最后一个压入的）
     */
    public Activity currentActivity() {
        Activity activity = activityStack.getLast();
        return activity;
    }

    /**
     * 结束当前Activity（堆栈中最后一个压入的）
     */
    public void finishActivity() {
        Activity activity = activityStack.getLast();
        finishActivity(activity);
    }

    /**
     * 结束指定的Activity
     */
    public void finishActivity(Activity activity) {
        if (activity != null) {
            if (activityStack != null) {
                activityStack.remove(activity);
            }
            activity.finish();
            activity = null;
        }
    }

    /**
     * 结束指定类名的Activity
     */
    public void finishActivity(Class<?> cls) {
        for (Activity activity : activityStack) {
            if (activity.getClass().equals(cls)) {
                finishActivity(activity);
            }
        }
    }

    /**
     * 结束所有Activity
     */
    public void finishAllActivity() {
        for (int i = 0, size = activityStack.size(); i < size; i++) {
            if (null != activityStack.get(i)) {
                activityStack.get(i).finish();
            }
        }
        activityStack.clear();
    }

    /**
     * 退出应用程序
     */
  /*  public void AppExit(Context context) {
        try {
            finishAllActivity();
            ActivityManager activityMgr = (ActivityManager) context
                    .getSystemService(Context.ACTIVITY_SERVICE);
            activityMgr.restartPackage(context.getPackageName());
            System.exit(0);
        } catch (Exception e) {
        }
    }*/

    public int ActivityStackSize() {
        return activityStack == null ? 0 : activityStack.size();
    }

    public ImageLoader getmImageLoader() {
        return mImageLoader;
    }

    public void setmImageLoader(ImageLoader mImageLoader) {
        this.mImageLoader = mImageLoader;
    }

    public RequestQueue getmRequestQueue() {
        return mRequestQueue;
    }

    public void setmRequestQueue(RequestQueue mRequestQueue) {
        this.mRequestQueue = mRequestQueue;
    }





    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context
                .getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager
                .getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return "";
    }
}
