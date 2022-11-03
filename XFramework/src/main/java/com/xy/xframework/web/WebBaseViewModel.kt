package com.xy.xframework.web

import android.app.Application
import android.net.http.SslError
import android.webkit.*
import com.xy.xframework.base.XBaseViewModel

/**
 * web view 容器类的viewModel
 */
class WebBaseViewModel(application: Application, val url: String?) : XBaseViewModel(application) {


    val mWebViewClient = object : WebViewClient() {

        override fun shouldOverrideUrlLoading(view: WebView?, request: WebResourceRequest?): Boolean {
            if (url.isNullOrBlank()) {
                //这边显示404或者错误页面
                return false
            }
            if (URLUtil.isNetworkUrl(url)) {
                view?.loadUrl(url)
            }
            return true
        }

        override fun onReceivedSslError(view: WebView?, handler: SslErrorHandler?, error: SslError?) {
            super.onReceivedSslError(view, handler, error)
            // 页面SSL证书存在问题, 不要使用handler.proceed()忽略该问题, 否则无法上架google商店，应该让H5同学确认修正
        }


        override fun onReceivedError(view: WebView?, request: WebResourceRequest?, error: WebResourceError?) {
            super.onReceivedError(view, request, error)
            //异常错误
        }

        override fun onReceivedHttpError(view: WebView?, request: WebResourceRequest?, errorResponse: WebResourceResponse?) {
            super.onReceivedHttpError(view, request, errorResponse)

        }

        override fun onPageFinished(view: WebView?, url: String?) {
            super.onPageFinished(view, url)
            //加载结束
        }
    }
}