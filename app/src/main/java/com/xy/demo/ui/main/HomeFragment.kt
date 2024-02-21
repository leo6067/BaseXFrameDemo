package com.xy.demo.ui.main

import android.content.Intent
import android.view.GestureDetector
import android.view.MotionEvent
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
import com.xy.demo.network.Globals
import com.xy.demo.ui.adapter.DeviceAdapter
import com.xy.demo.ui.dialog.CastDialog
import com.xy.demo.ui.infrared.AddWayActivity
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
			
			withContext(Dispatchers.Main) {
				binding.deviceNum.setText("(" + allRemote.size + ")")
			}
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
				CastDialog().show(it1.supportFragmentManager, "1")
			}
		}
		
		
		binding.recyclerView.addOnScrollListener(object : OnScrollListener() {
			override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
				super.onScrollStateChanged(recyclerView, newState)
				Globals.log("XXXXXXXnewState " + newState)
			}
			
			override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
				super.onScrolled(recyclerView, dx, dy)
				Globals.log("XXXXXXXnewState dy" + dy)
				if (dy > 0) {
					LiveEventBus.get<String>(Constants.EVENT_SCROLL_UP).post("上划")
				}
			}
		})
		
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
					withContext(Dispatchers.Main) {
						binding.deviceNum.setText("(" + allRemote.size + ")")
					}
					dismissLoading()
				}
			}
	}
	
	
}