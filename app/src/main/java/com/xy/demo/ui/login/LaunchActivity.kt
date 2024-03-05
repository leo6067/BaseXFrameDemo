package com.xy.demo.ui.login

import android.content.Intent
import com.alibaba.fastjson.JSON
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityLaunchBinding
import com.xy.demo.logic.LanguageUtil
import com.xy.demo.logic.ad.AdManage
import com.xy.demo.ui.infrared.TVConActivity
import com.xy.demo.ui.main.MainActivity
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.utils.ToastUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject

class LaunchActivity : MBBaseActivity<ActivityLaunchBinding, MBBaseViewModel>() {
	
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		LanguageUtil.reFreshLanguage(null, this, null)
		return R.layout.activity_launch
	}
	
	
	override fun initView() {
		super.initView()

//		GlobalScope.launch(Dispatchers.Main) {
//			if (firstEntry) {
//				binding.progressView.visibility = View.VISIBLE
//				binding.entryTV.visibility = View.GONE
//				BaseSharePreference.instance.putBoolean("SHARE_FIRST", false)
//				delay(3000)
//				binding.progressView.visibility = View.GONE
//				binding.entryTV.visibility = View.VISIBLE
//
//			} else {
//				binding.progressView.visibility = View.GONE
//				binding.entryTV.visibility = View.VISIBLE
//			}
//		}

//		AdManage.loadOpenAd(this@LaunchActivity)
		
		startActivity(Intent(this@LaunchActivity, MainActivity::class.java))
	}
	
	
}