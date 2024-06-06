package com.xy.demo.ui

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.util.Log
import androidx.lifecycle.viewModelScope
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityPdfactivityBinding
import com.xy.demo.db.MyDataBase
import com.xy.demo.db.PdfFileModel
import com.xy.demo.network.Globals
import com.xy.xframework.utils.DateUtils
import com.xy.xframework.utils.FileUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


class PDFActivity : MBBaseActivity<ActivityPdfactivityBinding, MBBaseViewModel>() {
	
	companion object {
		//fromType  1： 内存卡路劲文件  2；公开可得路径
		fun setNewInstance(activity: Activity, filePath: String, fileName: String, fromType: Int) {
			val intent = Intent(activity, PDFActivity::class.java)
			intent.putExtra("filePath", filePath)
			intent.putExtra("fileName", fileName)
			intent.putExtra("fileType", fromType)
			activity.startActivity(intent)
		}
		
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_pdfactivity
	}
	
	
	override fun initView() {
		super.initView()
		
		val filePath = intent.getStringExtra("filePath")
		val fileName = intent.getStringExtra("fileName")
		val fileType = intent.getIntExtra("fileType",2)
		
		if (fileType == 1) {
			readRawFile(filePath,fileName)
		}else{
			binding.pdfview.fromFile(File(filePath))
				.defaultPage(1)
				.showMinimap(false)     //pdf放大的时候，是否在屏幕的右上角生成小地图
				.swipeVertical(true)  //pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
				.enableSwipe(true)   //是否允许翻页，默认是允许翻页
				.load()
			getPdfInfo(filePath)
		}
	}
	
	
	fun readRawFile(filePath: String?,fileName:String?) {
		val uri = Uri.parse(filePath)
		val contentResolver = contentResolver
		try {
			contentResolver.openInputStream(uri).use { inputStream ->
				FileOutputStream(File(filesDir, fileName)).use { fileOutputStream ->
					// 读取输入流并写入输出流
					val buffer = ByteArray(1024)
					var bytesRead: Int
					while (inputStream!!.read(buffer).also { bytesRead = it } != -1) {
						fileOutputStream.write(buffer, 0, bytesRead)
					}
					binding.pdfview.fromFile(File(filesDir, fileName))
						.defaultPage(1)
						.showMinimap(false)     //pdf放大的时候，是否在屏幕的右上角生成小地图
						.swipeVertical(true)  //pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
						.enableSwipe(true)   //是否允许翻页，默认是允许翻页
						.load()
					
					getPdfInfo(filesDir.path+"/"+fileName)
				}
			}
		} catch (e: IOException) {
			e.printStackTrace()
			// 处理异常
		}
	}
	
	fun getPdfInfo(pdfPath: String?) {
		Globals.log("xxxxxxxxfilesDir...pdfPath."+pdfPath )
		val file = File(pdfPath)
		try {
			if (file.exists()) {
				// 获取文件大小
				var pdfFileModel = PdfFileModel()
				pdfFileModel.path = pdfPath
				pdfFileModel.size = FileUtils.byte2FitMemorySize(file.length())
				pdfFileModel.lastTime = DateUtils.getTimeStampString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm", 0)
				pdfFileModel.name = file.name
				val parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
				val pdfRenderer = PdfRenderer(parcelFileDescriptor)
				// 获取PDF页面数量
				pdfFileModel.totalPages = pdfRenderer.pageCount.toString()
				insertData(pdfFileModel)
				// 关闭资源
				pdfRenderer.close()
				parcelFileDescriptor.close()
			}
		} catch (e: Exception) {
		}
	}
	
	
	fun insertData(pdfFileModel:PdfFileModel){
		viewModel.viewModelScope.launch (Dispatchers.IO){
			MyDataBase.instance.PdfFileDao().insertOrReplaceByPath(pdfFileModel)
		}
	}
	
	override fun onDestroy() {
		super.onDestroy()
		binding.pdfview.recycle()
	}
	
}