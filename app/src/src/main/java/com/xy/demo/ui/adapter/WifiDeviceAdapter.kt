package com.xy.demo.ui.adapter

import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.connectsdk.device.ConnectableDevice
import com.xy.demo.R
import com.xy.demo.model.BrandModel
import com.xy.demo.network.Globals


//wifi 搜索
class WifiDeviceAdapter : BaseQuickAdapter<ConnectableDevice,BaseViewHolder>(R.layout.item_wifi_device) {
 
 
	override fun convert(holder: BaseViewHolder, item: ConnectableDevice) {
		val itemName = holder.getView<TextView>(R.id.itemName)
		
		val spannableString = SpannableString(item.friendlyName +"\n" +item.services.toList()[0].serviceDescription.serviceID)
		spannableString.setSpan(ForegroundColorSpan(Color.parseColor("#60000000")), 0, item.friendlyName.length, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
		
		itemName.text = spannableString
	}
}