package com.xy.demo.ui.dialog

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.view.Gravity
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseDialogFragment
import com.xy.demo.databinding.DialogCastBinding
import com.xy.demo.ui.cast.ScreenMirrorActivity
import com.xy.demo.ui.cast.SearchWifiActivity


class CastDialog : MBBaseDialogFragment<DialogCastBinding>() {
	
	

	
	
	override fun getLayoutId(): Int {
		return R.layout.dialog_cast
	}
	
	override fun getGravity(): Int {
		return Gravity.BOTTOM
	}
	
	override fun initView() {
	
	
	}
	
	
	
	
	
	override fun initListener() {
		binding.photoTV.setOnClickListener {
			val intent = Intent()
			intent.putExtra(Constants.KEY_FILE_TYPE, "image/jpeg")
			activity?.let { it1 -> intent.setClass(it1, SearchWifiActivity::class.java) }
			startActivity(intent)
			dismiss()
		}
		
		binding.videoTV.setOnClickListener {
			val intent = Intent()
			intent.putExtra(Constants.KEY_FILE_TYPE, "video/mp4")
			activity?.let { it1 -> intent.setClass(it1, SearchWifiActivity::class.java) }
			startActivity(intent)
			dismiss()
		}
		
		binding.audioTV.setOnClickListener {

//			val intent = Intent(Intent.ACTION_GET_CONTENT)
//			intent.setType("audio/mp4a-latm|audio/mpeg|video/mp4|audio/x-wav");
//			intent.addCategory(Intent.CATEGORY_OPENABLE)
//			startActivity(intent)
			
			
			val intent = Intent()
			intent.putExtra(Constants.KEY_FILE_TYPE, "audio/x-wav")
			activity?.let { it1 -> intent.setClass(it1, SearchWifiActivity::class.java) }
			startActivity(intent)
			dismiss()
		}
		
		binding.imageTV.setOnClickListener {
			val intent = Intent()
			intent.putExtra(Constants.KEY_FILE_TYPE, "video/mp4")
			activity?.let { it1 -> intent.setClass(it1, SearchWifiActivity::class.java) }
			startActivity(intent)
			dismiss()
		}
		
		binding.closeIV.setOnClickListener {
			dismiss()
		}
		
		
		binding.castLay.setOnClickListener {
			startActivity(Intent(requireActivity(), ScreenMirrorActivity::class.java))
			dismiss()
		}
		
	}
	
	
}