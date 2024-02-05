package com.xy.network

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.xy.network.watch.NetworkStateLiveData
import com.xy.network.watch.NetworkType
import com.xy.network.watch.NetworkWatch
import okhttp3.ConnectionPool
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit

/**
 * 网络请求库统一管理类
 */
object NetworkManager {

    private val mServicesOfRetrofit = mutableMapOf<String, RetrofitClient>()
    private const val TIME_OUT: Long = 60

    @SuppressLint("StaticFieldLeak")
    lateinit var watch: NetworkWatch

    fun initNetWatch(context: Context) {
        watch = NetworkWatch(context)
    }

    fun observeForever(observer: Observer<NetworkType>) {
        if (this::watch.isInitialized) {
            NetworkStateLiveData.observeForever(observer)
        }
    }

    fun observe(owner: LifecycleOwner, observer: Observer<NetworkType>) {
        if (this::watch.isInitialized) {
            NetworkStateLiveData.observe(owner, observer)
        }
    }

    fun createBuilder(): OkHttpClient.Builder {
        return OkHttpClient.Builder()
            .readTimeout(TIME_OUT, TimeUnit.SECONDS)  //网络超时定义
            .connectTimeout(TIME_OUT, TimeUnit.SECONDS)
            .writeTimeout(TIME_OUT, TimeUnit.SECONDS)
            .retryOnConnectionFailure(true)
            .connectionPool(ConnectionPool(8, 15, TimeUnit.SECONDS))
    }

    /**
     * 应用拦截器
     */
    private val mInterceptors = mutableListOf<Interceptor>()

    /**
     * 网络拦截器
     */
    private val mNetWorkInterceptors = mutableListOf<Interceptor>()

    private var logInterceptor: Interceptor? = null

    /**
     * 是否支持代理抓包
     */
    private var enableProxy = true

    /**
     * 添加应用拦截器
     */
    fun addInterceptor(interceptor: Interceptor): NetworkManager {
        mInterceptors.add(interceptor)
        return this
    }

    /**
     * 添加网络拦截器
     */
    fun addNetWorkInterceptor(interceptor: Interceptor): NetworkManager {
        mNetWorkInterceptors.add(interceptor)
        return this
    }

    fun addLogInterceptor(interceptor: Interceptor): NetworkManager {
        logInterceptor = interceptor
        return this
    }

    /**
     * 获取全局应用拦截器
     */
    fun getInterceptors(): List<Interceptor> {
        return mInterceptors
    }

    /**
     * 获取全局网络拦截器
     */
    fun getNetWorkInterceptors(): List<Interceptor> {
        return mNetWorkInterceptors
    }

    /**
     * 将日志拦截器放在所有拦截器下面
     */
    fun getLogInterceptors(): Interceptor? {
        return logInterceptor
    }

    /**
     * 设置是否支持代理抓包，默认是可以
     */
    fun setEnableProxy(enableProxy: Boolean): NetworkManager {
        this.enableProxy = enableProxy
        return this
    }

    /**
     * 判断是否支持代理抓包
     */
    fun isEnableProxy(): Boolean {
        return enableProxy
    }

    /**
     * 根据baseUrl 创建不同的RetrofitCreator   如果有则直接使用，如果没有则创建
     */
    @Synchronized
    fun buildWithBaseUrl(baseUrl: String): RetrofitClient {
        if (baseUrl.isBlank()) {
            throw IllegalArgumentException("BaseUrl 不能为空!!")
        }
        var mRetrofitClient = mServicesOfRetrofit[baseUrl]
        if (mRetrofitClient == null) {
            mRetrofitClient = RetrofitClient(baseUrl)
            mServicesOfRetrofit[baseUrl] = mRetrofitClient
        }
        return mRetrofitClient
    }

    /**
     * 移除指定baseUrl对应的RetrofitClient
     */
    fun removeWithBaseUrl(baseUrl: String): NetworkManager {
        val mRetrofitClient = mServicesOfRetrofit.remove(baseUrl)
        mRetrofitClient?.release()
        return this
    }

    /**
     * 移除所有的RetrofitClient
     */
    fun clear() {
        mServicesOfRetrofit.forEach {
            it.value.release()
        }
        mServicesOfRetrofit.clear()
    }
}