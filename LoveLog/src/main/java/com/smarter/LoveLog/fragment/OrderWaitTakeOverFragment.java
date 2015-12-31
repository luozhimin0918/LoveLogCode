package com.smarter.LoveLog.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.RecycleOrderCompletedAdapter;
import com.smarter.LoveLog.adapter.RecycleOrderWaitTakeOverAdapter;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class OrderWaitTakeOverFragment extends Fragment implements RecycleOrderWaitTakeOverAdapter.OnCheckDefaultListener {
    protected WeakReference<View> mRootView;
    private View view;




    @Bind(R.id.recyclerView)
    RecyclerView recyclerView;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
            view = inflater.inflate(R.layout.order_all_fragment, null);
            mRootView = new WeakReference<View>(view);

            ButterKnife.bind(this, view);
            initData();
        } else {
            ViewGroup parent = (ViewGroup) mRootView.get().getParent();
            if (parent != null) {
                parent.removeView(mRootView.get());
            }
        }
        return mRootView.get();

    }

    private void initData() {

        initRecycleViewVertical();

    }

    // 创建数据集
    String[] dataset = new String[]{"张三","美女","拉丁","弟弟","火热","额额"};
    String[] dataValue=new String[]{"15083806689","15083806689","15083806689","15083806689","15083806689","15083806689"};
    // 创建Adapter，并指定数据集
    RecycleOrderWaitTakeOverAdapter adapter;
    public void initRecycleViewVertical(){

        // 创建一个线性布局管理器
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        // 默认是Vertical，可以不写
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        // 设置布局管理器
        recyclerView.setLayoutManager(layoutManager);


        // 创建Adapter，并指定数据集
        adapter = new RecycleOrderWaitTakeOverAdapter(dataset,dataValue);
        adapter.setOnCheckDefaultListener(this);
        // 设置Adapter
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void oncheckOK(Boolean[] ischeckArray) {

    }
}
