package com.xy.demo.network


import com.bumptech.glide.load.HttpException
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MyApplication

import kotlinx.coroutines.*
import java.net.ConnectException
import java.net.UnknownHostException
import java.util.concurrent.CancellationException

/**
 *
 * 网络发送器和错误统一处理
 */
object NetLaunchManager {
    /**
     * @description 直接网络发送
     */
    fun <T> launchRequest(
        block: suspend () -> MBResponse<T>,
        success: (T?) -> Unit = {},
        error: (Throwable) -> Unit = {}
    ) {
        CoroutineScope(Dispatchers.Main).launch {
            launchRequestSuspend(block, success, error)
        }
    }

    /**
     * @description 依附式网络发送
     */
    suspend fun <T> launchRequestSuspend(
        block: suspend () -> MBResponse<T>,
        success: (T?) -> Unit = {},
        error: (Throwable) -> Unit = {},
        success2: ((MBResponse<T>) -> Unit)? = null,
        complete: () -> Unit = {}
    ) {
        runCatching {
            withContext(Dispatchers.IO) {
                withTimeout(NetManager.TIME_OUT) {
                    block()
                }
            }
        }.onSuccess {
           
            success2?.invoke(it)
            when (it.code) {
                200 -> {
     
                    var resultStr = AesUtils.decrypt(
                        MyApplication.instance.resources.getString(R.string.AES_KEY), it.encryptdata as String)
                    Globals.log("xxxxx请求数据 onSuccess$resultStr")
                    success(it.encryptdata)
                }
//                401 -> {
////                    UserManager.tokenInvalid()
////                    RouterManager.routerPare(RouterManager.ROUTER_LOGIN.pageRouter())
//                    error(Throwable("token invalid"))
//                }
//                623 -> {
////                    ToastUtils.show(R.string.time_error.string())
//                    error(Throwable("time error"))
//                }
                else -> error(Throwable(it.message))
            }
            complete()
        }.onFailure {
            Globals.log("xxxxx请求数据 onFailure"+it.message)
            if (it is CancellationException) {
                complete()
                return
            }
            if (it is HttpException || it is ConnectException || it is UnknownHostException || it is TimeoutCancellationException) {
            }
            error(it)
            complete()
        }
    }
}