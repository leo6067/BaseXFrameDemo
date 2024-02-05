package com.xy.demo.base

import android.app.Application
import androidx.lifecycle.viewModelScope
import com.xy.demo.network.MBResponse
import com.xy.demo.network.NetLaunchManager
import com.xy.xframework.base.XBaseViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

open class MBBaseViewModel(application: Application) : XBaseViewModel(application) {

    fun launchUI(block: suspend CoroutineScope.() -> Unit) = viewModelScope.launch {
        block()
    }

    fun <T> launchRequest(
        block: suspend () -> MBResponse<T>,
        success: (T?) -> Unit = {},
        error: (Throwable) -> Unit = {},
        showLoading: Boolean = false,
        loadingTitle: String? = null,
        success2: ((MBResponse<T>) -> Unit)? = null,//返回全部数据
    ) {
        if (showLoading) showLoading(loadingTitle)
        launchUI {
            NetLaunchManager.launchRequestSuspend(block, success, error, success2) {
                if (showLoading) {
                    dismissLoading()
                }
            }
        }
    }

    suspend fun <T : Any> launchIO(call: suspend () -> T): T {
        return withContext(Dispatchers.IO) { call.invoke() }.apply {
        }
    }
    
    
    

}