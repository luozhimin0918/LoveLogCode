package com.smarter.LoveLog.ui.McoySnapPageLayout;

/**
 * Created by Administrator on 2015/12/16.
 */
import android.content.Context;
import android.view.View;

import com.smarter.LoveLog.R;

public class McoyProductContentPage implements McoySnapPageLayout.McoySnapPage {

    private Context context;

    private View rootView = null;
    private McoyScrollView mcoyScrollView = null;

    public McoyProductContentPage(Context context,View rootView) {
        this.context = context;
        this.rootView = rootView;

        mcoyScrollView = (McoyScrollView) this.rootView
                .findViewById(R.id.productDetail_scrollview);
    }

    @Override
    public View getRootView() {
        return rootView;
    }

    @Override
    public boolean isAtTop() {

        /*int scrollY = mcoyScrollView.getScrollY();
        int height = mcoyScrollView.getHeight();
        int scrollViewMeasuredHeight = mcoyScrollView.getChildAt(0).getMeasuredHeight();

        if ((scrollY + height) >= scrollViewMeasuredHeight) {
            return true;
        }*/
        if(mcoyScrollView.getScrollY()==0){
            // 到顶部了
            //TODO
            return true;
        }
        return false;

    }

    @Override
    public boolean isAtBottom() {
        return false;
    }

}

