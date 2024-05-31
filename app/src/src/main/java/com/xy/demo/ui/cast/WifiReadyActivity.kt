package com.xy.demo.ui.cast

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityWifiReadyBinding

class WifiReadyActivity : MBBaseActivity<ActivityWifiReadyBinding,MBBaseViewModel>() {
 
	
	override fun getLayoutId(): Int {
		return R.layout.activity_wifi_ready
	}
}