package com.xy.demo.ui.adapter

import android.text.TextUtils
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xy.demo.R
import com.xy.demo.model.SettingModel

class SettingAdapter :BaseQuickAdapter<SettingModel,BaseViewHolder>(R.layout.item_setting) {
	override fun convert(holder: BaseViewHolder, item: SettingModel) {
		val itemIV = holder.getView<ImageView>(R.id.itemIV)
		Glide.with(itemIV).load(item.drawableId).into(itemIV)
		
		holder.setText(R.id.itemTitle,item.titleStr)
		
		
		val itemSrc = holder.getView<TextView>(R.id.itemSrc)
		
		if (TextUtils.isEmpty(item.srcStr)){
			itemSrc.visibility = View.GONE
		}else{
			itemSrc.text = item.srcStr
		}
		
	}
}