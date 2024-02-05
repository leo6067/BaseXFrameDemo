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
import com.xy.demo.R
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


class MainActivity : MBBaseActivity<ActivityMainBinding, MainViewModel>() {
	
	var backTime: Long = 0
	
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
			transaction.commit()
		}
		AdManage.showBannerAd(binding.adView)
		
	}
	
	override fun initParams() {
		super.initParams()
		
		viewModel.getBrandListHttp()
		binding.settingIV.setOnClickListener {
			startActivity(Intent(this@MainActivity, SettingActivity::class.java))
		}
		
		binding.closeIV.setOnClickListener {
			binding.adView.visibility =View.GONE
			binding.closeIV.visibility =View.GONE
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