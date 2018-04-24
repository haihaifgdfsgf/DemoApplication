package com.caohai.demoapplication;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.tencent.smtt.sdk.WebChromeClient;
import com.tencent.smtt.sdk.WebSettings;
import com.tencent.smtt.sdk.WebView;
import com.tencent.smtt.sdk.WebViewClient;


public class MyFragment extends Fragment {
    private WebView mWebView;

    public MyFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my, container, false);
        mWebView = (WebView) view.findViewById(R.id.webview);
        //使用setting
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptEnabled(true);
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
        webSettings.setUseWideViewPort(false);//关键点
        webSettings.setLoadWithOverviewMode(false);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        webSettings.setDisplayZoomControls(false);
        webSettings.setJavaScriptEnabled(true);     // 设置支持javascript脚本
        webSettings.setAllowFileAccess(true);       // 允许访问文件
        webSettings.setBuiltInZoomControls(false);   // 设置显示缩放按钮
        webSettings.setSupportZoom(false);           // 支持缩放
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.addJavascriptInterface(new JsInterface(getActivity()), "android");
        mWebView.loadUrl("file:///android_asset/index.html");
        return view;
    }

    private final class JsInterface {
        Context curContext;

        JsInterface(Context context) {
            curContext = context;
        }

        @JavascriptInterface
        public void goActivity() {
            Toast.makeText(getActivity(), "fdianji", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(getActivity(), TwoActivity.class);
            startActivity(intent);
        }
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

    }

    @Override
    public void onDetach() {
        super.onDetach();
    }


}
