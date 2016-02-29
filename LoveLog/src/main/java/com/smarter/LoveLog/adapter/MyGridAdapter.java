package com.smarter.LoveLog.adapter;

/**
 * Created by Administrator on 2015/12/22.
 */
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.smarter.LoveLog.R;
import com.smarter.LoveLog.activity.AddressManageActivity;
import com.smarter.LoveLog.activity.FeedbackActivity;
import com.smarter.LoveLog.activity.ItegralSelfActivity;
import com.smarter.LoveLog.activity.LoginActivity;
import com.smarter.LoveLog.activity.MessageCenterActivity;
import com.smarter.LoveLog.activity.MyCommentActivity;
import com.smarter.LoveLog.activity.MyInvitationActivity;
import com.smarter.LoveLog.activity.MyNotificationActivity;
import com.smarter.LoveLog.activity.MyRedPacketActivity;
import com.smarter.LoveLog.activity.PersonalDataActivity;
import com.smarter.LoveLog.activity.WalletSelfActivity;
import com.smarter.LoveLog.db.SharedPreferences;

import io.rong.imkit.RongIM;
import io.rong.imlib.model.Conversation;

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
    public View getView(final  int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(mContext).inflate(
                    R.layout.grid_item, parent, false);
        }
        TextView tv = BaseViewHolder.get(convertView, R.id.tv_item);
        ImageView iv = BaseViewHolder.get(convertView, R.id.iv_item);
        iv.setBackgroundResource(imgs[position]);

        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Boolean islogin=   SharedPreferences.getInstance().getBoolean("islogin", false);

                if( islogin){




                            if(position==0){//帖子
                                //
                                Intent intent2 = new Intent(mContext, MyInvitationActivity.class);
                              /*  Bundle bundle = new Bundle();
                                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                                intent.putExtras(bundle);*/
                                mContext.startActivity(intent2);
                            }
                            if(position==1){//评论
                                //
                                Intent intent2 = new Intent(mContext, MyCommentActivity.class);
                              /*  Bundle bundle = new Bundle();
                                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                                intent.putExtras(bundle);*/
                                mContext.startActivity(intent2);
                            }
                            if(position==img_text.length-8){
                                //
                                Intent intent2 = new Intent(mContext, WalletSelfActivity.class);
                              /*  Bundle bundle = new Bundle();
                                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                                intent.putExtras(bundle);*/
                                mContext.startActivity(intent2);
                            }
                            if(position==img_text.length-7){
                                //
                                Intent intent2 = new Intent(mContext, MyRedPacketActivity.class);
                              /*  Bundle bundle = new Bundle();
                                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                                intent.putExtras(bundle);*/
                                mContext.startActivity(intent2);
                            }


                            if(position==img_text.length-6){
                                //
                                Intent intent2 = new Intent(mContext, ItegralSelfActivity.class);
                              /*  Bundle bundle = new Bundle();
                                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                                intent.putExtras(bundle);*/
                                mContext.startActivity(intent2);
                            }


                            if(position==img_text.length-5){
                                //

                                //挑战到地址管理界面
                                Intent intent2 = new Intent(mContext, AddressManageActivity.class);
                              /*  Bundle bundle = new Bundle();
                                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                                intent.putExtras(bundle);*/
                                mContext.startActivity(intent2);
                            }
                            if(position==img_text.length-4){
                                //反馈
                                Intent intent2 = new Intent(mContext, MessageCenterActivity.class);
                              /*  Bundle bundle = new Bundle();
                                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                                intent.putExtras(bundle);*/
                                mContext.startActivity(intent2);
                            }

                            if(position==img_text.length-3){
                                //反馈
                                Intent intent2 = new Intent(mContext, MyNotificationActivity.class);
                              /*  Bundle bundle = new Bundle();
                                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                                intent.putExtras(bundle);*/
                                mContext.startActivity(intent2);
                            }
                            if(position==img_text.length-2){
                                /**
                                 * 启动客服聊天界面。
                                 *
                                 * @param context          应用上下文。
                                 * @param conversationType 开启会话类型。
                                 * @param targetId         客服 Id。
                                 * @param title            客服标题。
                                 */
                                RongIM.getInstance().startConversation(mContext, Conversation.ConversationType.APP_PUBLIC_SERVICE, "KEFU145033288579386", "客服");


                            }




                            if(position==img_text.length-1){
                                //反馈
                                Intent intent2 = new Intent(mContext, FeedbackActivity.class);
                              /*  Bundle bundle = new Bundle();
                                bundle.putSerializable("PromotePostsData", (Serializable) pp);
                                intent.putExtras(bundle);*/
                                mContext.startActivity(intent2);
                            }



                }else {
                    //登录
                    Intent intent = new Intent(mContext, LoginActivity.class);
                  /*  Bundle bundle = new Bundle();
                    bundle.putSerializable("PromotePostsData", (Serializable) pp);
                    intent.putExtras(bundle);*/
                    mContext.startActivity(intent);
                }


            }
        });

        tv.setText(img_text[position]);
        return convertView;
    }

}

