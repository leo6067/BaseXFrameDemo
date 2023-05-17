package com.xy.network.converter

import com.alibaba.fastjson.JSON
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Converter

class FastJsonRequestBodyConverter<T> : Converter<T, RequestBody> {
    private val MEDIA_TYPE = "application/json; charset=UTF-8".toMediaType()

    override fun convert(value: T): RequestBody? {
        try {
            val jsonStr = JSON.toJSONString(value)
            return jsonStr.toRequestBody(contentType = MEDIA_TYPE)
        } catch (e: Exception) {

        }
        return null
    }
}