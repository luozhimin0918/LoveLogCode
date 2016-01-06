package com.smarter.LoveLog.ui.popwindow;

/**
 * Created by Administrator on 2015/12/11.
 */

import java.util.HashMap;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.widget.PopupWindow.OnDismissListener;
import android.widget.TextView;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.db.Data;


/**
 * 宝贝详情界面的弹窗
 * @author http://yecaoly.taobao.com
 *
 */
@SuppressLint("CommitPrefEdits")
public class BabyPopWindow implements OnDismissListener, OnClickListener {
    private TextView pop_num,pop_ok;
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


    public BabyPopWindow(Context context) {
        this.context=context;
        View view=LayoutInflater.from(context).inflate(R.layout.popwindow_activity_car_popwindow, null);

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
                if (!pop_num.getText().toString().equals("750")) {

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

                    HashMap<String, Object> allHashMap=new HashMap<String,Object>();

                    allHashMap.put("type",str_type);
                    allHashMap.put("num",pop_num.getText().toString());
                    allHashMap.put("id",Data.arrayList_cart_id+=1);

                    Data.arrayList_cart.add(allHashMap);
                    setSaveData();
                    dissmiss();


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

