package com.xy.demo.ui.dialog

import android.view.Gravity
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
	
	override fun getMargin(): Int {
		val resources = context?.resources
		val density = resources?.displayMetrics?.density
		val pixels = Math.round(80 * density!!)   //两边间距 dp 40
		return pixels
	}
	
	override fun initView() {
		binding.rateBar.setOnRatingChangeListener { rating, numStars ->
			Globals.log("xxxxxxx" + Math.ceil(rating.toDouble()) + "    numStars  " + numStars)
		}
		
		binding.rateTV.setOnClickListener {
			dismiss()
		}
		
		binding.laterTV.setOnClickListener {
			dismiss()
		}
	}
	
	override fun initListener() {
	
	}
}