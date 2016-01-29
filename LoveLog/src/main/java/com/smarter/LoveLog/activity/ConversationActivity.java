package com.smarter.LoveLog.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.smarter.LoveLog.R;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2016/1/29.
 */
public class ConversationActivity extends BaseFragmentActivity implements  View.OnClickListener{

    @Bind(R.id.back_but)
    ImageView back_but;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //唯一有用的代码，加载一个 layout
        setContentView(R.layout.conversation);
        ButterKnife.bind(this);

        //继承的是ActionBarActivity，直接调用 自带的 Actionbar，下面是Actionbar 的配置，如果不用可忽略…
       /* getSupportActionBar().setTitle("聊天");
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.mipmap.ic_launcher);*/

        setListen();
    }

    private void setListen() {
        back_but.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case  R.id.back_but:
                finish();
                break;
        }
    }
}
