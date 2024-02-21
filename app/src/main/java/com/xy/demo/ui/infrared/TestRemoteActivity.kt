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
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityTestRemoteBinding
import com.xy.demo.db.RemoteModel
import com.xy.demo.logic.ConsumerIrManagerApi
import com.xy.demo.logic.JsonUtil
import com.xy.demo.logic.parse.ParamParse
import com.xy.demo.model.SubBrandListModel
import com.xy.demo.ui.vm.HttpViewModel
import com.xy.demo.ui.vm.MainViewModel
import com.xy.xframework.utils.Globals


//开始测试指令 是否有效
class TestRemoteActivity : MBBaseActivity<ActivityTestRemoteBinding, HttpViewModel>() {
	
	
	var totalNum = 2
	var indexNum = 1
	
	
	var remoteModel = RemoteModel()
	lateinit var subModelList: MutableList<SubBrandListModel.SubBrandModel>
	
	companion object {
		var activity: TestRemoteActivity ?=null
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
		binding.titleLay.titleTV.text = getString(R.string.test_acer_remote)
		binding.respondLay.visibility = View.GONE
		
		remoteModel = intent.getSerializableExtra(Constants.KEY_REMOTE) as RemoteModel
		
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
	
	
	@RequiresApi(Build.VERSION_CODES.O)
	override fun onClick(view: View) {
		val vibrator = getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
	 
		when (view.id) {
			R.id.backIV -> finish()
			R.id.voiceIV -> {   //测试指令  根据下标 indexNum 取指令
				sendOrder()
				binding.respondLay.visibility = View.VISIBLE
				vibrator.vibrate(VibrationEffect.createOneShot(200, VibrationEffect.DEFAULT_AMPLITUDE))
			}
			
			R.id.leftIV -> {
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
					binding.rightIV.setImageResource(R.drawable.icon_test_right_a)
				} else {
					binding.rightIV.setImageResource(R.drawable.icon_test_right)
				}
				
			}
			
			R.id.rightIV -> {
				if (indexNum < totalNum) {
					indexNum++
					upNumUI()
				}
				
				if (indexNum == totalNum) {
					binding.rightIV.setImageResource(R.drawable.icon_test_right_a)
				} else {
					binding.rightIV.setImageResource(R.drawable.icon_test_right)
				}
				
				if (indexNum == 1) {
					binding.leftIV.setImageResource(R.drawable.icon_test_left)
				} else {
					binding.leftIV.setImageResource(R.drawable.icon_test_left_a)
				}
			}
			
			R.id.invalidTV -> {
				if (indexNum < totalNum) {
					binding.rightIV.performClick()
				}
				
				binding.respondLay.visibility = View.GONE
			}
			
			R.id.validTV -> {
				Constants.isHomeToSave = false
				val intent = Intent()
				intent.putExtra(Constants.KEY_REMOTE, remoteModel)
				intent.setClass(this@TestRemoteActivity, SaveRemoteActivity::class.java)
				startActivity(intent)
			}
		}
		
		
	}
	
	
	//固定为  音量+ 指令
	fun sendOrder() {
		// 根据下标 取指令测试   remoteModel.json  外部进来 全品牌指令  indexNum
//		commandStr = remoteModel.json.get(indexNum).toString()
//		val codeModes = JSONArray.parseArray(commandStr, CodeMode::class.java)
//		for (p in codeModes.indices) {
//			if (codeModes[p].remoteKey == key) {
//				val irInfo = ParamParse.getIrCodeList(codeModes[p].remoteCode, codeModes[p].frequency.toInt())
//				//最终 红外 指令
//				ConsumerIrManagerApi.getConsumerIrManager(this).transmit(irInfo.getFrequency(), irInfo.getIrCodeList())
//				Globals.log("xxxxx指令发送：", irInfo.getIrCodeList().toString())
//			}
//		}
		if (subModelList.size >= indexNum){
			
			Globals.log("xxxxxxxxxxx"+indexNum)
			
			
			val subBrandModel = subModelList[indexNum-1]
			remoteModel.modelId = subBrandModel.modelId
			val irInfo = ParamParse.getIrCodeList(subBrandModel.remoteCode, subBrandModel.frequency.toInt())
			ConsumerIrManagerApi.getConsumerIrManager(this).transmit(irInfo.getFrequency(), irInfo.getIrCodeList())
		}
	}
	
	
}