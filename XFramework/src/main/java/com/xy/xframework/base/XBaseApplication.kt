package com.xy.xframework.base


import android.app.Application
import com.qw.download.manager.DownloadConfig
import com.qw.download.manager.DownloadManager
import com.simple.spiderman.SpiderMan
import com.xy.xframework.web.WebViewParam


abstract class XBaseApplication : Application() {
	
	companion object {
		lateinit var application: Application
	}
	
	override fun onCreate() {
		super.onCreate()
		
		BaseAppContext.init(this)
		application = this
		registerActivityLifecycleCallbacks(AppActivityManager.getInstance())
		initDown()
		initSpiderMan()
		
	}
	
	fun initSpiderMan() {
		SpiderMan.getInstance()
			.init(this) //设置是否捕获异常，不弹出崩溃框
			.setEnable(true) //设置是否显示崩溃信息展示页面
			.showCrashMessage(true) //是否回调异常信息，友盟等第三方崩溃信息收集平台会用到,
			.setOnCrashListener { t, ex, model ->
				//CrashModel 崩溃信息记录，包含设备信息
			}
		
	}
	
	
	//  断点下载框架下載全局配置
	open fun initDown() {
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