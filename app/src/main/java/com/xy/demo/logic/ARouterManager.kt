package com.xy.demo.logic

import android.net.Uri
import com.alibaba.android.arouter.launcher.ARouter
import com.xy.xframework.utils.Globals
import java.net.URLDecoder
import java.net.URLEncoder

class ARouterManager {
	
	fun String.pageRouter(map: MutableMap<String, String>? = null): String {
		val build = StringBuilder(RouterManager.ROUTER_SCHEME).append(this)
		if (map != null && map.isNotEmpty()) {
			build.append("?")
			map.forEach { param ->
				build.append(param.key).append("=").append(URLEncoder.encode(param.value))
				build.append("&")
			}
		}
		var url = build.toString()
		if (url.substring(url.length - 1) == "&") {
			url = url.substring(0, url.length - 1)
		}
		return url
	}
	
	/**
	 * 获取路由参数
	 */
	fun String.getRouterParams():HashMap<String,String>{
		val map = HashMap<String,String>()
		try {
			val uri = Uri.parse(this)
			val params = uri.queryParameterNames
			if (!params.isNullOrEmpty()) {
				params.forEach { param ->
					var value = uri.getQueryParameter(param)
					if (!value.isNullOrBlank()) {
						value = URLDecoder.decode(value)
						map[param]=value
					}
				}
			}
		}catch (e:Exception){
			e.printStackTrace()
		}
		return map
	}
	
	object RouterManager {
		
		const val ROUTER_SCHEME: String = "xyApp:/"
		const val ROUTER_MAIN: String = "/app/MainActivity"
		const val ROUTER_ABOUT: String = "/app/AboutUsActivity"
		const val ROUTER_SPLASH: String = "/app/SplashActivity"
		const val ROUTER_WEB: String = "/app/WebActivity"
		const val ROUTER_GOODS_ORDER: String = "/app/GoodsOrder"
		const val ROUTER_ADDRESS_EDIT: String = "/app/AddressEditActivity"
		const val ROUTER_ADDRESS_LIST: String = "/app/AddressListActivity"
		const val ROUTER_ORDER_DETAIL: String = "/app/OrderDetailActivity"
		const val ROUTER_LOGIN: String = "/app/LoginActivity"
		const val ROUTER_BLIND_BOX_DETAIL: String = "/app/BlindBoxDetailActivity"
		const val ROUTER_GOODS_DETAIL: String = "/app/GoodsDetailActivity"
		const val ROUTER_CHARGE: String = "/app/ChargeActivity"
		const val ROUTER_LUCKY: String = "/app/LuckyActivity"
		const val ROUTER_USERDETAIL: String = "/app/UserDetailActivity"
		const val ROUTER_VIRTUREORDERDETAIL: String = "/app/VirtureOrderDetailActivity"
		const val ROUTER_PAY_SUCCESS: String = "/app/PaySuccessActivity"
		const val ROUTER_HOME_STORE: String = "/app/HomeStoreActivity"
		
		/**
		 *  路由解析
		 *  外部打开网页  magicbox://web?url=http://www.baidu.com
		 *  内部打开网页  magicbox://app/WebActivity?url=http://www.baidu.com
		 *  开屏页面     magicbox://app/SplashActivity
		 *  首页        magicbox://app/MainActivity?tabName=mallHome/boxHome/storeHome/myHome
		 *  关于我们页面  magicbox://app/AboutUsActivity
		 *  我到订单页面  magicbox://app/GoodsOrder
		 *  编辑收货地址  magicbox://app/AddressEditActivity?id=0&isDel=0
		 *  收货地址     magicbox://app/AddressListActivity
		 *  订单详情     magicbox://app/OrderDetailActivity?orderNos=0&goodsId=0&goodsNum=0&addressId=0
		 *  登录页面     magicbox://app/LoginActivity
		 *  盲盒详情     magicbox://app/BlindBoxDetailActivity?id=0&tabName=推荐
		 *  充值页面     magicbox://app/ChargeActivity?needChargeNum=111
		 *  商品详情     magicbox://app/GoodsDetailActivity?id=0&title=详情&tabName=推荐
		 *  开箱页面     magicbox://app/LuckyActivity?goodsId=0&orderNos=111&goodsPrice=222&isFive=true
		 * eg.
		 *  val map = mutableMapOf<String,String>()
		 *  map["pa1"]="a"
		 *  RouterManager.ROUTER_MAIN.pageRouter(map)
		 */
		fun routerPare(router: String) {
			Globals.log("路由router = $router")
			try {
				if (router.isNotEmpty()) {
					Globals.log("解析协议")
					if (router.startsWith(ARouterManager.RouterManager.ROUTER_SCHEME+"/")) {
						val uri = Uri.parse(router)
						val url = uri.getQueryParameter("url")
						val path = uri.path
						if (uri.host == "web" && !url.isNullOrBlank()) {
							Globals.log("解析协议")
							openBrowser(URLDecoder.decode(url))
							return
						} else if (uri.host == "app" && !path.isNullOrBlank()) {
							val postcard = ARouter.getInstance().build("/app${uri.path}")
							val params = uri.queryParameterNames
							if (!params.isNullOrEmpty()) {
								params.forEach { param ->
									var value = uri.getQueryParameter(param)
									if (!value.isNullOrBlank()) {
										val decodeValue = URLDecoder.decode(value)
										Globals.log("路由router key = $param , value = $decodeValue")
										postcard.withString(param, value)
									}
								}
							}
							Globals.log("解析协议")
							postcard.navigation()
							return
						}
					} else {
						Globals.log("请使用正规跳转协议")
						ARouter.getInstance().build(router).navigation()
					}
				} else {
					Globals.log("路由配置有问题")
				}
			} catch (e: Exception) {
				Globals.log("路由配置有问题"+e)
			}
		}
		
		/**
		 * 应用外浏览器打开
		 */
		private fun openBrowser(url: String) {
//			ActivityManager.getCurrentActivity()?.openBrowser(url)
		}
	}
}