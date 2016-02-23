package com.smarter.LoveLog.fragment;

/**
 * Created by Administrator on 2016/2/22.
 */
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.smarter.LoveLog.R;
import com.smarter.LoveLog.ui.scaleView.ScaleView;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;


/**
 * 显示大图的实现，并且可以放大缩小
 * @author http://yecaoly.taobao.com
 *
 */
@SuppressLint("ValidFragment")
public class PictrueFragment extends Fragment {

    private String urlString;
    Bitmap bitmapDrawable;
    Activity mContext;
    @SuppressLint("ValidFragment")
    public PictrueFragment(String urlString,Activity mContext){
        this.mContext=mContext;
        this.urlString=urlString;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view=LayoutInflater.from(getActivity()).inflate(R.layout.scale_pic_item, null);
        initView(view);
        return view;
    }
    ScaleView imageView;
    LinearLayout linearBar;
    private void initView(View view){
        imageView=(ScaleView) view.findViewById(R.id.scale_pic_item);
        linearBar= (LinearLayout) view.findViewById(R.id.linearBar);
        linearBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mContext.finish();
            }
        });
        Runnable downloadRun = new Runnable(){

            @Override
            public void run() {
                // TODO Auto-generated method stub
                try {
                    if(urlString!=null&&!urlString.equals("")){
                        bitmapDrawable =((BitmapDrawable)loadImageFromUrl(urlString)).getBitmap();



                    }



                    if(bitmapDrawable!=null){
                        handler.sendEmptyMessageDelayed(SHOW_VIEW, 100);
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        new Thread(downloadRun).start();
    }


    public  Drawable loadImageFromUrl(String url) throws IOException {

        URL m = new URL(url);
        InputStream i = (InputStream) m.getContent();
        Drawable d = Drawable.createFromStream(i, "src");
        return d;
    }


    private final int SHOW_VIEW = 0;
    Handler handler = new Handler(new Handler.Callback() {

        public boolean handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_VIEW:
                    imageView.setImageBitmap(bitmapDrawable);
                    break;

                default:
                    break;
            }
            return false;
        }
    });


    public Bitmap getBitmapDrawable() {
        return bitmapDrawable;
    }

    public void setBitmapDrawable(Bitmap bitmapDrawable) {
        this.bitmapDrawable = bitmapDrawable;
    }
}

