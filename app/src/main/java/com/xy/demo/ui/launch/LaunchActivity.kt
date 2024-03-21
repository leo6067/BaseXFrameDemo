package com.xy.demo.ui.launch

import android.content.Intent
import androidx.lifecycle.lifecycleScope
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityLaunchBinding
import com.xy.demo.logic.LanguageUtil
import com.xy.demo.ui.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LaunchActivity : MBBaseActivity<ActivityLaunchBinding, MBBaseViewModel>() {
	
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
	override fun getLayoutId(): Int {
		LanguageUtil.reFreshLanguage(null, this, null)
		return R.layout.activity_launch
	}
	
	override fun initLogic() {
		super.initLogic()
		lifecycleScope.launch {
			delay(1000)
			startActivity(Intent(this@LaunchActivity, MainActivity::class.java))
			finish()
		}
	}
}