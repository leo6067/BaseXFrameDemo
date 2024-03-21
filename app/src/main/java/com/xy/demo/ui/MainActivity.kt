package com.xy.demo.ui

import android.app.ActivityManager
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Process
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityMainBinding
import com.xy.demo.logic.LanguageUtil
import com.xy.demo.ui.file.BigFileActivity
import com.xy.demo.ui.setting.SettingActivity
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.base.XBaseViewModel
import com.xy.xframework.utils.ToastUtils
import java.io.File


class MainActivity : MBBaseActivity<ActivityMainBinding, XBaseViewModel>() {
	
	override fun getLayoutId(): Int {
		
		return R.layout.activity_main
	}
	
	override fun showTitleBar(): Boolean = false
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
	override fun onCreate(savedInstanceState: Bundle?) {
		if (BaseSharePreference.instance.getString("AppTheme", "light").equals("night")) {
			//设置夜晚主题  需要在setContentView之前
			setTheme(R.style.AppDarkTheme)
		} else {
			//设置白天主题
			setTheme(R.style.AppLightTheme);
		}
		super.onCreate(savedInstanceState)
	}
	
	
	override fun initView() {
		
		binding.lottieIV.setAnimation("main_scan.json");
// lottieAnimationView01.loop(true);//循环播放动画，已经废弃，但是还可以使用，建议使用下面的两行代码
//		binding.lottieIV.setRepeatMode(LottieDrawable.REVERSE);//设置播放模式
		binding.lottieIV.setRepeatCount(-1);//设置重复次数
		binding.lottieIV.playAnimation()
		
		
		val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
		val memoryInfo = ActivityManager.MemoryInfo()
		activityManager.getMemoryInfo(memoryInfo)
		val availableMemory = memoryInfo.availMem
		val totalMemory = memoryInfo.totalMem
		// 可用内存和总内存
		binding.memoryTV.text = ((availableMemory * 100 / totalMemory)).toInt().toString()
	}
	
	override fun onResume() {
		super.onResume()
		dismissLoading()
	}
	
	override fun initLogic() {
		super.initLogic()
		
		binding.settingIV.setOnClickListener {
			startActivity(Intent(this@MainActivity, SettingActivity::class.java))
		}
		
		
		binding.clearTV.setOnClickListener {
			startActivity(Intent(this@MainActivity, ClearScanActivity::class.java))
		}
		
		binding.bigClearTV.setOnClickListener {
			
			XXPermissions.with(this)
				.permission(Permission.MANAGE_EXTERNAL_STORAGE)
				.request(object : OnPermissionCallback {
					override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
						startActivity(Intent(this@MainActivity, BigFileActivity::class.java))   //大文件清理
					}
					
					override fun onDenied(permissions: MutableList<String>, allGranted: Boolean) {
						ToastUtils.showLong(getString(R.string.please_go_to_application_management_to_grant_permissions))
						var intent = Intent(Settings.ACTION_MANAGE_APP_ALL_FILES_ACCESS_PERMISSION);
						intent.setData(Uri.parse("package:" + getPackageName()));
						startActivity(intent);
					}
				})
			
		}
		
		binding.uninstallTV.setOnClickListener {
			showLoading()
			startActivity(Intent(this@MainActivity, AppManageActivity::class.java))
//			if (hasPermissionUsage()) {
//				showLoading()
//				startActivity(Intent(this@MainActivity, AppManageActivity::class.java))
//			} else {
//				startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
//			}
		}
		
		binding.processTV.setOnClickListener {
			if (hasPermissionUsage()) {
				startActivity(Intent(this@MainActivity, ProcessActivity::class.java))
			} else {
				startActivity(Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS))
			}
		}
		
		binding.noticeBT.setOnClickListener {
			var intent = Intent()
			intent.setAction(Settings.ACTION_NOTIFICATION_LISTENER_SETTINGS)
			startActivity(intent)
		}
		
	}
	
	fun hasPermissionUsage(): Boolean {
		
		//判断是否开启查看权限 	//手动跳转 授权 应用使用情况
		val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
		var mode = 0
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
			mode = appOps.checkOpNoThrow(
				AppOpsManager.OPSTR_GET_USAGE_STATS,
				Process.myUid(), packageName
			)
		}
		val granted = mode == AppOpsManager.MODE_ALLOWED
//		if (!granted) { startActivity( Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)) }
		return mode == AppOpsManager.MODE_ALLOWED
	}
	
	
	fun getRootDirPath(context: Context): String? {
		return if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()) {
			val file: File = ContextCompat.getExternalFilesDirs(
				context.applicationContext,
				null
			).get(0)
			file.absolutePath
		} else {
			context.applicationContext.filesDir.absolutePath
		}
	}
	
	
	/**
	 * 判断是否为第三方应用，并且有界面的应用
	 *
	 * @param context
	 * @param packageName
	 * @return true:第三方应用，并且有界面
	 */
	fun isUserApp(context: Context, packageName: String): Boolean {
		val names: MutableList<String> = ArrayList()
		val packageManager = context.packageManager
		val intent = Intent(Intent.ACTION_MAIN)
		intent.addCategory(Intent.CATEGORY_HOME)
		val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_DEFAULT_ONLY) //
		for (resolveInfo in list) {
			names.add(resolveInfo.activityInfo.packageName)
		}
		if (!names.contains(packageName)) {
			if (packageManager.getLaunchIntentForPackage(packageName) != null) {
				return true
			}
		}
		return false
	}
	
	
	/**
	 * Kill掉某个正在运行的应用
	 * @param context
	 * @param packageToKill
	 */
	private fun killAppByPackage(context: Context, packageToKill: String) {
		val packages: List<ApplicationInfo>
		val pm: PackageManager
		pm = context.packageManager
		//get a list of installed apps.
		packages = pm.getInstalledApplications(0)
		val mActivityManager = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
		
		//利用killBackgroundProcesses方法(API > 8)
		for (packageInfo in packages) {
			if (packageInfo.flags and ApplicationInfo.FLAG_SYSTEM == 1) {
				continue
			}
			if (packageInfo.packageName == packageToKill && mActivityManager != null) {
				mActivityManager.killBackgroundProcesses(packageInfo.packageName)
			}
		}
		
		//利用反射调用forceStopPackage方法
		//需要android.permission.FORCE_STOP_PACKAGES权限
		//需要系统签名
		try {
			val method = Class.forName("android.app.ActivityManager").getMethod("forceStopPackage", String::class.java)
			method.invoke(mActivityManager, packageToKill)
		} catch (e: java.lang.Exception) {
			e.printStackTrace()
		}
	}
	
}