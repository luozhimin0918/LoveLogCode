package com.smarter.LoveLog.ui;

/**
 * Created by Administrator on 2016/2/17.
 */
import android.content.Context;
import android.util.AttributeSet;
import android.webkit.WebView;
/** * 重新webview
 * @author paoyx */
public class FoundWebView extends WebView {
        ScrollInterface web;
public FoundWebView(Context context) {
        super(context);
// TODO Auto-generated constructor stub
        }
public FoundWebView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        }
public FoundWebView(Context context, AttributeSet attrs) {

        super(context, attrs);
// TODO Auto-generated constructor stub
        }
@Override
protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
//Log.e("hhah",""+l+" "+t+" "+oldl+" "+oldt);
        web.onSChanged(l, t, oldl, oldt);
        }
public void setOnCustomScroolChangeListener(ScrollInterface t){        this.web=t;
        }
/**
 * 定义滑动接口
 * @param
 */
public interface ScrollInterface {

    public void onSChanged(int l, int t, int oldl, int oldt) ;
}}
