package com.xy.demo.ui

import android.animation.Animator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.lifecycle.lifecycleScope
import com.airbnb.lottie.LottieDrawable
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityClearScanBinding
import com.xy.demo.utils.loadAssetLottieDrawable
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.Timer
import kotlin.concurrent.timerTask

class ClearScanActivity : MBBaseActivity<ActivityClearScanBinding, MBBaseViewModel>() {
	
	var progressInt: Int = 0
	
	override fun showTitleBar(): Boolean {
		return super.showTitleBar()
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
	override fun getLayoutId(): Int {
		return R.layout.activity_clear_scan
	}
	
	override fun initView() {
		super.initView()
		
		binding.lottieIV.setAnimation("clearing.json");
// lottieAnimationView01.loop(true);//循环播放动画，已经废弃，但是还可以使用，建议使用下面的两行代码
//		binding.lottieIV.setRepeatMode(LottieDrawable.REVERSE);//设置播放模式
		binding.lottieIV.setRepeatCount(0);//设置重复次数
		binding.lottieIV.playAnimation()
		
		binding.progressTV.text = ""
		
		val timer = Timer()
		timer.schedule(timerTask {
			if (progressInt != 100) {
				progressInt += 1
				lifecycleScope.launch(Dispatchers.Main) {
					binding.progressTV.text = "$progressInt"
					if (progressInt == 100) {
						delay(1000)
						binding.progressTV.visibility = View.GONE
					}
				}
			} else {
				timer.cancel()
			}
		}, 0, 40)
		
		
	}
}