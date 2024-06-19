package com.xy.demo.ui.common

import android.app.Activity
import android.content.Intent
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.ParcelFileDescriptor
import android.text.TextUtils
import androidx.lifecycle.viewModelScope
import com.github.barteksc.pdfviewer.scroll.DefaultScrollHandle
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.encryption.InvalidPasswordException
import com.xy.demo.MainActivity
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityPdfactivityBinding
import com.xy.demo.db.MyDataBase
import com.xy.demo.db.PdfFileModel
import com.xy.demo.network.Globals
import com.xy.xframework.utils.DateUtils
import com.xy.xframework.utils.FileUtils
import com.xy.xframework.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


// PDF 阅读器  文件读取
class PDFActivity : MBBaseActivity<ActivityPdfactivityBinding, MBBaseViewModel>() {
	
	var originalFilePath: String = ""
	var fileName: String = ""
	
	companion object {
		//fromType  1： 内存卡路劲文件  2；公开可得路径
		fun setNewInstance(activity: Activity, filePath: String, fileName: String, fromType: Int) {
			val intent = Intent(activity, PDFActivity::class.java)
			intent.putExtra("filePath", filePath)
			intent.putExtra("fileName", fileName)
			intent.putExtra("fileType", fromType)
			activity.startActivity(intent)
		}
		
		fun setNewInstance(activity: Activity, filePath: String, fileName: String, fromType: Int, password: String) {
			val intent = Intent(activity, PDFActivity::class.java)
			intent.putExtra("filePath", filePath)
			intent.putExtra("fileName", fileName)
			intent.putExtra("fileType", fromType)
			intent.putExtra("password", password)
			activity.startActivity(intent)
		}
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_pdfactivity
	}
	
	override fun onResume() {
		super.onResume()
		dismissLoading()
	}
	
	override fun initView() {
		super.initView()
		
		val password = intent.getStringExtra("password")
		var filePath = intent.getStringExtra("filePath").toString()
		fileName = intent.getStringExtra("fileName").toString()
		val fileType = intent.getIntExtra("fileType", 2)
		
		originalFilePath = filePath
		titleBarView?.setTitle(fileName)
		
		if (!TextUtils.isEmpty(password)) {
			val document = PDDocument.load(File(filePath), password)
			// 移除加密信息
			document.setAllSecurityToBeRemoved(true)
			// 创建临时文件以存储解密后的PDF文档
			val tempFile = File("$cacheDir/decrypted.pdf")
			filePath = "$cacheDir/decrypted.pdf"
			document.save(tempFile.absolutePath)
			document.close()
		}
		
		Globals.log("xxxxxxxfileTypefileType" + fileType + "    filePath" + filePath)
		
		getPdfInfo()
		if (fileType == 1) {
			readRawFile(filePath, fileName)
		} else {
			try {
				binding.pdfView.fromFile(File(filePath))
					.defaultPage(1)
					.enableAnnotationRendering(true)
					.enableAntialiasing(true)
					.spacing(10) // in dp
				 
//                .pageFitPolicy(FitPolicy.BOTH)
					.enableSwipe(true)
					.scrollHandle(  DefaultScrollHandle(getBaseContext()))
					.load();
				
			} catch (e: Exception) {
				Globals.log("xxxxxx--------**+++++++**-" + e.message)
			}
		}
	}
	
	
	override fun onStop() {
		super.onStop()
		val tempFile = File("$cacheDir/decrypted.pdf")
		if (tempFile.exists()) {
			tempFile.delete()
		}
	}
	
	
	fun readRawFile(filePath: String?, fileName: String) {
		
		val uri = Uri.parse(filePath)
		val contentResolver = contentResolver
		try {
			contentResolver.openInputStream(uri).use { inputStream ->
				FileOutputStream(File(filesDir, fileName)).use { fileOutputStream ->
					titleBarView?.setTitle(fileName)
					// 读取输入流并写入输出流
					val buffer = ByteArray(1024)
					var bytesRead: Int
					while (inputStream!!.read(buffer).also { bytesRead = it } != -1) {
						fileOutputStream.write(buffer, 0, bytesRead)
					}
					try {
						binding.pdfView.fromFile(File(filesDir, fileName))
							.defaultPage(0)
//							.showMinimap(false)     //pdf放大的时候，是否在屏幕的右上角生成小地图
//							.swipeVertical(true)  //pdf文档翻页是否是垂直翻页，默认是左右滑动翻页
							.enableSwipe(true)   //是否允许翻页，默认是允许翻页
							.load()
					} catch (e: Exception) {
					
					}
				}
			}
		} catch (e: IOException) {
			e.printStackTrace()
			// 处理异常
		}
	}
	
	fun getPdfInfo() {
		val file = File(originalFilePath)
		
		try {
			if (file.exists()) {
				// 获取文件大小
				val pdfFileModel = PdfFileModel()
				pdfFileModel.path = originalFilePath
				pdfFileModel.size = FileUtils.byte2FitMemorySize(file.length())
				pdfFileModel.lastTime = DateUtils.getTimeStampString(System.currentTimeMillis(), "yyyy-MM-dd HH:mm", 0)
				pdfFileModel.name = fileName
				insertData(pdfFileModel)

//				val parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
//				val pdfRenderer = PdfRenderer(parcelFileDescriptor)
				// 获取PDF页面数量
//				pdfFileModel.totalPages = pdfRenderer.pageCount.toString()
				// 关闭资源
//				pdfRenderer.close()
//				parcelFileDescriptor.close()
			}
		} catch (e: Exception) {
		}
	}
	
	
	fun insertData(pdfFileModel: PdfFileModel) {
		viewModel.viewModelScope.launch(Dispatchers.IO) {
			MyDataBase.instance.PdfFileDao().insertOrReplaceByPath(pdfFileModel)
		}
	}
	
	override fun onDestroy() {
		super.onDestroy()
		binding.pdfView.recycle()
	}
	
}