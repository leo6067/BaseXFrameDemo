package com.xy.demo.ui.dialog

import android.content.Context
import android.os.Vibrator
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.xy.demo.R
import com.xy.demo.base.MBBaseDialogFragment
import com.xy.demo.databinding.DialogRemoteMoreBinding
import com.xy.demo.logic.ConsumerIrManagerApi
import com.xy.demo.logic.parse.ParamParse
import com.xy.demo.model.OrderListModel
import com.xy.demo.ui.adapter.RemoteMoreAdapter
import com.xy.xframework.utils.Globals
import java.lang.Exception

class RemoteMoreDialog(var dataList: MutableList<OrderListModel.OrderModel>) : MBBaseDialogFragment<DialogRemoteMoreBinding>() {
	
	
	var mAdapter = RemoteMoreAdapter()
	
	
	override fun getLayoutId(): Int {
		return R.layout.dialog_remote_more
	}
	
	override fun getGravity(): Int {
		return Gravity.BOTTOM
	}
	
	override fun initView() {
		binding.recyclerView.layoutManager = GridLayoutManager(context, 3)
		binding.recyclerView.adapter = mAdapter
		
		// tag 直接传参
//		if (tag.toString() == "1"){   // 菜单 --更多
//			dataList.clear()
//			dataList.add("TV")
//			dataList.add("AV")
//			dataList.add("PC")
//			dataList.add("SLEEP")
		
		mAdapter.setNewInstance(dataList)
	}
	
	override fun initListener() {
		binding.closeIV.setOnClickListener {
			dismiss()
		}
		
		
		mAdapter.setOnItemClickListener { adapter, view, position ->
			//指令
			val orderModel = dataList[position]
			val irInfo = ParamParse.getIrCodeList(orderModel.remoteCode, orderModel.frequency.toInt())
			//最终 红外 指令
			try {
				ConsumerIrManagerApi.getConsumerIrManager(context).transmit(irInfo.getFrequency(), irInfo.getIrCodeList())
			} catch (e: Exception) {
//				binding.invalidTV.performClick()
			}
		}
	}
	
	
	//常规指令
	fun makeParam(key: String) {
		for (p in dataList.indices) {
			if (dataList[p].remoteKey == key) {
				val irInfo = ParamParse.getIrCodeList(dataList[p].remoteCode, dataList[p].frequency.toInt())
				//最终 红外 指令
				try {
					ConsumerIrManagerApi.getConsumerIrManager(context).transmit(irInfo.getFrequency(), irInfo.getIrCodeList())
				} catch (e: Exception) {
//				binding.invalidTV.performClick()
				}
			}
		}
	}
	
	
}