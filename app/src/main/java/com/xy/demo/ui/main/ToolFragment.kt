package com.xy.demo.ui.main

import android.content.ContentResolver
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.provider.MediaStore
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.GridLayoutManager
import com.hjq.permissions.Permission
import com.hjq.permissions.XXPermissions
import com.vincent.filepicker.Constant
import com.vincent.filepicker.activity.NormalFilePickActivity
import com.xy.demo.R
import com.xy.demo.base.MBBaseFragment
import com.xy.demo.base.MBBaseViewModel
import com.xy.demo.databinding.FragmentHomeBinding
import com.xy.demo.databinding.FragmentToolBinding
import com.xy.demo.ui.adapter.TransformAdapter
import com.xy.demo.ui.vm.MainViewModel
import com.xy.xframework.imagePicker.WeChatPresenter
import com.xy.xframework.utils.Globals
import com.xy.xframework.utils.ToastUtils
import com.ypx.imagepicker.ImagePicker
import com.ypx.imagepicker.bean.MimeType
import com.ypx.imagepicker.bean.selectconfig.CropConfig
import com.ypx.imagepicker.data.OnImagePickCompleteListener
import droidninja.filepicker.FilePickerBuilder



//所有工具
open class ToolFragment : MBBaseFragment<FragmentToolBinding, MainViewModel>() {
	
	private val filePaths = ArrayList<Uri>()
	private val transformAdapter = TransformAdapter()
	private val editTransformAdapter = TransformAdapter()
	override fun getLayoutId(): Int {
		return R.layout.fragment_tool
	}
	
	override fun initView() {
		binding.transformRecycler.layoutManager = GridLayoutManager(activity,3)
		binding.editRecycler.layoutManager = GridLayoutManager(activity,3)
		binding.transformRecycler.adapter = transformAdapter
		binding.editRecycler.adapter = editTransformAdapter
		viewModel.getTransList()
		viewModel.getEditList()
		initObserve()
	}
	
	
	
	fun  initObserve(){
		
		
		viewModel.transModels.observe(this){
			transformAdapter.setNewInstance(it)
		}
		
		
		viewModel.editModels.observe(this){
			editTransformAdapter.setNewInstance(it)
		}
		
		transformAdapter.setOnItemClickListener { adapter, view, position ->  }
		editTransformAdapter.setOnItemClickListener { adapter, view, position ->  }
		
		
	}
 
	
	
	
 
	
	
	
	
}