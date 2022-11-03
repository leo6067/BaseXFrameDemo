package com.xy.xframework.base

import android.app.Application
import com.xy.xframework.web.WebViewParam
import org.json.JSONObject

abstract class XBaseApplication : Application() {

    companion object {
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    /**
     * 获取用户信息
     */
     fun getAppInfo(): WebViewParam{
         return WebViewParam()
     }

    /**
     * 路由跳转
     */
    fun handleRouter(path: String){

    }

    /**
     * 上报埋点
     */
    fun handlePoint(eventName: String?, properties: String?){

    }
}