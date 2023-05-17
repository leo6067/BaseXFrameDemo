package com.xy.xframework.web

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.util.Log
import android.view.KeyEvent
import android.view.View
import android.webkit.WebSettings
import android.widget.Toast
import androidx.databinding.ViewDataBinding
import com.bumptech.glide.Glide
import com.gyf.immersionbar.ImmersionBar
import com.jeremyliao.liveeventbus.LiveEventBus
import com.xy.xframework.BR
import com.xy.xframework.R
import com.xy.xframework.base.XBaseActivity
import com.xy.xframework.base.XBaseApplication
import com.xy.xframework.base.XBaseViewModel
import com.xy.xframework.databinding.BaseWebViewLayoutBinding
import com.xy.xframework.statusBar.StatusBarUtil
import java.util.*

/**
 * 默认webView容器类，目前功能比较简单
 */
abstract class WebBaseActivity() : XBaseActivity<BaseWebViewLayoutBinding, WebBaseViewModel>() {



    /**
     * 是否可以返回，如果被拦截了则设置为false
     */
    private var canBack = true

    /**
     * 网页标题
     */
    val title: String by lazy { intent.getStringExtra("title") ?: "" }

    /**
     * url
     */
    val url: String by lazy { intent.getStringExtra("url") ?: "" }

    /**
     * 是否显示状态栏
     */
    val isShowTitleBar: Boolean by lazy { intent.getBooleanExtra("isShowTitleBar",true) }

    /**
     * 状态栏字体颜色是否是黑色：默认true=黑色
     */
    val statusBarFontBlack: Boolean by lazy { intent.getBooleanExtra("statusBarFontBlack",true) }

    /**
     * 状态栏背景颜色
     */
    val statusBarBgColor: String? by lazy { intent.getStringExtra("statusBarBgColor")  }


    val uiChangeEvent = WebViewUIChangeEvent()

    override fun initVariableId(): Int = BR.viewModel

    override fun getLayoutId(): Int = R.layout.base_web_view_layout

    override fun createViewModel(): WebBaseViewModel {
        return WebBaseViewModel(application, url)
    }



    abstract fun getWebViewJs():WebViewJS



    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
    override fun initBase() {
        titleBarView?.setTitle(title)
        binding.mWebView.settings.apply {
            setSupportZoom(false)
            builtInZoomControls = false
            useWideViewPort = true
            allowFileAccess = true
            loadWithOverviewMode = true
            javaScriptEnabled = true
            domStorageEnabled = true
            databaseEnabled = true

            setAppCacheMaxSize(1024 * 1024 * 9);//设置缓冲大小，我设的是8M
            defaultTextEncodingName = "utf-8"
            // 特别注意：5.1以上默认禁止了https和http混用，以下方式是开启
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW
            }
                 setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK)

        }
//        if (webViewJS  is  Int)
//        {
//            webViewJS = WebViewJS(uiChangeEvent)
//        }
        val js = getWebViewJs()
        binding.mWebView.addJavascriptInterface(js, "jsObj")
        handleTitleBar()


        val url = "file:///android_asset/webloading.gif"

        Glide.with(this@WebBaseActivity).asGif().load(url).into(binding.webLoadImg)

    }

  private  fun handleTitleBar() {
        try {
            if (!isShowTitleBar) {
                StatusBarUtil.setFitsSystemWindows(this, false)
                titleBarView?.visibility = View.GONE
                statusBarBgColor?.let {
                    StatusBarUtil.setStatusBarColor(this, Color.parseColor("#$it"))
                }
            }
            StatusBarUtil.setStatusBarDarkTheme(this, statusBarFontBlack)
        } catch (e: Exception) {
            e.printStackTrace()
            StatusBarUtil.setStatusBarDarkTheme(this, true)
        }
    }

    fun loadUrl(url: String) {
        binding.mWebView.loadUrl(url)
    }

    override fun initViewObservable() {
        super.initViewObservable()

        uiChangeEvent.reloadH5Event.observe(this) {
            Log.d("WebBaseActivity", "reloadH5Event = $it")
            binding.mWebView.reload()
        }

        uiChangeEvent.goBackEvent.observe(this) {
            Log.d("WebBaseActivity", "goBackEvent = $it")
            if (binding.mWebView.canGoBack()) {
                binding.mWebView.goBack()
            }else{
                finish()
            }
        }

        uiChangeEvent.interceptBackEvent.observe(this) {
            Log.d("WebBaseActivity", "interceptBackEvent = $it")
            canBack = it != 1
        }

        uiChangeEvent.closeWeb.observe(this) {
            Log.d("WebBaseActivity", "closeWeb = $it")
            finish()
        }

        uiChangeEvent.secondaryPage.observe(this) {
            Log.d("WebBaseActivity", "secondaryPage url = ${it["url"]} , title = ${it["title"]}")
            Intent(this, this.javaClass)
                .putExtra("url", it["url"])
                .putExtra("title", it["title"])
            this.startActivity(intent)
        }

        uiChangeEvent.postPointInfo.observe(this) { map ->
            Log.d("WebBaseActivity", "postPointInfo = $map")
            val application = XBaseApplication.application as XBaseApplication
            application.handlePoint(map["eventId"], map["msg"])
        }

        uiChangeEvent.routerPath.observe(this) {
            Log.d("WebBaseActivity", "routerPath = $it")
            val application = XBaseApplication.application as XBaseApplication
            application.handleRouter(it)
        }

        uiChangeEvent.toast.observe(this) {
            Log.d("WebBaseActivity", "toast = $it")
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        }

        LiveEventBus.get("reloadWebEvent", String::class.java).observe(this) {
            Log.d("WebBaseActivity", "reloadWebEvent")
            binding.mWebView.reload()
        }
    }

    override fun onPause() {
        super.onPause()

        Log.e("xxxxxonPause","onPause")
//        binding.mWebView.onPause()
//        binding.mWebView.pauseTimers()
    }

    override fun onResume() {
        super.onResume()

//        binding.mWebView.resumeTimers()
//        binding.mWebView.onResume()
    }

    override fun onDestroy() {

        binding.mWebView.visibility = View.GONE
        binding.mWebView.destroy()
        super.onDestroy()
    }

    override fun onLeftClick() {
        super.onLeftClick()
        receiveWebBack()
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            receiveBack()
            if (canBack) {
                //如果没有被拦截，则使用默认逻辑，判断是否可以回退
                if (binding.mWebView.canGoBack()) {
                    binding.mWebView.goBack()
                    return true
                }
            } else {
                //如果被拦截了，则什么逻辑都不用处理
                return true
            }
        }
        return super.onKeyDown(keyCode, event)
    }

    /**
     * 拦截到物理按键返回事件
     */
    private fun receiveBack() {
        evaluateJavascript("receiveBack")
    }

    /**
     * 拦截到页面返回事件（原生导航栏）调用h5页面方法 receiveWebBack
     */
    private fun receiveWebBack() {
        evaluateJavascript("receiveWebBack")
    }




    private fun evaluateJavascript(functionName: String, callback: ((String) -> Unit)? = null) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            binding.mWebView.evaluateJavascript("javascript:${functionName}()") {
                callback?.invoke(it)
            }
        }
    }
}