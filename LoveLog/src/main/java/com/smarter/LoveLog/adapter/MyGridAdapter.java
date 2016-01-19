package com.smarter.LoveLog.adapter;

/**
 * Created by Administrator on 2015/12/22.
 */
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarter.LoveLog.R;

/**
 * @Description:gridview的Adapter
 * @author http://blog.csdn.net/finddreams
 */
public class MyGridAdapter extends BaseAdapter {
    private Context mContext;

    public String[] img_text = { "帖子", "评论", "收藏", "关注", "钱包", "红包",
            "积分", "地址", "消息","通知","咨询","反馈" };
    public int[] imgs = { R.mipmap.grid_icon01, R.mipmap.grid_icon02,
            R.mipmap.grid_icon03,R.mipmap.grid_icon04,
            R.mipmap.grid_icon05,R.mipmap.grid_icon06,
            R.mipmap.grid_icon07, R.mipmap.grid_icon08, R.mipmap.grid_icon09,R.mipmap.grid_icon10,
            R.mipmap.grid_icon11,R.mipmap.grid_icon12 };

    public MyGridAdapter(Context mContext) {
        super();
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return img_text.length;
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.grid_item, parent, false);
        }
        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
        iv.setBackgroundResource(imgs[position]);

        tv.setText(img_text[position]);
        return convertView;
    }

}

