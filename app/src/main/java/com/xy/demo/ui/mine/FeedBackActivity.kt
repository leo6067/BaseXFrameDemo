package com.xy.demo.ui.mine

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityFeedBackBinding
import com.xy.demo.ui.vm.MainViewModel

class FeedBackActivity : MBBaseActivity<ActivityFeedBackBinding, MainViewModel>() {
	
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	override fun getLayoutId(): Int {
		return R.layout.activity_feed_back
	}
	
	override fun initView() {
		super.initView()
		titleBarView?.setTitle(getString(R.string.feedback))
		binding.positiveTV.setOnClickListener {
			finish()
		}
	}
}