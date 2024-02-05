package com.xy.demo.ui.infrared

import android.annotation.SuppressLint
import android.view.View
import com.alibaba.fastjson.JSONArray
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityTvconBinding
import com.xy.demo.db.RemoteModel
import com.xy.demo.logic.ConsumerIrManagerApi
import com.xy.demo.logic.JsonUtil
import com.xy.demo.logic.parse.ParamParse
import com.xy.demo.model.OrderListModel
import com.xy.demo.ui.dialog.RemoteMoreDialog
import com.xy.demo.ui.vm.MainViewModel
import com.xy.xframework.utils.Globals
import com.xy.xframework.utils.ToastUtils


//电视 指令 UI  遥控器
class TVConActivity : MBBaseActivity<ActivityTvconBinding, MainViewModel>() {
	
	
	lateinit var remoteModel: RemoteModel
	
	//所有指令
	var allCodeList = mutableListOf(OrderListModel.OrderModel())
	
	//常规 指令
	var commonCodeList = mutableListOf(OrderListModel.OrderModel())
	
	//数字 指令
	var numberCodeList = mutableListOf(OrderListModel.OrderModel())
	
	//更多指令
	var moreCodeList = mutableListOf(OrderListModel.OrderModel())
	
	var commonCode =
		arrayOf("power", "up", "down", "left", "right", "select", "home", "back", "menu", "vole+", "vole-", "channel_up", "channel_down", "mute")
	var numberCode = arrayOf("1", "2", "3", "4", "5", "6", "7", "8", "9", "0")
	
	
	override fun showTitleBar(): Boolean = false
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_tvcon
	}
	
	
	@SuppressLint("ResourceAsColor")
	override fun initView() {
		super.initView()
		
		titleBarView?.tvTitle?.text = "remote"
		titleBarView?.tvTitle?.setTextColor(R.color.color_333333)
		if (intent.getSerializableExtra(Constants.KEY_REMOTE) != null) {
			remoteModel = intent.getSerializableExtra(Constants.KEY_REMOTE) as RemoteModel
		}
		
		
		showLoading()
		viewModel.getOrderListHttp(remoteModel.brandId, remoteModel.modelId)
		
		
		functionClick()
	}
	
	override fun initViewObservable() {
		super.initViewObservable()
		viewModel.orderListModel.observe(this) {
			dismissLoading()
			
			paramList(it.list)
			allCodeList = it.list
		}
	}
	
	
	private fun paramList(orderList: MutableList<OrderListModel.OrderModel>) {
		numberCodeList.clear()
		moreCodeList.clear()
		for (item in orderList) {
			for (codeStr in commonCode) {   //筛取 常规 指令
				if (item.remoteKey.equals(codeStr)) {
					commonCodeList.add(item)
					break
				}
			}
			
			for (codeStr in numberCode) { //筛取 数字 指令
				if (item.remoteKey.equals(codeStr)) {
					Globals.log("xxxxxnumberCodeList"+item.remoteKey)
					numberCodeList.add(item)
					break
				}
			}
		}
		
		moreCodeList = (orderList - commonCodeList.toSet() - numberCodeList.toSet()).toMutableList()
		
		
		
	 
		
		if (numberCodeList.size == 0) {
			binding.numberTV.setBackgroundResource(R.drawable.shape_con_bg_b)
		}
		
		
		if (moreCodeList.size == 0) {
			binding.moreTV.isClickable = false
			binding.moreTV.setBackgroundResource(R.drawable.shape_con_bg_b)
		}
		
		
	}
	
	
	private fun functionClick() {
		// 统一lambda接口
		binding.functionLay.setOnMenuListener {
			onMenuClick { position ->
				// 单击
				functionParam(position)
			}
			
			onMenuLongClick { position ->
				// 长按
				functionParam(position)
			}
			
			onTouch { event, position ->
				// 触摸
				
			}
		}
	}
	
	
	//圆环
	fun functionParam(position: Int) {
		when (position) {
			-1 -> makeParam("select")
			0 -> makeParam("up")
			1 -> makeParam("right")
			2 -> makeParam("down")
			3 -> makeParam("left")
		}
	}
	
	//常规指令
	override fun onClick(v: View) {
		when (v) {
			
			binding.titleLay.backIV -> {
				finish()
			}
			
			binding.powerTV -> {
				makeParam("power")
				
			}
			
			
			binding.moreTV -> {
				RemoteMoreDialog(moreCodeList).show(supportFragmentManager, "1")
			}
			
			
			binding.muteTV -> {    // 静音
				makeParam("mute")
			}
			
			binding.backTV -> {
				makeParam("back")
				
			}
//
			binding.homeTV -> {
				makeParam("home")
			}
			
			binding.numberTV -> {
				if (numberCodeList.size > 0) {
					RemoteMoreDialog(numberCodeList).show(supportFragmentManager, "2")
				}
				
			}
//
			binding.volAddTV -> {
				makeParam("vole+")
				
			}
			
			binding.volSubTV -> {
				makeParam("vole-")
				
			}
			
			binding.channelAddTV -> {
				makeParam("channel_up")
				
			}
			
			binding.channelSubTV -> {
				makeParam("channel_down")
				
			}
		}
	}
	
	
	//常规指令
	fun makeParam(key: String) {
		for (p in commonCodeList.indices) {
			if (commonCodeList[p].remoteKey == key) {
				val irInfo = ParamParse.getIrCodeList(commonCodeList[p].remoteCode, commonCodeList[p].frequency.toInt())
				//最终 红外 指令
				ConsumerIrManagerApi.getConsumerIrManager(this).transmit(irInfo.getFrequency(), irInfo.getIrCodeList())
				Globals.log("xxxxx指令发送：", irInfo.getIrCodeList().toString())
			}
		}
	}
	
	
}