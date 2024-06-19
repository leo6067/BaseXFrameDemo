package com.xy.demo.ui.common

import android.app.Activity
import android.content.Intent
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.inputmethod.EditorInfo
import android.widget.TextView.OnEditorActionListener
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.encryption.InvalidPasswordException
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivitySearchListBinding
import com.xy.demo.db.MyDataBase
import com.xy.demo.db.PdfFileModel
import com.xy.demo.ui.adapter.PdfFileAdapter
import com.xy.demo.ui.dialog.DialogManage
import com.xy.demo.ui.vm.FileViewModel
import com.xy.xframework.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException


class SearchListActivity : MBBaseActivity<ActivitySearchListBinding, FileViewModel>() {
	
	var mAdapter = PdfFileAdapter()
	
	val emptyFileList = ArrayList<PdfFileModel>()
	
	var fromType: Int = 0
	
	companion object {
		//fromType  //1：主页 2 文件页   4 压缩 5 提取文字 6 提取图片  7 加密pdf  8 解锁pdf
		fun setNewInstance(activity: Activity, fromType: Int) {
			val intent = Intent(activity, SearchListActivity::class.java)
			intent.putExtra("fromType", fromType)
			activity.startActivity(intent)
		}
	}
	
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun showTitleBar(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_search_list
	}
	
	
	override fun initView() {
		super.initView()
		
		fromType = intent.getIntExtra("fromType", 1)
		
		binding.recyclerview.layoutManager = LinearLayoutManager(this)
		binding.recyclerview.adapter = mAdapter
		mAdapter.setEmptyView(R.layout.include_home_empty)
		
		initObserver()
		initListener()
	}
	
	
	fun initObserver() {
		viewModel.fileList.observe(this) {
			mAdapter.setNewInstance(it)
			dismissLoading()
		}
		
		LiveEventBus.get<String>(Constants.EVENT_REFRESH_FILE).observe(this) {
			if (!TextUtils.isEmpty(binding.searchEdit.text.toString())) {
				viewModel.getSearchDocumentFile(binding.searchEdit.text.toString())
			}
		}
	}
	
	
	fun initListener() {
		
		binding.ivBack.setOnClickListener { finish() }
		binding.closeIV.setOnClickListener {
			binding.searchEdit.setText("")
		}
		
		
		binding.searchEdit.addTextChangedListener(object : TextWatcher {
			override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
			}
			
			override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
			}
			
			override fun afterTextChanged(p0: Editable?) {
				if (!TextUtils.isEmpty(p0)) {
					binding.closeIV.visibility = View.VISIBLE
				} else {
					binding.closeIV.visibility = View.GONE
				}
			}
		})
		
		
		binding.searchEdit.setOnEditorActionListener(OnEditorActionListener { v, actionId, event ->
			if (actionId == EditorInfo.IME_ACTION_SEARCH) {
				mAdapter.setNewInstance(emptyFileList)
				if (TextUtils.isEmpty(binding.searchEdit.text.toString())) {
					false
				} else {
					viewModel.getSearchDocumentFile(binding.searchEdit.text.toString())
					true
				}
			} else {
				false
			}
		})
		
		
		mAdapter.setOnItemClickListener { adapter, view, position ->
			mAdapter.setOnItemClickListener { adapter, view, position ->
				val pdfFileModel = mAdapter.data[position]
				var isEncrypted = false
				runBlocking {
					isEncrypted = withContext(Dispatchers.IO) {
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
					
					when (fromType) {  //doType   //1：主页 2 文件页   4 压缩 5 提取文字 6 提取图片  7 加密pdf  8 解锁pdf
						Constants.PDF_FROM_HOME -> {
							if (isEncrypted) {//加密 ---show  解密
								DialogManage.showDecodeDialog(this@SearchListActivity, pdfFileModel.path, pdfFileModel.name, 1)
							} else {
								showLoading()
								PDFActivity.setNewInstance(this@SearchListActivity, pdfFileModel.path, pdfFileModel.name, 2)
							}
						}
						
						//压缩功能
						Constants.PDF_FROM_COMPRESS -> {
							if (isEncrypted) {//加密
								ToastUtils.showShort(getString(R.string.the_file_is_encrypted_please_use_the_unlock_function_first))
							} else {
								DialogManage.showCompressDialog(this@SearchListActivity, pdfFileModel.path)
							}
						}
						
						
						//提取文字
						Constants.PDF_FROM_WORD -> {
							if (isEncrypted) {
								ToastUtils.showShort(getString(R.string.the_file_is_encrypted_please_use_the_unlock_function_first))
							} else {
								DialogManage.showExtractDialog(this@SearchListActivity, pdfFileModel.path, 2)
							}
						}
						//提取图片
						Constants.PDF_FROM_BITMAP ->
							if (isEncrypted) {
								ToastUtils.showShort(getString(R.string.the_file_is_encrypted_please_use_the_unlock_function_first))
							} else {
								DialogManage.showExtractDialog(this@SearchListActivity, pdfFileModel.path, 1)
							}
						//加密
						Constants.PDF_FROM_LOCK ->
							if (isEncrypted) {
								ToastUtils.showShort(getString(R.string.it_s_an_encrypted_file))
							} else {
								DialogManage.showEncryptDialog(this@SearchListActivity, pdfFileModel)
							}
						//解密
						Constants.PDF_FROM_UNLOCK -> {
							if (isEncrypted) {//确实存在加密----展示解密弹窗
								DialogManage.showDecodeDialog(this@SearchListActivity, pdfFileModel.path, pdfFileModel.name, 2)
							} else {
								showLoading()
								PDFActivity.setNewInstance(this@SearchListActivity, pdfFileModel.path, pdfFileModel.name, 2)
							}
						}
					}
				}
			}
		}
		
	}
	
}