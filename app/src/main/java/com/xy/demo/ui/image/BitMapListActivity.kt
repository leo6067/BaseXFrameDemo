package com.xy.demo.ui.image

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Environment
import android.util.Base64
import android.view.View
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityBitMapListBinding
import com.xy.demo.network.Globals
import com.xy.demo.ui.adapter.ImageSelectAdapter
import com.xy.demo.ui.adapter.ParamModel
import com.xy.demo.ui.dialog.DialogManage
import com.xy.demo.ui.vm.MainViewModel
import com.xy.xframework.base.BaseSharePreference
import com.xy.xframework.utils.ToastUtils
import org.json.JSONArray
import org.json.JSONException
import java.io.File
import java.io.FileOutputStream
import java.io.IOException



//提取pdf 中图片
class BitMapListActivity : MBBaseActivity<ActivityBitMapListBinding, MainViewModel>() {
	
	var mAdapter = ImageSelectAdapter()
	var selectNumber = 0
	
	companion object {
		fun newInstance(activity: Activity) {   //doType  1：压缩 2 加密 3 解密 4 提取文字  5 提取图片  6 编辑
			val intent = Intent()
			intent.setClass(activity, BitMapListActivity::class.java)
			activity.startActivity(intent)
		}
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_bit_map_list
	}
	
	@SuppressLint("ResourceAsColor")
	override fun initView() {
		super.initView()
		
		titleBarView?.setTitle(getString(R.string.photo))
		titleBarView?.getRightTextView()?.visibility = View.VISIBLE
		titleBarView?.getRightTextView()?.text = getString(R.string.select_all)
		titleBarView?.getRightTextView()?.setTextColor(R.color.color_2578FF)
		
		this.mRecyclerView = binding.recyclerview
		initRecycler(0, 0, 2)
		binding.recyclerview.adapter = mAdapter
		
	}
	
	
	override fun initParams() {
		super.initParams()
		
		var jsonString = BaseSharePreference.instance.getString(Constants.BITMAP_LIST, "")
		
		val bitmapList: MutableList<Bitmap> = ArrayList()
		val paramModelList: MutableList<ParamModel> = ArrayList()
		if (jsonString != null) {
			try {
				val jsonArray = JSONArray(jsonString)
				for (i in 0 until jsonArray.length()) {
					val encodedBitmap: String = jsonArray.getString(i)
					val byteArray: ByteArray = Base64.decode(encodedBitmap, Base64.DEFAULT)
					val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
					bitmapList.add(bitmap)
					
					var paramModel = ParamModel(bitmap, false)
					
					paramModelList.add(paramModel)
				}
			} catch (e: JSONException) {
				e.printStackTrace()
			}
		}
		
		mAdapter.setNewInstance(paramModelList)
		initListener()
	}
	
	
	fun initListener() {
		mAdapter.setOnItemClickListener { adapter, view, position ->
			mAdapter.data[position].selectStatus = !mAdapter.data[position].selectStatus
			//计数使用
			selectNumber = 0
			for (index in mAdapter.data.indices) {
				if (mAdapter.data[index].selectStatus) {
					selectNumber++
				}
			}
			if (selectNumber == mAdapter.data.size) {
				titleBarView?.getRightTextView()?.text =getString(R.string.cancel)
			} else   {
				titleBarView?.getRightTextView()?.text = getString(R.string.select_all)
			}
			mAdapter.notifyDataSetChanged()
		}
		
		
		
		
		titleBarView?.setRightClickListener {
			Globals.log("xxxxxxxxxxsetRightClickListener")
			if (selectNumber == mAdapter.data.size) {
				// "取消"
				for (index in mAdapter.data.indices) {
					mAdapter.data[index].selectStatus = false
				}
				selectNumber = 0
				titleBarView?.getRightTextView()?.text = getString(R.string.select_all)
				
			} else {
				for (index in mAdapter.data.indices) {
					mAdapter.data[index].selectStatus = true
				}
				selectNumber = mAdapter.data.size
				titleBarView?.getRightTextView()?.text = getString(R.string.cancel)
			}
			mAdapter.notifyDataSetChanged()
		}
		
		
		
		
		binding.positiveTV.setOnClickListener {
			val bitmapList: MutableList<Bitmap> = ArrayList()
			for (index in mAdapter.data.indices) {
				if (mAdapter.data[index].selectStatus) {
					bitmapList.add(mAdapter.data[index].bitmap)
				}
			}
			if (bitmapList.size ==0){
				ToastUtils.showShort(getString(R.string.please_first_select_the_photo_you_want_to_save))
				return@setOnClickListener
			}
			showLoading()
			saveBitmapListToLocalAndNotifyGallery(this@BitMapListActivity, bitmapList)
			DialogManage.showBitmapDialog(this@BitMapListActivity)
		}
		
	}
	
	
	fun saveBitmapListToLocalAndNotifyGallery(context: Context, bitmapList: List<Bitmap>) {
		val directory = File(Constants.publicXXYDir)
		if (!directory.exists()) {
			directory.mkdirs()
		}
		
		
		for ((index, bitmap) in bitmapList.withIndex()) {
			val fileName = "image_${System.currentTimeMillis()}_$index.png"
			val file = File(directory, fileName)
			saveBitmapToFile(file, bitmap)
			notifyGallery(context, file)
		}
	}
	
	private fun saveBitmapToFile(file: File, bitmap: Bitmap) {
		try {
			FileOutputStream(file).use { out ->
				bitmap.compress(Bitmap.CompressFormat.PNG, 100, out)
			}
		} catch (e: IOException) {
			e.printStackTrace()
		}
	}
	
	private fun notifyGallery(context: Context, file: File) {
		val uri = Uri.fromFile(file)
		context.sendBroadcast(Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE, uri))
	}
}