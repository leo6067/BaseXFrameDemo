package com.xy.demo.ui

import android.content.Context
import android.content.Intent
import android.content.pm.ApplicationInfo
import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import com.blankj.utilcode.util.AppUtils
import com.xy.demo.R
import com.xy.demo.adapter.AppAdapter
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityAppManageBinding


class AppManageActivity : MBBaseActivity<ActivityAppManageBinding, MBBaseViewModel>() {
	
	var adapter = AppAdapter()
	
	override fun showTitleBar(): Boolean {
		return super.showTitleBar()
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
	override fun getLayoutId(): Int {
		return R.layout.activity_app_manage
	}
	
	
	override fun initView() {
		super.initView()
		
//		setStatusBarColor(this,resources.getColor(R.color.colorBarColor,theme))
		
		
		titleBarView?.setBackgroundColor(resources.getColor(R.color.colorBarColor,theme))
		titleBarView?.tvTitle?.text = "软件管理"
		titleBarView?.setLeftIcon(R.drawable.ic_white_back)
		
		
		mRecyclerView = binding.recyclerView
		initRecycler(1,1,0)
		
		binding.recyclerView.adapter = adapter
		fetchAppList(this)
	}
	
	
	fun fetchAppList(context: Context) {
		
		
		showLoading()
 
		val appInfoList: MutableList<AppUtils.AppInfo> = ArrayList<AppUtils.AppInfo>()
//		val packageManager = context.packageManager
//		val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
		
		
	
		
		for (app in AppUtils.getAppsInfo()) {
			
			if (!app.isSystem){
				appInfoList.add(app)
			}
			
			Log.d("AppList", "App: " + app.name + ", Package Name: " + app.packageName)
		}
		dismissLoading()
		adapter.setNewInstance(appInfoList)
		
		
		
	}
	
	
	fun hasApplication(context: Context, packageName: String?): Boolean {
		val packageManager = context.packageManager
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			val intent = Intent(Intent.ACTION_MAIN).addCategory(Intent.CATEGORY_LAUNCHER)
			val list = packageManager.queryIntentActivities(intent, PackageManager.MATCH_ALL)
			var i = 0
			val count = list.size
			while (i < count) {
				if (list[i].activityInfo.applicationInfo.packageName.equals(packageName, ignoreCase = true)) {
					return true
				}
				i++
			}
		} else {
			//packageManager.queryIntentActivities(intent,0)
			//获取系统中安装的应用包的信息
			val listPackageInfo = packageManager.getInstalledPackages(0)
			for (i in listPackageInfo.indices) {
				if (listPackageInfo[i].packageName.equals(packageName, ignoreCase = true)) {
					return true
				}
			}
		}
		return false
	}
	
}