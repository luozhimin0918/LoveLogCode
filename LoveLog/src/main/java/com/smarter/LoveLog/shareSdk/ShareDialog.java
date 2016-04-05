package com.smarter.LoveLog.shareSdk;

/**
 * Created by Administrator on 2016/4/1.
 */
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.SimpleAdapter;

import com.smarter.LoveLog.R;
import com.smarter.LoveLog.model.community.PromotePostsData;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.system.email.Email;
import cn.sharesdk.system.text.ShortMessage;
import cn.sharesdk.tencent.qq.QQ;
import cn.sharesdk.tencent.qzone.QZone;
import cn.sharesdk.wechat.friends.Wechat;
import cn.sharesdk.wechat.moments.WechatMoments;

public class ShareDialog {

    private AlertDialog dialog;
    private GridView gridView;
    private RelativeLayout cancelButton;
    private SimpleAdapter saImageItems;
    private int[] image = {R.mipmap.share_sina, R.mipmap.share_wechat, R.mipmap.share_wechat_moments, R.mipmap.share_qq, R.mipmap.share_qq, R.mipmap.share_qq, R.mipmap.share_qq};
    private String[] name = {"微博", "微信好友", "朋友圈", "QQ","QQ空间","邮件","信息"};

    public ShareDialog(Context context) {

        dialog = new AlertDialog.Builder(context).create();
        dialog.show();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM); // 非常重要：设置对话框弹出的位置
        window.setContentView(R.layout.share_dialog);
        gridView = (GridView) window.findViewById(R.id.share_gridView);
        cancelButton = (RelativeLayout) window.findViewById(R.id.share_cancel);
        List<HashMap<String, Object>> shareList = new ArrayList<HashMap<String, Object>>();
        for (int i = 0; i < image.length; i++) {
            HashMap<String, Object> map = new HashMap<String, Object>();
            map.put("ItemImage", image[i]);//添加图像资源的ID
            map.put("ItemText", name[i]);//按序号做ItemText
            shareList.add(map);
        }

        saImageItems = new SimpleAdapter(context, shareList, R.layout.share_item, new String[]{"ItemImage", "ItemText"}, new int[]{R.id.imageView1, R.id.textView1});
        gridView.setAdapter(saImageItems);
    }

    public void setCancelButtonOnClickListener(OnClickListener Listener) {
        cancelButton.setOnClickListener(Listener);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        gridView.setOnItemClickListener(listener);
    }


    /**
     * 关闭对话框
     */
    public void dismiss() {
        dialog.dismiss();
    }


    /**
     * 分享
     * @param item
     * @param promotePostsData
     * @param mActivity
     */
    public static void showShare( HashMap<String, Object> item, PromotePostsData promotePostsData,PlatformActionListener mActivity){
        if (item.get("ItemText").equals("微信好友")) {
//                    Toast.makeText(mContext, "您点中了" + item.get("ItemText"), Toast.LENGTH_LONG).show();

            //2、设置分享内容
            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE);//非常重要：一定要设置分享属性
            sp.setTitle(promotePostsData.getShare().getTitle());  //分享标题
            sp.setText(promotePostsData.getShare().getDesc());   //分享文本
            sp.setImageUrl(promotePostsData.getShare().getThumb());//网络图片rul
            sp.setUrl(promotePostsData.getShare().getUrl());   //网友点进链接后，可以看到分享的详情

            //3、非常重要：获取平台对象
            Platform wechat = ShareSDK.getPlatform(Wechat.NAME);
            wechat.setPlatformActionListener(mActivity); // 设置分享事件回调
            // 执行分享
            wechat.share(sp);


        } else if (item.get("ItemText").equals("朋友圈")) {
            //2、设置分享内容
            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setShareType(Platform.SHARE_WEBPAGE); //非常重要：一定要设置分享属性
            sp.setTitle(promotePostsData.getShare().getTitle());  //分享标题
            sp.setText(promotePostsData.getShare().getDesc());   //分享文本
            sp.setImageUrl(promotePostsData.getShare().getThumb());//网络图片rul
            sp.setUrl(promotePostsData.getShare().getUrl());   //网友点进链接后，可以看到分享的详情

            //3、非常重要：获取平台对象
            Platform wechatMoments = ShareSDK.getPlatform(WechatMoments.NAME);
            wechatMoments.setPlatformActionListener(mActivity); // 设置分享事件回调
            // 执行分享
            wechatMoments.share(sp);

        } else if (item.get("ItemText").equals("QQ")) {
            //2、设置分享内容
            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setTitle(promotePostsData.getShare().getTitle());
            sp.setText(promotePostsData.getShare().getDesc());
            sp.setImageUrl(promotePostsData.getShare().getThumb());//网络图片rul
            sp.setTitleUrl(promotePostsData.getShare().getUrl());  //网友点进链接后，可以看到分享的详情
            //3、非常重要：获取平台对象
            Platform qq = ShareSDK.getPlatform(QQ.NAME);
            qq.setPlatformActionListener(mActivity); // 设置分享事件回调
            // 执行分享
            qq.share(sp);

        }else if(item.get("ItemText").equals("QQ空间")){
            //2、设置分享内容
            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setTitle(promotePostsData.getShare().getTitle());
            sp.setText(promotePostsData.getShare().getDesc());
            sp.setImageUrl(promotePostsData.getShare().getThumb());//网络图片rul
            sp.setTitleUrl(promotePostsData.getShare().getUrl());  //网友点进链接后，可以看到分享的详情
            //3、非常重要：获取平台对象
            Platform qzone = ShareSDK.getPlatform(QZone.NAME);
            qzone.setPlatformActionListener(mActivity); // 设置分享事件回调
            // 执行分享
            qzone.share(sp);
        }else if(item.get("ItemText").equals("邮件")){
            //2、设置分享内容
            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setTitle(promotePostsData.getShare().getTitle());
            sp.setText(promotePostsData.getShare().getDesc());
            sp.setImageUrl(promotePostsData.getShare().getThumb());//网络图片rul
            sp.setTitleUrl(promotePostsData.getShare().getUrl());  //网友点进链接后，可以看到分享的详情
            //3、非常重要：获取平台对象
            Platform email = ShareSDK.getPlatform(Email.NAME);
            email.setPlatformActionListener(mActivity); // 设置分享事件回调
            // 执行分享
            email.share(sp);
        }else if(item.get("ItemText").equals("信息")){
            //2、设置分享内容
            Platform.ShareParams sp = new Platform.ShareParams();
            sp.setTitle(promotePostsData.getShare().getTitle());
            sp.setText(promotePostsData.getShare().getDesc());
            sp.setImageUrl(promotePostsData.getShare().getThumb());//网络图片rul
            sp.setTitleUrl(promotePostsData.getShare().getUrl());  //网友点进链接后，可以看到分享的详情
            //3、非常重要：获取平台对象
            Platform message = ShareSDK.getPlatform(ShortMessage.NAME);
            message.setPlatformActionListener(mActivity); // 设置分享事件回调
            // 执行分享
            message.share(sp);
        }

    }

}
