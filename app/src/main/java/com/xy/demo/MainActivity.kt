package com.xy.demo

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2.OnPageChangeCallback
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MyApplication
import com.xy.demo.databinding.ActivityMainBinding
import com.xy.demo.model.TabEntity
import com.xy.demo.network.Globals
import com.xy.demo.ui.main.FileFragment
import com.xy.demo.ui.main.HomeFragment
import com.xy.demo.ui.main.SettingFragment
import com.xy.demo.ui.main.ToolFragment
import com.xy.demo.utils.LanguageUtil
import com.xy.xframework.base.BaseAppContext
import com.xy.xframework.base.XBaseViewModel
import com.xy.xframework.statusBar.StatusBarUtil
import com.xy.xframework.utils.ToastUtils
import java.io.File


class MainActivity : MBBaseActivity<ActivityMainBinding, XBaseViewModel>() {
	
	
	var mFragmentList: ArrayList<Fragment> = ArrayList()
	

	
	/*未选择时的icon*/
	private val mIconUnselectIds = intArrayOf(
		R.drawable.ic_home_b, R.drawable.ic_file_b,
		R.drawable.ic_tool_b, R.drawable.ic_setting_b
	)
	
	/*选择时的icon*/
	private val mIconSelectIds = intArrayOf(
		R.drawable.ic_home_a, R.drawable.ic_file_a,
		R.drawable.ic_tool_a, R.drawable.ic_setting_a
	)
	
	private val mTabEntities = ArrayList<CustomTabEntity>()
	
	val homeFragment = HomeFragment()
	val fileFragment = FileFragment()
	
	
	override fun getLayoutId(): Int {
		LanguageUtil.reFreshLanguage(null, this, null)
		return R.layout.activity_main
	}
	
	
	override fun showTitleBar(): Boolean = false
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
	override fun initView() {
		StatusBarUtil.setStatusBarDarkTheme(this, true)
//		launchRequest({ NetManager.getStoreCount() }, {}, {})
		var downUrl =
			"https://softforspeed.51xiazai.cn/alading/NeteaseCloudMusic_Music_official_2.10.6.200601.exe"
		val rootDirPath = getRootDirPath(BaseAppContext.getInstance());
		Globals.log("xxxxxfilesDir", getRootDirPath(BaseAppContext.getInstance()))
		Globals.log("xxxxxfilesDir--本地可用的存储 路劲", filesDir.absolutePath.toString() + "新增的文件名")
		
		
		
		initTabLayout()
	}
	
	
	override fun onResume() {
		super.onResume()
		homeFragment.onResume()
	}
	
	
	public fun setTab(index: Int) {
		binding.mViewPager.setCurrentItem(index)
	}
	
	public fun getSelectIndex(): Int {
		return binding.mViewPager.currentItem
	}
	
	
	private fun initTabLayout() {
		  val mTitles = arrayOf(
		  getString(R.string.home),  getString(R.string.file),
			 getString(R.string.tool), getString(R.string.setting)
		)
		for (index in 0 until mTitles.size) {
			mTabEntities.add(TabEntity(mTitles[index], mIconSelectIds[index], mIconUnselectIds[index]))
		}
		
		
		mFragmentList.add(homeFragment)
		mFragmentList.add(fileFragment)
		mFragmentList.add(ToolFragment())
		mFragmentList.add(SettingFragment())
		
		binding.mTabLayout.setTabData(mTabEntities)
		binding.mTabLayout.setOnTabSelectListener(object : OnTabSelectListener {
			override fun onTabSelect(position: Int) {
				Globals.log("xxxxxxxpositionxx" + position)
				binding.mViewPager.setCurrentItem(position)
				if (position == 1) {
					fileFragment.getAuthority()
				}
				if (position == 0) {
					homeFragment.refreshFile()
				}
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
		binding.mViewPager.setCurrentItem(0)
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