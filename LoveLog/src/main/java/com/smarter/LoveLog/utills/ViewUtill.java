package com.smarter.LoveLog.utills;

import android.content.Context;

import com.smarter.LoveLog.ui.popwindow.ProgressAlertDialog;

/**
 * Created by Administrator on 2016/1/19.
 */
public class ViewUtill {

    //加载progress提示
    public static ProgressAlertDialog initProgress(Context context) {
        ProgressAlertDialog progressAlertDialog = new ProgressAlertDialog(context);
        return  progressAlertDialog;
    }
}
