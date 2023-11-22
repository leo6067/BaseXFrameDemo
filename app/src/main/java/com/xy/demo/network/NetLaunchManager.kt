package com.xy.demo.network


import com.bumptech.glide.load.HttpException
import com.xy.demo.R
import com.xy.demo.base.MyApplication
import com.xy.demo.network.params.AESUtils
import com.xy.xframework.utils.ToastUtils
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
            Globals.log("xxxxxxxx999999" + it.toString())
            try {
                when (it.code) {
                    0 -> success(it.data)
                    315 -> {
                        ToastUtils.showShort(it.msg)
                        error(it)
                    }
                    318 -> {
                        ToastUtils.showShort(it.msg)
                        error(it)
                    }
                    1302 -> ToastUtils.showShort(it.msg)
                    301 -> ToastUtils.showShort(it.msg)
                    302 -> {
                        // 用户封禁
                        ToastUtils.showShort(it.msg)
//                        SettingActivity.exitUser(activity)
                    }
//                    else -> {
//                        ToastUtils.showShort(it.msg)
//                        error(it)
//                    }
                }
            } catch (s: Exception) {
            }
            complete()
            success2?.invoke(it)
        }.onFailure {
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