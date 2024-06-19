package com.xy.demo.ui.success

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
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
import com.xy.demo.databinding.ActivitySuccessCompressBinding
import com.xy.demo.model.CompressModel
import com.xy.demo.model.ImagePdfParam
import com.xy.demo.ui.common.PDFActivity
import com.xy.demo.ui.dialog.DialogManage
import com.xy.demo.ui.vm.MainViewModel
import com.xy.demo.utils.CompressUtil
import com.xy.xframework.utils.FileUtils
import com.xy.xframework.utils.ToastUtils
import java.io.File
import java.io.IOException


//压缩成功
class SuccessCompressActivity : MBBaseActivity<ActivitySuccessCompressBinding,MainViewModel>() {
	
	
	lateinit var compressModel: CompressModel
	companion object {
		fun newInstance(activity: Activity, compressModel: CompressModel) {
			val intent = Intent()
			intent.putExtra("compressModel", compressModel)
			intent.setClass(activity, SuccessCompressActivity::class.java)
			activity.startActivity(intent)
		}
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	
	override fun getLayoutId(): Int {
		return R.layout.activity_success_compress
	}
 
	
	
	override fun onResume() {
		super.onResume()
		dismissLoading()
	}
	override fun initView() {
		super.initView()
		
	 
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
			compressModel = intent.getSerializableExtra("compressModel", CompressModel::class.java)!!
		} else {
			@Suppress("DEPRECATION")
			compressModel = (intent.getSerializableExtra("compressModel") as? CompressModel)!!
		}
		
		
		val locationStr = getString(R.string.file_location)
		val spannableString = SpannableString(locationStr + compressModel.afterPath)
		spannableString.setSpan(ForegroundColorSpan(Color.BLUE), locationStr.length, spannableString.length, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
		binding.locationTV.text = spannableString
		binding.nameTV.text = compressModel.fileName
		
		binding.sizeTV.text = getString(R.string.file_size) +compressModel.beforeSize + "->"+compressModel.afterSize
		binding.compressTV.text = getString(R.string.decrease)+ compressModel.compressSize+")"
		
		notifyMediaStore(compressModel.afterPath)
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
					val document = PDDocument.load(File(compressModel.afterPath))
					PDFActivity.setNewInstance(this@SuccessCompressActivity, compressModel.afterPath, compressModel.fileName, 2)
				} catch (e: InvalidPasswordException) {
					DialogManage.showDecodeDialog(this@SuccessCompressActivity, compressModel.afterPath, compressModel.fileName, 1)
				} catch (e: IOException) {
					ToastUtils.showShort(getString(R.string.open_failure))
				}
				
			}
			
			
			binding.shareTV ->{
				CompressUtil.shareFile(this@SuccessCompressActivity, compressModel.afterPath)
			}
 
		}
	}
}