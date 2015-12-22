package com.smarter.LoveLog.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.smarter.LoveLog.R;
import com.smarter.LoveLog.adapter.MyGridAdapter;
import com.smarter.LoveLog.ui.MyGridView;

import java.lang.ref.WeakReference;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Administrator on 2015/11/30.
 */
public class SelfFragment extends Fragment {
    protected WeakReference<View> mRootView;
    private View view;


   // @Bind(R.id.gridview)
    MyGridView gridview;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        if (mRootView == null || mRootView.get() == null) {
            view = inflater.inflate(R.layout.self_fragment, null);
            mRootView = new WeakReference<View>(view);

           // ButterKnife.bind(view);
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
        gridview= (MyGridView) view.findViewById(R.id.gridview);
        gridview.setFocusable(false);

        gridview.setAdapter(new MyGridAdapter(getContext()));


    }
}
