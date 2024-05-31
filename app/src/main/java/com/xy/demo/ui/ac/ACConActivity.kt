package com.xy.demo.ui.ac

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.content.res.Configuration
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import androidx.core.view.ViewCompat
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityAcconBinding
import com.xy.demo.db.MyDataBase
import com.xy.demo.db.RemoteModel
import com.xy.demo.logic.ConsumerIrManagerApi
import com.xy.demo.logic.parse.ParamParse
import com.xy.demo.model.OrderListModel
import com.xy.demo.network.Globals
import com.xy.demo.ui.vm.HttpViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class ACConActivity : MBBaseActivity<ActivityAcconBinding, HttpViewModel>() {
	
	lateinit var remoteModel: RemoteModel
	
	var desiredProgress: Int = 26  //当前温度  默认温度
	var speedStr: String = ""
	var modeStr: String = ""
	var powerBoolean: Boolean = false
	
	var modeKey: String = "empty"  //模式
	var temperatureKey: String = "empty"  //温度
	var speedKey: String = "empty"  //风速
	var swingKey: String = "empty"  //扫风
 
	
	//所有指令
	var allCodeList = mutableListOf(OrderListModel.OrderModel())
	
	
	override fun showTitleBar(): Boolean = false
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
	override fun getLayoutId(): Int {
		return R.layout.activity_accon
	}
	
	
	override fun initView() {
		super.initView()
		
		notNetWorkLin = binding.netInclude.netLin
		if (intent.getSerializableExtra(Constants.KEY_REMOTE) != null) {
			remoteModel = intent.getSerializableExtra(Constants.KEY_REMOTE) as RemoteModel
			binding.titleLay.titleTV.text = remoteModel.brandName + " " + getString(R.string.air_conditioning)
		}
		
		showLoading()
		firstUi()
		
		viewModel.getACOrderListHttp(remoteModel.brandId, remoteModel.modelId)
	}
	
	
	fun firstUi() {
		Globals.log("XXXXXXXXXXXacOrderModel remoteModel.parameter-------" + remoteModel.toString())
		
		if (remoteModel.isNewAc == 1) {
			remoteModel.isNewAc = 0
			temperatureKey = "cool_26"
			speedStr = getString(R.string.auto)
			modeStr = getString(R.string.cool)
			speedKey = "wind_class_auto"
			modeKey = "mode_cool"
			
			composeParam(speedKey)
			composeParam(modeKey)
			composeParam(temperatureKey)
		}
		upViewUi()
	}
	
	fun upViewUi() {
		powerBoolean = if (remoteModel.isOpen == 1) {
			binding.powerIV.setBackgroundResource(R.drawable.ac_power)
			true
		} else {
			binding.powerIV.setBackgroundResource(R.drawable.ac_power_off)
			false
		}
		
		desiredProgress = remoteModel.tcInt
		
		doProgress()
		
		if (!powerBoolean) {
			binding.speedRG.clearCheck()
			binding.speedARB.isEnabled = false
			binding.speedBRB.isEnabled = false
			binding.speedCRB.isEnabled = false
			binding.speedDRB.isEnabled = false
			
			binding.modeRG.clearCheck()
			binding.modeARB.isEnabled = false
			binding.modeBRB.isEnabled = false
			binding.modeCRB.isEnabled = false
			binding.modeCRB.isEnabled = false
			binding.modeDRB.isEnabled = false
			binding.modeERB.isEnabled = false
			
			binding.swingARB.isChecked = false
			binding.swingARB.isEnabled = false
			binding.swingBRB.isChecked = false
			binding.swingBRB.isEnabled = false
			
			speedStr = getString(R.string.auto)
			modeStr = getString(R.string.cool)
			
			binding.windTV.text = modeStr + " | " + speedStr + " " + getString(R.string.fan_speed)
			return
		} else {
			binding.speedARB.isEnabled = true
			binding.speedBRB.isEnabled = true
			binding.speedCRB.isEnabled = true
			binding.speedDRB.isEnabled = true
			
			binding.modeARB.isEnabled = true
			binding.modeBRB.isEnabled = true
			binding.modeCRB.isEnabled = true
			binding.modeCRB.isEnabled = true
			binding.modeDRB.isEnabled = true
			binding.modeERB.isEnabled = true
			
			binding.swingARB.isEnabled = true
			binding.swingBRB.isEnabled = true
		}
		
		
		
		when (remoteModel.speedInt) {
			0 -> {
				binding.speedRG.clearCheck()
			}
			
			1 -> {
				binding.speedARB.isChecked = true
				speedStr = getString(R.string.auto)
				speedKey = "wind_class_auto"
			}
			
			2 -> {
				binding.speedBRB.isChecked = true
				speedStr = getString(R.string.low)
				speedKey = "wind_class_low"
			}
			
			3 -> {
				binding.speedCRB.isChecked = true
				speedStr = getString(R.string.medium)
				speedKey = "wind_class_medium"
			}
			
			4 -> {
				binding.speedDRB.isChecked = true
				speedStr = getString(R.string.high)
				speedKey = "wind_class_high"
			}
		}
		
		when (remoteModel.modeInt) {
			0 -> {
				binding.modeRG.clearCheck()
				modeKey = ""
			}
			
			1 -> {
				binding.modeARB.isChecked = true
				modeStr = getString(R.string.cool)
				modeKey = "mode_cool"
			}
			
			2 -> {
				binding.modeBRB.isChecked = true
				modeStr = getString(R.string.heating)
				modeKey = "mode_heat"
			}
			
			3 -> {
				binding.modeCRB.isChecked = true
				modeStr = getString(R.string.ventilate)
				modeKey = "mode_fan"
				temperatureKey = ""
			}
			
			4 -> {
				binding.modeDRB.isChecked = true
				modeStr = getString(R.string.dry)
				modeKey = "mode_dry"
				temperatureKey = ""
			}
			
			5 -> {
				binding.modeERB.isChecked = true
				modeStr = getString(R.string.auto)
				modeKey = "mode_auto"
				temperatureKey = ""
			}
		}
		
		
		
		when (remoteModel.swingInt) {
			0 -> {
				binding.swingARB.isChecked = false
				binding.swingBRB.isChecked = false
			}
			
			1 -> {
				swingKey = "wind_vertical"
				binding.swingARB.isChecked = true
			}
			
			2 -> {
				swingKey = "wind_horizontal"
				binding.swingBRB.isChecked = true
			}
		}
		
		doTopUI()
		
	}
	
	
	override fun initViewObservable() {
		super.initViewObservable()
		viewModel.orderListModel.observe(this) {
			dismissLoading()
			allCodeList = it.list
		}
		
		//风速
		binding.speedRG.setOnCheckedChangeListener { radioGroup, i ->
			if (!powerBoolean) {
				return@setOnCheckedChangeListener
			}
			when (i) {
				R.id.speedARB -> {
					speedStr = getString(R.string.auto)
					speedKey = "wind_class_auto"
					remoteModel.speedInt = 1
				}
				
				R.id.speedBRB -> {
					speedStr = getString(R.string.low)
					speedKey = "wind_class_low"
					remoteModel.speedInt = 2
				}
				
				R.id.speedCRB -> {
					speedStr = getString(R.string.medium)
					speedKey = "wind_class_medium"
					remoteModel.speedInt = 3
				}
				
				R.id.speedDRB -> {
					speedStr = getString(R.string.high)
					speedKey = "wind_class_high"
					remoteModel.speedInt = 4
				}
			}
			composeParam(speedKey)
			doTopUI()
		}
		
		
		binding.modeRG.setOnCheckedChangeListener { radioGroup, i ->
			
			if (!powerBoolean) {
				return@setOnCheckedChangeListener
			}
			when (i) {
				R.id.modeARB -> {
					modeStr = getString(R.string.cool)
					modeKey = "mode_cool"
					remoteModel.modeInt = 1
				}
				
				R.id.modeBRB -> {
					modeStr = getString(R.string.heating)
					modeKey = "mode_heat"
					remoteModel.modeInt = 2
				}
				
				R.id.modeCRB -> {
					modeStr = getString(R.string.ventilate)
					modeKey = "mode_fan"
					temperatureKey = ""
					remoteModel.modeInt = 3
				}
				
				R.id.modeDRB -> {
					modeStr = getString(R.string.dry)
					modeKey = "mode_dry"
					temperatureKey = ""
					remoteModel.modeInt = 4
				}
				
				R.id.modeERB -> {
					modeStr = getString(R.string.auto)
					modeKey = "mode_auto"
					temperatureKey = ""
					remoteModel.modeInt = 5
				}
			}
			composeParam(modeKey)
			doTopUI()
		}


//		binding.swingRG.setOnCheckedChangeListener { radioGroup, i ->
//			Globals.log("xxxxxxxxxxbinding.swingRG")
//			when (i) {
//				R.id.swingARB -> {
//					Globals.log("xxxxxxxxxxbinding.swingRG--------")
//					swingKey = "wind_vertical"
//				}
//
//				R.id.swingBRB -> {
//					swingKey = "wind_horizontal"
//				}
//			}
//			composeParam(swingKey)
//			doTopUI()
//		}
		
		
		binding.swingARB.setOnClickListener {
			if (!powerBoolean) {
				return@setOnClickListener
			}
			swingKey = "wind_vertical"
			composeParam(swingKey)
			doTopUI()
			binding.swingBRB.isChecked = false
			remoteModel.swingInt = 1
		}
		
		
		binding.swingBRB.setOnClickListener {
			if (!powerBoolean) {
				
				return@setOnClickListener
			}
			swingKey = "wind_horizontal"
			composeParam(swingKey)
			doTopUI()
			binding.swingARB.isChecked = false
			remoteModel.swingInt = 2
		}
	}
	
	
	//修改温度
	fun doProgress() {
		binding.progressView.max = 14 // 设置最大进度为 15，因为 16-30 共有 15 个不同的值
		// 假设你想要设置进度为 25
		val scaledProgress = desiredProgress - 16 // 将值缩放到 0-15 的范围内
		if (scaledProgress >= 0 && scaledProgress <= 15) {
			binding.progressView.progress = scaledProgress
		}
		binding.thermometerTV.text = desiredProgress.toString()
	}
	
	//修改温度
	fun doTopUI() {
		binding.windTV.text = modeStr + " | " + speedStr + " " + getString(R.string.fan_speed)
	}
	
	
	//常规指令
	override fun onClick(v: View) {
		when (v) {
			binding.titleLay.backIV -> finish()
			binding.netInclude.titleLay.backIV -> finish()
			binding.adTCubTV -> {
				if (!powerBoolean) {
					return
				}
				//只有制冷 制热模式 温度控制有效
				if (modeKey == "mode_cool" || modeKey == "mode_heat") {
					if (desiredProgress > 16) {
						desiredProgress--
						doProgress()
					}
					makeParam()
				}
			}
			
			binding.adTAddTV -> {
				if (!powerBoolean) {
					return
				}
				//只有制冷 制热模式 温度控制有效
				if (modeKey == "mode_cool" || modeKey == "mode_heat") {
					if (desiredProgress < 30) {
						desiredProgress++
						doProgress()
					}
					makeParam()
				}
			}
			
			binding.powerIV -> {
				if (powerBoolean) {
					binding.powerIV.setBackgroundResource(R.drawable.ac_power_off)
					powerBoolean = false
					composeParam("power_off")
					remoteModel.isOpen = 0
					upViewUi()
				} else {  //开机
					binding.powerIV.setBackgroundResource(R.drawable.ac_power)
					powerBoolean = true
					composeParam("power")
					remoteModel.isOpen = 1
					upViewUi()
				}
			}
		}
	}
	
	
	//常规指令
	fun makeParam() {
		when (desiredProgress) {
			16 -> {
				if (modeKey == "mode_cool") temperatureKey = "cool_16" else if (modeKey == "mode_heat") temperatureKey = "heat_16"
				remoteModel.tcInt = 16
			}
			
			17 -> {
				if (modeKey == "mode_cool") temperatureKey = "cool_17" else if (modeKey == "mode_heat") temperatureKey = "heat_17"
				remoteModel.tcInt = 17
			}
			
			18 -> {
				if (modeKey == "mode_cool") temperatureKey = "cool_18" else if (modeKey == "mode_heat") temperatureKey = "heat_18"
				remoteModel.tcInt = 18
			}
			
			19 -> {
				if (modeKey == "mode_cool") temperatureKey = "cool_19" else if (modeKey == "mode_heat") temperatureKey = "heat_19"
				remoteModel.tcInt = 19
			}
			
			20 -> {
				if (modeKey == "mode_cool") temperatureKey = "cool_20" else if (modeKey == "mode_heat") temperatureKey = "heat_20"
				remoteModel.tcInt = 20
			}
			
			21 -> {
				if (modeKey == "mode_cool") temperatureKey = "cool_21" else if (modeKey == "mode_heat") temperatureKey = "heat_21"
				remoteModel.tcInt = 21
			}
			
			22 -> {
				if (modeKey == "mode_cool") temperatureKey = "cool_22" else if (modeKey == "mode_heat") temperatureKey = "heat_22"
				remoteModel.tcInt = 22
			}
			
			23 -> {
				if (modeKey == "mode_cool") temperatureKey = "cool_23" else if (modeKey == "mode_heat") temperatureKey = "heat_23"
				remoteModel.tcInt = 23
			}
			
			24 -> {
				if (modeKey == "mode_cool") temperatureKey = "cool_24" else if (modeKey == "mode_heat") temperatureKey = "heat_24"
				remoteModel.tcInt = 24
			}
			
			25 -> {
				if (modeKey == "mode_cool") temperatureKey = "cool_25" else if (modeKey == "mode_heat") temperatureKey = "heat_25"
				remoteModel.tcInt = 25
			}
			
			26 -> {
				if (modeKey == "mode_cool") temperatureKey = "cool_26" else if (modeKey == "mode_heat") temperatureKey = "heat_26"
				remoteModel.tcInt = 26
			}
			
			27 -> {
				if (modeKey == "mode_cool") temperatureKey = "cool_27" else if (modeKey == "mode_heat") temperatureKey = "heat_27"
				remoteModel.tcInt = 27
			}
			
			28 -> {
				if (modeKey == "mode_cool") temperatureKey = "cool_28" else if (modeKey == "mode_heat") temperatureKey = "heat_28"
				remoteModel.tcInt = 28
			}
			
			29 -> {
				if (modeKey == "mode_cool") temperatureKey = "cool_29" else if (modeKey == "mode_heat") temperatureKey = "heat_29"
				remoteModel.tcInt = 29
			}
			
			30 -> {
				if (modeKey == "mode_cool") temperatureKey = "cool_30" else if (modeKey == "mode_heat") temperatureKey = "heat_30"
				remoteModel.tcInt = 30
			}
		}
		
		composeParam(temperatureKey)
	}
	
	
	fun composeParam(orderKey: String) {
		for (p in allCodeList.indices) {
			if (allCodeList[p].remoteKey == orderKey
//				allCodeList[p].remoteKey == modeKey
//				|| allCodeList[p].remoteKey == temperatureKey
//				|| allCodeList[p].remoteKey == speedKey
//				|| allCodeList[p].remoteKey == swingKey
			) {
				val irInfo = ParamParse.getIrCodeList(allCodeList[p].remoteCode, allCodeList[p].frequency.toInt())
				//最终 红外 指令
				try {
					ConsumerIrManagerApi.getConsumerIrManager(this).transmit(irInfo.getFrequency(), irInfo.getIrCodeList())
				} catch (e: Exception) {
				}
			}
		}
		
		
	}
	
	
	override fun onPause() {
		super.onPause()

//		val gson = Gson()
//		val jsonString = gson.toJson(acOrderModel)
//		remoteModel.parameter = jsonString
//
//		Globals.log("XXXXXXXXXXXacOrderModel------" + remoteModel.parameter)
		Globals.log("XXXXXXXXXXXacOrderModel*****------" + remoteModel.toString())
		
		GlobalScope.launch(Dispatchers.IO) {
			if (MyDataBase.instance.RemoteDao().getByName(remoteModel.name) == null) {
				MyDataBase.instance.RemoteDao().insert(remoteModel)  // 插入数据
			} else {
				MyDataBase.instance.RemoteDao().update(remoteModel)
			}
		}
	}
	
	
	//点击震动
	// 检查 VIBRATE 权限是否已授予
	fun touchVibrator() {
		if (checkSelfPermission(Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
			// 权限已授予，可以安全地使用振动器
			val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
			if (vibrator.hasVibrator()) {    // 设备有振动器，触发振动
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
					vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
				} else {
					vibrator.vibrate(200)
				}
			}
		} else {
			// 权限未授予，但对于 VIBRATE，我们不需要在这里请求它，因为它是一个正常权限
			// 如果你的应用有其他需要在运行时请求的权限，你可以在这里请求它们
		}
	}
}

