package com.marno.gameclient.utils;

import android.app.Activity;
import android.os.Build;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;

/**
 * Created by marno on 2016/5/23/19:28.
 * WebView设置
 */
public class WebViewConfig {

    //初始化WebView配置
    public static void initWebView(final Activity activity, final WebView webView) {
        webView.setDrawingCacheEnabled(true);
        webView.setHorizontalScrollBarEnabled(false);
        webView.setVerticalScrollBarEnabled(false);

        WebSettings settings = webView.getSettings();
        settings.setJavaScriptEnabled(true);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setAppCacheEnabled(true);//启用缓存
        settings.setCacheMode(WebSettings.LOAD_DEFAULT);//设置缓存模式
        settings.setAllowFileAccess(true);
        settings.setDatabaseEnabled(true);

        //强制调整字体大小，单行显示，不会因为系统字体大小设置改变页面布局
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.TEXT_AUTOSIZING);
        } else {
            settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        }

        webView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int keyCode, KeyEvent keyEvent) {
                if (keyCode == KeyEvent.KEYCODE_BACK && webView.canGoBack()) {
                    webView.goBack();//返回上一页面
                    return true;
                }
                return false;
            }
        });

    }

}
