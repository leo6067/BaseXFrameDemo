package com.xy.demo.ui

import android.os.Environment
import android.util.Log
import android.view.View
import android.widget.ExpandableListView
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityCacheBinding
import com.xy.demo.model.FirstModel
import com.xy.demo.model.SecondModel
import com.xy.demo.model.ThirdModel
import com.xy.demo.utils.UnitConversion
import java.io.File

class CacheActivity : MBBaseActivity<ActivityCacheBinding, MBBaseViewModel>() {
	
	
	var mListData = ArrayList<FirstModel>()
	
	private var mAdapter: ExpandListViewAdapter? = null
	
	
	override fun getLayoutId(): Int {
		return R.layout.activity_cache
	}
	
	
	override fun initLogic() {
		super.initLogic()
		spaceMessageInit()
		
		initDate()
		
		mAdapter = ExpandListViewAdapter(mListData, this)
		binding.recyclerView.setAdapter(mAdapter)
 
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
		
		binding.spaceTV.text =
			"""
	    	总 共：${totalSpaceToGB}GB
	    	已 用：${usedSpaceToGB}GB
	    	剩 余：${freeSpaceToGB}GB
	    	""".trimIndent()
		 
	}
	
	
	private fun initDate() {
		
		val firstModel = FirstModel()
		val listSecondModel: MutableList<SecondModel> = ArrayList<SecondModel>()
		
		for (j in 0..5) {
			val secondModel = SecondModel()
			secondModel.setCheck(false)
			secondModel.setTitle(" 视频 $j")
			listSecondModel.add(secondModel)
		}
		firstModel.setCheck(false)
		firstModel.setTitle(" 视频缓存 ")
		firstModel.setListSecondModel(listSecondModel)
		
		mListData.add(firstModel)
		
		
		
		val firstModelA = FirstModel()
		val listSecondModelA: MutableList<SecondModel> = ArrayList<SecondModel>()
		
		for (j in 0..5) {
			val secondModel = SecondModel()
			secondModel.setCheck(false)
			secondModel.setTitle(" 图片 $j")
			listSecondModelA.add(secondModel)
		}
		firstModelA.setCheck(false)
		firstModelA.setTitle(" 图片缓存 ")
		firstModelA.setListSecondModel(listSecondModelA)
		
		mListData.add(firstModelA)
		
		
		
		val firstModelB = FirstModel()
		val listSecondModelB: MutableList<SecondModel> = ArrayList<SecondModel>()
		
		for (j in 0..5) {
			val secondModel = SecondModel()
			secondModel.setCheck(false)
			secondModel.setTitle(" 安装包 $j")
			listSecondModelB.add(secondModel)
		}
		firstModelB.setCheck(false)
		firstModelB.setTitle(" 无用安装包 ")
		firstModelB.setListSecondModel(listSecondModelB)
		
		mListData.add(firstModelB)
		
		
 
	}
 
	
}