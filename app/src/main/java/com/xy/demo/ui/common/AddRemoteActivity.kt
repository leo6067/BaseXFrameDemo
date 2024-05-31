package com.xy.demo.ui.common

import android.app.Activity
import android.content.Intent
import android.view.View
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityAddRemoteBinding
import com.xy.demo.ui.vm.HttpViewModel



//
class AddRemoteActivity : MBBaseActivity<ActivityAddRemoteBinding, HttpViewModel>() {
	
	
	companion object {
		var activity: AddRemoteActivity? = null
		fun newInstance(fromType: Int, activity: Activity) {
			val intent = Intent()
			intent.putExtra(Constants.KEY_REMOTE_TYPE, fromType)
			intent.setClass(activity, AddRemoteActivity::class.java)
			activity.startActivity(intent)
		}
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
		when (view) {
			binding.titleLay.backIV -> finish()
			binding.netInclude.titleLay.backIV -> finish()
			binding.startTV -> {
				val intExtra = intent.getIntExtra(Constants.KEY_REMOTE_TYPE, 1)
				BrandActivity.newInstance(intExtra, this@AddRemoteActivity)
			}
		}
	}
}