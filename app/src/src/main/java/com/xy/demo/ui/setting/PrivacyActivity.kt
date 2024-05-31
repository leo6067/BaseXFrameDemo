package com.xy.demo.ui.setting


import android.annotation.SuppressLint
import android.net.http.SslError
import android.webkit.SslErrorHandler
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityPrivacyBinding
import com.xy.demo.logic.LanguageUtil


class PrivacyActivity : MBBaseActivity<ActivityPrivacyBinding, MBBaseViewModel>() {
	
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
	override fun getLayoutId(): Int {
		return R.layout.activity_privacy
	}
	
	override fun initView() {
		super.initView()
	 
		
		val mWebSettings = binding.webView.getSettings()
		mWebSettings.setJavaScriptEnabled(true)

//        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //设置缓存

//        mWebSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK); //设置缓存
		mWebSettings.setDomStorageEnabled(true) //设置适应Html5的一些方法
		
		binding.webView.setWebViewClient(object : WebViewClient() {
			override fun shouldOverrideUrlLoading(view: WebView, request: String): Boolean {
				view.loadUrl(request)
				return true
			}
			
			
			//SslErrorHandler.cancel( )
			//停止加载问题页面
			//SslErrorHandler.proceed( )
			//忽略SSL证书错误，继续加载页面
			@SuppressLint("WebViewClientOnReceivedSslError")
			override fun onReceivedSslError(view: WebView?, sslErrorHandler: SslErrorHandler, error: SslError?) {
				sslErrorHandler.cancel()
			}
		})
		
		binding.webView.loadUrl(Constants.AGREEMENT_PRIVACY)
		LanguageUtil.reFreshLanguage(null, this, null)
		binding.titleLay.titleTV.text = getString(R.string.privacy_policy)
	}
}