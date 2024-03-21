package com.xy.demo.adapter


import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.jeremyliao.liveeventbus.LiveEventBus
import com.jeremyliao.liveeventbus.core.LiveEvent
import com.xy.demo.R
import com.xy.demo.base.Constants
import com.xy.demo.db.JunkModel
import com.xy.demo.model.SettingModel
import com.xy.demo.network.Globals
import com.xy.demo.utils.MyUtils

class JunkFileAdapter :BaseQuickAdapter<JunkModel,BaseViewHolder>(R.layout.item_junk_file) {
	override fun convert(holder: BaseViewHolder, item: JunkModel) {
		val itemIV = holder.getView<ImageView>(R.id.itemIV)
		val itemSize = holder.getView<TextView>(R.id.itemSize)
		val itemCheck = holder.getView<CheckBox>(R.id.itemCheck)
	 
		itemSize.setText(MyUtils.byte2FitMemorySize(item.junkSize))
		if (item.junkName.equals("1")){
			holder.setText(R.id.itemContent, context.getString(R.string.junk_files))
			Glide.with(itemIV).load(R.drawable.icon_file_junk).into(itemIV)
		}else{
			holder.setText(R.id.itemContent, context.getString(R.string.cached_files))
			Glide.with(itemIV).load(R.drawable.icon_file_cache).into(itemIV)
		}
//		holder.setText(R.id.itemContent,item.titleStr)
		
		
		itemCheck.setOnCheckedChangeListener{_,isCheck->
				item.isCheck = isCheck
			 
				LiveEventBus.get(Constants.EVENT_CACHE_FILE_SIZE,JunkModel::class.java).post(item)
			 
		}
	}
}