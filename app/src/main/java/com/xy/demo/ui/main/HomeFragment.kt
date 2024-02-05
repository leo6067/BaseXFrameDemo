package com.xy.demo.ui.main

import android.content.Intent
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import com.jeremyliao.liveeventbus.LiveEventBus
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseFragment
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.FragmentHomeBinding
import com.xy.demo.db.MyDataBase
import com.xy.demo.db.RemoteModel
import com.xy.demo.logic.ad.AdManage
import com.xy.demo.network.Globals
import com.xy.demo.ui.adapter.DeviceAdapter
import com.xy.demo.ui.dialog.CastDialog
import com.xy.demo.ui.dialog.RateDialog
import com.xy.demo.ui.infrared.AddWayActivity
import com.xy.demo.ui.infrared.SaveRemoteActivity
import com.xy.demo.ui.infrared.TVConActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


// 添加过设备的首页UI
class HomeFragment : MBBaseFragment<FragmentHomeBinding, MBBaseViewModel>() {
	
	val mAdapter = DeviceAdapter()
	
	
	override fun getLayoutId(): Int {
		return R.layout.fragment_home
	}
	
	override fun initView() {
 
 
		binding.recyclerView.layoutManager = GridLayoutManager(activity, 2)
		binding.recyclerView.adapter = mAdapter
		
		GlobalScope.launch(Dispatchers.IO) {
			val allRemote = MyDataBase.instance.RemoteDao().getAllRemote() as MutableList
			mAdapter.setNewInstance(allRemote)
			
			binding.deviceNum.setText("(" + allRemote.size + ")")
		}
		
		mAdapter.setOnItemClickListener { adapter, view, position ->
			activity?.let {
				val intent = Intent()
				intent.putExtra(Constants.KEY_REMOTE, mAdapter.data.get(position))
				intent.setClass(it, TVConActivity::class.java)
				startActivity(intent)
			}
		}
		
		
		binding.addDevice.setOnClickListener {
			activity?.startActivity(Intent(activity, AddWayActivity::class.java))
		}
		
		binding.topLin.setOnClickListener {
			activity?.let { it1 ->
				CastDialog().show(it1.supportFragmentManager, "1") }
		}
		
		AdManage.showBannerAd(binding.adView)
		
		
		binding.closeIV.setOnClickListener {
			binding.adView.visibility = View.GONE
			binding.closeIV.visibility = View.GONE
		}
		
	}
	
	
	override fun initViewObservable() {
		super.initViewObservable()
		LiveEventBus
			.get<String>(Constants.EVENT_DEVICES).observe(this) {
				GlobalScope.launch(Dispatchers.Main) {
					
					showLoading()
					withContext(Dispatchers.IO) {
						MyDataBase.instance.RemoteDao().deleteByName(it)
					}
					delay(1000)
					
					val allRemote = withContext(Dispatchers.IO) {
						MyDataBase.instance.RemoteDao().getAllRemote() as MutableList
					}
					
					mAdapter.setNewInstance(allRemote)
					dismissLoading()
				}
			}
	}
	
	
}