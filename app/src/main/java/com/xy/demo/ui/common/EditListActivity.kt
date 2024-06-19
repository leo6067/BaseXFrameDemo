package com.xy.demo.ui.common

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.viewModelScope
import com.jeremyliao.liveeventbus.LiveEventBus
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityEditListBinding
import com.xy.demo.db.MyDataBase
import com.xy.demo.db.PdfFileModel
import com.xy.demo.ui.adapter.PdfFileSelectAdapter
import com.xy.demo.ui.dialog.DialogManage
import com.xy.demo.ui.vm.FileViewModel
import com.xy.demo.utils.CompressUtil
import com.xy.xframework.dialog.BaseDialog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.File


//重命名  删除
class EditListActivity : MBBaseActivity<ActivityEditListBinding, FileViewModel>() {
	
	val mAdapter = PdfFileSelectAdapter()
	
	var emptyList = ArrayList<PdfFileModel>()
	
	var selectNumber = 0
	
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
	
	
	@SuppressLint("ResourceAsColor")
	override fun initView() {
		super.initView()
		
		titleBarView?.getRightTextView()?.visibility = View.VISIBLE
		titleBarView?.getRightTextView()?.text = getString(R.string.select_all)
		titleBarView?.getRightTextView()?.setTextColor(R.color.color_2578FF)
		
		mRecyclerView = binding.recyclerview
		initRecycler(1, 1, 0)
		binding.recyclerview.adapter = mAdapter
		initData()
		
		titleBarView?.setTitle(getString(R.string.selected) + " " + selectNumber + " " + getString(R.string.item))
	}
	
	
	public fun initData() {
		mAdapter.setNewInstance(emptyList)
		upUI()
		showLoading()
		val fromType = intent.getIntExtra("fromType", 1)
		if (fromType == 1) {
			viewModel.viewModelScope.launch(Dispatchers.IO) {
				val pdfList = MyDataBase.instance.PdfFileDao().getAllPdfList()
				pdfList.reverse()
				withContext(Dispatchers.Main) {
					mAdapter.setNewInstance(pdfList)
					dismissLoading()
				}
			}
		} else {
			viewModel.getDocumentFile()
		}
		
		
		
		LiveEventBus.get<String>(Constants.EVENT_REFRESH_FILE_EDIT).observe(this) {
			if (fromType == 1) {
				viewModel.viewModelScope.launch(Dispatchers.IO) {
					val pdfList = MyDataBase.instance.PdfFileDao().getAllPdfList()
					pdfList.reverse()
					withContext(Dispatchers.Main) {
						mAdapter.setNewInstance(pdfList)
						dismissLoading()
					}
				}
			} else {
				viewModel.getDocumentFile()
			}
		}
		
	}
	
	
	override fun initViewObservable() {
		super.initViewObservable()
		
		viewModel.fileList.observe(this) {
			it.reverse()
			mAdapter.setNewInstance(it)
			dismissLoading()
		}
		
		mAdapter.setOnItemClickListener { adapter, view, position ->
			selectNumber = 0
			mAdapter.data[position].selectStatus = !mAdapter.data[position].selectStatus
			for (index in mAdapter.data.indices) {
				if (mAdapter.data[index].selectStatus) {
					selectNumber++
				}
			}
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
			
			if (selectNumber > 0) {
				binding.deleteTV.isClickable = true
				binding.deleteTV.isSelected = true
			} else {
				binding.deleteTV.isClickable = false
				binding.deleteTV.isSelected = false
			}
			
			if (selectNumber == mAdapter.data.size) {
				titleBarView?.getRightTextView()?.text = getString(R.string.cancel)
			} else {
				titleBarView?.getRightTextView()?.text = getString(R.string.select_all)
			}
			
			titleBarView?.setTitle(getString(R.string.selected) + " " + selectNumber + " " + getString(R.string.item))
			mAdapter.notifyDataSetChanged()
		}
		
		
		
		
		binding.renameTV.setOnClickListener {
			for (index in mAdapter.data.indices) {
				if (mAdapter.data[index].selectStatus) {
					DialogManage.showRenameDialog(this@EditListActivity, mAdapter.data[index])
				}
			}
		}
		
		
		
		binding.deleteTV.setOnClickListener {
			showDeleteDialog()
		}
		
		
		titleBarView?.setRightClickListener {
			if (selectNumber == mAdapter.data.size) {
				// "取消"
				for (index in mAdapter.data.indices) {
					mAdapter.data[index].selectStatus = false
				}
				selectNumber = 0
				titleBarView?.getRightTextView()?.text = getString(R.string.select_all)
				titleBarView?.setTitle(getString(R.string.selected) + " " + 0 + " " + getString(R.string.item))
				upUI()
			} else {
				for (index in mAdapter.data.indices) {
					mAdapter.data[index].selectStatus = true
				}
				selectNumber = mAdapter.data.size
				titleBarView?.getRightTextView()?.text = getString(R.string.cancel)
				titleBarView?.setTitle(getString(R.string.selected) + " " + mAdapter.data.size + " " + getString(R.string.item))
				binding.deleteTV.isSelected = true
				binding.deleteTV.isClickable = true
			}
			mAdapter.notifyDataSetChanged()
		}
		
		binding.shareTV.setOnClickListener {
			for (index in mAdapter.data.indices) {
				if (mAdapter.data[index].selectStatus) {
					CompressUtil.shareFile(this@EditListActivity, mAdapter.data[index].path)
				}
			}
		}
	}
	
	
	fun upUI() {
		binding.renameTV.isSelected = false
		binding.shareTV.isSelected = false
		binding.renameTV.isClickable = false
		binding.shareTV.isClickable = false
		binding.deleteTV.isSelected = false
		binding.deleteTV.isClickable = false
		titleBarView?.getRightTextView()?.text = getString(R.string.select_all)
		titleBarView?.setTitle(getString(R.string.selected) + " " + 0 + " " + getString(R.string.item))
	}
	
	
	// 重命名 窗口
	fun showDeleteDialog() {
		val inflate = LayoutInflater.from(this).inflate(R.layout.dialog_warn, null)
		BaseDialog.setSeat(Gravity.CENTER)
		BaseDialog.showBaseDialog(this, inflate)
		val closeIV = inflate.findViewById<ImageView>(R.id.closeIV)
		
		val negativeTV = inflate.findViewById<TextView>(R.id.negativeTV)
		val positiveTV = inflate.findViewById<TextView>(R.id.positiveTV)
		positiveTV.setOnClickListener {
			for (index in mAdapter.data.indices) {
				if (mAdapter.data[index].selectStatus) {
					val tempFile = File(mAdapter.data[index].path)
					tempFile.delete()
					GlobalScope.launch(Dispatchers.IO) {
						MyDataBase.instance.PdfFileDao().deleteByPath(mAdapter.data[index].path)
						delay(1500)
						withContext(Dispatchers.Main) {
							initData()
							LiveEventBus.get<String>(Constants.EVENT_REFRESH_FILE_EDIT).post(Constants.EVENT_REFRESH_FILE_EDIT)
						}
					}
				}
			}
			BaseDialog.dismissBaseDialog()
		}
		
		negativeTV.setOnClickListener {
			BaseDialog.dismissBaseDialog()
		}
		closeIV.setOnClickListener {
			BaseDialog.dismissBaseDialog()
		}
	}
	
	
}