package com.smarter.LoveLog.adapter;

/**
 * Created by Administrator on 2015/12/1.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarter.LoveLog.R;

public class Adapter_GridView extends BaseAdapter {
    private Context context;
    private int[] data;
    private String[] titleData;
    public Adapter_GridView(Context context,int[] data,String[] titleData){

        this.context=context;
        this.data=data;
        this.titleData=titleData;
    }

    @Override
    public int getCount() {
        return data.length;
    }

    @Override
    public Object getItem(int arg0) {
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        return 0;
    }

    @Override
    public View getView(int position, View currentView, ViewGroup arg2) {
        HolderView holderView=null;
        if (currentView==null) {
            holderView=new HolderView();
            currentView=LayoutInflater.from(context).inflate(R.layout.adapter_grid_home, null);
            holderView.iv_pic=(ImageView) currentView.findViewById(R.id.iv_adapter_grid_pic);
            holderView.titleData= (TextView) currentView.findViewById(R.id.titleData);
            currentView.setTag(holderView);
        }else {
            holderView=(HolderView) currentView.getTag();
        }


        holderView.iv_pic.setImageResource(data[position]);
        holderView.titleData.setText(titleData[position]);


        return currentView;
    }


    public class HolderView {

        private ImageView iv_pic;
        private TextView titleData;

    }

}

