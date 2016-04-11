package com.smarter.LoveLog;

import android.os.Bundle;

import com.umeng.socialize.media.WBShareCallBackActivity;

/**
 * Created by Administrator on 2016/4/11.
 */
public class WBShareActivity extends WBShareCallBackActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
        }catch (Exception e){
            finish();
        }
    }
}
