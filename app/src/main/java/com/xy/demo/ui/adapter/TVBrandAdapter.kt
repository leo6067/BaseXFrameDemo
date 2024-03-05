package com.xy.demo.ui.adapter

import android.graphics.Color
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.ruffian.library.widget.RLinearLayout
import com.xy.demo.R
import com.xy.demo.model.BrandModel


//品牌列表 适配器
class TVBrandAdapter : BaseQuickAdapter<BrandModel,BaseViewHolder>(R.layout.item_brand) {
 
	override fun convert(holder: BaseViewHolder, item: BrandModel) {
		val rootLay = holder.getView<RLinearLayout>(R.id.rootLay)
		val itemIcon = holder.getView<ImageView>(R.id.itemIcon)
		val itemName = holder.getView<TextView>(R.id.itemName)
		if (item.brandName.length ==1){
			itemName.setTextColor(Color.parseColor("#999999"))
			rootLay.helper.backgroundColorNormal = Color.parseColor("#ffffff")
			itemIcon.visibility = View.GONE
		}else{
			itemName.setTextColor(Color.parseColor("#616161"))
			rootLay.helper.backgroundColorNormal = Color.parseColor("#f3f3f3")
			itemIcon.visibility = View.VISIBLE
		}
		
		itemName.text = item.brandName
	}
}