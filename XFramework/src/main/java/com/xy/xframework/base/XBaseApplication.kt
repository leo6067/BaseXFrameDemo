package com.xy.xframework.base

import android.app.Application
import com.liulishuo.filedownloader.FileDownloader
import com.xy.xframework.web.WebViewParam


abstract class XBaseApplication : Application() {

    companion object {
        lateinit var application: Application
    }

    override fun onCreate() {
        super.onCreate()

        application = this

        initDown()


    }




    fun initDown(){
        //下載全局配置  https://github.com/qinweiforandroid/QDownload

        //另外一套
//        FileDownloader.setupOnApplicationOnCreate(this)
//            .connectionCreator(
//                FileDownloadUrlConnection.Creator(
//                    FileDownloadUrlConnection.Configuration()
//                        .connectTimeout(15000) // set connection timeout.
//                        .readTimeout(15000) // set read timeout.
//                )
//            )
//            .commit()
//https://github.com/lingochamp/FileDownloader/blob/master/README-zh.md
        FileDownloader.setup(this)
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