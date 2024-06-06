package com.xy.demo

import android.content.Context
import android.os.Bundle
import android.os.Environment
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityMainBinding
import com.xy.demo.model.TabEntity
import com.xy.demo.network.Globals
import com.xy.demo.network.NetLaunchManager.launchRequest
import com.xy.demo.network.NetManager
import com.xy.demo.ui.main.FileFragment
import com.xy.demo.ui.main.HomeFragment
import com.xy.demo.ui.main.SettingFragment
import com.xy.demo.ui.main.ToolFragment
import com.xy.xframework.base.BaseAppContext
import com.xy.xframework.base.XBaseViewModel
import java.io.File


class MainActivity : MBBaseActivity<ActivityMainBinding, XBaseViewModel>() {
	
	
	var downloadId = 0
	
	
	var mFragmentList: ArrayList<Fragment> = ArrayList()
	
	private val mTitles = arrayOf("主页", "文件", "工具", "设置")
	
	/*未选择时的icon*/
	private val mIconUnselectIds = intArrayOf(
		R.drawable.toast_icon, R.drawable.toast_icon,
		R.drawable.toast_icon, R.drawable.toast_icon
	)
	
	/*选择时的icon*/
	private val mIconSelectIds = intArrayOf(
		R.drawable.toast_icon, R.drawable.toast_icon,
		R.drawable.toast_icon, R.drawable.toast_icon
	)
	
	private val mTabEntities = ArrayList<CustomTabEntity>()
	
	
	override fun getLayoutId(): Int = R.layout.activity_main
	
	
	override fun showTitleBar(): Boolean = false
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
	}
	
	
	override fun initView() {
//		launchRequest({ NetManager.getStoreCount() }, {}, {})
		var downUrl =
			"https://softforspeed.51xiazai.cn/alading/NeteaseCloudMusic_Music_official_2.10.6.200601.exe"
		val rootDirPath = getRootDirPath(BaseAppContext.getInstance());
		Globals.log("xxxxxfilesDir", getRootDirPath(BaseAppContext.getInstance()))
		Globals.log("xxxxxfilesDir--本地可用的存储 路劲", filesDir.absolutePath.toString() + "新增的文件名")
		
		XXPermissions.with(this)
			.permission(Permission.READ_MEDIA_IMAGES, Permission.MANAGE_EXTERNAL_STORAGE)
			.request { permissions, all ->
				if (all){
					initTabLayout()
				}else{
					System.exit(0)
				}
			}
	}
	
	
	private fun initTabLayout() {
		for (index in 0 until mTitles.size) {
			mTabEntities.add(TabEntity(mTitles[index], mIconSelectIds[index], mIconUnselectIds[index]))
		}
		mFragmentList.add(HomeFragment())
		mFragmentList.add(FileFragment())
		mFragmentList.add(ToolFragment())
		mFragmentList.add(SettingFragment())
		
		binding.mTabLayout.setTabData(mTabEntities)
		binding.mTabLayout.setOnTabSelectListener(object : OnTabSelectListener {
			override fun onTabSelect(position: Int) {
				binding.mViewPager.setCurrentItem(position);
			}
			override fun onTabReselect(position: Int) {
			}
		})
		
		binding.mViewPager.registerOnPageChangeCallback(object : OnPageChangeCallback() {
			override fun onPageSelected(position: Int) {
				binding.mTabLayout.currentTab = position
			}
		})
		binding.mViewPager.setAdapter(MyPagerAdapter(this, mFragmentList))
		binding.mViewPager.setCurrentItem(1)
	}
	
	
	private class MyPagerAdapter(fragmentActivity: FragmentActivity, mFragments: ArrayList<Fragment>) : FragmentStateAdapter(fragmentActivity) {
		
		var mFragmentList = mFragments
		override fun getItemCount(): Int {
			return mFragmentList.size
		}
		
		override fun createFragment(position: Int): Fragment {
			return mFragmentList.get(position)
		}
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
	
}