package com.xy.demo.ui.vm

import android.app.Application
import android.content.ContentResolver
import android.graphics.pdf.PdfRenderer
import android.os.ParcelFileDescriptor
import android.provider.MediaStore
import androidx.lifecycle.viewModelScope
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.db.PdfFileModel
import com.xy.xframework.base.XBaseApplication.Companion.application
import com.xy.xframework.command.SingleLiveEvent
import com.xy.xframework.utils.DateUtils
import com.xy.xframework.utils.FileUtils
import com.xy.xframework.utils.Globals
import kotlinx.coroutines.launch
import java.io.File

/**
 * author: Leo
 * createDate: 2022/11/3 17:29
 */
class FileViewModel(application: Application) : MBBaseViewModel(application) {
	private val allFileList = ArrayList<PdfFileModel>()
	
	var fileList = SingleLiveEvent<ArrayList<PdfFileModel>>()
	
	/**
	 * 获取手机指定类型 file
	 */
	fun getDocumentFile() {
		allFileList.clear()
		val columns = arrayOf(
			MediaStore.Files.FileColumns._ID,
			MediaStore.Files.FileColumns.MIME_TYPE,
			MediaStore.Files.FileColumns.SIZE,
			MediaStore.Files.FileColumns.DATE_MODIFIED,
			MediaStore.Files.FileColumns.DATA
		)
//		val select = "(_data LIKE '%.pdf')"
		val selection = "${MediaStore.Files.FileColumns.DATA} LIKE ?"
		val selectionArgs = arrayOf("%.pdf")
		val contentResolver: ContentResolver = application.contentResolver
		val cursor = contentResolver.query(MediaStore.Files.getContentUri("external"), columns, selection, selectionArgs, null)
		var columnIndexOrThrow_DATA = 0
//		if (cursor != null) {
//			columnIndexOrThrow_DATA = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
//		}
		if (cursor != null) {
			while (cursor.moveToNext()) {
				val path = cursor.getString(cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA))
				Globals.log("xxxxxxxxxxxxpdfFileModel.pdfPath  path-----" + path)
//				val path = cursor.getString(columnIndexOrThrow_DATA)
				getPdfInfo(path)
			}
			createFileList()
		}
		cursor?.close()
	}
	
	
	open fun getPdfInfo(pdfPath: String) {
		Globals.log("xxxxxxxxxxxxpdfFileModel.pdfPath------" + pdfPath)
		val file = File(pdfPath)
		try {
			if (file.exists()) {
				// 获取文件大小
				var pdfFileModel = PdfFileModel()
			
				pdfFileModel.path = pdfPath
				pdfFileModel.size = FileUtils.byte2FitMemorySize(file.length())
				pdfFileModel.lastTime = DateUtils.getTimeStampString(file.lastModified(), "yyyy-MM-dd HH:mm", 0)
				pdfFileModel.name = file.name
//				val parcelFileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
//				val pdfRenderer = PdfRenderer(parcelFileDescriptor)
//				// 获取PDF页面数量
//				pdfFileModel.totalPages = pdfRenderer.pageCount.toString()
				
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
//				// 关闭资源 缩略图
//				pdfFileModel.setBitmap( baos.toByteArray())
				allFileList.add(pdfFileModel)
//				page.close()
//				pdfRenderer.close()
//				parcelFileDescriptor.close()
			}
		} catch (e: Exception) {
			Globals.log("xxxxxxxxException"+e.toString())
		}
	}
	
	
	
	
	//通知更新 列表
	fun createFileList(){
		viewModelScope.launch {
			fileList.value = allFileList
		}
	}
	
	
	
	fun getSearchDocumentFile(fileName:String) {
		allFileList.clear()
		val columns = arrayOf(
			MediaStore.Files.FileColumns._ID,
			MediaStore.Files.FileColumns.MIME_TYPE,
			MediaStore.Files.FileColumns.SIZE,
			MediaStore.Files.FileColumns.DATE_MODIFIED,
			MediaStore.Files.FileColumns.DATA
		)
		// 注意：这里我们使用了 LIKE 语句来匹配文件名部分
		val select = "${MediaStore.Files.FileColumns.DISPLAY_NAME} LIKE ? AND ${MediaStore.Files.FileColumns.MIME_TYPE} = ?"
		val selectionArgs = arrayOf("%$fileName%", "application/pdf")
		
		val contentResolver: ContentResolver = application.contentResolver
		val cursor = contentResolver.query(MediaStore.Files.getContentUri("external"), columns, select, selectionArgs, null)
		var columnIndexOrThrow_DATA = 0
		if (cursor != null) {
			columnIndexOrThrow_DATA = cursor.getColumnIndexOrThrow(MediaStore.Files.FileColumns.DATA)
		}
		if (cursor != null) {
			while (cursor.moveToNext()) {
				val path = cursor.getString(columnIndexOrThrow_DATA)
				getPdfInfo(path)
			}
			createFileList()
		}
		cursor?.close()
	}
	
	
	
 
	
}