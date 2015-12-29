package com.smarter.LoveLog.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.RecyclePinglunAdapter;
import com.smarter.LoveLog.ui.McoySnapPageLayout.McoyScrollView;
import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class InvitationDeatilActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "InvitationDeatilActivity";
    Context  mContext;



    @Bind(R.id.imageTopHeader)
    ImageView imageTopHeader;
    @Bind(R.id.invitation_Detail_scrollview)
    McoyScrollView invitation_Detail_scrollview;
    @Bind(R.id.alphaBar)
    LinearLayout alphaBar;
    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;






    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_invitation_deatil_view);
        ButterKnife.bind(this);
        mContext=this;

        getDataIntent();
        intData();
        setListen();

    }

    private void setListen() {
        invitation_Detail_scrollview.setOnJDScrollListener(new McoyScrollView.OnJDScrollListener() {
            @Override
            public void onScroll(int x, int y, int oldx, int oldy) {
                if (imageTopHeader != null && imageTopHeader.getHeight() > 0) {
                    int height = imageTopHeader.getHeight();
                    if (y < height) {
                        int alpha = (int) (new Float(y) / new Float(height)
                                * 250);
                        Log.d("YJL", "" + alpha);
                        alphaBar.getBackground().setAlpha(alpha);
                    } else {
                        alphaBar.getBackground().setAlpha(255);
                    }
                }
            }
        });
    }

    private void intData() {
        alphaBar.getBackground().setAlpha(0);

        initRecycleViewVertical();//评论刷新

    }

    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            String  str = intent.getStringExtra("ObjectData");
           // Toast.makeText(this,str+"",Toast.LENGTH_LONG).show();
        }


    }

    @Override
    public void onClick(View v) {
         switch (v.getId()){

         }
    }



    public void initRecycleViewVertical(){

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        // 默认是Vertical，可以不写
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);

        // 创建数据集
        String[] dataset = new String[]{"用户名/昵称","绑定手机号","性别","会员等级","修改密码","收货地址"};
        String[] dataValue=new String[]{"美羊羊","15083806689","男","V0初级会员","",""};
        // 创建Adapter，并指定数据集
        RecyclePinglunAdapter adapter = new RecyclePinglunAdapter(dataset,dataValue);
        // 设置Adapter
        recyclerView.setAdapter(adapter);
        recyclerView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                v.getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

    }


}
