package com.xy.demo.ui.image

import android.app.Activity
import android.content.Intent
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import com.xy.demo.R
import com.xy.demo.base.MBBaseActivity
import com.xy.demo.databinding.ActivityDragSortBinding
import com.xy.demo.model.ImgModel
import com.xy.demo.ui.adapter.GridViewAdapter
import com.xy.demo.ui.adapter.ItemTouchGridAdapter
import com.xy.demo.ui.vm.MainViewModel
import com.xy.demo.utils.ItemTouchHelperCallback



//排序
class DragSortActivity : MBBaseActivity<ActivityDragSortBinding, MainViewModel>() {
	
	lateinit var mAdapter: ItemTouchGridAdapter
	
	lateinit var mItemTouchHelper: ItemTouchHelper
	
	val imageList = arrayListOf<ImgModel>()
	
	
	override fun fitsSystemWindows(): Boolean {
		return false
	}
	
	companion object {
		fun newInstance(activity: Activity, imgList: ArrayList<String>) {
			val intent = Intent()
			intent.putStringArrayListExtra("imgPathList", imgList)
			intent.setClass(activity, DragSortActivity::class.java)
			activity.startActivity(intent)
		}
	}
	
	override fun getLayoutId(): Int {
		return R.layout.activity_drag_sort
	}
	
	
	override fun initView() {
		super.initView()
		titleBarView?.setTitle(getString(R.string.edit_file))
		
		val imgPathList = intent.getStringArrayListExtra("imgPathList")
		for (index in 0 until imgPathList?.size!!) {
			val imgModel = ImgModel()
			imgModel.imgPath = imgPathList[index]
			imageList.add(imgModel)
		}
		
		binding.recyclerview.setItemViewCacheSize(150)
		binding.recyclerview.setLayoutManager(GridLayoutManager(this, 2))
		mAdapter = ItemTouchGridAdapter()
		binding.recyclerview.adapter = mAdapter
		mAdapter.setNewInstance(imageList)
		
		
		
//		mAdapter = GridViewAdapter(this@DragSortActivity,imageList)
//		binding.gridView.adapter = mAdapter
 
		
//		val itemTouchHelper = ItemTouchHelper(MyItemTouchHelperCallBack())
//		itemTouchHelper.attachToRecyclerView(binding.recyclerview)
		
		val itemTouchHelper = ItemTouchHelper(ItemTouchHelperCallback(imageList))
		itemTouchHelper.attachToRecyclerView(binding.recyclerview)
		
		
		
		initListener()
	}
	
 
	
	
	fun initListener() {
		binding.saveTV.setOnClickListener {
			//排序重置后
			val imgPathList = ArrayList<String>()
			for (index in 0 until mAdapter.data.size) {
				imgPathList.add(mAdapter.data[index].imgPath)
			}
			PdfParamActivity.newInstance(this@DragSortActivity, imgPathList)
			finish()
		}
	}
	
	
}