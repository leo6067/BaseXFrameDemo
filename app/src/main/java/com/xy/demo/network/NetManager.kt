package com.xy.demo.network
import com.xy.demo.BuildConfig
import com.xy.network.NetworkManager
import com.xy.xframework.network.log.Level
import com.xy.xframework.network.log.LoggingInterceptor


object NetManager {
    const val TIME_OUT = 10000L
    const val DEFAULT_PAGE_NUM = 20
    private val domain = if (BuildConfig.DEBUG) {
        "Constants.DOMAIN_DEBUG"
    } else
        "  Constants.DOMAIN_RELEASE"

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


    suspend fun getStoreCount(): MBResponse<Int> {
        return apiService.getStoreCount()
    }


}




