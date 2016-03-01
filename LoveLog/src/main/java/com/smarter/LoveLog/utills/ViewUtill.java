package com.smarter.LoveLog.utills;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.smarter.LoveLog.activity.LoginActivity;
import com.smarter.LoveLog.ui.popwindow.AlertDialog;
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

    public  static void   ShowAlertDialog(final  Context mContext){
        new AlertDialog(mContext).builder().setTitle("提示")
                .setMsg("您未登录，请登录")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //登录
                        Intent intent = new Intent(mContext, LoginActivity.class);
                              /*  Bundle bundle = new Bundle();
                                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                                intent.putExtras(bundle);*/
                        mContext.startActivity(intent);

                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();
    }
}
