package com.xy.demo.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityFeedBackBinding
import com.xy.demo.ui.vm.HttpViewModel
import com.xy.xframework.utils.ToastUtils

class FeedBackActivity : MBBaseActivity<ActivityFeedBackBinding, HttpViewModel>() {
	
	
	override fun showTitleBar(): Boolean {
		return super.showTitleBar()
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_feed_back
	}
	
	
	override fun initView() {
		super.initView()
		titleBarView?.setTitle(getString(R.string.feedback))
		titleBarView?.tvTitle?.setTextColor(getColor(R.color.black))
		
		binding.submitTV.setOnClickListener {
			
			if (binding.contentET.text.toString().isEmpty()) {
				ToastUtils.showLong(getString(R.string.please_enter_feedback))
				return@setOnClickListener
			}
			
			
			showLoading()
//			viewModel.postFeedBack(binding.contentET.text )
		}
		
		
	}
	
	override fun initViewObservable() {
		super.initViewObservable()
		
		viewModel.resultStr.observe(this) {
			dismissLoading()
			finish()
		}
	}
}