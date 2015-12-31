package com.smarter.LoveLog.adapter;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.smarter.LoveLog.R;

/**
 * Created by Administrator on 2015/12/22.
 */
public class RecycleAddressAdapter extends RecyclerView.Adapter<RecycleAddressAdapter.ViewHolder>{





    // 数据集
    private String[] mName;
    private String[] mPhoneNum;
    private Boolean[] isdefuals;
    public RecycleAddressAdapter(String[] names, String[] phones,Boolean[] isdefual) {
        super();
        mName = names;
        mPhoneNum=phones;
        isdefuals=isdefual;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        // 创建一个View，简单起见直接使用系统提供的布局，就是一个TextView
        View view = View.inflate(viewGroup.getContext(), R.layout.adapter_activity_address_manage_item, null);
        // 创建一个ViewHolder
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, final int i) {
        // 绑定数据到ViewHolder上
        viewHolder.nameTextView.setText(mName[i]);
        viewHolder.phoneTextview.setText(mPhoneNum[i]);
        if(isdefuals[i]){
            viewHolder.isImage.setBackgroundResource(R.mipmap.choiceon);
        }else{
            viewHolder.isImage.setBackgroundResource(R.mipmap.choice);

        }

        viewHolder.idDefaul.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                        for(int j=0;j<isdefuals.length;j++){
                            if(i==j){
                                isdefuals[j]=true;
                            }else {
                                isdefuals[j]=false;
                            }
                        }

                OnCheckDefaultListener.oncheckOK(isdefuals);
            }
        });

    }


    //回调开始
    public interface OnCheckDefaultListener {
        void oncheckOK(Boolean[] ischeckArray);
    }
    private OnCheckDefaultListener OnCheckDefaultListener;

    public void setOnCheckDefaultListener(OnCheckDefaultListener onCheckDefault) {
        this.OnCheckDefaultListener=onCheckDefault;
    }
    //结束
    @Override
    public int getItemCount() {
        return mName.length;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView nameTextView,phoneTextview;
        public LinearLayout idDefaul;
        public ImageView isImage;

        public ViewHolder(View itemView) {
            super(itemView);
            nameTextView = (TextView) itemView.findViewById(R.id.nameTextView);
            phoneTextview = (TextView) itemView.findViewById(R.id.phoneTextview);
            idDefaul= (LinearLayout) itemView.findViewById(R.id.idDefaul);
            isImage= (ImageView) itemView.findViewById(R.id.isImage);

        }
    }
}