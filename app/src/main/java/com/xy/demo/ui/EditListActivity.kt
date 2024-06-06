package com.xy.demo.ui

import android.app.Activity
import android.content.Intent
import androidx.lifecycle.viewModelScope
import com.jeremyliao.liveeventbus.LiveEventBus
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityEditListBinding
import com.xy.demo.db.MyDataBase
import com.xy.demo.network.Globals
import com.xy.demo.ui.adapter.PdfFileSelectAdapter
import com.xy.demo.ui.dialog.DialogManage
import com.xy.demo.ui.vm.FileViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


//重命名  删除
class EditListActivity : MBBaseActivity<ActivityEditListBinding, FileViewModel>() {
	
	
	val mAdapter = PdfFileSelectAdapter()
	
	
	companion object {
		//fromType  1： 最近文件 数据库中  2；公开可得路径
		fun setNewInstance(activity: Activity, fromType: Int) {
			val intent = Intent(activity, EditListActivity::class.java)
			intent.putExtra("fromType", fromType)
			activity.startActivity(intent)
		}
	}
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_edit_list
	}
	
	
	override fun initView() {
		super.initView()
		mRecyclerView = binding.recyclerview
		initRecycler(1, 1, 0)
		binding.recyclerview.adapter = mAdapter
		initData()
	}
	
	
	public fun initData() {
		showLoading()
		val fromType = intent.getIntExtra("fromType", 1)
		if (fromType == 1) {
			viewModel.viewModelScope.launch(Dispatchers.IO) {
				val pdfList = MyDataBase.instance.PdfFileDao().getAllPdfList()
				withContext(Dispatchers.Main) {
					mAdapter.setNewInstance(pdfList)
					dismissLoading()
				}
			}
		} else {
			viewModel.getDocumentFile()
		}
	}
	
	
	
	override fun initViewObservable() {
		super.initViewObservable()
	 
		viewModel.fileList.observe(this) {
			mAdapter.setNewInstance(it)
			dismissLoading()
		}
		
		
		LiveEventBus.get<Any>(Constants.EVENT_FILE_SELECT).observe(this) {
			val selectNumber = mAdapter.selectNumber
			if (selectNumber == 1) {
				binding.renameTV.isSelected = true
				binding.shareTV.isSelected = true
				binding.renameTV.isClickable = true
				binding.shareTV.isClickable = true
			} else {
				binding.renameTV.isSelected = false
				binding.shareTV.isSelected = false
				binding.renameTV.isClickable = false
				binding.shareTV.isClickable = false
			}
			
		}
		
		
		
		
		binding.renameTV.setOnClickListener {
			for (index in mAdapter.data.indices) {
				if (mAdapter.data[index].selectStatus) {
					DialogManage.showRenameDialog(this@EditListActivity, mAdapter.data[index])
				}
			}
		}
		
		binding.shareTV.setOnClickListener {
			Globals.log("xxxxxxxxshareTV")
		}
		
		
		binding.finishTV.setOnClickListener {
		
		}
		
		
	}
	
	
}