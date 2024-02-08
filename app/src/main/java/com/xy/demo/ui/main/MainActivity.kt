package com.xy.demo.ui.main

import android.Manifest
import android.content.Intent
import android.os.Build
import android.view.KeyEvent
import android.view.View
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.jeremyliao.liveeventbus.LiveEventBus
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityMainBinding
import com.xy.demo.db.MyDataBase
import com.xy.demo.logic.LanguageUtil
import com.xy.demo.logic.ad.AdManage
import com.xy.demo.ui.adapter.ControlAdapter
import com.xy.demo.ui.setting.SettingActivity
import com.xy.demo.ui.vm.MainViewModel
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.base.XBaseViewModel
import com.xy.xframework.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class MainActivity : MBBaseActivity<ActivityMainBinding, MainViewModel>() {
	
	
	override fun getLayoutId(): Int {
		LanguageUtil.reFreshLanguage(null, this, null)
		return R.layout.activity_main
	}
	
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
	override fun initView() {
		super.initView()
		
	}
	
	override fun onResume() {
		super.onResume()
		showLoading()
		val fragmentManager: FragmentManager = supportFragmentManager // 对于 AppCompatActivity
		val transaction: FragmentTransaction = fragmentManager.beginTransaction()
		
		lifecycleScope.launch(Dispatchers.IO) {
			val allRemote = MyDataBase.instance.RemoteDao().getAllRemote() as MutableList
			if (allRemote.size > 0) {
				transaction.replace(R.id.fragment_container, HomeFragment())
			} else {
				transaction.replace(R.id.fragment_container, AddFragment())
			}
			
			delay(1000)
			dismissLoading()
			transaction.commitAllowingStateLoss()
		}
	}
	
	override fun initParams() {
		super.initParams()
		

		binding.settingIV.setOnClickListener {
			startActivity(Intent(this@MainActivity, SettingActivity::class.java))
		}
		
		binding.closeIV.setOnClickListener {
			binding.adLin.visibility = View.GONE
			Constants.showMainTopBanner = false
		}
		
		
		binding.bottomCloseIV.setOnClickListener {
			binding.bottomLay.visibility = View.GONE
			Constants.showMainBottomBanner = false
		}
		
		
		
		if (Constants.showMainTopBanner) {
			AdManage.showBannerAd(binding.adView, binding.adLin)
		}
		
		if (Constants.showMainBottomBanner) {
			AdManage.showBannerAd(binding.bottomAdView, binding.bottomLay)
		}
		
		
		LiveEventBus
			.get<String>(Constants.EVENT_SCROLL_UP).observe(this) {
				GlobalScope.launch(Dispatchers.Main) {
					AdManage.showBannerAd(binding.bottomAdView, binding.bottomLay)
				}
			}
		
		
	}


//	fun showNotificationDialog() {
//		var permission: String? = Permission.NOTIFICATION_SERVICE
//		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//			permission = Manifest.permission.POST_NOTIFICATIONS
//		}
//		if (!XXPermissions.isGranted(activity, permission)) {
//			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
//				XXPermissions.with(activity).permission(permission).request(OnPermissionCallback { permissions, allGranted -> })
//			} else {
//				NotificationDialog(activity).showAllowingStateLoss()
//			}
//		}
//	}
	
	
	override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true)
//			if (System.currentTimeMillis() - backTime < 2000) {
////					moveTaskToBack(true)
////					System.exit(0)
//
//			} else {
//				ToastUtils.showShort("再次点击退出app")
//			}
//			backTime = System.currentTimeMillis()
			return true
		}
		return super.onKeyDown(keyCode, event)
	}
	
	
}