package com.xy.demo.ui.main

import android.content.Intent
import android.view.View
import com.xy.demo.R
import com.xy.demo.base.MBBaseFragment
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.FragmentAddBinding
import com.xy.demo.logic.ad.AdManage
import com.xy.demo.ui.dialog.CastDialog
import com.xy.demo.ui.infrared.AddWayActivity


// 没有添加过设备的首页UI
class AddFragment : MBBaseFragment<FragmentAddBinding, MBBaseViewModel>() {
	
	override fun getLayoutId(): Int {
		return R.layout.fragment_add
	}
	
	
	override fun initView() {
		binding.remoteLin.setOnClickListener {
			startActivity(Intent(activity, AddWayActivity::class.java))
		}
		
		binding.screenLin.setOnClickListener {
			activity?.let { it1 ->
				CastDialog().show(it1.supportFragmentManager, "1")
			}
		}
		
		AdManage.showBannerAd(binding.adView)
		
		
		binding.closeIV.setOnClickListener {
			binding.adView.visibility = View.GONE
			binding.closeIV.visibility = View.GONE
		}
		
	}
	
	
}