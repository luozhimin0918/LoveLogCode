package com.smarter.LoveLog.activity;

/**
 * Created by Administrator on 2016/1/27.
 */

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Handler.Callback;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.smarter.LoveLog.R;
import com.smarter.LoveLog.fragment.PictrueFragment;
import com.smarter.LoveLog.ui.HackyViewPager;
import com.smarter.LoveLog.ui.ZoomableImageView;
import com.smarter.LoveLog.ui.popwindow.ActionSheetDialog;
import com.smarter.LoveLog.utills.DeviceUtil;
import android.support.v4.app.Fragment;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ShowAnoWebImageActivity extends BaseFragmentActivity {

    private HackyViewPager viewPager;
    private String[] resId=new String[]{};
    /**得到上一个界面点击图片的位置*/
    private int position=0;
    public  String[] urlStrArr=new String[]{};
    List<String> imgList = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_big_pictrue_a);

        if(getIntent()!=null){
            Intent intent=getIntent();
            urlStrArr=intent.getStringArrayExtra("images");

           if(urlStrArr!=null&&urlStrArr.length>0){
               Collections.addAll(imgList, urlStrArr);


               String lastImg=imgList.get(imgList.size()-1);
                imgList.remove(imgList.size()-1);


               for(int j=0;j<imgList.size();j++){
                   if(imgList.get(j).equals(lastImg)){
                       position=j;
                   }
               }

               initViewPager();
           }

        }


    }

    private void initViewPager(){

        viewPager = (HackyViewPager) findViewById(R.id.viewPager_show_bigPic);
        ViewPagerAdapter adapter=new ViewPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        //跳转到第几个界面
        viewPager.setCurrentItem(position);

    }

    public class ViewPagerAdapter extends FragmentStatePagerAdapter {

        public ViewPagerAdapter(FragmentManager fm) {
            super(fm);
        }



        @Override
        public Fragment getItem(int position) {
            String show_resId=imgList.get(position);
            return new PictrueFragment(show_resId);
        }

        @Override
        public int getCount() {
            return imgList.size();
        }


    }
}
