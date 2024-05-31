package com.xy.demo.base


import android.content.IntentFilter
import android.net.ConnectivityManager
import android.net.wifi.WifiManager
import androidx.appcompat.app.AppCompatDelegate
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.NetworkUtils
import com.connectsdk.discovery.DiscoveryManager
import com.connectsdk.discovery.DiscoveryProvider
import com.connectsdk.service.DIALService
import com.connectsdk.service.DeviceService
import com.scwang.smart.refresh.footer.ClassicsFooter
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.xy.demo.BuildConfig
import com.xy.demo.cast.HttpService

import com.xy.network.NetworkManager
import com.xy.xframework.base.BaseAppContext
import com.xy.xframework.base.XBaseApplication

class MyApplication : XBaseApplication() {
	
	companion object {
		const val TAG = "MyApplication"
		
		lateinit var instance: MyApplication
		lateinit var mDiscoveryManager: DiscoveryManager
		val httpService = HttpService()
		
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
//		AdManage.initAdMob(this)
		BaseAppContext.init(this)
		instance = this
		// 路由初始化
		initFun()
		NetworkManager.initNetWatch(this)
		
		//红外
		DIALService.registerApp("Levak")
		DiscoveryManager.init(this)
		mDiscoveryManager = DiscoveryManager.getInstance()
		try {
			// DLNA
			mDiscoveryManager?.registerDeviceService(
				(Class.forName("com.connectsdk.service.DLNAService") as Class<DeviceService>),
				(Class.forName("com.connectsdk.discovery.provider.SSDPDiscoveryProvider") as Class<DiscoveryProvider>)
			)
		} catch (e: ClassNotFoundException) {
			e.printStackTrace();
		}
		
		mDiscoveryManager.setPairingLevel(DiscoveryManager.PairingLevel.ON); //始终处于可连接状态
		
		
		httpService.start(50000,false)
		
		
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
	 */
	private fun initConfig() {
		//获得应用tab//缓存lottie
		
		
	}
	
	/**
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