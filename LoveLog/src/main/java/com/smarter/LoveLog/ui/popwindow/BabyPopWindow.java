package com.smarter.LoveLog.ui.popwindow;

/**
 * Created by Administrator on 2015/12/11.
 */

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.NetworkImageView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.activity.InvitationAllPinglunActivity;
import com.smarter.LoveLog.activity.MakeOutOrderActivity;
import com.smarter.LoveLog.adapter.ImagePagerAdapter;
import com.smarter.LoveLog.db.AppContextApplication;
import com.smarter.LoveLog.db.Data;
import com.smarter.LoveLog.model.goods.GoodsData;

import butterknife.Bind;
import butterknife.ButterKnife;


/**
 * 宝贝详情界面的弹窗
 * @author http://yecaoly.taobao.com
 *
 */
@SuppressLint("CommitPrefEdits")
public class BabyPopWindow implements OnDismissListener, OnClickListener {
    @Bind(R.id.iv_adapter_grid_pic)
    NetworkImageView iv_adapter_grid_pic;
    @Bind(R.id.shopPrice)
    TextView shopPrice;
    @Bind(R.id.goodsNumber)
    TextView goodsNumber;



    RequestQueue mQueue;
    private TextView pop_num;
    private TextView pop_ok;
    private ImageView pop_del,pop_reduce,pop_add;
    private LinearLayout outside;
    private PopupWindow popupWindow;
    private OnItemClickListener listener;
    private final int ADDORREDUCE=1;
    private Context context;
    private RadioButton versionCheck1,versionCheck2,versionCheck3;
    private RadioGroup group;

    /**保存选择的类型的数据*/
    private String str_type="校园版";
    GoodsData goodsData=new GoodsData();

    public BabyPopWindow(Context context,GoodsData goodsData) {
        this.context=context;
        this.goodsData=goodsData;
        mQueue =  AppContextApplication.getInstance().getmRequestQueue();
        View view=LayoutInflater.from(context).inflate(R.layout.popwindow_activity_car_popwindow, null);
        ButterKnife.bind(this, view);

        initData();

        pop_add=(ImageView) view.findViewById(R.id.pop_add);
        pop_reduce=(ImageView) view.findViewById(R.id.pop_reduce);
        pop_num=(TextView) view.findViewById(R.id.pop_num);
        pop_ok=(TextView) view.findViewById(R.id.pop_ok);
        pop_del=(ImageView) view.findViewById(R.id.pop_del);
        outside= (LinearLayout) view.findViewById(R.id.outside);

        versionCheck1=(RadioButton) view.findViewById(R.id.versionCheck1);
        versionCheck2=(RadioButton) view.findViewById(R.id.versionCheck2);
        versionCheck3=(RadioButton) view.findViewById(R.id.versionCheck3);
        group= (RadioGroup) view.findViewById(R.id.group);


        pop_add.setOnClickListener(this);
        pop_reduce.setOnClickListener(this);
        pop_ok.setOnClickListener(this);
        pop_del.setOnClickListener(this);
        outside.setOnClickListener(this);

        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.versionCheck1:   str_type=versionCheck1.getText().toString(); break;
                    case R.id.versionCheck2:   str_type=versionCheck2.getText().toString();break;
                    case R.id.versionCheck3:   str_type=versionCheck3.getText().toString(); break;

                }

            }
        });



        popupWindow=new PopupWindow(view, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        //设置popwindow的动画效果
        popupWindow.setAnimationStyle(R.style.popWindow_anim_style);
        popupWindow.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        popupWindow.setOnDismissListener(this);// 当popWindow消失时的监听
    }

    private void initData() {
        //产品图片
        iv_adapter_grid_pic.setDefaultImageResId(R.drawable.loading_small);
        iv_adapter_grid_pic.setErrorImageResId(R.drawable.loading_small);
        String UserimageUrl="";
        if(goodsData.getImg().getThumb()!=null){
            UserimageUrl=goodsData.getImg().getThumb();
        }

        if(mQueue.getCache().get(UserimageUrl)==null){
            iv_adapter_grid_pic.startAnimation(ImagePagerAdapter.getInAlphaAnimation(2000));
        }
        iv_adapter_grid_pic.setImageUrl(UserimageUrl, AppContextApplication.getInstance().getmImageLoader());

        //产品卖价
        shopPrice.setText(goodsData.getShop_price());
        goodsNumber.setText("库存:"+goodsData.getGoods_number());



    }


    public interface OnItemClickListener{
        /** 设置点击确认按钮时监听接口 */
        public void onClickOKPop();
    }

    /**设置监听*/
    public void setOnItemClickListener(OnItemClickListener listener){
        this.listener=listener;
    }


    // 当popWindow消失时响应
    @Override
    public void onDismiss() {
        backgroundAlpha(context,1f);
    }
    /**
     * 设置添加屏幕的背景透明度
     * @param bgAlpha
     */
    public static  void backgroundAlpha(Context con,float bgAlpha)
    {

        WindowManager.LayoutParams lp =   ((Activity)con).getWindow().getAttributes();
        lp.alpha = bgAlpha; //0.0-1.0
        ((Activity)con).getWindow().setAttributes(lp);
    }

    /**弹窗显示的位置*/
    public void showAsDropDown(View parent){
        popupWindow.showAtLocation(parent, Gravity.BOTTOM, 0, 0);
        popupWindow.setFocusable(true);
        popupWindow.setOutsideTouchable(true);
        popupWindow.update();
    }

    /**消除弹窗*/
    public void dissmiss(){
        popupWindow.dismiss();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.pop_add:
                if (!pop_num.getText().toString().equals(""+goodsData.getGoods_number())) {

                    String num_add=Integer.valueOf(pop_num.getText().toString())+ADDORREDUCE+"";
                    pop_num.setText(num_add);
                }else {
                    Toast.makeText(context, "不能超过最大产品数量", Toast.LENGTH_SHORT).show();
                }

                break;
            case R.id.pop_reduce:
                if (!pop_num.getText().toString().equals("1")) {
                    String num_reduce=Integer.valueOf(pop_num.getText().toString())-ADDORREDUCE+"";
                    pop_num.setText(num_reduce);
                }else {
                    Toast.makeText(context, "购买数量不能低于1件", Toast.LENGTH_SHORT).show();
                }
                break;
            case R.id.pop_del:
                listener.onClickOKPop();
                dissmiss();

                break;
            case R.id.pop_ok:
                listener.onClickOKPop();
                //挑战到所有评论界面//
                //
                Intent intent = new Intent(context, MakeOutOrderActivity.class);
                Bundle bundle = new Bundle();
                bundle.putSerializable("goods",goodsData);
                intent.putExtras(bundle);
                context.startActivity(intent);

                   /* HashMap<String, Object> allHashMap=new HashMap<String,Object>();

                    allHashMap.put("type",str_type);
                    allHashMap.put("num",pop_num.getText().toString());
                    allHashMap.put("id",Data.arrayList_cart_id+=1);

                    Data.arrayList_cart.add(allHashMap);
                    setSaveData();
                    dissmiss();*/


                break;
            case R.id.outside:
                listener.onClickOKPop();
                dissmiss();
                break;
            default:
                break;
        }
    }
    /**保存购物车的数据*/
    private void setSaveData(){
        SharedPreferences sp=context.getSharedPreferences("SAVE_CART", Context.MODE_PRIVATE);
        Editor editor=sp.edit();
        editor.putInt("ArrayCart_size", Data.arrayList_cart.size());
        for (int i = 0; i < Data.arrayList_cart.size(); i++) {
            editor.remove("ArrayCart_type_"+i);
            editor.remove("ArrayCart_num_"+i);
            editor.putString("ArrayCart_type_"+i, Data.arrayList_cart.get(i).get("type").toString());
            editor.putString("ArrayCart_num_"+i, Data.arrayList_cart.get(i).get("num").toString());

        }



    }

}

