package com.xy.demo.ui.dialog

import android.util.Log
import android.view.Gravity
import com.xingliuhua.xlhratingbar.XLHRatingBar.OnRatingChangeListener
import com.xy.demo.R
import com.xy.demo.base.MBBaseDialogFragment
import com.xy.demo.databinding.DialogRateBinding
import com.xy.demo.network.Globals


class RateDialog : MBBaseDialogFragment<DialogRateBinding>() {
	
	
	override fun getLayoutId(): Int {
		return R.layout.dialog_rate
	}
	
	override fun getGravity(): Int {
		return Gravity.CENTER
	}
	
	override fun initView() {
		
		binding.rateBar.setOnRatingChangeListener { rating, numStars ->
			
			Globals.log("xxxxxxx" + Math.ceil(rating.toDouble())+ "    numStars  " + numStars)
		}
		
	}
	
	override fun initListener() {
	
	}
}