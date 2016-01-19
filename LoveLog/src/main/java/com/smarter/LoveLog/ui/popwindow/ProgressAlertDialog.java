package com.smarter.LoveLog.ui.popwindow;

import android.content.Context;
import android.view.Window;
import com.smarter.LoveLog.R;

/**
 * Created by Administrator on 2016/1/19.
 */
public class ProgressAlertDialog {

    Context context;
    android.app.AlertDialog ad;

    public ProgressAlertDialog(Context context) {
        // TODO Auto-generated constructor stub
        this.context=context;
        ad=new android.app.AlertDialog.Builder(context).create();

        //关键在下面的两行,使用window.setContentView,替换整个对话框窗口的布局
        Window window = ad.getWindow();
        window.setContentView(R.layout.progress_view_ing);

    }

    /**
     * 关闭对话框
     */
    public void dismiss() {
        ad.dismiss();
    }

    public  void  show(){
        ad.show();
    }

}
