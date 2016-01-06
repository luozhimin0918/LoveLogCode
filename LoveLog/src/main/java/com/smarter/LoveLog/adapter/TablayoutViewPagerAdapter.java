package com.smarter.LoveLog.adapter;

/**
 * Created by Administrator on 2015/12/30.
 */

import android.app.Activity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.smarter.LoveLog.R;

import java.util.List;

public class TablayoutViewPagerAdapter extends FragmentPagerAdapter {

    private List<Fragment> list_fragment;                         //fragment列表
    private List<String> list_Title;                              //tab名的列表
    private Context mContext;


    public TablayoutViewPagerAdapter(Context mContext,FragmentManager fm,List<Fragment> list_fragment,List<String> list_Title) {
        super(fm);
        this.list_fragment = list_fragment;
        this.list_Title = list_Title;
        this.mContext=mContext;
    }
    public TablayoutViewPagerAdapter(FragmentManager fm,List<Fragment> list_fragment,List<String> list_Title) {
        super(fm);
        this.list_fragment = list_fragment;
        this.list_Title = list_Title;
    }

    @Override
    public Fragment getItem(int position) {
        return list_fragment.get(position);
    }

    @Override
    public int getCount() {
        return list_Title.size();
    }

    //此方法用来显示tab上的名字
    @Override
    public CharSequence getPageTitle(int position) {

        return list_Title.get(position % list_Title.size());
    }

    public View getTabView(int position) {//待用
        View v = LayoutInflater.from(mContext).inflate(R.layout.line, null);
       /* TextView tv = (TextView) v.findViewById(R.id.textView);
        tv.setText(tabTitles[position]);
        ImageView img = (ImageView) v.findViewById(R.id.imageView);
        //img.setImageResource(imageResId[position]);*/
        return v;
    }
}

