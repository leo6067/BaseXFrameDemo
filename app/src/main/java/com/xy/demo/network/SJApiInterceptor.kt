package com.xy.demo.network;


import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.TimeUtils
import com.google.gson.Gson
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MyApplication
import okhttp3.FormBody
import okhttp3.Headers
import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.ResponseBody.Companion.toResponseBody
import okio.Buffer
import okio.GzipSource
import org.json.JSONObject
import java.io.EOFException
import java.nio.charset.Charset
import java.nio.charset.UnsupportedCharsetException
import java.util.Date


/**
 * 上甲接口拦截器，添加公共参数
 */
class SJApiInterceptor : Interceptor {
	
	//  appkey  每个APP 不一样    隐私政策链接内容   包名  加密密码
	
	private fun commonParams(): JSONObject {
		val paramJson = JSONObject()
	 
		paramJson.put("market", "google")
		paramJson.put("appkey", "100001")
		paramJson.put("source", "1")//1安卓2ios
		paramJson.put("v", "1")
		paramJson.put("version", AppUtils.getAppVersionName())
		return paramJson
	}
	
	override fun intercept(chain: Interceptor.Chain): Response {
		//处理请求
		//加密密钥
		var decryptKey = MyApplication.instance.resources.getString(R.string.AES_KEY)
	
	
		
		var request = chain.request()
		val requestBuilder = request.newBuilder()
		val urlBuilder = request.url.newBuilder()
		when (request.method) {
			"GET" -> {
				//添加公共参数
				val httpUrl = urlBuilder.build()
				val paramJson = commonParams()
				httpUrl.queryParameterNames.forEach {
					paramJson.put(it, httpUrl.queryParameter(it))
					urlBuilder.removeAllQueryParameters(it)
				}
				//除了获取密钥组，其他都要走下面if步骤
				//获取随机key,value
			
				Globals.log("request params"+paramJson.toString())
			
				//加密参数
				val encrypt = AesUtils.encrypt(decryptKey, paramJson.toString())
				val time = TimeUtils.date2String(Date())
				val md5Params = hashMapOf("encryptdata" to encrypt, "t" to time)
				
				val httpUrl1 = urlBuilder
					.addQueryParameter("encryptdata", encrypt)
					.addQueryParameter("t", time)
					.addQueryParameter("appkey", "100001")
					
					.addQueryParameter("sign", MD5Util.md5(md5Params,MyApplication.instance.resources.getString(R.string.AES_SECRET)))
//				httpUrl1.takeIf { k != null }?.addQueryParameter("s", k)
				
				Globals.log("xxxxxxrequestBuilder -------++++++++++ ------respBody "+httpUrl1)
				requestBuilder.url(httpUrl1.build())
			}
			"POST" -> {
				val requestBody = request.body
				if (requestBody is FormBody) {
					val paramJson = commonParams() //用于组装加密
					for (i in 0 until requestBody.size) {
						paramJson.put((requestBody).name(i), (requestBody).value(i))
					}
//					if (paramJson.optString("token").isNullOrBlank()) {
//						paramJson.put("token", LoginUtils.getToken())
//					}
					
					Globals.log("post request params"+paramJson.toString())
					//重新组装请求体
					val time = TimeUtils.date2String(Date())
					val encrypt = AesUtils.encrypt(decryptKey, paramJson.toString())
					val md5Params =
						hashMapOf("encryptdata" to encrypt, "t" to time)
					val newBodyBuilder = FormBody.Builder()
					newBodyBuilder.add("encryptdata", encrypt)
					newBodyBuilder.add("t", time)
					newBodyBuilder.add("appkey", "100001")
					newBodyBuilder.add("sign", MD5Util.md5(md5Params,MyApplication.instance.resources.getString(R.string.AES_SECRET)))
					
					Globals.log("xxxxxxrequestBuilder -------++++++++++ ------respBody "+newBodyBuilder.toString())
					requestBuilder.post(newBodyBuilder.build()).build()//构造新的请求体//构造新的请求体
				}
			}
		}
		
		var response = kotlin.runCatching {
			chain.proceed(requestBuilder.build())
		}.getOrThrow()
		//以下用于打印解密内容
//		if (!BaseApp.isDebug)
//			return response
		
		
		val responseBody = response.body
		val contentLength = responseBody!!.contentLength()
		val headers = response.headers
		
		
		
		
		if (bodyHasUnknownEncoding(response.headers)  )
			return response
		val source = responseBody!!.source()
		source.request(Long.MAX_VALUE) // Buffer the entire body.
		var buffer: Buffer = source.buffer
		if ("gzip".equals(headers["Content-Encoding"], ignoreCase = true)) {
			GzipSource(buffer.clone()).use { gzippedResponseBody ->
				buffer = Buffer()
				buffer.writeAll(gzippedResponseBody)
			}
		}
		
		val UTF8 = Charset.forName("UTF-8")
		var charset: Charset = UTF8
		val contentType = responseBody!!.contentType()
//		if (contentType != null) {
//			charset = contentType.charset(UTF8)!!
//		}

//		if (!isPlaintext(buffer)) {
//			return response
//		}
		
		
		
		if (contentLength != 0L) {
	
			kotlin.runCatching {
				val resp = buffer.clone().readString((charset))
				
			
				val baseApiModel = Gson().fromJson(resp, AppResponse::class.java)
				if (baseApiModel.code == 200) {
					val logTag = StringBuilder("")
					var fValue = ""
					var hValue = ""
					
					if (request.method.equals("GET")) {
						var httpUrl = request.url
						fValue = httpUrl.queryParameter("f").toString()
						hValue = httpUrl.queryParameter("h").toString()
					} else {
						val requestBody = request.body as FormBody
						for (i in 0 until requestBody.size) {
							var name = (requestBody).name(i)
							if (name.equals("f")) {
								fValue = (requestBody).value(i)
								continue
							}
							
							if (name.equals("h")) {
								hValue = (requestBody).value(i)
								continue
							}
						}
					}
					
					logTag.append("f=")
					logTag.append(fValue)
					logTag.append(" h=")
					logTag.append(hValue)
//					Globals.log(
//						logTag.toString(), "decrypt data", AesUtils.decrypt(
//							decryptKey,
//							baseApiModel.encryptData
//						)
//					)
				}
//			var bodyString  = "{\"code\":200,\"message\":\"操作成功\",\"encryptdata\":"+ AesUtils.decrypt(
//					decryptKey,
//					baseApiModel.encryptdata
//				) +"}"
//
//
//
//				/*将解密后的明文返回*/
//				val newResponseBody = bodyString.toResponseBody(contentType)
//				response = response.newBuilder().body(newResponseBody).build()
//
//
//				var respBody: String? = null
//
//					val source = response.body?.source()
//					source?.request(Long.MAX_VALUE)
//					val buffer = source?.buffer()
//					var charset = UTF8
//					val contentType = response.body?.contentType()
//					if (contentType != null) {
//						try {
//							charset = contentType.charset(UTF8)
//						} catch (e: UnsupportedCharsetException) {
//							e.printStackTrace()
//						}
//					}
//					respBody = buffer?.clone()?.readString(charset!!)
//
////				Globals.log("xxxxxxresponse -------++++++++++  "+bodyString)
//				Globals.log("xxxxxxresponse -------++++++++++ ------respBody "+respBody)
//				return response
			}
		}
		
	 
		return response
	}
	
	private fun bodyHasUnknownEncoding(headers: Headers): Boolean {
		val contentEncoding = headers["Content-Encoding"]
		return (contentEncoding != null && !contentEncoding.equals("identity", ignoreCase = true)
				&& !contentEncoding.equals("gzip", ignoreCase = true))
	}
	
	private fun isPlaintext(buffer: Buffer): Boolean {
		return try {
			val prefix = Buffer()
			val byteCount = if (buffer.size < 64) buffer.size else 64
			buffer.copyTo(prefix, 0, byteCount)
			for (i in 0..15) {
				if (prefix.exhausted()) {
					break
				}
				val codePoint = prefix.readUtf8CodePoint()
				if (Character.isISOControl(codePoint) && !Character.isWhitespace(codePoint)) {
					return false
				}
			}
			true
		} catch (e: EOFException) {
			false
		}
	}
	
}

