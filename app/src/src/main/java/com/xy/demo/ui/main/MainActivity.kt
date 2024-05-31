package com.xy.demo.ui.main

import android.Manifest
import android.content.Intent
import android.os.Build
import android.view.GestureDetector
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentPagerAdapter
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.lifecycleScope
import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.flyco.tablayout.listener.CustomTabEntity
import com.flyco.tablayout.listener.OnTabSelectListener
import com.google.android.material.tabs.TabLayoutMediator
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.jeremyliao.liveeventbus.LiveEventBus
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityMainBinding

import com.xy.demo.logic.LanguageUtil
import com.xy.demo.model.TabEntity
import com.xy.demo.ui.vm.HttpViewModel




//, GestureDetector.OnGestureListener  触摸 手势
class MainActivity : MBBaseActivity<ActivityMainBinding, HttpViewModel>() {
	
	
 
	
	/*文本信息*/
	lateinit var mTitles: Array<String>
	
	/*未选择时的icon*/
	private val mIconUnselectIds = intArrayOf(
		R.drawable.icon_tab_home_a, R.drawable.icon_tab_tv_a,
		R.drawable.icon_tab_setting_a
	)
	
	/*选择时的icon*/
	private val mIconSelectIds = intArrayOf(
		R.drawable.icon_tab_home_b, R.drawable.icon_tab_tv_b,
		R.drawable.icon_tab_setting_b
	)
	private val mTabEntities = ArrayList<CustomTabEntity>()
	private val mFragments = ArrayList<Fragment>()
	
	
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
		
		/*文本信息*/
		mTitles = arrayOf(getString(R.string.my_remote), getString(R.string.cast), getString(R.string.settings))
		initTabAndViewPager()
	}
	
	
	override fun initParams() {
		super.initParams()
 
	}
	
	
	private fun initTabAndViewPager() {
		mFragments.clear()
		mFragments.add(HomeFragment())
		mFragments.add(CastFragment())
		mFragments.add(SettingFragment())
		
		//设置adapter
		binding.viewPager.adapter = fragmentAdapter
		//设置viewpage的滑动方向
		binding.viewPager.orientation = ViewPager2.ORIENTATION_HORIZONTAL
		//禁止滑动
		// binding.viewpager.isUserInputEnabled = false
		//设置显示的页面，0：是第一页
		//binding.viewpager.currentItem = 1
		//设置缓存页
//		binding.viewPager.offscreenPageLimit = mFragments.size
		//设置viewPage2切换效果
		//binding.viewpager.setPageTransformer(TransFormer())
		
		
		for (i in mTitles.indices) {
			mTabEntities.add(TabEntity(mTitles[i], mIconSelectIds[i], mIconUnselectIds[i]))
		}
		
		binding.tabLay.setTabData(mTabEntities)
		
		binding.tabLay.setOnTabSelectListener(object : OnTabSelectListener {
			override fun onTabSelect(position: Int) {
				binding.viewPager.currentItem = position
			}
			
			override fun onTabReselect(position: Int) {
			}
		})
		
		//设置选中事件
		binding.viewPager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
			override fun onPageSelected(position: Int) {
				super.onPageSelected(position)
				binding.tabLay.currentTab = position
			}
		})
		
		
//		LiveEventBus.get<Int>("mainTab").observe(this) {
//			currentPosition = it
//			binding.viewPager.currentItem = currentPosition
//			Globals.log("XXXXXXXXXX  it" + currentPosition)
//		}
//
		val currentPosition = intent.getIntExtra("mainTab", 0);
		binding.viewPager.currentItem = currentPosition
	}
	
	
	/**
	 * viewPager adapter
	 */
	private val fragmentAdapter: FragmentStateAdapter by lazy {
		object : FragmentStateAdapter(this) {
			override fun getItemCount(): Int {
				return mFragments.size
			}
			
			override fun createFragment(position: Int): Fragment {
				return mFragments[position]
			}
		}
	}
	
	
	override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			moveTaskToBack(true)
			return true
		}
		return super.onKeyDown(keyCode, event)
	}
	
	
}