package com.xy.xframework.base

import android.app.Application
import com.liulishuo.filedownloader.FileDownloader
import com.liulishuo.filedownloader.connection.FileDownloadUrlConnection
import com.qw.download.manager.DownloadConfig
import com.qw.download.manager.DownloadManager
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
        DownloadConfig.init(
            DownloadConfig.Builder()
                .setConnectTimeout(10000) //连接超时时间
                .setReadTimeout(10000) //读取超时时间
                .setMaxTask(3) //最多3个任务同时下载
                .setMaxThread(3) //1个任务分3个线程分段下载
                .setAutoResume(true) //启动自动恢复下载
                .setRetryCount(3) //单个任务异常下载失败重试次数
                .setDownloadDir(externalCacheDir!!.absolutePath)
                .builder()
        )
        //初始化下载组件(可在子线程中做)
        DownloadManager.init(this)




        //另外一套
        FileDownloader.setupOnApplicationOnCreate(this)
            .connectionCreator(
                FileDownloadUrlConnection.Creator(
                    FileDownloadUrlConnection.Configuration()
                        .connectTimeout(15000) // set connection timeout.
                        .readTimeout(15000) // set read timeout.
                )
            )
            .commit()
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