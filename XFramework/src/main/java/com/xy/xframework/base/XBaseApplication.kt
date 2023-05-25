package com.xy.xframework.base

import android.app.Application
import com.downloader.PRDownloader
import com.downloader.PRDownloaderConfig

import com.xy.xframework.web.WebViewParam


abstract class XBaseApplication : Application() {

    companion object {
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()
        BaseAppContext.init(this)
        application = this

        initDown()
    }


    fun initDown() {

        val config: PRDownloaderConfig = PRDownloaderConfig.newBuilder()
            .setDatabaseEnabled(true)
            .build()
        PRDownloader.initialize(applicationContext, config)

    }


    /**
     * 获取用户信息
     */
    fun getAppInfo(): WebViewParam {
        return WebViewParam()
    }

    /**
     * 路由跳转
     */
    fun handleRouter(path: String) {

    }

    /**
     * 上报埋点
     */
    fun handlePoint(eventName: String?, properties: String?) {

    }
}