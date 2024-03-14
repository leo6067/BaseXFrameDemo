package com.xy.demo.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityFeedBackBinding

class FeedBackActivity : MBBaseActivity<ActivityFeedBackBinding,MBBaseViewModel>() {
	
	
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
		titleBarView?.setTitle("Feedback")
		titleBarView?.tvTitle?.setTextColor(getColor(R.color.black))
	}
}