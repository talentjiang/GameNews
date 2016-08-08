package com.marno.gameclient.module.web;


import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.marno.gameclient.R;
import com.marno.gameclient.base.BaseActivity;
import com.marno.gameclient.utils.ActivityUtil;
import com.marno.gameclient.utils.WebViewConfig;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by marno on 2016/4/11/09:03.
 */
public class WebViewActivity extends BaseActivity {


    @BindView(R.id.llayout_container_base)
    LinearLayout mLlayoutContainer;
    @BindView(R.id.tv_title)
    TextView mTvTitle;

    private WebView mWebView;


    @Override
    public void onResume() {
        super.onResume();
        mWebView.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();
        mWebView.onPause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mWebView.removeAllViews();
        mWebView.clearHistory();
        mWebView.destroy();
    }

    @Override
    protected int getLayout() {
        return R.layout.activity_baseview_toolbar;
    }

    @Override
    protected View getToolBarView() {
        return findViewById(R.id.toolbar);
    }

    @Override
    protected void initView(Bundle savedInstanceState) {

        mWebView = new WebView(this);
        WebViewConfig.initWebView(this, mWebView);
        mLlayoutContainer.addView(mWebView);

    }

    @Override
    protected void initData() {
        Intent intent = getIntent();
        String url = intent.getExtras().getString(ActivityUtil.ARG_1);
        if (!TextUtils.isEmpty(url)) {
            mWebView.loadUrl(url);
            setWebTitle();
        }
    }

    @OnClick(R.id.btn_back)
    public void onClick(View view) {
        confirmQuit();
    }

    private void setWebTitle() {
        mWebView.setWebChromeClient(new WebChromeClient() {
            @Override
            public void onReceivedTitle(WebView view, String title) {
                mTvTitle.setText(title);
            }
        });
    }


    @Override
    public void onBackPressed() {
        confirmQuit();
    }

    private void confirmQuit() {
        if (mWebView != null && mWebView.canGoBack()) {
            mWebView.goBack();
        } else {
            finish();
        }
    }

}
