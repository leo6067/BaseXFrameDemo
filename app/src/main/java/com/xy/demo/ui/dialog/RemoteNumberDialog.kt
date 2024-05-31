package com.xy.demo.ui.dialog

import android.content.Context
import android.os.Vibrator
import android.view.Gravity
import android.view.View
import androidx.recyclerview.widget.GridLayoutManager
import com.google.gson.annotations.Until
import com.xy.demo.R
import com.xy.demo.base.MBBaseDialogFragment
import com.xy.demo.databinding.DialogRemoteMoreBinding
import com.xy.demo.databinding.DialogRemoteNumberBinding
import com.xy.demo.logic.ConsumerIrManagerApi
import com.xy.demo.logic.parse.ParamParse
import com.xy.demo.model.OrderListModel
import com.xy.demo.ui.adapter.RemoteMoreAdapter
import com.xy.xframework.utils.Globals
import java.lang.Exception

class RemoteNumberDialog(var dataList: MutableList<OrderListModel.OrderModel>) : MBBaseDialogFragment<DialogRemoteNumberBinding>() {
	
	
	override fun getLayoutId(): Int {
		return R.layout.dialog_remote_number
	}
	
	override fun getGravity(): Int {
		return Gravity.BOTTOM
	}
	
	override fun initView() {
	}
	
	override fun initListener() {
		binding.closeIV.setOnClickListener {
			dismiss()
		}
		
		binding.zeroTV.setOnClickListener {
			makeParam("0")
		}
		
		binding.oneTV.setOnClickListener {
			makeParam("1")
		}
		
		binding.twoTV.setOnClickListener {
			makeParam("2")
		}
		
		binding.threeTV.setOnClickListener {
			makeParam("3")
			
		}
		
		binding.fourTV.setOnClickListener {
			makeParam("4")
		}
		
		binding.fiveTV.setOnClickListener {
			makeParam("5")
		}
		
		binding.sixTV.setOnClickListener {
			makeParam("6")
		}
		
		binding.sevenTV.setOnClickListener {
			makeParam("7")
		}
		
		binding.eightTV.setOnClickListener {
			makeParam("8")
		}
		
		binding.nineTV.setOnClickListener {
			makeParam("9")
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