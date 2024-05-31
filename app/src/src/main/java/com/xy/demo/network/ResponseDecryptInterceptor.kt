package com.xy.demo.network

import com.xy.demo.base.Constants
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import java.nio.charset.Charset

/**
 * 对数据  解密操作
 */

class ResponseDecryptInterceptor : Interceptor {
	
	override fun intercept(chain: Interceptor.Chain): Response {
		val request = chain.request()
		var response = chain.proceed(request)
		
		val url = request.url
		/*本次请求的接口地址*/
		val apiPath = "${url.scheme}://${url.host}:${url.port}${url.encodedPath}".trim()
		/*服务端的接口地址*/
		val serverPath = "${url.scheme}://${url.host}/".trim()
		
		/*如果请求的不是服务端的接口，公共接口不解密*/
		if (!serverPath.startsWith(Constants.releaseBaseUrl)) {
			return response
		}
		// apiPath 上传 下载 接口     TODO  apiPath 包含上传 下载 接口 也需不加密解密
//		if (apiPath.contains("downLoadUrl")) {
//			return chain.proceed(request)
//		}
		
		
		if (response.isSuccessful) {
			val responseBody = response.body
			if (responseBody != null) {
				/*开始解密*/
				try {
					val source = responseBody.source()
					source.request(java.lang.Long.MAX_VALUE)
					val buffer = source.buffer()
					var charset = Charset.forName("UTF-8")
					val contentType = responseBody.contentType()
					if (contentType != null) {
						charset = contentType.charset(charset)
					}
					val bodyString = buffer.clone().readString(charset)
					Globals.log("xxxxx解密数据 "+bodyString)
					// 调用解密方法
					val responseData =  HttpUtils.decryptParam(bodyString).trim()
					/*将解密后的明文返回*/
					val newResponseBody = responseData.toResponseBody(contentType)
					response = response.newBuilder().body(newResponseBody).build()
				} catch (e: Exception) {
					/*异常说明解密失败 信息被篡改 直接返回即可 */
					Globals.log("解密异常====》${e}")
					return response
				}
			} else {
				Globals.log("响应体为空");
			}
		}
		return response
		
	}
}
