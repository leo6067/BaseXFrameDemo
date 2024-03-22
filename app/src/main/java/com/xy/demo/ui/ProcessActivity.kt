package com.xy.demo.ui

import android.app.ActivityManager
import android.app.AppOpsManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import android.os.Process
import android.text.TextUtils
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.FileUtils
import com.xy.demo.BuildConfig
import com.xy.demo.R
import com.xy.demo.adapter.ProcessAdapter
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityProcessBinding
import com.xy.demo.model.AppProcessInfo
import com.xy.demo.network.Globals
import com.xy.demo.utils.MyUtils
import java.lang.String
import java.util.Calendar
import java.util.Random


class ProcessActivity : MBBaseActivity<ActivityProcessBinding, MBBaseViewModel>() {
	
	
	var adapter = ProcessAdapter()
	
	
	override fun showTitleBar(): Boolean {
		return super.showTitleBar()
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_process
	}
	
	override fun initView() {
		super.initView()
		titleBarView?.setBackgroundColor(resources.getColor(R.color.colorBarColor, theme))
		titleBarView?.tvTitle?.text = getString(R.string.background_processes_)
		titleBarView?.setLeftIcon(R.drawable.ic_white_back)
		
		
		mRecyclerView = binding.recyclerView
		initRecycler(1, 1, 0)
		
		mRecyclerView?.adapter = adapter
		
		getProcessList()
	}
	
	
	//检测用户是否对本app开启了“Apps with usage access”权限
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
	
	/**
	 * 获取可清理进程
	 */
	private fun getProcessList() {
		val cleanList: MutableList<AppProcessInfo> = ArrayList<AppProcessInfo>()
		val calendar = Calendar.getInstance()
		val endTime = calendar.timeInMillis
		calendar.add(Calendar.HOUR_OF_DAY, -12)
		val usageStatsManager = getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
		val queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, endTime - 1000*60*60*30, endTime) //半小时
		for (usageStats in queryUsageStats) {
			Globals.log("xxxxxxxxxappusageStats.packageName" + usageStats.packageName)
			
			if (TextUtils.equals(usageStats.packageName, BuildConfig.APPLICATION_ID)) {
				continue
			}
			
			
			val appInfo = AppUtils.getAppInfo(usageStats.packageName)
			if (appInfo != null && !appInfo.isSystem) {
				//无法获取进程所占用的内存，所以用安装包大小倍数假装。。
				var fileLength = FileUtils.getFileLength(appInfo.packagePath)
				fileLength = if (fileLength > 200 * 1024 * 1000) {
					FileUtils.getFileLength(appInfo.packagePath)
				} else {
					FileUtils.getFileLength(appInfo.packagePath) * (Random().nextInt(3) + 3)
				}
				
				//更新可加速内存大小
				val appProcessInfo = AppProcessInfo()
				appProcessInfo.setAppName(appInfo.name)
				appProcessInfo.setProcessName(appInfo.packageName)
				appProcessInfo.setIcon(appInfo.icon)
				appProcessInfo.setMemory(fileLength)
				appProcessInfo.setCheck(true)
				cleanList.add(appProcessInfo)
				Globals.log("xxxxxxxxxappProcessInfo" + appProcessInfo.toString())
			}
		}
		
		
		val activityManager = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
		val memoryInfo = ActivityManager.MemoryInfo()
		activityManager.getMemoryInfo(memoryInfo)
		
		val availableMemory = memoryInfo.availMem
		val totalMemory = memoryInfo.totalMem
		
		// 可用内存和总内存（以字节为单位）
		
		
		binding.ratioTV.text = ((availableMemory * 100 / totalMemory)).toInt().toString()
		binding.memoryTV.text =
			String.format("%s", MyUtils.byte2FitMemorySize(availableMemory)) + " /" + String.format("%s", MyUtils.byte2FitMemorySize(totalMemory))
		
		
		adapter.setNewInstance(cleanList)
		
		
	}
}