package com.xy.demo.adapter


import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xy.demo.R
import com.xy.demo.db.JunkModel
import com.xy.demo.model.SettingModel

class JunkFileAdapter :BaseQuickAdapter<JunkModel,BaseViewHolder>(R.layout.item_junk_file) {
	override fun convert(holder: BaseViewHolder, item: JunkModel) {
		val itemIV = holder.getView<ImageView>(R.id.itemIV)
		val itemSize = holder.getView<TextView>(R.id.itemSize)
		
		itemSize.setText(item.junkSize.toString())
		if (item.junkName.equals("1")){
			holder.setText(R.id.itemContent,"文件垃圾")
			Glide.with(itemIV).load(R.drawable.icon_file_junk).into(itemIV)
		}else{
			holder.setText(R.id.itemContent,"缓存垃圾")
			Glide.with(itemIV).load(R.drawable.icon_file_cache).into(itemIV)
		}
//		holder.setText(R.id.itemContent,item.titleStr)
	}
}