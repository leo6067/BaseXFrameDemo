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

class RemoteMoreDialog(var dataList: MutableList<OrderListModel.OrderModel>) : MBBaseDialogFragment<DialogRemoteMoreBinding>() {
	
	
	var mAdapter = RemoteMoreAdapter()
	
	lateinit var vibrator : Vibrator
	
	override fun getLayoutId(): Int {
		return R.layout.dialog_remote_more
	}
	
	override fun getGravity(): Int {
		return Gravity.BOTTOM
	}
	override fun initView() {
		
		vibrator  = context?.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
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
			makeParam("0")
			dismiss()
		}
		
		
		
		mAdapter.setOnItemClickListener { adapter, view, position ->
			//指令
			val orderModel = dataList[position]
			val irInfo = ParamParse.getIrCodeList(orderModel.remoteCode, orderModel.frequency.toInt())
			//最终 红外 指令
			ConsumerIrManagerApi.getConsumerIrManager(context).transmit(irInfo.getFrequency(), irInfo.getIrCodeList())
			dismiss()
		}
	}
	
	
	
	//常规指令
	fun makeParam(key: String) {
		for (p in dataList.indices) {
			if (dataList[p].remoteKey == key) {
				val irInfo = ParamParse.getIrCodeList(dataList[p].remoteCode, dataList[p].frequency.toInt())
				//最终 红外 指令
				ConsumerIrManagerApi.getConsumerIrManager(context).transmit(irInfo.getFrequency(), irInfo.getIrCodeList())
				Globals.log("xxxxx指令发送：", irInfo.getIrCodeList().toString())
			}
		}
	}
	
 
}