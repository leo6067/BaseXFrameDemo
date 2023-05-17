package com.xy.xframework.web


import android.util.Log
import android.webkit.JavascriptInterface
import android.widget.Toast
import androidx.annotation.Keep
import com.alibaba.fastjson.JSON
import com.xy.xframework.base.XBaseApplication
import com.xy.xframework.statusBar.StatusBarUtil
import com.xy.xframework.utils.ResourceUtils

@Keep
open class WebViewJS(var uiEvent: WebViewUIChangeEvent) {

    /**
     * 获取用户信息
     */
    @JavascriptInterface
    fun appInfo(): String {
        val application = XBaseApplication.application as XBaseApplication
        val appInfo = application.getAppInfo()
        Log.d("WebViewJS", "appInfo:$appInfo")
        appInfo.statusBarHeight = ResourceUtils.px2dp(StatusBarUtil.getStatusBarHeight(application).toFloat())
        return JSON.toJSONString(appInfo)
    }

    /**
     * 关闭页面
     */
    @JavascriptInterface
    fun closeWeb() {
        uiEvent.closeWeb.postValue(0)
    }

    /**
     * 刷新页面
     */
    @JavascriptInterface
    fun reloadH5() {
        uiEvent.reloadH5Event.postValue(0)
    }

    /**
     * 后退操作
     */
    @JavascriptInterface
    fun goBack() {
        uiEvent.goBackEvent.postValue(0)
    }

    /**
     * 截取后退监听操作
     *
     * @param flag 1:设置截取 2:取消截取
     */
    @JavascriptInterface
    fun isInterceptBack(flag: Int) {
        uiEvent.interceptBackEvent.postValue(flag)
    }

    /**
     * 打开新的H5页面
     */
    @JavascriptInterface
    fun secondaryPage(url: String?, title: String?) {
        if (url.isNullOrEmpty()) {
            return
        }
        val map = mutableMapOf<String, String>()
        map["url"] = url
        title?.let {
            map["title"] = it
        }
        uiEvent.secondaryPage.postValue(map)
    }

    /**
     * 上报埋点
     * eventId 事件key
     * msg json字符串
     */
    @JavascriptInterface
    fun postPointInfo(eventId:String?,msg: String?) {
        if (eventId.isNullOrEmpty() || msg.isNullOrEmpty()) {
            return
        }
        val map = mutableMapOf<String, String>()
        map["eventId"] = eventId
        map["msg"] = msg
        uiEvent.postPointInfo.postValue(map)
    }

    /**
     * 跳转页面
     *
     * @param url 目前固定标识判断，后期使用路由可直接路由
     */
    @JavascriptInterface
    fun routerPath(url: String?) {
        url?.let {
            uiEvent.routerPath.postValue(it)
        }
    }

    /**
     * 提示
     *
     */
    @JavascriptInterface
    fun toast(msg: String?) {
        msg?.let {
            uiEvent.toast.postValue(it)
        }
    }







}