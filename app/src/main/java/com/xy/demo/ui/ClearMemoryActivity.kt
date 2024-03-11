package com.xy.demo.ui

import android.app.ActivityManager
import android.app.AppOpsManager
import android.app.usage.UsageStatsManager
import android.content.Context
import android.os.Build
import android.os.Process
import android.text.TextUtils
import android.util.Log
import androidx.annotation.RequiresApi
import com.blankj.utilcode.util.AppUtils
import com.blankj.utilcode.util.FileUtils
import com.jaredrummler.android.processes.AndroidProcesses
import com.jaredrummler.android.processes.models.AndroidAppProcess
import com.xy.demo.BuildConfig
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityClearMemoryBinding
import com.xy.demo.model.AppProcessInfo
import com.xy.demo.network.Globals
import java.util.Calendar
import java.util.Random


class ClearMemoryActivity : MBBaseActivity<ActivityClearMemoryBinding, MBBaseViewModel>() {
	
	
	private var totalSize: Long = 0
	private var checkTotalSize: Long = 0
	
 
	override fun getLayoutId(): Int {
		return R.layout.activity_clear_memory
	}
	
 
	@RequiresApi(Build.VERSION_CODES.Q)
	override fun initView() {
		super.initView()
		
		//To change body of implemented methods use File | Settings | File Templates.
		val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
	 
		
		val beforeMem = getAvailMemory(this@ClearMemoryActivity);
		
		
		
		
		
		binding.clearOOMBT.setOnClickListener {
			
			getProcessList()
		
		}
	}
	
	
	override fun initLogic() {
		super.initLogic()
		
	 
		
		
	}
	
	
	//检测用户是否对本app开启了“Apps with usage access”权限
//	protected fun hasPermissionUsage(): Boolean {
//		val appOps = getSystemService(Context.APP_OPS_SERVICE) as AppOpsManager
//		var mode = 0
//		if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
//			mode = appOps.checkOpNoThrow(
//				AppOpsManager.OPSTR_GET_USAGE_STATS,
//				Process.myUid(), packageName
//			)
//		}
//		return mode == AppOpsManager.MODE_ALLOWED
//	}
	
 
	
	
	
	
	//获取可用内存大小
	private fun getAvailMemory(context: Context): Long {
		// 获取android当前可用内存大小
		val am = context.getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
		val mi = ActivityManager.MemoryInfo()
		am.getMemoryInfo(mi)
		//mi.availMem; 当前系统的可用内存
		//return Formatter.formatFileSize(context, mi.availMem);// 将获取的内存大小规格化
		Log.d(TAG, "可用内存---->>>" + mi.availMem / (1024 * 1024))
		return mi.availMem / (1024 * 1024)
	}
	
	
	/**
	 * 获取可清理进程  并且 清理
	 */
	@RequiresApi(Build.VERSION_CODES.Q)
	private fun getProcessList() {
		val cleanList: MutableList<AppProcessInfo> = ArrayList<AppProcessInfo>()
		val calendar = Calendar.getInstance()
		val endTime = calendar.timeInMillis
		calendar.add(Calendar.HOUR_OF_DAY, -12)
		val usageStatsManager = getSystemService(USAGE_STATS_SERVICE) as UsageStatsManager
		val queryUsageStats = usageStatsManager.queryUsageStats(UsageStatsManager.INTERVAL_DAILY, endTime - 60000, endTime)
		for (usageStats in queryUsageStats) {
			if (TextUtils.equals(usageStats.packageName, BuildConfig.APPLICATION_ID)) {
				continue
			}
			if (usageStats.packageName.contains("android")) {
				continue
			}
			Globals.log("xxxxxxxappProcessInfo  usageStats.packageName "+usageStats.packageName)
		try {
			val appInfo: AppUtils.AppInfo = AppUtils.getAppInfo(usageStats.packageName)
			if (appInfo != null && !appInfo.isSystem()) {
				//无法获取进程所占用的内存，所以用安装包大小倍数假装。。
				var fileLength: Long = FileUtils.getFileLength(appInfo.getPackagePath())
				fileLength = if (fileLength > 200 * 1024 * 1000) {
					FileUtils.getFileLength(appInfo.getPackagePath())
				} else {
					FileUtils.getFileLength(appInfo.getPackagePath()) * (Random().nextInt(3) + 3)
				}
				totalSize += fileLength
				//更新可加速内存大小
				setTotalSize()
				val appProcessInfo = AppProcessInfo()
				appProcessInfo.setAppName(appInfo.getName())
				appProcessInfo.setProcessName(appInfo.getPackageName())
				appProcessInfo.setIcon(appInfo.getIcon())
				appProcessInfo.setMemory(fileLength)
				appProcessInfo.setCheck(true)
				cleanList.add(appProcessInfo)
				
				
				Globals.log("xxxxxxxappProcessInfo"+appProcessInfo.toString())
			}
		}catch (e:Exception){}
		}
		
		
		
		// 获取正在运行的程序信息, 就是以下粗体的这句代码,获取系统运行的进程     要使用这个方法，需要加载
		val processInfos: List<AndroidAppProcess> = AndroidProcesses.getRunningAppProcesses()
		//扫描速度过快，延迟两秒完成，优化体验
		killProcess(processInfos)
		
		
		val activityManager =   getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
		 var runningTasks = activityManager.getRunningTasks(10); // 获取最近10个运行的任务 for (ActivityManager.RunningTaskInfo task : runningTasks) { String packageName = task.topActivity.getPackageName(); // 获取应用包名 String className = task.topActivity.getClassName(); // 获取应用类名
		
		 
		
	}
	
	
	private fun setTotalSize() {
//		if (!isFinishing) {
//			val strings: Array<String> = MyUtils.byte2FitMemorySize2(totalSize)
//			mTvJunkNum.setText(strings[0])
//			mTvJunkUnit.setText(strings[1])
//			mTvScan.setText(getString(R.string.clean_checked) + java.lang.String.format("%s", MyUtils.byte2FitMemorySize(totalSize)))
//		}
	}
	
	private fun getTotalSize(appProcessInfos: List<AppProcessInfo>) {
		totalSize = 0
		for (appProcessInfo in appProcessInfos) {
			totalSize += appProcessInfo.memory
		}
		checkTotalSize = totalSize
		setTotalSize()
	}
	
	
	private fun killProcess(cleanList: List<AndroidAppProcess>) {
		val am = getSystemService(Context.ACTIVITY_SERVICE) as ActivityManager
		
		
		Globals.log("xxxxxxxappProcessInfo"+cleanList.size)
 
		
		
		
		for (info in cleanList) {
//			if (info.isCheck) {
//				am.killBackgroundProcesses(info.processName)
//			}
			Globals.log("xxxxxxxappProcessInfo"+info.pid)
			android.os.Process.killProcess(info.pid);
		}
		//        rocketsFiresAnimationDrawable.stop();
//		mIvRockets.setImageResource(R.drawable.pc_sjjs)
//		if (fullScan) {
//			SPUtils.getInstance().put(Constants.PHONE_ACCELERATE, TimeUtils.getNowMills())
//		}
//		//通知首页更新通知
//		if (SPUtils.getInstance().getBoolean(Constants.SETTING_NOTIFICATION)) {
//			EventBus.getDefault().post(0, EventBusTag.UPDATE_NOTIFiCATION)
//		}
//		SuccessActivity.launchActivity(this@PhoneAccelerateActivity, checkTotalSize, totalSize, SuccessActivity.ACCELERATE)
 
	}
	
	
}