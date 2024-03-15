package com.xy.demo.ui

import android.app.ActivityManager
import android.app.AppOpsManager
import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.os.Process
import android.provider.Settings
import androidx.core.content.ContextCompat
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityMainBinding
import com.xy.demo.network.Globals
import com.xy.demo.ui.setting.SettingActivity
import com.xy.xframework.base.BaseAppContext
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.base.XBaseViewModel
import java.io.BufferedReader
import java.io.File
import java.io.FileReader
import java.io.IOException


class MainActivity : MBBaseActivity<ActivityMainBinding, XBaseViewModel>() {
	
	
	var downloadId = 0
	override fun getLayoutId(): Int = R.layout.activity_main
	
	
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
		
		
		//判断是否开启查看权限 	//手动跳转 授权 应用使用情况
		val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
		val mode = appOps.checkOpNoThrow(
			"android:get_usage_stats",
			Process.myUid(), getPackageName()
		)
		val granted = mode == AppOpsManager.MODE_ALLOWED
		
		if (!granted) {
			startActivity(
				Intent(Settings.ACTION_USAGE_ACCESS_SETTINGS)
			)
		}
		
		
		
		
		binding.memoryTV.text = "99"
		
		
		Globals.log("XXXXXXXXX"+System.currentTimeMillis())
		
		
		
		binding.settingIV.setOnClickListener {
			startActivity(Intent(this@MainActivity, SettingActivity::class.java))
		}
		
		
		
		binding.clearTV.setOnClickListener {
			startActivity(Intent(this@MainActivity, ClearScanActivity::class.java))
		}
		
		binding.bigClearTV.setOnClickListener {
			startActivity(Intent(this@MainActivity, CacheActivity::class.java))
		}
		
		binding.uninstallTV.setOnClickListener {
			startActivity(Intent(this@MainActivity, AppManageActivity::class.java))
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
	
	
	protected fun hasPermissionUsage(): Boolean {
		val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
		var mode = 0
		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
			mode = appOps.checkOpNoThrow(
				AppOpsManager.OPSTR_GET_USAGE_STATS,
				Process.myUid(), packageName
			)
		}
		return mode == AppOpsManager.MODE_ALLOWED
	}
	
	
	override fun onResume() {
		super.onResume()
		
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
	 * 获取当用户在试用的应用包名，适用于5.0以上
	 *
	 * @return
	 */
	fun getForegroundApp(): String? {
		val files = File("/proc").listFiles()
		var lowestOomScore = Int.MAX_VALUE
		var foregroundProcess: String? = null
		for (file in files) {
			if (!file.isDirectory) {
				continue
			}
			var pid: Int
			pid = try {
				file.name.toInt()
			} catch (e: NumberFormatException) {
				continue
			}
			try {
				val cgroup = read(String.format("/proc/%d/cgroup", pid))
				val lines = cgroup.split("\n".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
				var cpuSubsystem: String
				var cpuaccctSubsystem: String
				if (lines.size == 2) { // 有的手机里cgroup包含2行或者3行，我们取cpu和cpuacct两行数据
					cpuSubsystem = lines[0]
					cpuaccctSubsystem = lines[1]
				} else if (lines.size == 3) {
					cpuSubsystem = lines[0]
					cpuaccctSubsystem = lines[2]
				} else if (lines.size == 5) {
					cpuSubsystem = lines[2]
					cpuaccctSubsystem = lines[4]
				} else {
					continue
				}
				if (!cpuaccctSubsystem.endsWith(Integer.toString(pid))) {
					continue
				}
				if (cpuSubsystem.endsWith("bg_non_interactive")) {
					continue
				}
				val cmdline = read(String.format("/proc/%d/cmdline", pid))
				if (isContainsFilter(cmdline)) {
					continue
				}
				val uid = cpuaccctSubsystem.split(":".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()[2].split("/".toRegex())
					.dropLastWhile { it.isEmpty() }
					.toTypedArray()[1].replace("uid_", "").toInt()
				if (uid >= 1000 && uid <= 1038) {
					continue
				}
//                var appId: Int = uid - AID_APP
//                while (appId > AID_USER) {
//                    appId -= AID_USER
//                }
//                if (appId < 0) {
//                    continue
//                }
				val oomScoreAdj = File(String.format("/proc/%d/oom_score_adj", pid))
				if (oomScoreAdj.canRead()) {
					val oomAdj = read(oomScoreAdj.absolutePath).toInt()
					if (oomAdj != 0) {
						continue
					}
				}
				val oomscore = read(String.format("/proc/%d/oom_score", pid)).toInt()
				if (oomscore < lowestOomScore) {
					lowestOomScore = oomscore
					foregroundProcess = cmdline
				}
				if (foregroundProcess == null) {
					return null
				}
				
				Globals.log("xxxxxxforegroundProcess" + foregroundProcess)
				val indexOf = foregroundProcess.indexOf(":")
				if (indexOf != -1) {
					foregroundProcess = foregroundProcess.substring(0, indexOf)
				}
			} catch (e: NumberFormatException) {
				e.printStackTrace()
			} catch (e: IOException) {
				e.printStackTrace()
			} catch (e: Exception) {
				e.printStackTrace()
			}
		}
		
		return foregroundProcess
	}
	
	@Throws(IOException::class)
	private fun read(path: String): String {
		val output = StringBuilder()
		val reader = BufferedReader(FileReader(path))
		output.append(reader.readLine())
		var line = reader.readLine()
		while (line != null) {
			output.append('\n').append(line)
			line = reader.readLine()
		}
		reader.close()
		return output.toString().trim { it <= ' ' } // 不调用trim()，包名后会带有乱码
	}
	
	/**
	 * filter包名过滤
	 *
	 * @param cmdline
	 * @return
	 */
	fun isContainsFilter(cmdline: String): Boolean {
		var flag = false
		if (filterMap == null || filterMap.isEmpty() || filterMap.size === 0) {
			initFliter()
		}
		if (filterMap != null) {
			for (key in filterMap.keys) {
				if (cmdline.contains(key!!)) {
					flag = true
					break
				}
			}
		}
		return flag
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
	 * 初始化filter
	 */
	
	var filterMap = HashMap<String, Int>()
	fun initFliter() {
		if (filterMap == null) {
			filterMap = HashMap<String, Int>()
		}
		if (filterMap.isEmpty() || filterMap.size === 0) {
			filterMap.put("com.android.systemui", 0)
			filterMap.put("com.aliyun.ams.assistantservice", 0)
			filterMap.put("com.meizu.cloud", 0)
			filterMap.put("com.android.incallui", 0)
			filterMap.put("com.amap.android.location", 0)
			filterMap.put("com.android.providers.contacts", 0)
			filterMap.put("com.samsung.android.providers.context", 0)
			filterMap.put("com.android.dialer", 0)
			filterMap.put("com.waves.maxxservice", 0)
			filterMap.put("com.lge.camera", 0)
			filterMap.put("se.dirac.acs", 0)
			filterMap.put("/", 0)
		}
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