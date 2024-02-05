package com.xy.demo.ui.adapter

import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jeremyliao.liveeventbus.LiveEventBus
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.db.RemoteModel
import com.xy.demo.network.Globals
import com.xy.demo.ui.infrared.SaveRemoteActivity


//首页 设备 适配器
class DeviceAdapter : BaseQuickAdapter<RemoteModel, BaseViewHolder>(R.layout.item_device) {
	override fun convert(holder: BaseViewHolder, item: RemoteModel) {
		holder.setText(R.id.nameTV, item.brandName)
		holder.setText(R.id.modelTV, item.name)
		val moreTV = holder.getView<TextView>(R.id.moreTV)
		val typeIV = holder.getView<ImageView>(R.id.typeIV)
		val tvIcon = holder.getView<ImageView>(R.id.tvIcon)
		Globals.log("xxxxxxhome $item")
		moreTV.setOnClickListener { v -> popupWindow(context, v,item) }
		if (item.type == 1) {
			typeIV.setBackgroundResource(R.drawable.icon_ir)
		} else {
			typeIV.setBackgroundResource(R.drawable.wifi)
		}
		if (item.color == 1) {
			tvIcon.setBackgroundResource(R.drawable.icon_save_tv_blue)
		}
		if (item.color == 2) {
			tvIcon.setBackgroundResource(R.drawable.icon_save_tv_red)
		}
		if (item.color == 3) {
			tvIcon.setBackgroundResource(R.drawable.icon_save_tv_pink)
		}
		if (item.color == 4) {
			tvIcon.setBackgroundResource(R.drawable.icon_save_tv_yellow)
		}
	}
	
	fun popupWindow(context: Context, view: View?,remoteModel: RemoteModel) {
		// 创建PopupWindow实例,200,LayoutParams.MATCH_PARENT分别是宽度和高度
		val contentView = LayoutInflater.from(context).inflate(R.layout.popup_window_menu, null)
		val popupWindow = PopupWindow(contentView, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true)
		
		// 设置弹出窗口的背景色
		popupWindow.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
		// 设置弹出窗口的位置
		popupWindow.showAsDropDown(view, -50, 0)
		
		//设置可以获取焦点
		popupWindow.isFocusable = true
		//设置可以触摸弹出框以外的区域
		popupWindow.isOutsideTouchable = true
		val editTV = contentView.findViewById<TextView>(R.id.editTV)
		val deleteTV = contentView.findViewById<TextView>(R.id.deleteTV)
		editTV.setOnClickListener {
			val intent = Intent()
			intent.putExtra(Constants.KEY_REMOTE,remoteModel)
			intent.setClass(context, SaveRemoteActivity::class.java)
			context.startActivity(intent)
			popupWindow.dismiss()
		}
		deleteTV.setOnClickListener {
			LiveEventBus.get<String>(Constants.EVENT_DEVICES).post(remoteModel.name)
			popupWindow.dismiss()
		}
	}
}