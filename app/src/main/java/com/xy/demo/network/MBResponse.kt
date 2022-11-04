package com.xy.demo.network

import android.util.Log
import androidx.annotation.Keep

@Keep
class MBResponse<T> {

    var code: Int = 99999
    var data: T? = null
    var message: String? = null

    /**
     * 判断是否成功
     */
    fun isSuccess(): Boolean {
        return code == 200
    }

    override fun toString(): String {
        Log.e("BaseResponse","code=$code, data=$data, message='$message'")
        return "BaseResponse(code=$code, data=$data, message='$message')"
    }
}