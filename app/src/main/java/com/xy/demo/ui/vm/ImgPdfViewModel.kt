package com.xy.demo.ui.vm

import android.app.Activity
import android.app.Application
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDPage
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream
import com.tom_roush.pdfbox.pdmodel.common.PDRectangle
import com.tom_roush.pdfbox.pdmodel.graphics.image.PDImageXObject
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.model.ImagePdfParam
import com.xy.demo.model.PageSizeModel
import com.xy.demo.network.Globals
import com.xy.demo.ui.dialog.DialogManage
import com.xy.demo.ui.success.ImgToPdfActivity
import com.xy.demo.utils.ImageToPdfConverter
import com.xy.xframework.command.SingleLiveEvent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Locale

/**
 * author: Leo
 * createDate: 2022/11/3 17:29
 */
class ImgPdfViewModel(application: Application) : MBBaseViewModel(application) {


	private val pageSizeModelS = ArrayList<PageSizeModel>()
	
	var pageSizeModelList = SingleLiveEvent<ArrayList<PageSizeModel>>()
	
	
	fun getPageSizes(){
		pageSizeModelS.clear()
		pageSizeModelS.add(PageSizeModel("A0","841*1184mm",true))
		pageSizeModelS.add(PageSizeModel("A1","594*841mm",false))
		pageSizeModelS.add(PageSizeModel("A2","420*594mm",false))
		pageSizeModelS.add(PageSizeModel("A3","297*420mm",false))
		pageSizeModelS.add(PageSizeModel("A4","210*297mm",false))
		pageSizeModelS.add(PageSizeModel("A5","148*210mm",false))
//		pageSizeModelS.add(PageSizeModel("A6","105*148mm",true))
		pageSizeModelS.add(PageSizeModel("Letter","216*279mm",false))
		pageSizeModelS.add(PageSizeModel("Legal","216*356mm",false))
		pageSizeModelList.postValue(pageSizeModelS)
	}
	
	
	
	
	// 图片转PDF
	fun createPDF(activity: Activity,imagePdfParam: ImagePdfParam){
	
		// 输出PDF文件路径
		val filePath = Constants.publicXXYDir + imagePdfParam.name + ".pdf"
		var pageSize = PDRectangle.A0  // 设置页面尺寸，可以是 A0, A1, A2, A3, A4, A5 等
		val isLandscape = imagePdfParam.direction ==2 // 是否为横向
		when(imagePdfParam.pageSize){
			0->pageSize = PDRectangle.A0
			1->pageSize = PDRectangle.A1
			2->pageSize = PDRectangle.A2
			3->pageSize = PDRectangle.A3
			4->pageSize = PDRectangle.A4
			5->pageSize = PDRectangle.A5
			6->pageSize = PDRectangle.LETTER
			7->pageSize = PDRectangle.LEGAL
		}
		
//		ImageToPdfConverter.convertImagesToPdf(activity,imagePdfParam, pageSize, isLandscape,filePath)
//		try {
//			// 创建一个空的PDF文档
//			val document = PDDocument()
//			// 遍历图片所有图片
//			for (index in 0 until  imagePdfParam.imgPathList.size){
//				val imageFile = File(imagePdfParam.imgPathList[index])
//				if (imageFile.isFile && imageFile.extension.lowercase(Locale.ROOT) in arrayOf("jpg", "jpeg", "png", "gif", "bmp")) {
//					addImageToPdf(document, imageFile, pageSize, isLandscape)
//				}
//			}
//
//			val file = File(Constants.publicXXYDir,imagePdfParam.name + ".pdf")
//			// 保存PDF文件
//			document.save(FileOutputStream(file))
//			document.close()
//			if (imagePdfParam.encryptStatus){
//				DialogManage.encryptPDF(activity,filePath,imagePdfParam.pwd)
//			}
//			imagePdfParam.path = filePath
//			activity.finish()
//			ImgToPdfActivity.newInstance(activity,imagePdfParam)
//		} catch (e: IOException) {
//			Globals.log("xxxxxxxxxxe"+e.toString())
//			e.printStackTrace()
//		}
	}
	
	
	
	
	@Throws(IOException::class)
	private fun addImageToPdf(document: PDDocument, imageFile: File, pageSize: PDRectangle, isLandscape: Boolean) {
		// 根据页面方向设置页面尺寸
		val pageDimensions = if (isLandscape) PDRectangle(pageSize.height, pageSize.width) else pageSize
		val page = PDPage(pageDimensions)
		document.addPage(page)
		// 读取图片并创建图像对象
		val imageBitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
		val imageXObject = PDImageXObject.createFromByteArray(document, getBitmapBytes(imageBitmap), imageFile.name)
		
		// 获取页面的尺寸
		val pageSize = page.mediaBox
		val margin = 28.35f // 1cm 的间隔对应大约 28.35 点
		// 计算图像的缩放比例以适应页面
		val scale = Math.min((pageSize.width - 2 * margin) / imageBitmap.width.toFloat(),
			(pageSize.height - 2 * margin) / imageBitmap.height.toFloat())
		val imageWidth = imageBitmap.width.toFloat() * scale
		val imageHeight = imageBitmap.height.toFloat() * scale
		
		// 计算图像的位置以居中显示，并留有间隔
		val xOffset = (pageSize.width - imageWidth) / 2
		val yOffset = (pageSize.height - imageHeight) / 2 + 30
		// 创建页面内容流
		val contentStream = PDPageContentStream(document, page)
		// 在页面上绘制图像
		contentStream.drawImage(imageXObject, xOffset, yOffset, imageWidth, imageHeight)
 
	 
		// 关闭内容流
		contentStream.close()
	}
	
	@Throws(IOException::class)
	private fun getBitmapBytes(bitmap: Bitmap): ByteArray {
		val outputStream = ByteArrayOutputStream()
		bitmap.compress(Bitmap.CompressFormat.PNG, 0, outputStream)
		return outputStream.toByteArray()
	}
	
	
}