package com.xy.demo.ui.setting

import android.content.Intent
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivitySettingBinding
import com.xy.demo.logic.LanguageUtil
import com.xy.demo.model.SettingModel
import com.xy.demo.ui.adapter.SettingAdapter
import com.xy.demo.ui.dialog.RateDialog
import com.xy.xframework.utils.Globals
import com.xy.xframework.utils.PackageUtils
import com.xy.xframework.web.WebBaseActivity


class SettingActivity : MBBaseActivity<ActivitySettingBinding, MBBaseViewModel>() {
 
	
	var mAdapter = SettingAdapter()
	
	var dataList = ArrayList<SettingModel>()
	
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_setting
	}
	
	override fun initView() {
		super.initView()
		
		titleBarView?.tvTitle?.text = getString(R.string.settings)
		
		this.mRecyclerView = binding.recyclerView
		initRecycler(1,1,1)
		mRecyclerView?.adapter = mAdapter
		
		dataList.add(SettingModel(R.drawable.icon_setting_feedback, getString(R.string.feedback),
			getString(R.string.report_bugs_and_tell_us_what_to_improve)))
		dataList.add(SettingModel(R.drawable.icon_setting_pate, getString(R.string.rate_us), getString(R.string.like_this_app_please_rate_us)))
		dataList.add(SettingModel(R.drawable.icon_setting_share, getString(R.string.share),
			getString(R.string.share_tv_remote_control_with_your_friends)))
		
		
		when (LanguageUtil.getLanguage()) {
			"en" -> dataList.add(SettingModel(R.drawable.icon_setting_language, getString(R.string.language_options),"English"))
			"zh" -> dataList.add(SettingModel(R.drawable.icon_setting_language, getString(R.string.language_options),"中文"))
			"tw" -> dataList.add(SettingModel(R.drawable.icon_setting_language, getString(R.string.language_options),"繁体"))
			"ja" -> dataList.add(SettingModel(R.drawable.icon_setting_language, getString(R.string.language_options),"しろうと"))
			"ko" -> dataList.add(SettingModel(R.drawable.icon_setting_language, getString(R.string.language_options),"한국어 "))
		}
		
		
	 
		
	
		dataList.add(SettingModel(R.drawable.icon_setting_privacy, getString(R.string.privacy_policy),""))
		
		mAdapter.setNewInstance(dataList)
		
		binding.versionTV.text = "version "+PackageUtils.getInstance().getVersionName(this)
		
		
		mAdapter.setOnItemClickListener { adapter, view, position ->
			  when (position) {
				0 -> startActivity(Intent(this@SettingActivity, FeedBackActivity::class.java))  //反馈
				1 -> RateDialog().show(supportFragmentManager, "1")
				2 -> shareApp() //分享
				3 -> startActivity(Intent(this@SettingActivity, LanguageActivity::class.java))  //语言
				4 ->{
					var intent = Intent()
					intent.setClass(this@SettingActivity, PrivacyActivity::class.java)
				    startActivity(intent)  //隐私
				}
			}
		}
	}
	
 
	
	
	fun shareApp(){
		val textIntent = Intent(Intent.ACTION_SEND)
		textIntent.type = "text/plain"
		textIntent.putExtra(Intent.EXTRA_TEXT, "share app")
		startActivity(Intent.createChooser(textIntent, "share"))
	}
	
	
	
}