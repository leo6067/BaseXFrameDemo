package com.xy.demo.ui.main

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xy.demo.MainActivity
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityLanguageBinding
import com.xy.demo.ui.vm.MainViewModel

class LaunchActivity : MBBaseActivity<ActivityLanguageBinding, MainViewModel>() {
	
	override fun getLayoutId(): Int {
		return R.layout.activity_launch
	}
	
	override fun initView() {
		super.initView()
		startActivity(Intent(this@LaunchActivity, MainActivity::class.java))
		finish()
	}
}