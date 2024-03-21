package com.xy.demo.ui

import android.view.View
import androidx.lifecycle.lifecycleScope
import com.jeremyliao.liveeventbus.LiveEventBus
import com.xy.demo.R
import com.xy.demo.adapter.JunkFileAdapter
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityClearScanBinding
import com.xy.demo.db.JunkModel
import com.xy.demo.db.MyDataBase
import com.xy.demo.logic.CacheUtil
import com.xy.demo.utils.MyUtils
import com.xy.demo.utils.UnitConversion
import com.xy.xframework.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Timer
import kotlin.concurrent.timerTask


// 垃圾清理  首页头部进来
//   clearing_finish.json  清理 --清理完成

class ClearScanActivity : MBBaseActivity<ActivityClearScanBinding, MBBaseViewModel>() {
	
	var progressInt: Int = 0
	var totalSize: Long = 0
	
	var mAdapter = JunkFileAdapter()
	
	
	override fun showTitleBar(): Boolean {
		return super.showTitleBar()
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
	override fun getLayoutId(): Int {
		return R.layout.activity_clear_scan
	}
	
	override fun initView() {
		super.initView()
		titleBarView?.setBackgroundColor(resources.getColor(R.color.colorBarColor, theme))
		titleBarView?.tvTitle?.text = getString(R.string.junk_cleaner)
		titleBarView?.setLeftIcon(R.drawable.ic_white_back)
		
		
		binding.clearLay.processLAV.visibility = View.GONE
		binding.clearLay.progressTV.visibility = View.GONE
		binding.clearLay.bfbTV.visibility = View.GONE
		binding.clearLay.clearTV.visibility = View.GONE
		binding.clearLay.clearingTV.visibility = View.GONE
		
		
		binding.scanLay.scanLAV.visibility = View.VISIBLE
		binding.scanLay.scanLAV.setAnimation("clear_scan.json");
//      lottieAnimationView01.loop(true);//循环播放动画，已经废弃，但是还可以使用，建议使用下面的两行代码
// 		binding.lottieIV.setRepeatMode(LottieDrawable.REVERSE);//设置播放模式
		binding.scanLay.scanLAV.setRepeatCount(10);//设置重复次数
		binding.scanLay.scanLAV.playAnimation()
		
		showFileView()
		
	}
	
	
	fun showFileView() {
		mRecyclerView = binding.fileLay.recyclerView
		initRecycler(1, 1, 0)
		binding.fileLay.recyclerView.adapter = mAdapter
		
		
		lifecycleScope.launch {
			withContext(Dispatchers.Default) {
				delay(5000)
			}
			
			binding.scanLay.scanLAV.cancelAnimation()
			binding.scanLay.scanLAV.visibility = View.GONE
			binding.scanLay.scanningTV.visibility = View.GONE
			binding.fileLay.fileView.visibility = View.VISIBLE
			
			val junkList = withContext(Dispatchers.IO) {
				
				val junkModelList = MyDataBase.instance.JunkDao().getAllJunk()
				
				if (junkModelList.isEmpty()) {
					val junkModel = JunkModel()
					junkModel.junkName = "1"
					junkModel.recycleTime = 1710491855555
					junkModel.isCheck = false
					junkModel.junkSize = CacheUtil().generateValue(junkModel.recycleTime) * 8600
					MyDataBase.instance.JunkDao().insert(junkModel)  // 插入数据
					
					val junkModelB = JunkModel()
					junkModelB.junkName = "2"
					junkModelB.recycleTime = 1710491824999
					junkModelB.isCheck = false
					junkModelB.junkSize = CacheUtil().generateValue(junkModelB.recycleTime) * 6600
					MyDataBase.instance.JunkDao().insert(junkModelB)  // 插入数据
					
					junkModelList.add(junkModel)
					junkModelList.add(junkModelB)
				} else {
					
					val junkModel = junkModelList[0]
					val junkModelB = junkModelList[1]
					
					junkModel.junkSize = CacheUtil().generateValue(junkModel.recycleTime) * 8600
					junkModelB.junkSize = CacheUtil().generateValue(junkModelB.recycleTime) * 6600
					
					MyDataBase.instance.JunkDao().upJunkSize("1", junkModel.junkSize)
					MyDataBase.instance.JunkDao().upJunkSize("2", junkModelB.junkSize)
					junkModelList.clear()
					junkModelList.add(junkModel)
					junkModelList.add(junkModelB)
				}
				junkModelList
			}
			
			
			withContext(Dispatchers.Main) {
				mAdapter.setNewInstance(junkList)
				binding.fileLay.sizeTV.text = UnitConversion.getMB(junkList[0].junkSize + junkList[1].junkSize).toString()
			}
			
		}
		
		
		//清理按钮
		binding.fileLay.clearTV.setOnClickListener {
			if (!mAdapter.data[0].isCheck && !mAdapter.data[1].isCheck) {
				ToastUtils.showShort(getString(R.string.please_select_the_garbage_you_want_to_clean_first))
				return@setOnClickListener
			}
			
			
			lifecycleScope.launch(Dispatchers.IO) {
				if (mAdapter.data[0].isCheck) {
					MyDataBase.instance.JunkDao().upJunkTime("1", System.currentTimeMillis())
				}
				if (mAdapter.data[1].isCheck) {
					MyDataBase.instance.JunkDao().upJunkTime("2", System.currentTimeMillis() - 8600)
				}
			}
			showClearView()
		}
	}
	
	override fun initViewObservable() {
		super.initViewObservable()
		LiveEventBus.get(Constants.EVENT_CACHE_FILE_SIZE, JunkModel::class.java).observe(this) {
			if (it.isCheck) {
				totalSize += it.junkSize
			} else {
				totalSize -= it.junkSize
			}
			binding.fileLay.clearTV.setText(getString(R.string.clean) + "  " + MyUtils.byte2FitMemorySize(totalSize))
			
		}
	}
	
	
	fun showClearView() {
		binding.fileLay.fileView.visibility = View.GONE
		binding.clearLay.processLAV.visibility = View.VISIBLE
		binding.clearLay.progressTV.visibility = View.VISIBLE
		binding.clearLay.bfbTV.visibility = View.VISIBLE
		binding.clearLay.clearingTV.visibility = View.VISIBLE
		
		binding.clearLay.processLAV.setAnimation("clearing_finish.json");
// lottieAnimationView01.loop(true);//循环播放动画，已经废弃，但是还可以使用，建议使用下面的两行代码
//		binding.lottieIV.setRepeatMode(LottieDrawable.REVERSE);//设置播放模式
		binding.clearLay.processLAV.setRepeatCount(0);//设置重复次数
		binding.clearLay.processLAV.playAnimation()
		
		binding.clearLay.progressTV.text = ""
		
		val timer = Timer()
		timer.schedule(timerTask {
			if (progressInt != 100) {
				progressInt += 1
				lifecycleScope.launch(Dispatchers.Main) {
					binding.clearLay.progressTV.text = "$progressInt"
					if (progressInt == 100) {
						delay(1000)
						binding.clearLay.progressTV.visibility = View.GONE
						binding.clearLay.clearingTV.visibility = View.GONE
						delay(2000)
						binding.clearLay.clearTV.visibility = View.VISIBLE
						binding.clearLay.clearTV.text =
							getString(R.string.cleared_up) +" "+ MyUtils.byte2FitMemorySize(totalSize) +" "+ getString(R.string.junk)
					}
				}
			} else {
				timer.cancel()
			}
		}, 0, 40)
	}
	
	
}