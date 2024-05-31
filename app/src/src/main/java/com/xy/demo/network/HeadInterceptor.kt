package com.xy.demo.network
import android.util.Log
import okhttp3.Interceptor
import okhttp3.Response


/**
 * 共同请求头
 */
class HeadInterceptor : Interceptor {

    var deviceId: String? = null


   fun commonHead(): MutableMap<String, String> {
        val client =" makeClient()"
        val sign = "getSign(client)"

        val headMap = mutableMapOf<String, String>()
        headMap["client"] = client
        headMap["token"] = sign
        headMap["Authorization"] = "Bearer "
        Log.d("magicBox", "head: $headMap")
        return headMap
    }

    override fun intercept(chain: Interceptor.Chain): Response {
        val mutableMap = commonHead()
        val request = chain.request().newBuilder().apply {
            mutableMap.keys.forEach {
                mutableMap[it]?.let { it1 -> addHeader(it, it1) }
            }
        }.build()
        return chain.proceed(request)
    }

//    private fun makeClient(): String {
//        val appkey = LogUtil.baseConfig.getAppKey() ?: ""
//        val pk = LogUtil.baseConfig.appPackage
//        val platform = 1  // 1-android  2-ios
//        val appversion = LogUtil.baseConfig.appVersion
//        val sysversion = DeviceInfo.getOsVersion()
//        val deviceId = DeviceIDUtils.getDeviceId(LogUtil.baseConfig.context)
//        val imei = DeviceInfo.getImei()
//        val brand = DeviceInfo.getBrand()
//        val model = DeviceInfo.getModel()
//        val channel = LogUtil.baseConfig.appChannel ?: ""
//        val screenWidth = DeviceInfo.getScreenWidth()
//        val screenHeight = DeviceInfo.getScreenHeight()
//        val androidId = DeviceInfo.getAndroidId()
//        val oaid = AppManager.getOaid()
//
//        val client = "${appkey}|${deviceId}|${imei}|${platform}|${channel}|${pk}|${appversion}|${sysversion}|${brand}|${model}|${screenWidth}|${screenHeight}|${androidId}|${oaid}"
//        return Base64Coder.encodeString(client)
//    }

//    //MD5签名
//    private fun getSign(client: String): String {
//        val random = RandomUtil.string(8)
//        val time = System.currentTimeMillis().toString()
//
//        val signString = client+random+time + AppManager.APP_SECRET
//
//        val sign = DeviceUtil.getMD5(signString, true)
//        val token = "${sign}_${random}_${time}"
//        return Base64Coder.encodeString(token);
//    }

}