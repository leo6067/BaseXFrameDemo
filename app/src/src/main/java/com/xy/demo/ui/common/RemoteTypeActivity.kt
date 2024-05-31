package com.xy.demo.ui.common

import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityRemoteTypeBinding



//RemoteTypeActivity-  -----BrandActivity ----  ReadyActivity 校验
class RemoteTypeActivity : MBBaseActivity<ActivityRemoteTypeBinding, MBBaseViewModel>() {
	
	
	companion object {
		var activity: RemoteTypeActivity? = null
		
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_remote_type
	}
	
	
	override fun initView() {
		super.initView()
		activity = this
		binding.titleLay.titleTV.text = getString(R.string.add_remote_a)
		binding.acLin.setOnClickListener {
			BrandActivity.newInstance(3, this@RemoteTypeActivity)
		}
		
		binding.tvLin.setOnClickListener {
//			AddRemoteActivity.newInstance(1, this@RemoteTypeActivity)
			BrandActivity.newInstance(1, this@RemoteTypeActivity)
		}
	}
}