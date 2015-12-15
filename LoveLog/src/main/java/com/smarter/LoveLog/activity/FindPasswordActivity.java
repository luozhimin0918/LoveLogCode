package com.smarter.LoveLog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import butterknife.ButterKnife;
import com.smarter.LoveLog.R;

/**
 * Created by Administrator on 2015/11/30.
 */
public class FindPasswordActivity extends BaseFragmentActivity implements View.OnClickListener{

    /*@Bind(R.id.pager)
    AutoLoopViewPager pager;*/


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_findpword_view);
        ButterKnife.bind(this);
        getDataIntent();

        intData();
        setListen();

    }

    private void setListen() {
    }

    private void intData() {

    }

    private void getDataIntent() {
        Intent intent = getIntent();
        if(intent!=null){
            String  str = intent.getStringExtra("ObjectData");
            Toast.makeText(this,str+"",Toast.LENGTH_LONG).show();
        }


    }


    @Override
    public void onClick(View v) {
         switch (v.getId()){
           /*  case R.id.buy_now:
                 break;*/

         }
    }



}
