package com.xy.demo.ui.vm

import android.annotation.SuppressLint
import android.app.Application
import android.text.TextUtils
import android.view.View
import androidx.databinding.ViewDataBinding
import com.alibaba.fastjson.JSONArray
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityMainBinding
import com.xy.demo.logic.ad.AdManage
import com.xy.demo.model.BrandListModel
import com.xy.demo.model.OrderListModel
import com.xy.demo.model.SubBrandListModel
import com.xy.demo.network.AesUtils
import com.xy.demo.network.NetManager
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.command.SingleLiveEvent
import com.xy.xframework.utils.Globals
import java.util.Objects


class MainViewModel(application: Application ) : MBBaseViewModel(application) {
	
	
	
	//获取所有的品牌数据
	@SuppressLint("SuspiciousIndentation")
	fun getBrandListHttp() {
		var hashMap = HashMap<String, String>()
		hashMap.put("f", "tv")
		hashMap.put("h", "getTvBrandList")
		launchRequest({ NetManager.mainHttp(hashMap) }, {
			val resultStr = AesUtils.decrypt(
				Constants.ZS_AES_KEY, it
			)
			BaseSharePreference.instance.putString(Constants.KEY_BRAND_LIST, resultStr)
//			var brandListModel = JSONArray.parseObject(resultStr, BrandListModel::class.java)
		}, {
		
		})
	}
	
 
}