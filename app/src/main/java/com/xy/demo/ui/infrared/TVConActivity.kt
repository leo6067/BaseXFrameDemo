package com.xy.demo.ui.infrared

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import androidx.annotation.RequiresApi
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
import com.xy.demo.ui.dialog.CastDialog
import com.xy.demo.ui.dialog.RemoteMoreDialog
import com.xy.demo.ui.dialog.RemoteNumberDialog
import com.xy.demo.ui.setting.FeedBackActivity
import com.xy.demo.ui.vm.HttpViewModel
import com.xy.xframework.utils.Globals
import com.xy.xframework.utils.ToastUtils


//电视 指令 UI  遥控器
class TVConActivity : MBBaseActivity<ActivityTvconBinding, HttpViewModel>() {
	
	lateinit var vibrator : Vibrator
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
	var numberCode = arrayOf("0","1", "2", "3", "4", "5", "6", "7", "8", "9")
	
	
	override fun showTitleBar(): Boolean = false
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_tvcon
	}
	
	
	@RequiresApi(Build.VERSION_CODES.O)
	@SuppressLint("ResourceAsColor", "UseCompatLoadingForDrawables")
	override fun initView() {
		super.initView()
		notNetWorkLin = binding.netInclude.netLin
		if (intent.getSerializableExtra(Constants.KEY_REMOTE) != null) {
			remoteModel = intent.getSerializableExtra(Constants.KEY_REMOTE) as RemoteModel
		}
		
		binding.titleLay.titleTV.text = remoteModel.name
		binding.titleLay.feedBackIV.visibility = View.VISIBLE
		binding.titleLay.rightIV.visibility = View.VISIBLE
		
		showLoading()
		viewModel.getOrderListHttp(remoteModel.brandId, remoteModel.modelId)
		
		 vibrator  = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
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
	
	
	@RequiresApi(Build.VERSION_CODES.O)
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
	@RequiresApi(Build.VERSION_CODES.O)
	fun functionParam(position: Int) {
		vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
		when (position) {
			-1 -> makeParam("select")
			0 -> makeParam("up")
			1 -> makeParam("right")
			2 -> makeParam("down")
			3 -> makeParam("left")
		}
	}
	
	//常规指令
	@RequiresApi(Build.VERSION_CODES.O)
	override fun onClick(v: View) {
		
		when (v) {
			binding.titleLay.backIV -> {
				finish()
			}
			
			binding.netInclude.titleLay.backIV -> {
				finish()
			}
	 
			binding.titleLay.feedBackIV -> {
				startActivity(Intent(this@TVConActivity,FeedBackActivity::class.java))
			}
			
			binding.titleLay.rightIV -> {
				CastDialog().show(supportFragmentManager, "1")
			}
			
			binding.powerTV -> {
				vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
				makeParam("power")
			}
			
			
			binding.moreTV -> {
//				vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
				RemoteMoreDialog(moreCodeList).show(supportFragmentManager, "1")
			}
			
			
			binding.muteTV -> {    // 静音
//				vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
//				makeParam("mute")
			}
			
			binding.backTV -> {
				vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
				makeParam("back")
			}
//
			binding.homeTV -> {
				vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
				makeParam("home")
			}
			
			binding.numberTV -> {
				if (numberCodeList.size > 0) {
					RemoteNumberDialog(numberCodeList).show(supportFragmentManager, "2")
				}
			}
//
			binding.volAddTV -> {
				makeParam("vole+")
				vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
			}
			
			binding.volSubTV -> {
				makeParam("vole-")
				vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
			}
			
			binding.channelAddTV -> {
				makeParam("channel_up")
				vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
			}
			
			binding.channelSubTV -> {
				makeParam("channel_down")
				vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
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