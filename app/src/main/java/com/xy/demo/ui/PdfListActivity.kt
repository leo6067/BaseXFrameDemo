package com.xy.demo.ui

import android.R.attr
import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import androidx.recyclerview.widget.LinearLayoutManager
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.ActivityPdfListBinding
import com.xy.demo.db.PdfFileModel
import com.xy.demo.ui.adapter.PdfFileAdapter
import com.xy.xframework.utils.DateUtils
import com.xy.xframework.utils.FileUtils
import com.xy.xframework.utils.Globals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.ByteArrayOutputStream
import java.io.File


class PdfListActivity : MBBaseActivity<ActivityPdfListBinding,MBBaseViewModel>() {
	
	
	private val allFileList = ArrayList<PdfFileModel>()
 
	var mAdapter = PdfFileAdapter()
	
	
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_pdf_list
	}
	
	
	@SuppressLint("ResourceAsColor")
	override fun initView() {
		super.initView()
		titleBarView?.setTitle(getString(R.string.select_file))
		titleBarView?.setRightImage(getDrawable(R.drawable.ic_search_gray))
		
		binding.recyclerview.layoutManager = LinearLayoutManager(this)
		binding.recyclerview.adapter = mAdapter
		getFileList()
	}
	
	fun getFileList() {
		GlobalScope.launch {
			var temp = withContext(Dispatchers.IO) {
				documentData()
				"占位"
			}
			withContext(Dispatchers.Main) {
				mAdapter.setNewInstance(allFileList)
			}
		}
	}
	
	
	/**
	 * 获取手机文档数据
	 */
	fun documentData() {
		val columns = arrayOf(
			MediaStore.Files.FileColumns._ID,
			MediaStore.Files.FileColumns.MIME_TYPE,
			MediaStore.Files.FileColumns.SIZE,
			MediaStore.Files.FileColumns.DATE_MODIFIED,
			MediaStore.Files.FileColumns.DATA
		)
		val select = "(_data LIKE '%.pdf')"
		val cursor = contentResolver.query(MediaStore.Files.getContentUri("external"), columns, select, null, null)
		var columnIndexOrThrow_DATA = 0
		if (cursor != null) {
			columnIndexOrThrow_DATA = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
		}
		if (cursor != null) {
			while (cursor.moveToNext()) {
				val path = cursor.getString(columnIndexOrThrow_DATA)
				Globals.log("xxxxxxxxxxxx path" + path)
				getPdfInfo(path)
			}
		}
		cursor?.close()
	}
	
	
	  fun getPdfInfo(pdfPath: String) {
		val file = File(pdfPath)
		try {
			if (file.exists()) {
				// 获取文件大小
				var pdfFileModel = PdfFileModel()
				pdfFileModel.size = FileUtils.byte2FitMemorySize(file.length())
				Globals.log("xxxxxxxxxxxxpdfFileModel.size" + pdfFileModel.size)
				pdfFileModel.lastTime = DateUtils.getTimeStampString(file.lastModified(), "yyyy-MM-dd HH:mm:ss", 0)
				Globals.log("xxxxxxxxxxxxpdfFileModel.time" + file.lastModified().toString())
				pdfFileModel.name = file.name
				val parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
				val pdfRenderer = PdfRenderer(parcelFileDescriptor)
				// 获取PDF页面数量
				pdfFileModel.totalPages = pdfRenderer.pageCount.toString()
				
				// 获取 PDF 第一页
//				val page = pdfRenderer.openPage(0)
//				// 计算缩略图的宽度和高度
//				val width = resources.displayMetrics.densityDpi / 72 * page.width
//				val height = resources.displayMetrics.densityDpi / 72 * page.height
//				// 渲染页面为位图
//				val bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888)
//				page.render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
//				val baos = ByteArrayOutputStream()
//				bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos)
//				pdfFileModel.setBitmap( baos.toByteArray())
				allFileList.add(pdfFileModel)
				// 关闭资源
//				page.close()
				pdfRenderer.close()
				parcelFileDescriptor.close()
			}
		} catch (e: Exception) {
		}
	}
}