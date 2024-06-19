package com.xy.demo.ui.image

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.text.TextUtils
import android.view.View
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.PDPage
import com.tom_roush.pdfbox.pdmodel.PDPageContentStream
import com.tom_roush.pdfbox.pdmodel.common.PDRectangle
import com.tom_roush.pdfbox.pdmodel.graphics.image.PDImageXObject
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityImagePdfBinding
import com.xy.demo.model.ImagePdfParam
import com.xy.demo.ui.adapter.PageSizeAdapter
import com.xy.demo.ui.vm.ImgPdfViewModel
import com.xy.demo.utils.ImageToPdfConverter
import com.xy.xframework.utils.Globals
import com.xy.xframework.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.io.IOException
import java.util.Locale


// 图片转 PDF 参数设置
class PdfParamActivity : MBBaseActivity<ActivityImagePdfBinding, ImgPdfViewModel>() {
	
	
	var mAdapter = PageSizeAdapter()
	var imagePdfParam = ImagePdfParam()
	
	
	companion object {
		fun newInstance(activity: Activity, imgList: ArrayList<String>) {
			val intent = Intent()
			intent.putStringArrayListExtra("imgPathList", imgList)
			intent.setClass(activity, PdfParamActivity::class.java)
			activity.startActivity(intent)
		}
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_image_pdf
	}
	
	
	override fun initView() {
		super.initView()
		titleBarView?.setTitle(getString(R.string.pdf_settings))
		mRecyclerView = binding.recyclerview
		initRecycler(0, 0, 2)
		binding.recyclerview.adapter = mAdapter
		
		viewModel.getPageSizes()
		
	}
	
	override fun initViewObservable() {
		super.initViewObservable()
		
		val imgPathList = intent.getStringArrayListExtra("imgPathList")
		
		imagePdfParam.imgPathList = imgPathList
		
		viewModel.pageSizeModelList.observe(this) {
			mAdapter.setNewInstance(it)
		}
		
		mAdapter.setOnItemClickListener { adapter, view, position ->
			Globals.log("xxxxxxxxposition" + position)
			mAdapter.setCheck(position)
			imagePdfParam.pageSize = position
		}
		
		
		
		binding.orRG.setOnCheckedChangeListener { radioGroup, id ->
			when (id) {
				R.id.autoRB -> imagePdfParam.direction = 1
				R.id.orhRB -> imagePdfParam.direction = 1
				R.id.orvRB -> imagePdfParam.direction = 2
			}
		}
		
		
		binding.pwCB.setOnCheckedChangeListener { compoundButton, b ->
			if (b) {
				binding.pwET.visibility = View.VISIBLE
			} else {
				binding.pwET.visibility = View.GONE
			}
			imagePdfParam.encryptStatus = b
		}
		
		
		binding.closeIV.setOnClickListener {
			binding.nameET.setText("")
		}
		
		
		binding.saveTV.setOnClickListener {
			if (TextUtils.isEmpty(binding.nameET.text)) {
				ToastUtils.showShort(getString(R.string.please_give_the_file_name))
				return@setOnClickListener
			}
			imagePdfParam.name = binding.nameET.text.toString()
			if (imagePdfParam.encryptStatus && TextUtils.isEmpty(binding.pwET.text)) {
				ToastUtils.showShort(getString(R.string.please_set_the_encryption_password_first))
				return@setOnClickListener
			}
			showLoadingA()
			imagePdfParam.pwd = binding.pwET.text.toString()
			GlobalScope.launch(Dispatchers.IO) {
				var imageToPdfConverter = ImageToPdfConverter()
				imageToPdfConverter.createPdf(this@PdfParamActivity, imagePdfParam)
//				viewModel.createPDF(this@PdfParamActivity,imagePdfParam)
			}
		}
	}
	
	
}