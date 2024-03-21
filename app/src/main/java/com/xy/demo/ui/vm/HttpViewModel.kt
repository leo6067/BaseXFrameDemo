package com.xy.demo.ui.vm

import android.annotation.SuppressLint
import android.app.Application
import android.text.TextUtils
import androidx.databinding.ViewDataBinding
import com.alibaba.fastjson.JSONArray
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.base.MyApplication

import com.xy.demo.network.AesUtils
import com.xy.demo.network.NetManager
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.command.SingleLiveEvent
import com.xy.xframework.utils.Globals
import java.util.Objects


class HttpViewModel(application: Application) : MBBaseViewModel(application) {
	
	var resultStr = SingleLiveEvent<String>()
	
	
 
	
	
	// 问题反馈
	fun postFeedBack(brandId:String,feedType:Int,content:String) {
		var hashMap = HashMap<String, String>()
		hashMap.put("f", "appkey")
		hashMap.put("h", "subFeed")
		hashMap.put("brandId", brandId)
		hashMap.put("feedType", feedType.toString())
		hashMap.put("content", content)
		launchRequest({ NetManager.feedBackHttp(hashMap) }, {
			val jsonStr = AesUtils.decrypt(
				MyApplication.instance.resources.getString(R.string.AES_KEY), it
			)
			
			if (TextUtils.isEmpty(jsonStr)){
				return@launchRequest
			}
			resultStr.value = jsonStr
		}, {
		
		})
	}
}