package com.xy.demo.ui.dialog

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.text.Html
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.view.Gravity
import com.bumptech.glide.Glide
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseDialogFragment
import com.xy.demo.databinding.DialogStartNextBinding
import com.xy.demo.db.RemoteModel
import com.xy.demo.ui.infrared.BrandActivity

class StartWayDialog : MBBaseDialogFragment<DialogStartNextBinding>() {
	
	
	var remoteModel = RemoteModel()
	override fun getLayoutId(): Int {
		return R.layout.dialog_start_next
	}
	
	override fun getGravity(): Int {
		return Gravity.BOTTOM
	}
	
	override fun initView() {
		
		
		// tag 直接传参  红外
		if (tag.toString() == "1") {
			
			remoteModel.type = 1
			Glide.with(this).load(R.drawable.way_ir).into(binding.iconIV)
			binding.titleTV.text = resources.getString(R.string.control_tv_via_ir)
 
			binding.srcTV.text = resources.getString(R.string.your_phone_needs_to_have)
			binding.srcTVB.text = resources.getString(R.string.built_in_ir_infrared_blaster)
		} else {
			
			remoteModel.type = 2
			Glide.with(this).load(R.drawable.way_wifi).into(binding.iconIV)
			binding.titleTV.text = resources.getString(R.string.control_tv_via_wi_fi)
			
			binding.srcTV.text = resources.getString(R.string.your_tv_and_phone_need_to_be)
			binding.srcTVB.text = resources.getString(R.string.on_the_same_wifi_network)
			
//			val style = SpannableStringBuilder(resources.getString(R.string.your_tv_and_phone_need_to_be_n_on_the_same_wifi_network))
////			   style.setSpan(new BackgroundColorSpan(Color.RED),2,5,Spannable.SPAN_EXCLUSIVE_INCLUSIVE);     //设置指定位置textview的背景颜色
//			style.setSpan(ForegroundColorSpan(resources.getColor(R.color.color_DDDDDD)), 0, 37, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)     //设置指定位置文字的颜色
//			style.setSpan(
//				ForegroundColorSpan(Color.WHITE),
//				38,
//				resources.getString(R.string.your_tv_and_phone_need_to_be_n_on_the_same_wifi_network).length,
//				Spannable.SPAN_EXCLUSIVE_INCLUSIVE
//			)     //设置指定位置文字的颜色
//			//粗体
//			style.setSpan(
//				StyleSpan(Typeface.BOLD_ITALIC), 38, resources.getString(R.string.your_tv_and_phone_need_to_be_n_on_the_same_wifi_network).length,
//				Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
//			)
		
		}
		
		
	}
	
	override fun initListener() {
		binding.closeIV.setOnClickListener {
			dismiss()
		}
		
		binding.startTV.setOnClickListener {
			activity?.let { it1 ->
				val intent = Intent()
				intent.putExtra(Constants.KEY_REMOTE, remoteModel)
				intent.setClass(it1, BrandActivity::class.java)
				startActivity(intent)
			}
			dismiss()
		}
		
	}
}