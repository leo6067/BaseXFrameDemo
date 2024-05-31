package com.xy.demo.network

import com.xy.demo.base.Constants
import okhttp3.Interceptor
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.Buffer
import java.net.URLDecoder
import java.nio.charset.Charset
import java.util.Locale

/**
 * @description: 对请求数据进行加密处理
 *
 */

class RequestEncryptInterceptor : Interceptor {
	override fun intercept(chain: Interceptor.Chain): Response {
		var request = chain.request()
		var charset = Charset.forName("UTF-8")
 
		val method = request.method.lowercase(Locale.getDefault()).trim { it <= ' ' }
		val url = request.url
		/*本次请求的接口地址*/
		val apiPath = "${url.scheme}://${url.host}:${url.port}${url.encodedPath}".trim()
		/*服务端的接口地址*/
		val serverPath = "${url.scheme}://${url.host}/".trim()
		
		
		
		/*如果请求的不是服务端的接口，不加密*/
		if (!serverPath.startsWith(Constants.releaseBaseUrl)) {
			return chain.proceed(request)
		}
		// apiPath 上传 下载 接口     TODO  apiPath 包含上传 下载 接口 也需不加密解密
//		if (apiPath.contains("downLoadUrl")) {
//			return chain.proceed(request)
//		}
		Globals.log("xxxxx请求数据  method" + method)
		
		
		/*如果请求方式是Get或者Delete，此时请求数据是拼接在请求地址后面的*/
		if (method.equals("get") || method.equals("delete")) {
			/*如果有请求数据 则加密*/
			if (url.encodedQuery != null) {
				try {
					val queryparamNames = request.url.encodedQuery
					Globals.log("xxxxx请求数据"+queryparamNames)
					val encryptqueryparamNames= queryparamNames?.let { HttpUtils.encryptParam(it) }
					Globals.log("xxxxx请求加密数据"+queryparamNames)
					//拼接加密后的url，参数字段自己跟后台商量，这里我用param，后台拿到数据先对param进行解密，解密后的数据就是请求的数据
					val newUrl = "$apiPath?param=$encryptqueryparamNames"
					//构建新的请求
					request = request.newBuilder().url(newUrl ).build()
				} catch (e: Exception) {
					e.printStackTrace()
					return chain.proceed(request)
				}
			}
		} else {
			//不是Get和Delete请求时，则请求数据在请求体中
			val requestBody = request.body
			/*判断请求体是否为空  不为空则执行以下操作*/
			if (requestBody != null) {
				val contentType = requestBody.contentType()
				if (contentType != null) {
					charset = contentType.charset(charset)
					/*如果是二进制上传  则不进行加密*/
					if (contentType.type.toLowerCase().equals("multipart")) {
						return chain.proceed(request)
					}
				}
				
				/*获取请求的数据*/
				try {
					val buffer = Buffer()
					requestBody.writeTo(buffer)
					val requestData = URLDecoder.decode(buffer.readString(charset).trim(), "utf-8")
					
					val encryptData=HttpUtils.encryptParam(requestData)
					/*构建新的请求体*/
					val newRequestBody = encryptData.toRequestBody(contentType)
					
					/*构建新的requestBuilder*/
					val newRequestBuilder = request.newBuilder()
					//根据请求方式构建相应的请求
					when (method) {
						"post" -> newRequestBuilder.post(newRequestBody)
						"put" -> newRequestBuilder.put(newRequestBody)
					}
					request = newRequestBuilder.build()
					
				} catch (e: Exception) {
					Globals.log("加密异常====》${e}")
					return chain.proceed(request)
				}
			}
		}
		return chain.proceed(request)
	}
}
