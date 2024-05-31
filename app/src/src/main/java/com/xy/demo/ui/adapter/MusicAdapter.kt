package com.xy.demo.ui.adapter

import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.xy.demo.R
import com.xy.demo.model.MusicModel
import com.xy.demo.network.Globals

class MusicAdapter :BaseQuickAdapter<MusicModel,BaseViewHolder>(R.layout.item_music) {
	override fun convert(holder: BaseViewHolder, item: MusicModel) {
		
		val musicName = holder.getView<TextView>(R.id.musicName)
	 
		musicName.text = item.name
  
	}
}