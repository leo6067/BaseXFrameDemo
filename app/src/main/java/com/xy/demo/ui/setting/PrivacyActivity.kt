package com.xy.demo.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityPrivacyBinding

class PrivacyActivity : MBBaseActivity<ActivityPrivacyBinding,MBBaseViewModel>() {
	
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
	override fun getLayoutId(): Int {
		return R.layout.activity_privacy
	}
	
	override fun initView() {
		super.initView()
		binding.titleLay.titleTV.text =  getString(R.string.privacy_policy)
		binding.webView.loadUrl(Constants.AGREEMENT_PRIVACY)
	}
}