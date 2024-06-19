package com.xy.demo.ui.main

import android.net.Uri
import androidx.recyclerview.widget.GridLayoutManager
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseFragment
import com.xy.demo.databinding.FragmentToolBinding
import com.xy.demo.ui.common.PdfListActivity
import com.xy.demo.ui.adapter.TransformAdapter
import com.xy.demo.ui.image.DealImageActivity
import com.xy.demo.ui.vm.MainViewModel
import com.xy.xframework.imagePicker.WeChatPresenter
import com.ypx.imagepicker.ImagePicker
import com.ypx.imagepicker.bean.MimeType
import com.ypx.imagepicker.data.OnImagePickCompleteListener


//所有工具
open class ToolFragment : MBBaseFragment<FragmentToolBinding, MainViewModel>() {
	
	private val filePaths = ArrayList<Uri>()
	private val transformAdapter = TransformAdapter()
	private val editTransformAdapter = TransformAdapter()
	override fun getLayoutId(): Int {
		return R.layout.fragment_tool
	}
	
	override fun initView() {
		binding.transformRecycler.layoutManager = GridLayoutManager(activity, 3)
		binding.editRecycler.layoutManager = GridLayoutManager(activity, 3)
		binding.transformRecycler.adapter = transformAdapter
		binding.editRecycler.adapter = editTransformAdapter
		activity?.let {
			viewModel.getTransList(it)
			viewModel.getEditList(it)
		}
		initObserve()
	}
	
	
	fun initObserve() {
		viewModel.transModels.observe(this) {
			transformAdapter.setNewInstance(it)
		}
		
		
		viewModel.editModels.observe(this) {
			editTransformAdapter.setNewInstance(it)
		}
		
		transformAdapter.setOnItemClickListener { adapter, view, position ->
			when (position) {
				0 -> imageToPdf()
				1 -> activity?.let { it1 -> PdfListActivity.newInstance(it1, Constants.PDF_FROM_BITMAP) }
			}
			
		}
		editTransformAdapter.setOnItemClickListener { adapter, view, position ->
			when (position) {
				0 -> activity?.let { it1 -> PdfListActivity.newInstance(it1, Constants.PDF_FROM_COMPRESS) }
				1 -> activity?.let { it1 -> PdfListActivity.newInstance(it1, Constants.PDF_FROM_WORD) }
				2 -> activity?.let { it1 -> PdfListActivity.newInstance(it1, Constants.PDF_FROM_LOCK) }
				3 -> activity?.let { it1 -> PdfListActivity.newInstance(it1, Constants.PDF_FROM_UNLOCK) }
			}
		}
	}
	
	
	fun imageToPdf() {
		val imgList = ArrayList<String>()
		ImagePicker.withMulti(WeChatPresenter()) //设置presenter
			.setMaxCount(20) //设置选择数量
			.setColumnCount(3) //设置列数
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