package com.xy.demo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityProcessBinding

class ProcessActivity : MBBaseActivity<ActivityProcessBinding, MBBaseViewModel>() {
 
	
	override fun getLayoutId(): Int {
		return R.layout.activity_process
	}
	
	override fun initView() {
		super.initView()
 
		
	}
}