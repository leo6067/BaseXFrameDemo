package com.xy.xframework.web

import android.webkit.WebChromeClient
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.BindingAdapter

/**
 * webView binding属性设置
 */

/**
 * 加载html数据
 */
@BindingAdapter("loadUrl")
fun WebView.loadJsBridgeHtml(html: String?) {
    if (!html.isNullOrEmpty()) {
        loadUrl(html)
    }
}

/**
 * 设置WebView client监听代理
 * <p>
 * @receiver WebView
 * @param webViewClient WebViewClient?
 */
@BindingAdapter("setWebViewClient")
fun WebView.setWebViewClient(webViewClient: WebViewClient?) {
    if (webViewClient != null) {
        setWebViewClient(webViewClient)
    }
}

/**
 * 设置WebView WebChromeClient监听
 * <p>
 * @receiver WebView
 * @param webChromeClient WebChromeClient?
 */
@BindingAdapter("setWebChromeClient")
fun WebView.setBindingWebChromeClient(webChromeClient: WebChromeClient?) {
    if (webChromeClient != null) {
        setWebChromeClient(webChromeClient)
    }
}
