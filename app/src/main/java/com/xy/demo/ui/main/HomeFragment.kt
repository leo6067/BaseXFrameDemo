package com.xy.demo.ui.main

import android.provider.Settings.Global
import androidx.lifecycle.viewModelScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.jeremyliao.liveeventbus.LiveEventBus
import com.tom_roush.pdfbox.pdmodel.PDDocument
import com.tom_roush.pdfbox.pdmodel.encryption.InvalidPasswordException
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseFragment
import com.xy.demo.databinding.FragmentHomeBinding
import com.xy.demo.db.MyDataBase
import com.xy.demo.db.PdfFileModel
import com.xy.demo.ui.common.EditListActivity
import com.xy.demo.ui.common.PDFActivity
import com.xy.demo.ui.common.PdfListActivity
import com.xy.demo.ui.adapter.PdfFileAdapter
import com.xy.demo.ui.common.SearchListActivity
import com.xy.demo.ui.dialog.DialogManage
import com.xy.demo.ui.image.DealImageActivity
import com.xy.demo.ui.vm.FileViewModel
import com.xy.xframework.imagePicker.WeChatPresenter
import com.xy.xframework.utils.Globals
import com.xy.xframework.utils.ToastUtils
import com.ypx.imagepicker.ImagePicker
import com.ypx.imagepicker.bean.MimeType
import com.ypx.imagepicker.data.OnImagePickCompleteListener
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import java.io.File
import java.io.IOException


open class HomeFragment : MBBaseFragment<FragmentHomeBinding, FileViewModel>() {
	
	val mAdapter = PdfFileAdapter()
	
	override fun getLayoutId(): Int {
		return R.layout.fragment_home
	}
	
	override fun initView() {
		binding.recyclerview.layoutManager = LinearLayoutManager(activity)
		binding.recyclerview.adapter = mAdapter
		mAdapter.setEmptyView(R.layout.include_home_empty)
		initListener()
	}
	
	override fun onResume() {
		super.onResume()
		
		refreshFile()
	
	}
	
	
	
	
	public fun refreshFile(){
		GlobalScope.launch {
			val pdfList = MyDataBase.instance.PdfFileDao().getAllPdfList()
			pdfList.reverse()
			withContext(Dispatchers.Main) {
				mAdapter.setNewInstance(pdfList)
			}
		}
	}
	
 
	
	fun initListener() {
		//打开pdf
		mAdapter.setOnItemClickListener { adapter, view, position ->
			val pdfFileModel = mAdapter.data[position] as PdfFileModel
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
				
				try {
					// 尝试加载 PDF 文件，不提供密码
					if (isEncrypted) {
						activity?.let { DialogManage.showDecodeDialog(it, pdfFileModel.path, pdfFileModel.name, 1) }
					} else {
						showLoading()
						activity?.let { PDFActivity.setNewInstance(it, pdfFileModel.path, pdfFileModel.name, 2) }
					}
				} catch (e: IOException) {
				
				}
				
			}
		}
		
		binding.imageTV.setOnClickListener {
			imageToPdf()
		}
		
		binding.compressTV.setOnClickListener {
			activity?.let { it1 -> PdfListActivity.newInstance(it1, Constants.PDF_FROM_COMPRESS) }
		}
		
		binding.extractTV.setOnClickListener {
			activity?.let { it1 -> PdfListActivity.newInstance(it1, Constants.PDF_FROM_WORD) }
		}
		
		binding.lockTV.setOnClickListener {
			activity?.let { it1 -> PdfListActivity.newInstance(it1, Constants.PDF_FROM_LOCK) }
		}
		
		
		binding.editIV.setOnClickListener {
			activity?.let { it1 -> EditListActivity.setNewInstance(it1, 1) }
		}
		
		
		binding.searchIV.setOnClickListener {
			activity?.let { it1 -> SearchListActivity.setNewInstance(it1, Constants.PDF_FROM_HOME) }
		}
		
	}
	
	
	fun imageToPdf() {
		val imgList = ArrayList<String>()
		ImagePicker.withMulti(WeChatPresenter()) //设置presenter
			.setMaxCount(50) //设置选择数量
			.setColumnCount(4) //设置列数
			.mimeTypes(MimeType.JPEG, MimeType.PNG, MimeType.WEBP)
			//设置需要过滤掉加载的文件类型
			.setPreview(true)
			.pick(activity, OnImagePickCompleteListener {
//						val imageItem = it[0] as ImageItem
				for (index in 0 until it.size) {
					imgList.add(it[index].path)
				}
				activity?.let { it1 -> DealImageActivity.newInstance(it1, imgList) }
			})
		
	}
	
	
}