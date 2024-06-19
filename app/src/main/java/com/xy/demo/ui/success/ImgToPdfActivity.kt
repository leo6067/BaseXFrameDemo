package com.xy.demo.ui.success

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.text.SpannableString
import android.text.Spanned
import android.text.style.ForegroundColorSpan
import android.view.View
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.encryption.InvalidPasswordException
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity

import com.xy.demo.databinding.ActivityImgToPdfBinding
import com.xy.demo.model.ImagePdfParam
import com.xy.demo.ui.common.PDFActivity
import com.xy.demo.ui.common.PdfListActivity
import com.xy.demo.ui.dialog.DialogManage
import com.xy.demo.ui.vm.MainViewModel
import com.xy.demo.utils.CompressUtil
import com.xy.xframework.utils.FileUtils
import com.xy.xframework.utils.ToastUtils
import java.io.File
import java.io.IOException


//图片转PDF  成功
class ImgToPdfActivity : MBBaseActivity<ActivityImgToPdfBinding, MainViewModel>() {
	
	lateinit var imagePdfParam: ImagePdfParam
	
	companion object {
		fun newInstance(activity: Activity, imagePdfParam: ImagePdfParam) {
			val intent = Intent()
			intent.putExtra("imagePdfParam", imagePdfParam)
			intent.setClass(activity, ImgToPdfActivity::class.java)
			activity.startActivity(intent)
		}
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_img_to_pdf
	}
	
	
	override fun onResume() {
		super.onResume()
		dismissLoading()
	}
	override fun initView() {
		super.initView()
	
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			imagePdfParam = intent.getSerializableExtra("imagePdfParam", ImagePdfParam::class.java)!!
		} else {
			@Suppress("DEPRECATION")
			imagePdfParam = (intent.getSerializableExtra("imagePdfParam") as? ImagePdfParam)!!
		}
		
		titleBarView?.setTitle(imagePdfParam?.name)
		binding.nameTV.text = imagePdfParam.name
		
		val locationStr = getString(R.string.file_location)
		val spannableString = SpannableString(locationStr + imagePdfParam.path)
		spannableString.setSpan(ForegroundColorSpan(Color.BLUE), locationStr.length, spannableString.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		binding.locationTV.text = spannableString
		
		binding.sizeTV.text = getString(R.string.file_size) + FileUtils.getFileOrFilesSize(imagePdfParam.path, 3)+"M"
		
	 
		
		if (imagePdfParam.encryptStatus){
			DialogManage.encryptPDF(this@ImgToPdfActivity,imagePdfParam.path,imagePdfParam.pwd,true)
		}
		notifyMediaStore(imagePdfParam.path)
	}
	
	
	
	fun notifyMediaStore(filePath: String) {
		val file = File(filePath)
		val uri = Uri.fromFile(file)
		val intent = Intent(Intent.ACTION_MEDIA_SCANNER_SCAN_FILE).apply {
			data = uri
		}
		sendBroadcast(intent)
		
		LiveEventBus.get<String>(Constants.EVENT_REFRESH_FILE_FRAGMENT).post(Constants.EVENT_REFRESH_FILE_FRAGMENT)
	}
	
	
	override fun onClick(view: View) {
		when (view) {
			binding.openTV -> {
				try {
					showLoading()
					// 尝试加载 PDF 文件，不提供密码
					val document = PDDocument.load(File(imagePdfParam.path))
					PDFActivity.setNewInstance(this@ImgToPdfActivity, imagePdfParam.path, imagePdfParam.name, 2)
				} catch (e: InvalidPasswordException) {
					DialogManage.showDecodeDialog(this@ImgToPdfActivity, imagePdfParam.path, imagePdfParam.name, 1)
				} catch (e: IOException) {
					ToastUtils.showShort(getString(R.string.open_failure))
				}
			}
			binding.compressTV -> {
				DialogManage.showCompressDialog(this@ImgToPdfActivity,imagePdfParam.path)
//				PdfListActivity.newInstance(this@ImgToPdfActivity, Constants.PDF_FROM_COMPRESS)
			}
			
			binding.shareTV -> {
				CompressUtil.shareFile(this@ImgToPdfActivity,imagePdfParam.path)
//				PdfListActivity.newInstance(this@ImgToPdfActivity, Constants.PDF_FROM_COMPRESS)
			}
	 
		}
	}
}