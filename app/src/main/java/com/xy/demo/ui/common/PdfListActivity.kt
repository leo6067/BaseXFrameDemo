package com.xy.demo.ui.common

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.os.Build
import android.view.View
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.encryption.InvalidPasswordException
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityPdfListBinding
import com.xy.demo.db.PdfFileModel
import com.xy.demo.ui.adapter.PdfFileAdapter
import com.xy.demo.ui.dialog.DialogManage
import com.xy.demo.ui.vm.FileViewModel
import com.xy.xframework.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException


// 选择pdf 文件   含有搜索入口， 本身无搜索功能
class PdfListActivity : MBBaseActivity<ActivityPdfListBinding, FileViewModel>() {
	
	
	private val allFileList = ArrayList<PdfFileModel>()
	
	var mAdapter = PdfFileAdapter()
	
	
	var fromType: Int = 0
	
	
	companion object {
		//1：主页 2 文件页   4 压缩 5 提取文字 6 提取图片  7 加密pdf  8 解锁pdf
		fun newInstance(activity: Activity, doType: Int) {
			val intent = Intent()
			intent.putExtra("fromType", doType)
			intent.setClass(activity, PdfListActivity::class.java)
			activity.startActivity(intent)
		}
	}
	
	
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
		titleBarView?.getRightTextView()?.visibility = View.VISIBLE
		
		binding.recyclerview.layoutManager = LinearLayoutManager(this)
		binding.recyclerview.adapter = mAdapter
		
		fromType = intent.getIntExtra("fromType", 0)
		
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { // Android 11 (API 30) 及以上版本
			XXPermissions.with(this)
				.permission(Permission.MANAGE_EXTERNAL_STORAGE)
				.request { permissions, all ->
					if (all) {
						getFileList()
					}
				}
		} else { // Android 10 (API 29) 及以下版本
			XXPermissions.with(this)
				.permission(Permission.READ_EXTERNAL_STORAGE)
				.request { permissions, all ->
					if (all) {
						getFileList()
					}
				}
		}
		
		initObserver()
		initListener()
	}
	
	
	
	
	fun getFileList() {
		showLoading()
		viewModel.viewModelScope.launch {
			viewModel.getDocumentFile()
		}
	}
	
	
	fun initObserver() {
		LiveEventBus.get<Any>(Constants.EVENT_REFRESH_FILE).observe(this) {
			viewModel.getDocumentFile()
		}
		
		
		
		viewModel.fileList.observe(this) {
			it.reverse()
			mAdapter.setNewInstance(it)
			dismissLoading()
		}
		
		
		
		titleBarView?.setRightClickListener {
			SearchListActivity.setNewInstance(this@PdfListActivity, fromType)
		}
		
	}
	
	
	fun initListener() {
		mAdapter.setOnItemClickListener { adapter, view, position ->
			val pdfFileModel = mAdapter.data[position]
		 
			showLoading()
			GlobalScope.launch {
				var	isEncrypted = withContext(Dispatchers.IO) {
					try {
						// 尝试加载 PDF 文件，不提供密码
						val document = PDDocument.load(File(pdfFileModel.path))
						false
					} catch (e: InvalidPasswordException) {
						true
					} catch (e: IOException) {
						false
					}
				}
				
				
				withContext(Dispatchers.Main) {
					dismissLoading()
					when (fromType) {  //doType   //1：主页 2 文件页   4 压缩 5 提取文字 6 提取图片  7 加密pdf  8 解锁pdf
						Constants.PDF_FROM_HOME -> {
							if (isEncrypted) {//加密
								DialogManage.showDecodeDialog(this@PdfListActivity, pdfFileModel.path, pdfFileModel.name, 1)
							} else {
								showLoading()
								PDFActivity.setNewInstance(this@PdfListActivity, pdfFileModel.path, pdfFileModel.name, 2)
							}
						}
						
						//压缩功能
						Constants.PDF_FROM_COMPRESS -> {
							if (isEncrypted) {//加密
								ToastUtils.showShort(getString(R.string.the_file_is_encrypted_please_use_the_unlock_function_first))
							} else {
								DialogManage.showCompressDialog(this@PdfListActivity, pdfFileModel.path)
							}
						}
						
						
						//提取文字
						Constants.PDF_FROM_WORD -> {
							if (isEncrypted) {
								ToastUtils.showShort(getString(R.string.the_file_is_encrypted_please_use_the_unlock_function_first))
							} else {
								DialogManage.showExtractDialog(this@PdfListActivity, pdfFileModel.path, 2)
							}
						}
						//提取图片
						Constants.PDF_FROM_BITMAP ->
							if (isEncrypted) {
								ToastUtils.showShort(getString(R.string.the_file_is_encrypted_please_use_the_unlock_function_first))
							} else {
								DialogManage.showExtractDialog(this@PdfListActivity, pdfFileModel.path, 1)
							}
						//加密
						Constants.PDF_FROM_LOCK ->
							if (isEncrypted) {
								ToastUtils.showShort(getString(R.string.it_s_an_encrypted_file))
							} else {
								DialogManage.showEncryptDialog(this@PdfListActivity, pdfFileModel)
							}
						//解密
						Constants.PDF_FROM_UNLOCK -> {
							if (isEncrypted) {//确实存在加密----展示解密弹窗
								DialogManage.showDecodeDialog(this@PdfListActivity, pdfFileModel.path, pdfFileModel.name, 2)
							} else {
								showLoading()
								PDFActivity.setNewInstance(this@PdfListActivity, pdfFileModel.path, pdfFileModel.name, 2)
							}
						}
					}
				}
			}
		}
		
	}
	
	
}