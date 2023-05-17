package com.xy.network

import com.alibaba.fastjson.JSONObject
import okhttp3.*
import okhttp3.MediaType.Companion.toMediaTypeOrNull

class NetErrorInterceptor : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val builder: Request.Builder = chain.request().newBuilder()
        val request = builder.build()
        try {
            return chain.proceed(request)
        } catch (e: Exception) {
            val exception = ApiException().apply {
                code = 1000 //未知错误
                msg = if (e.cause!=null){
                    e.cause?.message ?: ""
                }else{
                    e.message ?: ""
                }
//                throwable = e
            }

            val mediaType = "application/x-www-form-urlencoded".toMediaTypeOrNull()
            val jsonObject = JSONObject()
            jsonObject["code"] = exception.code
            jsonObject["message"] = exception.msg
            return Response.Builder()
                .code(200)
                .protocol(Protocol.HTTP_1_1)
                .body(ResponseBody.create(mediaType, jsonObject.toJSONString()))
                .message(exception.msg)
                .request(request)
                .build()
        }
    }
}