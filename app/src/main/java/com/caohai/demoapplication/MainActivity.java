package com.caohai.demoapplication;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.webkit.JavascriptInterface;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


public class MainActivity extends FragmentActivity {
    private WebView mWebView;
    private LinearLayout mLinearLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mLinearLayout = (LinearLayout) findViewById(R.id.fragment_content);
//        mWebView = (WebView) findViewById(R.id.webview);
//        //使用setting
//        WebSettings webSettings = mWebView.getSettings();
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setJavaScriptEnabled(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
//        webSettings.setUseWideViewPort(false);//关键点
//        webSettings.setLoadWithOverviewMode(false);
//        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
//        webSettings.setDisplayZoomControls(false);
//        webSettings.setJavaScriptEnabled(true);     // 设置支持javascript脚本
//        webSettings.setAllowFileAccess(true);       // 允许访问文件
//        webSettings.setBuiltInZoomControls(false);   // 设置显示缩放按钮
//        webSettings.setSupportZoom(false);           // 支持缩放
//        mWebView.setWebChromeClient(new WebChromeClient());
//        mWebView.setWebViewClient(new WebViewClient());
//        mWebView.addJavascriptInterface(new JsInterface(this), "android");
//        mWebView.loadUrl("file:///android_asset/index.html");
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        MyFragment fragment1 = new MyFragment();
        transaction.add(R.id.fragment_content, fragment1);
        transaction.commit();
    }

    private final class JsInterface {
        Context curContext;

        JsInterface(Context context) {
            curContext = context;
        }


        @JavascriptInterface
        public void goActivity() {
            Toast.makeText(MainActivity.this, "dianji", Toast.LENGTH_SHORT).show();
        }


    }
}
