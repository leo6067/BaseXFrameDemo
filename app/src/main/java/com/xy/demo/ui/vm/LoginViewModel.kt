package com.xy.demo.ui.vm

import android.app.Application
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.network.NetLaunchManager
import com.xy.demo.network.NetManager
import com.xy.xframework.command.SingleLiveEvent

/**
 * author: Leo
 * createDate: 2022/11/3 17:29
 */
class LoginViewModel (application: Application) : MBBaseViewModel(application) {
 
	
	var userJson =  SingleLiveEvent<String>()
	
	
	fun loginHttp(){
//		 launchRequest({NetManager.loginHttp()},{
//
//		 },{
//
//		 })
	}
}