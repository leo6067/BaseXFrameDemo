package com.xy.demo.ui.main

import android.content.Intent
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.OnScrollListener
import com.jeremyliao.liveeventbus.LiveEventBus
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseFragment
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.FragmentHomeBinding
import com.xy.demo.db.MyDataBase
import com.xy.demo.db.RemoteModel
import com.xy.demo.network.Globals
import com.xy.demo.ui.ac.ACConActivity
import com.xy.demo.ui.adapter.DeviceAdapter
import com.xy.demo.ui.common.RemoteTypeActivity
import com.xy.demo.ui.infrared.TVConActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Collections


// 添加过设备的首页UI
class HomeFragment : MBBaseFragment<FragmentHomeBinding, MBBaseViewModel>() {
	
	val mAdapter = DeviceAdapter()
	
	
	override fun getLayoutId(): Int {
		return R.layout.fragment_home
	}
	
	
	override fun onResume() {
		super.onResume()
		
		lifecycleScope.launch(Dispatchers.IO) {
			val allRemote = MyDataBase.instance.RemoteDao().getAllRemote() as MutableList
			withContext(Dispatchers.Main) {
				allRemote.reverse()
				mAdapter.setNewInstance(allRemote)
				if (allRemote.size > 0) {
					binding.recyclerView.visibility = View.VISIBLE
					binding.deviceIV.visibility = View.GONE
					binding.equipmentTV.visibility = View.GONE
					binding.addDeviceTV.visibility = View.GONE
				} else {
					binding.recyclerView.visibility = View.GONE
					binding.deviceIV.visibility = View.VISIBLE
					binding.equipmentTV.visibility = View.VISIBLE
					binding.addDeviceTV.visibility = View.VISIBLE
				}
			}
		}
	}
	
	override fun initView() {
		binding.recyclerView.layoutManager = GridLayoutManager(activity, 2)
		binding.recyclerView.adapter = mAdapter
		mAdapter.setOnItemClickListener { adapter, view, position ->
			val remoteModel = mAdapter.data.get(position)
			activity?.let {
				val intent = Intent()
				intent.putExtra(Constants.KEY_REMOTE, mAdapter.data.get(position))
				if (remoteModel.type == 1) {
					intent.setClass(it, TVConActivity::class.java)
				} else {
					intent.setClass(it, ACConActivity::class.java)
				}
				startActivity(intent)
			}
		}
		
		binding.addDevice.setOnClickListener {
			activity?.startActivity(Intent(activity, RemoteTypeActivity::class.java))
		}
		
		binding.addDeviceTV.setOnClickListener {
			activity?.startActivity(Intent(activity, RemoteTypeActivity::class.java))
		}
		
		binding.recyclerView.addOnScrollListener(object : OnScrollListener() {
			override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
				super.onScrollStateChanged(recyclerView, newState)
				
			}
			
			override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
				super.onScrolled(recyclerView, dx, dy)
				Globals.log("XXXXXXXnewState dy" + dy)
				if (dy > 0) {
					if (binding.bottomLay.visibility == View.GONE) {  //广告
//						AdManage.showBannerAd(binding.bottomAdView, binding.bottomLay)
					}
				}
			}
		})
		
		
		//广告相关
//		binding.closeIV.setOnClickListener {
//			binding.adLin.visibility = View.GONE
//			Constants.showMainTopBanner = false
//		}
//
//
//		binding.bottomCloseIV.setOnClickListener {
//			binding.bottomLay.visibility = View.GONE
//			Constants.showMainBottomBanner = false
//		}
//
//
//
//		if (Constants.showMainTopBanner) {
//			AdManage.showBannerAd(binding.adView, binding.adLin)
//		}
//
//		if (Constants.showMainBottomBanner) {
//			AdManage.showBannerAd(binding.bottomAdView, binding.bottomLay)
//		}
		
		
	}
	
	
	override fun initViewObservable() {
		super.initViewObservable()
		LiveEventBus
			.get<String>(Constants.EVENT_DEVICES).observe(this) {
				lifecycleScope.launch(Dispatchers.Main) {
					showLoading()
					withContext(Dispatchers.IO) {
						MyDataBase.instance.RemoteDao().deleteByName(it)
					}
					delay(1000)
					val allRemote = withContext(Dispatchers.IO) {
						MyDataBase.instance.RemoteDao().getAllRemote() as MutableList
					}
					allRemote.reverse()
					mAdapter.setNewInstance(allRemote)
					dismissLoading()
				}
			}
	}
	
	
}