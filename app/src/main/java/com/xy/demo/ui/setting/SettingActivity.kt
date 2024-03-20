package com.xy.demo.ui.setting

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.xy.demo.R
import com.xy.demo.adapter.SettingAdapter
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivitySettingBinding
import com.xy.demo.model.SettingModel
import com.xy.demo.ui.dialog.RateDialog

class SettingActivity : MBBaseActivity<ActivitySettingBinding, MBBaseViewModel>() {
	
	
	var mAdapter = SettingAdapter()
	
	var dataList = ArrayList<SettingModel>()
	
	
	override fun showTitleBar(): Boolean {
		return super.showTitleBar()
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
	
	override fun getLayoutId(): Int {
		return R.layout.activity_setting
	}
	
	
	override fun initView() {
		
		titleBarView?.setTitle(getString(R.string.setting))
		titleBarView?.setBackgroundResource(R.drawable.root_bg)
		titleBarView?.setLeftIcon(R.drawable.ic_white_back)
		
		binding.recyclerView.layoutManager = LinearLayoutManager(this, RecyclerView.VERTICAL, false)
		binding.recyclerView.adapter = mAdapter
		
		dataList.add(SettingModel(R.drawable.icon_setting_fb, getString(R.string.feeback)))
		dataList.add(SettingModel(R.drawable.icon_setting_rate, getString(R.string.rate_us)))
		dataList.add(SettingModel(R.drawable.icon_setting_share, getString(R.string.share)))
		dataList.add(SettingModel(R.drawable.icon_setting_language, getString(R.string.language_options)))
		dataList.add(SettingModel(R.drawable.icon_setting_privacy, getString(R.string.privacy_policy)))
		
		mAdapter.setNewInstance(dataList)
		
		
		mAdapter.setOnItemClickListener { adapter, view, position ->
			when (position) {
				0 -> startActivity(Intent(this@SettingActivity, FeedBackActivity::class.java))  //反馈
				1 -> RateDialog().show(this@SettingActivity.supportFragmentManager, "1")
				2 -> shareApp() //分享
				3 -> startActivity(Intent(this@SettingActivity, LanguageActivity::class.java))  //语言
				4 -> {
					var intent = Intent()
					intent.setClass(this@SettingActivity, PrivacyActivity::class.java)
					startActivity(intent)  //隐私
				}
			}
		}
	}
	
	
	fun shareApp() {
		val textIntent = Intent(Intent.ACTION_SEND)
		textIntent.type = "text/plain"
		textIntent.putExtra(Intent.EXTRA_TEXT, "share app")
		startActivity(Intent.createChooser(textIntent, "share"))
	}
	
}