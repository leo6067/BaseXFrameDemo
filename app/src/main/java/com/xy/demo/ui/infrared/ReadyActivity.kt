package com.xy.demo.ui.infrared

import android.content.Context
import android.content.Intent
import android.hardware.ConsumerIrManager
import android.os.Build
import android.view.View
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityReadyBinding
import com.xy.demo.db.RemoteModel
import com.xy.demo.ui.vm.HttpViewModel


//测试设备 准备
class ReadyActivity : MBBaseActivity<ActivityReadyBinding, HttpViewModel>() {
	
	lateinit var remoteModel: RemoteModel
	
	companion object {
		var activity: ReadyActivity ?=null
	}
	
	
	
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_ready
	}
	
	override fun onClick(view: View) {
		when (view.id) {
			R.id.backIV -> finish()
			R.id.startTV -> {
				val intent = Intent()
				intent.putExtra(Constants.KEY_REMOTE, remoteModel)
				intent.setClass(this@ReadyActivity, TurnOnActivity::class.java)
				startActivity(intent)
		 
			}
			
			R.id.startWifiTV -> {
				val intent = Intent()
				intent.putExtra(Constants.KEY_REMOTE, remoteModel)
				intent.setClass(this@ReadyActivity, TurnOnActivity::class.java)
				startActivity(intent)
				
			}
			
		}
	}
	
	
	override fun initView() {
		super.initView()
		activity = this
		notNetWorkLin = binding.netInclude.netLin
		showLoading()
		remoteModel = intent.getSerializableExtra(Constants.KEY_REMOTE) as RemoteModel
		viewModel.getSubBrandListHttp(remoteModel.brandId)

//
//		var subModelList =
//			JSONArray.parseArray(JsonUtil.paramJson(this@ReadyActivity, "SubBrand.json"), SubBrandListModel::class.java) as ArrayList<SubBrandListModel>
//
	
	}
	
	
	override fun initViewObservable() {
		super.initViewObservable()
		 
		
		// 获取系统的红外遥控服务
		val  service = getApplicationContext().getSystemService(Context.CONSUMER_IR_SERVICE) as ConsumerIrManager
		
		
//		if (service.hasIrEmitter()){
//			binding.irLin.visibility = View.VISIBLE
//			binding.wifiLin.visibility = View.GONE
//		}else{
//			binding.irLin.visibility = View.GONE
//			binding.wifiLin.visibility = View.VISIBLE
//		}
		
		
		
		
		viewModel.subBrandListModel.observe(this) {
			dismissLoading()
			binding.regexNumTV.text = String.format(
				resources.getString(R.string.we_ve_found_5_remote_n_controls_for_your_acer_tv), it.list.size)
			
			if (it.list.size ==0){
				binding.irLin.visibility = View.GONE
				binding.wifiLin.visibility = View.VISIBLE
			}
		}
	}
}