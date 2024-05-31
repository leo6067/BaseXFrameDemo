package com.xy.demo.ui.cast

import android.content.ActivityNotFoundException
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.content.pm.ResolveInfo
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.view.View
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityScreenMirrorBinding
import com.xy.demo.ui.setting.FeedBackActivity
import com.xy.xframework.utils.ToastUtils

class ScreenMirrorActivity : MBBaseActivity<ActivityScreenMirrorBinding, MBBaseViewModel>() {
	
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		
		return R.layout.activity_screen_mirror
	}
	
	override fun initView() {
		super.initView()
		
		
		binding.titleLay.feedBackIV.visibility = View.VISIBLE
		
		binding.titleLay.titleTV.text = getString(R.string.screen_mirroring)
		
		
		val firstStrA = getString(R.string.make_sure_your_phone_and_tv_or_wireless_adapter_connect_to_the)
		val firstStrB = getString(R.string.same_wifi)
		val firstStrC = getString(R.string.and)
		val firstStrD = getString(R.string.turn_off_vpn)
		
		val builder = SpannableStringBuilder(firstStrA)
		
		builder.append(firstStrB)
		builder.setSpan(ForegroundColorSpan(Color.parseColor("#5582FC")), firstStrA.length, builder.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
		
		builder.append(firstStrC)
		builder.setSpan(
			ForegroundColorSpan(Color.parseColor("#000000")),
			(firstStrA + firstStrB).length,
			builder.length,
			Spannable.SPAN_EXCLUSIVE_INCLUSIVE
		)
		
		builder.append(firstStrD)
		builder.setSpan(
			ForegroundColorSpan(Color.parseColor("#5582FC")),
			(firstStrA + firstStrB + firstStrC).length,
			builder.length,
			Spannable.SPAN_EXCLUSIVE_INCLUSIVE
		)
		binding.firstTV.text = builder
		
		
		val secondStrA = getString(R.string.enable_the)
		val secondStrB = getString(R.string.miracast_display)
		val secondStrC = getString(R.string.function_on_your_tv_you_need_to_enable_it_manually_on_some_devices_go_to_system_settings_to_check)
		
		val builderB = SpannableStringBuilder(secondStrA)
		
		builderB.append(secondStrB)
		builderB.setSpan(ForegroundColorSpan(Color.parseColor("#5582FC")), secondStrA.length, builderB.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
		builderB.append(secondStrC)
		builderB.setSpan(
			ForegroundColorSpan(Color.parseColor("#000000")),
			(secondStrA + secondStrB).length,
			builderB.length,
			Spannable.SPAN_EXCLUSIVE_INCLUSIVE
		)
		binding.secondTV.text = builderB
		
 
		val thirdStrA = getString(R.string.click_the)
		val thirdStrB = getString(R.string.start_mirroring)
		val thirdStrC = getString(R.string.button_enable_the_wireless_display_function_on_your_phone_and_wait_for_the_device_searching)
		
		val builderC = SpannableStringBuilder(thirdStrA)
		
		builderC.append(thirdStrB)
		builderC.setSpan(ForegroundColorSpan(Color.parseColor("#5582FC")), thirdStrA.length, builderC.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE)
		builderC.append(thirdStrC)
		builderC.setSpan(
			ForegroundColorSpan(Color.parseColor("#000000")),
			(thirdStrA + thirdStrB).length,
			builderC.length,
			Spannable.SPAN_EXCLUSIVE_INCLUSIVE
		)
		binding.thirdTV.text = builderC
		
	}
	
	override fun onClick(view: View) {
		
		when (view) {
			binding.titleLay.backIV -> {
				finish()
			}
			
			binding.titleLay.feedBackIV -> {
				startActivity(Intent(this@ScreenMirrorActivity, FeedBackActivity::class.java))
			}
			
			
			binding.startTV -> {
				openScreenMirror()
			}
		}
	}
	
	
	val ACTION_WIFI_DISPLAY_SETTINGS = "android.settings.WIFI_DISPLAY_SETTINGS"
	val ACTION_CAST_SETTINGS = "android.settings.CAST_SETTINGS"
	
	
	fun openScreenMirror() {
		
		val wifiActionIntent: Intent = Intent(ACTION_WIFI_DISPLAY_SETTINGS)
		val castActionIntent: Intent = Intent(ACTION_CAST_SETTINGS)
		
		var systemResolveInfo: ResolveInfo? = getSystemResolveInfo(wifiActionIntent)
		if (systemResolveInfo != null) {
			try {
				val systemWifiIntent = Intent()
				systemWifiIntent.setClassName(
					systemResolveInfo.activityInfo.applicationInfo.packageName,
					systemResolveInfo.activityInfo.name
				)
				startSettingsActivity(systemWifiIntent)
			} catch (ignored: ActivityNotFoundException) {
			}
		}
		
		systemResolveInfo = getSystemResolveInfo(castActionIntent)
		if (systemResolveInfo != null) {
			try {
				val systemCastIntent = Intent()
				systemCastIntent.setClassName(
					systemResolveInfo.activityInfo.applicationInfo.packageName,
					systemResolveInfo.activityInfo.name
				)
				startSettingsActivity(systemCastIntent)
			} catch (ignored: ActivityNotFoundException) {
			}
		}
		
		if (systemResolveInfo == null) {
			ToastUtils.showShort(getString(R.string.the_screen_casting_settings_are_not_found_on_the_device))
		}
		
	}
	
	fun startSettingsActivity(intent: Intent) {
		intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
		try {
			startActivity(intent);
		} catch (e: SecurityException) {
			// We don't have permission to launch this activity, alert the user and return.
		}
	}
	
	
	private fun getSystemResolveInfo(intent: Intent): ResolveInfo? {
		val pm: PackageManager = getPackageManager()
		val list = pm.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY)
		for (info in list) {
			try {
				val activityInfo = pm.getApplicationInfo(
					info.activityInfo.packageName,
					0
				)
				if (activityInfo.flags and ApplicationInfo.FLAG_SYSTEM != 0) {
					return info
				}
			} catch (ignored: PackageManager.NameNotFoundException) {
			}
		}
		return null
	}
}