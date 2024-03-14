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
                    .request("mmbox")
                    .response("mmbox")
                    .build()
            )
            .setEnableProxy(true)
            .addInterceptor(HeadInterceptor())//加入头文件
            .addInterceptor(ResponseInterceptor())
            .createService(Api::class.java)
    }

//    //盲盒详情
//    suspend fun blindBoxDetail(id: Int?): MBResponse<BlindBoxDetailBean> =
//        apiService.blindBoxDetail(id)


    suspend fun getStoreCount(): MBResponse<String> {
        return apiService.getStoreCount()
    }






}




