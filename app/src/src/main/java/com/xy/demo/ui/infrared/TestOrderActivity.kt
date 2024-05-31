package com.xy.demo.ui.infrared

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.VibrationEffect
import android.os.Vibrator
import android.view.View
import androidx.annotation.RequiresApi
import com.alibaba.fastjson.JSONArray
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityTestRemoteBinding
import com.xy.demo.db.RemoteModel
import com.xy.demo.logic.ConsumerIrManagerApi
import com.xy.demo.logic.JsonUtil
import com.xy.demo.logic.parse.ParamParse
import com.xy.demo.model.SubBrandListModel
import com.xy.demo.ui.vm.HttpViewModel
import java.lang.Exception


//开始测试指令 是否有效
class TestOrderActivity : MBBaseActivity<ActivityTestRemoteBinding, HttpViewModel>() {
	
	var totalNum = 2
	var indexNum = 1
	var remoteModel = RemoteModel()
	lateinit var subModelList: MutableList<SubBrandListModel.SubBrandModel>
	
	companion object {
		var activity: TestOrderActivity? = null
	}
	
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
	override fun getLayoutId(): Int {
		return R.layout.activity_test_remote
	}
	
	
	@SuppressLint("SetTextI18n")
	override fun initView() {
		super.initView()
		activity = this
		
		notNetWorkLin = binding.netInclude.netLin
		remoteModel = intent.getSerializableExtra(Constants.KEY_REMOTE) as RemoteModel
		binding.titleLay.titleTV.text = getString(R.string.test) + " " + remoteModel.brandName + " " + getString(R.string.remote)
		binding.respondLay.visibility = View.GONE
		
		showLoading()
		viewModel.getSubBrandListHttp(remoteModel.brandId)
	}
	
	
	override fun initViewObservable() {
		super.initViewObservable()
		
		viewModel.subBrandListModel.observe(this) {
			dismissLoading()
			subModelList = it.list
			totalNum = it.list.size
			upNumUI()
		}
	}
	
	
	fun upNumUI() {
		binding.numberTV.text = "$indexNum/$totalNum"
	}
	
	
	override fun onClick(view: View) {
		when (view) {
			
			binding.netInclude.titleLay.backIV -> finish()
			binding.titleLay.backIV -> finish()
			
			binding.voiceIV -> {   //测试指令  根据下标 indexNum 取指令
				sendOrder()
				binding.respondLay.visibility = View.VISIBLE
				touchVibrator()
			}
			
			binding.leftIV -> {
				if (indexNum > 1) {
					indexNum--
					upNumUI()
				}
				
				if (indexNum == 1) {
					binding.leftIV.setImageResource(R.drawable.icon_test_left)
				} else {
					binding.leftIV.setImageResource(R.drawable.icon_test_left_a)
				}
				if (indexNum == totalNum) {
					binding.rightIVTV.setImageResource(R.drawable.icon_test_right_a)
				} else {
					binding.rightIVTV.setImageResource(R.drawable.icon_test_right)
				}
				
			}
			
			binding.rightIVTV -> {
				if (indexNum < totalNum) {
					indexNum++
					upNumUI()
				}
				
				if (indexNum == totalNum) {
					binding.rightIVTV.setImageResource(R.drawable.icon_test_right_a)
				} else {
					binding.rightIVTV.setImageResource(R.drawable.icon_test_right)
				}
				
				if (indexNum == 1) {
					binding.leftIV.setImageResource(R.drawable.icon_test_left)
				} else {
					binding.leftIV.setImageResource(R.drawable.icon_test_left_a)
				}
			}
			
			binding.invalidTV -> {
				if (indexNum < totalNum) {
					binding.rightIVTV.performClick()
				}
				
				binding.respondLay.visibility = View.GONE
			}
			
			binding.validTV -> {
				Constants.isHomeToSave = false
				val intent = Intent()
				intent.putExtra(Constants.KEY_REMOTE, remoteModel)
				intent.setClass(this@TestOrderActivity, SaveRemoteActivity::class.java)
				startActivity(intent)
			}
		}
	}
	
	
	//固定为  音量+ 指令
	fun sendOrder() {
		if (subModelList.size >= indexNum) {
			val subBrandModel = subModelList[indexNum - 1]
			remoteModel.modelId = subBrandModel.modelId
			val irInfo = ParamParse.getIrCodeList(subBrandModel.remoteCode, subBrandModel.frequency.toInt())
			try {
				ConsumerIrManagerApi.getConsumerIrManager(this).transmit(irInfo.getFrequency(), irInfo.getIrCodeList())
			} catch (e: Exception) {
//				binding.invalidTV.performClick()
			}
		}
	}
	
	
	//点击震动
	// 检查 VIBRATE 权限是否已授予
	fun touchVibrator() {
		if (checkSelfPermission(Manifest.permission.VIBRATE) == PackageManager.PERMISSION_GRANTED) {
			// 权限已授予，可以安全地使用振动器
			val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
			if (vibrator.hasVibrator()) {
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