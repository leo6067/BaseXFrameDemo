package com.xy.demo.network
import com.xy.demo.base.Constants

import com.xy.network.NetworkManager
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.network.log.Level
import com.xy.xframework.network.log.LoggingInterceptor


object NetManager {
    const val TIME_OUT = 10000L
    const val DEFAULT_PAGE_NUM = 20
    val releaseAPP = BaseSharePreference.instance.getBoolean(Constants.KEY_APP_RELEASE, true)
    val domain = if (releaseAPP) {
        Constants.releaseBaseUrl
    } else
        Constants.debugBaseUrl


    val apiService by lazy {
        NetworkManager.buildWithBaseUrl(domain)
            .addInterceptor(
                LoggingInterceptor.Builder()
//                    .loggable(LogUtil.baseConfig.isWatchRequestLog)
                    .setLevel(Level.BASIC)
//                    .request("mmbox")
//                    .response("mmbox")
                    .build()
            )
            .setEnableProxy(true)
//            .addInterceptor(HeadInterceptor())//加入头文件
            .addInterceptor(SJApiInterceptor())
            .addInterceptor(ResponseInterceptor())
//            .addInterceptor(RequestEncryptInterceptor())
//            .addInterceptor(ResponseDecryptInterceptor())
            .createService(Api::class.java)
    }

 

    suspend fun getGood(): MBResponse<String> {
        return apiService.getGood()
    }
    
 
    
     suspend fun mainHttp(hashMap: HashMap<String,String>): MBResponse<String> {
        return apiService.mainHttp(hashMap)
    }
    suspend fun feedBackHttp(hashMap: HashMap<String,String>): MBResponse<String> {
        return apiService.feedBackHttp(hashMap)
    }
    
    
}




