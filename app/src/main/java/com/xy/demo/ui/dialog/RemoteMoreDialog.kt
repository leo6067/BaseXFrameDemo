package com.xy.demo.ui.dialog

import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.xy.demo.R
import com.xy.demo.base.MBBaseDialogFragment
import com.xy.demo.databinding.DialogRemoteMoreBinding
import com.xy.demo.model.OrderListModel
import com.xy.demo.ui.adapter.RemoteMoreAdapter

class RemoteMoreDialog(var dataList: MutableList<OrderListModel.OrderModel>) : MBBaseDialogFragment<DialogRemoteMoreBinding>() {
	
	
	var mAdapter = RemoteMoreAdapter()
	
	
	override fun getLayoutId(): Int {
		return R.layout.dialog_remote_more
	}
	
	override fun getGravity(): Int {
		return Gravity.BOTTOM
	}
	override fun initView() {
		
		
		binding.recyclerView.layoutManager = GridLayoutManager(context,3)
		binding.recyclerView.adapter = mAdapter
		
		// tag 直接传参
		if (tag.toString() == "1"){   // 菜单 --更多
//			dataList.clear()
//			dataList.add("TV")
//			dataList.add("AV")
//			dataList.add("PC")
//			dataList.add("SLEEP")
			
			
			
			binding.zeroTV.visibility = View.GONE
			binding.titleTV.text = "More"
		
		}else{ // 数字
//			dataList.clear()
//			dataList.add("1")
//			dataList.add("2")
//			dataList.add("3")
//			dataList.add("4")
//			dataList.add("5")
//			dataList.add("6")
//			dataList.add("7")
//			dataList.add("8")
//			dataList.add("9")
			binding.titleTV.text = "Number"
		}
		
		mAdapter.setNewInstance(dataList)
		
		
	}
	override fun initListener() {
		binding.closeIV.setOnClickListener {
			dismiss()
		}
		
		binding.zeroTV.setOnClickListener {
		
		}
		
		
		
		mAdapter.setOnItemClickListener { adapter, view, position ->  }
	}
}