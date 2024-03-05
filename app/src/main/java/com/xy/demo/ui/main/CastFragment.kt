package com.xy.demo.ui.main

import android.content.Intent
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseFragment
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.FragmentCastBinding
import com.xy.demo.ui.cast.ScreenMirrorActivity
import com.xy.demo.ui.cast.SearchWifiActivity


class CastFragment : MBBaseFragment<FragmentCastBinding, MBBaseViewModel>() {
	
	override fun getLayoutId(): Int {
		return R.layout.fragment_cast
	}
	
	
	override fun initView() {
		
		binding.photoTV.setOnClickListener {
			val intent = Intent()
			intent.putExtra(Constants.KEY_FILE_TYPE, "image/jpeg")
			activity?.let { it1 -> intent.setClass(it1, SearchWifiActivity::class.java) }
			startActivity(intent)
		}
		
		binding.videoTV.setOnClickListener {
			val intent = Intent()
			intent.putExtra(Constants.KEY_FILE_TYPE, "video/mp4")
			activity?.let { it1 -> intent.setClass(it1, SearchWifiActivity::class.java) }
			startActivity(intent)
		}
		
		binding.musicTV.setOnClickListener {
			val intent = Intent()
			intent.putExtra(Constants.KEY_FILE_TYPE, "audio/x-wav")
			activity?.let { it1 -> intent.setClass(it1, SearchWifiActivity::class.java) }
			startActivity(intent)
		}
		
		binding.mirrorTV.setOnClickListener {
			startActivity(Intent(requireActivity(), ScreenMirrorActivity::class.java))
		}
	}
	
	
}