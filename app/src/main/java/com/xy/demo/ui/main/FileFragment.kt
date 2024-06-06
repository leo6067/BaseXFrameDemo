package com.xy.demo.ui.main

import android.net.Uri
import android.view.LayoutInflater
import android.widget.LinearLayout
import androidx.activity.result.contract.ActivityResultContracts.OpenDocument
import androidx.documentfile.provider.DocumentFile
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.xy.demo.R
import com.xy.demo.base.MBBaseFragment
import com.xy.demo.base.MyApplication
import com.xy.demo.databinding.FragmentFileBinding
import com.xy.demo.db.PdfFileModel
import com.xy.demo.ui.PDFActivity
import com.xy.demo.ui.adapter.PdfFileAdapter
import com.xy.demo.ui.vm.FileViewModel
import com.xy.xframework.utils.Globals
import kotlinx.coroutines.launch


open class FileFragment : MBBaseFragment<FragmentFileBinding, FileViewModel>() {
	
	
	private val allFileList = ArrayList<PdfFileModel>()
	private val collectFileList = ArrayList<Uri>()
	var mAdapter = PdfFileAdapter()
	
	
	override fun getLayoutId(): Int {
		return R.layout.fragment_file
	}
	
	override fun initView() {
		
		binding.recyclerview.layoutManager = LinearLayoutManager(activity)
		binding.recyclerview.adapter = mAdapter
		
		XXPermissions.with(this)
			.permission(Permission.MANAGE_EXTERNAL_STORAGE)
			.request { permissions, all ->
				getFileList()
			}
	}
	
	
	override fun initViewObservable() {
		super.initViewObservable()
		//内部存储文件
		val mGetPdfLauncher = registerForActivityResult(OpenDocument(), this::onPdfFilePicked)
		val ramView = LayoutInflater.from(activity).inflate(R.layout.include_ram, null)
		val ramLin = ramView.findViewById<LinearLayout>(R.id.bgLin)
		ramLin.setOnClickListener { mGetPdfLauncher.launch(arrayOf("application/pdf")) }
		
		mAdapter.setHeaderView(ramView)
		
		mAdapter.setOnItemClickListener { adapter, view, position ->
			val pdfFileModel = adapter.data[position] as PdfFileModel
			activity?.let { PDFActivity.setNewInstance(it, pdfFileModel.path, pdfFileModel.name, 2) }
		}
		
		
		viewModel.fileList.observe(this) {
			mAdapter.setNewInstance(it)
			dismissLoading()
		}
		
		
	}
	
 
 
 
	
	//本地文件选择回调
	fun onPdfFilePicked(pdfFileUri: Uri) {
		val documentFile = DocumentFile.fromSingleUri(MyApplication.instance, pdfFileUri)
		// 使用 documentFile 访问文件内容或属性
		// 对所选 PDF 文件的 URI 进行后续操作
		Globals.log("xxxxxxxxxxxpdfFileUri" + documentFile?.name)
		Globals.log("xxxxxxxxxxxpdfFileUri--" + documentFile?.uri)
		
		activity?.let { PDFActivity.setNewInstance(it, documentFile?.uri.toString(), documentFile?.name.toString(), 1) }
	}
 
	
	fun getFileList() {
		showLoading()
		viewModel.viewModelScope.launch {
			
			viewModel.getDocumentFile()
			
		}
	}
	
	
	
	
	
}