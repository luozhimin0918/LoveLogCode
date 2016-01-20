package com.smarter.LoveLog.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.CheckBox;

/**
 * Created by Administrator on 2016/1/20.
 */
public class QCheckBox extends CheckBox {
    public QCheckBox(Context context) {
        super(context);
    }

    public QCheckBox(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public QCheckBox(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean performClick() {
        return callOnClick();
    }
}
