package com.smarter.LoveLog.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewParent;

import com.nshmura.recyclertablayout.RecyclerTabLayout;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.DemoYearsPagerAdapter;
import com.smarter.LoveLog.adapter.RecycleAddressAdapter;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class MyOrderFormActivity extends BaseFragmentActivity implements View.OnClickListener{
    String Tag= "MyOrderFormActivity";
   @Bind(R.id.recycler_tab_layout)
   RecyclerTabLayout recycler_tab_layout;
    @Bind(R.id.view_pager)
    ViewPager view_pager;






    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order_form_view);
        ButterKnife.bind(this);



        getDataIntent();
        intData();
        setListen();

    }

    private void setListen() {

    }

    private void intData() {
        int startYear = 1;
        int endYear = 7;
//        int initialYear = Calendar.getInstance().get(Calendar.YEAR);


        List<String> items = new ArrayList<String>();
        for (int i = startYear; i <= endYear; i++) {
            items.add(String.valueOf(i));
        }


        DemoYearsPagerAdapter adapter = new DemoYearsPagerAdapter();
        adapter.addAll(items);

        view_pager.setAdapter(adapter);
        view_pager.setCurrentItem(endYear - startYear);


        recycler_tab_layout.setUpWithViewPager(view_pager);
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



}
