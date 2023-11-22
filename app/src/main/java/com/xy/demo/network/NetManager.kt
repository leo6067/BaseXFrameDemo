package com.xy.demo.network

import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MyApplication
import com.xy.demo.model.CheckSettingModel
import com.xy.demo.model.IpModel
import com.xy.demo.model.VideoStoreModel
import com.xy.network.NetworkManager
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.network.log.Level
import com.xy.xframework.network.log.LoggingInterceptor
import java.util.*
import kotlin.collections.HashMap


object NetManager {
    const val TIME_OUT = 10000L
    const val DEFAULT_PAGE_NUM = 20


//    val releaseAPP = BaseSharePreference.spObject.getBoolean(Constants.KEY_APP_RELEASE, true)
//    val domain = if (releaseAPP) {
//        Constants.releaseBaseUrl
//    } else
//        Constants.debugBaseUrl


    val domain = MyApplication.instance.getResources().getString(R.string.apiUrl)

    val apiService by lazy {
        NetworkManager.buildWithBaseUrl(domain)
//            .addInterceptor(
//                LoggingInterceptor.Builder()
//                    .loggable(LogUtil.baseConfig.isWatchRequestLog)
//                    .setLevel(Level.BASIC)
//                    .request("mmbox")
//                    .response("mmbox")
//                    .build()
//            )
            .setEnableProxy(true)
//            .addInterceptor(HeadInterceptor())//加入头文件
            .addInterceptor(ResponseInterceptor())
            .createService(Api::class.java)
    }





    suspend fun getOutIp(): MBResponse<IpModel> {
        return apiService.getOutIp()
    }


    suspend fun getCheckSetting(jsonString: Map<String, String>): MBResponse<CheckSettingModel> {
        return apiService.getCheckSetting(jsonString)
    }

    suspend fun getVideoStore(jsonString: Map<String, String>): MBResponse<VideoStoreModel> {
        return apiService.getVideoStore(jsonString)
    }


}




