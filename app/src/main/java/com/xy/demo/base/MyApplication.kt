package com.xy.demo.base

import androidx.appcompat.app.AppCompatDelegate
import com.alibaba.android.arouter.launcher.ARouter


import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.tom_roush.pdfbox.android.PDFBoxResourceLoader
import com.xy.demo.BuildConfig


import com.xy.network.NetworkManager
import com.xy.xframework.base.BaseAppContext
import com.xy.xframework.base.XBaseApplication

class MyApplication : XBaseApplication() {
	
	companion object {
		const val TAG = "MyApplication"
		
		lateinit var instance: MyApplication
		
		
	}
	
	init {
//        SmartRefreshLayout.setDefaultRefreshHeaderCreator { context, _ ->
//            MRefreshHeaderView(
//                context
//            )
//        }
		SmartRefreshLayout.setDefaultRefreshFooterCreator { context, _ ->
			ClassicsFooter(
				context
			)
		}
		
	 
	}
	
	override fun onCreate() {
		super.onCreate()
		BaseAppContext.init(this)
		instance = this
		// 路由初始化
		
		initFun()
		
		
		NetworkManager.initNetWatch(this)
		//pdf
		PDFBoxResourceLoader.init(getApplicationContext());
	}
	
	private fun initFun() {
		AppCompatDelegate.setDefaultNightMode(
			AppCompatDelegate.MODE_NIGHT_NO
		)
		initRouter()
 
	}
	
	fun initBugLy() {
//        Beta.autoInit = true
//        Beta.autoCheckAppUpgrade = true
//        Beta.upgradeDialogLayoutId = R.layout.upgrade_dialog
//        Bugly.init(this, "33247bc835", BuildConfig.DEBUG)
	}
	
	/**
	 * @description 处理部分配置项
	 *
	 * @date: 2021/9/22 14:54
	 * @author: zengqingshuai
	 * @param
	 * @return
	 */
	private fun initConfig() {
		//获得应用tab//缓存lottie
		
		
	}
	
	/**
	 * @description 获得用户信息和token
	 *
	 * @date: 2021/8/19 17:23
	 * @author: zengqingshuai
	 * @param
	 * @return
	 */
	private fun getUserInfo() {
	
	}
	
	
	private fun initRouter() {
		if (BuildConfig.DEBUG) {
			ARouter.openLog()
			ARouter.openDebug()
		}
		ARouter.init(this)
	}
	
	
 
}