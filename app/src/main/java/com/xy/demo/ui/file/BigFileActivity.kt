package com.xy.demo.ui.file

import android.os.Environment
import android.view.View
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewModelScope
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.jeremyliao.liveeventbus.LiveEventBus
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityCacheBinding
import com.xy.demo.model.FirstModel
import com.xy.demo.model.SecondModel
import com.xy.demo.utils.MyUtils
import com.xy.demo.utils.UnitConversion
import com.xy.demo.utils.file.FileSearcher
import com.xy.demo.utils.file.FileTools
import com.xy.demo.view.ExpandListViewAdapter
import com.xy.xframework.utils.DateUtils
import com.xy.xframework.utils.Globals
import com.xy.xframework.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File
import java.util.Locale


class BigFileActivity : MBBaseActivity<ActivityCacheBinding, MBBaseViewModel>() {
	
	
	var mListData = ArrayList<FirstModel>()
	
	var needClearFile = ArrayList<File>()
	
	var needClearSize: Long = 0
	
	var allFileSize: Long = 0
	
	private var mAdapter: ExpandListViewAdapter? = null
	
	
	override fun showTitleBar(): Boolean {
		return super.showTitleBar()
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
	override fun getLayoutId(): Int {
		return R.layout.activity_cache
	}
	
	
	override fun initLogic() {
		
		titleBarView?.setBackgroundColor(resources.getColor(R.color.colorBarColor, theme))
		titleBarView?.tvTitle?.text = getString(R.string.big_files_cleaner)
		titleBarView?.setLeftIcon(R.drawable.ic_white_back)
		binding.scanLay.scanLAV.visibility = View.VISIBLE
		binding.scanLay.scanningTV.visibility = View.VISIBLE
		binding.scanLay.scanLAV.setAnimation("clear_scan.json");
//      lottieAnimationView01.loop(true);//循环播放动画，已经废弃，但是还可以使用，建议使用下面的两行代码
// 		binding.lottieIV.setRepeatMode(LottieDrawable.REVERSE);//设置播放模式
		binding.scanLay.scanLAV.setRepeatCount(-1);//设置重复次数
		binding.scanLay.scanLAV.playAnimation()
		
		
		XXPermissions.with(this)
			.permission(Permission.MANAGE_EXTERNAL_STORAGE)
			.request(object : OnPermissionCallback {
				override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
					initDate()
				}
				
				override fun onDenied(permissions: MutableList<String>, allGranted: Boolean) {
					ToastUtils.showLong(getString(R.string.please_go_to_application_management_to_grant_permissions))
					finish()
				}
			})
		
		
		mAdapter = ExpandListViewAdapter(mListData, this)
		binding.recyclerView.setAdapter(mAdapter)
		binding.recyclerView.cacheColorHint = 1000
		
		binding.clearTV.setOnClickListener {
			lifecycleScope.launch() {
				
				var clearFinish = async {
					for (i in needClearFile.indices) {
						needClearFile[i].delete()
					}
					delay(5000)
					"deleting"
				}
				
				
				withContext(Dispatchers.Main) {
					showClearView()
				}
				
				clearFinish.await()
				//清理完成
				withContext(Dispatchers.Main) {
					binding.finishLay.finisIV.visibility = View.VISIBLE
					binding.finishLay.finisTV.visibility = View.VISIBLE
					binding.finishLay.finisTV.text =
						getString(R.string.cleared_up) + " " + MyUtils.byte2FitMemorySize(needClearSize) + " " + getString(R.string.junk)
					binding.scanLay.scanLAV.visibility = View.GONE
					binding.scanLay.scanningTV.visibility = View.GONE
				}
			}
			
		}
		
	}
	
	override fun initViewObservable() {
		super.initViewObservable()
		
		LiveEventBus.get<String>(Constants.EVENT_BIG_FILE_SIZE).observe(this) {
			needClearSize = 0
			needClearFile.clear()
			for (i in mListData.indices) {
				for (j in mListData[i].listSecondModel.indices) {
					
					if (mListData[i].listSecondModel[j].isCheck) {
						needClearFile.add(mListData[i].listSecondModel[j].file)
						needClearSize += mListData[i].listSecondModel[j].file.length()
					}
				}
			}
			binding.clearTV.text = getString(R.string.clean) + "  " + MyUtils.byte2FitMemorySize(needClearSize)
		}
		
		
	}
	
	
	//存储空间信息初始化
	private fun spaceMessageInit() {
		val dir = File(Environment.getExternalStorageDirectory().toString())
		val freeSpace = dir.freeSpace
		val freeSpaceToGB: Float = UnitConversion.getGB(freeSpace)
		val totalSpace = dir.totalSpace
		val totalSpaceToGB: Float = UnitConversion.getGB(totalSpace)
		val usedSpace = totalSpace - freeSpace
		val usedSpaceToGB: Float = UnitConversion.getGB(usedSpace)
		
		
	}
	
	
	private fun initDate() {
		allFileSize = 0
		viewModel.viewModelScope.launch {
			val apkResult = async(Dispatchers.IO) {
				makeFileList(FileTools.APK)
			}
			
			val musicResult = async(Dispatchers.IO) {
				makeFileList(FileTools.MUSIC)
			}
			
			val imgResult = async(Dispatchers.IO) {
				makeFileList(FileTools.IMAGE)
			}
			
			val videoResult = async(Dispatchers.IO) {
				makeFileList(FileTools.VIDEO)
			}
			val docResult = async(Dispatchers.IO) {
				makeFileList(FileTools.DOCUMENT)
			}
			
			
			val results = listOf(apkResult.await(), musicResult.await(), imgResult.await(), videoResult.await(), docResult.await())
			
			withContext(Dispatchers.Main) {
				binding.scanLay.scanLAV.visibility = View.GONE
				binding.scanLay.scanningTV.visibility = View.GONE
				binding.scanLay.scanLAV.cancelAnimation()
				binding.fileLin.visibility = View.VISIBLE
			}
		}
		
	}
	
	
	private fun makeFileList(fileType: Int): String {
		
		val dir = File(Environment.getExternalStorageDirectory().toString())
		lateinit var fileSearcher: FileSearcher
		val firstModel = FirstModel()
		
		
		when (fileType) {
			FileTools.APK -> {
				Globals.log("xxxxxxxfile APK")
				fileSearcher = FileSearcher(dir, FileTools.APK)
				firstModel.title = getString(R.string.apk)
				firstModel.icon = R.drawable.icon_big_apk
			}
			
			FileTools.MUSIC -> {
				Globals.log("xxxxxxxfile MUSIC")
				fileSearcher = FileSearcher(dir, FileTools.MUSIC)
				firstModel.title = getString(R.string.music)
				firstModel.icon = R.drawable.icon_big_audio
			}
			
			FileTools.IMAGE -> {
				Globals.log("xxxxxxxfile IMAGE")
				fileSearcher = FileSearcher(dir, FileTools.IMAGE)
				firstModel.title = getString(R.string.image)
				firstModel.icon = R.drawable.icon_big_image
			}
			
			FileTools.VIDEO -> {
				Globals.log("xxxxxxxfile VIDEO")
				fileSearcher = FileSearcher(dir, FileTools.VIDEO)
				firstModel.title = getString(R.string.video)
				firstModel.icon = R.drawable.icon_big_video
			}
			
			FileTools.DOCUMENT -> {
				Globals.log("xxxxxxxfile DOCUMENT")
				fileSearcher = FileSearcher(dir, FileTools.DOCUMENT)
				firstModel.title = getString(R.string.document)
				firstModel.icon = R.drawable.icon_big_doc
			}
		}
		
		
		val listSecondModel: MutableList<SecondModel> = ArrayList()
		val allMusicFiles = fileSearcher.search()
		
		
		for (j in allMusicFiles.indices) {
			
			//过滤 大于 10M  的文件 才展示
			if (allMusicFiles[j].length() < 10 * 1024 * 1024) {
				continue
			}
			val secondModel = SecondModel()
			secondModel.isCheck = false
			secondModel.title = allMusicFiles[j].name
			secondModel.fileTime = DateUtils.getTimeStampString(allMusicFiles[j].lastModified(), "yyyy-MM-dd HH:mm:ss", 0)
			secondModel.fileSizeStr = MyUtils.byte2FitMemorySize(allMusicFiles[j].length())
			secondModel.fileSize = allMusicFiles[j].length()
			
			allFileSize += allMusicFiles[j].length()
			secondModel.file = allMusicFiles[j]
			listSecondModel.add(secondModel)
		}
		
		firstModel.isCheck = false
		firstModel.listSecondModel = listSecondModel
		mListData.add(firstModel)
		
		
		val fitMemorySize = MyUtils.byte2FitMemorySize(allFileSize)
		
		
		if (fitMemorySize.contains("KB")) {
			binding.fileUnitTV.text = "KB"
			binding.fileSizeTV.text = fitMemorySize.substring(0, fitMemorySize.length - 2)
		} else if (fitMemorySize.contains("MB")) {
			binding.fileUnitTV.text = "MB"
			binding.fileSizeTV.text = fitMemorySize.substring(0, fitMemorySize.length - 2)
		} else if (fitMemorySize.contains("GB")) {
			binding.fileUnitTV.text = "GB"
			binding.fileSizeTV.text = fitMemorySize.substring(0, fitMemorySize.length - 2)
		} else {
			binding.fileUnitTV.text = "B"
			binding.fileSizeTV.text = fitMemorySize.substring(0, fitMemorySize.length - 1)
		}
		
		
		return ""
	}
	
	
	fun showClearView() {
		
		binding.fileLin.visibility = View.GONE
		
		binding.scanLay.scanLAV.visibility = View.VISIBLE
		binding.scanLay.scanningTV.visibility = View.VISIBLE
		binding.scanLay.scanLAV.setAnimation("clear_scan.json");
//      lottieAnimationView01.loop(true);//循环播放动画，已经废弃，但是还可以使用，建议使用下面的两行代码
// 		binding.lottieIV.setRepeatMode(LottieDrawable.REVERSE);//设置播放模式
		binding.scanLay.scanLAV.setRepeatCount(-1);//设置重复次数
		binding.scanLay.scanLAV.playAnimation()
		binding.scanLay.scanningTV.text = getString(R.string.clearing)
	}
	
}