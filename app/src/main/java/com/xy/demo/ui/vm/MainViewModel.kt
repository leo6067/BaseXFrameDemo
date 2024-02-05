package com.xy.demo.ui.vm

import android.annotation.SuppressLint
import android.app.Application
import android.text.TextUtils
import com.alibaba.fastjson.JSONArray
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.model.BrandListModel
import com.xy.demo.model.OrderListModel
import com.xy.demo.model.SubBrandListModel
import com.xy.demo.network.AesUtils
import com.xy.demo.network.NetManager
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.command.SingleLiveEvent
import com.xy.xframework.utils.Globals
import java.util.Objects


class MainViewModel(application: Application) : MBBaseViewModel(application) {
	
	
	var subBrandListModel = SingleLiveEvent<SubBrandListModel>()
	var orderListModel = SingleLiveEvent<OrderListModel>()
	var resultStr = SingleLiveEvent<String>()
	
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
	
	
	
	
	
	//获取品牌下的子品牌列表
	fun getSubBrandListHttp(brandId:String) {
		var hashMap = HashMap<String, String>()
		hashMap.put("f", "tv")
		hashMap.put("h", "getTvBrandModelList")
		hashMap.put("brandId", brandId)
		launchRequest({ NetManager.mainHttp(hashMap) }, {
			val resultStr = AesUtils.decrypt(
				Constants.ZS_AES_KEY, it
			)
			
			if (TextUtils.isEmpty(resultStr)){
				return@launchRequest
			}
			val subBrandList = JSONArray.parseObject(resultStr, SubBrandListModel::class.java) as SubBrandListModel
			subBrandListModel.value = subBrandList
		}, {
		
		})
	}
	
	
	//获取品牌下的子品牌列表---红外指令
	fun getOrderListHttp(brandId:String,modelId:String) {
		var hashMap = HashMap<String, String>()
		hashMap.put("f", "tv")
		hashMap.put("h", "getBandDetailInfo")
		hashMap.put("brandId", brandId)
		hashMap.put("modelId", modelId)
		launchRequest({ NetManager.mainHttp(hashMap) }, {
			val resultStr = AesUtils.decrypt(
				Constants.ZS_AES_KEY, it
			)
			
			if (TextUtils.isEmpty(resultStr)){
				return@launchRequest
			}
			val orderList = JSONArray.parseObject(resultStr, OrderListModel::class.java) as OrderListModel
			orderListModel.value = orderList
			
			Globals.log("xxxxx指令 resultStr："+resultStr)
		}, {
		
		})
	}
	
	
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
				Constants.ZS_AES_KEY, it
			)
			
			if (TextUtils.isEmpty(jsonStr)){
				return@launchRequest
			}
			resultStr.value = jsonStr
		}, {
		
		})
	}
}