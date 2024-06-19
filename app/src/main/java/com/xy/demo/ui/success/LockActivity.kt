package com.xy.demo.ui.success

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
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
import com.xy.demo.databinding.ActivityLockBinding
import com.xy.demo.ui.common.PDFActivity
import com.xy.demo.ui.dialog.DialogManage
import com.xy.demo.ui.vm.MainViewModel
import com.xy.demo.utils.CompressUtil
import java.io.File
import java.io.IOException

class LockActivity : MBBaseActivity<ActivityLockBinding, MainViewModel>() {
	
	
	lateinit var filePath: String
	
	companion object {
		fun newInstance(activity: Activity, filePath: String) {   //doType  1：压缩 2 加密 3 解密 4 提取文字  5 提取图片
			val intent = Intent()
			intent.putExtra("filePath", filePath)
			intent.setClass(activity, LockActivity::class.java)
			activity.startActivity(intent)
		}
	}
	
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
	override fun getLayoutId(): Int {
		return R.layout.activity_lock
	}
	
	
	override fun initView() {
		super.initView()
		
		
		filePath = intent.getStringExtra("filePath").toString()
		
		val file = File(filePath)
		
		titleBarView?.setTitle(file.name)
		binding.nameTV.text = file.name
		
		val locationStr = getString(R.string.file_location)
		val spannableString = SpannableString(locationStr + filePath)
		spannableString.setSpan(ForegroundColorSpan(Color.BLUE), locationStr.length, spannableString.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		binding.locationTV.text = spannableString
		
		
		notifyMediaStore(filePath)
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
		val file = File(filePath)
		when (view) {
//			binding.compressTV -> DialogManage.showCompressDialog(this@PdfCreateActivity,)
//			binding.compressTV -> DialogManage.showCompressDialog(this@PdfCreateActivity,)
			binding.openTV -> {
				try {
					// 尝试加载 PDF 文件，不提供密码
					val document = PDDocument.load(File(filePath))
					PDFActivity.setNewInstance(this@LockActivity, filePath, file.name, 2)
				} catch (e: InvalidPasswordException) {
					DialogManage.showDecodeDialog(this@LockActivity, filePath, file.name, 1)
				} catch (e: IOException) {
				
				}
			}
			
			binding.shareTV -> {
				CompressUtil.shareFile(this@LockActivity,filePath)
//				PdfListActivity.newInstance(this@ImgToPdfActivity, Constants.PDF_FROM_COMPRESS)
			}
		}
	}
}