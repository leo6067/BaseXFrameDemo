package com.xy.network

import com.xy.network.converter.FastJsonConverterFactory
import okhttp3.Interceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.net.Proxy

/**
 * RetrofitClient    每个baseUrl对应一个client
 */
class RetrofitClient(private val baseUrl: String) {

    @Volatile
    private var retrofit: Retrofit? = null

    /**
     * 是否支持代理抓包
     */
    private var enableProxy: Boolean? = null

    /**
     * 应用拦截器
     */
    private val mInterceptors = mutableListOf<Interceptor>()

    /**
     * 网络拦截器
     */
    private val mNetWorkInterceptors = mutableListOf<Interceptor>()

    /**
     * 添加应用拦截器（单个）
     */
    fun addInterceptor(interceptor: Interceptor): RetrofitClient {
        if (retrofit == null) {
            mInterceptors.add(interceptor)
        }
        return this
    }

    /**
     * 添加网络拦截器（单个）
     */
    fun addNetWorkInterceptor(interceptor: Interceptor): RetrofitClient {
        if (retrofit == null) {
            mNetWorkInterceptors.add(interceptor)
        }
        return this
    }

    /**
     * 设置是否支持代理抓包，默认是可以
     */
    fun setEnableProxy(enableProxy: Boolean): RetrofitClient {
        if (retrofit == null) {
            this.enableProxy = enableProxy
        }
        return this
    }

    /**
     * 创建api代理类
     * @return T
     */
    fun <T> createService(service: Class<T>): T {
        if (retrofit == null) {
            build()
        }
        return retrofit!!.create(service)
    }

    /**
     * 构建
     */
    fun build(): RetrofitClient {
        if (retrofit == null) {
            val interceptors = NetworkManager.getInterceptors()
            if (interceptors.isNotEmpty()) {
                mInterceptors.addAll(interceptors)
            }
            NetworkManager.getLogInterceptors()?.run {
                mInterceptors.add(this)
            }

            val okHttpBuilder = NetworkManager.createBuilder()

            mInterceptors.forEach { okHttpBuilder.addInterceptor(it) }

            okHttpBuilder.addInterceptor(NetErrorInterceptor())
            mNetWorkInterceptors.addAll(NetworkManager.getNetWorkInterceptors())
            mNetWorkInterceptors.forEach { okHttpBuilder.addNetworkInterceptor(it) }

            //设置网络代理抓包
            if ((enableProxy == null && !NetworkManager.isEnableProxy()) || (enableProxy != null && enableProxy == false)) {
                okHttpBuilder.proxy(Proxy.NO_PROXY)
            }
            retrofit = Retrofit.Builder()
                .client(okHttpBuilder.build())
                .addConverterFactory(GsonConverterFactory.create())
                .baseUrl(baseUrl)
                .build()
        }
        return this
    }

    /**
     * 释放资源
     */
    fun release() {
        retrofit = null
        mInterceptors.clear()
        mNetWorkInterceptors.clear()
    }
}

