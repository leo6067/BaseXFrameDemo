package com.xy.demo.ui.infrared

import android.content.Intent
import android.view.View
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityAddRemoteBinding
import com.xy.demo.db.RemoteModel
import com.xy.demo.ui.vm.MainViewModel


//添加 遥控电器  红外或者wifi
class AddRemoteActivity : MBBaseActivity<ActivityAddRemoteBinding, MainViewModel>() {
	
	var remoteModel = RemoteModel()
	
	companion object {
		var activity: AddRemoteActivity? = null
	}
	
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_add_remote
	}
	
	override fun initView() {
		super.initView()
		
		notNetWorkLin = binding.netInclude.netLin
//		binding.knowTV.paint.flags = Paint.UNDERLINE_TEXT_FLAG
		activity = this
		
		
		binding.titleLay.titleTV.text = getString(R.string.connecting_way)
		
	}
	
	
	override fun onClick(view: View) {
		when (view.id) {
			R.id.backIV -> finish()
			
			R.id.startTV -> {
				remoteModel.type = 1
				val intent = Intent()
				intent.putExtra(Constants.KEY_REMOTE, remoteModel)
				intent.setClass(this@AddRemoteActivity, BrandActivity::class.java)
				startActivity(intent)
			}
		}
	}
}