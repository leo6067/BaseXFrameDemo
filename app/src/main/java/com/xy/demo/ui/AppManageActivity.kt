package com.xy.demo.ui

import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.constant.PermissionConstants
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.AppUtils
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.xy.demo.R
import com.xy.demo.adapter.AppAdapter
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityAppManageBinding
import com.xy.xframework.utils.Globals
import com.xy.xframework.utils.PackageUtils
import kotlinx.coroutines.async
import kotlinx.coroutines.launch


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
  
		titleBarView?.setBackgroundColor(resources.getColor(R.color.colorBarColorB, theme))
		titleBarView?.tvTitle?.text = getString(R.string.app_manager)
		titleBarView?.setLeftIcon(R.drawable.ic_white_back)
		mRecyclerView = binding.recyclerView
		initRecycler(1, 1, 0)
		binding.recyclerView.adapter = adapter
	}
	
	
	override fun initLogic() {
		super.initLogic()
		
		fetchAppList()
		adapter.setOnItemClickListener { adapter, view, position ->
//			XXPermissions.with(this).permission("android.permission.REQUEST_DELETE_PACKAGES")
//				.request { permissions, all ->
//					if (all){
			val bean = adapter.data[position] as AppUtils.AppInfo
			val appInfo = PackageUtils.getInstance().getAppInfo(this@AppManageActivity, bean.getPackageName())
			Globals.log("xxxxxx bean$appInfo")
			AppUtils.uninstallApp(this@AppManageActivity, appInfo.packageName, 100)
		}
	}
	
	override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
		super.onActivityResult(requestCode, resultCode, data)
		if (requestCode == 100) {
			fetchAppList()
		}
	}
	
	fun fetchAppList() {
		showLoading()
		lifecycleScope.launch {
			var appList = async {
				val appInfoList: MutableList<AppUtils.AppInfo> = ArrayList<AppUtils.AppInfo>()
//		val packageManager = context.packageManager
//		val apps = packageManager.getInstalledApplications(PackageManager.GET_META_DATA)
				for (app in AppUtils.getAppsInfo()) {
					if (!app.isSystem) {  //筛选掉系统的app
						appInfoList.add(app)
					}
				}
				appInfoList
			}
			dismissLoading()
			adapter.setNewInstance(appList.await())
		}
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