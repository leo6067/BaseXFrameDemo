package com.xy.demo.network


import com.alibaba.fastjson.JSON
import com.blankj.utilcode.util.*
import com.google.gson.reflect.TypeToken

import com.xy.demo.base.Constants

import org.json.JSONObject
import java.util.*
import kotlin.random.Random

object HttpUtils {
	
	
	
	
	// 拦截器参数加密
	fun encryptParam(params: String) :String{
		val aesParams = AesUtils.encrypt(Constants.ZS_AES_KEY, JSON.toJSONString(params))
		val time =  TimeUtils.date2String(Date())
		val md5Params = hashMapOf("encryptdata" to aesParams, "t" to time)
		return MD5Util.md5(md5Params, Constants.ZS_SECRET_KEY)
	}
	
	
	// 拦截器参数解密
	fun decryptParam(params: String) :String{
		val json = JSONObject(params)
		val encryptData = json.getString("encryptdata")
		//根据返回的加密value值取key密钥
		val k = json.optString("s")
		val decryptKey = if (k.isNullOrBlank()) Constants.ZS_AES_KEY else keyMaps[k]
		val data = AesUtils.decrypt(decryptKey, encryptData)
		return data
	}
	
	/**
	 * http 接口公共参数
	 * 招商
	 */
	fun getZSCommonParams(params: Map<String, String> = hashMapOf()): Map<String, String> {
		val commonParams = hashMapOf<String, String>()
		commonParams["brokerId"] = "Constants.BROKER_ID"
		commonParams["appkey"] =" Constants.APPKEY"
		commonParams["f"] = "forward"
		commonParams["market"] = "BaseApp.mChannel"
		commonParams["v"] = "1"
		commonParams["platform"] = "1" //1、android 2、iOS
		commonParams.putAll(params)
	 
		val aesParams = AesUtils.encrypt(Constants.ZS_AES_KEY, JSON.toJSONString(commonParams))
		val time =  TimeUtils.date2String(Date())
		val md5Params = hashMapOf("encryptdata" to aesParams, "t" to time)
		md5Params["sign"] = MD5Util.md5(md5Params, Constants.ZS_SECRET_KEY)
		return md5Params
	}
 
	
	
	
	/********* 上甲http接口 **********/
	var keyMaps = linkedMapOf(
		"ab" to "ffbeDEfSWFGw1W31",
		"bb" to "ffbeDEfSWFGw2W32",
		"cb" to "ffbeDEfSWFGw3W33",
		"db" to "ffbeDEfSWFGw4W34",
		"eb" to "ffbeDEfSWFGw5W35"
	)
	
	/**
	 * 上甲接口参数，加密
	 */
	fun getSJCommonParams(params: HashMap<String, String> = hashMapOf()): Map<String, String> {
//		params["brokerId"] = Constants.BROKER_ID
//		params["appkey"] = Constants.SJSJ_APPKEY
//		params["v"] = Constants.SJSJ_API_VERSION
//		params["market"] = WalleChannelReader.getChannel(BaseApp.getInstance(), Constants.DEFAULT_CHANNEL)!!
		params["source"] = "1"//1安卓2ios
		params["version"] = AppUtils.getAppVersionName()
		LogUtils.iTag("requestparams",GsonUtils.toJson(params))
		val i = Random(System.currentTimeMillis()).nextInt(keyMaps.size)
		val k = keyMaps.keys.toMutableList()[i]
		val decryptKey = keyMaps[k]
		val aesParams = AesUtils.encrypt(decryptKey, GsonUtils.toJson(params))
		val time = TimeUtils.date2String(Date())
		val md5Params = hashMapOf("encryptdata" to aesParams, "t" to time, "s" to k)
//		md5Params["sign"] = MD5Util.md5(md5Params, Constants.SJSJ_API_SIGN)
		return md5Params
	}
	
	/**
	 * 解析接口返回数据
	 */
	inline fun <reified T> decryptSJData(str: String?): T? {
		return kotlin.runCatching {
			val json = JSONObject(str!!)
			val encryptData = json.getString("encryptdata")
			//根据返回的加密value值取key密钥
			val k = json.optString("s")
			val decryptKey = if (k.isNullOrBlank()) Constants.ZS_AES_KEY else keyMaps[k]
			val data = AesUtils.decrypt(decryptKey, encryptData)
			LogUtils.iTag("httpresp", data)
			GsonUtils.fromJson<T>(data, object : TypeToken<T>() {}.type)
		}.getOrElse {
			null
		}
	}
	
//	fun getResponseModel(str: String?): BaseModel? {
//		return kotlin.runCatching {
//			GsonUtils.fromJson(str!!, BaseModel::class.java)
//		}.getOrElse {
//			null
//		}
//	}
}