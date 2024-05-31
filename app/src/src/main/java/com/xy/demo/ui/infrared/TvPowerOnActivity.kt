package com.xy.demo.ui.infrared

import android.content.Intent
import android.view.View
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityPowerOnBinding


// 询问电视 是否开机
class TvPowerOnActivity : MBBaseActivity<ActivityPowerOnBinding, MBBaseViewModel>() {
	
	
	companion object {
		var activity: TvPowerOnActivity? = null
	}
	
	
	override fun initView() {
		super.initView()
		activity = this
		
		binding.titleLay.titleTV.text = getString(R.string.test_remote)
	}
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_power_on
	}
	
	
	override fun onClick(view: View) {
		when (view.id) {
			R.id.backIV -> finish()
			R.id.closeTV -> finish()
			R.id.nextTV -> {
				val remoteModel = intent.getSerializableExtra(Constants.KEY_REMOTE)
				val intent = Intent()
				intent.putExtra(Constants.KEY_REMOTE, remoteModel)
				intent.setClass(this@TvPowerOnActivity, TestOrderActivity::class.java)
				startActivity(intent)
			}
		}
	}
}