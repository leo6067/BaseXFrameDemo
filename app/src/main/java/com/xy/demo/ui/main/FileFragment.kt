package com.xy.demo.ui.main

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.contract.ActivityResultContracts.GetContent
import androidx.activity.result.contract.ActivityResultContracts.OpenDocument
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.encryption.InvalidPasswordException
import com.xy.demo.MainActivity
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseFragment
import com.xy.demo.base.MyApplication
import com.xy.demo.databinding.FragmentFileBinding
import com.xy.demo.db.MyDataBase
import com.xy.demo.db.PdfFileModel
import com.xy.demo.ui.adapter.PdfFileAdapter
import com.xy.demo.ui.common.EditListActivity
import com.xy.demo.ui.common.PDFActivity
import com.xy.demo.ui.common.SearchListActivity
import com.xy.demo.ui.dialog.DialogManage
import com.xy.demo.ui.vm.FileViewModel
import com.xy.xframework.utils.Globals
import com.xy.xframework.utils.ToastUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.io.IOException


open class FileFragment : MBBaseFragment<FragmentFileBinding, FileViewModel>() {
	
	
	private val allFileList = ArrayList<PdfFileModel>()
	private val collectFileList = ArrayList<Uri>()
	var mAdapter = PdfFileAdapter()
	
	
	override fun getLayoutId(): Int {
		return R.layout.fragment_file
	}
	
	override fun initView() {
		getAuthority()
		binding.recyclerview.layoutManager = LinearLayoutManager(activity)
		binding.recyclerview.adapter = mAdapter
		binding.editIV.setOnClickListener {
			activity?.let { it1 -> EditListActivity.setNewInstance(it1, 2) }
		}
		binding.searchEdit.setOnClickListener {
			activity?.let { it1 -> SearchListActivity.setNewInstance(it1, Constants.PDF_FROM_FILE) }
		}
	}
	
	
	fun getAuthority() {
		if (activity != null) {
			val mainActivity = activity as MainActivity
			if (mainActivity.getSelectIndex() != 1) {
				return
			}
		}
		
//		try {
//			if (binding.recyclerview!=null && mAdapter!=null){
//				binding.recyclerview.layoutManager = LinearLayoutManager(activity)
//				binding.recyclerview.adapter = mAdapter
//			}
//		}catch (e:Exception){
//
//		}
	
		
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) { // Android 11 (API 30) 及以上版本
			XXPermissions.with(this)
				.permission(Permission.MANAGE_EXTERNAL_STORAGE)
				.request(object : OnPermissionCallback {
					override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
						if (!allGranted) {
							Globals.log("xxxxxxxxxxx请手动授予但部分权限未正常授予")
//							ToastUtils.showShort("获取部分权限成功，但部分权限未正常授予")
							return
						}
						getFileList()
					}
					
					override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
						/*		if (doNotAskAgain) {
									ToastUtils.showShort("被永久拒绝授权，请手动授予录音和日历权限")
									// 如果是被永久拒绝就跳转到应用权限系统设置页面
									XXPermissions.startPermissionActivity(this@MainActivity, permissions)
								} else {
								}*/
						
						if (activity != null) {
							val mainActivity = activity as MainActivity
							mainActivity.setTab(0)
						}
//						System.exit(0)
					}
				})
		} else { // Android 10 (API 29) 及以下版本
			XXPermissions.with(this)
				.permission(Permission.READ_EXTERNAL_STORAGE)
				.request(object : OnPermissionCallback {
					override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
						if (!allGranted) {
//							ToastUtils.showShort("获取部分权限成功，但部分权限未正常授予")
							return
						}
						getFileList()
					}
					
					override fun onDenied(permissions: MutableList<String>, doNotAskAgain: Boolean) {
						/*		if (doNotAskAgain) {
									ToastUtils.showShort("被永久拒绝授权，请手动授予录音和日历权限")
									// 如果是被永久拒绝就跳转到应用权限系统设置页面
									XXPermissions.startPermissionActivity(this@MainActivity, permissions)
								} else {
								}*/
						if (activity != null) {
							val mainActivity = activity as MainActivity
							mainActivity.setTab(0)
						}
					}
				})
		}
	}
	
	
	override fun initViewObservable() {
		super.initViewObservable()
		
		//手动选择本地内部文件
		val getContent = registerForActivityResult(ActivityResultContracts.GetContent()) { pdfFileUri: Uri? ->
			if (pdfFileUri == null) {
				// 用户没有选择文件
//				Toast.makeText(this, "No file selected", Toast.LENGTH_SHORT).show()
			} else {
				// 用户选择了文件
				val documentFile = DocumentFile.fromSingleUri(MyApplication.instance, pdfFileUri)
				// 使用 documentFile 访问文件内容或属性
				// 对所选 PDF 文件的 URI 进行后续操作
				val contentResolver = activity?.contentResolver
				try {
					documentFile?.uri?.let {
						contentResolver?.openInputStream(it).use { inputStream ->
							FileOutputStream(File(activity?.filesDir, documentFile?.name)).use { fileOutputStream ->
								
								runBlocking {
									val isEncrypted = withContext(Dispatchers.IO) {
										try {
											// 尝试加载 PDF 文件，不提供密码
											val document = PDDocument.load(File(activity?.filesDir?.path))
											false
										} catch (e: InvalidPasswordException) {
											true
										} catch (e: IOException) {
											false
										}
									}
									
									if (isEncrypted) {//加密
									
									} else {
										showLoading()
										activity?.let {
											PDFActivity.setNewInstance(
												it,
												documentFile?.uri.toString(),
												documentFile?.name.toString(),
												1
											)
										}
									}
									
								}
							}
						}
					}
				} catch (e: IOException) {
					e.printStackTrace()
					// 处理异常
				}
			}
		}
		
		// 头部选择本地文件
		val ramView = LayoutInflater.from(activity).inflate(R.layout.include_ram, null)
		val ramLin = ramView.findViewById<LinearLayout>(R.id.bgLin)
		ramLin.setOnClickListener { getContent.launch("application/pdf") }
		mAdapter.setHeaderView(ramView)
		
		
		mAdapter.setOnItemClickListener { adapter, view, position ->
			val pdfFileModel = adapter.data[position] as PdfFileModel
			val file = File(pdfFileModel.path)
			if (!file.exists()) {
				ToastUtils.showShort(getString(R.string.the_file_does_not_exist))
				return@setOnItemClickListener
			}
			
			runBlocking {
				val isEncrypted = withContext(Dispatchers.IO) {
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
				
				if (isEncrypted) {//加密
					activity?.let { DialogManage.showDecodeDialog(it, pdfFileModel.path, pdfFileModel.name, 1) }
				} else {
					showLoading()
					activity?.let { PDFActivity.setNewInstance(it, pdfFileModel.path, pdfFileModel.name, 2) }
				}
			}
		}
		
		viewModel.fileList.observe(this) {
	 
			
			GlobalScope.launch(Dispatchers.Main) {
			 
				it.reverse()
				mAdapter.setNewInstance(it)
				mAdapter.notifyDataSetChanged()
				dismissLoading()
			}
		}
		
		
		
		LiveEventBus.get<String>(Constants.EVENT_REFRESH_FILE).observe(this) {
			Globals.log("xxxxxxEVENT_REFRESH_FILE---FileFragment---")
			viewModel.viewModelScope.launch {
				delay(1000)
				viewModel.getDocumentFile()
			}
		}
		
		
		
		LiveEventBus.get<String>(Constants.EVENT_REFRESH_FILE_EDIT).observe(this) {
			viewModel.viewModelScope.launch {
				Globals.log("xxxxxxxxxxxxEVENT_REFRESH_FILE_EDIT--FileFragment---")
				delay(1000)
				viewModel.getDocumentFile()
			}
		}
		LiveEventBus.get<String>(Constants.EVENT_REFRESH_FILE_FRAGMENT).observe(this) {
			Globals.log("xxxxxxxxxxxpdf   EVENT_REFRESH_FILE_FRAGMENT--FileFragment------")
			viewModel.viewModelScope.launch {
				delay(1000)
				viewModel.getDocumentFile()
			}
		}
	}
	
	
	fun getFileList() {
		showLoading()
		viewModel.viewModelScope.launch {
			viewModel.getDocumentFile()
		}
	}
	
	
}