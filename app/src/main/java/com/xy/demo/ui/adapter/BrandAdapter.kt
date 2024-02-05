package com.xy.demo.ui.adapter

import android.graphics.Color
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xy.demo.R
import com.xy.demo.model.BrandModel
import com.xy.demo.network.Globals

class BrandAdapter : BaseQuickAdapter<BrandModel,BaseViewHolder>(R.layout.item_brand) {
 
 
	override fun convert(holder: BaseViewHolder, item: BrandModel) {
		val itemName = holder.getView<TextView>(R.id.itemName)
		if (item.brandName.length ==1){
			itemName.setTextColor(Color.parseColor("#999999"))
		}else{
			itemName.setTextColor(Color.parseColor("#ffffff"))
		}
		itemName.text = item.brandName
	}
}