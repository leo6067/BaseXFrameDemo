package com.xy.demo.ui.vm

import android.annotation.SuppressLint
import android.app.Application
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.base.MyApplication
import com.xy.demo.network.NetManager
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.command.SingleLiveEvent

/**
 * author: Leo
 * createDate: 2022/11/3 17:29
 */
class LoginViewModel (application: Application) : MBBaseViewModel(application) {


//	var brandListModel = SingleLiveEvent<String>()
//
//	//获取所有的品牌数据
//	@SuppressLint("SuspiciousIndentation")
//	fun getBrandListHttp() {
//		var hashMap = HashMap<String, String>()
//		hashMap.put("f", "tv")
//		hashMap.put("h", "getTvBrandList")
//		launchRequest({ NetManager.mainHttp(hashMap) }, {
//
//			val resultStr =  JMUtils().decrypt(
//				MyApplication.instance.resources.getString(R.string.AES_KEY), it
//			)
//			BaseSharePreference.instance.putString(Constants.KEY_BRAND_LIST, resultStr)
////			var brandListModel = JSONArray.parseObject(resultStr, BrandListModel::class.java)
//
//			brandListModel.postValue(resultStr)
//		}, {
//
//		})
//	}
}