package com.xy.demo.ui.image

import android.app.Activity
import android.content.Intent
import android.media.ImageReader
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.alibaba.android.arouter.launcher.ARouter.logger
import com.d.lib.pulllayout.rv.itemtouchhelper.OnStartDragListener
import com.d.lib.pulllayout.rv.itemtouchhelper.SimpleItemTouchHelperCallback
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDPage
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream.AppendMode
import com.tom_roush.pdfbox.pdmodel.common.PDRectangle
import com.tom_roush.pdfbox.pdmodel.graphics.image.JPEGFactory
import com.tom_roush.pdfbox.pdmodel.graphics.image.LosslessFactory
import com.tom_roush.pdfbox.pdmodel.graphics.image.PDImageXObject
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityDragSortBinding
import com.xy.demo.model.ImgModel
import com.xy.demo.ui.adapter.ItemTouchGridAdapter
import com.xy.demo.ui.vm.MainViewModel

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.util.Locale


class DragSortActivity : MBBaseActivity<ActivityDragSortBinding, MainViewModel>() {
	
	lateinit var mAdapter: ItemTouchGridAdapter
	
	lateinit var mItemTouchHelper: ItemTouchHelper
	
	val imageList = arrayListOf<ImgModel>()
	
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	companion object {
		fun newInstance(activity: Activity, imgList: ArrayList<String>) {
			val intent = Intent()
			intent.putStringArrayListExtra("imgPathList", imgList)
			intent.setClass(activity, DragSortActivity::class.java)
			activity.startActivity(intent)
		}
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_drag_sort
	}
	
	
	override fun initView() {
		super.initView()
		
		val imgPathList = intent.getStringArrayListExtra("imgPathList")
		for (index in 0 until imgPathList?.size!!) {
			val imgModel = ImgModel()
			imgModel.imgPath = imgPathList[index]
			imageList.add(imgModel)
		}
		
		binding.recyclerview.setLayoutManager(GridLayoutManager(this, 2))
		mAdapter = ItemTouchGridAdapter(this@DragSortActivity, imageList)
		binding.recyclerview.adapter = mAdapter
		
		binding.recyclerview.setCanPullDown(false)
		binding.recyclerview.setCanPullUp(false)
		binding.recyclerview.setHasFixedSize(true)
		
		onLayoutManagerChange()
	}
	
	private fun onLayoutManagerChange() {
		
		binding.recyclerview.setAdapter(mAdapter)
		
		// Step 3-2: Attaches the ItemTouchHelper to the provided RecyclerView.
		val itemTouchHelperCallback: ItemTouchHelper.Callback = SimpleItemTouchHelperCallback(mAdapter)
		mItemTouchHelper = ItemTouchHelper(itemTouchHelperCallback)
		mAdapter.setOnStartDragListener(OnStartDragListener { viewHolder -> mItemTouchHelper.startDrag(viewHolder) })
		mItemTouchHelper.attachToRecyclerView(binding.recyclerview)
	}
	
	
	
	fun initListener(){
		
		binding.saveTV.setOnClickListener {
			
			
			val file = File(mAdapter.datas[0].imgPath)
			try {
				val fileBytes = fileToByteArray(file)
				// 现在你可以使用fileBytes数组了
			} catch (e: IOException) {
				e.printStackTrace()
			}
			
			
		
		}
		
		
		
		
	}
	
	
	@Throws(IOException::class)
	fun fileToByteArray(file: File?): ByteArray? {
		// 创建一个FileInputStream来读取文件
		val fis = FileInputStream(file)
		
		// 创建一个ByteArrayOutputStream来存储文件的内容
		val bos = ByteArrayOutputStream()
		
		// 创建一个缓冲区来读取文件内容
		val buffer = ByteArray(1024)
		var bytesRead: Int
		
		// 读取文件内容，并写入到ByteArrayOutputStream中
		while (fis.read(buffer).also { bytesRead = it } != -1) {
			bos.write(buffer, 0, bytesRead)
		}
		
		// 关闭流
		fis.close()
		bos.close()
		
		// 返回转换后的byte数组
		return bos.toByteArray()
	}
	
	
//	@Throws(IOException::class)
//	fun imageToPdf(
//		files: Array<File>, fitOption: String, autoRotate: Boolean, colorType: String?
//	): ByteArray? {
//		PDDocument().use { doc ->
//			for (file in files) {
//				val contentType: String = file.getContentType()
//				val originalFilename: String = Filenames.toSimpleFileName(file.getOriginalFilename())
//				if (originalFilename != null
//					&& (originalFilename.lowercase(Locale.getDefault()).endsWith(".tiff")
//							|| originalFilename.lowercase(Locale.getDefault()).endsWith(".tif"))
//				) {
//					val reader: ImageReader = ImageIO.getImageReadersByFormatName("tiff").next()
//					reader.setInput(ImageIO.createImageInputStream(file.getInputStream()))
//					val numPages: Int = reader.getNumImages(true)
//					for (i in 0 until numPages) {
//						val pageImage: BufferedImage = reader.read(i)
//						val convertedImage: BufferedImage = ImageProcessingUtils.convertColorType(pageImage, colorType)
//						val pdImage = LosslessFactory.createFromImage(doc, convertedImage)
//						addImageToDocument(doc, pdImage, fitOption, autoRotate)
//					}
//				} else {
//					val image: BufferedImage = ImageIO.read(file.getInputStream())
//					val convertedImage: BufferedImage = ImageProcessingUtils.convertColorType(image, colorType)
//					// Use JPEGFactory if it's JPEG since JPEG is lossy
//					val pdImage = if (contentType != null && "image/jpeg" == contentType) JPEGFactory.createFromImage(
//						doc,
//						convertedImage
//					) else LosslessFactory.createFromImage(doc, convertedImage)
//					addImageToDocument(doc, pdImage, fitOption, autoRotate)
//				}
//			}
//			val byteArrayOutputStream = ByteArrayOutputStream()
//			doc.save(byteArrayOutputStream)
//
//			return byteArrayOutputStream.toByteArray()
//		}
//	}

	
	@Throws(IOException::class)
	private fun addImageToDocument(
		doc: PDDocument, image: PDImageXObject, fitOption: String, autoRotate: Boolean
	) {
		val imageIsLandscape = image.width > image.height
		var pageSize = PDRectangle.A4
		println(fitOption)
		
		//autoRotate 横向、纵向
		if (autoRotate && imageIsLandscape) {
			pageSize = PDRectangle(pageSize.height, pageSize.width)
		}
		if ("fitDocumentToImage" == fitOption) {
			pageSize = PDRectangle(image.width.toFloat(), image.height.toFloat())
		}
		val page = PDPage(pageSize)
		doc.addPage(page)
		val pageWidth = page.mediaBox.width
		val pageHeight = page.mediaBox.height
		try {
			PDPageContentStream(doc, page, AppendMode.APPEND, true, true).use { contentStream ->
				if ("fillPage" == fitOption || "fitDocumentToImage" == fitOption) {
					contentStream.drawImage(image, 0f, 0f, pageWidth, pageHeight)
				} else if ("maintainAspectRatio" == fitOption) {
					val imageAspectRatio = image.width.toFloat() / image.height.toFloat()
					val pageAspectRatio = pageWidth / pageHeight
					var scaleFactor = 1.0f
					scaleFactor = if (imageAspectRatio > pageAspectRatio) {
						pageWidth / image.width
					} else {
						pageHeight / image.height
					}
					val xPos = (pageWidth - image.width * scaleFactor) / 2
					val yPos = (pageHeight - image.height * scaleFactor) / 2
					contentStream.drawImage(
						image,
						xPos,
						yPos,
						image.width * scaleFactor,
						image.height * scaleFactor
					)
				}
			}
		} catch (e: IOException) {
		 
			throw e
		}
	}
	
	
	
	
	
}